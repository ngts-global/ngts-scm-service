package com.ngts.auth.controllers;

import java.util.HashSet;
import java.util.Set;
import com.ngts.auth.payload.request.LoginRequest;
import com.ngts.auth.payload.request.SignupRequest;
import com.ngts.auth.payload.response.MessageResponse;
import com.ngts.auth.payload.response.UserInfoResponse;
import com.ngts.auth.repository.UsersRepository;
import com.ngts.auth.service.UsersService;
import com.ngts.common.constants.NgtsCommonConstants;
import com.ngts.common.redis.PublisherService;
import com.ngts.common.redis.RedisCacheUtils;
import com.ngts.common.utils.RolesUtils;
import com.ngts.auth.entity.ERole;
import com.ngts.auth.entity.Roles;
import com.ngts.auth.entity.Users;
import com.ngts.auth.jwt.AuthJwtUtils;
import com.ngts.auth.repository.RolesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@Slf4j
@CrossOrigin(origins = "/**", maxAge = 3600)
@RestController
@RequestMapping("/auth/api")
public class AuthController {

  @Autowired
  PublisherService publisherService;

  @Autowired
  RolesUtils rolesUtils;

  @Autowired
  UsersRepository userRepository;

  @Autowired
  UsersService usersService;

  @Autowired
  RolesRepository roleRepository;

  @Autowired
  AuthJwtUtils jwtUtils;

  @Autowired
  private RedisCacheUtils redisCacheUtils;

  @Value("${ngtsscm.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    UserInfoResponse userInfoResponse = usersService.findAllUsersByEmailAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
    if(userInfoResponse == null)
      return ResponseEntity.badRequest().body("Bad credentials");

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userInfoResponse.getEmail());
    redisCacheUtils.hSet(jwtCookie.getValue(), NgtsCommonConstants.AUTH_TOKEN, userInfoResponse, jwtExpirationMs );

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(userInfoResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){

    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    Users users = new Users();
    users.setUsername(signUpRequest.getUsername());
    users.setEmail(signUpRequest.getEmail());
    users.setPassword(signUpRequest.getPassword());

    Set<String> strRoles = signUpRequest.getRole();
    Set<Roles> roles = new HashSet<>();

    if (strRoles == null) {
      Roles userRole = roleRepository.findByName(ERole.ROLE_STUDENTS)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
          break;
        case "staffs":
          Roles modRole = roleRepository.findByName(ERole.ROLE_STAFFS)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);
          break;
        default:
          Roles userRole = roleRepository.findByName(ERole.ROLE_STUDENTS)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
    users.setRoles(roles);
    userRepository.save(users);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

  @GetMapping("/regUsers")
  public ResponseEntity<?> getAllRegisteredUsers(){
    return ResponseEntity.ok().body(usersService.getAllRegUsers());
  }

}
