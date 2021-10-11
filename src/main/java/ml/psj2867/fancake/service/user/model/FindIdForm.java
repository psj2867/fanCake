package ml.psj2867.fancake.service.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
public class FindIdForm{
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
}