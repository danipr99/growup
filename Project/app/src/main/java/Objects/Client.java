package Objects;

public class Client extends User{
    private String uidMyCoach;

    public Client(){
        super("", "", "", 0);
        this.uidMyCoach="";
    }

    public Client(String nameSurname, String email, String password, int age, String uid, String uidMyCoach) {
        super(nameSurname, email, password, age, uid);
        this.uidMyCoach=uidMyCoach;
    }

    public String getUidMyCoach() {
        return uidMyCoach;
    }

    public void setUidMyCoach(String coach){
        this.uidMyCoach = coach;
        /*
        if(this.myCoach.isMyClient(this)){//Elimino al cliente de la lista de clientes de su antiguo Coach
            this.myCoach.deleteClient(this);
        }
        coach.newClient(this);
        this.myCoach=coach;*/
    }
}
