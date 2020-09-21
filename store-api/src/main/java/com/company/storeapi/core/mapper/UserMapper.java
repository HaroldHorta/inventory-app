package com.company.storeapi.core.mapper;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.model.entity.User;
import com.company.storeapi.model.enums.Role;
import com.company.storeapi.model.payload.request.user.RequestAddUserDTO;
import com.company.storeapi.model.payload.response.user.ResponseUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class UserMapper {

    Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public User toUser(RequestAddUserDTO requestAddUserDTO){

        User user = new User();
        user.setUsername(requestAddUserDTO.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
        String encodedPassword = encoder.encode(requestAddUserDTO.getPassword());
        user.setPassword(encodedPassword);
        Matcher mather = pattern.matcher(requestAddUserDTO.getEmail());

        if (mather.find()) {
            user.setEmail(requestAddUserDTO.getEmail());
        } else {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Email no valido");
        }


        Set<String> strRoles = requestAddUserDTO.getRole();
        Set<Role> roles = new HashSet<>();
        if(strRoles.isEmpty()){
            roles.add(Role.SELLER);
        }else{
            strRoles.forEach(
                    role ->{
                        switch (role){
                            case "admin":
                                roles.add(Role.ADMINISTRATOR);
                                break;
                            case "vet":
                                roles.add(Role.VETERINARY);
                                break;
                            case "super":
                                roles.add(Role.SUPER_ADMINISTRATOR);
                                break;
                            default:
                                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "rol no existente");
                        }
                    }
            );
        }

        user.setRoles(roles);
        return user;
    }

    public abstract User toUser(ResponseUserDTO responseUserDTO);

    public abstract ResponseUserDTO toUserDto(User user);
}
