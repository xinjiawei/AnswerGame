package com.example.answer;

import android.util.Pair;

import java.util.List;

public class Question {

    private String describe;  //question
    private int answer;       //Answer, the answer is the order of the options, starting with 1, for example,
    // in the following option list, there are four options ABCD, then 1 corresponds to choice A
    private List<Pair<String, String>> option; //The answer choices are a List

    public Question() {
    }

    public Question(String describe, int answer, List<Pair<String, String>> option) {
        this.describe = describe;
        this.answer = answer;
        this.option = option;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public List<Pair<String, String>> getOption() {
        return option;
    }

    public void setOption(List<Pair<String, String>> option) {
        this.option = option;
    }
}
