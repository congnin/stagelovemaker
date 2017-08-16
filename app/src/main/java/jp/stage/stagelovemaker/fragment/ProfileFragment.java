package jp.stage.stagelovemaker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.activity.MainActivity;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.base.EventDistributor;
import jp.stage.stagelovemaker.model.DiscoverModel;
import jp.stage.stagelovemaker.model.SettingModel;
import jp.stage.stagelovemaker.model.UserInfoModel;
import jp.stage.stagelovemaker.model.UserTokenModel;
import jp.stage.stagelovemaker.network.IHttpResponse;
import jp.stage.stagelovemaker.network.NetworkManager;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.Utils;

/**
 * Created by congn on 7/11/2017.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    CircularImageView ivSettings;
    CircularImageView ivEditProfile;
    TextView tvFormattedName;
    TextView tvSettings;
    TextView tvEditInfo;
    CircleImageView ivAvatar;

    NetworkManager networkManager;
    UserTokenModel userTokenModel;
    Gson gson;

    private static final int EVENTS = EventDistributor.MY_PROFILE_CHANGE;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        networkManager = new NetworkManager(getActivity(), iHttpResponse);
        gson = new Gson();
    }

    private IHttpResponse iHttpResponse = new IHttpResponse() {
        @Override
        public void onHttpComplete(String response, int idRequest) {
            switch (idRequest) {
                case Constants.ID_SELF_INFO:
                    userTokenModel = gson.fromJson(response, UserTokenModel.class);
                    if (userTokenModel != null) {
                        handelResultAPI(idRequest);
                    }
            }
        }

        @Override
        public void onHttpError(String response, int idRequest, int errorCode) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ivSettings = (CircularImageView) view.findViewById(R.id.iv_settings);
        ivEditProfile = (CircularImageView) view.findViewById(R.id.iv_edit);
        tvFormattedName = (TextView) view.findViewById(R.id.tv_formatted_name);
        tvSettings = (TextView) view.findViewById(R.id.tv_settings);
        tvEditInfo = (TextView) view.findViewById(R.id.tv_edit);
        ivAvatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivSettings.setOnClickListener(this);
        ivEditProfile.setOnClickListener(this);

        if (userTokenModel != null) {
            updateUserInfo();
        } else {
            requestSelfProfile();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventDistributor.getInstance().register(contentUpdate);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventDistributor.getInstance().unregister(contentUpdate);
    }

    private EventDistributor.EventListener contentUpdate = new EventDistributor.EventListener() {
        @Override
        public void update(EventDistributor eventDistributor, Integer arg) {
            if ((arg & EVENTS) != 0) {
                requestSelfProfile();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_settings:
                if (userTokenModel != null && userTokenModel.getUserInfo() != null
                        && userTokenModel.getUserInfo().getSetting() != null) {
                    SettingModel settingModel = userTokenModel.getUserInfo().getSetting();
                    DiscoverModel discoverModel = userTokenModel.getUserInfo().getDiscover();
                    SettingFragment settingFragment = SettingFragment.newInstance(settingModel, discoverModel);
                    settingFragment.setCallback(settingCallback);
                    add(settingFragment, SettingFragment.TAG, true, true, R.id.flContainer);
                }
                break;
            case R.id.iv_edit:
                EditProfileFragment editProfileFragment = EditProfileFragment.newInstance(userTokenModel.getUserInfo());
                add(editProfileFragment, EditProfileFragment.TAG, true, true, R.id.flContainer);
        }
    }

    public void requestSelfProfile() {
        int id = Utils.getApplication(getActivity()).getId(getActivity());
        networkManager.requestApiNoProgress(networkManager.getProfile(id), Constants.ID_SELF_INFO);
    }

    void handelResultAPI(final int idRequest) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                switch (idRequest) {
                    case Constants.ID_SELF_INFO:
                        updateUserInfo();
                        break;

                }
            });
        }
    }

    private void updateUserInfo() {
        UserInfoModel userInfoModel = userTokenModel.getUserInfo();
        if (!TextUtils.isEmpty(userInfoModel.getFirstName())) {
            if (userInfoModel.getAge() != null) {
                tvFormattedName.setText(userInfoModel.getFirstName() + ", " + userInfoModel.getAge());
            } else {
                tvFormattedName.setText(userInfoModel.getFirstName());
            }
        }

        if (!userInfoModel.getAvatars().isEmpty()) {
            for (int i = 0; i < userInfoModel.getAvatars().size(); i++) {
                if (!TextUtils.isEmpty(userInfoModel.getAvatars().get(i).getUrl())) {
                    Glide.with(getContext()).load(userInfoModel.getAvatars().get(i).getUrl()).centerCrop().into(ivAvatar);
                    break;
                }
            }
        }
    }

    public SettingFragment.SettingFragmentCallback settingCallback =
            () -> EventDistributor.getInstance().sendMyProfileUpdateBroadcast();
}
