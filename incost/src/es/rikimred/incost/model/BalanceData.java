/**
 * 
 */
package es.rikimred.incost.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.rikimred.incost.entity.Balance;
import es.rikimred.incost.model.domain.GenericData;
import es.rikimred.incost.util.MiscUtils;

/**
 * 
 * @author roberto
 */
public class BalanceData extends GenericData {

	// Entity
	private Balance entity;

	// Año/mes
	private String yearMonth;

	// Total ingresos del entity
	private BigDecimal totalIncome;

	// Total presupuesto
	private BigDecimal totalBudget;

	// (totalIncome - totalBudget)
	private BigDecimal totalBalance;

	// Total gastos (suma de gastos)
	private BigDecimal totalExpenses;

	// Total entity (ingresos - gastos)
	private BigDecimal summary;

	// Total por gastar (totalBudget - totalExpenses)
	private BigDecimal totalToExpenses;

	// Liquidez necesaria (totalBalance + totalToExpenses)
	private BigDecimal neededLiquidity;

	// Total caja (suma de efectivo en caja)
	private BigDecimal totalCash;

	// Total desviación (totalCash - neededLiquidity)
	private BigDecimal totalDetour;

	// Resultado balance (totalCash - totalToExpenses)
	private BigDecimal balanceResult;

	private static final String SEPARATOR = "/";

	private static final BigDecimal ZERO = BigDecimal.ZERO;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm");

	/**
	 * @param entity
	 */
	public BalanceData(final Balance entity) {
		super(entity);
		this.entity = entity;
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		setTotalIncome(ZERO);
		setTotalBudget(ZERO);
		setTotalExpenses(ZERO);
		setTotalCash(ZERO);
		//
		setTotalBalance(ZERO);
		setTotalToExpenses(ZERO);
		setNeededLiquidity(ZERO);
		setTotalDetour(ZERO);
		setBalanceResult(ZERO);
	}

	public String getName() {
		return entity.getName();
	}

	public void setName(final String name) {
		this.entity.setName(name.toUpperCase());
	}

	public Integer getMonth() {
		return entity.getMonth();
	}

	public void setMonth(final Integer month) {
		this.entity.setMonth(month);
	}

	public Integer getYear() {
		return entity.getYear();
	}

	public void setYear(final Integer year) {
		this.entity.setYear(year);
	}

	public Date getLastUpdate() {
		return entity.getLastUpdate();
	}

	public String getLastUpdateFormatted() {
		return sdf.format(entity.getLastUpdate());
	}

	public Boolean hasSubBalance() {
		return entity.hasSubBalance();
	}

	public void setHasSubBalance(final Boolean hasSubBalance) {
		entity.setHasSubBalance(hasSubBalance);
	}

	public Balance getParent() {
		return entity.getParent();
	}

	public void setParent(final Balance parent) {
		entity.setParent(parent);
	}

	/**
	 * @return the entity
	 */
	public Balance getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(final Balance balance) {
		this.entity = balance;
	}

	/**
	 * @return the yearMonth
	 */
	public String getYearMonth() {
		if (yearMonth == null) {
			if (getMonth() != null && getYear() != null) {
				yearMonth = MiscUtils.getNameOfMonth(getMonth(), false) + SEPARATOR + getYear();
			}
		}
		return yearMonth;
	}

	/**
	 * @param yearMonth the yearMonth to set
	 */
	public void setYearMonth(final String yearMonth) {
		this.yearMonth = yearMonth;
		String[] yearMonthArray = yearMonth.split(SEPARATOR);
		setMonth(MiscUtils.getNumberOfMonth(yearMonthArray[0]));
		setYear(Integer.parseInt(yearMonthArray[1]));
	}

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(final BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
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
	 * @return the summary
	 */
	public BigDecimal getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(final BigDecimal summary) {
		this.summary = summary;
	}

	/**
	 * @return the totalToExpenses
	 */
	public BigDecimal getTotalToExpenses() {
		return totalToExpenses;
	}

	/**
	 * @param totalToExpenses the totalToExpenses to set
	 */
	public void setTotalToExpenses(final BigDecimal totalToExpenses) {
		this.totalToExpenses = totalToExpenses;
	}

	/**
	 * @return the neededLiquidity
	 */
	public BigDecimal getNeededLiquidity() {
		return neededLiquidity;
	}

	/**
	 * @param neededLiquidity the neededLiquidity to set
	 */
	public void setNeededLiquidity(BigDecimal neededLiquidity) {
		this.neededLiquidity = neededLiquidity;
	}

	/**
	 * @return the totalCash
	 */
	public BigDecimal getTotalCash() {
		return totalCash;
	}

	/**
	 * @param totalCash the totalCash to set
	 */
	public void setTotalCash(BigDecimal totalCash) {
		this.totalCash = totalCash;
	}

	/**
	 * @return the totalDetour
	 */
	public BigDecimal getTotalDetour() {
		return totalDetour;
	}

	/**
	 * @param totalDetour the totalDetour to set
	 */
	public void setTotalDetour(BigDecimal totalDetour) {
		this.totalDetour = totalDetour;
	}

	/**
	 * Resultado balance (totalCash - totalToExpenses)
	 * @return the balanceResult
	 */
	public BigDecimal getBalanceResult() {
		return balanceResult;
	}

	/**
	 * @param balanceResult the balanceResult to set
	 */
	public void setBalanceResult(BigDecimal balanceResult) {
		this.balanceResult = balanceResult;
	}

	/**
	 * 
	 */
	public void calculateBalance() {
		// Total Balance (totalIncome - totalBudget)
		BigDecimal totalBalance = getTotalIncome().subtract(getTotalBudget());
		// Total por Gastar (totalBudget - totalExpenses)
		BigDecimal totalToExpenses = getTotalBudget().subtract(getTotalExpenses());
		// Liquidez necesaria (totalBalance + totalToExpenses)
		BigDecimal neededLiquidity = totalBalance.add(totalToExpenses);
		// Total desviación (totalCash - neededLiquidity)
		BigDecimal totalDetour = getTotalCash().subtract(neededLiquidity);
		// Resultado balance (totalCash - totalToExpenses)
		BigDecimal balanceResult = getTotalCash().subtract(totalToExpenses);

		//
		setTotalBalance(totalBalance);
		setTotalToExpenses(totalToExpenses);
		setNeededLiquidity(neededLiquidity);
		setTotalDetour(totalDetour);
		setBalanceResult(balanceResult);
	}
}
