package com.example.android.imcnewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestOptions;
import com.example.android.imcnewsapp.adapters.GridCategoryAdapter;
import com.example.android.imcnewsapp.adapters.NewsAdapter;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //images for user at homepage
    SliderLayout sliderLayout;

    GridView gridView;
    GridCategoryAdapter gridCategoryAdapter;


    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    List<HomepageModel.News> news;

    //Categories List
    List<HomepageModel.CategoryButton> categoryButtons;

    // Variables for making infinite news feed
    int posts = 3;
    int page = 1;
    boolean isFromStart = true;

    // Progressbar
    ProgressBar progressBar;

    NestedScrollView nestedScrollView;

    // Swipe to refresh
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method to initialise widgets
        InitialiseViews();

        AddImagesToSlider();

        //Initial conditions page 1 as the start
        page = 1;
        isFromStart = true;

        //Getting news
        getHomeData();

        //Listener for Getting new data on scrolling and swiping down
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    isFromStart = false;
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    getHomeData();
                }
            }
        });

    }

    private void getHomeData() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("posts", posts + "");

        Call<HomepageModel> call = apiInterface.getHomepageApi(params);
        call.enqueue(new Callback<HomepageModel>() {

            @Override
            public void onResponse(Call<HomepageModel> call, Response<HomepageModel> response) {

                UpdateDataOnHomePage(response.body());
            }

            @Override
            public void onFailure(Call<HomepageModel> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
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

        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

        if (isFromStart) {
            news.clear();
            categoryButtons.clear();
        }


        for (int i = 0; i < body.getBanners().size(); i++) {
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

        // Setting the slider options
        sliderLayout.startAutoCycle();
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setDuration(3000);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);


        // Making way for getting news in correct order and conserving the page numbers
        int beforeNewsSize = news.size();


        for (int i = 0; i < body.getNews().size(); i++) {
            news.add(body.getNews().get(i));
        }
        categoryButtons.addAll(body.getCategoryButton());

        if (isFromStart) {
            recyclerView.setAdapter(newsAdapter);
            gridView.setAdapter(gridCategoryAdapter);
        }else{
            newsAdapter.notifyItemRangeInserted(beforeNewsSize, body.getNews().size());
        }


    }

    private void AddImagesToSlider() {

    }

    @SuppressLint("ResourceAsColor")
    private void InitialiseViews() {

        categoryButtons = new ArrayList<>();
        sliderLayout = findViewById(R.id.slider);
        gridView = findViewById(R.id.gridView);
        gridCategoryAdapter = new GridCategoryAdapter(this, categoryButtons);

        //progress bar
        progressBar = findViewById(R.id.progressBar);

        // Nested scrollview
        nestedScrollView = findViewById(R.id.nestedView);


        // RecyclerView
        recyclerView = findViewById(R.id.recyclerNews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        news = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, news);

        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(R.color.purple_200,
                R.color.purple_500,
                R.color.purple_700);


        swipeRefreshLayout.setOnRefreshListener(this);


    }

    //if slider is stopped close the slider
    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onRefresh() {
        isFromStart = true;
        page = 1;
        getHomeData();

    }
}