package qilin.caiqiaolinpan.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.Calendar;

import qilin.caiqiaolinpan.R;
import qilin.caiqiaolinpan.database.DatabaseOperations;

public class AddContactActivity extends Activity {

    private final String TAG = this.getClass().getName();

    private String name;
    private String phone;
    private String dob;
    private int profilePictureId = R.drawable.profile_0;
    private String profilePictureUri;

    private EditText et_new_contact_name;
    private EditText et_new_contact_phone;
    private EditText et_new_contact_dob;
    private ImageView iv_choose_profile_picture;

    private Calendar calendar;

    private Context context = this;

    // request code
    private final static int DATE_DIALOG_REQUEST = 0;
    private final static int PROFILE_PICTURE_REQUEST = 1;

    // return profile picture type
    private final static int SYSTEM_PROFILE_PICTURE = 0;
    private final static int GALLERY_PICTURE = 1;
    private final static int CAMERA_PICTURE = 2;

    // used to get profilePictureUri from intent
    private static final String PROFILE_PICTURE_URI = "profilePictureUri";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        et_new_contact_name = (EditText) findViewById(R.id.et_new_contact_name);
        et_new_contact_phone = (EditText) findViewById(R.id.et_new_contact_phone);
        et_new_contact_dob = (EditText) findViewById(R.id.et_new_contact_dob);
        iv_choose_profile_picture = (ImageView) findViewById(R.id.iv_choose_profile_picture);

        // onClickListener will only respond when user presses twice, so use OnTouchListener
        et_new_contact_dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(DATE_DIALOG_REQUEST);
                return false;
            }
        });

        // profile_picture onClick to allow user to choose another profile_picture
        iv_choose_profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddContactActivity.this, ChooseProfilePictureActivity.class);
                startActivityForResult(intent, PROFILE_PICTURE_REQUEST);
            }
        });
    }

    // back from ChooseProfilePictureActivity, request code is PROFILE_PICTURE_REQUEST
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "got result from ChooseProfilePictureActivity");
        if (requestCode == PROFILE_PICTURE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "resultCode is ok");
                Bundle bundle = data.getExtras();
                int profilePictureType = bundle.getInt("type");
                switch (profilePictureType) {
                    case SYSTEM_PROFILE_PICTURE:
                        profilePictureId = bundle.getInt("imageId");
                        profilePictureUri = "";
                        iv_choose_profile_picture.setImageResource(profilePictureId);
                        Log.i(TAG, "set system profile_picture");
                        break;
                    case GALLERY_PICTURE:
                    case CAMERA_PICTURE:
                        Log.i(TAG, "on USER_DEFINED_PROFILE_PICTURE");
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Uri picUri = Uri.parse(extras.getString(PROFILE_PICTURE_URI));
                            profilePictureUri = picUri.toString();
                            profilePictureId = 0;
                            Log.i(TAG, "profile picture uri: " + picUri.toString());
                            Bitmap profilePictureBitmap = decodeUriToBitmap(picUri);
                            if (profilePictureBitmap != null) {
                                iv_choose_profile_picture.setImageBitmap(profilePictureBitmap);
                                Log.i(TAG, "set self-defined profile_picture");
                            } else {
                                Log.e(TAG, "failed to decode Uri to Bitmap");
                            }
                        }
                        break;
                    default:
                        profilePictureId = R.drawable.profile_0;
                        profilePictureUri = "";
                        iv_choose_profile_picture.setImageResource(profilePictureId);
                        Log.i(TAG, "set default profile picture");
                }
            } else {
                Log.i(TAG, "resultCode is not OK");
            }
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATE_DIALOG_REQUEST:
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        et_new_contact_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                break;
        }
        return dialog;
    }

    // on 'add' button click
    public void addContact(View v) {
        name = et_new_contact_name.getText().toString();
        phone = et_new_contact_phone.getText().toString();
        dob = et_new_contact_dob.getText().toString();

        //todo: index, get the largest, plus 1

        if (name.equals("")) {
            Toast.makeText(this, "Contact name can't be null...", Toast.LENGTH_SHORT).show();
        } else if (phone.equals("")) {
            Toast.makeText(this, "Contact phone number can't be null...", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseOperations dop = new DatabaseOperations(context);
            dop.putInformation(dop, 1, name, phone, dob, profilePictureId, profilePictureUri);
        }
    }

    private Bitmap decodeUriToBitmap(Uri uri) {
        Log.i(TAG, "decoding URI: " + uri.toString());

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "failed to decode Uri to Bitmap, FileNotFoundException: " + e);
            return null;
        }
        return bitmap;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}