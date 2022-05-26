package com.revature.thevault.repository.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.repository.entity.BudgetEntity;

@Repository("budgetRepository")
public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer>{
	List<BudgetEntity> findAllByAccountProfileEntity(AccountProfileEntity accountProfile);
	BudgetEntity getByPkBudgetId(Integer id);
	void deleteAllByAccountProfileEntity(AccountProfileEntity accountProfile);
	void deleteByPkBudgetId(Integer id);
}
