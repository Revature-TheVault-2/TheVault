package com.revature.thevault.repository.dao;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.DepositEntity;
import com.revature.thevault.repository.entity.DepositTypeEntity;

@Repository("depositRepository")
public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {
    List<DepositEntity> findByAccountentity(AccountEntity accountEntity);
    @Query(value = "select * from deposit_table d where "
    		+ "d.fk_account_id = :id "
    		+ "AND d.date_deposit between cast(:dateStart AS timestamp) and cast(:dateEnd AS timestamp)",
    		nativeQuery = true)
    List<DepositEntity> findByAccountIdAndDatesBetween(@Param("id")int id, String dateStart, String dateEnd);
    List<DepositEntity> findByAccountentityAndDeposittypeentity(AccountEntity accountEntity, DepositTypeEntity depositTypeEntity);
    
//    "where date_time between cast(:dateFrom AS timestamp) AND cast(:dateTo AS timestamp)", nativeQuery = true)
//    Events[] findAllEventsBetweenDate(@Param("dateTo")String dateTo, @Param("dateFrom")String dateFrom);

    @Transactional
    void deleteByAccountentity(AccountEntity accountEntity);
}
