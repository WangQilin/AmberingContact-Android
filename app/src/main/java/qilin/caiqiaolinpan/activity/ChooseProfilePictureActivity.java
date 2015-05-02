package qilin.caiqiaolinpan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import qilin.caiqiaolinpan.ImageAdapter;
import qilin.caiqiaolinpan.R;

public class ChooseProfilePictureActivity extends Activity {

    private final String TAG = this.getClass().getName();

    private final int[] imageIds = {
            R.drawable.take_a_photo,
            R.drawable.profile_0,
            R.drawable.profile_1,
            R.drawable.profile_2,
            R.drawable.profile_3,
            R.drawable.profile_4,
            R.drawable.profile_5,
            R.drawable.profile_6,
            R.drawable.profile_7,
            R.drawable.profile_8,
            R.drawable.profile_9,
            R.drawable.profile_10,
    };

    // Uri of the photo bitmap
    private Uri cameraPictureUri;
    private Uri galleryPictureUri;

    // profile picture file location
    private static final String PROFILE_PICTURE_FILE_LOCATION = "file:///sdcard/profilePicture.jpg";

    // request codes
    private final static int GET_GALLERY_IMAGE_REQUEST = 0;
    private final static int GET_CAMERA_IMAGE_REQUEST = 1;
    private final static int CROP_GALLERY_IMAGE_REQUEST = 2;
    private final static int CROP_CAMERA_IMAGE_REQUEST = 3;

    // return profile picture type
    private final static int SYSTEM_PROFILE_PICTURE = 0;
    private final static int GALLERY_PICTURE = 1;
    private final static int CAMERA_PICTURE = 2;

    // strings of the alert window
    private String[] items = new String[]{"gallery", "camera"};

    // used by AddContactActivity to get profilePictureUri from intent
    private static final String PROFILE_PICTURE_URI = "profilePictureUri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile_picture);

        cameraPictureUri = Uri.parse(PROFILE_PICTURE_FILE_LOCATION);

        ImageAdapter adapter = new ImageAdapter(ChooseProfilePictureActivity.this, imageIds);
        GridView gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // if the take_a_photo pic is pressed, allow user to choose a pic from gallery or camera
                if (position == 0) {
                    Log.i(TAG, "user chose to use a self-defined pic as profile pic");
                    showDialog();
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("type", SYSTEM_PROFILE_PICTURE);
                    returnIntent.putExtra("imageId", imageIds[position]);
                    setResult(RESULT_OK, returnIntent);
                    Log.i(TAG, "user chose a system pic as avatar");
                    finish();
                }
            }
        });
    }

    // show dialog, allow the user to choose from gallery or camera
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.activity_choose_avatar_popup_title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case GET_GALLERY_IMAGE_REQUEST:
                                Log.i(TAG, "choose a pic from gallery");
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*");
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery, GET_GALLERY_IMAGE_REQUEST);
                                break;
                            case GET_CAMERA_IMAGE_REQUEST:
                                //capture a big bitmap and store it in Uri
                                Log.i(TAG, "take a photo");
                                if (cameraPictureUri == null) {
                                    Log.e(TAG, "profilePictureUri is null!");
                                }
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // check if memory card is applicable for image storage
                                if (hasSdcard()) {
                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUri);
                                    startActivityForResult(cameraIntent, GET_CAMERA_IMAGE_REQUEST);
                                } else {
                                    Log.e(TAG, "no memory card for image storage");
                                }
                                break;
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_GALLERY_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "got photo from gallery, about to start cropping");
                    galleryPictureUri = data.getData();
                    cropPicture(galleryPictureUri, GALLERY_PICTURE);
                } else {
                    Log.e(TAG, "failed to get pic from gallery");
                }
                break;
            case GET_CAMERA_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    if (hasSdcard()) {
                        Log.i(TAG, "got photo from camera, about to start cropping");
                        cropPicture(cameraPictureUri, CAMERA_PICTURE);
                    } else {
                        Log.e(TAG, "no sdcard for photo storage");
                        Toast.makeText(ChooseProfilePictureActivity.this, "didn't find an SD card for image storage", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "failed to get pic from camera");
                }
                break;
            // when photo cropping finishes
            case CROP_GALLERY_IMAGE_REQUEST:
                if (data != null) {
                    Log.i(TAG, "finish cropping gallery image, about to get back to AddContactActivity...");
                    data.putExtra(PROFILE_PICTURE_URI, galleryPictureUri.toString());
                    data.putExtra("type", GALLERY_PICTURE);
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    Log.e(TAG, "got a null from cropping");
                }
                break;
            // when photo cropping finishes
            case CROP_CAMERA_IMAGE_REQUEST:
                if (data != null) {
                    Log.i(TAG, "finish cropping camera image, about to get back to AddContactActivity...");
                    data.putExtra(PROFILE_PICTURE_URI, cameraPictureUri.toString());
                    data.putExtra("type", CAMERA_PICTURE);
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    Log.e(TAG, "got a null from cropping");
                }
                break;
        }
    }

    // crop picture
    public void cropPicture(Uri uri, int type) {
        Log.i(TAG, "start photo zooming");
        Log.i(TAG, uri.toString());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // set to crop
        intent.putExtra("crop", "true");
        // aspectX aspectY is the ratio of width & height
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY are pic's width and height in dp?
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);

        if (type == GALLERY_PICTURE) {
            startActivityForResult(intent, CROP_GALLERY_IMAGE_REQUEST);
        } else if (type == CAMERA_PICTURE) {
            startActivityForResult(intent, CROP_CAMERA_IMAGE_REQUEST);
        }
    }

    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}