package com.example.android.imcnewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.bumptech.glide.request.RequestOptions;
import com.example.android.imcnewsapp.adapters.GridCategoryAdapter;
import com.example.android.imcnewsapp.model.HomepageModel;
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

        Call<HomepageModel> call = apiInterface.getHomepageApi(params);
        call.enqueue(new Callback<HomepageModel>() {

            @Override
            public void onResponse(Call<HomepageModel> call, Response<HomepageModel> response) {

                UpdateDataOnHomePage(response.body());
            }

            @Override
            public void onFailure(Call<HomepageModel> call, Throwable t) {

            }
        });
    }

    private void UpdateDataOnHomePage(HomepageModel body) {
        // Adding Slider images from server
        // We are getting images now from body response and not from locally stored images (Drawables)

        // we are not getting images, since we are loading the images from localhost server
        //"image": "http://localhost/newsapp/wp-content/uploads/2020/12/bas.jpg"
        // So, our emulator will not get the images
        // we need to replace localhost with the emulator local host 10.0.2.2

        for (int i= 0; i < body.getBanners().size() ; i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.setRequestOption(new RequestOptions().centerCrop());
            defaultSliderView.image(body.getBanners().get(i).getImage());
            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    // Handling Click event for slides

                }
            });

            sliderLayout.addSlider(defaultSliderView);
        }
    }

    private void AddImagesToSlider() {

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