package jp.stage.stagelovemaker.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.TitleBar;

/**
 * Created by congn on 7/12/2017.
 */

public class EditProfileFragment extends BaseFragment implements TitleBar.TitleBarCallback,
        View.OnClickListener {
    public static final String TAG = "EditProfileFragment";
    final int NUMBER_AVATAR = 6;
    final int NO_AVATAR = -2;

    TitleBar titleBar;
    ScrollView scrollView;
    RelativeLayout avatarMainLayout;
    LinearLayout avatarAddLayout;

    ArrayList<RoundedImageView> avatarImageView = new ArrayList<>();
    ArrayList<ImageView> removeImageView = new ArrayList<>();

    Uri picUri;

    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        titleBar = (TitleBar) view.findViewById(R.id.titleBar);
        scrollView = (ScrollView) view.findViewById(R.id.edit_profile_scroll);
        avatarMainLayout = (RelativeLayout) view.findViewById(R.id.avatar_main_layout);
        avatarAddLayout = (LinearLayout) view.findViewById(R.id.avatar_add_layout);
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.avatar_main_img));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_main_img));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.avatar_default_1_img));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_default_1_img));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.avatar_default_2_img));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_default_2_img));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.avatar_default_3_img));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_default_3_img));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.avatar_default_4_img));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_default_4_img));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.avatar_default_5_img));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_default_5_img));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        titleBar.setTitle(getString(R.string.edit_profile));
        titleBar.enableBackButton();
        titleBar.setCallback(this);

        for (int i = 0; i < avatarImageView.size(); i++) {
            avatarImageView.get(i).setOnClickListener(this);
            removeImageView.get(i).setOnClickListener(this);
        }

        for (int i = 0; i < NUMBER_AVATAR; i++) {
            removeImageView.get(i).setVisibility(View.INVISIBLE);
            avatarImageView.get(i).setImageResource(R.drawable.icon_add_avatar);
            avatarImageView.get(i).setCornerRadius(0);
        }
        calculateAvatarLayout();
    }

    void calculateAvatarLayout() {
        int margin = (int) getResources().getDimension(R.dimen.margin_avatar_edit);
        int widthAvatarLayout = Utils.getiWidthScreen(getActivity()) - (margin * 2);
        int widthAvatarMain = (widthAvatarLayout / 3) * 2;
        ViewGroup.LayoutParams params = avatarMainLayout.getLayoutParams();
        params.width = widthAvatarMain;
        params.height = widthAvatarMain;
        avatarMainLayout.requestLayout();

        ViewGroup.LayoutParams paramsAdd = avatarAddLayout.getLayoutParams();
        paramsAdd.height = widthAvatarLayout / 3;
        avatarAddLayout.requestLayout();
    }

    @Override
    public void onTitleBarClicked() {

    }

    @Override
    public void onRightButtonClicked() {

    }

    @Override
    public void onBackButtonClicked() {

    }

    @Override
    public void onMiddleButtonClicked() {

    }

    @Override
    public void onClick(View v) {

    }
}
