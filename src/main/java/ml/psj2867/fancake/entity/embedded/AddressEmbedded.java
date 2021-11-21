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

    private String addressPhoneNumber;
    private String addressRecipient;
    private String address;
    private String addressDetail;
    private String addressZipCode;       

    public static AddressEmbedded of(UserEntity user){
        return AddressEmbedded.builder() 
                            .addressRecipient(user.getDetail().getAddress().getAddressRecipient())
                            .address(user.getDetail().getAddress().getAddress())
                            .addressDetail(user.getDetail().getAddress().getAddressDetail())
                            .addressZipCode(user.getDetail().getAddress().getAddressZipCode())
                            .addressPhoneNumber(user.getDetail().getAddress().getAddressPhoneNumber())
                            .build();
    }
}