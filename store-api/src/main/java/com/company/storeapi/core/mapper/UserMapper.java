package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.User;
import com.company.storeapi.model.payload.response.user.ResponseUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

   ResponseUserDTO toUserDto(User user);

}
