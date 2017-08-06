package jp.stage.stagelovemaker.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.utils.AlertDialog;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.GPSTracker;
import jp.stage.stagelovemaker.utils.Utils;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by congn on 8/4/2017.
 */

public class SearchFragment extends BaseFragment implements AlertDialog.AlertDialogDelegate,
        GPSTracker.GPSTrackerDelegate {
    public static final String TAG = "SearchFragment";

    PulsatorLayout pulsatorLayout;
    TextView tvDescription;
    FloatingActionButton btRewind;
    FloatingActionButton btNope;
    FloatingActionButton btBoost;
    FloatingActionButton btLike;
    FloatingActionButton btSuperLike;
    SearchFragmentCallback delegate;
    GPSTracker gps;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        pulsatorLayout = (PulsatorLayout) view.findViewById(R.id.pulsator);
        tvDescription = (TextView) view.findViewById(R.id.des_search_txt);
        btRewind = (FloatingActionButton) view.findViewById(R.id.bt_rewind);
        btNope = (FloatingActionButton) view.findViewById(R.id.bt_pass);
        btBoost = (FloatingActionButton) view.findViewById(R.id.bt_boost);
        btLike = (FloatingActionButton) view.findViewById(R.id.bt_like);
        btSuperLike = (FloatingActionButton) view.findViewById(R.id.bt_superlike);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pulsatorLayout.start();
        Typeface mediumType = Typeface.createFromAsset(getActivity().getAssets(), "fonts/proximanovasoft-bold.otf");
        tvDescription.setTypeface(mediumType);
        btRewind.setEnabled(false);
        btNope.setEnabled(false);
        btBoost.setEnabled(false);
        btLike.setEnabled(false);
        btSuperLike.setEnabled(false);

        getLocation();
    }

    public void getLocation() {
//        if (Utils.getLocation(getActivity()) == null) {
//            if (gps == null) {
//                gps = new GPSTracker(getActivity());
//                gps.setDelegate(this);
//            }
//            gps.getLocation();
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (getActivity() != null && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (gps != null && gps.getLocation() == null) {
//                                    tvDescription.setText(R.string.des_deny_location);
//                                    //accessButton.setVisibility(View.VISIBLE);
//                                }
//                            }
//                        });
//                    }
//                }
//            }, 120000);
//            if (gps.canGetLocation()) {
//                double latitude = gps.getLatitude();
//                double longitude = gps.getLongitude();
//                Utils.setLocation(getActivity(), latitude, longitude);
//                showAnimationSearch(true);
//
//            } else {
//                if (getActivity() != null) {
//
//                }
//                if (gps.getTypeDenyLocation() == 0) {
//                    if (getActivity() != null) {
//
//                    }
//                    AlertDialog dialog = new AlertDialog(getContext(), Utils.fromHtml(getString(R.string.request_access_location)), R.mipmap.icon_location);
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.setDelegate(SearchFragment.this, Constants.GET_LOCATION);
//                    dialog.show();
//                } else if (gps.getTypeDenyLocation() == 2) {
//                    showAnimationSearch(false);
//                }
//            }
//        }
        showAnimationSearch(true);
    }

    public void showAnimationSearch(Boolean bsearch) {
        tvDescription.setText(R.string.search_people_match_you);
        if (delegate != null && bsearch) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    delegate.onSearchFinished();
                }
            }, 3000);
        }
        pulsatorLayout.setVisibility(View.VISIBLE);
        pulsatorLayout.start();
    }

    public void setCallback(SearchFragmentCallback fragment) {
        delegate = fragment;
    }

    @Override
    public void clickAllow(Boolean bAllow, String type) {
        if (bAllow) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getActivity().startActivity(intent);
        }
        tvDescription.setText(R.string.des_deny_location);
    }

    @Override
    public void setYourLocation(Location location) {
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        Utils.setLocation(getActivity(), latitude, longitude);
    }

    public interface SearchFragmentCallback {
        void onSearchFinished();
    }
}
