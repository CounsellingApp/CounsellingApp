package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import lnmiit.college.counsellingapp.R;

public class Respond_To_A_Question extends AppCompatActivity {
    private TextView txt_reply_question, txt_reply_author, txt_reply_tags;
    private EditText txt_reply_answer;
    private Button btn_post_answer;
    private ImageButton btn_record_message;
    private Toolbar toolbar;

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
                dialog_privacy_settings.show(getSupportFragmentManager(),"sure");

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
