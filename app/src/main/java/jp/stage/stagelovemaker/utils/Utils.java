package jp.stage.stagelovemaker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import jp.stage.stagelovemaker.MyApplication;

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

    public static File getOutputMediaFile(Context context) {
        String imageFileName = "avatar";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
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

    public static String formatDateLocal(Context context, Date date) {
        if (context != null && date != null) {
            DateFormat outFormat = android.text.format.DateFormat.getDateFormat(context);
            return outFormat.format(date);
        }
        return "";
    }

    public static Spanned fromHtml(String source) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            // noinspection deprecation
            return Html.fromHtml(source);
        }
        return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void writeSharedPref(Activity activity, String key, String value) {
        if (activity != null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public static void clearSharedPref(Activity activity, String key) {
        if (activity != null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
            sharedPref.edit().remove(key).commit();
        }
    }


    public static String readSharedPref(String key, Activity activity) {
        if (activity != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            return preferences.getString(key, null);
        }
        return "";
    }

    public static void writeIntSharedPref(Activity activity, String key, int value) {
        if (activity != null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public static int readIntSharedPref(String key, Activity activity) {
        if (activity != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            return preferences.getInt(key, 0);
        }
        return 0;
    }

    public static void writeBooleanSharedPref(Activity activity, String key, Boolean value) {
        if (activity != null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static Boolean readBooleanSharedPref(String key, Activity activity) {
        if (activity != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            return preferences.getBoolean(key, true);
        }
        return true;
    }

    public static Location getLocation(Activity activity) {
        if (Utils.getApplication(activity) != null) {
            return Utils.getApplication(activity).getLocation();
        }
        return null;
    }

    public static void setLocation(Activity activity, double lat, double longtitude) {
        if (Utils.getApplication(activity) != null) {
            Location location = Utils.getApplication(activity).getLocation();
            if (location == null) {
                location = new Location("");
            }
            location.setLatitude(lat);
            location.setLongitude(longtitude);
            Utils.getApplication(activity).setLocation(location);
        }
    }

    public static MyApplication getApplication(Activity activity) {
        if (activity != null) {
            return (MyApplication) activity.getApplication();
        }
        return null;
    }
}
