package com.example.answer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daquexian.flexiblerichtextview.FlexibleRichTextView;

import org.scilab.forge.jlatexmath.core.AjLatexMath;

import io.github.kbiakov.codeview.classifier.CodeProcessor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init flexiblerichtextview
        AjLatexMath.init(this);
        //code highlight
        CodeProcessor.init(this);

        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Button start = findViewById(R.id.start);
        start.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
            startActivity(intent);
        });

        FlexibleRichTextView flexibleRichTextView = findViewById(R.id.id_rich_tv);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("$$\\begin{bmatrix}" +
                "M&A&T&H" +
                "\\\\A&A&&\\" +
                "\\T&&T&\\" +
                        "\\H&&&H" +
                        "\\\\\\end{bmatrix}$$");

        flexibleRichTextView.setText(stringBuilder.toString());


    }
}