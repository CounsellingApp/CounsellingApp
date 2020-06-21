package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.Useremail;

public class Respond_To_A_Question extends AppCompatActivity {
    private TextView txt_reply_question, txt_reply_author, txt_reply_tags;
    private EditText txt_reply_answer;
    private Button btn_post_answer;
    private ImageButton btn_record_message;
    private Toolbar toolbar;
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
        ff = FirebaseFirestore.getInstance();

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
                                 ff.collection("Questions").document(getIntent().getStringExtra("questionid")).update("isanswered",true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                         Toast.makeText(Respond_To_A_Question.this,"Boolean updated succesfully",Toast.LENGTH_LONG).show();
                                     }
                                 });

                            answersmap = (Map<String,String>) getIntent().getSerializableExtra("faculty_answers");
                            answersmap.put(Useremail.email,answer);
                            ff.collection("Questions").document(getIntent().getStringExtra("questionid")).update("faculty_answers",answersmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Respond_To_A_Question.this,"Map updated succesfully",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
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

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default: super.onOptionsItemSelected(item);
        }
        return true;
    }

}
