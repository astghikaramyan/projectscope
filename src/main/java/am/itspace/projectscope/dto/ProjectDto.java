package am.itspace.projectscope.dto;

import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class ProjectDto {
    private Integer projectId;

    private String projectName;

    private LocalDate date;

    private LocalDate deadline;

    Set<UserDto> userDtos;

    Set<LogDto> logDtos;
}
