package com.rimas.explorenepal.api;

import com.rimas.explorenepal.model.BookmarkList_Data;
import com.rimas.explorenepal.model.RecommendationList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RecommendationApi {

    private static final String url= "http://10.0.2.2:80/explore/api/explore/";
    private static final String base_url = "https://jsonplaceholder.typicode.com/";
    private static final String Phone_url= "http://192.168.1.70:80/explore/api/explore/";
//    private static final String Phone_url= "http://sameeralam.com.np/explore/api/explore/";
//    private static final String Phone_url= "http://192.168.30.150:80/explore/api/explore/";



    public static RecommendationApi.ExploreService exploreService=null;

    public static RecommendationApi.ExploreService getExploreService(){
        if (exploreService==null){

            Retrofit retrofit= new Retrofit.Builder()
                    .baseUrl(Phone_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            exploreService=retrofit.create(RecommendationApi.ExploreService.class);

        }
        return exploreService;
    }


    public interface ExploreService{

        @GET("readRecommendationData.php")
        Call<ArrayList<RecommendationList>> getRecommendationList();
//        @GET("posts")
//        Call<ArrayList<PostList>> getPostList();


        @POST("createFavourite.php")
        @FormUrlEncoded
        Call<BookmarkList_Data> savePost(@Field("id") Integer id,@Field("name") String name,
                                @Field("location") String location,
                                @Field("description") String description,
                                @Field("lat") Double lat,
                                @Field("_long") Double _long,
                                @Field("image") String image);

        @POST("deleteFavourite.php")
        @FormUrlEncoded
        Call<BookmarkList_Data> deletePost(@Field("id") Integer id);



    }
}
