package com.example.parcial2movil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CancionesListAdapter extends RecyclerView.Adapter<CancionesListAdapter.CancionViewHolder> {


    private List<Cancion> canciones;
    private int resourse;
    private Activity activity;


    public CancionesListAdapter(int resourse, Activity activity) {
        super();
        this.canciones = new ArrayList<Cancion>();
        this.resourse = resourse;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CancionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourse,parent,false);
        return new CancionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancionViewHolder holder, int position) {
        Cancion cancion = canciones.get(position);
        holder.nombre.setText(cancion.getTitle());
        holder.artista.setText(cancion.getArtist().getTitle());
        holder.duracion.setText(cancion.getDuration());
    }

    @Override
    public int getItemCount() {
        return canciones.size();
    }

    public void adicionacancion(List<Cancion> cancion) {
        canciones.addAll(cancion);
        notifyDataSetChanged();
    }


    class CancionViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private TextView artista;
        private TextView duracion;

        public CancionViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.item_nombre);
            artista = (TextView) itemView.findViewById(R.id.item_artista);
            duracion = (TextView) itemView.findViewById(R.id.item_duration);
        }


    }
}
