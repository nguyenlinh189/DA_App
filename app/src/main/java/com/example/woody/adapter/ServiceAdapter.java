package com.example.woody.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woody.ConfirmPayment;
import com.example.woody.R;
import com.example.woody.api.APIUtils;
import com.example.woody.model.Service;
import com.example.woody.model.UsedService;
import com.example.woody.model.User;
import com.example.woody.ui.library_wood.WoodAdapter;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{
    private List<Service> listService=new ArrayList<>();
    private User user;

    public ServiceAdapter(User user) {
        this.user = user;
    }

    public void setListService(List<Service>listService){
        this.listService=listService;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ServiceViewHolder holder, int position) {
        Service service=listService.get(position);
        holder.name.setText(service.getName());
        holder.pricePerMonth.setText(service.getPrice()/service.getDuration()+"/1 tháng");
        holder.price.setText(service.getPrice()+"");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsedService usedService=new UsedService();
                usedService.setService(service);
                usedService.setUser(user);
                usedService.setStatus("PENDING");
                usedService.setTypePayment("VNPAY");
                Calendar calendar=Calendar.getInstance();
                usedService.setDateStart(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").format(calendar.getTime()));
                calendar.add(Calendar.MONTH, service.getDuration());
                usedService.setDateEnd(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").format(calendar.getTime()));
                usedService.setTotalMoney(usedService.getService().getPrice());
                Intent  intent=new Intent(view.getContext(), ConfirmPayment.class);
                intent.putExtra("usedService", usedService);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }
    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private TextView name, pricePerMonth, price;
        private Button button;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            pricePerMonth=itemView.findViewById(R.id.pricePerMonth);
            price=itemView.findViewById(R.id.price);
            button=itemView.findViewById(R.id.btnthanhtoan);
        }
    }
}
