/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import es.rikimred.incost.entity.Income;
import es.rikimred.incost.global.GlobalBalance;
import es.rikimred.incost.global.GlobalFixedIncome;
import es.rikimred.incost.global.GlobalIncome;
import es.rikimred.incost.lazy.LazyIncomeDataModel;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.model.IncomeData;
import es.rikimred.incost.service.IncomeService;

/**
 * Controlador de 'income.xhtml'
 * @author jrneyra
 */
@Named
@SessionScoped
public class IncomeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private IncomeService incomeService;

	// Lazy loading item list
	private LazyIncomeDataModel lazyModel;

	// Creating new item
	private IncomeData newItem;

	// Selected item that will be updated
	private IncomeData selectedItem;

	private FixedDataModel fixedIncome;

	/**
	 * 
	 */
	public IncomeController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando ingresos");
		newItem = new IncomeData(new Income());
		newItem.setIncomeDate(new Date());
		doUpdateTable();
	}

	/**
	 * Actualiza la tabla de incomes
	 */
	public void doUpdateTable() {
		logger.info("Actualizando tabla ingresos");
		// balanceData =
		// (BalanceData) SessionUtils.getSessionMap().get(SessionKeys.BALANCE_LOADED.Key());
		lazyModel = new LazyIncomeDataModel();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		Income entity = newItem.getEntity();
		entity.setBalance(GlobalBalance.instance().getLoadedBalance().getEntity());
		entity = incomeService.create(entity);

		// Actualizar el mapa de incomes
		GlobalIncome.instance().put(new IncomeData(entity));
		// Global.incomePut(new IncomeData(entity));

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha creado el ingreso " + newItem.getDescription());
		newItem = new IncomeData(new Income());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingreso creado", "Ingreso creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateItem(final ActionEvent actionEvent) {
		final Income entity = incomeService.update(selectedItem.getEntity());
		// Actualizar el mapa de incomes
		GlobalIncome.instance().put(new IncomeData(entity));
		// Global.incomePut(new IncomeData(entity));

		// Actualizar el item balance
		updateBalanceItem();

		logger.info(String.format("El ingreso [%d] ha sido actualizado", selectedItem.getId()));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		// Si se ha seleccionado un elemento
		if (selectedItem != null) {
			GlobalIncome.instance().remove(selectedItem.getId());
			// Global.incomeRemove(selectedItem.getId());
			incomeService.delete(selectedItem.getId());

			// Actualizar el item balance
			updateBalanceItem();
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin selección",
							"Debe seleccionar un ingreso para eliminar."));
		}
	}

	public void doCreateFromFixed(final ActionEvent actionEvent) {
		Income entity = new Income();
		entity.setAmount(fixedIncome.getAmount());
		entity.setDescription(fixedIncome.getDescription());
		entity.setIncomeDate(new Date());
		entity.setBalance(GlobalBalance.instance().getLoadedBalance().getEntity());
		entity = incomeService.create(entity);

		// Actualizar el mapa de incomes
		GlobalIncome.instance().put(new IncomeData(entity));
		// Global.incomePut(new IncomeData(entity));

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha creado el ingreso " + newItem.getDescription());
		fixedIncome = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingreso creado", "Ingreso creado correctamente"));
	}

	/**
	 * @return the lazyModel
	 */
	public LazyIncomeDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the newItem
	 */
	public IncomeData getNewItem() {
		return newItem;
	}

	public void setNewItem(final IncomeData newItem) {
		this.newItem = newItem;
	}

	/**
	 * @return the selectedItem
	 */
	public IncomeData getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(final IncomeData selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void setFixedIncome(final FixedDataModel fixedIncome) {
		this.fixedIncome = fixedIncome;
	}

	public FixedDataModel getFixedIncome() {
		return fixedIncome;
	}

	/**
	 * 
	 * @return
	 */
	public List<FixedDataModel> getFixedIncomeList() {
		return GlobalFixedIncome.instance().list();
	}

	/**
	 * @return the totalIncome
	 */
	public BigDecimal getTotalIncome() {
		// Actualizar el total de ingresos
		BigDecimal totalIncome = BigDecimal.ZERO;
		for (final IncomeData i : GlobalIncome.instance().list()) {// Global.incomeList()) {
			totalIncome = totalIncome.add(i.getAmount());
		}
		return totalIncome;
	}

	/**
	 * 
	 */
	private void updateBalanceItem() {
		final BalanceData balanceData = GlobalBalance.instance().getLoadedBalance();
		// Actualizar el income
		balanceData.setTotalIncome(getTotalIncome());
		// Actualizar el balance
		balanceData.calculateBalance();
		// balanceData.setTotalBalance(getTotalIncome().subtract(balanceData.getTotalBudget()));
		// Volverlo a guardar en el mapa
		GlobalBalance.instance().put(balanceData);
	}
}
