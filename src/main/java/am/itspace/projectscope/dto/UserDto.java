package am.itspace.projectscope.dto;

import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.model.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class UserDto {

    private Integer userId;

    @NotBlank(message = "Name cannot be missing or empty.")
    private String userName;

    private String surname;

    @Email
    private String email;

    private String password;

    private String profilePicture;

    private Type type;

    private Set<ProjectDto> projectDtos;

    private Set<LogDto> logDtos;
}
