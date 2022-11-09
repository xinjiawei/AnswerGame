package com.example.answer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AnswerActivity extends AppCompatActivity {

    public List<Question> questionList = new ArrayList<>();
    private GlobalViewModel globalViewModel;

    public static int count = 0;
    public static int bonus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        globalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        // ==========================================================================================
        // 问题在这里设置，每一个问题是一个Question对象，只需要new Question()填充数据， 然后
        // 添加到questionList(ArrayList)
        // 即可，问题可以不为11个


        String[] arrayDescribe = {"01","02","03","04","05","06","07","08","09","10","11"};
        int[] targetSelect = {1,1,1,1,1,1,1,1,1,1,1};
        String[][] Answers={{"00","01","00","01"},{"10","11","00","01"},{"20","21","00","01"}};

        for (int i = 0; i < 2; i++) {
            Question question = new Question();
            ArrayList<Pair<String, String>> list = new ArrayList<>();
            list.add(new Pair<>("A", Answers[i][0]));
            list.add(new Pair<>("B", Answers[i][1]));
            list.add(new Pair<>("C", Answers[i][2]));
            list.add(new Pair<>("D", Answers[i][3]));
            question.setDescribe(arrayDescribe[i]);
            question.setAnswer(targetSelect[i]);
            question.setOption(list);
            questionList.add(question);
        }

//        for (int i = 0; i < 11; i++) {
//            ArrayList<Pair<String, String>> pairs = new ArrayList<>();
//            pairs.add(new Pair<>("A", "选项"));
//            pairs.add(new Pair<>("B", "选项"));
//            pairs.add(new Pair<>("C", "选项"));
//            pairs.add(new Pair<>("D", "选项"));
//            pairs.add(new Pair<>("E", "选项"));
//            question.setDescribe(arrayDescribe[i]);
//            question.setAnswer(2);
//            question.setOption(pairs);
//            questionList.add(question);
//        }


        // ==========================================================================================

        Toolbar toolbar = findViewById(R.id.toolbar);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), questionList);
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(questionList.size());
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                compute();
                String title = "$" + bonus + " (" + (position + 1) + "/" + (questionList.size()) + ")";
                toolbar.setTitle(title);
                if (position == adapter.getItemCount() - 1) {
                    toolbar.setTitle("The answer to end!");
                }
            }
        });

        globalViewModel.nextQuestion.observe(this, isNext -> {
            if (isNext && viewPager.getCurrentItem() < adapter.getItemCount()) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem + 1);
            }
        });
        globalViewModel.finishAnswer.observe(this, isFinish -> {
            if (isFinish) {
                viewPager.setCurrentItem(adapter.getItemCount());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count = 0;
        bonus = 0;
        globalViewModel.finalBonus.setValue(0);
    }

    private void compute() {
        System.out.println(count);
        switch (count) {
            case 1:
                bonus = 1000;
                globalViewModel.finalBonus.setValue(1000);
                break;
            case 2:
                bonus = 2000;
                break;
            case 3:
                bonus = 4000;
                break;
            case 4:
                bonus = 8000;
                break;
            case 5:
                bonus = 16000;
                break;
            case 6:
                bonus = 32000;
                globalViewModel.finalBonus.setValue(32000);
                break;
            case 7:
                bonus = 64000;
                break;
            case 8:
                bonus = 125000;
                break;
            case 9:
                bonus = 250000;
                break;
            case 10:
                bonus = 500000;
                break;
            case 11:
                bonus = 1000000;
                globalViewModel.finalBonus.setValue(1000000);
                break;
            default:
                break;
        }
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(
                @NonNull FragmentManager fragmentManager,
                @NonNull Lifecycle lifecycle,
                List<Question> questionList
        ) {
            super(fragmentManager, lifecycle);
            for (Question question : questionList) {
                fragmentList.add(new QuestionFragment(question));
            }
            fragmentList.add(new BonusFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}