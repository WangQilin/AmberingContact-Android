package qilin.caiqiaolinpan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private final int[] imageIds = {
            R.drawable.profile_0, R.drawable.profile_1,
            R.drawable.profile_2, R.drawable.profile_3,
            R.drawable.profile_4, R.drawable.profile_5,
            R.drawable.profile_6, R.drawable.profile_7,
            R.drawable.profile_8, R.drawable.profile_9,
            R.drawable.profile_10,
    };

    private ImageView iv;

    private Context context;

    // constructor
    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return imageIds[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (iv == null) {
            iv = new ImageView(context);
            iv.setLayoutParams(new GridView.LayoutParams(85, 85));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(8, 8, 8, 8);
        } else {
            iv = (ImageView) convertView;
        }

        iv.setImageResource(imageIds[position]);

        return iv;
    }
}
