package com.example.novaserviciosapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novaserviciosapp.R;
import com.example.novaserviciosapp.model.Servicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ServicioAdapter
 * Adapter oficial del RecyclerView.
 */
public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder> {

    private final List<Servicio> listaServicios;
    private final OnItemLongClickListener listener;

    /**
     * Interfaz para manejar Long Press desde la Activity
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(Servicio servicio);
    }

    public ServicioAdapter(List<Servicio> listaServicios, OnItemLongClickListener listener) {
        this.listaServicios = listaServicios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servicio, parent, false);

        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicioViewHolder holder, int position) {

        Servicio servicio = listaServicios.get(position);

        holder.tvNombre.setText(servicio.getNombre());
        holder.tvTipo.setText(servicio.getTipo());

        try {

            SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat formatoVisual = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

            Date fechaDate = formatoOriginal.parse(servicio.getFecha());
            String fechaFormateada = formatoVisual.format(fechaDate);

            holder.tvFechaHora.setText(fechaFormateada + " • " + servicio.getHora());

        } catch (ParseException e) {
            holder.tvFechaHora.setText(servicio.getFecha() + " • " + servicio.getHora());
        }

        holder.tvCosto.setText("S/ " + String.format("%.2f", servicio.getCosto()));

        // 🔥 LONG PRESS
        holder.itemView.setOnLongClickListener(v -> {

            android.util.Log.d("DEBUG", "ID enviado: " + servicio.getId());

            listener.onItemLongClick(servicio);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaServicios.size();
    }

    static class ServicioViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvTipo, tvFechaHora, tvCosto;

        public ServicioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
            tvCosto = itemView.findViewById(R.id.tvCosto);
        }
    }

    public void eliminarItem(int position) {

        listaServicios.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaServicios.size());
    }
}