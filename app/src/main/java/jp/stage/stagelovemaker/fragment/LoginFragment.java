package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.activity.MainActivity;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.LoginActionBar;

/**
 * Created by congn on 7/28/2017.
 */

public class LoginFragment extends BaseFragment implements LoginActionBar.LoginActionBarDelegate{
    public static final String TAG = "LoginFragment";
    LoginActionBar actionBar;

    TextView stageTitleTv, userNameTv, passwordTv, forgotPassTv, registerTv;

    boolean bFlagButtonNext = false;
    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        actionBar = (LoginActionBar) view.findViewById(R.id.action_bar);
        stageTitleTv = (TextView)view.findViewById(R.id.stage_title_tv);
        userNameTv = (TextView)view.findViewById(R.id.username_tv);
        passwordTv = (TextView)view.findViewById(R.id.password_tv);
        forgotPassTv = (TextView)view.findViewById(R.id.forgotPass_tv);
        registerTv = (TextView)view.findViewById(R.id.signUp_tv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionBar.setDelegate(getTag(), this);
        actionBar.setTitle(getString(R.string.log_in));
        actionBar.setTextNextColor(ContextCompat.getColor(getContext(), R.color.color_dim_text));

        stageTitleTv.setTypeface(Utils.getProximaBold(getContext()));
        userNameTv.setTypeface(Utils.getProximaBold(getContext()));
        passwordTv.setTypeface(Utils.getProximaBold(getContext()));
        forgotPassTv.setTypeface(Utils.getProximaBold(getContext()));
        registerTv.setTypeface(Utils.getProximaBold(getContext()));
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
