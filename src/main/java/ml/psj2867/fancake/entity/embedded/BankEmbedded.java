package ml.psj2867.fancake.entity.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.fancake.entity.UserEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankEmbedded{
    private String accountName;
    private String accountNumber;
    private String accountOwner;
    
    public static BankEmbedded of(UserEntity user){
        return BankEmbedded.builder() 
                            .accountName(user.getBank().getAccountName())
                            .accountNumber(user.getBank().getAccountNumber())
                            .accountOwner(user.getBank().getAccountOwner())
                            .build();
    }
}