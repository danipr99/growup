package Objects;

import java.util.ArrayList;

public class Diet {
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snack;
    private String recommendations;
    private ArrayList<Integer> macros; // [kcal][carbs][prot][fat]

    public Diet(String breakfast, String lunch, String dinner, String snack, String recommendations, ArrayList<Integer> macros) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snack = snack;
        this.recommendations = recommendations;
        this.macros = macros;
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

    public ArrayList<Integer> getMacros() {
        return macros;
    }

    public void setMacros(ArrayList<Integer> macros) {
        this.macros = macros;
    }
}
