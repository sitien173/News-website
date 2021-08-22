package ptit.ltw.Converter;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ptit.ltw.Dto.UserDto;
import ptit.ltw.Entity.User;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    public UserDto userEntityToDto(@NotNull User user){
        return modelMapper.map(user, UserDto.class);
    }
    public Collection<UserDto> userEntityToDto(Collection<User> users){
        return users.stream().map(u -> modelMapper.map(u,UserDto.class)).collect(Collectors.toList());
    }
    public User userDtoToEntity(@NotNull UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }
    public Collection<User> userDtoToEntity(Collection<UserDto> userDtoList){
        return userDtoList.stream().map(uDto -> modelMapper.map(uDto,User.class)).collect(Collectors.toList());
    }
}
