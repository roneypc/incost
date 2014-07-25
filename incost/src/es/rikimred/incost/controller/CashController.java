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

import es.rikimred.incost.entity.Cash;
import es.rikimred.incost.global.GlobalBalance;
import es.rikimred.incost.global.GlobalCash;
import es.rikimred.incost.global.GlobalFixedCash;
import es.rikimred.incost.lazy.LazyCashDataModel;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.CashData;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.service.CashService;

/**
 * Controlador de 'cash.xhtml'
 * @author roberto
 */
@Named
@SessionScoped
public class CashController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private CashService cashService;

	// Lazy loading item list
	private LazyCashDataModel lazyModel;

	// Creating new item
	private CashData newItem;

	// Selected item that will be updated
	private CashData selectedItem;

	private FixedDataModel fixedCash;

	/**
	 * 
	 */
	public CashController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando cash");
		newItem = new CashData(new Cash());
		// newItem.setLastUpdate(new Date());
		doUpdateTable();
	}

	/**
	 * Actualiza la tabla de cash
	 */
	public void doUpdateTable() {
		logger.info("Actualizando tabla cash");
		lazyModel = new LazyCashDataModel();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		Cash entity = newItem.getEntity();
		entity.setBalance(GlobalBalance.instance().getLoadedBalance().getEntity());
		entity.setLastUpdate(new Date());
		entity = cashService.create(entity);

		// Actualizar el mapa de cash
		// Global.cashPut(new CashData(entity));
		GlobalCash.instance().put(new CashData(entity));

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha creado el cash " + newItem.getDescription());
		newItem = new CashData(new Cash());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Efectivo creado", "Efectivo creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateItem(final ActionEvent actionEvent) {
		Cash entity = selectedItem.getEntity();
		entity.setBalance(GlobalBalance.instance().getLoadedBalance().getEntity());
		entity.setLastUpdate(new Date());
		entity = cashService.update(selectedItem.getEntity());

		// Actualizar el mapa de cash
		// Global.cashPut(new CashData(entity));
		GlobalCash.instance().put(new CashData(entity));

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha actualizado el cash " + selectedItem.getDescription());
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Efectivo actualizado",
						"Efectivo actualizado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		// Si se ha seleccionado un elemento
		if (selectedItem != null) {
			// Global.cashRemove(selectedItem.getId());
			GlobalCash.instance().remove(selectedItem.getId());
			cashService.delete(selectedItem.getId());

			// Actualizar el item balance
			updateBalanceItem();
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin selección",
							"Debe seleccionar un efectivo para eliminar."));
		}
	}

	public void doCreateFromFixed(final ActionEvent actionEvent) {
		Cash entity = new Cash();
		entity.setAmount(fixedCash.getAmount());
		entity.setDescription(fixedCash.getDescription());
		entity.setLastUpdate(new Date());
		entity.setBalance(GlobalBalance.instance().getLoadedBalance().getEntity());
		entity = cashService.create(entity);

		// Actualizar el mapa de cash
		// Global.cashPut(new CashData(entity));
		GlobalCash.instance().put(new CashData(entity));

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha creado el efectivo " + newItem.getDescription());
		fixedCash = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Efectivo creado", "Efectivo creado correctamente"));
	}

	/**
	 * @return the lazyModel
	 */
	public LazyCashDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the newItem
	 */
	public CashData getNewItem() {
		return newItem;
	}

	public void setNewItem(final CashData newItem) {
		this.newItem = newItem;
	}

	/**
	 * @return the selectedItem
	 */
	public CashData getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(final CashData selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void setFixedCash(final FixedDataModel fixedCash) {
		this.fixedCash = fixedCash;
	}

	public FixedDataModel getFixedCash() {
		return fixedCash;
	}

	/**
	 * 
	 * @return
	 */
	public List<FixedDataModel> getFixedCashList() {
		// return Global.fixedCashList();
		return GlobalFixedCash.instance().list();
	}

	/**
	 * @return the totalIncome
	 */
	public BigDecimal getTotalCash() {
		// Actualizar el total de cash
		BigDecimal totalCash = BigDecimal.ZERO;
		// for (final CashData i : Global.cashList()) {
		for (final CashData i : GlobalCash.instance().list()) {
			totalCash = totalCash.add(i.getAmount());
		}
		return totalCash;
	}

	/**
	 * 
	 */
	private void updateBalanceItem() {
		final BalanceData balanceData = GlobalBalance.instance().getLoadedBalance();
		// Actualizar el cash
		balanceData.setTotalCash(getTotalCash());
		// Actualizar el balance
		balanceData.calculateBalance();
		// balanceData.setTotalBalance(getTotalIncome().subtract(balanceData.getTotalBudget()));
		// Volverlo a guardar en el mapa
		GlobalBalance.instance().put(balanceData);
	}
}
