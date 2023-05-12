package Objects;

import java.util.Arrays;
import java.util.Objects;
//Clase de la que heredan los dos tipos de usuarios: Coachs y Clients
public class User {
    private String nameSurname;
    private String email;
    private String pasword;
    private int age;
    private String uid;

    public User(String nameSurname, String email, String pasword, int age, String uid){
        this.nameSurname=nameSurname;
        this.email=email;
        this.pasword=pasword;
        this.age=age;
        this.uid=uid;
    }
    public User(String nameSurname, String email, String pasword, int age){
        this.nameSurname=nameSurname;
        this.email=email;
        this.pasword=pasword;
        this.age=age;
        this.uid="Prueba";
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public String getEmail() {
        return email;
    }

    public String getPasword() {
        return pasword;
    }

    public int getAge() {
        return age;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public String toString() {
        return "Objects.User{" +
                "nameSurname=" + nameSurname +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }


}

