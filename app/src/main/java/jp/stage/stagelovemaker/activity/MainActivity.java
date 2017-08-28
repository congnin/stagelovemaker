package jp.stage.stagelovemaker.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import jp.stage.stagelovemaker.MyApplication;
import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.adapter.FeaturesPagerAdapter;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.base.CommonActivity;
import jp.stage.stagelovemaker.base.EventDistributor;
import jp.stage.stagelovemaker.base.UserPreferences;
import jp.stage.stagelovemaker.fragment.MessageFragment;
import jp.stage.stagelovemaker.model.AvatarModel;
import jp.stage.stagelovemaker.model.ErrorModel;
import jp.stage.stagelovemaker.model.InfoRoomModel;
import jp.stage.stagelovemaker.model.RoomResponseModel;
import jp.stage.stagelovemaker.model.UserInfo;
import jp.stage.stagelovemaker.model.UserInfoModel;
import jp.stage.stagelovemaker.model.UserTokenModel;
import jp.stage.stagelovemaker.network.IHttpResponse;
import jp.stage.stagelovemaker.network.NetworkManager;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.GPSTracker;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.MainTabBar;
import jp.stage.stagelovemaker.views.NonSwipeableViewPager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends CommonActivity implements MainTabBar.MainTabBarCallback,
        ViewPager.OnPageChangeListener {
    public static final String TAG = "MainActivity";
    MainTabBar mainTabBar;
    NonSwipeableViewPager viewPager;
    FeaturesPagerAdapter featuresPagerAdapter;
    int indexTab;

    UserInfoModel loginModel;
    NetworkManager networkManager;
    Gson gson = new Gson();

    private static final int EVENTS = EventDistributor.MY_PROFILE_CHANGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkLogin()) {
            if (isTaskRoot()) {
                Intent intent = getIntent();
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                    finish();
                    return;
                }
            }
            setContentView(R.layout.activity_main);
            mainTabBar = (MainTabBar) findViewById(R.id.main_tabbar);
            viewPager = (NonSwipeableViewPager) findViewById(R.id.view_pager);

            mainTabBar.setCallback(this);
            mainTabBar.changeTab(MainTabBar.TAB_STAGE);
            networkManager = new NetworkManager(this, iHttpResponse);
            viewPager.addOnPageChangeListener(this);

            requestSelfProfile(true);
        } else {
            startNewActivity(LoginActivity.class, null);
            ActivityCompat.finishAffinity(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventDistributor.getInstance().register(contentUpdate);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventDistributor.getInstance().unregister(contentUpdate);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private EventDistributor.EventListener contentUpdate = new EventDistributor.EventListener() {
        @Override
        public void update(EventDistributor eventDistributor, Integer arg) {
            if ((EVENTS & arg) != 0) {
                Log.d(TAG, "Received contentUpdate Intent.");
                requestSelfProfile(false);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                featuresPagerAdapter.getLocation(true);
            } else {
                featuresPagerAdapter.getLocation(false);
            }
        }
    }

    @Override
    public void onTabChanged(int index) {
        Utils.hideSoftKeyboard(this);
        viewPager.setCurrentItem(index - 1, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mainTabBar.changeTab(position + 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private boolean checkLogin() {
        String accessToken = UserPreferences.getPrefUserAccessToken();
        return !TextUtils.isEmpty(accessToken);
    }

    public void requestSelfProfile(boolean init) {
        int id = UserPreferences.getCurrentUserId();
        if (init) {
            networkManager.requestApiNoProgress(networkManager.getProfile(id), Constants.ID_SELF_INFO);
        } else {
            networkManager.requestApiNoProgress(networkManager.getProfile(id), Constants.ID_UPDATE_USER);
        }

    }

    void loadDataProfile() {
        int id = UserPreferences.getCurrentUserId();
        if (Utils.getLocation(this) == null) {
            GPSTracker gps = new GPSTracker(this);
            gps.getLocation();
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Utils.setLocation(this, latitude, longitude);
                if (networkManager == null) {
                    networkManager = new NetworkManager(this, iHttpResponse);
                }
                networkManager.requestApiNoProgress(networkManager.updateLocation(id, latitude, longitude),
                        Constants.ID_UPDATED_LOCATION);
            }
        }

        loadFeatures();
        MyApplication app = Utils.getApplication(this);
        if (app != null) {
            if (loginModel.getSetting() != null) {
//                app.setUnitDistance(loginModel.getSetting().getDistanceUnit(), this);
            }
        }
        final Handler handler = new Handler();
        handler.postDelayed(() -> mainTabBar.changeTab(MainTabBar.TAB_STAGE), 100);
    }

    private void loadFeatures() {
        featuresPagerAdapter = new FeaturesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(featuresPagerAdapter);
    }

    public IHttpResponse iHttpResponse = new IHttpResponse() {
        @Override
        public void onHttpComplete(String response, int idRequest) {
            switch (idRequest) {
                case Constants.ID_SELF_INFO:
                    UserTokenModel model = gson.fromJson(response, UserTokenModel.class);
                    if (model != null) {
                        loginModel = model.getUserInfo();
                        setAvatar();
                        loadDataProfile();
                    }
                    break;
                case Constants.ID_UPDATE_USER:
                    UserTokenModel update = gson.fromJson(response, UserTokenModel.class);
                    if (update != null) {
                        setAvatar();
                        loginModel = update.getUserInfo();
                    }
                    break;
//                case Constants.ID_CHAT_ROOM:
//                    RoomResponseModel roomResponseModel = gson.fromJson(response, RoomResponseModel.class);
//                    if(roomResponseModel != null){
//                        InfoRoomModel infoRoomModel = roomResponseModel.getInfoRoom();
//                        runOnUiThread(() -> {
//                            MessageFragment messageFragment = MessageFragment.newInstance(infoRoomModel);
//                            add(messageFragment, MessageFragment.TAG, true, true, R.id.flContainer);
//                        });
//                    }
//                    break;
            }
        }

        @Override
        public void onHttpError(String response, int idRequest, int errorCode) {
//            ErrorModel error = gson.fromJson(response, ErrorModel.class);
//            if(TextUtils.isEmpty(error.getErrorMsg())){
//                Toast.makeText(MainActivity.this, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
//            }
        }
    };

    private void setAvatar() {
        if (loginModel != null && loginModel.getAvatars() != null && !loginModel.getAvatars().isEmpty()) {
            List<AvatarModel> listAvatar = loginModel.getAvatars();
            for (int i = 0; i < listAvatar.size(); i++) {
                if (!TextUtils.isEmpty(listAvatar.get(i).getUrl())) {
//                    MyApplication.setMainAvatar(listAvatar.get(i).getUrl());
                    break;
                }
            }
        }
    }

    public void getChatRoom(UserInfoModel receiver) {
        MessageFragment messageFragment = MessageFragment.newInstance(receiver);
        add(messageFragment, MessageFragment.TAG, true, true, R.id.flContainer);
    }

    public UserInfoModel getLoginModel(){
        return loginModel;
    }
}
