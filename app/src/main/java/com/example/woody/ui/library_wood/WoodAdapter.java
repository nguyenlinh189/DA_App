package com.example.woody.ui.library_wood;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woody.DetailWoodActivity;
import com.example.woody.R;
import com.example.woody.api.APIUtils;
import com.example.woody.model.SaveAccount;
import com.example.woody.model.User;
import com.example.woody.model.Wood;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Wood> woodList;
    private List<Wood> oldList;
    private Context context;
    private List<Wood> listWoodFav;

    private static int TYPE_ITEM=1;
    private static int TYPE_LOADING=2;
    private int pInListFav=-1;
    private User user;
    private int mark=1;


    public WoodAdapter(Context context, List<Wood> list,  User user) {
        this.context = context;
        this.woodList = list;
        this.oldList = list;
        this.user=user;
        listWoodFav=user.getListFavouriteWood();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wood, parent, false);
            return new WoodViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==TYPE_ITEM){
            Wood wood = woodList.get(position);
            if (wood == null) {
                return;
            }
            WoodViewHolder woodViewHolder= (WoodViewHolder) holder;
            woodViewHolder.displayName.setText(wood.getVietnameName());
            woodViewHolder.scienceName.setText(wood.getScientificName());
            Glide.with(context).load(wood.getListImage().get(2).getPath()).into(woodViewHolder.imageView);
            woodViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), DetailWoodActivity.class);
                    intent.putExtra("wood", wood);
                    view.getContext().startActivity(intent);
                }
            });
//            Toast.makeText(context.getApplicationContext(), SaveAccount.listFavouriteWood.size(),Toast.LENGTH_LONG).show();
            pInListFav=-1;
            for (int i=0;i<listWoodFav.size();i++)
                if(listWoodFav.get(i).getId()==wood.getId())
                {
                    woodViewHolder.save.setImageResource(R.drawable.ic_save);
                    pInListFav=i;
                    break;
                }
            if(pInListFav==-1)
                woodViewHolder.save.setImageResource(R.drawable.ic_dontsave);
            woodViewHolder.save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mark=1;
                    if(woodViewHolder.save.getDrawable().getConstantState().equals(ResourcesCompat.getDrawable(view.getResources(), R.drawable.ic_save, null).getConstantState())==true)
                    {
                        mark=0;
                        for (int i=0;i<listWoodFav.size();i++)
                            if(listWoodFav.get(i).getId()==wood.getId())
                            {
                                user.getListFavouriteWood().remove(i);
                                break;
                            }

                        woodViewHolder.save.setImageResource(R.drawable.ic_dontsave);
                    }
                    else
                    {
                        mark=1;
                        user.getListFavouriteWood().add(wood);
                        woodViewHolder.save.setImageResource(R.drawable.ic_save);
                    }
                    APIUtils.getApiServiceInterface().updateListFavourite(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful() && response.body()!=null)
                            {

                                user=response.body();
                                SaveAccount.listFavouriteWood=user.getListFavouriteWood();
                                listWoodFav=SaveAccount.listFavouriteWood;
                                notifyDataSetChanged();
                                if(mark==1)
                                    Toast.makeText(view.getContext(), "Thêm gỗ vào mục yêu thích thành công", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(view.getContext(), "Xóa gỗ khỏi danh sách yêu thành công", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (woodList != null) {
            return woodList.size();
        }
        return 0;
    }
    public void addLoadingEffect(){
        woodList.add(null);
        notifyItemInserted(woodList.size()-1);
    }
    public void removeLoadingEffect(){
        int position=woodList.size()-1;
        Wood wood=woodList.get(position);
        if(wood==null){
            woodList.remove(position);
            notifyDataSetChanged();
        }
    }
    public void setList(List<Wood>list){
        woodList=list;
        notifyDataSetChanged();
    }

    public void setWoodList(List<Wood>listWoodFav){
        this.listWoodFav=listWoodFav;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position){
        return woodList.get(position)==null?TYPE_LOADING:TYPE_ITEM;
    }


    public class WoodViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardView;
        private ImageView imageView,save;
        private TextView scienceName, displayName;

        public WoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.card);
            scienceName = itemView.findViewById(R.id.science_name);
            displayName = itemView.findViewById(R.id.display_name);
            save=itemView.findViewById(R.id.save);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView){
            super(itemView);
            this.progressBar=itemView.findViewById(R.id.loading);
        }
    }
}
