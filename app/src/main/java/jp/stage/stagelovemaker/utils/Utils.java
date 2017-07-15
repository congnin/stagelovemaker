package jp.stage.stagelovemaker.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.StringTokenizer;

/**
 * Created by congn on 7/11/2017.
 */

public final class Utils {
    private Utils() {
        //no instance
    }

    static int iWidth = 0;
    static int iHeight = 0;

    public static int getiWidthScreen(Activity activity) {
        if (iWidth == 0) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            iWidth = (displaymetrics.widthPixels);
        }
        return iWidth;
    }

    public static int getiHeightScreen(Activity activity) {
        if (iHeight == 0) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            iHeight = (displaymetrics.heightPixels);
        }
        return iHeight;
    }

    public static void hideSoftKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    public static String capitalize(String text) {
        String result = "";
        StringTokenizer tokens = new StringTokenizer(text, " ");
        while (tokens.hasMoreTokens()) {
            String temp = tokens.nextToken();
            result = result + temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase() + " ";
        }
        return result.trim();
    }

    public static float dip2px(Context context, float dpValue) {
        if (context != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            return dpValue * scale;
        }
        return 0;
    }

    public static Typeface getFont(Context context, String fontUrl) {
        return Typeface.createFromAsset(context.getAssets(), fontUrl);
    }

    public static Typeface getMissionFont(Context context) {
        return getFont(context, "fonts/Mission-Script.otf");
    }

    public static Typeface getOpenSanBold(Context context) {
        return getFont(context, "fonts/OpenSans-Bold.otf");
    }

    public static Typeface getProximaBold(Context context) {
        return getFont(context, "fonts/proximanovasoft-bold.otf");
    }

    public static Typeface getProximaMedium(Context context) {
        return getFont(context, "fonts/proximanovasoft-medium.otf");
    }

    public static Typeface getProximaRegular(Context context) {
        return getFont(context, "fonts/proximanovasoft-regular.otf");
    }

    public static Typeface getProximaSemiBold(Context context) {
        return getFont(context, "fonts/proximanovasoft-semibold.otf");
    }
}
