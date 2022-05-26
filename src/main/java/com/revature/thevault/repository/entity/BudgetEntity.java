package com.revature.thevault.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "budget_table")
public class BudgetEntity {
	
	@Id
    @Column(name = "pk_budget_id")
    @GeneratedValue(generator = "budget_table_pk_budget_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "budget_table_pk_budget_id_seq", sequenceName = "budget_table_pk_budget_id_seq")
	int pkBudgetId;
	
	@Column(name = "budget_name")
	String budgetName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_profile_id")
	AccountProfileEntity accountProfileEntity;
	
	@Column(name = "income")
	float income;
	
	@Column(name = "expenses")
	float[] expenses;
	
}
