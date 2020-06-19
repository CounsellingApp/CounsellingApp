package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import lnmiit.college.counsellingapp.AnsweredQuestion;
import lnmiit.college.counsellingapp.AskQuestion;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        ff.collection("users").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot queryDocumentSnapshot=task.getResult();
                 type= queryDocumentSnapshot.getDouble("type");
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
//        bottomNavigationView=findViewById(R.id.bottomnavview);
//        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
//        if(type==1){
//            bottomNavigationView.setVisibility(View.VISIBLE);
//            getSupportFragmentManager().beginTransaction().replace(R.id.mainframehandler,new UnansweredQuestion()).commit();
//        }
//
//    }
//    private BottomNavigationView.OnNavigationItemSelectedListener navListner=  new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            Fragment selectedFragment=null;
//            switch(menuItem.getItemId()){
//                case R.id.unansweredQuestion:selectedFragment=new UnansweredQuestion();break;
//                case R.id.answeredQuestion:selectedFragment=new AnsweredQuestion();break;
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.mainframehandler,selectedFragment).commit();
//            return true;
//        }
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
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
                        finish();
                    }
                });
                builder.show();
                break;
            case R.id.home:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.mainframehandler,new MainScreenViewPager());
                fragmentTransaction.commit();
                setTitle("Home");
                break;
        }

        return  true;
    }
}

