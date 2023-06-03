package Objects;

public class Client extends User{
    private String uidMyCoach;
    private boolean isBulk;

    public Client(){
        super("", "",  0);
        this.uidMyCoach="";
    }

    public Client(String nameSurname, String email, int age, String uid, String uidMyCoach, boolean isBulk) {
        super(nameSurname, email, age, uid);
        this.uidMyCoach=uidMyCoach;
        this.isBulk=isBulk;
    }

    public boolean isBulk() {
        return isBulk;
    }

    public void setBulk(boolean bulk) {
        isBulk = bulk;
    }

    public String getUidMyCoach() {
        return uidMyCoach;
    }

    public void setUidMyCoach(String coach){
        this.uidMyCoach = coach;

    }
}
