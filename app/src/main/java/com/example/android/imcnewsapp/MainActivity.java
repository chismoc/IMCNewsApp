package com.example.android.imcnewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.bumptech.glide.request.RequestOptions;
import com.example.android.imcnewsapp.adapters.GridCategoryAdapter;
import com.example.android.imcnewsapp.rest.ApiClient;
import com.example.android.imcnewsapp.rest.ApiInterface;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //Getting news
        getHomeData();
    }

    private void getHomeData() {

        ApiInterface  apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("page",1+"");
        params.put("posts",10+"");

        Call<Object> call = apiInterface.getHomepageApi(params);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void AddImagesToSlider() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.basic);
        images.add(R.drawable.android);
        images.add(R.drawable.binary);
        images.add(R.drawable.components);
        images.add(R.drawable.db);
        images.add(R.drawable.html);
        images.add(R.drawable.java);
        images.add(R.drawable.logic);


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