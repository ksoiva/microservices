package nulp.trspo.user.domain.service;

import lombok.RequiredArgsConstructor;
import nulp.trspo.user.persistence.entity.UserEntity;
import nulp.trspo.user.persistence.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity addUser(UserEntity user){
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#userId")
    public void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    @Cacheable(value = "users", key = "#userId")
    public Optional<UserEntity> getUserById(Long userId){
        System.out.println("to DB");
        return userRepository.findById(userId);
    }

    @CachePut(value = "users", key = "#userId")
    public UserEntity updateUserById(Long userId, UserEntity newUser){
        newUser.setId(userId);
        return userRepository.save(newUser);
    }
}
