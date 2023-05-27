package Objects;

import java.util.ArrayList;
import java.util.List;

public class Diet {
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snack;
    private String recommendations;
    private int kcal;
    private int carbs;
    private int prot;
    private int fat;
    private String uidCoach;
    private boolean use;


    public Diet(String breakfast, String lunch, String dinner, String snack, String recommendations, int kcal, int carbs, int prot, int fat, String uidCoach) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snack = snack;
        this.recommendations = recommendations;
        this.kcal = kcal;
        this.carbs = carbs;
        this.prot = prot;
        this.fat = fat;
        this.uidCoach = uidCoach;
        this.use = false;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public String getUidCoach() {
        return uidCoach;
    }

    public void setUidCoach(String uidCoach) {
        this.uidCoach = uidCoach;
    }



    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getSnack() {
        return snack;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProt() {
        return prot;
    }

    public void setProt(int prot) {
        this.prot = prot;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
}
