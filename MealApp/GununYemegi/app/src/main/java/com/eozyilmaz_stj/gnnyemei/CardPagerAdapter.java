package com.eozyilmaz_stj.gnnyemei;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<Meal> mData;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(Meal item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.activity_adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(Meal item, View view) {
        TextView txtDate = (TextView) view.findViewById(R.id.txtDateView);
        TextView txtMainCourse = (TextView) view.findViewById(R.id.txtMainCourseView);
        TextView txtSoup = (TextView) view.findViewById(R.id.txtSoupView);
        TextView txtSoupCal = (TextView) view.findViewById(R.id.txtSoupCalView);
        TextView txtMainCourseCal = (TextView) view.findViewById(R.id.txtMainCourseCalView);
        TextView txtAlternativeMeal = (TextView) view.findViewById(R.id.txtAlternativeMealView);
        TextView txtAlternativeMealCal = (TextView) view.findViewById(R.id.txtAlternativeMealCalView);
        TextView txtSupplementalMeal = (TextView) view.findViewById(R.id.txtSupplementalMealView);
        TextView txtSupplementalMealCal = (TextView) view.findViewById(R.id.txtSupplementalMealCalView);
        TextView txtDessert = (TextView) view.findViewById(R.id.txtDessertView);
        TextView txtDessertCal = (TextView) view.findViewById(R.id.txtDessertCalView);
        TextView txtTotalCal = (TextView) view.findViewById(R.id.txtTotalCalView);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = format.parse(item.getMealDate());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy - EEEE", new Locale("tr"));
            String currentDate = simpleDateFormat.format(date);
            txtDate.setText(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtMainCourse.setText(item.getMainCourse());
        txtSoup.setText(item.getSoup());
        txtSoupCal.setText(item.getSoupCalorie() + " kalori");
        txtMainCourseCal.setText(item.getMainCourseCalorie() + " kalori");
        txtAlternativeMeal.setText(item.getAlternativeMeal());
        txtAlternativeMealCal.setText(item.getAlternativeMealCalorie() + " kalori");
        txtSupplementalMeal.setText(item.getSupplementalMeal());
        txtSupplementalMealCal.setText(item.getSupplementalMealCalorie() + " kalori");
        txtDessert.setText(item.getDessert());
        txtDessertCal.setText(item.getDessertCalorie() + " kalori");

        int totalCal =  item.getSoupCalorie() + item.getMainCourseCalorie() + item.getAlternativeMealCalorie() + item.getSupplementalMealCalorie() + item.getDessertCalorie();

        txtTotalCal.setText(totalCal + " kalori");


    }
}
