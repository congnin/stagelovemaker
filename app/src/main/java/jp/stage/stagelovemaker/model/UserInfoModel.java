package jp.stage.stagelovemaker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by congn on 8/11/2017.
 */

public class UserInfoModel implements Parcelable {
    @SerializedName("avatars")
    @Expose
    private List<Object> avatars = null;
    @SerializedName("discover")
    @Expose
    private DiscoverModel discover;
    @SerializedName("setting")
    @Expose
    private SettingModel setting;
    @SerializedName("meta")
    @Expose
    private MetaModel meta;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private Boolean gender;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public List<Object> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<Object> avatars) {
        this.avatars = avatars;
    }

    public DiscoverModel getDiscover() {
        return discover;
    }

    public void setDiscover(DiscoverModel discover) {
        this.discover = discover;
    }

    public SettingModel getSetting() {
        return setting;
    }

    public void setSetting(SettingModel setting) {
        this.setting = setting;
    }

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.avatars);
        dest.writeParcelable(this.discover, flags);
        dest.writeParcelable(this.setting, flags);
        dest.writeParcelable(this.meta, flags);
        dest.writeValue(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeValue(this.gender);
        dest.writeValue(this.age);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.created);
        dest.writeString(this.modified);
    }

    public UserInfoModel() {
    }

    protected UserInfoModel(Parcel in) {
        this.avatars = new ArrayList<Object>();
        in.readList(this.avatars, Object.class.getClassLoader());
        this.discover = in.readParcelable(DiscoverModel.class.getClassLoader());
        this.setting = in.readParcelable(SettingModel.class.getClassLoader());
        this.meta = in.readParcelable(MetaModel.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.gender = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.latitude = (Integer) in.readValue(Integer.class.getClassLoader());
        this.longitude = (Integer) in.readValue(Integer.class.getClassLoader());
        this.created = in.readString();
        this.modified = in.readString();
    }

    public static final Parcelable.Creator<UserInfoModel> CREATOR = new Parcelable.Creator<UserInfoModel>() {
        @Override
        public UserInfoModel createFromParcel(Parcel source) {
            return new UserInfoModel(source);
        }

        @Override
        public UserInfoModel[] newArray(int size) {
            return new UserInfoModel[size];
        }
    };
}
