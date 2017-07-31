package jp.stage.stagelovemaker.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.Button;
import jp.stage.stagelovemaker.views.TitleBar;

/**
 * Created by congn on 7/14/2017.
 */

public class SettingFragment extends BaseFragment implements TitleBar.TitleBarCallback,
        SeekBar.OnSeekBarChangeListener, RangeBar.OnRangeBarChangeListener, View.OnClickListener {
    public static final String TAG = "SettingFragment";
    TitleBar titleBar;
    Button btGetPlus;
    Button btGetMoreBoosts;
    Button btGetSuperLikes;

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

    int fromAge;
    int toAge;
    int radius;
    Boolean isMile;
    String valueDistance;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        btGetPlus = (Button) view.findViewById(R.id.bt_get_tinder_plus);
        btGetMoreBoosts = (Button) view.findViewById(R.id.bt_get_more_boosts);
        btGetSuperLikes = (Button) view.findViewById(R.id.bt_get_super_likes);
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleBar.setTitle(getString(R.string.settings_no_all_cap));
        titleBar.enableBackButton();
        titleBar.setCallback(this);
        btGetPlus.setText(getString(R.string.get_tinder_plus), ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((GradientDrawable) btGetPlus.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) btGetPlus.getBackground()).setColor(Color.WHITE);

        btGetMoreBoosts.setContent(R.drawable.ic_boost_empty_chat, getString(R.string.get_more_boost),
                ContextCompat.getColor(getContext(), R.color.moderate_violet));
        ((GradientDrawable) btGetMoreBoosts.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) btGetMoreBoosts.getBackground()).setColor(Color.WHITE);

        btGetSuperLikes.setContent(R.drawable.superlike_gradient, getString(R.string.get_super_likes),
                ContextCompat.getColor(getContext(), R.color.light_blue));
        ((GradientDrawable) btGetSuperLikes.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) btGetSuperLikes.getBackground()).setColor(Color.WHITE);

        tvShowMe.setTypeface(Utils.getProximaBold(getContext()));
        tvMen.setTypeface(Utils.getProximaSemiBold(getContext()));
        tvWomen.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutShowMe.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShowMe.getBackground()).setColor(Color.WHITE);

        tvMaxDistance.setTypeface(Utils.getProximaBold(getContext()));
        tvDistance.setTypeface(Utils.getProximaBold(getContext()));
        ((GradientDrawable) layoutDistance.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutDistance.getBackground()).setColor(Color.WHITE);
        seekBarDistance.setOnSeekBarChangeListener(this);

        tvAgeRange.setTypeface(Utils.getProximaBold(getContext()));
        tvAge.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutAge.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutAge.getBackground()).setColor(Color.WHITE);
        rangeAge.setOnRangeBarChangeListener(this);

        tvShowMeOnTinder.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutShowMeOnTinder.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShowMeOnTinder.getBackground()).setColor(Color.WHITE);

        tvNotification.setTypeface(Utils.getProximaBold(getContext()));
        tvNewMatches.setTypeface(Utils.getProximaSemiBold(getContext()));
        tvMessages.setTypeface(Utils.getProximaSemiBold(getContext()));
        tvMessageLikes.setTypeface(Utils.getProximaSemiBold(getContext()));
        tvSuperLikes.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutNotification.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutNotification.getBackground()).setColor(Color.WHITE);

        tvShowDistanceIn.setTypeface(Utils.getProximaBold(getContext()));
        tvChooseDistanceUnit.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutShowDistance.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShowDistance.getBackground()).setColor(Color.WHITE);
//        ((GradientDrawable) btKm.getBackground()).setStroke(1, Color.TRANSPARENT);
//        ((GradientDrawable) btKm.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
//        btKm.setTextColor(Color.WHITE);
        chooseDistanceUnit(btKm, btMi);
        btKm.setTypeface(Utils.getProximaSemiBold(getContext()));
        btMi.setTypeface(Utils.getProximaSemiBold(getContext()));

        tvContactUs.setTypeface(Utils.getProximaBold(getContext()));
        tvHelpSupport.setTypeface(Utils.getProximaBold(getContext()));
        ((GradientDrawable) layoutHelpSupport.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutHelpSupport.getBackground()).setColor(Color.WHITE);

        tvShareApp.setTypeface(Utils.getProximaBold(getContext()));
        ((GradientDrawable) layoutShareApp.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutShareApp.getBackground()).setColor(Color.WHITE);

        tvLegal.setTypeface(Utils.getProximaBold(getContext()));
        tvLicenses.setTypeface(Utils.getProximaSemiBold(getContext()));
        tvPrivacy.setTypeface(Utils.getProximaSemiBold(getContext()));
        tvTerms.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutLegal.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutLegal.getBackground()).setColor(Color.WHITE);

        tvLogout.setTypeface(Utils.getProximaBold(getContext()));
        ((GradientDrawable) layoutLogout.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutLogout.getBackground()).setColor(Color.WHITE);

        valueDistance = getString(R.string.km);
        seekBarDistance.setMax(160);
        isMile = false;
        rangeAge.setTickCount(Constants.MAX_AGE - Constants.MIN_AGE + 1);
        tvChooseDistanceUnit.setText(getString(R.string.km));

        btMi.setOnClickListener(this);
        btKm.setOnClickListener(this);
    }

    @Override
    public void onTitleBarClicked() {

    }

    @Override
    public void onRightButtonClicked() {

    }

    @Override
    public void onBackButtonClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void onMiddleButtonClicked() {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekBarDistance && isMile != null) {
            String more = "";
            if (isMile == true) {
                radius = progress;
                if (progress == 100) {
                    more = "+";
                }
            } else {
                radius = (int) (progress * 0.625);
                if (progress == 160) {
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
                break;
            case R.id.bt_mi:
                chooseDistanceUnit(btMi, btKm);
                isMile = true;
                tvChooseDistanceUnit.setText(getString(R.string.mi));
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
}
