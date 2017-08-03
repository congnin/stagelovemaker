package jp.stage.stagelovemaker.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.base.BaseFragment;
import jp.stage.stagelovemaker.utils.Utils;
import jp.stage.stagelovemaker.views.OnSingleClickListener;
import jp.stage.stagelovemaker.views.TitleBar;

import static android.app.Activity.RESULT_OK;
import static jp.stage.stagelovemaker.utils.Constants.REQUEST_IMAGE_CAPTURE;
import static jp.stage.stagelovemaker.utils.Constants.REQUEST_IMAGE_GALLERY;

/**
 * Created by congn on 7/12/2017.
 */

public class EditProfileFragment extends BaseFragment implements TitleBar.TitleBarCallback,
        View.OnClickListener {
    public static final String TAG = "EditProfileFragment";
    final int NUMBER_AVATAR = 6;
    final int NO_AVATAR = -2;

    TitleBar titleBar;
    ScrollView scrollView;
    RelativeLayout avatarMainLayout;
    LinearLayout avatarAddLayout;
    TextView smartPhotoTv, aboutUserTv, smartPhotoDesTv, instagramTv;
    TextView currentWorkTv, controlProfileTv, schoolTv, genderTv;
    EditText aboutUserEdt;
    TextView selectWorkTv, selectSchoolTv, connectInstaTv, showAgeTv, inviDistanceTv;
    RadioButton manBtn, womanBtn;

    ArrayList<RoundedImageView> avatarImageView = new ArrayList<>();
    ArrayList<ImageView> removeImageView = new ArrayList<>();

    int indexChange;
    Uri picUri;
    CropImageView cropImageView;
    RelativeLayout layoutCropView;
    TextView cancelCropView;
    TextView chooseCropView;
    ImageView rotateImage;
    int iRotation;

    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
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
        removeImageView.add((ImageView) view.findViewById(R.id.add_imageView_1));
        removeImageView.add((ImageView) view.findViewById(R.id.add_imageView_2));
        removeImageView.add((ImageView) view.findViewById(R.id.add_imageView_3));
        removeImageView.add((ImageView) view.findViewById(R.id.add_imageView_4));
        removeImageView.add((ImageView) view.findViewById(R.id.add_imageView_5));
        removeImageView.add((ImageView) view.findViewById(R.id.add_imageView_6));
        cropImageView = (CropImageView) view.findViewById(R.id.cropImageView);
        layoutCropView = (RelativeLayout) view.findViewById(R.id.layout_cropview);
        cancelCropView = (TextView) view.findViewById(R.id.back_txt_cropview);
        chooseCropView = (TextView) view.findViewById(R.id.done_txt_cropview);
        rotateImage = (ImageView) view.findViewById(R.id.rotate_img);
        smartPhotoTv = (TextView)view.findViewById(R.id.smart_photo_tv);
        smartPhotoDesTv = (TextView)view.findViewById(R.id.smart_photo_des_tv);
        aboutUserTv = (TextView)view.findViewById(R.id.about_user_tv);
        instagramTv = (TextView)view.findViewById(R.id.instagram_tv);
        currentWorkTv = (TextView)view.findViewById(R.id.currentWork_tv);
        schoolTv = (TextView)view.findViewById(R.id.school_tv);
        controlProfileTv = (TextView)view.findViewById(R.id.controlProfile_tv);
        genderTv = (TextView)view.findViewById(R.id.gender_tv);
        aboutUserEdt = (EditText)view.findViewById(R.id.edt_about_user);
        selectWorkTv = (TextView)view.findViewById(R.id.select_work_tv);
        selectSchoolTv = (TextView)view.findViewById(R.id.select_school_tv);
        showAgeTv = (TextView)view.findViewById(R.id.show_age_tv);
        inviDistanceTv =(TextView)view.findViewById(R.id.invi_distance_tv);
        connectInstaTv = (TextView)view.findViewById(R.id.connect_instagram);
        manBtn = (RadioButton)view.findViewById(R.id.man_radio_btn);
        womanBtn = (RadioButton)view.findViewById(R.id.woman_radio_btn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleBar.setTitle(getString(R.string.edit_profile));
        titleBar.enableBackButton();
        titleBar.setCallback(this);

        //set UI
        smartPhotoTv.setTypeface(Utils.getProximaBold(getContext()));
        smartPhotoDesTv.setTypeface(Utils.getProximaBold(getContext()));
        schoolTv.setTypeface(Utils.getProximaBold(getContext()));
        genderTv.setTypeface(Utils.getProximaBold(getContext()));
        currentWorkTv.setTypeface(Utils.getProximaBold(getContext()));
        instagramTv.setTypeface(Utils.getProximaBold(getContext()));
        controlProfileTv.setTypeface(Utils.getProximaBold(getContext()));
        aboutUserTv.setTypeface(Utils.getProximaBold(getContext()));
        selectSchoolTv.setTypeface(Utils.getProximaBold(getContext()));
        selectWorkTv.setTypeface(Utils.getProximaBold(getContext()));
        inviDistanceTv.setTypeface(Utils.getProximaBold(getContext()));
        connectInstaTv.setTypeface(Utils.getProximaBold(getContext()));
        showAgeTv.setTypeface(Utils.getProximaBold(getContext()));
        manBtn.setTypeface(Utils.getProximaBold(getContext()));
        womanBtn.setTypeface(Utils.getProximaBold(getContext()));

        cancelCropView.setOnClickListener(mySingleListener);
        chooseCropView.setOnClickListener(mySingleListener);
        cropImageView.setGuidelines(CropImageView.Guidelines.ON);
        layoutCropView.setVisibility(View.GONE);
        ((GradientDrawable) rotateImage.getBackground()).setStroke(0,
                ContextCompat.getColor(getContext(), R.color.very_dark_gray));
        ((GradientDrawable) rotateImage.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.very_dark_gray));
        rotateImage.setOnClickListener(this);
        for (int i = 0; i < avatarImageView.size(); i++) {
            avatarImageView.get(i).setOnClickListener(mySingleListener);
            removeImageView.get(i).setOnClickListener(this);
        }
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
    public void onTitleBarClicked() {

    }

    @Override
    public void onRightButtonClicked() {

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
            case R.id.rotate_img: {
                iRotation = iRotation + 90;
                if (iRotation == 360) {
                    iRotation = 0;
                }
                cropImageView.setRotatedDegrees(iRotation);
                break;
            }
            case R.id.add_imageView_1: {
                indexChange = 0;
                removeAvatar(avatarImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.add_imageView_2: {
                indexChange = 1;
                removeAvatar(avatarImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.add_imageView_3: {
                indexChange = 2;
                removeAvatar(avatarImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.add_imageView_4: {
                indexChange = 3;
                removeAvatar(avatarImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.add_imageView_5: {
                indexChange = 4;
                removeAvatar(avatarImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
            case R.id.add_imageView_6: {
                indexChange = 5;
                removeAvatar(avatarImageView.get(indexChange), removeImageView.get(indexChange));
                break;
            }
        }
    }

    private OnSingleClickListener mySingleListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            Utils.hideSoftKeyboard(getActivity());
            switch (v.getId()) {
                case R.id.imageView_1: {
                    indexChange = 0;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_2: {
                    indexChange = 1;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_3: {
                    indexChange = 2;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_4: {
                    indexChange = 3;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_5: {
                    indexChange = 4;
                    chooseAvatar();
                    break;
                }
                case R.id.imageView_6: {
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
        }
    }

    void chooseAvatar() {
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

    void removeAvatar(RoundedImageView imageView, ImageView removeImage) {
        removeImage.setImageResource(R.drawable.add_image);
        imageView.setImageResource(R.drawable.draw_image_view_bg);
        imageView.setBorderColor(Color.TRANSPARENT);
    }
}
