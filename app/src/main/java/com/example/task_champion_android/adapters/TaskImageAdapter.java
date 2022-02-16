package com.example.task_champion_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.R;
import com.example.task_champion_android.databinding.TaskImageRowBinding;

public class TaskImageAdapter extends RecyclerView.Adapter<TaskImageAdapter.ImageViewHolder> {

    private TaskImageRowBinding binding;
    private Context context;

    public TaskImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TaskImageRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        binding.taskImageView.setImageResource(R.drawable.ic_plus);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(@NonNull TaskImageRowBinding binding) {
            super(binding.getRoot());
        }
    }
}
