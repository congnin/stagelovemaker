package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;

/**
 * Created by congn on 7/11/2017.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    CircularImageView ivSettings;
    CircularImageView ivEditProfile;
    TextView tvFormattedName;
    TextView tvSettings;
    TextView tvEditInfo;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ivSettings = (CircularImageView) view.findViewById(R.id.iv_settings);
        ivEditProfile = (CircularImageView) view.findViewById(R.id.iv_edit);
        tvFormattedName = (TextView) view.findViewById(R.id.tv_formatted_name);
        tvSettings = (TextView) view.findViewById(R.id.tv_settings);
        tvEditInfo = (TextView) view.findViewById(R.id.tv_edit);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivSettings.setOnClickListener(this);
        ivEditProfile.setOnClickListener(this);
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
