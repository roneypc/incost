/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import es.rikimred.incost.entity.FixedData;
import es.rikimred.incost.global.GlobalFixedExpenses;
import es.rikimred.incost.lazy.LazyFixedExpensesDataModel;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.service.FixedDataService;

/**
 * Controlador de 'fixedExpenses.xhtml'
 * @author jrneyra
 */
@Named
@SessionScoped
public class FixedExpensesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private FixedDataService fixedDataService;

	// Lazy loading item list
	private LazyFixedExpensesDataModel lazyModel;

	// Creating new item
	private FixedDataModel newItem = new FixedDataModel(new FixedData());

	// Selected item that will be updated
	private FixedDataModel selectedItem = new FixedDataModel(new FixedData());

	/**
	 * 
	 */
	public FixedExpensesController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando gastos fijos");
		doUpdateTable();
	}

	/**
	 * Actualiza la tabla de fixed expenses
	 */
	public void doUpdateTable() {
		logger.info("Actualizando tabla gastos fijos");
		lazyModel = new LazyFixedExpensesDataModel();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		FixedData entity = newItem.getEntity();
		entity.setCategory(GlobalFixedExpenses.instance().getFixedExpensesCategory());
		entity = fixedDataService.create(entity);

		// Actualizar el mapa de fixed expenses
		GlobalFixedExpenses.instance().put(new FixedDataModel(entity));

		logger.info("Se ha creado el gasto fijo " + newItem.getDescription());
		newItem = new FixedDataModel(new FixedData());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Gasto fijo creado", "Gasto fijo creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateItem(final ActionEvent actionEvent) {
		final FixedData entity = fixedDataService.update(selectedItem.getEntity());
		// Actualizar el mapa de fixed expenses
		GlobalFixedExpenses.instance().put(new FixedDataModel(entity));

		logger.info(String.format("El gasto fijo [%d] ha sido actualizado", selectedItem.getId()));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		// Si se ha seleccionado un elemento
		if (selectedItem != null) {
			GlobalFixedExpenses.instance().remove(selectedItem.getId());
			fixedDataService.delete(selectedItem.getId());
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin selección",
							"Debe seleccionar un gasto fijo para eliminar."));
		}
	}

	/**
	 * @return the lazyModel
	 */
	public LazyFixedExpensesDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the newItem
	 */
	public FixedDataModel getNewItem() {
		return newItem;
	}

	public void setNewItem(final FixedDataModel newItem) {
		this.newItem = newItem;
	}

	/**
	 * @return the selectedItem
	 */
	public FixedDataModel getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(final FixedDataModel selectedItem) {
		this.selectedItem = selectedItem;
	}
}
