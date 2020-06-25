package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import lnmiit.college.counsellingapp.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class Custom_Dialog extends DialogFragment {
    private FancyButton customdialogleftbutton, customdialogrightbutton;
    private TextView customdialogtextview;
    private int code;

    public interface Ondialogaction{
        public void mainactivity();
    }
    private Ondialogaction mondialogaction;
    public void setMondialogaction(Ondialogaction mondialogaction){
        this.mondialogaction = mondialogaction;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_customizable,null);
        customdialogleftbutton = view.findViewById(R.id.customdialogleftbutton);
        customdialogrightbutton = view.findViewById(R.id.customdialogrightbutton);
        customdialogtextview = view.findViewById(R.id.customdialogtext);
        customdialogrightbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        customdialogleftbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mondialogaction.mainactivity();
            }
        });
        code = getArguments().getInt("code");
        switch (code)
        {
            case 10:   //MainActivity
                customdialogrightbutton.setText("CANCEL");
                customdialogleftbutton.setText("YES");
                customdialogtextview.setText("Are you sure you want to logout?");
                break;

            case 20: //Ask Question
                customdialogrightbutton.setText("CANCEL");
                customdialogleftbutton.setText("YES");
                customdialogtextview.setText("Are you sure you want to submit this question?");
                break;
            case 30 : //Respond to a question
                customdialogrightbutton.setText("CANCEL");
                customdialogleftbutton.setText("YES");
                customdialogtextview.setText("Are you sure you want to post this answer?");
                break;

        }
        builder.setView(view);
        return builder.create();
    }




}
