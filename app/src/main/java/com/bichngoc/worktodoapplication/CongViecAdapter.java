package com.bichngoc.worktodoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CongViecAdapter extends RecyclerView.Adapter<CongViecAdapter.CongViecHolder> {
    private ArrayList<CongViec> listCongViec;
    private CongViec congViec;
    private Context context;//moi truong để hiển thị

    public CongViecAdapter(ArrayList<CongViec> listCongViec, Context context) {
        this.listCongViec = listCongViec;
        this.context = context;
    }

    @NonNull
    @Override
    public CongViecAdapter.CongViecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cong_viec, parent, false);
        return new CongViecHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CongViecAdapter.CongViecHolder holder, int position) {
        congViec = listCongViec.get(position);
        holder.tvTen.setText(congViec.getTenCV());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = (MainActivity) context;
                ((MainActivity) context).delete(congViec.getIdCV(), congViec.getTenCV());
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = (MainActivity) context;
                ((MainActivity) context).update(congViec);
            }
        });
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

        public CongViecHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.textview_ten);
            imgDelete = itemView.findViewById(R.id.imageview_delete);
            imgEdit = itemView.findViewById(R.id.imageview_edit);
        }
    }
}
