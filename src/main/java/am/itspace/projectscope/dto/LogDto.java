package am.itspace.projectscope.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode
@NoArgsConstructor
public class LogDto {

    private Integer logId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int hours;

    private UserDto userDao;

    private ProjectDto projectDto;
}
