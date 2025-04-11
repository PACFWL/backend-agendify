
package com.fatec.agendify.agendify.service.user;

import com.fatec.agendify.agendify.dto.user.LoginResponseDTO;
import com.fatec.agendify.agendify.dto.user.UserCreateDTO;
import com.fatec.agendify.agendify.dto.user.UserDTO;
import com.fatec.agendify.agendify.dto.user.UserLoginDTO;
import com.fatec.agendify.agendify.dto.user.UserUpdateDTO;
import com.fatec.agendify.agendify.mapper.UserMapper;
import com.fatec.agendify.agendify.model.user.User;
import com.fatec.agendify.agendify.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

public LoginResponseDTO loginUser(UserLoginDTO userDTO) {
    Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

    if (existingUser.isPresent() &&
        passwordEncoder.matches(userDTO.getPassword(), existingUser.get().getPassword())) {
        System.out.println("Senha fornecida: " + userDTO.getPassword());
        System.out.println("Senha armazenada (hash): " + existingUser.get().getPassword());
        String token = generateToken(existingUser.get());
        String role = existingUser.get().getRole().name();

        return new LoginResponseDTO(token, role);
    }

    throw new RuntimeException("Email ou senha inválidos.");
}



    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId()) 
                .claim("email", user.getEmail()) 
                .claim("roles", List.of(user.getRole().name())) 
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    
    public List<UserDTO> getUsersByRole(String role) {
        return userRepository.findByRole(User.Role.valueOf(role.toUpperCase())).stream()
                .map(UserMapper::toDTO)
                .toList();
    }

   public String getAuthenticatedUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    
    if (authentication == null || !authentication.isAuthenticated()) {
        throw new RuntimeException("Usuário não autenticado");
    }

    String token = null;

    if (authentication.getCredentials() instanceof String jwt) {
        token = jwt;
    } else if (authentication.getPrincipal() instanceof UserDetails userDetails) {
        return userDetails.getUsername();
    }

    if (token == null || token.trim().isEmpty()) {
        throw new IllegalArgumentException("JWT String argument cannot be null or empty.");
    }

    Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

            System.out.println("Roles no JWT: " + claims.get("roles"));

    return claims.getSubject(); 

}

public User.Role getAuthenticatedUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
        throw new RuntimeException("Usuário não autenticado");
    }

    String token = null;

    if (authentication.getCredentials() instanceof String jwt) {
        token = jwt;
    } else if (authentication.getPrincipal() instanceof UserDetails userDetails) {

        String roleName = userDetails.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .orElse("REQUESTER");
        return User.Role.valueOf(roleName);
    }

    if (token == null || token.trim().isEmpty()) {
        throw new IllegalArgumentException("JWT String argument cannot be null or empty.");
    }

    Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

    @SuppressWarnings("unchecked")
    List<String> roles = claims.get("roles", List.class);
    
    String roleName = roles != null && !roles.isEmpty() ? roles.get(0) : "REQUESTER";

    return User.Role.valueOf(roleName);
}

}