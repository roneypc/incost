/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 * Controlador de vistas
 * @author jrneyra
 */
@Named
@SessionScoped
public class ViewController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean disableBalanceButton = Boolean.TRUE;

	private Boolean disableBookButton = Boolean.TRUE;

	private Boolean disableBookBalanceButton = Boolean.TRUE;

	private Boolean disableSubBalanceButton = Boolean.TRUE;

	private Boolean disableBudgetButton = Boolean.TRUE;

	private Boolean disableBudgetNewButton = Boolean.FALSE;

	private Boolean disableBudgetFilterButton = Boolean.FALSE;

	private Boolean disableBudgetRefreshButton = Boolean.TRUE;

	private Boolean disableBudgetAddFixedButton = Boolean.TRUE;

	private Boolean disableCategoryButton = Boolean.TRUE;

	private Boolean disableExpensesButton = Boolean.TRUE;

	private Boolean disableExpensesFilterButton = Boolean.FALSE;

	private Boolean disableExpensesNewButton = Boolean.FALSE;

	private Boolean disableExpensesRefreshButton = Boolean.TRUE;

	private Boolean disableExpensesAddFixedButton = Boolean.TRUE;

	private Boolean disableIncomeButton = Boolean.TRUE;

	private Boolean disableIncomeAddFixedButton = Boolean.TRUE;

	private Boolean disableCashButton = Boolean.TRUE;

	private Boolean disableCashAddFixedButton = Boolean.TRUE;

	private Boolean disableFixedIncomeButton = Boolean.TRUE;

	private Boolean disableFixedBudgetButton = Boolean.TRUE;

	private Boolean disableFixedExpensesButton = Boolean.TRUE;

	private Boolean disableFixedCashButton = Boolean.TRUE;

	private String activeIndexes;

	@PostConstruct
	private void initialize() {
		setActiveIndexes("0");
	}

	/*
	 * LISTENERS
	 */

	/**
	 * Evento al seleccionar una fila balance
	 * @param event
	 */
	public void onBalanceRowSelect(final SelectEvent event) {
		disableBalanceButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila balance
	 * @param event
	 */
	public void onBalanceRowUnSelect(final UnselectEvent event) {
		disableBalanceButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila book
	 * @param event
	 */
	public void onBookRowSelect(final SelectEvent event) {
		disableBookButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila book
	 * @param event
	 */
	public void onBookRowUnSelect(final UnselectEvent event) {
		disableBookButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila book balance
	 * @param event
	 */
	public void onBookBalanceRowSelect(final SelectEvent event) {
		disableBookBalanceButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila book balance
	 * @param event
	 */
	public void onBookBalanceRowUnSelect(final UnselectEvent event) {
		disableBookBalanceButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila sub balance
	 * @param event
	 */
	public void onSubBalanceRowSelect(final SelectEvent event) {
		disableSubBalanceButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila sub balance
	 * @param event
	 */
	public void onSubBalanceRowUnSelect(final UnselectEvent event) {
		disableSubBalanceButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila budget
	 * @param event
	 */
	public void onBudgetRowSelect(final SelectEvent event) {
		disableBudgetButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila budget
	 * @param event
	 */
	public void onBudgetRowUnSelect(final UnselectEvent event) {
		disableBudgetButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed budget en panel de ayuda
	 * @param event
	 */
	public void onBudgetFixedRowSelect(final SelectEvent event) {
		disableBudgetAddFixedButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed budget en panel de ayuda
	 * @param event
	 */
	public void onBudgetFixedRowUnSelect(final UnselectEvent event) {
		disableBudgetAddFixedButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila category
	 * @param event
	 */
	public void onCategoryRowSelect(final SelectEvent event) {
		disableCategoryButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila category
	 * @param event
	 */
	public void onCategoryRowUnSelect(final UnselectEvent event) {
		disableCategoryButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila expenses
	 * @param event
	 */
	public void onExpensesRowSelect(final SelectEvent event) {
		disableExpensesButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila expenses
	 * @param event
	 */
	public void onExpensesRowUnSelect(final UnselectEvent event) {
		disableExpensesButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed expenses en panel de ayuda
	 * @param event
	 */
	public void onExpensesFixedRowSelect(final SelectEvent event) {
		disableExpensesAddFixedButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed expenses en panel de ayuda
	 * @param event
	 */
	public void onExpensesFixedRowUnSelect(final UnselectEvent event) {
		disableExpensesAddFixedButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila income
	 * @param event
	 */
	public void onIncomeRowSelect(final SelectEvent event) {
		disableIncomeButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila income
	 * @param event
	 */
	public void onIncomeRowUnSelect(final UnselectEvent event) {
		disableIncomeButton = Boolean.TRUE;
	}

	/**
	 * 
	 * @param event
	 */
	public void onCashRowSelect(final SelectEvent event) {
		disableCashButton = Boolean.FALSE;
	}

	/**
	 * 
	 * @param event
	 */
	public void onCashRowUnSelect(final UnselectEvent event) {
		disableCashButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed income en panel de ayuda
	 * @param event
	 */
	public void onIncomeFixedRowSelect(final SelectEvent event) {
		disableIncomeAddFixedButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed income en panel de ayuda
	 * @param event
	 */
	public void onIncomeFixedRowUnSelect(final UnselectEvent event) {
		disableIncomeAddFixedButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed cash en panel de ayuda
	 * @param event
	 */
	public void onCashFixedRowSelect(final SelectEvent event) {
		disableCashAddFixedButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed cash en panel de ayuda
	 * @param event
	 */
	public void onCashFixedRowUnSelect(final UnselectEvent event) {
		disableCashAddFixedButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed income
	 * @param event
	 */
	public void onFixedIncomeRowSelect(final SelectEvent event) {
		disableFixedIncomeButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed income
	 * @param event
	 */
	public void onFixedIncomeRowUnSelect(final UnselectEvent event) {
		disableFixedIncomeButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed budget
	 * @param event
	 */
	public void onFixedBudgetRowSelect(final SelectEvent event) {
		disableFixedBudgetButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed budget
	 * @param event
	 */
	public void onFixedBudgetRowUnSelect(final UnselectEvent event) {
		disableFixedBudgetButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed expenses
	 * @param event
	 */
	public void onFixedExpensesRowSelect(final SelectEvent event) {
		disableFixedExpensesButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed expenses
	 * @param event
	 */
	public void onFixedExpensesRowUnSelect(final UnselectEvent event) {
		disableFixedExpensesButton = Boolean.TRUE;
	}

	/**
	 * Evento al seleccionar una fila fixed cash
	 * @param event
	 */
	public void onFixedCashRowSelect(final SelectEvent event) {
		disableFixedCashButton = Boolean.FALSE;
	}

	/**
	 * Evento al deseleccionar una fila fixed cash
	 * @param event
	 */
	public void onFixedCashRowUnSelect(final UnselectEvent event) {
		disableFixedCashButton = Boolean.TRUE;
	}

	/**
	 * 
	 * @param event
	 */
	public void onTabChange(final TabChangeEvent event) {
		final String index = ((AccordionPanel) event.getComponent()).getActiveIndex();
		setActiveIndexes(index);
	}

	/**
	 * @return the activeIndexes
	 */
	public String getActiveIndexes() {
		return activeIndexes;
	}

	/**
	 * @param activeIndexes the activeIndexes to set
	 */
	public void setActiveIndexes(final String activeIndexes) {
		this.activeIndexes = activeIndexes;
	}

	/**
	 * Habilitat / Deshabilitar botones de la página budget al momento de hacer un filtrado /
	 * refresco
	 * @param enable
	 */
	public void setBudgetPageEnableButtonsOnFilter(final Boolean enable) {
		disableBudgetButton = enable;
		disableBudgetFilterButton = enable;
		disableBudgetNewButton = enable;
		disableBudgetRefreshButton = !enable;
	}

	/**
	 * Habilitat / Deshabilitar botones de la página expenses al momento de hacer un filtrado /
	 * refresco
	 * @param enable
	 */
	public void setExpensesPageEnableButtonsOnFilter(final Boolean enable) {
		disableExpensesButton = enable;
		disableExpensesFilterButton = enable;
		disableExpensesNewButton = enable;
		disableExpensesRefreshButton = !enable;
	}

	/*
	 * GETTERS & SETTERS
	 */

	/**
	 * @return the disableBalanceButton
	 */
	public Boolean getDisableBalanceButton() {
		return disableBalanceButton;
	}

	/**
	 * @param disableBalanceButton the disableBalanceButton to set
	 */
	public void setDisableBalanceButton(final Boolean disableBalanceButton) {
		this.disableBalanceButton = disableBalanceButton;
	}

	/**
	 * @return the disableBookButton
	 */
	public Boolean getDisableBookButton() {
		return disableBookButton;
	}

	/**
	 * @param disableBookButton the disableBookButton to set
	 */
	public void setDisableBookButton(final Boolean disableBookButton) {
		this.disableBookButton = disableBookButton;
	}

	/**
	 * @return the disableBookBalanceButton
	 */
	public Boolean getDisableBookBalanceButton() {
		return disableBookBalanceButton;
	}

	/**
	 * @param disableBookBalanceButton the disableBookBalanceButton to set
	 */
	public void setDisableBookBalanceButton(final Boolean disableBookBalanceButton) {
		this.disableBookBalanceButton = disableBookBalanceButton;
	}

	/**
	 * @return the disableSubBalanceButton
	 */
	public Boolean getDisableSubBalanceButton() {
		return disableSubBalanceButton;
	}

	/**
	 * @param disableSubBalanceButton the disableSubBalanceButton to set
	 */
	public void setDisableSubBalanceButton(final Boolean disableSubBalanceButton) {
		this.disableSubBalanceButton = disableSubBalanceButton;
	}

	/**
	 * @return the disableBudgetButton
	 */
	public Boolean getDisableBudgetButton() {
		return disableBudgetButton;
	}

	/**
	 * @param disableBudgetButton the disableBudgetButton to set
	 */
	public void setDisableBudgetButton(final Boolean disableBudgetButton) {
		this.disableBudgetButton = disableBudgetButton;
	}

	/**
	 * @return the disableBudgetNewButton
	 */
	public Boolean getDisableBudgetNewButton() {
		return disableBudgetNewButton;
	}

	/**
	 * @param disableBudgetNewButton the disableBudgetNewButton to set
	 */
	public void setDisableBudgetNewButton(final Boolean disableBudgetNewButton) {
		this.disableBudgetNewButton = disableBudgetNewButton;
	}

	/**
	 * @return the disableBudgetFilterButton
	 */
	public Boolean getDisableBudgetFilterButton() {
		return disableBudgetFilterButton;
	}

	/**
	 * @param disableBudgetFilterButton the disableBudgetFilterButton to set
	 */
	public void setDisableBudgetFilterButton(final Boolean disableBudgetFilterButton) {
		this.disableBudgetFilterButton = disableBudgetFilterButton;
	}

	/**
	 * @return the disableBudgetRefreshButton
	 */
	public Boolean getDisableBudgetRefreshButton() {
		return disableBudgetRefreshButton;
	}

	/**
	 * @param disableBudgetRefreshButton the disableBudgetRefreshButton to set
	 */
	public void setDisableBudgetRefreshButton(final Boolean disableBudgetRefreshButton) {
		this.disableBudgetRefreshButton = disableBudgetRefreshButton;
	}

	/**
	 * @return the disableBudgetAddFixedButton
	 */
	public Boolean getDisableBudgetAddFixedButton() {
		return disableBudgetAddFixedButton;
	}

	/**
	 * @param disableBudgetAddFixedButton the disableBudgetAddFixedButton to set
	 */
	public void setDisableBudgetAddFixedButton(final Boolean disableBudgetAddFixedButton) {
		this.disableBudgetAddFixedButton = disableBudgetAddFixedButton;
	}

	/**
	 * @return the disableCategoryButton
	 */
	public Boolean getDisableCategoryButton() {
		return disableCategoryButton;
	}

	/**
	 * @param disableCategoryButton the disableCategoryButton to set
	 */
	public void setDisableCategoryButton(final Boolean disableCategoryButton) {
		this.disableCategoryButton = disableCategoryButton;
	}

	/**
	 * @return the disableExpensesButton
	 */
	public Boolean getDisableExpensesButton() {
		return disableExpensesButton;
	}

	/**
	 * @param disableExpensesButton the disableExpensesButton to set
	 */
	public void setDisableExpensesButton(final Boolean disableExpensesButton) {
		this.disableExpensesButton = disableExpensesButton;
	}

	/**
	 * @return the disableExpensesFilterButton
	 */
	public Boolean getDisableExpensesFilterButton() {
		return disableExpensesFilterButton;
	}

	/**
	 * @param disableExpensesFilterButton the disableExpensesFilterButton to set
	 */
	public void setDisableExpensesFilterButton(final Boolean disableExpensesFilterButton) {
		this.disableExpensesFilterButton = disableExpensesFilterButton;
	}

	/**
	 * @return the disableExpensesNewButton
	 */
	public Boolean getDisableExpensesNewButton() {
		return disableExpensesNewButton;
	}

	/**
	 * @param disableExpensesNewButton the disableExpensesNewButton to set
	 */
	public void setDisableExpensesNewButton(final Boolean disableExpensesNewButton) {
		this.disableExpensesNewButton = disableExpensesNewButton;
	}

	/**
	 * @return the disableExpensesRefreshButton
	 */
	public Boolean getDisableExpensesRefreshButton() {
		return disableExpensesRefreshButton;
	}

	/**
	 * @param disableExpensesRefreshButton the disableExpensesRefreshButton to set
	 */
	public void setDisableExpensesRefreshButton(final Boolean disableExpensesRefreshButton) {
		this.disableExpensesRefreshButton = disableExpensesRefreshButton;
	}

	/**
	 * @return the disableExpensesAddFixedButton
	 */
	public Boolean getDisableExpensesAddFixedButton() {
		return disableExpensesAddFixedButton;
	}

	/**
	 * @param disableExpensesAddFixedButton the disableExpensesAddFixedButton to set
	 */
	public void setDisableExpensesAddFixedButton(final Boolean disableExpensesAddFixedButton) {
		this.disableExpensesAddFixedButton = disableExpensesAddFixedButton;
	}

	/**
	 * @return the disableIncomeButton
	 */
	public Boolean getDisableIncomeButton() {
		return disableIncomeButton;
	}

	/**
	 * @param disableIncomeButton the disableIncomeButton to set
	 */
	public void setDisableIncomeButton(final Boolean disableIncomeButton) {
		this.disableIncomeButton = disableIncomeButton;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getDisableCashButton() {
		return disableCashButton;
	}

	/**
	 * 
	 * @param disableCashButton
	 */
	public void setDisableCashButton(final Boolean disableCashButton) {
		this.disableCashButton = disableCashButton;
	}

	/**
	 * @return the disableIncomeAddFixedButton
	 */
	public Boolean getDisableIncomeAddFixedButton() {
		return disableIncomeAddFixedButton;
	}

	/**
	 * @param disableIncomeAddFixedButton the disableIncomeAddFixedButton to set
	 */
	public void setDisableIncomeAddFixedButton(final Boolean disableIncomeAddFixedButton) {
		this.disableIncomeAddFixedButton = disableIncomeAddFixedButton;
	}

	/**
	 * @return the disableCashAddFixedButton
	 */
	public Boolean getDisableCashAddFixedButton() {
		return disableCashAddFixedButton;
	}

	/**
	 * @param disableCashAddFixedButton the disableCashAddFixedButton to set
	 */
	public void setDisableCashAddFixedButton(final Boolean disableCashAddFixedButton) {
		this.disableCashAddFixedButton = disableCashAddFixedButton;
	}

	/**
	 * @return the disableFixedIncomeButton
	 */
	public Boolean getDisableFixedIncomeButton() {
		return disableFixedIncomeButton;
	}

	/**
	 * @param disableFixedIncomeButton the disableFixedIncomeButton to set
	 */
	public void setDisableFixedIncomeButton(final Boolean disableFixedIncomeButton) {
		this.disableFixedIncomeButton = disableFixedIncomeButton;
	}

	/**
	 * @return the disableFixedBudgetButton
	 */
	public Boolean getDisableFixedBudgetButton() {
		return disableFixedBudgetButton;
	}

	/**
	 * @param disableFixedBudgetButton the disableFixedBudgetButton to set
	 */
	public void setDisableFixedBudgetButton(final Boolean disableFixedBudgetButton) {
		this.disableFixedBudgetButton = disableFixedBudgetButton;
	}

	/**
	 * @return the disableFixedExpensesButton
	 */
	public Boolean getDisableFixedExpensesButton() {
		return disableFixedExpensesButton;
	}

	/**
	 * @param disableFixedExpensesButton the disableFixedExpensesButton to set
	 */
	public void setDisableFixedExpensesButton(final Boolean disableFixedExpensesButton) {
		this.disableFixedExpensesButton = disableFixedExpensesButton;
	}

	/**
	 * @return the disableFixedCashButton
	 */
	public Boolean getDisableFixedCashButton() {
		return disableFixedCashButton;
	}

	/**
	 * @param disableFixedCashButton the disableFixedCashButton to set
	 */
	public void setDisableFixedCashButton(final Boolean disableFixedCashButton) {
		this.disableFixedCashButton = disableFixedCashButton;
	}
}
