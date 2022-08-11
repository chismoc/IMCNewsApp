package com.example.android.imcnewsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.imcnewsapp.R;

public class NewsDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView sourceName, newsTitle, newsDesc, newsDate, newsView,labelSimilar;
    Button viewMore;
    ImageView imagy,small_icn;


    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initViews();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share){

       /**     if (detailNews != null){
                // Opening sharing options
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, detailNews.getTitle());
                i.putExtra(Intent.EXTRA_TEXT, detailNews.getPostContent());
                startActivity(i);

            }else{*/
                Toast.makeText(this, "Sorry!", Toast.LENGTH_SHORT).show();

          //  }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow_white);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();

                }
            });


        }

    }
}