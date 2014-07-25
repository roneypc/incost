/**
 * 
 */
package es.rikimred.incost.model;

import java.math.BigDecimal;
import java.util.Date;

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Expenses;
import es.rikimred.incost.model.domain.GenericData;

/**
 * 
 * @author roberto
 */
public class ExpensesData extends GenericData {

	// Entity
	private final Expenses entity;
	
	// Presupuestado
//	private BigDecimal budgetBalance;

	/**
	 * Constructor a partir del entity
	 * @param entity
	 */
	public ExpensesData(final Expenses entity) {
		super(entity);
		this.entity = entity;
	}

	/**
	 * Recuperar el entity
	 * @return
	 */
	public Expenses getEntity() {
		return entity;
	}

	/*
	 * GETTERS & SETTERS
	 */

	/**
	 * 
	 * @return
	 */
	public Budget getBudget() {
		return entity.getBudget();
	}

	/**
	 * 
	 * @param budget
	 */
	public void setBudget(final Budget budget) {
		entity.setBudget(budget);
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return entity.getAmount();
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(final BigDecimal amount) {
		entity.setAmount(amount);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return entity.getDescription();
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		entity.setDescription(description);
	}

	/**
	 * @return the expenseDate
	 */
	public Date getExpensesDate() {
		return entity.getExpensesDate();
	}

	/**
	 * @param expenseDate the expenseDate to set
	 */
	public void setExpensesDate(final Date expensesDate) {
		entity.setExpensesDate(expensesDate);
	}

	/**
	 * @return the readOnly
	 */
	public Boolean isReadOnly() {
		return entity.isReadOnly();
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(final Boolean readOnly) {
		entity.setReadOnly(readOnly);
	}
	
//	/**
//	 * @return the budgetBalance
//	 */
//	public BigDecimal getBudgetBalance() {
//		return budgetBalance;
//	}
//
//	/**
//	 * @param budgetBalance the budgetBalance to set
//	 */
//	public void setBudgetBalance(final BigDecimal budgetBalance) {
//		this.budgetBalance = budgetBalance;
//	}
}
