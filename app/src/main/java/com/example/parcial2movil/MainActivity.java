package com.example.parcial2movil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DEEZER";
    Retrofit retrofit;
    RecyclerView recyclerView;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new  Retrofit.Builder().
                baseUrl("https://api.deezer.com/playlist/93489551/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        obtenerDatos();

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BuscadorActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }



    public void obtenerDatos(){

        DeezerInterface service = retrofit.create(DeezerInterface.class);

        Call<CancionRespuesta> canciones = service.obtenerCancion();

        canciones.enqueue(new Callback<CancionRespuesta>() {
            @Override
            public void onResponse(Call<CancionRespuesta> call, Response<CancionRespuesta> response) {

                if (response.isSuccessful()){

                    CancionRespuesta cancionRespuesta = response.body();

                    Log.i("TAG","Aqui:"+cancionRespuesta.getData().size());

                    List<Cancion> listaCancion = cancionRespuesta.getData();


                    Cancion cancion = listaCancion.get(0);


                    for ( Cancion item : listaCancion
                    ) {
                        Log.i("TAG","Aqui"+ item.getTitle());
                        item.setDuration(convertirDuracion( item.getDuration()));
                    }

                    cargar(listaCancion);




                }else {
                    Log.e(TAG , "onResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CancionRespuesta> call, Throwable t) {

            }
        });
    }

    public String convertirDuracion(String s){

        int num = Integer.parseInt(s);

        int hor=num/3600;
        int min=(num-(3600*hor))/60;
        int seg=num-((hor*3600)+(min*60));

        if (seg<10){
            return min+":0"+seg;
        }
        else{
            return min+":"+seg;
        }
    }



    public void cargar(List<Cancion> lista){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerCanciones);
        ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


        CancionesListAdapter cancionesListAdapter = new CancionesListAdapter(R.layout.item_cancion,this);
        cancionesListAdapter.adicionacancion(lista);

        recyclerView.setAdapter(cancionesListAdapter);
    }

    public void recargar(List<Cancion> lista){

       CancionesListAdapter canciones = (CancionesListAdapter) recyclerView.getAdapter();
       canciones.adicionacancion(lista);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1){
            Log.i(TAG,"HOLA COMO ESTAS " );

            String nombre = data.getExtras().getString("cancion");
            String artista = data.getExtras().getString("artista");
            String album = data.getExtras().getString("album");
            String duracion = data.getExtras().getString("duracion");

            Artista artista1 = new Artista();
            artista1.setTitle(artista);

            Album album1 = new Album();
            album1.setTitle(album);

            Cancion cancion = new Cancion();
            cancion.setTitle(nombre);
            cancion.setArtist(artista1);
            cancion.setAlbum(album1);
            cancion.setDuration(duracion);

            List<Cancion> canciones = new ArrayList<>();

            canciones.add(cancion);

            recargar(canciones);


        }


    }
}
