package Objects;

import java.util.ArrayList;

public class Coach extends User{

    private ArrayList<Client> myClients;

    public Coach(String nameSurname, String email, String password, int age, ArrayList<Client> myClients) {
        super(nameSurname, email, password, age);
        this.myClients=myClients;
    }
    public Coach(String nameSurname, String email, String password, int age) {
        super(nameSurname, email, password, age);
        this.myClients= new ArrayList<Client>();
    }

    public ArrayList<Client> getMyClients() {
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
