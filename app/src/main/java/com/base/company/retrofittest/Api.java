package com.base.company.retrofittest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

//This Interface will have all the API "Endpoint" . Retrofit will create the rest of code.
public interface Api {
    @GET("posts")// We need to specify the Endpoint with, Request Type and Parameter(if any).
    Call<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int userId);

    //if end point is like  "posts?userId=1&_sort=id&_order=desc", This is Query Parameter as it has "?"
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer userId,//We use "Integer" not "int" because we can pass null if we have "Integer"
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //If you want to post the entire parameter separately
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@Field("userId") int userId, @Field("title") String title,@Field("body") String text);

    //If We want to pass Headers
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@Header ("content-type") String contentType,@Field("userId") int userId, @Field("title") String title, @Field("body") String text);


}
