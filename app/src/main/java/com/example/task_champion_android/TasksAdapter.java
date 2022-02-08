package com.example.task_champion_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.databinding.TasksRowBinding;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    TasksRowBinding binding;
    Context context;
    ArrayList<Item> items;

    public TasksAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TasksRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        binding.taskName.setText(items.get(position).getName());

        if (items.get(position).isCompleted()) {
            binding.completedView.setBackground(context.getDrawable(R.drawable.completion_view));
        }
        else {
            binding.completedView.setBackground(context.getDrawable(R.drawable.uncompleted_view));
        }

    }


    static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TaskViewHolder(@NonNull TasksRowBinding binding) {
            super(binding.getRoot());
        }
    }

}
