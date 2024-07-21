package com.turfnovo.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.turfnovo.auth.dto.request.LoginRequestDto;
import com.turfnovo.auth.dto.request.SignupRequestDto;
import com.turfnovo.auth.dto.request.UserUpdateDto;
import com.turfnovo.auth.dto.response.JwtResponseDto;
import com.turfnovo.auth.model.User;
import com.turfnovo.auth.repository.UserRepository;
import com.turfnovo.auth.security.JwtUtils;
import com.turfnovo.global.utils.Utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDto authenticateUser(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponseDto(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public User createUser(SignupRequestDto signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("User already exit with email: " + signupRequest.getEmail());
        }
        if (!Utils.isValidPhoneNumber(signupRequest.getPhoneNo())) {
            throw new RuntimeException("Invalid phone number: " + signupRequest.getPhoneNo());
        }
        User user = modelMapper.map(signupRequest, User.class);
        user.setUsername(user.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(String token, UserUpdateDto userUpdateDto) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User user = userRepository.findByUsername(email).get();

        user.updateNonNullFields(modelMapper.map(userUpdateDto, User.class));
        if (userUpdateDto.getPassword() != null)
            user.setPassword(encoder.encode(userUpdateDto.getPassword()));

        return userRepository.save(user);
    }
}
