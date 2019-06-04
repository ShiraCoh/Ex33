package com.example.android.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        GifImageView gif_end;
        TextView scoreLabel= (TextView)findViewById(R.id.score);
        int score = getIntent().getIntExtra("SCORE",0);
        int numQuest= getIntent().getIntExtra("NumOfQuest",0);
        scoreLabel.setText("SCORE: " + score +"/"+ numQuest );


        if (score == numQuest){
                gif_end = findViewById(R.id.gif);
                gif_end.setVisibility(View.VISIBLE);

        }
    }

    public void Restart(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
