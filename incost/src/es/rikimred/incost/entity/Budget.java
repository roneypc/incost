package es.rikimred.incost.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * The persistent class for the budget database table.
 */
@Entity
public class Budget extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "AMOUNT", precision = 12, scale = 2)
	private BigDecimal amount;

	private String description;

	@Column(name = "ID_SUB_BALANCE")
	private Integer idSubBalance;

	// bi-directional many-to-one association to Balance
	@ManyToOne
	@JoinColumn(name = "ID_BALANCE")
	private Balance balance;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "ID_CATEGORY")
	private Category category;

	// bi-directional many-to-one association to Expens
	@OneToMany(mappedBy = "budget")
	private List<Expenses> expenses;

	@Column(name = "READ_ONLY")
	private Boolean readOnly;

	public Budget() {
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(final Balance balance) {
		this.balance = balance;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public List<Expenses> getExpenses() {
		return expenses;
	}

	public void setExpenses(final List<Expenses> expenses) {
		this.expenses = expenses;
	}

	public Expenses addExpenses(final Expenses expenses) {
		getExpenses().add(expenses);
		expenses.setBudget(this);

		return expenses;
	}

	public Expenses removeExpenses(final Expenses expenses) {
		getExpenses().remove(expenses);
		expenses.setBudget(null);

		return expenses;
	}

	/**
	 * @return the readOnly
	 */
	public Boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(final Boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return the idSubBalance
	 */
	public Integer getIdSubBalance() {
		return idSubBalance;
	}

	/**
	 * @param idSubBalance the idSubBalance to set
	 */
	public void setIdSubBalance(final Integer idSubBalance) {
		this.idSubBalance = idSubBalance;
	}
}