package qilin.caiqiaolinpan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

import qilin.caiqiaolinpan.ImageAdapter;
import qilin.caiqiaolinpan.R;

public class ChooseAvatarActivity extends Activity {

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

    private GridView gv;

    // avatar name
    private final static String IMAGE_FILE_NAME = "avatar.jpg";

    // request codes
    private final static int IMAGE_REQUEST_CODE = 0;
    private final static int CAMERA_REQUEST_CODE = 1;
    private final static int RESULT_REQUEST_CODE = 2;

    // result code
    private final static int SYSTEM_AVATAR = 1;
    private final static int USER_DEFINED_AVATAR = 2;

    // strings of the alert window
    String[] items = new String[]{"choose from galary", "take a photo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_avatar);

        ImageAdapter adapter = new ImageAdapter(ChooseAvatarActivity.this, imageIds);
        gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // if the take_a_photo pic is pressed, allow user to take a photo and set as avatar
                if (position == 0) {
                    showDialog();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("imageId", imageIds[position]);
                    setResult(SYSTEM_AVATAR, intent);
                    finish();
                }
            }
        });
    }

    // show dialog, allow the user to choose from gallery or take a picture
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("choose profile picture")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case IMAGE_REQUEST_CODE:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*");
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                                break;
                            case CAMERA_REQUEST_CODE:
                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // check if memory card is applicable for image storage
                                if (hasSdcard()) {
                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                                }
                                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
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
        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                // choose pic from gallery
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;

                // take a pic
                case CAMERA_REQUEST_CODE:
                    if (hasSdcard()) {
                        File tempFile = new File(Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(ChooseAvatarActivity.this, "didn't find an SD card for image storage", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        // save the image cropped
                        setResult(USER_DEFINED_AVATAR, data);
                        finish();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // crop picture
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // set to crop
        intent.putExtra("crop", "true");
        // aspectX aspectY is the ratio of width & height
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY are pic's width and height in dp?
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    private static boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
}