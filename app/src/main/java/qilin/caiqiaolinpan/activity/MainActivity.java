package qilin.caiqiaolinpan.activity;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import qilin.caiqiaolinpan.R;

public class MainActivity extends Activity {
    private long lastBackPressTime = 0;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // todo: read from SQLite and initialize ListView, put a plus sign at the last row

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = openOrCreateDatabase("db", MODE_PRIVATE, null);


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
}