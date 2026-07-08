package com.dayma.mapper;

import com.dayma.dto.SizeDto;
import com.dayma.dto.UserDto;
import com.dayma.model.Size;
import com.dayma.model.User;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto user);

    List<UserDto> toDtoList(List<User> users);
}