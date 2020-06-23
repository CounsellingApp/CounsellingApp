package lnmiit.college.counsellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import lnmiit.college.counsellingapp.mn.crawler.rview.MainActivity;

public class AskQuestion extends AppCompatActivity {
    private EditText quesion;
    //String userid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        quesion=findViewById(R.id.question);
        Button button=findViewById(R.id.submit);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!(quesion.getText()+"").equals("")) {
                    final ProgressDialog progressDialog = ProgressDialog.show(AskQuestion.this,"","Please Wait");
                    FirebaseFirestore ff = FirebaseFirestore.getInstance();
                    Date date = new Date();
                    Map<String, String> faculty_answer = new HashMap<>();
                    UUID r = UUID.randomUUID();
                    Map<String, Object> map = new HashMap<>();
                    map.put("question_id", r.toString());
                    map.put("question", quesion.getText().toString());
                    map.put("asked_by", getIntent().getStringExtra("privacy"));
                    map.put("faculty_answers", faculty_answer);
                    map.put("tag", "tag");
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
                else {
                    Toast.makeText(AskQuestion.this,"Please enter a question first",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}