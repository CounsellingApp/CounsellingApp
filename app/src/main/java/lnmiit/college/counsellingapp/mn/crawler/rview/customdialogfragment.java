package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import lnmiit.college.counsellingapp.R;

public class customdialogfragment extends AppCompatDialogFragment {

    CheckBox cbgeneral, cbmusicproduction, cbfootball, cbpersonal;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter,null);
        builder.setView(view).setTitle("Select a Category").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        cbgeneral = view.findViewById(R.id.cbgenereal);
        cbmusicproduction = view.findViewById(R.id.cbmusicproduction);
        cbfootball = view.findViewById(R.id.cbfootball);
        cbpersonal = view.findViewById(R.id.cbpersonal);
        return builder.create();
    }
}
