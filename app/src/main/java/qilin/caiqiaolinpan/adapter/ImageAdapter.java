package qilin.caiqiaolinpan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import qilin.caiqiaolinpan.R;

public class ImageAdapter extends BaseAdapter {

    private final int[] imageIds;

    private Context context;

    // constructor
    public ImageAdapter(Context context, int[] imageIds) {
        this.context = context;
        this.imageIds = imageIds;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            v = new View(context);
            v = inflater.inflate(R.layout.choose_avatar_single_image, null);
            ImageView i = (ImageView) v.findViewById(R.id.avatar_single_image);
            i.setImageResource(imageIds[position]);
        } else {
            v = convertView;
        }

        return v;
    }
}
