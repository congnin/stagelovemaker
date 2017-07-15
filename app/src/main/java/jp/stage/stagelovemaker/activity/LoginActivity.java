package jp.stage.stagelovemaker.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidpagecontrol.PageControl;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.adapter.IntroPagerAdapter;
import jp.stage.stagelovemaker.base.CommonActivity;

/**
 * Created by congn on 7/11/2017.
 */

public class LoginActivity extends CommonActivity implements View.OnClickListener {
    ViewPager viewPager;
    IntroPagerAdapter adapter;
    PageControl pageControl;
    Button btLoginFacebook;
    Button btLoginByPhone;
    TextView tvAuthPolicy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pageControl = (PageControl) findViewById(R.id.page_control);
        btLoginFacebook = (Button) findViewById(R.id.bt_facebook);
        btLoginByPhone = (Button) findViewById(R.id.bt_login_phone_number);
        tvAuthPolicy = (TextView) findViewById(R.id.tv_authentication_policy);

        adapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        pageControl.setViewPager(viewPager);
        pageControl.setPosition(viewPager.getCurrentItem());

        Typeface semiBold = Typeface.createFromAsset(getAssets(), "fonts/proximanovasoft-semibold.otf");
        btLoginFacebook.setTypeface(semiBold);
        Typeface regularType = Typeface.createFromAsset(getAssets(), "fonts/proximanovasoft-regular.otf");
        tvAuthPolicy.setTypeface(regularType);

        String tos = getString(R.string.tos);
        String policy = getString(R.string.privacy_policy);
        String des = String.format(getString(R.string.policy_authentication), tos, policy);
        SpannableString spannableString = new SpannableString(des);
        ClickableSpan clickableTos = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        ClickableSpan clickablePolicy = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        int start = des.indexOf(tos);
        spannableString.setSpan(clickableTos, start, start + tos.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.darkgrey)), start, start + tos.length(), 0);

        start = des.indexOf(policy);

        spannableString.setSpan(clickablePolicy, start, start + policy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.darkgrey)), start, start + policy.length(), 0);

        tvAuthPolicy.setText(spannableString);

        tvAuthPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        btLoginFacebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        startNewActivity(MainActivity.class, bundle);
        finish();
    }
}
