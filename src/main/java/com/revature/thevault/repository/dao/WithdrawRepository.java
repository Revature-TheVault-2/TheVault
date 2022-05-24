package com.revature.thevault.repository.dao;

import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.DepositEntity;
import com.revature.thevault.repository.entity.RequestTypeEntity;
import com.revature.thevault.repository.entity.WithdrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

import java.sql.Date;
import java.util.List;

public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Integer> {

    List<WithdrawEntity> findByAccountentity(AccountEntity accountEntity);
    @Query("select w from WithdrawEntity w where "
    		+ "w.accountentity = :id "
    		+ "AND w.dateWithdraw <= :dateEnd "
    		+ "AND w.dateWithdraw >= :dateStart")
    List<WithdrawEntity> findByAccountIdAndDatesBetween(int id, Date dateStart, Date dateEnd);
    List<WithdrawEntity> findByAccountentityAndRequesttypeentity(AccountEntity accountEntity, RequestTypeEntity requestTypeByName);

    @Transactional
    void deleteByAccountentity(AccountEntity accountEntity);
}
