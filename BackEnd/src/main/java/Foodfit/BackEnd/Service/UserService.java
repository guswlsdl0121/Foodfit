package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Auth.ChangeableToUser;
import Foodfit.BackEnd.DTO.UserUpdateDTO;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void updateUser(Long id, UserUpdateDTO dto){
        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));

        findUser.setName(dto.getName());
        findUser.setGender(dto.getGender());
        findUser.setAge(dto.getAge());
    }
}
