package com.ipm.ipm.View.Fragment.IndustrialFragment;

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
import com.ipm.ipm.Model.Industrial;
import com.ipm.ipm.R;

import java.util.List;

public class IndustrialAdapter extends RecyclerView.Adapter<IndustrialAdapter.ViewHolder> {
    List<Industrial> industrials;
    Context context;
    IOnClickIndustrial iOnClickIndustrial;

    public IOnClickIndustrial getiOnClickIndustrial() {
        return iOnClickIndustrial;
    }

    public void setiOnClickIndustrial(IOnClickIndustrial iOnClickIndustrial) {
        this.iOnClickIndustrial = iOnClickIndustrial;
    }

    public void setIndustrials(List<Industrial> industrials) {
        this.industrials = industrials;
        notifyDataSetChanged();
    }

    public IndustrialAdapter(List<Industrial> industrials, Context context) {
        this.industrials = industrials;
        this.context = context;
    }

    @NonNull
    @Override
    public IndustrialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_industrial, parent, false);
        IndustrialAdapter.ViewHolder viewHolder = new IndustrialAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IndustrialAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Industrial industrial = industrials.get(position);
        holder.tvIndustrialName.setText(industrial.getName());
        holder.tvLocation.setText(industrial.getAddress());
        Glide.with(context).load(industrial.getImage()).placeholder(R.drawable.icon).into(holder.imgIndustrialImage);

        holder.btnSeeInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickIndustrial.clickIndustrial(industrial);
            }
        });
    }

    @Override
    public int getItemCount() {
        return industrials==null?0: industrials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndustrialName, tvLocation;
        ImageView imgIndustrialImage;
        Button btnSeeInfor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndustrialName = itemView.findViewById(R.id.tvIndustrialName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            imgIndustrialImage = itemView.findViewById(R.id.imgIndustrialImage);
            btnSeeInfor = itemView.findViewById(R.id.btnSeeInfor);
        }
    }
}
