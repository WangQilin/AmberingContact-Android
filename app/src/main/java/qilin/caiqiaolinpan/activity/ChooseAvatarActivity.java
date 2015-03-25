package qilin.caiqiaolinpan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import qilin.caiqiaolinpan.ImageAdapter;
import qilin.caiqiaolinpan.R;

public class ChooseAvatarActivity extends Activity {

    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_avatar);

        gv = (GridView) findViewById(R.id.gv);

        gv.setAdapter(new ImageAdapter(getApplicationContext()));

    }
}
