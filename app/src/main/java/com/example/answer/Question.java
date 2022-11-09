package com.example.answer;

import android.util.Pair;

import java.util.List;

public class Question {

    private String describe;  //问题
    private int answer;       //答案，答案为选项顺序，1开始，例如下面选项option列表中，有4个选项ABCD，那么1就对应A选项
    private List<Pair<String, String>> option; //答案选项，是一个List集合

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
