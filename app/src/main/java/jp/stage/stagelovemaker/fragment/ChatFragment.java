package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;

import jp.stage.stagelovemaker.base.BaseFragment;

/**
 * Created by congn on 7/11/2017.
 */

public class ChatFragment extends BaseFragment {
    public static ChatFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
