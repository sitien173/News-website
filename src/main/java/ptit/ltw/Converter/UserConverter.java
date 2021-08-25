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
        UserDto userDto = modelMapper.map(appUser, UserDto.class);
        userDto.setId(appUser.getId());
        return userDto;
    }
    public Collection<UserDto> userEntityToDto(Collection<AppUser> appUsers){
        return appUsers.stream().map(this::userEntityToDto).collect(Collectors.toList());
    }
    public AppUser userDtoToEntity(@NotNull UserDto userDto){
        AppUser appUser = modelMapper.map(userDto, AppUser.class);
        appUser.setId(userDto.getId());
        return appUser;
    }
    public Collection<AppUser> userDtoToEntity(Collection<UserDto> userDtoList){
        return userDtoList.stream().map(this::userDtoToEntity).collect(Collectors.toList());
    }
}
