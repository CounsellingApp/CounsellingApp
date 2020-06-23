package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.varunjohn1990.audio_record_view.AudioRecordView;
import com.varunjohn1990.audio_record_view.AudioRecordView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import lnmiit.college.counsellingapp.AskQuestion;
import lnmiit.college.counsellingapp.LoginActivity;
import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.Useremail;

public class Respond_To_A_Question extends AppCompatActivity {
    private TextView txt_reply_question, txt_reply_author, txt_reply_tags;
    private EditText txt_reply_answer;
    private Button btn_post_answer;
    private ImageButton btn_record_message;
    private Toolbar toolbar;
    private LinearLayout postlinearlayout;
    private LinearLayout mainlinearlayout;
    private String mypath;
    private MediaRecorder recorder;
    private AudioRecordView audioRecordView;
    FirebaseFirestore ff;
    Map<String,String> answersmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond__to__a__question);
        txt_reply_answer = findViewById(R.id.txt_reply_answer);
        txt_reply_author = findViewById(R.id.txt_reply_author);
        txt_reply_question = findViewById(R.id.txt_reply_question);
        txt_reply_tags = findViewById(R.id.txt_reply_tags);
        btn_post_answer = findViewById(R.id.btn_post_answer);
        toolbar = findViewById(R.id.toolbar);
        mainlinearlayout = findViewById(R.id.mainlinearlayout);
        postlinearlayout = findViewById(R.id.postlinearlayout);

        ff = FirebaseFirestore.getInstance();
        mypath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mypath.concat("/lavi.3gp");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("REPLY");
        txt_reply_question.setText(getIntent().getStringExtra("question"));
        txt_reply_tags.setText(getIntent().getStringExtra("tags"));
        txt_reply_author.setText(getIntent().getStringExtra("author"));

        final Dialog_Privacy_Settings dialog_privacy_settings = new Dialog_Privacy_Settings(Respond_To_A_Question.this);
        btn_post_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String answer = txt_reply_answer.getText().toString()+"";
                if(answer=="") {
                    //dialog_privacy_settings.show(getSupportFragmentManager(), "sure");
                    Toast.makeText(Respond_To_A_Question.this,"Please enter a response",Toast.LENGTH_LONG).show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Respond_To_A_Question.this);
                    builder.setMessage("Are you sure you want to post this answer?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                                 final ProgressDialog progressDialog = ProgressDialog.show(Respond_To_A_Question.this,"","Please Wait");
                                 ff.collection("Questions").document(getIntent().getStringExtra("questionid")).update("isanswered",true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {

                                     }
                                 });

                            answersmap = (Map<String,String>) getIntent().getSerializableExtra("faculty_answers");
                            answersmap.put(Useremail.email,answer);
                            ff.collection("Questions").document(getIntent().getStringExtra("questionid")).update("faculty_answers",answersmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Respond_To_A_Question.this,"Your response has been submitted succesfully",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    progressDialog.dismiss();
                                    startActivity(new Intent(Respond_To_A_Question.this,MainActivity.class));
                                    finish();
                                }
                            });

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });
        audioRecordView = new AudioRecordView();
        audioRecordView.initView(postlinearlayout);
        audioRecordView.setAudioRecordButtonImage(R.drawable.mic);
        audioRecordView.hideAttachmentOptionView();
        audioRecordView.removeAttachmentOptionAnimation(false);
        audioRecordView.showAttachmentIcon(false);
        audioRecordView.showCameraIcon(false);
        audioRecordView.showEmojiIcon(false);
        audioRecordView.getMessageView().setVisibility(View.GONE);
        audioRecordView.getSendView().setVisibility(View.GONE);
        audioRecordView.setRecordingListener(new AudioRecordView.RecordingListener() {
            @Override
            public void onRecordingStarted() {
                startRecording();
            }

            @Override
            public void onRecordingLocked() {

            }

            @Override
            public void onRecordingCompleted() {
                audioRecordView.getMessageView().setVisibility(View.GONE);
                stopRecording();
            }

            @Override
            public void onRecordingCanceled() {
                stopRecording();
                audioRecordView.getMessageView().setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Respond_To_A_Question.this,MainActivity.class));
                finish();
                return true;
            default: super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(mypath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.i("RecordView", "prepare() failed");
        }


    }

    private void stopRecording() {
        try {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        catch (Exception e)
        {
            Log.i("RecordView", "stop failed");
        }
    }

}
