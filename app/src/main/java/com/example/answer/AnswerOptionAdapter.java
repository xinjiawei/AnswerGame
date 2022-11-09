package com.example.answer;

import android.annotation.SuppressLint;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnswerOptionAdapter extends RecyclerView.Adapter<AnswerOptionAdapter.ViewHolder> {

    private List<Pair<String, String>> option = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDate(List<Pair<String, String>> option) {
        this.option = option;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public AnswerOptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_option_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerOptionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        System.out.println(option.get(position));
        holder.questionOption.setText(option.get(position).first);
        holder.questionDescribe.setText(option.get(position).second);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(view, position));
        }
    }

    @Override
    public int getItemCount() {
        return option.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionOption;
        TextView questionDescribe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionOption = itemView.findViewById(R.id.questionOption);
            questionDescribe = itemView.findViewById(R.id.questionDescribe);
        }
    }
}
