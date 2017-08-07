package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import at.markushi.ui.CircleButton;
import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.adapter.InstagramImagePageAdapter;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.model.InstagramPhoto;
import jp.stage.stagelovemaker.model.UserInfo;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.PageControl;
import jp.stage.stagelovemaker.views.TitleBar;
import jp.stage.stagelovemaker.views.UserImageSlide;

/**
 * Created by congn on 8/4/2017.
 */

public class DetailProfileFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "DetailProfileFragment";

    UserImageSlide userImageSlide;
    TextView tvName;
    TextView tvAge;
    TextView tvWork;
    TextView tvSchool;

    CircularImageView ivBack;
    CircleButton ivNope;
    CircleButton ivLike;
    CircleButton ivSuperLike;

    ArrayList<InstagramPhoto> photoModel;
    String instagramUser;
    PageControl pageControl;
    RelativeLayout instagramLayout;
    UserInfo userInfo;
    DetailProfileCallback delegate;

    ViewPager instagramPager;
    InstagramImagePageAdapter instagramImagePageAdapter;
    int numberColumn = 0;
    int widthButton;

    public static DetailProfileFragment newInstance(UserInfo user) {

        Bundle args = new Bundle();
        args.putParcelable("user", user);
        DetailProfileFragment fragment = new DetailProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pageControl.setCurrentPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_user, container, false);
        userImageSlide = (UserImageSlide) view.findViewById(R.id.user_image_slide);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvAge = (TextView) view.findViewById(R.id.tv_age);
        tvWork = (TextView) view.findViewById(R.id.tv_work);
        tvSchool = (TextView) view.findViewById(R.id.tv_school);
        ivBack = (CircularImageView) view.findViewById(R.id.iv_back);
        ivNope = (CircleButton) view.findViewById(R.id.circleButtonClose);
        ivLike = (CircleButton) view.findViewById(R.id.circleButtonHeart);
        ivSuperLike = (CircleButton) view.findViewById(R.id.circleButtonStar);

        instagramPager = (ViewPager) view.findViewById(R.id.view_pager);
        pageControl = (PageControl) view.findViewById(R.id.instagram_pagecontrol);
        instagramLayout = (RelativeLayout) view.findViewById(R.id.instagram_profile_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInfo = getArguments().getParcelable("user");
        userImageSlide.setAvatar(getChildFragmentManager(), userInfo.getAvatars());
        tvName.setText(userInfo.getFirstName() + ",");
        tvAge.setText(String.valueOf(userInfo.getBirthday()));
        tvSchool.setText(userInfo.getSchool());
        tvWork.setText(userInfo.getCurrentWork());

        ivBack.setOnClickListener(this);
        ivNope.setOnClickListener(this);
        ivLike.setOnClickListener(this);
        ivSuperLike.setOnClickListener(this);
        calculateLayout();
        if (photoModel == null || photoModel.size() == 0) {
            if (!TextUtils.isEmpty(userInfo.getInstagram_username())) {
                requestInstagramImage(userInfo.getInstagram_username());
                instagramLayout.setVisibility(View.VISIBLE);
            } else {
                instagramLayout.setVisibility(View.GONE);
            }
        } else {
            loadInstagramImage();
        }

    }

    private void calculateLayout() {
        int width = Utils.getiWidthScreen(getActivity());
        int margin = (int) getResources().getDimension(R.dimen.form_margin);
        int paddingButton = (int) getResources().getDimension(R.dimen.padding_interest_button);
        widthButton = (width - (margin * 2) - (paddingButton * 2)) / 3;
    }

    private void loadInstagramImage() {
        if (TextUtils.isEmpty(userInfo.getInstagram_username())) {
            return;
        }
        if (instagramImagePageAdapter == null) {
            numberColumn = 3;
            int paddingButton = (int) getResources().getDimension(R.dimen.padding_interest_button);
            ViewGroup.LayoutParams paramsAdapter = instagramPager.getLayoutParams();
            if (photoModel.size() > 2) {
                paramsAdapter.height = widthButton * 2 + paddingButton;
            } else {
                paramsAdapter.height = widthButton;
            }
        }

        int iAdd = 0;
        if ((photoModel.size() % (numberColumn * 2)) != 0) {
            iAdd = 1;
        }
        int numberFragment = (photoModel.size() / (numberColumn * 2)) + iAdd;
        if (photoModel.size() > 4) {
            pageControl.setNumberOfPages(numberFragment);
            pageControl.setCurrentPageColor(ContextCompat.getColor(getContext(), R.color.very_light_gray));
            pageControl.setHintPageColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
            pageControl.setCurrentPage(0);
        } else {
            pageControl.setVisibility(View.GONE);
        }
        instagramImagePageAdapter = new InstagramImagePageAdapter(getChildFragmentManager(),
                photoModel, numberColumn, numberFragment, userInfo.getInstagram_username());
        instagramPager.setAdapter(instagramImagePageAdapter);
        instagramPager.addOnPageChangeListener(viewPagerPageChangeListener);
        instagramImagePageAdapter.notifyDataSetChanged();
    }

    void requestInstagramImage(String userName) {
        Gson gson = new Gson();
        String response = "";
        try {
            response = Utils.loadJSONFromAsset(getActivity(), Constants.SEED_INSTAGRAM);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(response)) {
            InstagramPhoto[] models = gson.fromJson(response, InstagramPhoto[].class);
            if (models != null) {
                photoModel = new ArrayList<>(Arrays.asList(models));
                loadInstagramImage();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.circleButtonClose:
                if (delegate != null) {
                    delegate.onNopeClicked();
                }
                break;
            case R.id.circleButtonHeart:
                if (delegate != null) {
                    delegate.onLikeClicked();
                }
                break;
            case R.id.circleButtonStar:
                if (delegate != null) {
                    delegate.onStarClicked();
                }
                break;
        }
    }

    public void setCallback(DetailProfileCallback callback) {
        delegate = callback;
    }

    public interface DetailProfileCallback {
        void onNopeClicked();

        void onLikeClicked();

        void onStarClicked();
    }
}
