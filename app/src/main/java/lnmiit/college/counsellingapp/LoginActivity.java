package lnmiit.college.counsellingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import lnmiit.college.counsellingapp.mn.crawler.rview.MainActivity;
import lnmiit.college.counsellingapp.mn.crawler.rview.Showfancytoasr;

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
    private SignInButton googlesignInButton;
    public static GoogleSignInClient googleSignInClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(GoogleSignIn.getLastSignedInAccount(LoginActivity.this) != null) {
            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
            final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            Useremail.email = account.getEmail();
            Useremail.username = account.getDisplayName();
            if(account.getEmail().equals("lakshay.bhagtani@gmail.com")||account.getEmail().equals("me.govind23@gmail.com")||account.getEmail().equals("ishanb129@gmail.com")||account.getEmail().equals("mehak.sin58@gmail.com")||account.getEmail().equals("shillubhagtani@gmail.com")||account.getEmail().equals("wewillrecreateindia@gmail.com"))
            {
                Useremail.isfaculty = true;
                FirebaseFirestore.getInstance().collection("Faculty_Bag").document(Useremail.email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {

                            DocumentSnapshot dsnap = task.getResult();
                            if(dsnap.exists())
                            {
                                Useremail.photouri = Uri.parse(dsnap.get("photo_uri").toString());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }
                            else
                            {
//                                Useremail.photouri = account.getPhotoUrl();
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                                progressDialog.dismiss();
                            }
                        }
                    }
                });

            }
            else
            {
                Useremail.isfaculty = false;
                Useremail.photouri = account.getPhotoUrl();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        ff=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        mauth=FirebaseAuth.getInstance();
        googlesignInButton = findViewById(R.id.googlesignin);
        welcomeimage = findViewById(R.id.welcomeimage);
        welcometext = findViewById(R.id.welcometext);

        choice_layout=findViewById(R.id.choiceLinearLayout);

        faculty_layout=findViewById(R.id.facultyLinearLayout);


        faculty_username=findViewById(R.id.facultyUsername);

        faculty_password=findViewById(R.id.facultyPassword);




        login_as_faculty=findViewById(R.id.loginAsFaculty);


        faculty_login=findViewById(R.id.facultyLogin);



        login_as_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                faculty_layout.setVisibility(View.VISIBLE);
//                choice_layout.setVisibility(View.GONE);
//                welcomeimage.setVisibility(View.GONE);
//                welcometext.setVisibility(View.GONE);
                googlesinin();
            }
        });

        prefs = getSharedPreferences("lnmiit.college.counsellingapp",MODE_PRIVATE);

        faculty_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlesinin();
//                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
//                mauth.signInWithEmailAndPassword(faculty_username.getText().toString(),faculty_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            firebaseUser=firebaseAuth.getCurrentUser();
//                            ff.collection("users").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    DocumentSnapshot queryDocumentSnapshot=task.getResult();
//                                    Double type= queryDocumentSnapshot.getDouble("type");
//                                    if(type==1){
//                                        Showfancytoasr.show(LoginActivity.this,"Login successful");
//                                        Useremail.email= faculty_username.getText().toString();
//                                        Useremail.isfaculty=true;
//                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        startActivity(intent);
//                                        progressDialog.dismiss();
//                                        finish();
//                                    }
//                                    else{
//                                        firebaseUser=null;
//                                        Showfancytoasr.show(LoginActivity.this,"Login failed, please try again with the correct credentials");
//                                        progressDialog.dismiss();
//                                    }
//                                }
//                            });
//
//                        }
//                        else{
//                            Showfancytoasr.show(LoginActivity.this,"Login failed, please try again with the correct credentials");
//                            progressDialog.dismiss();
//                        }
//                    }
//                });
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,gso);
        googlesignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlesinin();
            }
        });

    }


    public void googlesinin(){
        Intent googleintent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleintent,500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==500)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
            try {

                final GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                //Toast.makeText(LoginActivity.this,"Signed in succesfully",LENGTH_LONG).show();
                AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
                mauth.signInWithCredential(authCredential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //Toast.makeText(LoginActivity.this,"Firebase Signin success",LENGTH_LONG).show();
                            firebaseUser = mauth.getCurrentUser();
                            final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                            String checkablemail = account.getEmail();
                            String[] parts = checkablemail.split("@");
                            if(parts[1].equals("lnmiit.ac.in")) {
                                Useremail.email = account.getEmail();
                                Useremail.isfaculty = false;
                                Useremail.photouri = account.getPhotoUrl();
                                Useremail.username = account.getDisplayName();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                progressDialog.dismiss();
                                startActivity(intent);
                                finish();
                            }
                            else if(checkablemail.equals("lakshay.bhagtani@gmail.com")||checkablemail.equals("me.govind23@gmail.com")||account.getEmail().equals("ishanb129@gmail.com")||account.getEmail().equals("ishanb129@@gmail.com")||account.getEmail().equals("mehak.sin58@gmail.com")||account.getEmail().equals("shillubhagtani@gmail.com")||account.getEmail().equals("wewillrecreateindia@gmail.com")) {
                                final FirebaseFirestore currentff = FirebaseFirestore.getInstance();
                                Useremail.email = account.getEmail();
                                Useremail.isfaculty = true;
                                Useremail.username = account.getDisplayName();
                                DocumentReference dref = currentff.collection("Faculty_Bag").document(Useremail.email);
                                dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            DocumentSnapshot dsnap = task.getResult();
                                            if(dsnap.exists())
                                            {
                                                Useremail.photouri = Uri.parse(dsnap.get("photo_uri").toString());
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                progressDialog.dismiss();
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                    FirebaseFirestore thisff = FirebaseFirestore.getInstance();
                                                    Map<String,Object> currmap = new HashMap<>();
                                                    currmap.put("email",Useremail.email);
                                                    currmap.put("username",Useremail.username);
                                                    currmap.put("photo_uri",account.getPhotoUrl().toString());
                                                    thisff.collection("Faculty_Bag").document(Useremail.email).set(currmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Useremail.photouri = account.getPhotoUrl();
                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                            progressDialog.dismiss();
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    });


                                            }
                                        }
                                    }
                                });



                            }
                            else
                            {
                                googleSignInClient.signOut();
                                Showfancytoasr.show(LoginActivity.this,"Please use your college email address");
                                progressDialog.dismiss();
                            }

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Showfancytoasr.show(LoginActivity.this,"Signin failed, please try again");
                        }
                    }
                });
            }
            catch (ApiException e)
            {
                //Showfancytoasr.show(LoginActivity.this,"Following error occured : "+e.getMessage());
                Log.i("Excaption",e.getMessage());
                progressDialog.dismiss();

            }
        }
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
