package com.example.article2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.article2.fragments.ArticleListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ArticleListFragment())
                    .commit();
        }
    }
}
