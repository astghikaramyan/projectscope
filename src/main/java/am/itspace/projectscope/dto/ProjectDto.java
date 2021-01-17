package am.itspace.projectscope.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
