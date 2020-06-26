package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import lnmiit.college.counsellingapp.AskQuestion;
import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.Useremail;
import mehdi.sakout.fancybuttons.FancyButton;

public class ask_a_question_dialog extends AppCompatDialogFragment {
    FancyButton anonymous, reveal_identity, btn_cancel_asking;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ask_a_question_dialog,null);
        builder.setView(view);
        anonymous = view.findViewById(R.id.btn_anonymous);
        reveal_identity = view.findViewById(R.id.btn_reveal_identinty);
        btn_cancel_asking = view.findViewById(R.id.btn_cancel_asking);
        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Identity will be hidden",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
                String uname = "Anonymous User";
                Intent intent = new Intent(v.getContext(), AskQuestion.class);
                intent.putExtra("privacy",uname);
                startActivity(intent);
            }
        });
        reveal_identity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Identity will be revealed",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
                String uname = Useremail.username;
                Intent intent = new Intent(v.getContext(), AskQuestion.class);
                intent.putExtra("privacy",uname);
                startActivity(intent);
            }
        });
        btn_cancel_asking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return builder.create();
    }
}
