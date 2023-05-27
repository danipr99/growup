package Objects;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

public class Exercice {

    private int series;
    private String name;
    private int kg;
    private int repetitions;
    private int rec;
    private int rir;
    private int volume;
    private String details;
    private int feedback;
    private String photo;
     public Exercice(String name, int series,int kg,int rec, int rir, String details, String photo){
         this.series = series;
         this.name=name;
         this.kg=kg;
         this.rec=rec;
         this.rir=rir;
         this.volume=kg*repetitions;
         this.details=details;
         this.feedback=7; //Default
         this.photo=photo;
     }


    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getRec() {
        return rec;
    }

    public void setRec(int rec) {
        this.rec = rec;
    }

    public int getRir() {
        return rir;
    }

    public void setRir(int rir) {
        this.rir = rir;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
