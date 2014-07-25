package es.rikimred.incost.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.rikimred.incost.entity.domain.GenericEntity;
import es.rikimred.incost.enums.BalanceStateEnum;
import es.rikimred.incost.enums.BalanceStatusEnum;

/**
 * The persistent class for the balance database table.
 * @author jrneyra
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Balance.totalByIdUser", query = "SELECT COUNT(b) FROM User u JOIN u.balances b WHERE u.id = :idUser") })
public class Balance extends GenericEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TOTALbyIDUSER = "Balance.totalByIdUser";

	@ManyToOne
	private Balance parent;

	@OneToMany(mappedBy = "parent")
	private List<Balance> subBalances;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE")
	private Date lastUpdate;

	private Integer month;

	private Integer year;

	@Column(name = "IN_USE")
	private Boolean inUse;

	@Column(name = "HAS_SUB_BALANCE")
	private Boolean hasSubBalance;

	@Enumerated(EnumType.STRING)
	@Column(name = "BALANCE_STATUS")
	private BalanceStatusEnum balanceStatus;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "BALANCE_STATE")
	private BalanceStateEnum balanceState;

	// bi-directional many-to-one association to Budget
	@OneToMany(mappedBy = "balance")
	private List<Budget> budgets;

	// bi-directional many-to-one association to Income
	@OneToMany(mappedBy = "balance")
	private List<Income> incomes;

	// bi-directional many-to-one association to Cash
	@OneToMany(mappedBy = "balance")
	private List<Cash> cashList;

	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "balances")
	private List<User> users;

	// bi-directional many-to-many association to Book
	@ManyToMany(mappedBy = "balances")
	private List<Book> books;

	public Balance() {
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(final Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public List<Budget> getBudgets() {
		return this.budgets;
	}

	public void setBudgets(final List<Budget> budgets) {
		this.budgets = budgets;
	}

	public Budget addBudgets(final Budget budgets) {
		getBudgets().add(budgets);
		budgets.setBalance(this);

		return budgets;
	}

	public Budget removeBudgets(final Budget budgets) {
		getBudgets().remove(budgets);
		budgets.setBalance(null);

		return budgets;
	}

	public List<Cash> getCashList() {
		return this.cashList;
	}

	public void setCashList(final List<Cash> cashList) {
		this.cashList = cashList;
	}

	public List<Income> getIncomes() {
		return this.incomes;
	}

	public void setIncomes(final List<Income> incomes) {
		this.incomes = incomes;
	}

	public Income addIncomes(final Income incomes) {
		getIncomes().add(incomes);
		incomes.setBalance(this);

		return incomes;
	}

	public Income removeIncomes(final Income incomes) {
		getIncomes().remove(incomes);
		incomes.setBalance(null);

		return incomes;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
	}

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(final List<Book> books) {
		this.books = books;
	}

	/**
	 * @return the inUse
	 */
	public Boolean isInUse() {
		return inUse;
	}

	/**
	 * @param inUse the inUse to set
	 */
	public void setInUse(final Boolean inUse) {
		this.inUse = inUse;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the parent
	 */
	public Balance getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(final Balance parent) {
		this.parent = parent;
	}

	/**
	 * @return the subBalances
	 */
	public List<Balance> getSubBalances() {
		return subBalances;
	}

	/**
	 * @param subBalances the subBalances to set
	 */
	public void setSubBalances(final List<Balance> subBalances) {
		this.subBalances = subBalances;
	}

	/**
	 * @return the hasSubBalance
	 */
	public Boolean hasSubBalance() {
		return hasSubBalance;
	}

	/**
	 * @param hasSubBalance the hasSubBalance to set
	 */
	public void setHasSubBalance(final Boolean hasSubBalance) {
		this.hasSubBalance = hasSubBalance;
	}

	/**
	 * @return the balanceStatus
	 */
	public BalanceStatusEnum getBalanceStatus() {
		return balanceStatus;
	}

	/**
	 * @param balanceStatus the balanceStatus to set
	 */
	public void setBalanceStatus(final BalanceStatusEnum balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	/**
	 * @return the balanceState
	 */
	public BalanceStateEnum getBalanceState() {
		return balanceState;
	}

	/**
	 * @param balanceState the balanceState to set
	 */
	public void setBalanceState(final BalanceStateEnum balanceState) {
		this.balanceState = balanceState;
	}
}