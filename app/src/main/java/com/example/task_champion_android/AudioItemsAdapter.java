package com.example.task_champion_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.databinding.AudioRowBinding;

import java.util.ArrayList;

public class AudioItemsAdapter extends RecyclerView.Adapter<AudioItemsAdapter.AudioItemViewHolder> {

    Context context;
    ArrayList<AudioItem> itemList;
    AudioRowBinding binding;

    public AudioItemsAdapter (Context context, ArrayList<AudioItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AudioItemsAdapter.AudioItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = AudioRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AudioItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioItemViewHolder holder, int position) {
        binding.audioFileName.setText(itemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class AudioItemViewHolder extends RecyclerView.ViewHolder {
        public  AudioItemViewHolder( @NonNull AudioRowBinding binding) {
            super(binding.getRoot());
        }
    }
}
