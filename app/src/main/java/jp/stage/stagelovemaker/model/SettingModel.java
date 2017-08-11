package jp.stage.stagelovemaker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by congn on 8/11/2017.
 */

public class SettingModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("distance_unit")
    @Expose
    private String distanceUnit;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.distanceUnit);
        dest.writeString(this.created);
        dest.writeString(this.modified);
    }

    public SettingModel() {
    }

    protected SettingModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.distanceUnit = in.readString();
        this.created = in.readString();
        this.modified = in.readString();
    }

    public static final Parcelable.Creator<SettingModel> CREATOR = new Parcelable.Creator<SettingModel>() {
        @Override
        public SettingModel createFromParcel(Parcel source) {
            return new SettingModel(source);
        }

        @Override
        public SettingModel[] newArray(int size) {
            return new SettingModel[size];
        }
    };
}
