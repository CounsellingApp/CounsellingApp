package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import lnmiit.college.counsellingapp.LoginActivity;
import lnmiit.college.counsellingapp.R;
import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;
import mehdi.sakout.fancybuttons.FancyButton;

public class Dialog_Audio_Player extends DialogFragment {
    public interface Audiosubmitted{
        public void trueaudiosubmitted(String answer);
    }
    private Audiosubmitted maudiosubmitted;
    public void setAudiosubmitted(Audiosubmitted maudiosubmitted)
    {
        this.maudiosubmitted = maudiosubmitted;
    }
    private FancyButton btnsubmit, btncancel;
    private VoicePlayerView audio_player;
    private Activity curactivity;

    public Dialog_Audio_Player(Activity curactivity) {
        this.curactivity = curactivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_audio_player,null);
        btncancel = view.findViewById(R.id.btn_cancel);
        btnsubmit = view.findViewById(R.id.btn_submit);
        audio_player = view.findViewById(R.id.audio_player);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Audio will not be submitted",Toast.LENGTH_LONG).show();
                File file = new File(getArguments().getString("Audio"));
                file.delete();
                curactivity.finish();
                startActivity(new Intent(curactivity,MainActivity.class));
                dismiss();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(),"","Uploading Audio, Please wait");


                File file = new File(getArguments().getString("Audio"));
                UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child("Audio_Answers")
                        .child(getArguments().getString("filename")).putFile(Uri.fromFile(file));
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(v.getContext(),"Audio uploaded succesfully",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        maudiosubmitted.trueaudiosubmitted(getArguments().getString("filename"));
                        dismiss();
                    }
                });
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(),"Uploadfailed",Toast.LENGTH_LONG).show();
                        Log.i("uploaderror",e.getMessage());
                        progressDialog.dismiss();
                    }
                });

            }
        });
        audio_player.setShareButtonVisibility(false);
        audio_player.setAudio(getArguments().getString("Audio"));
        builder.setView(view);
        return builder.create();
    }
}
