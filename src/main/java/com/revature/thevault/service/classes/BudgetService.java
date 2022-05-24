package com.revature.thevault.service.classes;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.thevault.presentation.model.request.BudgetRequest;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.repository.dao.BudgetRepository;

@Service("budgetService")
public class BudgetService {
	
	@Autowired
	private BudgetRepository budgetRepository;
	
//	@Override
//	public PostResponse createBudget(BudgetRequest newBudget) {
//		
//		return PostResponse.builder().success(true).createdObject(Collections.singletonList(null));
//	}
}
