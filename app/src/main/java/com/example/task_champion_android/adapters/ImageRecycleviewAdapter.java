package com.example.task_champion_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.task_champion_android.R;
import com.example.task_champion_android.db.Image;
import com.example.task_champion_android.supportClass.ImageBitmapString;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageRecycleviewAdapter extends RecyclerView.Adapter<ImageRecycleviewAdapter.ViewHolder>{

    List arrayList;
    Context context;

    public ImageRecycleviewAdapter(Context context ,List arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycleview,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Image image = (Image)arrayList.get(position);
        holder.image.setImageBitmap(ImageBitmapString.getBitMapFromStr(image.getImage()));
        image.setImage(image.getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
        }
    }

}
