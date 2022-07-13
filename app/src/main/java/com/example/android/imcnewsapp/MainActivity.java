package com.example.android.imcnewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.bumptech.glide.request.RequestOptions;
import com.example.android.imcnewsapp.adapters.GridCategoryAdapter;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //images for user at homepage
    SliderLayout sliderLayout;
    GridView gridView;

    GridCategoryAdapter gridCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method to initialise widgets
        InitialiseViews();
        AddImagesToSlider();
    }

    private void AddImagesToSlider() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.beautifulsite1);
        images.add(R.drawable.beautifulsite4);
        images.add(R.drawable.beautifulsite6);
        images.add(R.drawable.beautifulsite7);
        images.add(R.drawable.beautifulsite8);

        //
        for (int i = 0; i < images.size(); i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.setRequestOption(new RequestOptions().centerCrop());
            defaultSliderView.image(images.get(i));
            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    //click listener for slides
                }
            });
            sliderLayout.addSlider(defaultSliderView);
        }
        //display images in a cycle

        sliderLayout.startAutoCycle();
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setDuration(3000);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

    }

    private void InitialiseViews() {
        sliderLayout = findViewById(R.id.slider);
        gridView = findViewById(R.id.gridView);
        gridCategoryAdapter = new GridCategoryAdapter(this);
        gridView.setAdapter(gridCategoryAdapter);
    }

    //if slider is stopped close the slider
    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }
}