package com.revature.thevault.service.classes;

import com.revature.thevault.repository.dao.AccountTypeRepository;
import com.revature.thevault.repository.entity.AccountTypeEntity;
import com.revature.thevault.service.exceptions.InvalidAccountTypeException;
import com.revature.thevault.service.interfaces.AccountTypeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service("accountTypeService")
public class AccountTypeService implements AccountTypeInterface {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

	/**
	 * Instantiates an AccountTypeEntity with the value object using
	 * accountTypeRepository.findByName(String parameter) if the Object is null,
	 * then an exception is thrown, else the AccountTypeEntity is returned.
	 * 
	 * @Author Previous Batch
	 * @param
	 * @Return AccountTypeEntity
	 */
    @Override
    public AccountTypeEntity findAccountTypeEntityByName(String name) {
        AccountTypeEntity accountTypeEntity = accountTypeRepository.findByName(name);
        if(accountTypeEntity != null) return accountTypeEntity;
        else throw new InvalidAccountTypeException(HttpStatus.BAD_REQUEST, "Invalid Account Type Provided: " + name);
    }
}
