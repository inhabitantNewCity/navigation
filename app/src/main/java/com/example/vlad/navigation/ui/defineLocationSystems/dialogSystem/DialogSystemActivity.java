package com.example.vlad.navigation.ui.defineLocationSystems.dialogSystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vlad.navigation.R;

public class DialogSystemActivity extends AppCompatActivity {

    private BuilderNextQuestion builder = BuilderNextQuestion.getInstance();
    private TextView question;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_system);
        question = (TextView) findViewById(R.id.textViewQuestion);
        builder.setTextView(question);
        layout = (LinearLayout) findViewById(R.id.resultLayout);
    }

    public void onClickYes(View view){
        if(builder.execute(true)){
            builder.setResultLayout(layout,this);
            view.setVisibility(View.GONE);
            View buttonNo = findViewById(R.id.buttonNo);
            buttonNo.setVisibility(View.GONE);
        }
    }

    public void onClickNo(View view){
        if(builder.execute(false)){
            builder.setResultLayout(layout,this);
            view.setVisibility(View.GONE);
            View buttonNo = findViewById(R.id.buttonYes);
            buttonNo.setVisibility(View.GONE);
        }
    }
}
