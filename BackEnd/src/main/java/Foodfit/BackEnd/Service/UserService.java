package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.UserUpdateDTO;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void updateUser(Long id, UserUpdateDTO dto){
        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));

        findUser.setName(dto.getName());
        findUser.setGender(dto.getGender());
        findUser.setAge(dto.getAge());
    }

    public void updateProfileImage(Long id, MultipartFile image) throws Exception {
        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));
        log.info("expr = {}", image);
        byte [] byteImage = new ClassPathResource("defaultProfileImage.png").getContentAsByteArray();

        if(!image.isEmpty()) byteImage = image.getBytes();

        log.info("image.getBytes() = {}",  image.getBytes());

        findUser.setProfileImage(byteImage);
    }

}
