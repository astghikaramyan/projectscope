package am.itspace.projectscope.entity;

import am.itspace.projectscope.model.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    private String name;

    private String surname;

    private String email;

    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Fetch(FetchMode.SELECT)
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Set<ProjectEntity> projectEntities;

    //    @OneToMany(
//            mappedBy = "userEntity",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            orphanRemoval = true
//    )
//    @Fetch(FetchMode.SELECT)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
//    @OneToMany(mappedBy = "userEntity")
    private Set<LogEntity> logEntities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return userId.equals(that.userId) &&
                email.equals(that.email) &&
                password.equals(that.password) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password, type);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", type=" + type +
                '}';
    }
}
