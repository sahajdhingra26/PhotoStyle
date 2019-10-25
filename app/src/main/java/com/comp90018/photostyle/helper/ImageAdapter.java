package com.comp90018.photostyle.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp90018.photostyle.R;

import java.util.List;



public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    List<String> images;
    List<String> imagesLabels;

    public ImageAdapter(Context c ,List<String> images,List<String> imageLabels) {
        mContext = c;
        this.images=images;
        this.imagesLabels=imageLabels;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        View MyView = convertView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            //Inflate the layout
            LayoutInflater li = ((Activity) mContext).getLayoutInflater();;
            MyView = li.inflate(R.layout.grid_item, null);

            // Add The Image!!!
            ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);


            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(images.get(position), options);

            // Set inSampleSize
            options.inSampleSize = 4;

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap myBitmap = BitmapFactory.decodeFile(images.get(position), options);

            iv.setImageBitmap(myBitmap);



            // Add The Text!!!
            TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
            tv.setText(imagesLabels.get(position));


        }
        return MyView;
    }




}
