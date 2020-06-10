package lnmiit.college.counsellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lnmiit.college.counsellingapp.mn.crawler.rview.MainActivity;

public class AskQuestion extends AppCompatActivity {
    private EditText quesion;
    String userid=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        quesion=findViewById(R.id.question);
        Button button=findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore ff= FirebaseFirestore.getInstance();
                Date date=new Date();
                Map<String,Object> map=new HashMap<>();
                map.put("question",quesion.getText().toString());
                map.put("date",date);
                map.put("userid",userid);
                ff.collection("FacultyBag").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AskQuestion.this, "done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AskQuestion.this, MainActivity.class));
                        }
                    }
                });
            }
        });
    }
}