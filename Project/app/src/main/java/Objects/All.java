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

    public int loginPass(String email, String password) {
        int result=-1; //-1 -> not found & | 0 -> Client | 1 -> Coach | 2 -> Duplicate | 3 -> Wrong password
        for (Client client : allClients){
            if(client.getEmail().equals(email)){
                if(client.getPasword().equals(password)){
                    result=0;
                }else
                    result=3;

            }
        }
        for (Coach coach : allCoachs){
            if(coach.getEmail().equals(email)){
                if(result==0){
                    result=2; //Ha encontrado un cliente con el mismo correo
                }else{
                    if (coach.getPasword().equals(password)){
                        result=1;
                    }else
                        result=3;
                }
            }
        }
        return result;
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
