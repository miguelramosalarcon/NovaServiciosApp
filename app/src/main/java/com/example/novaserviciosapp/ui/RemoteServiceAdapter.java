package com.example.novaserviciosapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novaserviciosapp.R;
import com.example.novaserviciosapp.model.RemoteService;

import java.util.List;

public class RemoteServiceAdapter extends RecyclerView.Adapter<RemoteServiceAdapter.ViewHolder> {

    private final List<RemoteService> serviceList;

    public RemoteServiceAdapter(List<RemoteService> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_remote_service, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RemoteService service = serviceList.get(position);

        holder.tvTitle.setText(service.getTitle().toUpperCase());
        holder.tvBody.setText(service.getBody());
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
        }
    }
}