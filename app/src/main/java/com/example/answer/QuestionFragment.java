package com.example.answer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daquexian.flexiblerichtextview.FlexibleRichTextView;

import java.util.Objects;

public class QuestionFragment extends Fragment {

    private final Question question;
    private GlobalViewModel globalViewModel;

    public QuestionFragment(Question question) {
        this.question = question;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Objects.isNull(question)) {
            System.out.println("null");
        }
        globalViewModel = new ViewModelProvider(requireActivity()).get(GlobalViewModel.class);

//        TextView title = view.findViewById(R.id.title);
//        title.setText(question.getDescribe());

        //--
        FlexibleRichTextView flexibleRichTextView = view.findViewById(R.id.id_rich_tv);
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("$$\\sum_{i=1}^n a_i=0$$,");
        stringBuilder.append(question.getDescribe());
        //stringBuilder.append("\r\n");
        flexibleRichTextView.setText(stringBuilder.toString());
        //--

        //Set the RecyclerView answer option

        RecyclerView optionList = view.findViewById(R.id.option);
        AnswerOptionAdapter answerOptionAdapter = new AnswerOptionAdapter();
        optionList.setAdapter(answerOptionAdapter);
        optionList.setLayoutManager(new LinearLayoutManager(getContext()));
        answerOptionAdapter.setDate(question.getOption());
        answerOptionAdapter.setOnItemClickListener((v, position) -> {
            if (question.getAnswer() == position + 1) {
                AnswerActivity.count++;
                globalViewModel.nextQuestion.setValue(true);
            } else {
                globalViewModel.finishAnswer.setValue(true);
            }
        });
    }
}