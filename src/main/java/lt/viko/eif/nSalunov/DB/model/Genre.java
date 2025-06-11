package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entity representing a music genre.
 * <p>
 * Genres can be associated with multiple artists in a many-to-many relationship.
 * </p>
 */

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Artist> artists;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
