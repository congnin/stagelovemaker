package jp.stage.stagelovemaker.utils;

/**
 * Created by congn on 7/11/2017.
 */

public final class Constants {
    private Constants() {
        //no instance
    }

    public static final String KEY_DATA = "key_data";
    public static final String KEY_DATA_TWO = "key_data_two";
    public static final String KEY_DATA_THREE = "key_data_three";

    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 100;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_GALLERY = 0;

    public static final String TAG_CONTROL_INPUT_USERNAME= "ControlInputUsername";
    public static final String TAG_CONTROL_INPUT_PASSWORD = "ControlInputPass";
    public static final String TAG_CONTROL_INPUT_EMAIL = "ControlInputEmail";
    public static final String TAG_CONTROL_INPUT_CONFIRM_PASS = "ControlInputConfirm";
    public static final String TAG_CONTROL_INPUT_FIRSTNAME = "ControlInputFirstName";
    public static final String TAG_CONTROL_INPUT_LASTNAME = "ControlInputLastName";
    public static final String TAG_CONTROL_INPUT_ABOUT_ME = "ControlInputAboutMe";
    public static final String TAG_CONTROL_INPUT_LOCATION = "ControlInputLocation";
    public static final String TAG_CONTROL_INPUT_MESSAGE = "ControlInputMessage";
    public static final String TAG_CONTROL_INPUT_YOUTUBE = "ControlInputYoutube";
    public static final String TAG_CONTROL_INPUT_INAPPROPRIATE = "ControlInputInappropriate";

    public static final String SHARE_REF_TOKEN = "RefToken";
    public static final String SHARE_REF_DISTANCE = "RefDistance";
    public static final String SHARE_REF_USER_ID = "RefUserId";
    public static final String SHARE_REF_INDEX_MAINTAB = "RefIndexTab";
    public static final String SHARE_REF_SHOW_WELCOME = "RefShowWelcome";
    public static final String SHARE_REF_EDITPROFILE = "RefEditProfile";
    public static final String SHARE_REF_NOTIFICATION = "RefShowNotification";

    public static final String GET_LOCATION = "GetLocation";

    public static final String LINK_FAIL = "http://wwww.me";
    public static final String SEED_INSTAGRAM = "seed/instagram.json";

    public static final String INSTAGRAM_CLIENT_ID = "38781daf5c3a47538cf095da5c61c595";
    public static final String INSTAGRAM_CALLBACK_URL = "http://www.blablaing.com";


    public static final String INSTAGRAM_AUTH_URL = "https://instagram.com/oauth/authorize/";
    public static final String INSTAGRAM_BASE_URL = "https://api.instagram.com";

    public final static int ID_SIGN_UP = 1;
    public final static int ID_SIGN_IN = 2;
    public final static int ID_UPLOAD_AVATAR = 3;
    public final static int ID_VALIDATE_EMAIL = 4;
}
