package Objects;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Exercice {
    private static int contador=0;
    private int id;
    private String name;
    private ArrayList<Integer> kg;// kg
    private ArrayList<Integer> info; // Repetitions [0] | REC [1] | RIR [2][3]
    private int volume;
    private String details;
    private int feedback; // -1 without feedback  |  0  injured  |  1
    private Bitmap photo;
     public Exercice(String name, ArrayList<Integer> kg, ArrayList<Integer> info, String details, Bitmap photo){
         this.id=contador++;
         this.name=name;
         this.kg = kg;
         this.info=info;
         kg.forEach((element) -> {
             this.volume+=element;
         });
         this.volume=this.volume*info.get(0);

         this.details=details;
         this.feedback=-1;
         this.photo=photo;
     }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Exercice.contador = contador;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getKg() {
        return kg;
    }

    public void setKg(ArrayList<Integer> kg) {
        this.kg = kg;
    }

    public ArrayList<Integer> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Integer> info) {
        this.info = info;
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

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

}
