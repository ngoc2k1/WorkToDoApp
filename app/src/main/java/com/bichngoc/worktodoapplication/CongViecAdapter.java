package com.bichngoc.worktodoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bichngoc.worktodoapplication.databinding.ItemCongviecBinding;

import java.util.ArrayList;

public class CongViecAdapter extends RecyclerView.Adapter<CongViecAdapter.CongViecHolder> {
    private ArrayList<CongViec> listCongViec;
    private CongViec congViec;
    private Context context;
    private IOnCongViecListener listener;

    public CongViecAdapter(ArrayList<CongViec> listCongViec, Context context, IOnCongViecListener listener) {
        this.listCongViec = listCongViec;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CongViecAdapter.CongViecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCongviecBinding itemBinding = ItemCongviecBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CongViecHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CongViecAdapter.CongViecHolder holder, int position) {
        congViec = listCongViec.get(position);
        holder.tvTen.setText(congViec.getTenCV());
        holder.imgDelete.setOnClickListener(view -> listener.delete(congViec.getIdCV()));
        holder.imgEdit.setOnClickListener(view -> listener.update(congViec));
    }

    public void updateData(ArrayList<CongViec> listCongViecUpdate) {
        listCongViec.clear();
        listCongViec.addAll(listCongViecUpdate);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listCongViec.size();
    }

    public class CongViecHolder extends RecyclerView.ViewHolder {
        private TextView tvTen;
        private ImageView imgDelete, imgEdit;

        public CongViecHolder(ItemCongviecBinding itemView) {
            super(itemView.getRoot());
            tvTen = itemView.textviewTen;
            imgDelete = itemView.imageviewDelete;
            imgEdit = itemView.imageviewEdit;
        }
    }
}
