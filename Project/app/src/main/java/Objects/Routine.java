package Objects;

import java.util.ArrayList;

public class Routine {
    private ArrayList<Exercice> routine;
    private String name;

    public Routine(ArrayList<Exercice> routine, String name) {
        this.routine = routine;
        this.name = name;
    }

    public ArrayList<Exercice> getRoutine() {
        return routine;
    }

    public void setRoutine(ArrayList<Exercice> routine) {
        this.routine = routine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void newExercice(Exercice exercice){
        this.routine.add(exercice);
    }
    public void eraseExercice(Exercice exercice){
        routine.remove(exercice);
    }
}
