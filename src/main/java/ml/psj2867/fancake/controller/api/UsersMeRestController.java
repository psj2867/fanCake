package ml.psj2867.fancake.controller.api;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ml.psj2867.fancake.configure.security.TokenDto;
import ml.psj2867.fancake.exception.unauth.UnAuthorizedException;
import ml.psj2867.fancake.service.oauth.AuthService;
import ml.psj2867.fancake.service.stock.StockService;
import ml.psj2867.fancake.service.stock.model.StockDto;
import ml.psj2867.fancake.service.stock.model.StockListForm;
import ml.psj2867.fancake.service.trading.TradingService;
import ml.psj2867.fancake.service.trading.model.TradingHistoryDto;
import ml.psj2867.fancake.service.trading.model.TradingHistoryListForm;
import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.user.model.SimpleUserDto;
import ml.psj2867.fancake.service.user.model.UpdateUserPasswordForm;
import ml.psj2867.fancake.service.user.model.UserInfoForm;
import ml.psj2867.fancake.service.user.model.UserUpdateForm;
import ml.psj2867.fancake.util.MessageDto;
import ml.psj2867.fancake.util.SecurityUtil;

@Api(basePath = "api/users/me", description="현재 로그인 된 사용자 정보")
@RestController
@RequestMapping("api/users/me")
public class UsersMeRestController {    
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TradingService tradingService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Operation(description = "로그인 된 사용자 정보")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @GetMapping("")
    public SimpleUserDto getRootGetUserIfno(@Validated @Nullable UserInfoForm form){
        if(!SecurityUtil.isAuth())
            throw new UnAuthorizedException();
        if(form.isDetail())
            return userService.getDetailUser();
        else
            return SimpleUserDto.current();                
    }
    
    @Operation(description = "정보 수정")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @PutMapping("")
    public MessageDto putRootUpdateUser(@Validated @RequestBody UserUpdateForm userDetailForm){
        userService.updateUser(userDetailForm);
        return MessageDto.success();
    }
    @Operation(description = "현재 로그인 된 사용자 제거")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @DeleteMapping("")
    public MessageDto deleteRootDeleteUser(){
        userService.deleteUser();
        return MessageDto.success();
    }
   
    @Operation(description = "web socket 임시 토큰")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @GetMapping("token")
    public TokenDto getTempToken(){
        return authService.getTempToken();
    }
    
    @Operation(description = "비밀번호 변경")
    @ApiResponse(responseCode = "400", description = "현재 비밀번호와 다를 시")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @PutMapping("password")
    public MessageDto putPasswordUpdatePassword(@Validated @RequestBody UpdateUserPasswordForm passwordForm){
        passwordForm.encode(passwordEncoder);
        userService.updatePassword(passwordForm);
        return MessageDto.success();
    }

    @Operation(description = "로그인 된 사용자의 조각들")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @GetMapping("stocks")
    public Page<StockDto> getStocks(@Validated @Nullable StockListForm form){
        return stockService.getUserStock(form);
    }
    @Operation(description = "로그인 된 사용자의 거래 내용")
    @ApiResponse(responseCode = "401",description = "사용자 인증 실패" )
    @GetMapping("tradings")
    public Page<TradingHistoryDto> getTradings(@Validated @Nullable TradingHistoryListForm form){
        return tradingService.getTradingList(form);
    }   
  
}