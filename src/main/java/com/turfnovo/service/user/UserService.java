package com.turfnovo.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.turfnovo.dto.request.user.LoginRequestDto;
import com.turfnovo.dto.request.user.SignupRequestDto;
import com.turfnovo.dto.request.user.UserUpdateDto;
import com.turfnovo.dto.response.user.JwtResponseDto;
import com.turfnovo.dto.response.user.UserResponseDto;
import com.turfnovo.model.user.User;
import com.turfnovo.repository.user.UserRepository;
import com.turfnovo.security.JwtUtils;
import com.turfnovo.utils.Utils;

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
            throw new RuntimeException("User already exist with email: " + signupRequest.getEmail());
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

        Utils.copyNonNullProperties(userUpdateDto, user);
        if (userUpdateDto.getPassword() != null)
            user.setPassword(encoder.encode(userUpdateDto.getPassword()));

        return userRepository.save(user);
    }

    public UserResponseDto getUser(String token) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        return modelMapper.map(userRepository.findByUsername(email).orElse(null), UserResponseDto.class);
    }
}
