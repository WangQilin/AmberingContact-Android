package qilin.caiqiaolinpan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import qilin.caiqiaolinpan.R;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private long lastBackPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // todo: read from SQLite and initialize ListView
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressTime <= 1000) {
            finish();
        } else {
            lastBackPressTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "Press again to exit...",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void addContact(View v) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }
}