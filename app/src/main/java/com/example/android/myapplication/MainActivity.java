package com.example.android.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    TextView question;

    ListView answersList;
    ArrayAdapter<String> adapter;
    Server.Question currentQuestion;
    Spinner spinner;
    ArrayAdapter<CharSequence> spinnerAdapter;
    int quesCnt=0;
    int answerdQues=0;
    int correctanswers=0;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.score).setVisibility(View.GONE);
        mp = MediaPlayer.create(MainActivity.this,R.raw.elevator);
         mp.setLooping(true);
        mp.start();
        question = findViewById(R.id.text);
        answersList = findViewById(R.id.answersList);

        spinner = (Spinner) findViewById(R.id.planets_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.CHOICES,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" Selected", Toast.LENGTH_LONG).show();
                switch (position){
                    case 0:
                        quesCnt = 5;
                        break;
                    case 1:
                        quesCnt = 10;
                        break;
                    case 2:
                        quesCnt = 15;
                        break;
                    case 3:
                        quesCnt = 20;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    new ArrayList<String>());


                answersList.setAdapter(adapter);
                answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String chosen = (String) adapterView.getItemAtPosition(position);
                        if (currentQuestion.correctAnswer.equals(chosen)) {
                            Toast.makeText(adapterView.getContext(), "right!", Toast.LENGTH_LONG).show();
                            answerdQues++;
                            correctanswers++;

                        } else {
                            Toast.makeText(adapterView.getContext(), "wrong!", Toast.LENGTH_LONG).show();
                            answerdQues++;

                        }

                        if(answerdQues == quesCnt){//round finished
                            Intent intent = new Intent(getApplicationContext(),results.class);
                            intent.putExtra("SCORE",correctanswers);
                            intent.putExtra("NumOfQuest",quesCnt);
                            startActivity(intent);
                        }
                        getQuestion(view);
                    }
                });


    }


    public void getQuestion(View view) {

        spinner.setVisibility(View.GONE);
        findViewById(R.id.btn).setVisibility(View.GONE);
            Server.getTriviaQuestion(new Server.HandleQuestion() {
                @Override
                public void handleQuestion(Server.Question q) {
                    currentQuestion = q;
                    question.setText(Html.fromHtml(q.question));
                    adapter.clear();
                    for (String a : q.answers) {
                        adapter.add(a);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
    }


}
