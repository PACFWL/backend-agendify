
package com.fatec.agendify.agendify.service.user;

import com.fatec.agendify.agendify.dto.user.UserCreateDTO;
import com.fatec.agendify.agendify.dto.user.UserDTO;
import com.fatec.agendify.agendify.dto.user.UserLoginDTO;
import com.fatec.agendify.agendify.dto.user.UserUpdateDTO;
import com.fatec.agendify.agendify.mapper.UserMapper;
import com.fatec.agendify.agendify.model.User;
import com.fatec.agendify.agendify.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
   
    @Autowired
    private UserRepository userRepository;

    private static final String SECRET_KEY = System.getenv("SECRET_KEY");

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    
    public UserDTO createUser(UserCreateDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDTO);
    }

    public Optional<UserDTO> getUserById(String id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    public UserDTO updateUser(String id, UserUpdateDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        UserMapper.updateEntityFromDTO(user, userDTO);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public String loginUser(UserLoginDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && passwordEncoder.matches(userDTO.getPassword(), existingUser.get().getPassword())) {
            System.out.println("Senha fornecida: " + userDTO.getPassword());
            System.out.println("Senha armazenada (hash): " + existingUser.get().getPassword());
            return generateToken(existingUser.get());
        }
        throw new RuntimeException("Invalid email or password");
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public List<UserDTO> getUsersByRole(String role) {
        return userRepository.findByRole(User.Role.valueOf(role.toUpperCase())).stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}