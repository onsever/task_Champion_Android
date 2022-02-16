package com.example.task_champion_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.activities.DetailsActivity;
import com.example.task_champion_android.databinding.AudioRowBinding;
import com.example.task_champion_android.db.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class AudioItemsAdapter extends RecyclerView.Adapter<AudioItemsAdapter.AudioItemViewHolder> {

    Context context;
    List<MediaItem> itemList;
    AudioRowBinding binding;

    public AudioItemsAdapter (Context context, List<MediaItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AudioItemsAdapter.AudioItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = AudioRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AudioItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding.audioRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DetailsActivity)context).removeAudioFile(itemList.get(position));
            }
        });
        binding.audioPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DetailsActivity)context).playAudioFile(itemList.get(position));
            }
        });
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

    @SuppressLint("NotifyDataSetChanged")
    public void updateItems() {
        this.notifyDataSetChanged();
    }
}
