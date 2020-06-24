package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import lnmiit.college.counsellingapp.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class Dialog_Tags extends DialogFragment implements View.OnClickListener {

    FancyButton tagmentalhealth, tagphysicalhealth, tagharassment, tagcareer, tagacademic,  tagothers;
    FancyButton btn_submitquestion, btn_cancelquestion;
    FancyButton currentbutton = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogtags,null);
        tagacademic = view.findViewById(R.id.tagacademic);
        tagharassment = view.findViewById(R.id.tagharassment);

        tagcareer = view.findViewById(R.id.tagcareer);
        tagphysicalhealth = view.findViewById(R.id.tagphysicalhealth);
        tagmentalhealth = view.findViewById(R.id.tagmentalhealth);
        tagothers = view.findViewById(R.id.tagothers);
        btn_cancelquestion = view.findViewById(R.id.btn_cancelquestion);
        btn_submitquestion = view.findViewById(R.id.btn_submitquestion);

        btn_submitquestion.setOnClickListener(this);
        btn_cancelquestion.setOnClickListener(this);
        tagothers.setOnClickListener(this);
        tagmentalhealth.setOnClickListener(this);
        tagphysicalhealth.setOnClickListener(this);
        tagcareer.setOnClickListener(this);

        tagharassment.setOnClickListener(this);
        tagacademic.setOnClickListener(this);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.btn_cancelquestion:
                dismiss();
                break;
            case R.id.btn_submitquestion:
                Toast.makeText(v.getContext(),"Question will be submitted",Toast.LENGTH_LONG).show();
                dismiss();
                break;
            default:
                clickbutton((FancyButton)v);
        }
    }
    private void clickbutton(FancyButton afb)
    {
        if(currentbutton==afb)
        {
            afb.setBackgroundColor(getResources().getColor(R.color.white));
            afb.setTextColor(getResources().getColor(R.color.tab_background_selected));
            currentbutton=null;
        }
        else
        {
            if(currentbutton!=null) {
                currentbutton.setBackgroundColor(getResources().getColor(R.color.white));
                currentbutton.setTextColor(getResources().getColor(R.color.tab_background_selected));
            }
            afb.setBackgroundColor(getResources().getColor(R.color.tab_background_selected));
            afb.setTextColor(getResources().getColor(R.color.white));
            currentbutton=afb;
        }
    }
}
