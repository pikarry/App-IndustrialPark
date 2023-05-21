package com.ipm.ipm.View.Fragment.FactoryFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ipm.ipm.Constant.CurrencyFormatter;
import com.ipm.ipm.Model.Factory;
import com.ipm.ipm.R;

import java.util.List;

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder> {
    List<Factory> factories;
    Context context;

    IOnClickFactory iOnClickFactory;

    public IOnClickFactory getiOnClickFactory() {
        return iOnClickFactory;
    }

    public void setiOnClickFactory(IOnClickFactory iOnClickFactory) {
        this.iOnClickFactory = iOnClickFactory;
    }

    public void setFactories(List<Factory> factories) {
        this.factories = factories;
        notifyDataSetChanged();
    }

    public FactoryAdapter(List<Factory> factories, Context context) {
        this.factories = factories;
        this.context = context;
    }

    @NonNull
    @Override
    public FactoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_factory, parent, false);
        FactoryAdapter.ViewHolder viewHolder = new FactoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FactoryAdapter.ViewHolder holder, int position) {
        Factory factory = factories.get(position);
        holder.tvName.setText(factory.getName());
        holder.tvPrice.setText(CurrencyFormatter.formatVND(factory.getPrice()) + "/tháng");

        switch (factory.getStatus()){
            case "0":
                holder.tvStatus.setBackgroundResource(R.drawable.fr_stt_0);
                holder.tvStatus.setTextColor(R.color.text_factory_status_0);
                holder.tvStatus.setText("Chưa thuê");
                break;
            case "1":
                holder.tvStatus.setBackgroundResource(R.drawable.fr_stt_1);
                holder.tvStatus.setTextColor(R.color.text_factory_status_1);
                holder.tvStatus.setText("Chờ duyệt");
                break;
            case "2":
                holder.tvStatus.setBackgroundResource(R.drawable.fr_stt_2);
                holder.tvStatus.setTextColor(R.color.text_factory_status_2);
                holder.tvStatus.setText("Đã thuê");
                break;
            default:
                break;
        }

        holder.tvArea.setText(factory.getAcreage()+" m2");
        Glide.with(context).load(factory.getImage()).placeholder(R.drawable.icon).into(holder.imgFactory);

        holder.btnHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickFactory.onClickFactory(factory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return factories==null?0: factories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvArea, tvPrice;
        ImageView imgFactory;
        Button btnHire;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameFactory);
            tvStatus = itemView.findViewById(R.id.tvStatusFactory);
            tvArea = itemView.findViewById(R.id.tvAreaFactory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgFactory = itemView.findViewById(R.id.imgFactory);
            btnHire = itemView.findViewById(R.id.btnHireFactory);

        }
    }
}
