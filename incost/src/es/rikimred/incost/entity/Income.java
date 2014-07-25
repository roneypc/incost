package es.rikimred.incost.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * The persistent class for the income database table.
 * 
 */
@Entity
// @NamedQueries({ @NamedQuery(name = "Income.totalByIdBalance",
// query = "SELECT COUNT(i) FROM Income i WHERE i.balance.id = :idBalance") })
public class Income extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public static final String TOTALbyIDBALANCE = "Income.totalByIdBalance";

	@Column(name = "AMOUNT", precision = 12, scale = 2)
	private BigDecimal amount;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INCOME_DATE")
	private Date incomeDate;

	// bi-directional many-to-one association to Balance
	@ManyToOne
	@JoinColumn(name = "ID_BALANCE")
	private Balance balance;

	public Income() {
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Balance getBalance() {
		return this.balance;
	}

	public void setBalance(final Balance balance) {
		this.balance = balance;
	}

	/**
	 * @return the incomeDate
	 */
	public Date getIncomeDate() {
		return incomeDate;
	}

	/**
	 * @param incomeDate the incomeDate to set
	 */
	public void setIncomeDate(final Date incomeDate) {
		this.incomeDate = incomeDate;
	}

}