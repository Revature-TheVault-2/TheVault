package com.revature.thevault.repository.dao;

import com.revature.thevault.presentation.model.request.DepositRequest;
import com.revature.thevault.presentation.model.response.builder.PostResponse.Builder;
import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.DepositEntity;

import java.sql.Date;
import java.util.List;

import com.revature.thevault.repository.entity.DepositTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("depositRepository")
public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {
    List<DepositEntity> findByAccountentity(AccountEntity accountEntity);
    List<DepositEntity> findByAccountentityAndDateDepositBetween(AccountEntity accountEntity, Date dateStart, Date dateEnd);
    List<DepositEntity> findByAccountentityAndDeposittypeentity(AccountEntity accountEntity, DepositTypeEntity depositTypeEntity);

    @Transactional
    void deleteByAccountentity(AccountEntity accountEntity);
}
