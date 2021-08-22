package ptit.ltw.Converter;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ptit.ltw.Dto.VerificationTokenDto;
import ptit.ltw.Entity.VerificationToken;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class VerificationTokenConverter {
    private final ModelMapper modelMapper;

    public VerificationToken vfTokenDtoToEntity(@NotNull VerificationTokenDto verificationTokenDto){
        return modelMapper.map(verificationTokenDto,VerificationToken.class);
    }
    public Collection<VerificationToken> vfTokenDtoToEntity(Collection<VerificationTokenDto> verificationTokenDtoList){
        return verificationTokenDtoList.stream().map(vfDto -> modelMapper.map(vfDto,VerificationToken.class)).collect(Collectors.toList());
    }
    public VerificationTokenDto vfTokenEntityToDto(@NotNull VerificationToken verificationToken){
        return modelMapper.map(verificationToken,VerificationTokenDto.class);
    }
    public Collection<VerificationTokenDto> vfTokenEntityToDto(Collection<VerificationToken> verificationTokenList){
        return verificationTokenList.stream().map(vf -> modelMapper.map(vf,VerificationTokenDto.class)).collect(Collectors.toList());
    }

}