package am.itspace.projectscope.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "project")
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    private String name;

    private LocalDate date;

    private LocalDate deadline;

    @ManyToMany(mappedBy = "projectEntities")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    Set<UserEntity> userEntities;

    //    @OneToMany(
//            mappedBy = "projectEntity",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            orphanRemoval = true
//    )
//    @Fetch(FetchMode.SELECT)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectEntity")
//    @OneToMany(mappedBy = "projectEntity")
            Set<LogEntity> logEntities;


    @Override
    public String toString() {
        return "ProjectEntity{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", deadline=" + deadline +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectEntity)) return false;
        ProjectEntity that = (ProjectEntity) o;
        return projectId.equals(that.projectId) &&
                name.equals(that.name) &&
                date.equals(that.date) &&
                deadline.equals(that.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, name, date, deadline);
    }
}
