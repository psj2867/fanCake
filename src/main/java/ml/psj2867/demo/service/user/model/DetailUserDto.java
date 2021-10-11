package ml.psj2867.demo.service.user.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.entity.embedded.AddressEmbedded;
import ml.psj2867.demo.entity.embedded.BankEmbedded;
import ml.psj2867.demo.service.user.model.auth.LoginTypeEnum;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DetailUserDto extends SimpleUserDto {

    private String phoneNumber;
    private double balance;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LoginTypeEnum loginType;
    
    private BankEmbedded account;
    
    private AddressEmbedded address;

    public static DetailUserDto of(UserEntity userEntity) {
        return DetailUserDto.builder()
                    .name(userEntity.getName())
                    .userIdx(userEntity.getIdx())
                    .isAuthenticate(true) 
                    .balance(userEntity.getBalance())
                    .createdDate(userEntity.getCreatedDate())
                    .loginType(userEntity.getLoginType())
                    .phoneNumber(userEntity.getPhoneNumber())
                    .account(BankEmbedded.of(userEntity))
                    .address(AddressEmbedded.of(userEntity))
                    .build();
    }
}