package lnmiit.college.counsellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import lnmiit.college.counsellingapp.mn.crawler.rview.Custom_Dialog;
import lnmiit.college.counsellingapp.mn.crawler.rview.Dialog_Tags;
import lnmiit.college.counsellingapp.mn.crawler.rview.MainActivity;
import mehdi.sakout.fancybuttons.FancyButton;

public class AskQuestion extends AppCompatActivity implements View.OnClickListener, Custom_Dialog.Ondialogaction {
    private EditText question, txt_question_title;
    private TextView toolbartext;
    private FancyButton submit;
    private Toolbar toolbar;
    private FancyButton tagmentalhealth, tagphysicalhealth, tagharassment, tagcareer, tagacademic,  tagothers;
    private FancyButton currentbutton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        question=findViewById(R.id.question);
        txt_question_title = findViewById(R.id.txtquestion_title);
        submit = findViewById(R.id.submit);
        tagacademic = findViewById(R.id.tagacademic);
        tagharassment = findViewById(R.id.tagharassment);
        tagcareer = findViewById(R.id.tagcareer);
        tagphysicalhealth = findViewById(R.id.tagphysicalhealth);
        tagmentalhealth = findViewById(R.id.tagmentalhealth);
        tagothers = findViewById(R.id.tagothers);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbartext = toolbar.findViewById(R.id.toolbartitle);
        toolbartext.setText("ASK");
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tagothers.setOnClickListener(this);
        tagmentalhealth.setOnClickListener(this);
        tagphysicalhealth.setOnClickListener(this);
        tagcareer.setOnClickListener(this);
        tagharassment.setOnClickListener(this);
        tagacademic.setOnClickListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(currentbutton==null)
                {
                    Toast.makeText(AskQuestion.this,"PLEASE SELECT A TAG FIRST",Toast.LENGTH_LONG).show();
                }
                else if(!(question.getText()+"").equals("") && !(txt_question_title.getText()+"").equals("") ) {
                    Custom_Dialog custom_dialog = new Custom_Dialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt("code",20);
                    custom_dialog.setArguments(bundle);
                    custom_dialog.setMondialogaction(AskQuestion.this);
                    custom_dialog.show(getSupportFragmentManager(),"askquestion");

                  }
                else {
                    Toast.makeText(AskQuestion.this,"Please enter a TITLE and a QUESTION before submitting",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onClick(View v) {
        clickbutton((FancyButton)v);
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

    @Override
    public void mainactivity() {
        final ProgressDialog progressDialog = ProgressDialog.show(AskQuestion.this,"","Please Wait");
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        Date date = new Date();
        Map<String, String> faculty_answer = new HashMap<>();
        UUID r = UUID.randomUUID();
        Map<String, Object> map = new HashMap<>();
        map.put("question_id", r.toString());
        map.put("question", question.getText().toString());
        map.put("question_title",txt_question_title.getText().toString());
        map.put("asked_by", getIntent().getStringExtra("privacy"));
        map.put("faculty_answers", faculty_answer);
        map.put("tag", currentbutton.getText().toString()+"");
        map.put("date", date);
        map.put("userid", Useremail.email);
        map.put("isanswered", false);
        ff.collection("Questions").document(String.valueOf(r)).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AskQuestion.this, "Your question was submitted succesfully.", Toast.LENGTH_SHORT).show();
                    AskQuestion.this.finish();
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(AskQuestion.this, "Some error occured, please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}