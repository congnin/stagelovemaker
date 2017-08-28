package jp.stage.stagelovemaker;

import android.app.Application;
import android.location.Location;
import jp.stage.stagelovemaker.base.EventDistributor;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by congn on 7/31/2017.
 */

public class MyApplication extends Application {
    private Location location;

    private static MyApplication singleton;

    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanovasoft-regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        singleton = this;

        ClientConfig.initialize(this);
        EventDistributor.getInstance();
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
}
