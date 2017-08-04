package jp.stage.stagelovemaker.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.adapter.FeaturesPagerAdapter;
import jp.stage.stagelovemaker.base.CommonActivity;
import jp.stage.stagelovemaker.views.MainTabBar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends CommonActivity implements MainTabBar.MainTabBarCallback,
        ViewPager.OnPageChangeListener {
    MainTabBar mainTabBar;
    ViewPager viewPager;
    FeaturesPagerAdapter featuresPagerAdapter;
    int indexTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTabBar = (MainTabBar) findViewById(R.id.main_tabbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        featuresPagerAdapter = new FeaturesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(featuresPagerAdapter);
        mainTabBar.setCallback(this);
        mainTabBar.changeTab(MainTabBar.TAB_STAGE);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onTabChanged(int index) {
        viewPager.setCurrentItem(index - 1, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mainTabBar.changeTab(position + 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
