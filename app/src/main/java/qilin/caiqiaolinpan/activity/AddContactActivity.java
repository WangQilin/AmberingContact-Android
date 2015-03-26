package qilin.caiqiaolinpan.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

import qilin.caiqiaolinpan.ImageAdapter;
import qilin.caiqiaolinpan.R;

public class AddContactActivity extends Activity {

    private String name;
    private String phone;
    private String dob;

    private EditText et_new_contact_name;
    private EditText et_new_contact_phone;
    private EditText et_new_contact_dob;
    private ImageView iv_choose_avatar;

    private Calendar c;

    private final static int DATE_DIALOG = 1;
    private final static int AVATAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        et_new_contact_name = (EditText) findViewById(R.id.et_new_contact_name);
        et_new_contact_phone = (EditText) findViewById(R.id.et_new_contact_phone);
        et_new_contact_dob = (EditText) findViewById(R.id.et_new_contact_dob);
        iv_choose_avatar = (ImageView) findViewById(R.id.iv_choose_avatar);

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

    // from ChooseAvatarActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getExtras();
        int imageId = bundle.getInt("imageId");
        iv_choose_avatar.setImageResource(imageId);
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
    }

}