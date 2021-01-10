package am.itspace.projectscope.mapper;

import am.itspace.projectscope.dto.LogDto;
import am.itspace.projectscope.dto.UserDto;
import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.UserEntity;

@org.mapstruct.Mapper(componentModel = "spring")
public interface LogMapper extends Mapper<LogEntity, LogDto> {
}
