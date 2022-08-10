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
import com.example.android.imcnewsapp.model.HomepageModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridCategoryAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<HomepageModel.CategoryButton> categoryButtons;

    //constructor

    public GridCategoryAdapter(Context context, List<HomepageModel.CategoryButton> categoryButtons) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        this.categoryButtons = categoryButtons;


           }

    @Override
    public int getCount() {
        return categoryButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder categoryViewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_category_layout, null);
            categoryViewHolder = new CategoryViewHolder();
            categoryViewHolder.circleImageView = convertView.findViewById(R.id.category_image);
            categoryViewHolder.textView = convertView.findViewById(R.id.text_category);
            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }

        //pass the data to text and imageview using Glide

        categoryViewHolder.textView.setText(categoryButtons.get(position).getName());

        Glide.with(context)
                .load(categoryButtons.get(position).getImage()).into(categoryViewHolder.circleImageView);


        if (categoryButtons.get(position).getColor() != null) {
        categoryViewHolder.circleImageView.setCircleBackgroundColor(Color.parseColor(categoryButtons.get(position).getColor()));
        categoryViewHolder.circleImageView.setBorderColor(Color.parseColor(categoryButtons.get(position).getColor()));
        }

        return convertView;
    }

    //viewHolder
    static class CategoryViewHolder {
        CircleImageView circleImageView;
        TextView textView;
    }


    // We will create a real Category class that fetches data from net


}

