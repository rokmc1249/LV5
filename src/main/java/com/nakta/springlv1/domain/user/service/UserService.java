package com.nakta.springlv1.domain.user.service;

import com.nakta.springlv1.domain.user.dto.SignupRequestDto;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.entity.User;
import com.nakta.springlv1.domain.user.jwt.UserRoleEnum;
import com.nakta.springlv1.domain.user.repository.UserRepository;
import com.nakta.springlv1.global.exception.CustomException;
import com.nakta.springlv1.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public StringResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> tmpUser = userRepository.findByUsername(username);
        if (tmpUser.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_ID);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER; // 일반 사용자 권한 넣어줌
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new CustomException(ErrorCode.ADMINTOKEN_NOT_MATCH);
            }
            role = UserRoleEnum.ADMIN; // 일치하면 ADMIN 권한으로 덮어씌움
        }
        User user = new User(username,password,role);
        userRepository.save(user);
        return new StringResponseDto( "새로운 아이디 저장 성공 ㅋㅋ");

    }
}
