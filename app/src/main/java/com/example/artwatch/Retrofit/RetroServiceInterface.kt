package com.example.artwatch.Retrofit

import com.example.artwatch.Data.*
import retrofit2.Call
import retrofit2.http.*


interface RetroServiceInterface {
    @GET("464edad5-dacf-4fd1-b9a2-e1899e5efd3a")
    fun getFAQsList(): Call<List<FAQsModel>>

    @GET("fe4c77c4-6a02-4352-a3e3-fe6a60332203")
    fun getPrivacyList(): Call<List<FAQsModel>>

    @GET("GetAllArtists")
    fun getAllArtists(): Call<List<ArtistsModel>>

    @Headers("Content-Type: application/json")
    @POST("CreateArtist")
    fun addUser(@Body userData: ArtistsModel): Call<ArtistsModel>
//    @Headers(*["Content-Type: application/json", "x-access-token: eyJhbGciOiJIU"])
//    @POST("/api/employee/checkin")
//    fun CHECKIN(@Body data: String?): Call<String?>?

    @POST("CreateArtist")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun createArtist(@Body params: ArtistsModel): Call<ResponseModel>

    @POST("CreateIncident")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun createIncident(@Body params: IncidentsModel): Call<ResponseModel>

    @POST("CreateLawyer")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun createLawyer(@Body params: LawyersModel): Call<ResponseModel>

    @POST("GetArtist")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getArtist(@Body params: RequestModel): Call<ArtistResponseModel>

    @POST("EditArtist")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun editArtist(@Body params: EditArtistModel): Call<ResponseModel>
}/**/