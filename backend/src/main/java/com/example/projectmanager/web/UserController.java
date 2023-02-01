package com.example.projectmanager.web;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;
import com.example.projectmanager.payload.request.LoginRequest;
import com.example.projectmanager.payload.response.JWTLoginSuccessResponse;
import com.example.projectmanager.security.jwt.JwtTokenProvider;
import com.example.projectmanager.security.validator.UserValidator;
import com.example.projectmanager.service.MapValidationErrorService;
import com.example.projectmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static com.example.projectmanager.security.SecurityConstants.TOKEN_PREFIX;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final MapValidationErrorService mapValidationErrorService;

    private final UserService userService;

    private final UserValidator userValidator;

    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public UserController(MapValidationErrorService mapValidationErrorService, UserService userService, UserValidator userValidator, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        //Validate passwords match
        this.userValidator.validate(user, result);

        ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User newUser = this.userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable String userId){

        User foundUser = this.userService.getUserInfo(userId);
        return new ResponseEntity<User>(foundUser, HttpStatus.OK);
    }
}
