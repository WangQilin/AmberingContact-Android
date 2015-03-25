package qilin.caiqiaolinpan.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import qilin.caiqiaolinpan.R;

public class AddContactActivity extends ActionBarActivity {

    private String name;
    private String phone;
    private String dob;

    private EditText et_new_contact_name;
    private EditText et_new_contact_phone;
    private EditText et_new_contact_dob;

    private Calendar c;

    private final static int DATE_DIALOG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        et_new_contact_name = (EditText) findViewById(R.id.et_new_contact_name);
        et_new_contact_phone = (EditText) findViewById(R.id.et_new_contact_phone);
        et_new_contact_dob = (EditText) findViewById(R.id.et_new_contact_dob);

        // onClickListener will only respond when user presses twice
        et_new_contact_dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(DATE_DIALOG);
                return false;
            }
        });
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

    public void addContact(View v) {
        name = et_new_contact_name.getText().toString();
        phone = et_new_contact_phone.getText().toString();
        dob = et_new_contact_dob.getText().toString();
    }

}
