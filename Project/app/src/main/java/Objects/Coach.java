package Objects;

import java.util.ArrayList;
import java.util.List;

public class Coach extends User{

    private List<Client> myClients;


    public Coach(String nameSurname, String email, String password, int age,String uid, List<Client> myClients) {
        super(nameSurname, email, password, age, uid);
        this.myClients=myClients;
    }
    public Coach(String nameSurname, String email, String password, int age, String uid) {
        super(nameSurname, email, password, age, uid);
        this.myClients= new ArrayList<Client>();
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
