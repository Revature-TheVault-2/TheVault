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
    @Query(value = "select * from withdraw_table w where "
    		+ "w.fk_account_id = :id "
    		+ "AND w.date_withdraw between cast(:dateStart AS timestamp) and cast(:dateEnd AS timestamp)",
    		nativeQuery = true)
    List<WithdrawEntity> findByAccountIdAndDatesBetween(int id, String dateStart, String dateEnd);
    List<WithdrawEntity> findByAccountentityAndRequesttypeentity(AccountEntity accountEntity, RequestTypeEntity requestTypeByName);

    @Transactional
    void deleteByAccountentity(AccountEntity accountEntity);
}
