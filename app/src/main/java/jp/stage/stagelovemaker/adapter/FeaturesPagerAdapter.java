package jp.stage.stagelovemaker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import jp.stage.stagelovemaker.fragment.ChatFragment;
import jp.stage.stagelovemaker.fragment.ProfileFragment;
import jp.stage.stagelovemaker.fragment.StageFragment;

/**
 * Created by congn on 7/11/2017.
 */

public class FeaturesPagerAdapter extends FragmentPagerAdapter {
    ProfileFragment profileFragment;
    StageFragment stageFragment;
    ChatFragment chatFragment;

    public FeaturesPagerAdapter(FragmentManager fm) {
        super(fm);
        profileFragment = ProfileFragment.newInstance();
        stageFragment = StageFragment.newInstance();
        chatFragment = ChatFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return profileFragment;
            case 1:
                return stageFragment;
            case 2:
                return chatFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position >= getCount()) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
