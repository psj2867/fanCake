package ml.psj2867.fancake.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ml.psj2867.fancake.configure.security.TokenDto;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.exception.bad.FieldValidException;
import ml.psj2867.fancake.exception.inner.NotMatchedCredentialException;
import ml.psj2867.fancake.service.api.model.ErrorDto;
import ml.psj2867.fancake.service.user.AuthService;
import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.user.model.DetailUserDto;
import ml.psj2867.fancake.service.user.model.FindIdForm;
import ml.psj2867.fancake.service.user.model.IdDto;
import ml.psj2867.fancake.service.user.model.UserListForm;
import ml.psj2867.fancake.service.user.model.UserLoginForm;
import ml.psj2867.fancake.service.user.model.sign.SignUpUserForm;
import ml.psj2867.fancake.util.MessageDto;
@RestController
@RequestMapping("api/users")
public class UsersRestController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public Page<DetailUserDto> getList(UserListForm userListForm){        
        return userService.getUsers(userListForm);
    }

    @Operation(description = "회원가입")
    @PostMapping("")
    public MessageDto postRoot(@Validated @RequestBody SignUpUserForm signUpForm){
        signUpForm.encode(passwordEncoder);
        try {
            userService.addOriginUser(signUpForm);
            return MessageDto.success();
        } catch (DataIntegrityViolationException e) {
            ErrorDto error = new ErrorDto("user.signUp.duplicatedId", "id", signUpForm.getId());
            throw FieldValidException.of(error);
        } 
    }

    @Operation(description = "로그인")
    @PostMapping("login")
    public TokenDto postLogin(@Validated @RequestBody UserLoginForm userForm){
        try {
            TokenDto token = authService.loginOriginUser(userForm);
            return token;
        } catch (NotMatchedCredentialException e) {
            ErrorDto error = new ErrorDto("user.notMatched", "id", null);
            throw FieldValidException.of(error);
        } 
    }
    
    @Operation(description = "아이디 찾기")
    @GetMapping("id")
    public IdDto getFindID(@Validated FindIdForm findIdForm){
        Optional<UserEntity> userEntitiy = userService.findId(findIdForm);
        return  userEntitiy.map(IdDto::of)
                    .orElseThrow(()->FieldValidException.of(new ErrorDto("user.notMatched", null, null)));
    }
    @Operation(description = "비밀번호 찾기")
    @ApiResponse(responseCode = "202", description = "성공 시")
    @GetMapping("password")
    public ResponseEntity<MessageDto> getFindPassword(@Validated FindIdForm findIdForm){
        userService.findPassword(findIdForm);
        return ResponseEntity.accepted().body(MessageDto.success());
    }
  
    

}