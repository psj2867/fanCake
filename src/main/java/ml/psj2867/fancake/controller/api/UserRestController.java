package ml.psj2867.fancake.controller.api;

import java.util.Optional;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ml.psj2867.fancake.configure.security.TokenDto;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.exception.bad.FieldValidException;
import ml.psj2867.fancake.exception.inner.NotMatchedCredentialException;
import ml.psj2867.fancake.exception.unauth.UnAuthorizedException;
import ml.psj2867.fancake.service.api.model.ErrorDto;
import ml.psj2867.fancake.service.stock.StockService;
import ml.psj2867.fancake.service.stock.model.StockDto;
import ml.psj2867.fancake.service.stock.model.StockListForm;
import ml.psj2867.fancake.service.trading.TradingService;
import ml.psj2867.fancake.service.trading.model.TradingHistoryDto;
import ml.psj2867.fancake.service.trading.model.TradingHistoryListForm;
import ml.psj2867.fancake.service.user.AuthService;
import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.user.model.FindIdForm;
import ml.psj2867.fancake.service.user.model.IdDto;
import ml.psj2867.fancake.service.user.model.SimpleUserDto;
import ml.psj2867.fancake.service.user.model.UpdateUserPasswordForm;
import ml.psj2867.fancake.service.user.model.UserForm;
import ml.psj2867.fancake.service.user.model.UserInfoForm;
import ml.psj2867.fancake.service.user.model.UserUpdateForm;
import ml.psj2867.fancake.service.user.model.sign.SignUpUserForm;
import ml.psj2867.fancake.util.MessageDto;
import ml.psj2867.fancake.util.SecurityUtil;

@Api(basePath = "api/user", description="현재 로그인 된 사용자 정보")
@RestController
@RequestMapping("api/user")
public class UserRestController {    
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TradingService tradingService;
    
    @Operation(description = "로그인 된 사용자 정보")
    @GetMapping("")
    public SimpleUserDto getRoot(@Nullable UserInfoForm form){
        if(!SecurityUtil.isAuth())
            throw new UnAuthorizedException();
        if(form.isDetail())
            return userService.getDetailUser();
        else
            return SimpleUserDto.current();                
    }
    @Operation(description = "회원가입")
    @PostMapping("")
    public MessageDto postRoot(@Validated SignUpUserForm signUpForm){
        try {
            userService.addOriginUser(signUpForm);
            return MessageDto.success();
        } catch (DataIntegrityViolationException e) {
            ErrorDto error = new ErrorDto("user.signUp.duplicatedId", "id", signUpForm.getId());
            throw FieldValidException.of(error);
        } 
    }
    @Operation(description = "정보 수정")
    @PutMapping("")
    public MessageDto putRoot(@Validated UserUpdateForm userDetailForm){
        userService.updateUser(userDetailForm);
        return MessageDto.success();
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
    
    @Operation(description = "비밀번호 변경")
    @PutMapping("password")
    public MessageDto putPassword(@Validated UpdateUserPasswordForm passwordForm){
        userService.updatePassword(passwordForm);
        return MessageDto.success();
    }
    @Operation(description = "로그인")
    @PostMapping("login")
    public TokenDto postLogin(@Validated UserForm userForm){
        try {
            TokenDto token = authService.loginOriginUser(userForm);
            return token;
        } catch (NotMatchedCredentialException e) {
            ErrorDto error = new ErrorDto("user.notMatched", "id", null);
            throw FieldValidException.of(error);
        } 
    }

    @Operation(description = "로그인 된 사용자의 조각들")
    @GetMapping("stocks")
    public Page<StockDto> getStocks(@Nullable StockListForm form){
        return stockService.getUserStock(form);
    }
    @Operation(description = "로그인 된 사용자의 거래 내용")
    @GetMapping("tradings")
    public Page<TradingHistoryDto> getTradings(@Nullable TradingHistoryListForm form){
        return tradingService.getTradingList(form);
    }


}