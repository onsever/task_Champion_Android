package com.example.task_champion_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.R;
import com.example.task_champion_android.databinding.TasksRowBinding;
import com.example.task_champion_android.db.Item;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    TasksRowBinding binding;
    Context context;
    private List<Item> items;
    private ItemClickListener itemClickListener;

    public TasksAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TasksRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        if (items == null || items.size() == 0) {
            return 0;
        }

        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        binding.taskName.setText(items.get(position).getName());

        binding.rowLayout.setOnClickListener(v -> {
            itemClickListener.onItemClickedOn(items.get(position));
        });

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

    public interface ItemClickListener {
        void onItemClickedOn(Item item);
    }

}
