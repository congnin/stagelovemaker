package jp.stage.stagelovemaker.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by congn on 7/11/2017.
 */

public class StageFragment extends BaseFragment {
    PulsatorLayout pulsatorLayout;
    TextView tvDescription;
    FloatingActionButton btRewind;
    FloatingActionButton btNope;
    FloatingActionButton btBoost;
    FloatingActionButton btLike;
    FloatingActionButton btSuperLike;

    public static StageFragment newInstance() {

        Bundle args = new Bundle();

        StageFragment fragment = new StageFragment();
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
    }
}
