package org.sopt.service;

import org.sopt.domain.User;
import org.sopt.dto.request.UserCreateRequest;
import org.sopt.dto.response.UserCreateResponse;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.util.Validator.UserValidator.isNameValid;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입 기능
    @Transactional
    public UserCreateResponse createUser(UserCreateRequest request){
        String name = request.name();

        isNameValid(name);

        User user = new User(name);
        userRepository.save(user);
        return new UserCreateResponse(user.getId());
    }


}
