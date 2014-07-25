package es.rikimred.incost.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * The persistent class for the expenses database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Expenses.totalByIdBudget",
		query = "SELECT COUNT(e) FROM Expenses e WHERE e.budget.id = :idBudget") })
public class Expenses extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TOTALbyIDBUDGET = "Expenses.totalByIdBudget";

	@Column(name = "AMOUNT", precision = 12, scale = 2)
	private BigDecimal amount;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPENSES_DATE")
	private Date expensesDate;

	// bi-directional many-to-one association to Budget
	@ManyToOne
	@JoinColumn(name = "ID_BUDGET")
	private Budget budget;

	@Column(name = "READ_ONLY")
	private Boolean readOnly;

	public Expenses() {
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

	public Date getExpensesDate() {
		return expensesDate;
	}

	public void setExpensesDate(final Date expensesDate) {
		this.expensesDate = expensesDate;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(final Budget budget) {
		this.budget = budget;
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
}