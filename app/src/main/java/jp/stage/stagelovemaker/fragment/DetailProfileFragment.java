package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

import at.markushi.ui.CircleButton;
import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.model.UserInfo;
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

    UserInfo userInfo;
    DetailProfileCallback delegate;


    public static DetailProfileFragment newInstance(UserInfo user) {

        Bundle args = new Bundle();
        args.putParcelable("user", user);
        DetailProfileFragment fragment = new DetailProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInfo = getArguments().getParcelable("user");
        userImageSlide.setAvatar(getChildFragmentManager(), userInfo.getAvatars());
        tvName.setText(userInfo.getFirstName() + ", ");
        tvAge.setText(String.valueOf(userInfo.getBirthday()));
        tvSchool.setText(userInfo.getSchool());
        tvWork.setText(userInfo.getCurrentWork());

        ivBack.setOnClickListener(this);
        ivNope.setOnClickListener(this);
        ivLike.setOnClickListener(this);
        ivSuperLike.setOnClickListener(this);
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
