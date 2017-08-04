package jp.stage.stagelovemaker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

import jp.stage.stagelovemaker.activity.MainActivity;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by congn on 7/31/2017.
 */

public class MyApplication extends Application {
    int id;
    String accessToken;
    String unitDistance;
    Location location;
    Boolean firstShowWelcome;
    Boolean isAppShow;
    Boolean userShowWelcome;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanovasoft-regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        id = -1;
        isAppShow = false;
    }

    public String getAccessToken(Activity activity) {
        if (TextUtils.isEmpty(accessToken)) {
            accessToken = Utils.readSharedPref(Constants.SHARE_REF_TOKEN, activity);
        }
        return accessToken;

    }

    public void setAccessToken(String accessToken, Activity activity) {
        this.accessToken = accessToken;
        if (TextUtils.isEmpty(accessToken)) {
            Utils.clearSharedPref(activity, Constants.SHARE_REF_TOKEN);
        } else {
            Utils.writeSharedPref(activity, Constants.SHARE_REF_TOKEN, accessToken);
        }
    }

    public int getId(Activity activity) {
        if (id == -1) {
            String userId = Utils.readSharedPref(Constants.SHARE_REF_USER_ID, activity);
            if (!TextUtils.isEmpty(userId)) {
                id = Integer.parseInt(userId);
            }
        }
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id, Activity activity) {
        this.id = id;
        Utils.writeSharedPref(activity, Constants.SHARE_REF_USER_ID, String.valueOf(id));
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location != null) {
            if (this.location == null) {
                this.location = new Location(location);
            } else {
                this.location.set(location);
            }
        } else {
            this.location = null;
        }
    }

    public Boolean getFirstShowWelcome(Activity activity) {
        if (firstShowWelcome == null) {
            firstShowWelcome = Utils.readBooleanSharedPref(Constants.SHARE_REF_SHOW_WELCOME, activity);
        }
        return firstShowWelcome;
    }

    public Boolean getAppShow() {
        return isAppShow;
    }

    public void setAppShow(Boolean appShow) {
        isAppShow = appShow;
    }

    public void setFirstShowWelcome(Boolean firstShowWelcome, Activity activity) {
        Utils.writeBooleanSharedPref(activity, Constants.SHARE_REF_SHOW_WELCOME, firstShowWelcome);
        this.firstShowWelcome = firstShowWelcome;
    }

    public Boolean getUserShowWelcome() {
        return userShowWelcome;
    }

    public void setUserShowWelcome(Boolean userShowWelcome) {
        this.userShowWelcome = userShowWelcome;
    }

    public String getUnitDistance(Activity activity) {
        if (TextUtils.isEmpty(unitDistance)) {
            unitDistance = Utils.readSharedPref(Constants.SHARE_REF_DISTANCE, activity);
        }
        return unitDistance;
    }

    public void setUnitDistance(String unitDistance, Activity activity) {
        Utils.writeSharedPref(activity, Constants.SHARE_REF_DISTANCE, unitDistance);
        if (!TextUtils.isEmpty(this.unitDistance) && !this.unitDistance.equals(unitDistance)) {
            MainActivity mainActivity = (MainActivity) activity;
            //mainActivity.refreshChangeUnitDistance();
        }
        this.unitDistance = unitDistance;
    }
}
