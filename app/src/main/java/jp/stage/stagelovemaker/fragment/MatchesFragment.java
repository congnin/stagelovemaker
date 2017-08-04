package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.views.FormInputText;
import jp.stage.stagelovemaker.views.SearchEmpty;
import jp.stage.stagelovemaker.views.TitleBar;

/**
 * Created by congn on 8/4/2017.
 */

public class MatchesFragment extends BaseFragment implements FormInputText.FormInputTextDelegate {
    public static final String TAG = "MatchesFragment";
    FormInputText searchInput;
    SearchEmpty searchEmpty;

    public static MatchesFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MatchesFragment fragment = new MatchesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        searchEmpty = (SearchEmpty) view.findViewById(R.id.search_empty_chat_view);
        searchInput = (FormInputText) view.findViewById(R.id.tv_search);
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
}
