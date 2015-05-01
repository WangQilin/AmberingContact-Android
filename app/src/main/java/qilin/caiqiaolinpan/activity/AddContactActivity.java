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
    private int profilePicture;

    private EditText et_new_contact_name;
    private EditText et_new_contact_phone;
    private EditText et_new_contact_dob;
    private ImageView iv_choose_avatar;

    private Calendar c;

    private Context context = this;

    // to preserve state
    private String ps_avatarUri;
    private int ps_imageId = R.drawable.profile_0;

    // request code
    private final static int DATE_DIALOG = 1;
    private final static int AVATAR = 1;

    // result code
    private final static int SYSTEM_AVATAR = 1;
    private final static int USER_DEFINED_AVATAR = 2;
    private final static int BACK_BUTTON_PRESSED = 3;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        et_new_contact_name = (EditText) findViewById(R.id.et_new_contact_name);
        et_new_contact_phone = (EditText) findViewById(R.id.et_new_contact_phone);
        et_new_contact_dob = (EditText) findViewById(R.id.et_new_contact_dob);
        iv_choose_avatar = (ImageView) findViewById(R.id.iv_choose_avatar);

        if (ps_avatarUri != null) {
            Uri avatarUri = Uri.parse(ps_avatarUri);
            Bitmap avatarBitmap = decodeUriToBitmap(avatarUri);
            iv_choose_avatar.setImageBitmap(avatarBitmap);
        } else {
            iv_choose_avatar.setImageResource(ps_imageId);
        }

        // onClickListener will only respond when user presses twice
        et_new_contact_dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(DATE_DIALOG);
                return false;
            }
        });

        // avatar onClick to allow user to choose another avatar
        iv_choose_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddContactActivity.this, ChooseAvatarActivity.class);
                startActivityForResult(intent, AVATAR);
            }
        });
    }

    // back from ChooseAvatarActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "got result");
        Bundle bundle = data.getExtras();

        switch (resultCode) {
            case SYSTEM_AVATAR:
                iv_choose_avatar.setImageResource(bundle.getInt("imageId"));
                ps_imageId = bundle.getInt("imageId");
                Log.i(TAG, "set system avatar");
                break;
            case USER_DEFINED_AVATAR:
                Log.i(TAG, "on USER_DEFINED_AVATAR");
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Log.i(TAG, "extras not null");
                    Uri avatarUri = Uri.parse(extras.getString("avatarUri"));
                    ps_avatarUri = avatarUri.toString();
                    Log.i(TAG, avatarUri.toString());
                    Bitmap avatarBitmap = decodeUriToBitmap(avatarUri);
                    iv_choose_avatar.setImageBitmap(avatarBitmap);
                    Log.i(TAG, "set self-defined avatar");
                }
                break;
            case BACK_BUTTON_PRESSED:
                recreate();
            default:
                iv_choose_avatar.setImageResource(R.drawable.profile_0);
                Log.i(TAG, "set default avatar");
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATE_DIALOG:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        et_new_contact_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

                    }
                },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                );
                break;
        }
        return dialog;
    }

    // on 'done' button click
    public void addContact(View v) {
        name = et_new_contact_name.getText().toString();
        phone = et_new_contact_phone.getText().toString();
        dob = et_new_contact_dob.getText().toString();

        if (name.equals("")) {
            Toast.makeText(this, "Contact name can't be null...", Toast.LENGTH_SHORT).show();
        } else if (phone.equals("")) {
            Toast.makeText(this, "Contact phone number can't be null...", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseOperations dop = new DatabaseOperations(context);
            dop.putInformation(dop, name, phone, dob, 0);
        }
    }

    private Bitmap decodeUriToBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "failed to decode Uri to Bitmap, file not found");
            return null;
        }
        return bitmap;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        name = et_new_contact_name.getText().toString();
        phone = et_new_contact_phone.getText().toString();
        dob = et_new_contact_dob.getText().toString();

        savedInstanceState.putString("name", name);
        savedInstanceState.putString("phone", phone);
        savedInstanceState.putString("dob", dob);
        savedInstanceState.putInt("ps_imageId", ps_imageId);
        savedInstanceState.putString("ps_avatarUri", ps_avatarUri);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        name = savedInstanceState.getString("name");
        phone = savedInstanceState.getString("phone");
        dob = savedInstanceState.getString("dob");
        ps_imageId = savedInstanceState.getInt("ps_imageId");
        ps_avatarUri = savedInstanceState.getString("ps_avatarUri");
    }
}