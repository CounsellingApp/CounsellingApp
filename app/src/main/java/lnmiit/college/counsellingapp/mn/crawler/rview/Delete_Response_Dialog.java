package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import lnmiit.college.counsellingapp.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class Delete_Response_Dialog extends AppCompatDialogFragment {
    private FancyButton delete_response_yes, delete_response_no;
    private TextView dialogtext;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_response_dialog,null);
        delete_response_no = view.findViewById(R.id.btn_delete_response_no);
        delete_response_yes = view.findViewById(R.id.btn_delete_response_yes);
        dialogtext = view.findViewById(R.id.dialogtext);
        builder.setView(view);
        delete_response_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Question will be deleted permanently",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        delete_response_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Question will not be deleted",Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
        return builder.create();
    }

    public TextView getDialogtext() {
        return dialogtext;
    }

    public FancyButton getDelete_response_yes() {
        return delete_response_yes;
    }

    public FancyButton getDelete_response_no() {
        return delete_response_no;
    }
}
