package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import lnmiit.college.counsellingapp.R;

public class activity_answer extends AppCompatActivity {
    TextView txtproblem;
    TextView txtsolution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        txtproblem = findViewById(R.id.txtproblem);
        txtsolution = findViewById(R.id.txtsolution);
        Intent intent = getIntent();
        String problem = intent.getStringExtra("problem");
        String solution = intent.getStringExtra("solution");
        txtsolution.setText(solution);
        txtproblem.setText(problem);
    }
}
