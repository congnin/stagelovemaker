package jp.stage.stagelovemaker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.adapter.MatchesRecycleAdapter;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.base.EventDistributor;
import jp.stage.stagelovemaker.network.IHttpResponse;
import jp.stage.stagelovemaker.network.NetworkManager;
import jp.stage.stagelovemaker.views.FormInputText;
import jp.stage.stagelovemaker.views.SearchEmpty;
import jp.stage.stagelovemaker.views.TitleBar;

/**
 * Created by congn on 8/4/2017.
 */

public class MatchesFragment extends BaseFragment implements FormInputText.FormInputTextDelegate,
        IHttpResponse {
    public static final String TAG = "MatchesFragment";

    private static final int EVENTS = EventDistributor.MATCH_CHANGE;

    FormInputText searchInput;
    SearchEmpty searchEmpty;
    NetworkManager networkManager;

    protected RecyclerView recyclerViewMatches;
    protected MatchesRecycleAdapter listMatchesAdapter;

    private boolean itemsLoaded = false;
    private boolean viewsCreated = false;

    public static MatchesFragment newInstance() {
        Bundle args = new Bundle();
        MatchesFragment fragment = new MatchesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        networkManager = new NetworkManager(context, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventDistributor.getInstance().register(contentUpdate);
        if(viewsCreated && itemsLoaded){
            onFragmentLoaded();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMatches();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventDistributor.getInstance().unregister(contentUpdate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetViewState();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        searchEmpty = (SearchEmpty) view.findViewById(R.id.search_empty_chat_view);
        searchInput = (FormInputText) view.findViewById(R.id.tv_search);
        recyclerViewMatches = (RecyclerView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchInput.renderDara(getString(R.string.search), false);
        searchInput.setDelegate(this, "");
        searchEmpty.setVisibility(View.GONE);
    }

    @Override
    public void valuechange(String tag, String text) {
        if (!TextUtils.isEmpty(text)) {
            searchEmpty.setVisibility(View.VISIBLE);
        } else {
            searchEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void didReturn(String tag) {

    }

    @Override
    public void inputTextFocus(Boolean b, String tag) {

    }

    @Override
    public void onHttpComplete(String response, int idRequest) {

    }

    @Override
    public void onHttpError(String response, int idRequest, int errorCode) {

    }

    protected void loadMatches(){
        if(viewsCreated && !itemsLoaded){
            recyclerViewMatches.setVisibility(View.GONE);
        }
        networkManager
    }
}
