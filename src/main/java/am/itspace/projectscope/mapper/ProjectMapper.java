package am.itspace.projectscope.mapper;

import am.itspace.projectscope.dto.ProjectDto;
import am.itspace.projectscope.dto.UserDto;
import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.entity.UserEntity;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ProjectMapper extends Mapper<ProjectEntity, ProjectDto> {
}
