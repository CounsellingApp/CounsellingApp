package lnmiit.college.counsellingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import lnmiit.college.counsellingapp.mn.crawler.rview.Intro_Adapter;

public class Introduction_Activity extends AppCompatActivity {
    private ViewPager viewPager;
    private Intro_Adapter intro_adapter;
    private int[] layouts = {R.layout.introslide1,R.layout.introslide2,R.layout.introslide3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        viewPager = findViewById(R.id.viewpager);
        intro_adapter = new Intro_Adapter(Introduction_Activity.this,layouts);
        viewPager.setAdapter(intro_adapter);

    }
}