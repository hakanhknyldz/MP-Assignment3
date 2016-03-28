package hakanyildiz.co.assingment3.MyClasses;

/**
 * Created by hakan on 23.03.2016.
 */
public class User {
    int id;
    String email;
    String password;

    public User()
    {

    }

    public User(String email, String password)
    {

        this.email =email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
