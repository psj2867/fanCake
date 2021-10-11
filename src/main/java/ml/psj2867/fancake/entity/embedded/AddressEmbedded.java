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
public class AddressEmbedded{

    private String addressRecipient;
    private String address;
    private String addressDetail;
    private String addressZipCode;       

    public static AddressEmbedded of(UserEntity user){
        return AddressEmbedded.builder() 
                            .addressRecipient(user.getAddress().getAddressRecipient())
                            .address(user.getAddress().getAddress())
                            .addressDetail(user.getAddress().getAddressDetail())
                            .addressZipCode(user.getAddress().getAddressZipCode())
                            .build();
    }
}