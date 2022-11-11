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

import org.scilab.forge.jlatexmath.core.AjLatexMath;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.kbiakov.codeview.classifier.CodeProcessor;

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

        //init flexiblerichtextview
        AjLatexMath.init(this);
        //code highlight
        CodeProcessor.init(this);

        // ========================================================================


        String[] arrayDescribe = {
                "1+1=?" ,
                "7/0=? what\r is\r the\r result\r of\r in\r JAVA",
                "$$f(n) = \\begin{cases} \\frac{n}{2}-1, & \\text{if } n\\text{ is even} \\\\ 3n+1, & \" +\n" +
                        "                \"\\text{if } n\\text{ is odd} \\end{cases}$$, When $$n = ff(2)$$, " +
                        "what\r is\r the\r result\r of\r",
                "$$f(x+4)=\\frac{x+30}{x} \\sqrt{x+4}$$, When $$x = 64$$, " +
                        "what\r is\r the\r result\r of\r",
                "$$ \\[f(x,y,z) = 3y^2 z \\left( 3 + \\frac{7x+5}{2 + y^2} \\right)\\] $$, then $$f(1,2,3)=?$$",
                "$$y=x^2+2x+3,$$ What\r is\r the\r result\r of\r$$\\left. \\frac{du}{dx} \\right|_{x=0}.$$",
                "$$ P(x|c)=\\frac{P(c|x)\\cdot P(x)}{P(x)} $$, is it right?",
                "$$ \\sum_{i=1}^n \\frac{i(i+1)}2 $$, What\r is\r the\r result\r when\r $$n$$ equal\r $$3$$ ?",
                "$$ f(x)=\\int_{-\\infty}^x e^{-t^2}dt $$, What\r is\r the\r result\r of\r in\r JAVA",
                "$$ \\cos 2\\theta  = \\cos^2 \\theta - \\sin^2 \\theta = 1 - 2 \\cos^2 \\theta. $$, is it right?",
                "$$A = \\det\\begin{bmatrix}" +
                "0&\\cdots&0&1&3&6" +
                "\\\\\\vdots&\\ddots&\\vdots&3&8&6\\" +
                "\\0&\\cdots&0&5&6&9" +
                        "\\\\1&2&3&0&\\cdots&0" +
                        "\\\\2&3&0&\\vdots&\\ddots&\\vdots\\" +
                        "\\3&4&1&0&\\cdots&0" +
                        "\\\\\\end{bmatrix}$$, what is $$|A|$$"
        };
        //int ss = 7 / 0;

        int[] targetSelect = {2,2,3,3,4,1,1,3,2,2,4};
        String[][] Answers={
                {"1","2","3","4"},
                {"7","overflow","expection","error"},
                {"0","1","-2","2"},
                {"≈8.25","≈9.72","9","7.5"},
                {"128","216","324","180"},
                {"2","3","5","0"},
                {"T","F","",""},
                {"12","18","9","6"},
                {"Math.pow(3.14, 2.0)/2","Math.sqrt(3.14)/2","Math.abs(3.14, 2.0)/2","Math.round(3.14)/2"},
                {"T","F","",""},
                {"-234","110","-636","-348"},
        };

        for (int i = 0; i < 11; i++) {
            Question question = new Question();
            ArrayList<Pair<String, String>> list = new ArrayList<>();
            if(!Objects.equals(Answers[i][0], "")) list.add(new Pair<>("A", Answers[i][0]));
            if(!Objects.equals(Answers[i][1], "")) list.add(new Pair<>("B", Answers[i][1]));
            if(!Objects.equals(Answers[i][2], "")) list.add(new Pair<>("C", Answers[i][2]));
            if(!Objects.equals(Answers[i][3], "")) list.add(new Pair<>("D", Answers[i][3]));



            question.setDescribe(arrayDescribe[i]);
            question.setAnswer(targetSelect[i]);
            question.setOption(list);
            questionList.add(question);
        }


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