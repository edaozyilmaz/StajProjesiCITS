package com.eozyilmaz_stj.gnnyemei;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealAPI {
    @GET("/MealList/GetMealListOfDate")
    Call<Meal> getMealOfDay(@Query("date") String date);


}
