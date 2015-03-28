package qilin.caiqiaolinpan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
                if (position == 0) {
//                    if the take_a_photo pic is pressed, allow user to take a photo and set as avatar
                }
                Intent intent = new Intent();
                intent.putExtra("imageId", imageIds[position]);
                setResult(1, intent);
                finish();
            }
        });
    }
}