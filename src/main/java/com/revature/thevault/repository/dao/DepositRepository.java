package com.revature.thevault.repository.dao;

import com.revature.thevault.presentation.model.request.DepositRequest;
import com.revature.thevault.presentation.model.response.builder.PostResponse.Builder;
import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.DepositEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("depositRepository")
public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {

	List<DepositEntity> findByAccountId(int accountId);

	
	
}
