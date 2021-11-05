package ml.psj2867.fancake.service.user.model;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.entity.UserEntity;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateForm {
    

    @Nullable private String password;
    @Nullable private String phoneNumber;

    @Nullable private String accountNumber;
    @Nullable private String accountName;
    @Nullable private String accountOwner;

    @Nullable private String addressPhoneNumber;
    @Nullable private String addressRecipient;
    @Nullable private String address;
    @Nullable private String addressDetail;

    @Nullable private String zipCode;

    public void overWrite(UserEntity user){
        if(this.password != null) user.setPassword(password);
        if(this.phoneNumber != null) user.setPhoneNumber(phoneNumber);

        if(this.accountNumber != null) user.getDetail().getBank().setAccountNumber(this.accountNumber);
        if(this.accountName != null) user.getDetail().getBank().setAccountName(this.accountName);
        if(this.accountOwner != null) user.getDetail().getBank().setAccountOwner(this.accountOwner);

        if(this.addressPhoneNumber != null) user.getDetail().getAddress().setAddressPhoneNumber(addressPhoneNumber);
        if(this.addressRecipient != null) user.getDetail().getAddress().setAddressRecipient(this.addressRecipient);
        if(this.address != null) user.getDetail().getAddress().setAddress(this.address);
        if(this.addressDetail != null) user.getDetail().getAddress().setAddressDetail(this.addressDetail);
        if(this.zipCode != null) user.getDetail().getAddress().setAddressZipCode(this.zipCode);
    }

}