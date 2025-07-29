package com.tim.accountservice.mapper;

import com.tim.accountservice.dto.AccountDto;
import com.tim.accountservice.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", source = "id")
    AccountDto toDto(Account account);
    @Mapping(target = "id", source = "id")
    Account toEntity(AccountDto accountDto);
}
