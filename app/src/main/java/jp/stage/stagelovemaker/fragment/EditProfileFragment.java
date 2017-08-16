package jp.stage.stagelovemaker.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Calendar;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.base.EventDistributor;
import jp.stage.stagelovemaker.model.AvatarModel;
import jp.stage.stagelovemaker.model.ErrorModel;
import jp.stage.stagelovemaker.model.InstagramUserModel;
import jp.stage.stagelovemaker.model.UserInfoModel;
import jp.stage.stagelovemaker.network.IHttpResponse;
import jp.stage.stagelovemaker.network.NetworkManager;
import jp.stage.stagelovemaker.utils.Constants;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.OnSingleClickListener;
import jp.stage.stagelovemaker.views.TitleBar;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.app.Activity.RESULT_OK;
import static jp.stage.stagelovemaker.utils.Constants.REQUEST_IMAGE_CAPTURE;
import static jp.stage.stagelovemaker.utils.Constants.REQUEST_IMAGE_GALLERY;

/**
 * Created by congn on 7/12/2017.
 */

public class EditProfileFragment extends BaseFragment implements TitleBar.TitleBarCallback,
        View.OnClickListener, InstagramFragment.InstagramFragmentDelegate {
    public static final String TAG = "EditProfileFragment";

    TitleBar titleBar;
    TextView aboutUserTv;
    TextView instagramTv;
    TextView currentWorkTv, schoolTv, genderTv;
    EditText tvFirstName;
    EditText tvLastName;
    EditText tvBirthday;
    EditText tvAbout;
    EditText tvCurrentWork;
    EditText tvSchool;
    TextView connectInstaTv;
    RadioButton manBtn, womanBtn;

    ArrayList<RoundedImageView> avatarImageView = new ArrayList<>();
    ArrayList<ImageView> removeImageView = new ArrayList<>();
    ArrayList<ImageView> addImageView = new ArrayList<>();

    int indexChange;
    Uri picUri;
    CropImageView cropImageView;
    RelativeLayout layoutCropView;
    TextView cancelCropView;
    TextView chooseCropView;
    ImageView rotateImage;
    int iRotation;

    Calendar now;
    int iMonth;
    int iDay;
    int iYear;
    String firstName = "";
    String lastName = "";
    String birthday = "";
    String gender = "";
    NetworkManager networkManager;
    Gson gson;
    UserInfoModel userInfoModel;

    public static EditProfileFragment newInstance(UserInfoModel userInfoModel) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_DATA, userInfoModel);
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public IHttpResponse iHttpResponse = new IHttpResponse() {
        @Override
        public void onHttpComplete(String response, int idRequest) {
            switch (idRequest) {
                case Constants.ID_UPLOAD_AVATAR:
                    EventDistributor.getInstance().sendMyProfileUpdateBroadcast();
            }
        }

        @Override
        public void onHttpError(String response, int idRequest, int errorCode) {
            switch (idRequest) {
                case Constants.ID_UPLOAD_AVATAR:
                    ErrorModel errorModel = gson.fromJson(response, ErrorModel.class);
                    if (errorModel != null && !TextUtils.isEmpty(errorModel.getErrorMsg())) {
                        Toast.makeText(getActivity(), errorModel.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        networkManager = new NetworkManager(getActivity(), iHttpResponse);
        gson = new Gson();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userInfoModel = getArguments().getParcelable(Constants.KEY_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        titleBar = (TitleBar) view.findViewById(R.id.titleBar);
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.imageView_1));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.imageView_2));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.imageView_3));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.imageView_4));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.imageView_5));
        avatarImageView.add((RoundedImageView) view.findViewById(R.id.imageView_6));

        addImageView.add((ImageView) view.findViewById(R.id.add_imageView_1));
        addImageView.add((ImageView) view.findViewById(R.id.add_imageView_2));
        addImageView.add((ImageView) view.findViewById(R.id.add_imageView_3));
        addImageView.add((ImageView) view.findViewById(R.id.add_imageView_4));
        addImageView.add((ImageView) view.findViewById(R.id.add_imageView_5));
        addImageView.add((ImageView) view.findViewById(R.id.add_imageView_6));

        removeImageView.add((ImageView) view.findViewById(R.id.remove_imageView_1));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_imageView_2));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_imageView_3));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_imageView_4));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_imageView_5));
        removeImageView.add((ImageView) view.findViewById(R.id.remove_imageView_6));

        cropImageView = (CropImageView) view.findViewById(R.id.cropImageView);
        layoutCropView = (RelativeLayout) view.findViewById(R.id.layout_cropview);
        cancelCropView = (TextView) view.findViewById(R.id.back_txt_cropview);
        chooseCropView = (TextView) view.findViewById(R.id.done_txt_cropview);
        rotateImage = (ImageView) view.findViewById(R.id.rotate_img);
        aboutUserTv = (TextView) view.findViewById(R.id.about_user_tv);
        instagramTv = (TextView) view.findViewById(R.id.instagram_tv);
        currentWorkTv = (TextView) view.findViewById(R.id.currentWork_tv);
        schoolTv = (TextView) view.findViewById(R.id.school_tv);
        genderTv = (TextView) view.findViewById(R.id.gender_tv);
        tvAbout = (EditText) view.findViewById(R.id.edt_about_user);
        connectInstaTv = (TextView) view.findViewById(R.id.connect_instagram);
        manBtn = (RadioButton) view.findViewById(R.id.man_radio_btn);
        womanBtn = (RadioButton) view.findViewById(R.id.woman_radio_btn);
        tvBirthday = (EditText) view.findViewById(R.id.edit_birthday);
        tvFirstName = (EditText) view.findViewById(R.id.edit_first_name);
        tvLastName = (EditText) view.findViewById(R.id.edit_last_name);
        tvAbout = (EditText) view.findViewById(R.id.edt_about_user);
        tvCurrentWork = (EditText) view.findViewById(R.id.select_work_tv);
        tvSchool = (EditText) view.findViewById(R.id.select_school_tv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleBar.setTitle(getString(R.string.edit_profile));
        titleBar.enableBackButton();
        titleBar.setTitleRight(getString(R.string.done));
        titleBar.setCallback(this);

        cancelCropView.setOnClickListener(mySingleListener);
        chooseCropView.setOnClickListener(mySingleListener);
        cropImageView.setGuidelines(CropImageView.Guidelines.ON);
        tvBirthday.setOnClickListener(this);
        connectInstaTv.setOnClickListener(this);
        layoutCropView.setVisibility(View.GONE);
        ((GradientDrawable) rotateImage.getBackground()).setStroke(0,
                ContextCompat.getColor(getContext(), R.color.very_dark_gray));
        ((GradientDrawable) rotateImage.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.very_dark_gray));
        rotateImage.setOnClickListener(this);
        for (int i = 0; i < avatarImageView.size(); i++) {
            avatarImageView.get(i).setOnClickListener(mySingleListener);
            addImageView.get(i).setOnClickListener(mySingleListener);
            removeImageView.get(i).setOnClickListener(this);
        }

        now = Calendar.getInstance();
        iMonth = now.get(Calendar.MONTH);
        iDay = now.get(Calendar.DAY_OF_MONTH);
        iYear = now.get(Calendar.YEAR) - Constants.MIN_AGE;
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

    @Override
    public void onResume() {
        super.onResume();
        if (userInfoModel != null) {
            updateAppearance();
        }
    }

    private void updateAppearance() {
        tvFirstName.setText(userInfoModel.getFirstName());
        tvLastName.setText(userInfoModel.getLastName());
        tvBirthday.setText(userInfoModel.getMeta().getBirthday());
        tvAbout.setText(userInfoModel.getMeta().getAboutMe());
        tvCurrentWork.setText(userInfoModel.getMeta().getCurrentWork());
        tvSchool.setText(userInfoModel.getMeta().getSchool());

        if (userInfoModel.getAvatars() != null && !userInfoModel.getAvatars().isEmpty()) {
            ArrayList<AvatarModel> avatars = new ArrayList<>(userInfoModel.getAvatars());
            for (int i = 0; i < avatars.size(); i++) {

                AvatarModel avatarModel = avatars.get(i);
                if (avatarModel != null && !TextUtils.isEmpty(avatarModel.getUrl())) {
                    updateAvatar(avatarImageView.get(avatarModel.getNumberIndex()),
                            addImageView.get(avatarModel.getNumberIndex()),
                            removeImageView.get(avatarModel.getNumberIndex()), avatarModel);
                } else {
                    removeAvatar(avatarImageView.get(i), addImageView.get(i), removeImageView.get(i));
                }
            }
        }
    }

    @Override
    public void onTitleBarClicked() {

    }

    @Override
    public void onRightButtonClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void onBackButtonClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void onMiddleButtonClicked() {

    }

    @Override
    public void onClick(View v) {
        Utils.hideSoftKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.edit_birthday:
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.YEAR, now.get(Calendar.YEAR) - Constants.MIN_AGE);
                DatePickerDialog dpd;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dpd = new DatePickerDialog(getActivity(),
                            R.style.style_date_picker_dialog, myDateListener, iYear, iMonth, iDay);
                } else {
                    dpd = new DatePickerDialog(getActivity(), myDateListener, iYear, iMonth, iDay);
                }

                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                dpd.show();
                break;
            case R.id.rotate_img: {
                iRotation = iRotation + 90;
                if (iRotation == 360) {
                    iRotation = 0;
                }
                cropImageView.setRotatedDegrees(iRotation);
                break;
            }
            case R.id.remove_imageView_1: {
                indexChange = 0;
                removeAvatar(avatarImageView.get(indexChange), addImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.remove_imageView_2: {
                indexChange = 1;
                removeAvatar(avatarImageView.get(indexChange), addImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.remove_imageView_3: {
                indexChange = 2;
                removeAvatar(avatarImageView.get(indexChange), addImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.remove_imageView_4: {
                indexChange = 3;
                removeAvatar(avatarImageView.get(indexChange), addImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.remove_imageView_5: {
                indexChange = 4;
                removeAvatar(avatarImageView.get(indexChange), addImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.remove_imageView_6: {
                indexChange = 5;
                removeAvatar(avatarImageView.get(indexChange), addImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.connect_instagram:
                InstagramFragment fragment = InstagramFragment.createInstance();
                fragment.setDelegate(this);
                add(fragment, InstagramFragment.TAG, true, false);
                break;
        }
    }

    private OnSingleClickListener mySingleListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            Utils.hideSoftKeyboard(getActivity());
            switch (v.getId()) {
                case R.id.imageView_1:
                case R.id.add_imageView_1: {
                    indexChange = 0;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_2:
                case R.id.add_imageView_2: {
                    indexChange = 1;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_3:
                case R.id.add_imageView_3: {
                    indexChange = 2;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_4:
                case R.id.add_imageView_4: {
                    indexChange = 3;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_5:
                case R.id.add_imageView_5: {
                    indexChange = 4;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_6:
                case R.id.add_imageView_6: {
                    indexChange = 5;
                    chooseAvatar();
                    break;
                }
                case R.id.done_txt_cropview: {
                    setImageForUser(cropImageView.getCroppedImage());
                }
                case R.id.back_txt_cropview: {
                    layoutCropView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

    void setImageForUser(Bitmap imageBitmap) {
        if (imageBitmap != null) {
            removeImageView.get(indexChange).setVisibility(View.VISIBLE);
            avatarImageView.get(indexChange).setImageBitmap(imageBitmap);
            addImageView.get(indexChange).setVisibility(View.GONE);
            networkManager.requestApi(networkManager.uploadAvatar(indexChange, imageBitmap), Constants.ID_UPLOAD_AVATAR);
        }
    }

    void chooseAvatar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(getResources().getStringArray(R.array.choose_avatar), (dialog, item) -> {
            if (item == 0) {
                dispatchSelectImageIntent();
            } else {
                dispatchTakePictureIntent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dispatchSelectImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), REQUEST_IMAGE_GALLERY);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                picUri = FileProvider.getUriForFile(getContext(), "jp.stage.stagelovemaker.fileprovider", Utils.getOutputMediaFile(getContext()));
            } else {
                picUri = Uri.fromFile(Utils.getOutputMediaFile(getContext()));
            }
            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri);
            takePictureIntent.putExtra("return-data", true);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void cropImage() {
        iRotation = 0;
        layoutCropView.setVisibility(View.VISIBLE);
        cropImageView.setImageUriAsync(picUri);
        removeImageView.get(indexChange).setImageResource(R.drawable.delete_image);
    }

    void updateAvatar(RoundedImageView imageView, ImageView addImage, ImageView removeImage, AvatarModel model) {
        removeImage.setVisibility(View.VISIBLE);
        addImage.setVisibility(View.GONE);
        imageView.setBorderColor(ContextCompat.getColor(getActivity(), R.color.very_light_gray));
        Glide.with(getContext())
                .load(model.getUrl())
                .asBitmap()
                .placeholder(R.mipmap.ic_holder)
                .error(R.mipmap.ic_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }

    void removeAvatar(RoundedImageView imageView, ImageView addImage, ImageView removeImage) {
        removeImage.setVisibility(View.GONE);
        addImage.setVisibility(View.VISIBLE);
        //imageView.setImageResource(R.drawable.ic_add);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add);
        imageView.setImageBitmap(bitmap);
        imageView.setBorderColor(Color.TRANSPARENT);
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
            tvBirthday.setText(birthday);
        }
    };

    @Override
    public void instagramUserInfo(String model) {
        connectInstaTv.setText(model);
    }
}
