/**
 * 
 */
package es.rikimred.incost.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * @author roberto
 * 
 */
@Entity
public class Book extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String description;

	@Column(name = "BOOK_BALANCE", precision = 12, scale = 2)
	private BigDecimal bookBalance;

	@Column(name = "TOTAL_INCOME", precision = 12, scale = 2)
	private BigDecimal totalIncome;

	@Column(name = "TOTAL_BUDGET", precision = 12, scale = 2)
	private BigDecimal totalBudget;

	@Column(name = "TOTAL_EXPENSES", precision = 12, scale = 2)
	private BigDecimal totalExpenses;

	// bi-directional many-to-many association to Balance
	@ManyToMany
	@JoinTable(name = "book_balance", joinColumns = { @JoinColumn(name = "ID_BOOK") }, inverseJoinColumns = { @JoinColumn(name = "ID_BALANCE") })
	private List<Balance> balances;

	@ManyToOne
	@JoinColumn(name = "ID_USER")
	private User user;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the bookBalance
	 */
	public BigDecimal getBookBalance() {
		return bookBalance;
	}

	/**
	 * @param bookBalance the bookBalance to set
	 */
	public void setBookBalance(final BigDecimal bookBalance) {
		this.bookBalance = bookBalance;
	}

	/**
	 * @return the totalIncome
	 */
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	/**
	 * @param totalIncome the totalIncome to set
	 */
	public void setTotalIncome(final BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	/**
	 * @return the totalBudget
	 */
	public BigDecimal getTotalBudget() {
		return totalBudget;
	}

	/**
	 * @param totalBudget the totalBudget to set
	 */
	public void setTotalBudget(final BigDecimal totalBudget) {
		this.totalBudget = totalBudget;
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
	}

	/**
	 * @return the balances
	 */
	public List<Balance> getBalances() {
		if (balances == null) {
			balances = new ArrayList<Balance>();
		}
		return balances;
	}

	/**
	 * @param balances the balances to set
	 */
	public void setBalances(final List<Balance> balances) {
		this.balances = balances;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
