package com.example.answer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GlobalViewModel extends ViewModel {

    public MutableLiveData<Boolean> nextQuestion = new MutableLiveData<>();
    public MutableLiveData<Boolean> finishAnswer = new MutableLiveData<>();
    public MutableLiveData<Integer> finalBonus = new MutableLiveData<>(0);
}
