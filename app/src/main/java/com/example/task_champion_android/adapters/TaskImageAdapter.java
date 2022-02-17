package com.example.task_champion_android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.R;
import com.example.task_champion_android.databinding.TaskImageRowBinding;
import com.example.task_champion_android.db.MediaItem;

import java.io.File;
import java.util.List;

public class TaskImageAdapter extends RecyclerView.Adapter<TaskImageAdapter.ImageViewHolder> {

    private TaskImageRowBinding binding;
    private Context context;
    private List<MediaItem> imageList;
    private ItemClickListener itemClickListener;

    public TaskImageAdapter(Context context, List<MediaItem> imageList, ItemClickListener itemClickListener)
    {
        this.imageList = imageList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TaskImageRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Bitmap finalBitmap = BitmapFactory.decodeFile(imageList.get(position).getUri());
        binding.taskImageView.setImageBitmap(finalBitmap);
        binding.imageCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickListener.onItemLongClick(imageList.get(position));
                return true;
            }
        });
//        binding.taskImageView.setImageURI(Uri.parse(new File(imageList.get(position).getUri()).toString()));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(@NonNull TaskImageRowBinding binding) {
            super(binding.getRoot());
        }
    }

    public interface ItemClickListener {
        void onItemLongClick(MediaItem mediaItem);
    }
}
