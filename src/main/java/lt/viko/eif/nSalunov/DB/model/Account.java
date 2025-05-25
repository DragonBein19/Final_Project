package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "Login")
    private String Login;
    @Column(name = "Password")
    private String Password;

    //Constractors, getters, setters

    public Account() {}

    public Account(Long ID, String login, String password) {
        this.ID = ID;
        Login = login;
        Password = password;
    }

    public Long getID() { return ID; }
    public void setID(Long ID) { this.ID = ID; }

    public String getLogin() { return Login; }
    public void setLogin(String login) { Login = login; }

    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }
}
