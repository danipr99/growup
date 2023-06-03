package Objects;
import java.util.ArrayList;

public class All {

    private static ArrayList<Client> allClients;
    private static ArrayList<Coach>  allCoachs;
    private static Coach coach;
    private static Client client;

    public All() {
        allClients = new ArrayList<Client>();
        allCoachs =  new ArrayList<Coach>();
    }
    public All(ArrayList<Client> allClient, ArrayList<Coach> allCoach) {
        allClients = allClient;
        allCoachs = allCoach;
    }

    public ArrayList<Client> getAllClients() {
        return allClients;
    }

    public void setAllClients(ArrayList<Client> allClient) {
        allClients = allClient;
    }

    public ArrayList<Coach> getAllCoachs() {
        return allCoachs;
    }
    public ArrayList<String> getAllCoachsString() {
        ArrayList<String> result = new ArrayList<>();
        allCoachs.forEach(e -> result.add(e.getNameSurname()));
        return result;
    }

    public void setAllCoachs(ArrayList<Coach> allCoach) {
        allCoachs = allCoach;
    }
    public void newCoach(Coach coach){
        if(!allCoachs.contains(coach)){
            allCoachs.add(coach);
        }
    }
    public void newClient(Client client){
        if(!allClients.contains(client)){
            allClients.add(client);
        }
    }


    public Coach searchCoach(String coachString){
        for(Coach coach : allCoachs){
            if(coach.getNameSurname().equals(coachString)){
                return coach;
            }
        }
        return null;
    }
}
