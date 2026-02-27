package com.example.novaserviciosapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novaserviciosapp.R;
import com.example.novaserviciosapp.model.Servicio;

import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ServicioAdapter
 * Adapter oficial del RecyclerView.
 * Su función es convertir una lista de Servicio (datos)
 * en tarjetas visuales (item_servicio.xml).
 */
public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder> {

    private final List<Servicio> listaServicios;

    public ServicioAdapter(List<Servicio> listaServicios) {
        this.listaServicios = listaServicios;
    }

    @NonNull
    @Override
    public ServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla (convierte) el XML item_servicio.xml en una vista real
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servicio, parent, false);

        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicioViewHolder holder, int position) {
        // Obtiene el servicio actual
        Servicio servicio = listaServicios.get(position);

        // Pinta los datos en la tarjeta
        holder.tvNombre.setText(servicio.getNombre());
        holder.tvTipo.setText("Tipo: " + servicio.getTipo());

        try {

            // Formato original ISO
            SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            // Formato visual amigable
            SimpleDateFormat formatoVisual = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

            Date fechaDate = formatoOriginal.parse(servicio.getFecha());

            String fechaFormateada = formatoVisual.format(fechaDate);

            holder.tvFechaHora.setText(fechaFormateada + " • " + servicio.getHora());

        } catch (ParseException e) {
            holder.tvFechaHora.setText(servicio.getFecha() + " • " + servicio.getHora());
        }


        //holder.tvFechaHora.setText("Fecha/Hora: " + servicio.getFecha() + " " + servicio.getHora());
        holder.tvCosto.setText("S/ " + String.format("%.2f", servicio.getCosto()));
    }

    @Override
    public int getItemCount() {
        return listaServicios.size();
    }

    /**
     * ViewHolder
     * Mantiene referencias a los TextViews del item,
     * para no buscarlos repetidamente (mejor rendimiento).
     */
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
}