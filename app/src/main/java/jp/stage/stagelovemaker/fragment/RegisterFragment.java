package jp.stage.stagelovemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import jp.stage.stagelovemaker.views.LoginActionBar.LoginActionBarDelegate;

/**
 * Created by congn on 7/28/2017.
 *
 */

public class RegisterFragment extends BaseFragment implements LoginActionBarDelegate {
    public static final String TAG = "RegisterFragment";
    LoginActionBar actionBar;
    boolean bFlagButtonNext = false;

    TextView nameTv, userNameTv, passTv, repeatPassTv, emailTv, maleBtn, femaleBtn, birthdayTv, genderTv;

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

        nameTv = (TextView)view.findViewById(R.id.registerName_tv);
        userNameTv = (TextView)view.findViewById(R.id.regiterUserName_tv);
        passTv = (TextView)view.findViewById(R.id.registerPass_tv);
        repeatPassTv = (TextView)view.findViewById(R.id.registerRepeatPass_Tv);
        emailTv = (TextView)view.findViewById(R.id.registerEmail_tv);
        maleBtn = (TextView)view.findViewById(R.id.male_radioBtn);
        femaleBtn = (TextView)view.findViewById(R.id.feMale_radioBtn);
        birthdayTv = (TextView)view.findViewById(R.id.registerBirthday_tv);
        genderTv = (TextView)view.findViewById(R.id.registerGender_tv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionBar.setDelegate(getTag(), this);
        actionBar.setTitle(getString(R.string.register));
        actionBar.setTextNextColor(ContextCompat.getColor(getContext(), R.color.color_dim_text));

        //set Ui
        nameTv.setTypeface(Utils.getProximaBold(getContext()));
        userNameTv.setTypeface(Utils.getProximaBold(getContext()));
        passTv.setTypeface(Utils.getProximaBold(getContext()));
        repeatPassTv.setTypeface(Utils.getProximaBold(getContext()));
        emailTv.setTypeface(Utils.getProximaBold(getContext()));
        maleBtn.setTypeface(Utils.getProximaBold(getContext()));
        femaleBtn.setTypeface(Utils.getProximaBold(getContext()));
        birthdayTv.setTypeface(Utils.getProximaBold(getContext()));
        genderTv.setTypeface(Utils.getProximaBold(getContext()));
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
