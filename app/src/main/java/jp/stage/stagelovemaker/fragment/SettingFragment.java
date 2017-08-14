package jp.stage.stagelovemaker.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;

import jp.stage.stagelovemaker.MyApplication;
import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.activity.LoginActivity;
import jp.stage.stagelovemaker.activity.MainActivity;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.dialog.QuestionDialog;
import jp.stage.stagelovemaker.model.SettingModel;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.Button;
import jp.stage.stagelovemaker.views.LoginActionBar;
import jp.stage.stagelovemaker.views.TitleBar;

/**
 * Created by congn on 7/14/2017.
 */

public class SettingFragment extends BaseFragment implements TitleBar.TitleBarCallback,
        SeekBar.OnSeekBarChangeListener, RangeBar.OnRangeBarChangeListener, View.OnClickListener {
    public static final String TAG = "SettingFragment";
    TitleBar titleBar;

    TextView tvShowMe;
    TextView tvMen;
    TextView tvWomen;
    RelativeLayout layoutShowMe;

    TextView tvMaxDistance;
    TextView tvDistance;
    SeekBar seekBarDistance;
    RelativeLayout layoutDistance;

    TextView tvAgeRange;
    TextView tvAge;
    RangeBar rangeAge;
    RelativeLayout layoutAge;

    TextView tvShowMeOnTinder;
    SwitchCompat switchShowOnTinder;
    RelativeLayout layoutShowMeOnTinder;

    TextView tvNotification;
    TextView tvNewMatches;
    TextView tvMessages;
    TextView tvMessageLikes;
    TextView tvSuperLikes;
    RelativeLayout layoutNotification;

    TextView tvShowDistanceIn;
    TextView tvChooseDistanceUnit;
    android.widget.Button btKm;
    android.widget.Button btMi;
    RelativeLayout layoutShowDistance;

    TextView tvContactUs;
    TextView tvHelpSupport;
    RelativeLayout layoutHelpSupport;

    TextView tvShareApp;
    RelativeLayout layoutShareApp;

    TextView tvLegal;
    TextView tvLicenses;
    TextView tvPrivacy;
    TextView tvTerms;
    RelativeLayout layoutLegal;

    TextView tvLogout;
    RelativeLayout layoutLogout;

    SwitchCompat switchMan, switchWoman;

    TextView tvDeleteAccount;
    RelativeLayout layoutDeleteAccount;

    int fromAge;
    int toAge;
    int radius;
    Boolean isMile;
    String valueDistance;
    SettingFragmentCallback callback;

    public static SettingFragment newInstance(SettingModel settingModel) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_DATA, settingModel);
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        titleBar = (TitleBar) view.findViewById(R.id.titleBar);
        tvShowMe = (TextView) view.findViewById(R.id.tv_show_me);
        tvMen = (TextView) view.findViewById(R.id.tv_men);
        tvWomen = (TextView) view.findViewById(R.id.tv_women);
        layoutShowMe = (RelativeLayout) view.findViewById(R.id.layout_show_me);

        tvMaxDistance = (TextView) view.findViewById(R.id.tv_maximum_distance);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        seekBarDistance = (SeekBar) view.findViewById(R.id.seekbar_distance);
        layoutDistance = (RelativeLayout) view.findViewById(R.id.layout_distance);

        tvAgeRange = (TextView) view.findViewById(R.id.tv_age_range);
        tvAge = (TextView) view.findViewById(R.id.tv_age);
        rangeAge = (RangeBar) view.findViewById(R.id.rangebar);
        layoutAge = (RelativeLayout) view.findViewById(R.id.layout_age);

        tvShowMeOnTinder = (TextView) view.findViewById(R.id.tv_show_me_on_Tinder);
        layoutShowMeOnTinder = (RelativeLayout) view.findViewById(R.id.layout_show_me_on_tinder);

        tvNotification = (TextView) view.findViewById(R.id.tv_notifications);
        tvNewMatches = (TextView) view.findViewById(R.id.tv_new_matches);
        tvMessages = (TextView) view.findViewById(R.id.tv_messages);
        tvMessageLikes = (TextView) view.findViewById(R.id.tv_message_likes);
        tvSuperLikes = (TextView) view.findViewById(R.id.tv_super_likes);
        layoutNotification = (RelativeLayout) view.findViewById(R.id.layout_notification);

        tvShowDistanceIn = (TextView) view.findViewById(R.id.tv_show_distance_in);
        tvChooseDistanceUnit = (TextView) view.findViewById(R.id.tv_choose_distance_unit);
        btKm = (android.widget.Button) view.findViewById(R.id.bt_km);
        btMi = (android.widget.Button) view.findViewById(R.id.bt_mi);
        layoutShowDistance = (RelativeLayout) view.findViewById(R.id.layout_show_distance);

        tvContactUs = (TextView) view.findViewById(R.id.tv_contact_us);
        tvHelpSupport = (TextView) view.findViewById(R.id.tv_help_support);
        layoutHelpSupport = (RelativeLayout) view.findViewById(R.id.layout_help_support);

        tvShareApp = (TextView) view.findViewById(R.id.tv_share_app);
        layoutShareApp = (RelativeLayout) view.findViewById(R.id.layout_share_app);

        tvLegal = (TextView) view.findViewById(R.id.tv_legal);
        tvLicenses = (TextView) view.findViewById(R.id.tv_licenses);
        tvPrivacy = (TextView) view.findViewById(R.id.tv_privacy);
        tvTerms = (TextView) view.findViewById(R.id.tv_tos);
        layoutLegal = (RelativeLayout) view.findViewById(R.id.layout_legal);

        tvLogout = (TextView) view.findViewById(R.id.tv_logout);
        layoutLogout = (RelativeLayout) view.findViewById(R.id.layout_logout);
        tvDeleteAccount = (TextView) view.findViewById(R.id.tv_delete);
        layoutDeleteAccount = (RelativeLayout) view.findViewById(R.id.layout_delete);

        switchMan = (SwitchCompat) view.findViewById(R.id.switch_men);
        switchWoman = (SwitchCompat) view.findViewById(R.id.switch_women);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleBar.setTitle(getString(R.string.settings_no_all_cap));
        titleBar.enableBackButton();
        titleBar.setTitleRight(getString(R.string.done));
        titleBar.setCallback(this);

        ((GradientDrawable) layoutShowMe.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShowMe.getBackground()).setColor(Color.WHITE);

        ((GradientDrawable) layoutDistance.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutDistance.getBackground()).setColor(Color.WHITE);
        seekBarDistance.setOnSeekBarChangeListener(this);

        ((GradientDrawable) layoutAge.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutAge.getBackground()).setColor(Color.WHITE);
        rangeAge.setOnRangeBarChangeListener(this);

        tvShowMeOnTinder.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutShowMeOnTinder.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShowMeOnTinder.getBackground()).setColor(Color.WHITE);

        ((GradientDrawable) layoutNotification.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutNotification.getBackground()).setColor(Color.WHITE);

        ((GradientDrawable) layoutShowDistance.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShowDistance.getBackground()).setColor(Color.WHITE);

        chooseDistanceUnit(btKm, btMi);

        ((GradientDrawable) layoutHelpSupport.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutHelpSupport.getBackground()).setColor(Color.WHITE);

        ((GradientDrawable) layoutShareApp.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShareApp.getBackground()).setColor(Color.WHITE);

        ((GradientDrawable) layoutLegal.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutLegal.getBackground()).setColor(Color.WHITE);

        ((GradientDrawable) layoutLogout.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutLogout.getBackground()).setColor(Color.WHITE);

        ((GradientDrawable) layoutDeleteAccount.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutDeleteAccount.getBackground()).setColor(Color.WHITE);

        valueDistance = getString(R.string.km);
        seekBarDistance.setMax(100);
        isMile = false;
        rangeAge.setTickCount(Constants.MAX_AGE - Constants.MIN_AGE + 1);
        tvChooseDistanceUnit.setText(getString(R.string.km));

        btMi.setOnClickListener(this);
        btKm.setOnClickListener(this);

        switchMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!switchMan.isChecked()) {
                    if (!switchWoman.isChecked()) {
                        switchWoman.setChecked(true);
                    }
                }
            }
        });
        switchWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!switchWoman.isChecked()) {
                    if (!switchMan.isChecked()) {
                        switchMan.setChecked(true);
                    }
                }
            }
        });

        layoutLogout.setOnClickListener(this);
    }

    @Override
    public void onTitleBarClicked() {

    }

    @Override
    public void onRightButtonClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void onBackButtonClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void onMiddleButtonClicked() {

    }

    private void calculateDistanceByUnit() {
        String more = "";
        int progress;
        if (isMile) {
            progress = radius;
            if (progress == 100 / 1.6) {
                more = "+";
            }
            valueDistance = getString(R.string.mi);
        } else {
            progress = (int) (radius * 1.6);
            if (progress == 100) {
                more = "+";
            }
            valueDistance = getString(R.string.km);
        }
        tvDistance.setText(progress + valueDistance + more);
        seekBarDistance.setProgress((int) progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekBarDistance && isMile != null) {
            String more = "";
            if (isMile) {
                radius = progress;
                if (progress == 100 / 1.6) {
                    more = "+";
                }
            } else {
                radius = (int) (progress * 0.625);
                if (progress == 100) {
                    more = "+";
                }
            }
            tvDistance.setText(progress + valueDistance + more);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
        fromAge = i + Constants.MIN_AGE;
        toAge = i1 + Constants.MIN_AGE;
        tvAge.setText((fromAge) + "-" + (toAge));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_km:
                chooseDistanceUnit(btKm, btMi);
                isMile = false;
                tvChooseDistanceUnit.setText(getString(R.string.km));
                calculateDistanceByUnit();
                break;
            case R.id.bt_mi:
                chooseDistanceUnit(btMi, btKm);
                isMile = true;
                tvChooseDistanceUnit.setText(getString(R.string.mi));
                calculateDistanceByUnit();
                break;
            case R.id.layout_logout:
                QuestionDialog dialog = new QuestionDialog(getActivity(), Utils.capitalize(getString(R.string.log_out)),
                        getString(R.string.question_logout));
                dialog.setColorTitle(Color.RED);
                dialog.setTitleButtonOK(getString(R.string.logout).toUpperCase());
                dialog.setDelegate(questionLogout, "");
                dialog.show();
                break;
        }
    }


    private void chooseDistanceUnit(android.widget.Button button1, android.widget.Button button2) {
        ((GradientDrawable) button1.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((GradientDrawable) button1.getBackground()).setStroke(0, Color.TRANSPARENT);
        button1.setTextColor(Color.WHITE);

        ((GradientDrawable) button2.getBackground()).setColor(Color.TRANSPARENT);
        ((GradientDrawable) button2.getBackground()).setStroke(0, Color.TRANSPARENT);
        button2.setTextColor(ContextCompat.getColor(getContext(), R.color.gray37));
    }

    public QuestionDialog.QuestionRequestDialogDelegate questionLogout = new QuestionDialog.QuestionRequestDialogDelegate() {
        @Override
        public void clickAllow(Boolean bAllow, String type) {
            if (bAllow) {
                Toast.makeText(getContext(), getString(R.string.you_are_loggout), Toast.LENGTH_LONG).show();
                MyApplication app = Utils.getApplication(getActivity());
                if (app != null) {
                    app.setAccessToken("", getActivity());
                    app.setLocation(null);
                }
                startNewActivity(LoginActivity.class, null);
                ActivityCompat.finishAffinity(getActivity());
            }
        }
    };

    public void setCallback(SettingFragmentCallback callback) {
        this.callback = callback;
    }

    public interface SettingFragmentCallback {
        void onSettingChanged();
    }
}
