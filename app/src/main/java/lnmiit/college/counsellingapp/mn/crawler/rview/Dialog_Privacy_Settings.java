package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import lnmiit.college.counsellingapp.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class Dialog_Privacy_Settings extends AppCompatDialogFragment {
    private FancyButton privacy_public, privacy_private;
    private Activity activity;

    public Dialog_Privacy_Settings(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.privacy_of_response_dialog,null);
        privacy_public = view.findViewById(R.id.privacy_public);
        privacy_private = view.findViewById(R.id.privacy_private);
        privacy_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"The response will be private",Toast.LENGTH_LONG).show();
                activity.finish();
                getDialog().dismiss();
            }
        });
        privacy_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"The response will be public",Toast.LENGTH_LONG).show();
                activity.finish();
                getDialog().dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}
