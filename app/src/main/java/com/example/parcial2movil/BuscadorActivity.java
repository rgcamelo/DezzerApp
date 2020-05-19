package com.example.parcial2movil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuscadorActivity extends AppCompatActivity {

    Retrofit retrofit;
    TextView tv_nombre, tv_artista, tv_albul, tv_duracion;
    EditText search;
    Button btn_search;
    private static final String TAG = "CANCIONES";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        tv_nombre = (TextView)findViewById(R.id.tv_nombre);
        tv_artista = (TextView)findViewById(R.id.tv_artista);
        tv_albul = (TextView)findViewById(R.id.tv_album);
        tv_duracion = (TextView)findViewById(R.id.tv_duracion);
        search = (EditText)findViewById(R.id.search_text);
        btn_search = (Button)findViewById(R.id.btn_search);


        retrofit = new  Retrofit.Builder().
                baseUrl("https://api.deezer.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public void buscarCancion(View view){

        DeezerInterface service = retrofit.create(DeezerInterface.class);

        String cancion = search.getText().toString();
        Call<CancionRespuesta> cancionRespuestaCall = service.buscarCancion(cancion);

        cancionRespuestaCall.enqueue(new Callback<CancionRespuesta>() {
            @Override
            public void onResponse(Call<CancionRespuesta> call, Response<CancionRespuesta> response) {

                if ( response.isSuccessful()){

                    CancionRespuesta cancionRespuesta = response.body();
                    List<Cancion> lista = cancionRespuesta.getData();

                    Cancion cancion = lista.get(0);

                    tv_nombre.setText(cancion.getTitle());
                    tv_artista.setText(cancion.artist.getTitle());
                    tv_albul.setText(cancion.album.getTitle());
                    tv_duracion.setText(convertirDuracion(cancion.getDuration()));

                }
            }

            @Override
            public void onFailure(Call<CancionRespuesta> call, Throwable t) {
                Log.e("TAG",t.getMessage());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agregar,menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save:
                insertarCancion();
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void insertarCancion(){
        String nombre = tv_nombre.getText().toString();
        String artista = tv_artista.getText().toString();
        String album = tv_albul.getText().toString();
        String duration = tv_duracion.getText().toString();

        Intent i = getIntent();

        i.putExtra("cancion",nombre);
        i.putExtra("artista",artista);
        i.putExtra("album",album);
        i.putExtra("duracion",duration);

        setResult(RESULT_OK, i);

        /*List<Cancion> canciones = db.CancionesDao().obtenerCanciones();

        for (Cancion item: canciones
             ) {
            Log.i(TAG, "Cancion: "+ item.getTitle());
            Log.i(TAG, "Cancion: "+ item.getArtist().getTitle());
        }*/
    }
}
