package com.example.counsellingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class StartActivity extends AppCompatActivity {
    private Button join_as_user;
    private Button join_as_counsellor;
    private Button login;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        join_as_user=findViewById(R.id.JoinAsUser);
        login=findViewById(R.id.login);
        join_as_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
            }
        });
        ll=findViewById(R.id.loginlinearlayout);
        join_as_counsellor=findViewById(R.id.JoinAsCounsellor);
        join_as_counsellor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                join_as_user.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
            }
        });
        join_as_user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //ClipData data = ClipData.newPlainText(textview.getText(),textview.getText());
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(join_as_user);
                    join_as_user.startDrag(null,shadowBuilder,join_as_user,0);
                    return true;
                }
                return false;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
            }
        });
    }
}
