package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.utils.Constants;

/**
 * Created by congn on 8/4/2017.
 */

public class MainStageFragment extends BaseFragment implements SearchFragment.SearchFragmentCallback {
    public static final String TAG = "MainStageFragment";

    SearchFragment searchFragment;

    public static MainStageFragment newInstance() {
        Bundle args = new Bundle();
        MainStageFragment fragment = new MainStageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_stage, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            searchFragment = (SearchFragment) getActivity().getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
            if (searchFragment == null) {
                searchFragment = SearchFragment.newInstance();
            }
            searchFragment.setCallback(this);
        } else {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setCallback(this);
            replace(searchFragment, SearchFragment.TAG, false, false, R.id.flPals);
        }
    }

    @Override
    public void onSearchFinished() {
        StageFragment stageFragment = StageFragment.newInstance();
        replace(stageFragment, StageFragment.TAG, false, false, R.id.flPals);
    }

    public void allowAccessLocation(Boolean b) {
        if (searchFragment != null) {
            if (b) {
                searchFragment.getLocation();
            } else {
                searchFragment.clickAllow(false, Constants.GET_LOCATION);
            }
        }
    }
}
