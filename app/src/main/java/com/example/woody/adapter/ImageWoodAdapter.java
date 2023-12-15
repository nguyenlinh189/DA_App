package com.example.woody.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woody.R;
import com.example.woody.model.Image;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.List;

public class ImageWoodAdapter extends CardSliderAdapter<ImageWoodAdapter.ImageWoodViewHolder> {
    private List<Image> imgaes;

    public ImageWoodAdapter(List<Image> imgaes) {
        this.imgaes = imgaes;
    }

    @NonNull
    @Override
    public ImageWoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_wood,parent,false);
        return new ImageWoodViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return imgaes.size();
    }
    private Image getImageAtPosition(int position){
        return imgaes.get(position);
    }
    @Override
    public void bindVH(@NonNull ImageWoodViewHolder holder, int position) {
        Image img=getImageAtPosition(position);
        Glide.with(holder.itemView.getContext()).load(img.getPath()).into(holder.img);
        if(img.getNameImg()!=null){
            holder.txt.setText(img.getNameImg());
        }
    }

    class ImageWoodViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView txt;
        public ImageWoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            txt=itemView.findViewById(R.id.txt);
        }
    }
}
