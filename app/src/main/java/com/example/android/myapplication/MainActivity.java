package com.example.android.myapplication;

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
    GifImageView gif_end;
    ListView answersList;
    ArrayAdapter<String> adapter;
    Server.Question currentQuestion;
    Button b;
    Spinner spinner;
    ArrayAdapter<CharSequence> spinnerAdapter;
    int quesCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                quesCnt = position;
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
                String chosen = (String)adapterView.getItemAtPosition(position);
                if(currentQuestion.correctAnswer.equals(chosen)){
                    Toast.makeText(adapterView.getContext(), "right!", Toast.LENGTH_LONG).show();
                        getQuestion(view);
                } else{
                    Toast.makeText(adapterView.getContext(), "wrong!", Toast.LENGTH_LONG).show();
                        getQuestion(view);
                }

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

    public void testFunc(View view) {

        b = findViewById(R.id.gifbutton);
        gif_end = findViewById(R.id.gif);
        gif_end.setVisibility(View.VISIBLE);
    }
}
