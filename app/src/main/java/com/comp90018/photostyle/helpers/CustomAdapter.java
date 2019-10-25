package com.comp90018.photostyle.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.RadioButton;
import android.widget.TextView;

import com.comp90018.photostyle.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Model> itemLabelArrayList;
    public String typeBox="";


    public CustomAdapter(Context context, ArrayList<Model> itemLabelArrayList) {

        this.context = context;
        this.itemLabelArrayList = itemLabelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getCount() {
        return itemLabelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemLabelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.checkBox = (RadioButton) convertView.findViewById(R.id.cb);
            //holder.tvItemLabel = (TextView) convertView.findViewById(R.id.textView1);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        holder.checkBox.setText(itemLabelArrayList.get(position).getItemLabel());

        holder.checkBox.setChecked(itemLabelArrayList.get(position).getSelected());


        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer)  holder.checkBox.getTag();


                if(itemLabelArrayList.get(pos).getSelected()){

                    itemLabelArrayList.get(pos).setSelected(false);

                } else if(!itemLabelArrayList.get(pos).getSelected()) {

                    itemLabelArrayList.get(pos).setSelected(true);
                    Prefs.putString(getTypeBox(),itemLabelArrayList.get(pos).getItemLabel());
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected RadioButton checkBox;
        private TextView tvItemLabel;

    }

    public void setTypeBox(String typeBox){
        this.typeBox=typeBox;
    }
    public String getTypeBox(){
        return typeBox;
    }

}