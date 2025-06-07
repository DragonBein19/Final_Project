package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", length = 45)
    private String phoneNumber;

    @Column(name = "address", length = 45)
    private String address;

    @Column(length = 45)
    private String city;

    @Column(length = 45)
    private String country;

    @Column(length = 45)
    private String capacity;

    @Column(nullable = false)
    private boolean indoor;

    @Column(name = "map_url", length = 45)
    private String mapUrl;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private Set<Concert> concerts;

    public Venue() {}

    public Venue(String phoneNumber, String address, String city, String country,
                 String capacity, boolean indoor, String mapUrl) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.capacity = capacity;
        this.indoor = indoor;
        this.mapUrl = mapUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }

    public boolean isIndoor() { return indoor; }
    public void setIndoor(boolean indoor) { this.indoor = indoor; }

    public String getMapUrl() { return mapUrl; }
    public void setMapUrl(String mapUrl) { this.mapUrl = mapUrl; }
}
