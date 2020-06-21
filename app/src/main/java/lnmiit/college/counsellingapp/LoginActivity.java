package lnmiit.college.counsellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import lnmiit.college.counsellingapp.mn.crawler.rview.MainActivity;

import static android.widget.Toast.*;

public class LoginActivity extends AppCompatActivity {

    private EditText student_username,student_password,faculty_username,faculty_password,username,password,type;

    private FirebaseAuth mauth;
    private SharedPreferences prefs = null;
    private ImageButton login_as_student,login_as_faculty,student_login,faculty_login,signin;
    private TextView signup;
    private LinearLayout student_layout,faculty_layout,signup_layout,choice_layout;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore ff;
    private ImageView welcomeimage;
    private TextView welcometext;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        mauth=FirebaseAuth.getInstance();

        welcomeimage = findViewById(R.id.welcomeimage);
        welcometext = findViewById(R.id.welcometext);

        choice_layout=findViewById(R.id.choiceLinearLayout);
        signup_layout=findViewById(R.id.signupLayout);
        student_layout=findViewById(R.id.studentLinearLayout);
        faculty_layout=findViewById(R.id.facultyLinearLayout);

        student_username=findViewById(R.id.studentUsername);
        faculty_username=findViewById(R.id.facultyUsername);
        student_password=findViewById(R.id.studentPassword);
        faculty_password=findViewById(R.id.facultyPassword);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        type=findViewById(R.id.type);


        signup=findViewById(R.id.signup);
        login_as_student=findViewById(R.id.loginAsStudent);
        login_as_faculty=findViewById(R.id.loginAsFaculty);

        signin=findViewById(R.id.signin);
        faculty_login=findViewById(R.id.facultyLogin);
        student_login=findViewById(R.id.studentLogin);

        login_as_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_layout.setVisibility(View.VISIBLE);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
//                params.topMargin = 500;
//                params.bottomMargin=200;
                //student_layout.setLayoutParams(params);
                choice_layout.setVisibility(View.GONE);
                welcomeimage.setVisibility(View.GONE);
                welcometext.setVisibility(View.GONE);

            }
        });
        login_as_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faculty_layout.setVisibility(View.VISIBLE);
                choice_layout.setVisibility(View.GONE);
                welcomeimage.setVisibility(View.GONE);
                welcometext.setVisibility(View.GONE);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_layout.setVisibility(View.VISIBLE);
                choice_layout.setVisibility(View.GONE);
                welcomeimage.setVisibility(View.GONE);
                welcometext.setVisibility(View.GONE);
            }
        });
        prefs = getSharedPreferences("lnmiit.college.counsellingapp",MODE_PRIVATE);
        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
                mauth.signInWithEmailAndPassword(student_username.getText().toString(),student_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "signin successful", LENGTH_LONG).show();
                            Useremail.email = student_username.getText().toString();
                            Useremail.isfaculty=false;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "signin failed", LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
        faculty_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
                mauth.signInWithEmailAndPassword(faculty_username.getText().toString(),faculty_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseUser=firebaseAuth.getCurrentUser();
                            ff.collection("users").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot queryDocumentSnapshot=task.getResult();
                                    Double type= queryDocumentSnapshot.getDouble("type");
                                    if(type==1){
                                        Toast.makeText(LoginActivity.this, "signin successful", LENGTH_LONG).show();
                                        Useremail.email= faculty_username.getText().toString();
                                        Useremail.isfaculty=true;
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                        finish();
                                    }
                                    else{
                                        firebaseUser=null;
                                        Toast.makeText(LoginActivity.this, "signin failed", LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "signin failed", LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.createUserWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Map<String,Object>map=new HashMap<>();
                            if(type.getText().toString().equals("faculty")){
                                map.put("type",1);
                            }
                            else{
                                map.put("type",2);
                            }
                            firebaseUser=firebaseAuth.getCurrentUser();
                            ff.collection("users").document(firebaseUser.getUid()).set(map);
                            Toast.makeText(LoginActivity.this,"done", LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"error", LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefs.getBoolean("firstrun",true)){
            Intent intent = new Intent(LoginActivity.this, Introduction_Activity.class);
            startActivity(intent);
            finish();
            prefs.edit().putBoolean("firstrun",false).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(choice_layout.getVisibility()==View.GONE)

        {
            choice_layout.setVisibility(View.VISIBLE);
            welcomeimage.setVisibility(View.VISIBLE);
            welcometext.setVisibility(View.VISIBLE);
            student_layout.setVisibility(View.GONE);
            faculty_layout.setVisibility(View.GONE);
            signup_layout.setVisibility(View.GONE);
        }
        else
        {
            super.onBackPressed();
        }
    }
}
