package ptit.ltw.Converter;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ptit.ltw.Dto.UserDto;
import ptit.ltw.Entity.AppUser;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    public UserDto userEntityToDto(@NotNull AppUser appUser){
        return modelMapper.map(appUser, UserDto.class);
    }
    public Collection<UserDto> userEntityToDto(Collection<AppUser> appUsers){
        return appUsers.stream().map(u -> modelMapper.map(u,UserDto.class)).collect(Collectors.toList());
    }
    public AppUser userDtoToEntity(@NotNull UserDto userDto){
        return modelMapper.map(userDto, AppUser.class);
    }
    public Collection<AppUser> userDtoToEntity(Collection<UserDto> userDtoList){
        return userDtoList.stream().map(uDto -> modelMapper.map(uDto, AppUser.class)).collect(Collectors.toList());
    }
}
