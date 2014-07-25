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
import es.rikimred.incost.global.GlobalFixedCash;
import es.rikimred.incost.lazy.LazyFixedCashDataModel;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.service.FixedDataService;

/**
 * Controlador de 'fixedCash.xhtml'
 * @author jrneyra
 */
@Named
@SessionScoped
public class FixedCashController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private FixedDataService fixedDataService;

	// Lazy loading item list
	private LazyFixedCashDataModel lazyModel;

	// Creating new item
	private FixedDataModel newItem = new FixedDataModel(new FixedData());

	// Selected item that will be updated
	private FixedDataModel selectedItem = new FixedDataModel(new FixedData());

	/**
	 * 
	 */
	public FixedCashController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando cash fijos");
		doUpdateTable();
	}

	/**
	 * Actualiza la tabla de fixed cash
	 */
	public void doUpdateTable() {
		logger.info("Actualizando tabla cash fijos");
		lazyModel = new LazyFixedCashDataModel();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		FixedData entity = newItem.getEntity();
		entity.setCategory(GlobalFixedCash.instance().getFixedCashCategory());
		entity = fixedDataService.create(entity);

		// Actualizar el mapa de fixed cash
		// Global.fixedCashPut(new FixedDataModel(entity));
		GlobalFixedCash.instance().put(new FixedDataModel(entity));

		logger.info("Se ha creado el cash fijo " + newItem.getDescription());
		newItem = new FixedDataModel(new FixedData());
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Efectivo fijo creado",
						"Efectivo fijo creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateItem(final ActionEvent actionEvent) {
		final FixedData entity = fixedDataService.update(selectedItem.getEntity());
		// Actualizar el mapa de fixed cash
		// Global.fixedCashPut(new FixedDataModel(entity));
		GlobalFixedCash.instance().put(new FixedDataModel(entity));

		logger.info(String.format("Efectivo fijo [%d] ha sido actualizado", selectedItem.getId()));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		// Si se ha seleccionado un elemento
		if (selectedItem != null) {
			// Global.fixedCashRemove(selectedItem.getId());
			GlobalFixedCash.instance().remove(selectedItem.getId());
			fixedDataService.delete(selectedItem.getId());
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin selección",
							"Debe seleccionar un efectivo fijo para eliminar."));
		}
	}

	/**
	 * @return the lazyModel
	 */
	public LazyFixedCashDataModel getLazyModel() {
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
