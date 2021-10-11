package ml.psj2867.fancake.entity.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermsEmbedded{

    private Boolean privacyInfo;
    private Boolean seviceUsage;
    private Boolean eventInfo;
       
}