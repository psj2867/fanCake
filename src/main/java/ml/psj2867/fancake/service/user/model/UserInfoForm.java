package ml.psj2867.fancake.service.user.model;

import javax.annotation.Nullable;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoForm{
    @Nullable
    private boolean detail;
}