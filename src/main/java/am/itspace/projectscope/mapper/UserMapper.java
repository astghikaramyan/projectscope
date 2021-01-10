package am.itspace.projectscope.mapper;

import am.itspace.projectscope.dto.UserDto;
import am.itspace.projectscope.entity.UserEntity;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper extends Mapper<UserEntity, UserDto>{

}
