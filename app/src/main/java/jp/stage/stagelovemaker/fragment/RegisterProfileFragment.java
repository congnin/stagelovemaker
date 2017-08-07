package jp.stage.stagelovemaker.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.Date;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.activity.MainActivity;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.model.SignUp;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.Avatar;
import jp.stage.stagelovemaker.views.FormInputCombobox;
import jp.stage.stagelovemaker.views.FormInputText;
import jp.stage.stagelovemaker.views.LoginActionBar;

import static android.app.Activity.RESULT_OK;
import static jp.stage.stagelovemaker.utils.Constants.REQUEST_IMAGE_CAPTURE;
import static jp.stage.stagelovemaker.utils.Constants.REQUEST_IMAGE_GALLERY;

/**
 * Created by congn on 8/7/2017.
 */

public class RegisterProfileFragment extends BaseFragment implements LoginActionBar.LoginActionBarDelegate,
        FormInputText.FormInputTextDelegate, View.OnClickListener {
    public static final String TAG = "RegisterProfileFragment";

    LoginActionBar actionBar;
    Avatar ivAvatar;
    FormInputText tvFirstName;
    FormInputText tvLastName;
    FormInputCombobox tvBirthday;
    FormInputCombobox tvGender;

    Calendar now;
    String[] genders;
    int iMonth;
    int iDay;
    int iYear;
    int indexGender = -1;
    Boolean bFlagButtonNext = false;
    Uri picUri;
    CropImageView cropImageView;
    RelativeLayout layoutCropView;
    TextView cancelCropView;
    TextView chooseCropView;

    SignUp signUp;
    String firstName = "";
    String lastName = "";
    String birthday = "";
    String gender = "";
    ImageView rotateImage;
    int iRotation;

    public static RegisterProfileFragment newInstance() {
        Bundle args = new Bundle();
        RegisterProfileFragment fragment = new RegisterProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_profile, container, false);
        actionBar = (LoginActionBar) view.findViewById(R.id.action_bar);
        ivAvatar = (Avatar) view.findViewById(R.id.iv_avatar);
        tvFirstName = (FormInputText) view.findViewById(R.id.tv_first_name);
        tvLastName = (FormInputText) view.findViewById(R.id.tv_last_name);
        tvBirthday = (FormInputCombobox) view.findViewById(R.id.tv_birthday);
        tvGender = (FormInputCombobox) view.findViewById(R.id.tv_gender);
        cropImageView = (CropImageView) view.findViewById(R.id.cropImageView);
        layoutCropView = (RelativeLayout) view.findViewById(R.id.layout_cropview);
        cancelCropView = (TextView) view.findViewById(R.id.back_txt_cropview);
        chooseCropView = (TextView) view.findViewById(R.id.done_txt_cropview);
        rotateImage = (ImageView) view.findViewById(R.id.rotate_img);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionBar.setTitle(getString(R.string.register));
        actionBar.setDelegate(getTag(), this);
        tvFirstName.renderDara(getString(R.string.first_name), false);
        tvLastName.renderDara(getString(R.string.last_name), false);
        tvFirstName.setDelegate(this, Constants.TAG_CONTROL_INPUT_FIRSTNAME);
        tvLastName.setDelegate(this, Constants.TAG_CONTROL_INPUT_LASTNAME);
        tvBirthday.setTitle(getString(R.string.birthday));
        tvGender.setTitle(getString(R.string.gender));

        ivAvatar.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        cancelCropView.setOnClickListener(this);
        chooseCropView.setOnClickListener(this);
        cropImageView.setGuidelines(CropImageView.Guidelines.ON);
        layoutCropView.setVisibility(View.GONE);
        ((GradientDrawable) rotateImage.getBackground()).setStroke(0,
                ContextCompat.getColor(getContext(), R.color.very_dark_gray));
        ((GradientDrawable) rotateImage.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.very_dark_gray));
        rotateImage.setOnClickListener(this);

        genders = getResources().getStringArray(R.array.gender_profile);
        now = Calendar.getInstance();
        iMonth = now.get(Calendar.MONTH);
        iDay = now.get(Calendar.DAY_OF_MONTH);
        iYear = now.get(Calendar.YEAR) - Constants.MIN_AGE;
        getSignUpModel();
    }

    @Override
    public void didBack() {
        updateDataSignup();
        getActivity().onBackPressed();
    }

    @Override
    public void didNext() {
        nextAction();
    }

    @Override
    public void valuechange(String tag, String text) {
        switch (tag) {
            case Constants.TAG_CONTROL_INPUT_FIRSTNAME:
                firstName = text;
                break;
            case Constants.TAG_CONTROL_INPUT_LASTNAME:
                lastName = text;
                break;
        }
        validate();
    }

    @Override
    public void didReturn(String tag) {
        nextAction();
    }

    @Override
    public void inputTextFocus(Boolean b, String tag) {

    }

    @Override
    public void onClick(View v) {
        Utils.hideSoftKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.tv_birthday: {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.YEAR, now.get(Calendar.YEAR) - Constants.MIN_AGE);
                DatePickerDialog dpd;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dpd = new DatePickerDialog(getActivity(), R.style.style_date_picker_dialog, myDateListener, iYear, iMonth, iDay);
                } else {
                    dpd = new DatePickerDialog(getActivity(), myDateListener, iYear, iMonth, iDay);
                }

                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                dpd.show();
                break;
            }
            case R.id.tv_gender: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(genders, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        indexGender = item;
                        gender = genders[item].toString();
                        tvGender.setValue(genders[item].toString());
                        tvGender.setIssuseText("");
                        validate();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }
            case R.id.iv_avatar: {
                updateDataSignup();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(getResources().getStringArray(R.array.choose_avatar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            dispatchSelectImageIntent();
                        } else {
                            dispatchTakePictureIntent();
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }
            case R.id.done_txt_cropview: {
                if (signUp != null && cropImageView != null && cropImageView.getCroppedImage() != null) {
                    signUp.setAvatar(cropImageView.getCroppedImage());
                    ivAvatar.setAvatar(cropImageView.getCroppedImage());
                }
            }
            case R.id.back_txt_cropview: {
                layoutCropView.setVisibility(View.GONE);
                break;
            }
            case R.id.rotate_img: {
                iRotation = iRotation + 90;
                if (iRotation == 360) {
                    iRotation = 0;
                }
                cropImageView.setRotatedDegrees(iRotation);
                break;
            }
        }
    }

    private void updateDataSignup() {
        if (signUp != null) {
            signUp.setFirst_name(firstName);
            signUp.setLast_name(lastName);
            signUp.setBirthday(birthday);
            signUp.setIndexGender(indexGender);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (signUp != null) {
            birthday = signUp.getBirthday();
            firstName = signUp.getFirst_name();
            lastName = signUp.getLast_name();
            tvFirstName.setTitle(signUp.getFirst_name());
            tvLastName.setTitle(signUp.getLast_name());
            tvBirthday.setValue(signUp.getBirthday());
            if (signUp.getIndexGender() != -1) {
                gender = genders[signUp.getIndexGender()];
                indexGender = signUp.getIndexGender();
                tvGender.setValue(gender);
            }

            if (!TextUtils.isEmpty(signUp.getBirthday())) {
                Date date = Utils.parseDateString(signUp.getBirthday());
                if (date != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    iMonth = calendar.get(Calendar.MONTH);
                    iDay = calendar.get(Calendar.DAY_OF_MONTH);
                    iYear = calendar.get(Calendar.YEAR);
                }
            }

            if (signUp.getAvatar() != null) {
                ivAvatar.setAvatar(signUp.getAvatar());
            } else {
                if (!TextUtils.isEmpty(signUp.getLink_avatar())) {
                    ivAvatar.setAvatar(signUp.getLink_avatar());
                }
            }
        }
    }

    private boolean validate() {
        Boolean isValid = true;
        String blankField = getString(R.string.field_blank);
        if (bFlagButtonNext) {
            tvFirstName.setIssuseText("");
            tvLastName.setIssuseText("");
            tvBirthday.setIssuseText("");
            tvGender.setIssuseText("");
        }

        if (TextUtils.isEmpty(firstName)) {
            if (bFlagButtonNext) {
                tvFirstName.setIssuseText(blankField);
            }
            isValid = false;
        }

        if (TextUtils.isEmpty(lastName)) {
            if (bFlagButtonNext) {
                tvLastName.setIssuseText(blankField);
            }
            isValid = false;
        }

        if (TextUtils.isEmpty(birthday)) {
            if (bFlagButtonNext) {
                tvBirthday.setIssuseText(blankField);
            }
            isValid = false;
        }

        if (TextUtils.isEmpty(gender)) {
            if (bFlagButtonNext) {
                tvLastName.setIssuseText(blankField);
            }
            isValid = false;
        }

        if (isValid) {
            actionBar.setTextNextColor(Color.WHITE);
        } else {
            actionBar.setTextNextColor(ContextCompat.getColor(getContext(), R.color.color_dim_text));
        }

        if (bFlagButtonNext) {
            bFlagButtonNext = false;
        }
        return isValid;
    }

    protected void cropImage() {
        layoutCropView.setVisibility(View.VISIBLE);
        cropImageView.setImageUriAsync(picUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            cropImage();
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            picUri = data.getData();
            cropImage();

        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            iDay = dayOfMonth;
            iMonth = monthOfYear;
            iYear = year;
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            birthday = Utils.formatDateLocal(getActivity(), calendar.getTime());
            tvBirthday.setValue(birthday);
            tvBirthday.setIssuseText("");
            validate();
        }
    };

    public void getSignUpModel() {
        if (getActivity() != null) {
            RegisterFragment fragment = (RegisterFragment) getActivity().getSupportFragmentManager()
                    .findFragmentByTag(RegisterFragment.TAG);
            if (fragment != null) {
                signUp = fragment.getSignUp();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    picUri = FileProvider.getUriForFile(getContext(), "jp.stage.stagelovemaker.fileprovider", Utils.getOutputMediaFile(getContext()));
                } catch (Exception e) {

                }
            } else {
                picUri = Uri.fromFile(Utils.getOutputMediaFile(getContext()));
            }
            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri);
            takePictureIntent.putExtra("return-data", true);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchSelectImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), REQUEST_IMAGE_GALLERY);
    }

    public void nextAction() {
        bFlagButtonNext = true;
        Utils.hideSoftKeyboard(getActivity());
        if (validate()) {
            updateDataSignup();
            startNewActivity(MainActivity.class, null);
            ActivityCompat.finishAffinity(getActivity());
        }
    }
}
