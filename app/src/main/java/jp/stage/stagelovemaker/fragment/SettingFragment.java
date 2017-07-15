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
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.Button;
import jp.stage.stagelovemaker.views.TitleBar;

/**
 * Created by congn on 7/14/2017.
 */

public class SettingFragment extends BaseFragment implements TitleBar.TitleBarCallback {
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
    SwitchCompat switShowOnTinder;
    RelativeLayout layoutShowMeOnTinder;

    TextView tvNotification;
    TextView tvNewMatches;
    TextView tvMessages;
    TextView tvMessageLikes;
    TextView tvSuperLikes;
    RelativeLayout layoutNotification;

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

        tvAgeRange.setTypeface(Utils.getProximaBold(getContext()));
        tvAge.setTypeface(Utils.getProximaSemiBold(getContext()));
        ((GradientDrawable) layoutAge.getBackground()).setStroke(1, ContextCompat.getColor(getContext(), R.color.gray80));
        ((GradientDrawable) layoutAge.getBackground()).setColor(Color.WHITE);

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
}
