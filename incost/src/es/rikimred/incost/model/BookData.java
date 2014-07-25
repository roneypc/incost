/**
 * 
 */
package es.rikimred.incost.model;

import java.math.BigDecimal;

import es.rikimred.incost.entity.Book;
import es.rikimred.incost.model.domain.GenericData;

/**
 * 
 * @author roberto
 */
public class BookData extends GenericData {

	// Entity
	private Book entity;

	public BookData(Book entity) {
		super(entity);
		this.entity = entity;
	}

	/**
	 * @return the entity
	 */
	public Book getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(final Book balance) {
		this.entity = balance;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return entity.getDescription();
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		entity.setDescription(description);
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getBookBalance() {
		return entity.getBookBalance();
	}

	/**
	 * 
	 * @param bookBalance
	 */
	public void setBookBalance(final BigDecimal bookBalance) {
		entity.setBookBalance(bookBalance);
	}

	/**
	 * @return the totalIncome
	 */
	public BigDecimal getTotalIncome() {
		return entity.getTotalIncome();
	}

	/**
	 * @param totalIncome the totalIncome to set
	 */
	public void setTotalIncome(final BigDecimal totalIncome) {
		entity.setTotalIncome(totalIncome);
	}

	/**
	 * @return the totalBudget
	 */
	public BigDecimal getTotalBudget() {
		return entity.getTotalBudget();
	}

	/**
	 * @param totalBudget the totalBudget to set
	 */
	public void setTotalBudget(final BigDecimal totalBudget) {
		entity.setTotalBudget(totalBudget);
	}

	/**
	 * @return the totalExpenses
	 */
	public BigDecimal getTotalExpenses() {
		return entity.getTotalExpenses();
	}

	/**
	 * @param totalExpenses the totalExpenses to set
	 */
	public void setTotalExpenses(final BigDecimal totalExpenses) {
		entity.setTotalExpenses(totalExpenses);
	}

	// /**
	// *
	// */
	// public void calculateBalance() {
	// // Total Balance (totalIncome - totalBudget)
	// BigDecimal totalBalance = getTotalIncome().subtract(getTotalBudget());
	// // Total por Gastar (totalBudget - totalExpenses)
	// BigDecimal totalToExpenses = getTotalBudget().subtract(getTotalExpenses());
	// // Liquidez necesaria (totalBalance + totalToExpenses)
	// BigDecimal neededLiquidity = totalBalance.add(totalToExpenses);
	// // Total desviación (totalCash - neededLiquidity)
	// BigDecimal totalDetour = getTotalCash().subtract(neededLiquidity);
	// // Resultado balance (totalCash - totalToExpenses)
	// BigDecimal balanceResult = getTotalCash().subtract(totalToExpenses);
	//
	// //
	// setTotalBalance(totalBalance);
	// setTotalToExpenses(totalToExpenses);
	// setNeededLiquidity(neededLiquidity);
	// setTotalDetour(totalDetour);
	// setBalanceResult(balanceResult);
	// }
}
