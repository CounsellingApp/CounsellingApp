package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import es.dmoral.toasty.Toasty;
import lnmiit.college.counsellingapp.R;


public class Showfancytoasr {
    public static void show(Context context, String text)
    {
//        Toasty.Config.getInstance().allowQueue(true).apply();
//        Toasty.custom(context,text, R.drawable.linkedin,R.color.white,Toasty.LENGTH_LONG,true,true).show();
        Toasty.Config.getInstance()
                .allowQueue(false)
                .apply();
        Toasty.custom(context, text, context.getResources().getDrawable(R.drawable.logocwph),
                context.getResources().getColor(R.color.white), context.getResources().getColor(R.color.tab_background_selected), Toasty.LENGTH_LONG, true, true).show();
        Toasty.Config.reset();


    }
}
