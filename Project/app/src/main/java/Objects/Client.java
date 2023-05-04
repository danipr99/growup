package Objects;

public class Client extends User{
    private Coach myCoach;
    public Client(String nameSurname, String email, String password, int age, Coach myCoach) {
        super(nameSurname, email, password, age);
        this.myCoach=myCoach;
    }

    public Coach getMyCoach() {
        return myCoach;
    }

    public void setMyCoach(Coach coach){
        if(this.myCoach.isMyClient(this)){//Elimino al cliente de la lista de clientes de su antiguo Coach
            this.myCoach.deleteClient(this);
        }
        coach.newClient(this);
        this.myCoach=coach;
    }
}
