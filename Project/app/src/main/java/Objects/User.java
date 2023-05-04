package Objects;

import java.util.Arrays;
import java.util.Objects;
//Clase de la que heredan los dos tipos de usuarios: Coachs y Clients
public class User {
    private String[] nameSurname;
    private String email;
    private String pasword;
    private int age;

    public User(String nameSurname, String email, String pasword, int age){
        this.nameSurname=nameSurname.split(" ");
        this.email=email;
        this.pasword=pasword;
        this.age=age;
    }

    public String[] getNameSurname() {
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

    public void setNameSurname(String[] nameSurname) {
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


    @Override
    public String toString() {
        return "Objects.User{" +
                "nameSurname=" + Arrays.toString(nameSurname) +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Arrays.equals(nameSurname, user.nameSurname) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(email, age);
        result = 31 * result + Arrays.hashCode(nameSurname);
        return result;
    }

}

