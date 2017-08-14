package jp.stage.stagelovemaker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import jp.stage.stagelovemaker.MyApplication;
import jp.stage.stagelovemaker.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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

    public static File savebitmap(Context context, Bitmap bmp, String name) {
        File pictureFile = getOutputMediaFile(context);
        if (pictureFile == null) {
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bmp.compress(Bitmap.CompressFormat.PNG, 60, fos);
            fos.close();
            return pictureFile;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
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

    public static String formatDate(Context context, Date date) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date);
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

    public static Date parseDateString(String date) {
        Date calendar = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            calendar = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
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

    public static void setAvatar(Context context, final ImageView imageView, String avatarUrl) {
        if (context == null) {
            return;
        }
        String url = Constants.LINK_FAIL;
        if (!TextUtils.isEmpty(avatarUrl)) {
            url = avatarUrl;
        }

        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_holder)
                .error(R.mipmap.ic_holder)
                .into(imageView);
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    public static RequestBody toRequestBody(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }
}
