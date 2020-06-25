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
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Stopwatch;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;
import lnmiit.college.counsellingapp.AskQuestion;
import lnmiit.college.counsellingapp.LoginActivity;
import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.Useremail;

public class Respond_To_A_Question extends AppCompatActivity implements Dialog_Audio_Player.Audiosubmitted, Custom_Dialog.Ondialogaction {
    private TextView txt_reply_question, txt_reply_author, txt_reply_tags;
    private EditText txt_reply_answer;
    private Button btn_post_answer;
    private TextView toolbartext;
    private Toolbar toolbar;
    private ImageButton btnrecord;
    private LinearLayout mainlinearlayout;
    private String mypath=null;
    private MediaRecorder recorder;
    private LinearLayout sildetocancel;
    private Chronometer txt_timer;
    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    final int REFRESH_RATE = 100;
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
        txt_timer = findViewById(R.id.txt_timer);
        toolbar = findViewById(R.id.toolbar);
        mainlinearlayout = findViewById(R.id.mainlinearlayout);
        btnrecord = findViewById(R.id.btnrecord);
        sildetocancel = findViewById(R.id.slidetocancel);
        ff = FirebaseFirestore.getInstance();
        mypath = Environment.getExternalStorageDirectory().getAbsolutePath();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartext = toolbar.findViewById(R.id.toolbartitle);
        toolbartext.setText("REPLY");
        setTitle("");
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
                    Custom_Dialog custom_dialog = new Custom_Dialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt("code",30);
                    custom_dialog.setArguments(bundle);
                    custom_dialog.setMondialogaction(Respond_To_A_Question.this);
                    custom_dialog.show(getSupportFragmentManager(),"respondtoaquestion");
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Respond_To_A_Question.this);
//                    builder.setMessage("Are you sure you want to post this answer?");
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(final DialogInterface dialog, int which) {
//                                 final ProgressDialog progressDialog = ProgressDialog.show(Respond_To_A_Question.this,"","Please Wait");
//                                 ff.collection("Questions").document(getIntent().getStringExtra("questionid")).update("isanswered",true).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                     @Override
//                                     public void onSuccess(Void aVoid) {
//
//                                     }
//                                 });
//
//                            answersmap = (Map<String,String>) getIntent().getSerializableExtra("faculty_answers");
//                            answersmap.put(Useremail.email,answer);
//                            ff.collection("Questions").document(getIntent().getStringExtra("questionid")).update("faculty_answers",answersmap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(Respond_To_A_Question.this,"Your response has been submitted succesfully",Toast.LENGTH_LONG).show();
//                                    dialog.dismiss();
//                                    progressDialog.dismiss();
//                                    startActivity(new Intent(Respond_To_A_Question.this,MainActivity.class));
//                                    finish();
//                                }
//                            });
//
//                        }
//                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.create().show();
                }
            }
        });

        btnrecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    btnrecord.setImageResource(R.drawable.mic_clicked);
                    //btnrecord.setBackgroundColor(getResources().getColor(R.color.tab_background_selected));
                    sildetocancel.setVisibility(View.VISIBLE);
                    btn_post_answer.setVisibility(View.GONE);
                    String folfer_main = "CWPH_LNMIIT";
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),folfer_main);

                    if(!file.exists())
                    {
                        file.mkdirs();
                    }
                    mypath+=("/CWPH_LNMIIT/"+Useremail.email+getIntent().getStringExtra("questionid")+".mp4");
                    startchronometer();
                    startRecording();
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    btnrecord.setImageResource(R.drawable.mic);
                    //btnrecord.setBackgroundColor(getResources().getColor(R.color.white));
                    sildetocancel.setVisibility(View.GONE);
                    btn_post_answer.setVisibility(View.VISIBLE);
                    stopchronometer();
                    stopRecording();
                    Bundle bundle= new Bundle();
                    bundle.putString("Audio",mypath);
                    bundle.putString("filename",Useremail.email+getIntent().getStringExtra("questionid")+".mp4");
                    Dialog_Audio_Player dialog_audio_player = new Dialog_Audio_Player(Respond_To_A_Question.this);
                    dialog_audio_player.setArguments(bundle);
                    dialog_audio_player.setAudiosubmitted(Respond_To_A_Question.this);
                    dialog_audio_player.show(getSupportFragmentManager(),"dialog_audio_player");
                }

                return false;
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
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(mypath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.i("lakshay", e.getMessage());
        }


    }

    private void stopRecording() {

            try {
                recorder.stop();
                recorder.release();
                recorder = null;
            }
            catch (IllegalStateException e)
            {
                Toast.makeText(Respond_To_A_Question.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }


    }
    public void startchronometer()
    {
        txt_timer.setBase(SystemClock.elapsedRealtime());
        txt_timer.start();
    }
    public void stopchronometer()
    {
        txt_timer.stop();
    }

    @Override
    public void trueaudiosubmitted(String answer) {
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
                progressDialog.dismiss();
                startActivity(new Intent(Respond_To_A_Question.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void mainactivity() {
        final String answer = txt_reply_answer.getText().toString()+"";
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
                progressDialog.dismiss();
                startActivity(new Intent(Respond_To_A_Question.this,MainActivity.class));
                finish();
            }
        });
    }
}
