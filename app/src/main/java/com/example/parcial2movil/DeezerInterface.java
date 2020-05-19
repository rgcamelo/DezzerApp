package com.example.parcial2movil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeezerInterface {

    @GET("tracks")
    Call<CancionRespuesta> obtenerCancion();

    @GET("search")
    Call<CancionRespuesta> buscarCancion( @Query("q") String q );

}
