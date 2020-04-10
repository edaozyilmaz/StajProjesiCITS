package com.eozyilmaz_stj.gnnyemei;


public class Meal {
    int Id;
    String MealDate;
    String Soup;
    int SoupCalorie;
    String MainCourse;
    int MainCourseCalorie;
    String AlternativeMeal;
    int AlternativeMealCalorie;
    String SupplementalMeal;
    int SupplementalMealCalorie;
    String Dessert;
    int DessertCalorie;
    int TotalCalorie;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMealDate() {
        return MealDate;
    }

    public void setMealDate(String mealDate) {
        MealDate = mealDate;
    }

    public String getSoup() {
        return Soup;
    }

    public void setSoup(String soup) {
        Soup = soup;
    }

    public int getSoupCalorie() {
        return SoupCalorie;
    }

    public void setSoupCalorie(int soupCalorie) {
        SoupCalorie = soupCalorie;
    }

    public String getMainCourse() {
        return MainCourse;
    }

    public void setMainCourse(String mainCourse) {
        MainCourse = mainCourse;
    }

    public int getMainCourseCalorie() {
        return MainCourseCalorie;
    }

    public void setMainCourseCalorie(int mainCourseCalorie) {
        MainCourseCalorie = mainCourseCalorie;
    }

    public String getAlternativeMeal() {
        return AlternativeMeal;
    }

    public void setAlternativeMeal(String alternativeMeal) {
        AlternativeMeal = alternativeMeal;
    }

    public int getAlternativeMealCalorie() {
        return AlternativeMealCalorie;
    }

    public void setAlternativeMealCalorie(int alternativeMealCalorie) {
        AlternativeMealCalorie = alternativeMealCalorie;
    }

    public String getSupplementalMeal() {
        return SupplementalMeal;
    }

    public void setSupplementalMeal(String supplementalMeal) {
        SupplementalMeal = supplementalMeal;
    }

    public int getSupplementalMealCalorie() {
        return SupplementalMealCalorie;
    }

    public void setSupplementalMealCalorie(int supplementalMealCalorie) {
        SupplementalMealCalorie = supplementalMealCalorie;
    }

    public String getDessert() {
        return Dessert;
    }

    public void setDessert(String dessert) {
        Dessert = dessert;
    }

    public int getDessertCalorie() {
        return DessertCalorie;
    }

    public void setDessertCalorie(int dessertCalorie) {
        DessertCalorie = dessertCalorie;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "Id=" + Id +
                ", MealDate=" + MealDate +
                ", Soup='" + Soup + '\'' +
                ", SoupCalorie=" + SoupCalorie +
                ", MainCourse='" + MainCourse + '\'' +
                ", MainCourseCalorie=" + MainCourseCalorie +
                ", AlternativeMeal='" + AlternativeMeal + '\'' +
                ", AlternativeMealCalorie=" + AlternativeMealCalorie +
                ", SupplementalMeal='" + SupplementalMeal + '\'' +
                ", SupplementalMealCalorie=" + SupplementalMealCalorie +
                ", Dessert='" + Dessert + '\'' +
                ", DessertCalorie=" + DessertCalorie +
                '}';
    }
}
