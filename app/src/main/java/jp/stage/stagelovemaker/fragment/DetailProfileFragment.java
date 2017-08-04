package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.model.UserInfo;
import jp.stage.stagelovemaker.views.UserImageSlide;

/**
 * Created by congn on 8/4/2017.
 */

public class DetailProfileFragment extends BaseFragment {
    public static final String TAG = "DetailProfileFragment";

    UserImageSlide userImageSlide;
    UserInfo userInfo;
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInfo = getArguments().getParcelable("user");
        userImageSlide.setAvatar(getChildFragmentManager(), userInfo.getAvatars());
    }
}
