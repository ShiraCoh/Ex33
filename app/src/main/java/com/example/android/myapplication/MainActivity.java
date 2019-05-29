package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView question;
    ListView answersList;
    ArrayAdapter<String> adapter;
    Server.Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.text);
        answersList = findViewById(R.id.answersList);
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
                } else{
                    Toast.makeText(adapterView.getContext(), "wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void getQuestion(View view) {
        Server.getTriviaQuestion(new Server.HandleQuestion() {
            @Override
            public void handleQuestion(Server.Question q) {
                currentQuestion = q;
                question.setText(Html.fromHtml(q.question));
                adapter.clear();
                for(String a: q.answers){
                    adapter.add(a);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
