package com.eozyilmaz_stj.gnnyemei;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import java.text.ParseException;
import java.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainScrollingActivity extends AppCompatActivity {
    static TextView txtDate;
    static TextView txtSoup;
    static TextView txtSoupCal;
    static TextView txtMainCourse;
    static TextView txtMainCourseCal;
    static TextView txtAlternativeMeal;
    static TextView txtAlternativeMealCal;
    static TextView txtSupplementalMeal;
    static TextView txtSupplementalMealCal;
    static TextView txtDessert;
    static TextView txtDessertCal;
    static TextView txtTotal;
    static TextView txtTotalCal;
    static TextView txtDislike;
    static TextView txtLike;
    static RelativeLayout txtSoupLayout;
    static RelativeLayout txtMainCourseLayout;
    static RelativeLayout txtAlternativeMealLayout;
    static RelativeLayout txtSupplementalMealLayout;
    static RelativeLayout txtDessertLayout;
    FloatingActionButton btnShare;
    Button btnWeek;
    ImageButton btnLike,btnDislike;
    Intent shareIntent;
    String shareBody;
    String currentDate;
    int d, m, y, totalcal;
    Meal mealOfDay;
    Date now = Calendar.getInstance().getTime();
    static Date date;
    String dateLikeDislike;
    DataSnapshot dateDataSnapshot;
    LikeDislike likeDislikeObject;



    String[] days = {"-", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"};

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void getComponents(){
        btnShare = (FloatingActionButton) findViewById(R.id.btnShare);
        btnWeek = (Button) findViewById(R.id.btnWeek);
        txtSoupLayout = (RelativeLayout) findViewById(R.id.txtSoupLayout);
        txtMainCourseLayout = (RelativeLayout) findViewById(R.id.txtMainCourseLayout);
        txtAlternativeMealLayout = (RelativeLayout) findViewById(R.id.txtAlternativeMealLayout);
        txtSupplementalMealLayout = (RelativeLayout) findViewById(R.id.txtSupplementalMealLayout);
        txtDessertLayout = (RelativeLayout) findViewById(R.id.txtDessertLayout);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtSoup = (TextView) findViewById(R.id.txtSoup);
        txtSoupCal = (TextView) findViewById(R.id.txtSoupCal);
        txtMainCourse = (TextView) findViewById(R.id.txtMainCourseView);
        txtMainCourseCal = (TextView) findViewById(R.id.txtMainCourseCal);
        txtAlternativeMeal = (TextView) findViewById(R.id.txtAlternativeMealView);
        txtAlternativeMealCal = (TextView) findViewById(R.id.txtAlternativeMealCal);
        txtSupplementalMeal = (TextView) findViewById(R.id.txtSupplementalMeal);
        txtSupplementalMealCal = (TextView) findViewById(R.id.txtSupplementalMealCal);
        txtDessert = (TextView) findViewById(R.id.txtDessert);
        txtDessertCal = (TextView) findViewById(R.id.txtDessertCal);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotalCal = (TextView) findViewById(R.id.txtTotalCal);
        txtDislike = (TextView) findViewById(R.id.txtDislike);
        txtLike = (TextView) findViewById(R.id.txtLike);
        btnLike = findViewById(R.id.btnLike);
        btnDislike = findViewById(R.id.btnDislike);
    }

    private int getColorOfCalorie(int cal){
        if (cal <= 100){
            return getResources().getColor(R.color.firstColor);
        }
        else if (cal <= 200){
            return getResources().getColor(R.color.secondColor);
        }
        else if (cal <= 300 ){
            return getResources().getColor(R.color.thirdColor);
        }
        else if (cal <= 400){
            return getResources().getColor(R.color.forthColor);
        }
        else{
            return getResources().getColor(R.color.fifthColor);
        }
    }

    //<----Selected Date's Meal---->
    private void getMeals(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("tr"));
        currentDate = simpleDateFormat.format(date);

        final MealAPI mealAPI = RetroClient.getClient().create(MealAPI.class);
        final Call<Meal> call = mealAPI.getMealOfDay(currentDate);
        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                mealOfDay = response.body();

                txtSoup.setText(mealOfDay.Soup);
                txtSoupCal.setText(mealOfDay.SoupCalorie + " kalori");
                txtMainCourse.setText(mealOfDay.MainCourse);
                txtMainCourseCal.setText(mealOfDay.MainCourseCalorie + " kalori");
                txtAlternativeMeal.setText(mealOfDay.AlternativeMeal);
                txtAlternativeMealCal.setText(mealOfDay.AlternativeMealCalorie + " kalori");
                txtSupplementalMeal.setText(mealOfDay.SupplementalMeal);
                txtSupplementalMealCal.setText(mealOfDay.SupplementalMealCalorie + " kalori");
                txtDessert.setText(mealOfDay.Dessert);
                txtDessertCal.setText(mealOfDay.DessertCalorie + " kalori");

                totalcal = mealOfDay.SoupCalorie + mealOfDay.MainCourseCalorie + mealOfDay.AlternativeMealCalorie + mealOfDay.SupplementalMealCalorie + mealOfDay.DessertCalorie;
                txtTotalCal.setText(totalcal + "kalori");

                setCalorieColors(mealOfDay);

                if(response.body() != null)
                    Log.d("MealDebug", "Değer : " + response.body().toString()+"");

            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                Log.e("MealDebug", "HATA: " + t.toString());
                Log.e("MealDebug", "URL: " + call.request().url());

            }
        });
    }
    //<!----Selected Date's Meal---->


    //<-------Change Font------->
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    //<!-------Change Font------->


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFirebase(Calendar.getInstance().getTime());

        getComponents();


        //<-----Check Internet Connection---->
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null){
            DialogBox dialogBox = new DialogBox();
            dialogBox.show(getSupportFragmentManager(), "DialogBox");
        }
        //<!----Check Internet Connection---->


        //<------Loading Spinner----->
        final ProgressDialog myDialog = ProgressDialog.show(MainScrollingActivity.this, "Yükleniyor...", "Lütfen bekleyiniz.");
        myDialog.setCancelable(false);

        class LoadingDialogClass extends AsyncTask<String,Integer,Boolean> {
            @Override
            protected Boolean doInBackground(String... params){
                myDialog.show();
                return Boolean.TRUE;
            }
            @Override
            protected void onPreExecute() {
                getMeals(Calendar.getInstance().getTime());
            }
            @Override
            protected void onPostExecute(Boolean result) {
                // result is the value returned from doInBackground
                myDialog.dismiss();
            }
        }

        new LoadingDialogClass().execute();
        //<!-----Loading Spinner----->


        //<-----Transparent Status Bar---->
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        //<!-----Transparent Status Bar---->


        //<-------Search Food------->
        txtSoupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScrollingActivity.this, WebViewPage.class);
                intent.putExtra("txtId", "Soup");
                startActivity(intent);
            }
        });
        txtMainCourseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScrollingActivity.this, WebViewPage.class);
                intent.putExtra("txtId", "MainCourse");
                startActivity(intent);
            }
        });
        txtAlternativeMealLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScrollingActivity.this, WebViewPage.class);
                intent.putExtra("txtId", "AlternativeMeal");
                startActivity(intent);
            }
        });
        txtSupplementalMealLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScrollingActivity.this, WebViewPage.class);
                intent.putExtra("txtId", "SupplementalMeal");
                startActivity(intent);
            }
        });
        txtDessertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScrollingActivity.this, WebViewPage.class);
                intent.putExtra("txtId", "Dessert");
                startActivity(intent);
            }
        });
        //<!-------Search Food------->



        //<------Notification------>
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 101, myIntent,0);

        Calendar calendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long intendedTime = calendar.getTimeInMillis();
        long currentTime = currentCalendar.getTimeInMillis();

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);

        if (intendedTime >= currentTime) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else{
            calendar.add(Calendar.DAY_OF_MONTH,1);
            intendedTime = calendar.getTimeInMillis();
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        //<!-----Notification----->



        //<-----Share Button---->
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBody = txtDate.getText().toString()+ "\n" +txtSoup.getText().toString() + "\n" + txtMainCourse.getText().toString() + "\n" + txtAlternativeMeal.getText().toString() + "\n" + txtDessert.getText().toString();
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Yazıyı paylaş"));
            }
        });
        //<!-----Share Button---->


        //<-------btnWeek------->
        btnWeek.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(MainScrollingActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });
        //<!-------btnWeek------->

        //<-----Get Today's Date----->
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy - EEEE", new Locale("tr"));
        String currentDate = simpleDateFormat.format(new Date());
        txtDate.setText(currentDate);
        //<!-----Get Today's Date----->

        //<-----btnDate Date Picker Dialog----->
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        d = calendar.get(Calendar.DAY_OF_MONTH);
                        m = calendar.get(Calendar.MONTH);
                        y = calendar.get(Calendar.YEAR);

                        DatePickerDialog pickerDialog = new DatePickerDialog(MainScrollingActivity.this, new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker datepicker, int i, int i1, int i2){
                                String day, month, year;

                                GregorianCalendar gregorianCalendar = new GregorianCalendar(i, i1, i2-1);
                                int dayIntVal = gregorianCalendar.get(gregorianCalendar.DAY_OF_WEEK);
                                String dayOfWeek = days[dayIntVal];

                                i1++;

                                day = String.valueOf(i2);
                                month = String.valueOf(i1);
                                year = String.valueOf(i);

                                if(i2 < 10){
                                    day = "0" + day;
                                }

                                if(i1 < 10){
                                    month = "0" + month;
                                }
                                txtDate.setText(day + "." + month + "." + year + " - " + dayOfWeek);
                                date = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day)).getTime();
                                getMeals(date);
                                initFirebase(date);
                            }
                        },y,m,d);
                        pickerDialog.show();
            }
        });
        //<!-----btnDate Date Picker Dialog----->

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (!likeDislikeObject.getHasLiked()) {
                //    likeDislikeObject.setHasLiked(true);
                    likeDislikeObject.setLike(likeDislikeObject.getLike() + 1);
                    dateDataSnapshot.getRef().setValue(likeDislikeObject);
                //}
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (!likeDislikeObject.getHasDisliked()) {
                //   likeDislikeObject.setHasDisliked(true);
                    likeDislikeObject.setDislike(likeDislikeObject.getDislike() + 1);
                    dateDataSnapshot.getRef().setValue(likeDislikeObject);
                //}

            }
        });
    }

    //<-------LikeDislike------>
    private void initFirebase(Date date) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("tr"));
        dateLikeDislike = simpleDateFormat.format(date);

        DatabaseReference LikeRef = database.getReference("likes/likeObjects/"+dateLikeDislike);
        LikeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dateDataSnapshot = dataSnapshot;
                likeDislikeObject = dataSnapshot.getValue(LikeDislike.class);
                if(likeDislikeObject == null){
                    likeDislikeObject = new LikeDislike(0,0);
                    FirebaseDatabase.getInstance().getReference()
                            .child("likes")
                            .child("likeObjects")
                            .child(dateLikeDislike).setValue(likeDislikeObject);
                }

                txtLike.setText(Integer.toString(likeDislikeObject.getLike()));
                txtDislike.setText(Integer.toString(likeDislikeObject.getDislike()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                int y = 0;
            }
        });

    }
    //<!-------LikeDislike------>



    //<-----Change Color of the Chips----->
    private void setCalorieColors(Meal mealOfDay) {
        GradientDrawable chipSoupCalBackground = (GradientDrawable) txtSoupCal.getBackground();
        GradientDrawable chipMainCourseCalBackground = (GradientDrawable) txtMainCourseCal.getBackground();
        GradientDrawable chipAlternativeMealCalBackground = (GradientDrawable) txtAlternativeMealCal.getBackground();
        GradientDrawable chipSupplementalMealCalBackground = (GradientDrawable) txtSupplementalMealCal.getBackground();
        GradientDrawable chipDessertCalBackground = (GradientDrawable) txtDessertCal.getBackground();
        GradientDrawable chipTotalCalBackground = (GradientDrawable) txtTotalCal.getBackground();

        chipSoupCalBackground.setColor(getColorOfCalorie(mealOfDay.SoupCalorie));
        chipMainCourseCalBackground.setColor(getColorOfCalorie(mealOfDay.MainCourseCalorie));
        chipAlternativeMealCalBackground.setColor(getColorOfCalorie(mealOfDay.AlternativeMealCalorie));
        chipSupplementalMealCalBackground.setColor(getColorOfCalorie(mealOfDay.SupplementalMealCalorie));
        chipDessertCalBackground.setColor(getColorOfCalorie(mealOfDay.DessertCalorie));
        chipTotalCalBackground.setColor(getColorOfCalorie(totalcal));

        //<!-----Change Color of the Chips----->
    }



}
