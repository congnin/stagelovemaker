package jp.stage.stagelovemaker.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import at.markushi.ui.CircleButton;
import jp.stage.stagelovemaker.MyApplication;
import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.adapter.UserInfoAdapter;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.model.Avatar;
import jp.stage.stagelovemaker.model.UserInfo;
import jp.stage.stagelovemaker.views.SwipeDeck;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by congn on 7/11/2017.
 */

public class StageFragment extends BaseFragment {
    public static final String TAG = "StageFragment";

    private SwipeDeck cardStack;
    private ArrayList<UserInfo> userInfos = new ArrayList<>();
    private CircleButton btnRefresh, btnClose, btnHeart, btnStar;
    private ProgressBar pgbCard;
    private UserInfoAdapter cardAdapter;
    private CircularImageView ivDetail;

    public static StageFragment newInstance() {
        Bundle args = new Bundle();
        StageFragment fragment = new StageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stage, container, false);
        btnRefresh = (CircleButton) view.findViewById(R.id.circleButtonRefresh);
        btnClose = (CircleButton) view.findViewById(R.id.circleButtonClose);
        btnHeart = (CircleButton) view.findViewById(R.id.circleButtonHeart);
        btnStar = (CircleButton) view.findViewById(R.id.circleButtonStar);
        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);
        pgbCard = (ProgressBar) view.findViewById(R.id.pgbCard);
        pgbCard.setVisibility(View.VISIBLE);
        ivDetail = (CircularImageView) view.findViewById(R.id.iv_detail);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEvents();
        pgbCard.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getListUserInfo();
                cardAdapter = new UserInfoAdapter(getActivity(), userInfos);
                cardStack.setAdapter(cardAdapter);
                cardAdapter.notifyDataSetChanged();
                pgbCard.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private void addEvents() {
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedTop(long stableId) {
                Log.i("CardFragment", "card was swiped left, position in adapter: " + stableId);
                if (cardAdapter.getCount() > 0 && cardStack.getAdapterIndex() > 0) {
                    cardAdapter.remove(0);
                    cardStack.setAdapterIndex(cardStack.getAdapterIndex() - 1);
                }
            }

            @Override
            public void cardSwipedLeft(long stableId) {
                Log.i("CardFragment", "card was swiped left, position in adapter: " + stableId);
                if (cardAdapter.getCount() > 0 && cardStack.getAdapterIndex() > 0) {
                    cardAdapter.remove(0);
                    cardStack.setAdapterIndex(cardStack.getAdapterIndex() - 1);
                }
            }

            @Override
            public void cardSwipedRight(long stableId) {
                if (cardAdapter.getCount() > 0) {
                    Log.i("CardFragment", "card was swiped right, position in adapter: " + stableId);
                    if (cardAdapter.getCount() > 0 && cardStack.getAdapterIndex() > 0) {
                        cardAdapter.remove(0);
                        cardStack.setAdapterIndex(cardStack.getAdapterIndex() - 1);
                    }
                }
            }

            @Override
            public boolean isDragEnabled(long itemId) {
                return true;
            }
        });

        cardStack.setTopImage(R.id.item_swipe_top_indicator);
        cardStack.setLeftImage(R.id.item_swipe_left_indicator);
        cardStack.setRightImage(R.id.item_swipe_right_indicator);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadFragment();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardAdapter.getCount() > 0 && cardStack.getAdapterIndex() > 0) {
                    cardStack.swipeTopCardLeft(200);
                    cardAdapter.remove(0);
                    cardStack.setAdapterIndex(cardStack.getAdapterIndex() - 1);
                }
            }
        });

        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardAdapter.getCount() > 0 && cardStack.getAdapterIndex() > 0) {
                    cardStack.swipeTopCardRight(200);
                    cardAdapter.remove(0);
                    cardStack.setAdapterIndex(cardStack.getAdapterIndex() - 1);
                }
            }
        });

        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardAdapter.getCount() > 0 && cardStack.getAdapterIndex() > 0) {
                    cardStack.swipeTopCardTop(200);//200 time
                    cardAdapter.remove(0);
                    cardStack.setAdapterIndex(cardStack.getAdapterIndex() - 1);
                }
            }
        });

        cardStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = cardStack.getAdapterIndex();
                for (UserInfo userInfo : userInfos) {
                    Log.e(TAG, "onClick: " + userInfo.getFirstName());
                }
            }
        });

        ivDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = cardStack.getAdapterIndex();
                //Toast.makeText(getActivity(), "" + index, Toast.LENGTH_SHORT).show();
                if (index > 0) {
                    ArrayList<UserInfo> revert = userInfos.clone();
                    DetailProfileFragment detailProfileFragment = DetailProfileFragment.newInstance(userInfos.get(index - 1));
                    replace(detailProfileFragment, DetailProfileFragment.TAG, true, true);
                }

            }
        });
    }

    private void getListUserInfo() {
        if (userInfos == null) {
            userInfos = new ArrayList<>();
        }
        if (!userInfos.isEmpty()) {
            userInfos.clear();
        }

        UserInfo user1 = new UserInfo();
        //user1
        user1.setId(1);
        user1.setFirstName("Ngân");
        user1.setBirthday("22");
        user1.setSchool("HUTECH - Đại học Công nghệ Tp.HCM");
        user1.setCurrentWork("2 miles away");
        user1.setAbout("I like men, not boys");
        user1.setInstagram_username("ngandien");

        ArrayList<String> lsAvatar1 = new ArrayList<>();
        lsAvatar1.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/20638232_1054361224701104_1390295174461240428_n.jpg?oh=d76e0522256232b287d2393f8576466d&oe=5A2F22AB");
        lsAvatar1.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/20604732_1054362008034359_4748639644228319247_n.jpg?oh=6e2b61b85e218874eb51da57ad43ecbc&oe=5A2B89D2");
        lsAvatar1.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/15181144_899445113526050_9018492507123819802_n.jpg?oh=9d87a0f74a2399a2ef6ee5872c03fe16&oe=59FAF455");
        lsAvatar1.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/13495097_809484475855448_8592598439974406528_n.jpg?oh=e6f4d2daf0a10d979a948bc3878dff45&oe=5A325EC8");
        lsAvatar1.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/15135887_894052094065352_447000892431337308_n.jpg?oh=20831b7a43d98c53d8617544ea330d5d&oe=59EE325C");
        user1.setAvatars(lsAvatar1);
        userInfos.add(user1);

        //user2
        UserInfo user2 = new UserInfo();
        user2.setId(2);
        user2.setFirstName("Diễm");
        user2.setBirthday("27");
        user2.setSchool("Cao đẳng VHNT&DL Sài Gòn");
        user2.setCurrentWork("22 miles away");
        user2.setAbout("I like men, not boys");
        user2.setInstagram_username("diem");

        ArrayList<String> lsAvatar2 = new ArrayList<>();
        lsAvatar2.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/18519513_1560958607279279_3767255128205453509_n.jpg?oh=6100380b5c8da74376e93a6e515b18de&oe=5A31F83B");
        lsAvatar2.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/15590050_1348591628515979_5420563723546649085_n.jpg?oh=bb6f627b33b10e2db99c033f84d2aa7f&oe=59FC5043");
        lsAvatar2.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/15267779_1328452013863274_7616067246508678003_n.jpg?oh=b063f583f1c92242d336b8d4a6095d24&oe=59FEF054");
        lsAvatar2.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/14650236_1273262296048913_3172878498337134028_n.jpg?oh=dfa0ae4897d24cb14e2ec9caf66e1704&oe=59EBA61B");
        lsAvatar2.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/14079920_1207120922663051_1642451529921048407_n.jpg?oh=8d821d6285958619a5267ea2e41abd07&oe=5A2A7103");
        user2.setAvatars(lsAvatar2);
        userInfos.add(user2);


        //user3
        UserInfo user3 = new UserInfo();
        user3.setId(3);
        user3.setFirstName("Nhung");
        user3.setBirthday("27");
        user3.setSchool("Đại Học Quang Trung");
        user3.setCurrentWork("50 miles away");
        user3.setAbout("I like men, not boys");
        user3.setInstagram_username("nhung");

        ArrayList<String> lsAvatar3 = new ArrayList<>();
        lsAvatar3.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/13592450_816809195117256_2745206307939780766_n.jpg?oh=c27e54dd8f8474d281f6883e04d98ef1&oe=5A29F734");
        lsAvatar3.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/16387417_934913219973519_4492301815180262488_n.jpg?oh=4d3385bd3b8125ce1ac54560018eae4f&oe=59ED84AA");
        lsAvatar3.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/12800238_754675144663995_251013623987045466_n.jpg?oh=035849bc322eb7a32c55d44295d46f35&oe=59F5A37B");
        lsAvatar3.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/10307411_733501806781329_272789594752425752_n.jpg?oh=1175a9c29df324b4e67de5581e2dafc4&oe=5A2F4E74");
        lsAvatar3.add("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/398252_117627541702095_1453024477_n.jpg?oh=d4b9b2087c7e640e8f6d4a20729526b6&oe=59FC9B7F");
        user3.setAvatars(lsAvatar3);
        userInfos.add(user3);
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}
