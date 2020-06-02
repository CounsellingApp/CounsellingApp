package lnmiit.college.counsellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import lnmiit.college.counsellingapp.mn.crawler.rview.MainActivity;

import static android.widget.Toast.*;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private FirebaseAuth mauth;
    private ImageButton login;
    private SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mauth=FirebaseAuth.getInstance();
        username=findViewById(R.id.Username);
        password=findViewById(R.id.Password);
        login=findViewById(R.id.Login);
        prefs = getSharedPreferences("lnmiit.college.counsellingapp",MODE_PRIVATE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
                mauth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "signin successful", LENGTH_LONG).show();
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
}
