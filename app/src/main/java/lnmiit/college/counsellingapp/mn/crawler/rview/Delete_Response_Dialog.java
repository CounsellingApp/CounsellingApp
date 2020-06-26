package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import lnmiit.college.counsellingapp.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class Delete_Response_Dialog extends AppCompatDialogFragment {

    public interface OnInputSelected {
        void sendInput(boolean wasquestiondeleted,int position);
    }
    public OnInputSelected minputselected;
    public void setOnInputSelected(final OnInputSelected onInputSelected)
    {
        minputselected = onInputSelected;
    }
    private FancyButton delete_response_yes, delete_response_no;
    private TextView dialogtext;
    private FirebaseFirestore ff;
    private UnansweredRecyclerViewAdapter adapter;
    public Delete_Response_Dialog() {
        ff = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_response_dialog,null);
        delete_response_no = view.findViewById(R.id.btn_delete_response_no);
        delete_response_yes = view.findViewById(R.id.btn_delete_response_yes);
        dialogtext = view.findViewById(R.id.dialogtext);
        final Bundle bundle = getArguments();
        builder.setView(view);
        delete_response_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showfancytoasr.show(v.getContext(),"Question will be deleted permanently");
                ff.collection("Questions").document(bundle.getString("did")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        minputselected.sendInput(true,bundle.getInt("position"));

                        getDialog().dismiss();
                    }
                });

            }
        });
        delete_response_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                minputselected.sendInput(false,bundle.getInt("position"));
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
