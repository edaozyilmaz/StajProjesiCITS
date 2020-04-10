package com.eozyilmaz_stj.gnnyemei;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        mViewPager = findViewById(R.id.viewpager);
        mCardAdapter = new CardPagerAdapter();

        //<-----Loading Spinner----->
        final ProgressDialog myDialog = ProgressDialog.show(ViewPagerActivity.this, "Yükleniyor...", "Lütfen bekleyiniz.");
        myDialog.setCancelable(false);

        class LoadingDialogClass extends AsyncTask<String,Integer,Boolean> {
            @Override
            protected Boolean doInBackground(String... params){
                myDialog.show();
                return Boolean.TRUE;
            }
            @Override
            protected void onPreExecute() {
                Date now = Calendar.getInstance().getTime();
                new GetWeeklyMeallist().execute(simpleDateFormat.format(now));
            }
            @Override
            protected void onPostExecute(Boolean result) {
                // result is the value returned from doInBackground
                myDialog.dismiss();
            }
        }

        new LoadingDialogClass().execute();
        //<!-----Loading Spinner----->


        //<-----Check Internet Connection---->
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null){
            DialogBox dialogBox = new DialogBox();
            dialogBox.show(getSupportFragmentManager(), "DialogBox");
        }
        //<!----Check Internet Connection---->
    }



    /*
    * @param s = mm-DD-yyyy formatında string tarih
    * */
    private List<String> getWeekDaysFrom(String startDate) throws ParseException {
        List<String> sevenDaysList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(startDate));

        boolean isSunday = c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

        if(!isSunday){
            sevenDaysList.add(sdf.format(c.getTime()));
        }

        for(int i =0 ; i<6; i++){
            c.add(Calendar.DAY_OF_MONTH,1);
            if(c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
                sevenDaysList.add(sdf.format(c.getTime()));
            }
        }

        return sevenDaysList;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public class GetWeeklyMeallist extends AsyncTask<String,List<Meal>,List<Meal>>{

        @Override
        protected void onPostExecute(List<Meal> mealList) {
            super.onPostExecute(mealList);

            Log.d("MealDebug","Rendering");
            if(mealList != null){
                for (Meal meal : mealList) {
                    mCardAdapter.addCardItem(meal);
                }
            }

            mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                    dpToPixels(2, getApplicationContext()));

            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, new RotateUpTransformer());
            mViewPager.setOffscreenPageLimit(3);
        }

        @Override
        protected List<Meal> doInBackground(String... firstDay) {
            try {

                Log.d("MealDebug", "DoInBackground run.");
                final MealAPI mealAPI = RetroClient.getClient().create(MealAPI.class);
                List<Meal> mealList = new ArrayList<>();

                List<String> dayList = getWeekDaysFrom(firstDay[0]);
                Log.d("MealDebug", "Loop starting.");

                for (String day: dayList ) {
                    Call<Meal> call = mealAPI.getMealOfDay(day);
                    Meal meal = call.execute().body();

                    if(meal != null){
                        mealList.add(meal);
                        Log.d("MealDebug", "Meal added");
                    }
                }

                return mealList;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("MealDebug","Exception : "+ e.getMessage());
                return null;
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("MealDebug","Parse Exception : "+ e.getMessage());
                return null;
            }
        }
    }


}

