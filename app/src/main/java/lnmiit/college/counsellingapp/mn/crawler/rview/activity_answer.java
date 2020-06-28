package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lnmiit.college.counsellingapp.R;

public class activity_answer extends AppCompatActivity {
    private RecyclerView answersrecyclerview;
    private int position=-2;
    private List<AnswerModel> mylist;
    private String aca_question;
    private String aca_description;
    private String aca_auhor;
    private String aca_tags;
    private Toolbar toolbar;
    private String noa;
    private String question_id;
    private Map<String,String> answers_map;
    private TextView tootlbartitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mylist = (List<AnswerModel>) getIntent().getSerializableExtra("mylist");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tootlbartitle = toolbar.findViewById(R.id.toolbartitle);
        tootlbartitle.setText("RESPONSES");
        setTitle("");
        aca_question = getIntent().getStringExtra("question");
        aca_description = getIntent().getStringExtra("description");
        aca_auhor = getIntent().getStringExtra("author");
        aca_tags = getIntent().getStringExtra("tags");
        noa = getIntent().getStringExtra("noa");
        question_id = getIntent().getStringExtra("id");
        answers_map = (Map<String, String>) getIntent().getSerializableExtra("map");
        answersrecyclerview = findViewById(R.id.specific_answer_recycler_view);
        position = getIntent().getIntExtra("position",-1);
        Answers_adapter adapter = new Answers_adapter(position, activity_answer.this,aca_question,aca_description,aca_auhor,aca_tags,noa,getSupportFragmentManager(),question_id,answers_map);
        adapter.Setmainlist(mylist);
        answersrecyclerview.setAdapter(adapter);
        answersrecyclerview.setLayoutManager(new LinearLayoutManager(activity_answer.this));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            startActivity(new Intent(activity_answer.this,MainActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(activity_answer.this,MainActivity.class));
        finish();
    }
}
