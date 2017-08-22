package jp.stage.stagelovemaker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.model.UsersPageModel;
import jp.stage.stagelovemaker.model.UsersPageResponseModel;
import jp.stage.stagelovemaker.network.IHttpResponse;
import jp.stage.stagelovemaker.network.NetworkManager;
import jp.stage.stagelovemaker.utils.Constants;

/**
 * Created by congn on 8/4/2017.
 */

public class MainStageFragment extends BaseFragment implements SearchFragment.SearchFragmentCallback {
    public static final String TAG = "MainStageFragment";

    NetworkManager networkManager;
    StageFragment stageFragment;
    SearchFragment searchFragment;
    UsersPageModel usersPageModel;
    Gson gson;

    public static MainStageFragment newInstance() {
        Bundle args = new Bundle();
        MainStageFragment fragment = new MainStageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        networkManager = new NetworkManager(getContext(), iHttpResponse);
        gson = new Gson();
    }

    private IHttpResponse iHttpResponse = new IHttpResponse() {
        @Override
        public void onHttpComplete(String response, int idRequest) {
            switch (idRequest) {
                case Constants.ID_LIST_PEOPLE:
                    UsersPageResponseModel usersPageResponseModel
                            = gson.fromJson(response, UsersPageResponseModel.class);
                    usersPageModel = usersPageResponseModel.getUsersPage();
                    if (usersPageModel != null && usersPageModel.getRecords() != null &&
                            usersPageModel.getRecords().size() > 0) {
                        updateData();
                    } else {
                        if (searchFragment != null) {
                            searchFragment.changeBackgroundNoPals(R.string.no_matching_found);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onHttpError(String response, int idRequest, int errorCode) {
            switch (idRequest) {
                case Constants.ID_LIST_PEOPLE: {
                    if (searchFragment != null) {
                        searchFragment.changeBackgroundNoPals(R.string.no_matching_found);
                    }
                    break;
                }
            }
        }
    };

    private void updateData() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (searchFragment != null) {
                        searchFragment.stopTimer();
                    }
                    stageFragment = StageFragment.newInstance(usersPageModel);
                    replace(stageFragment, StageFragment.TAG, false, false, R.id.flPals);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("usersPage", usersPageModel);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_stage, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            usersPageModel = savedInstanceState.getParcelable("usersPage");
            if (usersPageModel != null) {
                searchFragment = (SearchFragment) getActivity().getSupportFragmentManager()
                        .findFragmentByTag(SearchFragment.TAG);
                if (searchFragment == null) {
                    searchFragment = SearchFragment.newInstance();
                }
                searchFragment.setCallback(this);
            }
        } else {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setCallback(this);
            replace(searchFragment, SearchFragment.TAG, false, false, R.id.flPals);
        }
    }

    @Override
    public void onSearchFinished() {
//        StageFragment stageFragment = StageFragment.newInstance();
//        replace(stageFragment, StageFragment.TAG, false, false, R.id.flPals);
        getListPeople();
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

    private void getListPeople() {
        networkManager.requestApiNoProgress(networkManager.getPeopleList(1), Constants.ID_LIST_PEOPLE);
    }
}
