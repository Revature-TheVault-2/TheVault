package com.revature.thevault.repository.dao;

import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.DepositEntity;
import com.revature.thevault.repository.entity.RequestTypeEntity;
import com.revature.thevault.repository.entity.WithdrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import java.sql.Date;
import java.util.List;

public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Integer> {

    List<WithdrawEntity> findByAccountentity(AccountEntity accountEntity);
    List<WithdrawEntity> findByAccountentityAndDateWithdrawBetween(AccountEntity accountEntity, Date dateStart, Date dateEnd);
    List<WithdrawEntity> findByAccountentityAndRequesttypeentity(AccountEntity accountEntity, RequestTypeEntity requestTypeByName);

    @Transactional
    void deleteByAccountentity(AccountEntity accountEntity);
}
