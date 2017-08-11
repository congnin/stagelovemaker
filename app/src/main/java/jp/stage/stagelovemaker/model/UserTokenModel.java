package jp.stage.stagelovemaker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by congn on 8/11/2017.
 */

public class UserTokenModel implements Parcelable {
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("userInfo")
    @Expose
    private UserInfoModel userInfo;
    @SerializedName("token_code")
    @Expose
    private String tokenCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public UserInfoModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoModel userInfo) {
        this.userInfo = userInfo;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.errorCode);
        dest.writeString(this.errorMsg);
        dest.writeParcelable(this.userInfo, flags);
        dest.writeString(this.tokenCode);
    }

    public UserTokenModel() {
    }

    protected UserTokenModel(Parcel in) {
        this.errorCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.errorMsg = in.readString();
        this.userInfo = in.readParcelable(UserInfoModel.class.getClassLoader());
        this.tokenCode = in.readString();
    }

    public static final Parcelable.Creator<UserTokenModel> CREATOR = new Parcelable.Creator<UserTokenModel>() {
        @Override
        public UserTokenModel createFromParcel(Parcel source) {
            return new UserTokenModel(source);
        }

        @Override
        public UserTokenModel[] newArray(int size) {
            return new UserTokenModel[size];
        }
    };
}
