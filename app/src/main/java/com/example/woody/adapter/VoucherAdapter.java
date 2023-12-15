package com.example.woody.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woody.ConfirmPayment;
import com.example.woody.R;
import com.example.woody.model.UsedService;
import com.example.woody.model.Voucher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private List<Voucher> list=new ArrayList<>();
    private Voucher voucher;
    private UsedService usedService;

    public VoucherAdapter(List<Voucher> list, UsedService usedService) {
        this.list = list;
        this.usedService=usedService;
        notifyDataSetChanged();
    }

    public void setList(List<Voucher> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        voucher=list.get(position);
        holder.code.setText(voucher.getCode());
        SimpleDateFormat Sformat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            holder.duration.setText(Sformat.format( Sformat.parse(voucher.getDateStart()))+" - "+Sformat.format( Sformat.parse(voucher.getDateEnd())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.amount.setText(voucher.getAmount()+" đồng");
        if(usedService.getVoucher()!=null) {
            if (voucher.getCode().equalsIgnoreCase(usedService.getVoucher().getCode())) {
                holder.btnChon.setText("Đã chọn");
                holder.btnChon.setEnabled(false);
            } else {
                holder.btnChon.setText("Chọn");
                holder.btnChon.setEnabled(true);
            }
        }
        holder.btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usedService.setVoucher(voucher);
                usedService.setTotalMoney(usedService.getService().getPrice()- voucher.getAmount());
                Intent intent=new Intent(view.getContext(), ConfirmPayment.class);
                intent.putExtra("usedService", usedService);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder {
        private TextView code, duration, amount;
        private Button btnChon;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.code);
            duration=itemView.findViewById(R.id.duration);
            amount=itemView.findViewById(R.id.amount);
            btnChon=itemView.findViewById(R.id.btnChon);
        }
    }
}
