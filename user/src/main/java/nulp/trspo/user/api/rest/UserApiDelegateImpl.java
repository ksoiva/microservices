package nulp.trspo.user.api.rest;

import lombok.RequiredArgsConstructor;
import nulp.trspo.user.api.UserApiDelegate;
import nulp.trspo.user.model.UserDto;
import nulp.trspo.user.domain.service.UserService;
import nulp.trspo.user.mapping.UserMapper;
import nulp.trspo.user.persistence.entity.UserEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UserApiDelegateImpl implements UserApiDelegate {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserDto> addUser(UserDto userDto) {
        UserEntity userEntity = userMapper.userDtoToUser(userDto);
        UserDto savedUser = userMapper.userToUserDTO(userService.addUser(userEntity));
        return ResponseEntity.ok(savedUser);
    }


    @Override
    public ResponseEntity<Void> deleteUserById(BigDecimal userId) {
        userService.deleteUserById(userId.longValue());
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<UserDto> getUserById(BigDecimal userId) {
        return userService.getUserById(userId.longValue())
                .map(user -> ResponseEntity.ok(userMapper.userToUserDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }


    @Override
    public ResponseEntity<UserDto> updateUserById(BigDecimal userId, UserDto userDto) {
        UserEntity userEntity = userMapper.userDtoToUser(userDto);
        UserDto updatedUser = userMapper.userToUserDTO(userService.updateUserById(userId.longValue(), userEntity));
        return ResponseEntity.ok(updatedUser);
    }
}