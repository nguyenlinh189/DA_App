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
import com.example.woody.api.APIUtils;
import com.example.woody.model.IdentifyWoodHistory;
import com.example.woody.model.SaveAccount;
import com.example.woody.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryIdentifyAdapter extends RecyclerView.Adapter<HistoryIdentifyAdapter.HistoryIdentifyViewHolder>{
    private List<IdentifyWoodHistory>listHistoryIdentify=new ArrayList<>();

    public HistoryIdentifyAdapter(List<IdentifyWoodHistory> listHistoryIdentify) {
        this.listHistoryIdentify = listHistoryIdentify;
        notifyDataSetChanged();
    }
    public void setList(List<IdentifyWoodHistory>list){
        this.listHistoryIdentify=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryIdentifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_identify, parent, false);
        return new HistoryIdentifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryIdentifyViewHolder holder, int position) {
        IdentifyWoodHistory identify=listHistoryIdentify.get(position);
        Glide.with(holder.itemView.getContext()).load(identify.getPath()).into(holder.imageView);
        if(identify.getWood()==null)
            holder.displayName.setText(identify.getResult());
        else
            holder.displayName.setText(identify.getWood().getScientificName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIUtils.getApiServiceInterface().deleteIdentify(identify).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body()!=null)
                        {
                            listHistoryIdentify.remove(identify);
                            SaveAccount.listIdentify.remove(identify);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHistoryIdentify.size();
    }

    public class HistoryIdentifyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView,delete;
        private TextView  displayName;

        public HistoryIdentifyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            displayName = itemView.findViewById(R.id.display_name);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
