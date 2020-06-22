package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import lnmiit.college.counsellingapp.AnsweredQuestion;
import lnmiit.college.counsellingapp.AskQuestion;
import lnmiit.college.counsellingapp.LoginActivity;
import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestion;
import lnmiit.college.counsellingapp.Useremail;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore ff;
    private Double type;
    private TextView navigationheadertitle, navigationheaderemail;
    private CircleImageView profileimage;
    private MenuItem askmenuitem;
    private Bitmap bitmap;
    private String imageIdentifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        ff.collection("users").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        DocumentSnapshot queryDocumentSnapshot=task.getResult();
                        type= queryDocumentSnapshot.getDouble("type");
                    }
                });

            }
        });
        setTitle("Home");
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigationview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.bringToFront();
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(Useremail.isfaculty) {
            fragmentTransaction.add(R.id.mainframehandler, new MainScreenViewPager());
        }
        else{
            fragmentTransaction.add(R.id.mainframehandler, new mainFragment());
        }
        fragmentTransaction.commit();
        View heaferview = navigationView.getHeaderView(0);
        navigationheadertitle = heaferview.findViewById(R.id.navigationdrawername);
        navigationheaderemail = heaferview.findViewById(R.id.navigationdraweremail);
        navigationheadertitle.setText(Useremail.username+"");
        navigationheaderemail.setText(Useremail.email+"");
        profileimage = heaferview.findViewById(R.id.profile_image);
        if(Useremail.isfaculty) {
            FirebaseStorage.getInstance().getReference().child("faculty_images").child(Useremail.email + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileimage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    profileimage.setImageResource(R.drawable.personimage);
                }
            });

            profileimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseImagefromdevice();
                }
            });
        }
        else{
            Picasso.get().load(Useremail.photouri).into(profileimage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        askmenuitem = menu.findItem(R.id.askquestion);
        if(Useremail.isfaculty)
        {
            askmenuitem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.filters:
                customdialogfragment cdg = new customdialogfragment();
                cdg.show(getSupportFragmentManager(),"Dialog");
                break;
            case R.id.askquestion:
                ask_a_question_dialog ask = new ask_a_question_dialog();
                ask.show(getSupportFragmentManager(),"ask");

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch(item.getItemId())
        {
            case R.id.about:
                Toast.makeText(MainActivity.this,"Implement the About CWPH Activity",Toast.LENGTH_LONG).show();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainframehandler,new about_fragment());
                fragmentTransaction.commit();
                setTitle("About CWPH");
                break;

            case R.id.settings:
                Toast.makeText(MainActivity.this,"Implement the Settings Activity",Toast.LENGTH_LONG).show();
                break;
                case R.id.developers:
                    Toast.makeText(MainActivity.this,"Implement the Developers Activity",Toast.LENGTH_LONG).show();
                    break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure you want to Logout?").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        if(LoginActivity.googleSignInClient!=null)
                        {
                            LoginActivity.googleSignInClient.signOut();
                        }
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                builder.show();
                break;
            case R.id.home:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if(Useremail.isfaculty) {
                    fragmentTransaction.replace(R.id.mainframehandler, new MainScreenViewPager());
                }
                else
                {
                    fragmentTransaction.replace(R.id.mainframehandler, new mainFragment());
                }
                fragmentTransaction.commit();
                setTitle("Home");
                break;
        }

        return  true;
    }
    private void chooseImagefromdevice()
    {
        if(Build.VERSION.SDK_INT <23)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1000);
        }
        else if ( Build.VERSION.SDK_INT>=23)
        {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            }
            else{
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            chooseImagefromdevice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            Uri chosenImageData = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), chosenImageData);
                profileimage.setImageBitmap(bitmap);
                uploadimage();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadimage() {
        if (bitmap != null) {
            // Get the data from an ImageView as bytes
            imageIdentifier = Useremail.email + ".png";
            profileimage.setDrawingCacheEnabled(true);
            profileimage.buildDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child("faculty_images").child(imageIdentifier).putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();// Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(MainActivity.this, "Uploading process was successful", Toast.LENGTH_LONG).show();
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Upload Succesful",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
    }
}

