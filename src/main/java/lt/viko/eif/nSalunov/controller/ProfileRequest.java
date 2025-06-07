package lt.viko.eif.nSalunov.controller;

public class ProfileRequest {
    private String Name;
    private String SureName;
    private String Email;
    private String Phone;
    private String Registration_Date;

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }

    public String getSureName() { return SureName; }
    public void setSureName(String sureName) { SureName = sureName; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getPhone() { return Phone; }
    public void setPhone(String phone) { Phone = phone; }

    public String getRegistration_Date() { return Registration_Date; }
    public void setRegistration_Date(String registration_Date) { Registration_Date = registration_Date; }
}
