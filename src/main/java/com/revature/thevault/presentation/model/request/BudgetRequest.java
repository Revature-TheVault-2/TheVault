package com.revature.thevault.presentation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Christian & Kimberly
 * This class can be used for create, read, update, and delete actions
 * on the BugdetRepository. Instead of creating a separate class for each
 * request type, just use the fields needed in order to execute the request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetRequest {
	private int budgetId;
	private String budgetName;
	private float income;
	private float[] expenses;
	private int profileId;
}
