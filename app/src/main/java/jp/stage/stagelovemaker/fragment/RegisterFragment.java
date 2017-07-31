package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.activity.MainActivity;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.views.LoginActionBar;
import jp.stage.stagelovemaker.views.LoginActionBar.LoginActionBarDelegate;

/**
 * Created by congn on 7/28/2017.
 */

public class RegisterFragment extends BaseFragment implements LoginActionBarDelegate {
    public static final String TAG = "RegisterFragment";
    LoginActionBar actionBar;
    boolean bFlagButtonNext = false;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        actionBar = (LoginActionBar) view.findViewById(R.id.action_bar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionBar.setDelegate(getTag(), this);
        actionBar.setTitle(getString(R.string.register));
        actionBar.setTextNextColor(ContextCompat.getColor(getContext(), R.color.color_dim_text));
    }

    @Override
    public void didBack() {
        getActivity().onBackPressed();
    }

    @Override
    public void didNext() {
        startNewActivity(MainActivity.class, null);
        ActivityCompat.finishAffinity(getActivity());
    }
}
