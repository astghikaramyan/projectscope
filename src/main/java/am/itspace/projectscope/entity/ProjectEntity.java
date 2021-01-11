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

    @Column(name = "project_name")
    private String projectName;

    private LocalDate date;

    private LocalDate deadline;

    @ManyToMany(mappedBy = "projectEntities")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Set<UserEntity> userEntities;


    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
//            fetch = FetchType.EAGER,
            mappedBy = "projectEntity")
    @Fetch(FetchMode.SELECT)

//    @OneToMany(mappedBy = "projectEntity")
    private Set<LogEntity> logEntities;


    @Override
    public String toString() {
        return "ProjectEntity{" +
                "projectId=" + projectId +
                ", name='" + projectName + '\'' +
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
                projectName.equals(that.projectName) &&
                date.equals(that.date) &&
                deadline.equals(that.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, date, deadline);
    }
}
