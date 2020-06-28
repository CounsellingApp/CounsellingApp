package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import lnmiit.college.counsellingapp.LoginActivity;
import lnmiit.college.counsellingapp.R;

public class Intro_Adapter extends PagerAdapter {
    private Context context;
    private int[] layouts;
    private LayoutInflater inflater;
    private ImageButton lgn;
    private Activity activity;
    public Intro_Adapter(Context context, int[] layouts,Activity activity)
    {
        this.context = context;
        this.layouts = layouts;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = inflater.inflate(layouts[position],container,false);
        if(position==2) {
            lgn = view.findViewById(R.id.lgn);
            lgn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    context.startActivity(intent);
                    activity.finish();

                }
            });
        }
        if(position==1)
        {
            ImageButton getstarted2 = view.findViewById(R.id.getstarted2);
            getstarted2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    context.startActivity(intent);
                    activity.finish();
                }
            });
        }
        if(position==0)
        {
            ImageButton getstarted1 = view.findViewById(R.id.getstarted1);
            getstarted1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    context.startActivity(intent);
                    activity.finish();
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
