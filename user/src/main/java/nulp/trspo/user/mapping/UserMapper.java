package nulp.trspo.user.mapping;

import nulp.trspo.user.model.UserDto;
import nulp.trspo.user.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

    UserDto userToUserDTO(UserEntity userEntity);
    UserEntity userDtoToUser(UserDto userDto);
}
