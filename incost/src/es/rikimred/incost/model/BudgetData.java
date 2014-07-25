/**
 * 
 */
package es.rikimred.incost.model;

import java.math.BigDecimal;

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Category;
import es.rikimred.incost.model.domain.GenericData;

/**
 * @author roberto
 */
public class BudgetData extends GenericData {

	// Entity
	private final Budget entity;

	// Balance del presupuesto: Restando todos los gastos
	private BigDecimal budgetBalance;

	private BigDecimal totalExpenses;

	/**
	 * Constructor a partir del entity
	 * @param entity
	 */
	public BudgetData(final Budget entity) {
		super(entity);
		this.entity = entity;
		initialize();
	}

	private void initialize() {
		setTotalExpenses(BigDecimal.ZERO);
		setBudgetBalance(getAmount());
	}

	/**
	 * Recuperar el entity
	 * @return
	 */
	public Budget getEntity() {
		return entity;
	}

	/*
	 * GETTERS & SETTERS
	 */

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return entity.getCategory();
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(final Category category) {
		entity.setCategory(category);
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
	 * @return the balance
	 */
	public BigDecimal getBudgetBalance() {
		return budgetBalance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBudgetBalance(final BigDecimal budgetBalance) {
		this.budgetBalance = budgetBalance;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return entity.isReadOnly();
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(final boolean readOnly) {
		this.entity.setReadOnly(readOnly);
	}

	/**
	 * @return the totalExpenses
	 */
	public BigDecimal getTotalExpenses() {
		return totalExpenses;
	}

	/**
	 * @param totalExpenses the totalExpenses to set
	 */
	public void setTotalExpenses(final BigDecimal totalExpenses) {
		this.totalExpenses = totalExpenses;
		BigDecimal amount = getAmount();
		if (getAmount() == null) {
			amount = BigDecimal.ZERO;
		}
		setBudgetBalance(amount.subtract(totalExpenses));
	}
}
