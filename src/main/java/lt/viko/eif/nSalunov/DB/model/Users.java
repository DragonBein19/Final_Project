package lt.viko.eif.nSalunov.DB.model;

import java.time.DateTimeException;
import java.util.Date;

public class Users {
    private int ID;
    private String UserName;
    private String Name;
    private String Surname;
    private String Email;
    private String Phone;
    private String Password;
    private Date Registration_Date;
    private Boolean IsAdmin;

    public Users(int ID, String userName, String name, String surname, String email, String phone, String password, Date registration_Date, Boolean isAdmin) {
        this.ID = ID;
        UserName = userName;
        Name = name;
        Surname = surname;
        Email = email;
        Phone = phone;
        Password = password;
        Registration_Date = registration_Date;
        IsAdmin = isAdmin;
    }

    public Users() { }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getUserName() { return UserName; }
    public void setUserName(String userName) { UserName = userName; }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }

    public String getSurname() { return Surname; }
    public void setSurname(String surname) { Surname = surname; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getPhone() { return Phone; }
    public void setPhone(String phone) { Phone = phone; }

    public String getPassword() { return Password;}
    public void setPassword(String password) { Password = password; }

    public Date getRegistration_Date() { return Registration_Date; }
    public void setRegistration_Date(Date registration_Date) { Registration_Date = registration_Date; }

    public Boolean getAdmin() { return IsAdmin; }
    public void setAdmin(Boolean admin) { IsAdmin = admin; }
}
