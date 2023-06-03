package Objects;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class Coach extends User{

    private List<Client> myClients;

    public Coach(){
        super("", "", 0);
        this.myClients = new ArrayList<Client>();
    }

    public Coach(String nameSurname, String email, int age,String uid, List<Client> myClients) {
        super(nameSurname, email, age, uid);
        this.myClients=myClients;
    }


    public void setMyClients(List<Client> myClients) {
        this.myClients = myClients;
    }

    public List<Client> getMyClients() {
        return myClients;
    }
    public boolean isMyClient(Client client){
        return myClients.contains(client);
    }
    public void newClient(Client client){
        myClients.add(client);
    }
    public void deleteClient(Client client){
        myClients.remove(client);
    }
}
