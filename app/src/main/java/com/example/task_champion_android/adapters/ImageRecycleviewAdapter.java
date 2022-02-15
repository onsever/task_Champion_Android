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
import com.example.task_champion_android.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageRecycleviewAdapter extends RecyclerView.Adapter<ImageRecycleviewAdapter.ViewHolder>{

    List<ItemViewModel> arrayList;
    Context context;

    public ImageRecycleviewAdapter(Context context ,List arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycleview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getImageUrl()).into(holder.imageView);

//        Image image = (Image)arrayList.get(position);
//        holder.image.setImageBitmap(ImageBitmapString.getBitMapFromStr(image.getImage()));
//        image.setImage(image.getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

}
