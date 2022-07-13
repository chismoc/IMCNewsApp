package com.example.android.imcnewsapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.imcnewsapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridCategoryAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<DemoCategory> demoCategoryList;

    //constructor

    public GridCategoryAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        demoCategoryList = new ArrayList<>();

        demoCategoryList.add(new DemoCategory(R.drawable.icn1,"Basics"));
        demoCategoryList.add(new DemoCategory(R.drawable.icn2,"Logic"));
        demoCategoryList.add(new DemoCategory(R.drawable.icn3,"Android"));
        demoCategoryList.add(new DemoCategory(R.drawable.icn5,"Binary"));
        demoCategoryList.add(new DemoCategory(R.drawable.icn6,"Components"));
        demoCategoryList.add(new DemoCategory(R.drawable.icn7,"Java"));
        demoCategoryList.add(new DemoCategory(R.drawable.icn8,"HTML"));
        demoCategoryList.add(new DemoCategory(R.drawable.ic4,"Database"));
           }

    @Override
    public int getCount() {
        return demoCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return demoCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       CategoryViewHolder categoryViewHolder = null ;

        if (convertView == null)
        {
            convertView  = layoutInflater.inflate(R.layout.item_category_layout,null);
            categoryViewHolder = new CategoryViewHolder();
            categoryViewHolder.circleImageView = convertView.findViewById(R.id.category_image);
            categoryViewHolder.textView = convertView.findViewById(R.id.text_category);
            convertView.setTag(categoryViewHolder);
        }else{
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }

        //pass the data to text and imageview using Glide

       categoryViewHolder.textView.setText(demoCategoryList.get(position).imageName);

        Glide.with(context)
                .load(demoCategoryList.get(position).imageId).into(categoryViewHolder.circleImageView);


      //  if (categoryBottons.get(position).getColor() != null) {
       //     hOldery.circleImageView.setCircleBackgroundColor(Color.parseColor(categoryBottons.get(position).getColor()));
     //       hOldery.circleImageView.setBorderColor(Color.parseColor(categoryBottons.get(position).getColor()));
       // }

        return convertView;
    }

    //viewHolder
    static class CategoryViewHolder{
        CircleImageView circleImageView;
        TextView textView;
    }

    class DemoCategory {
        int imageId;
        String imageName;

        public DemoCategory(int imageId, String imageName) {
            this.imageId = imageId;
            this.imageName = imageName;
        }
    }

    // We will create a real Category class that fetches data from net


}

