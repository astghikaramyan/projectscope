package am.itspace.projectscope.responsemodel;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Project {
    private String projectName;
    private LocalDate date;
    private LocalDate deadline;
    private String members;
    private int hours;
}
