package jp.stage.stagelovemaker.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;

/**
 * Created by congn on 7/11/2017.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    ImageView ivSettings;
    ImageView ivEditProfile;
    TextView tvFormattedName;
    TextView tvWorkSite;
    TextView tvCollege;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ivSettings = (ImageView) view.findViewById(R.id.iv_settings);
        ivEditProfile = (ImageView) view.findViewById(R.id.iv_edit);
        tvFormattedName = (TextView) view.findViewById(R.id.tv_formatted_name);
        tvWorkSite = (TextView) view.findViewById(R.id.tv_work_location);
        tvCollege = (TextView) view.findViewById(R.id.tv_college);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivSettings.setOnClickListener(this);
        ivEditProfile.setOnClickListener(this);

        Typeface boldType = Typeface.createFromAsset(getActivity().getAssets(), "fonts/proximanovasoft-bold.otf");
        tvFormattedName.setTypeface(boldType);

        Typeface semiBoldType = Typeface.createFromAsset(getActivity().getAssets(), "fonts/proximanovasoft-semibold.otf");
        tvWorkSite.setTypeface(semiBoldType);
        tvCollege.setTypeface(semiBoldType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_settings:
                SettingFragment settingFragment = SettingFragment.newInstance();
                add(settingFragment, SettingFragment.TAG, true, true, R.id.flContainer);
                break;
            case R.id.iv_edit:
                EditProfileFragment editProfileFragment = EditProfileFragment.newInstance();
                add(editProfileFragment, EditProfileFragment.TAG, true, true, R.id.flContainer);
        }
    }
}
