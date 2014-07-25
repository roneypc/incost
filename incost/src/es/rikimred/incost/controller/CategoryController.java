/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.enums.CategoryTypeEnum;
import es.rikimred.incost.global.Global;
import es.rikimred.incost.global.GlobalCategory;
import es.rikimred.incost.lazy.LazyCategoryDataModel;
import es.rikimred.incost.model.CategoryData;
import es.rikimred.incost.service.CategoryService;

/**
 * Controlador de 'category.xhtml'
 * @author roberto
 */
@Named
@SessionScoped
public class CategoryController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private CategoryService categoryService;

	@Inject
	private BudgetController budgetController;

	// Lazy loading item list
	private LazyCategoryDataModel lazyModel;

	// Creating new item
	private CategoryData newItem = new CategoryData(new Category());

	// Selected item that will be updated
	private CategoryData selectedItem = new CategoryData(new Category());

	/**
	 * 
	 */
	public CategoryController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando categorías");
		lazyModel = new LazyCategoryDataModel();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		Category entity = newItem.getEntity();
		entity.setUser(Global.activeUser());
		entity = categoryService.create(entity);

		// Actualizar el mapa de categories
		GlobalCategory.instance().put(new CategoryData(entity));

		logger.info("Se ha creado la categoría " + newItem.getName());
		newItem = new CategoryData(new Category());
		// Actualizar la tabla de presupuestos
		budgetController.doUpdateTable();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoría creada", "Categoría creado correctamente"));
	}

	public void doUpdateItem(final ActionEvent actionEvent) {
		final Category entity = categoryService.update(selectedItem.getEntity());
		// Actualizar el mapa de categories
		GlobalCategory.instance().put(new CategoryData(entity));
		logger.info(String.format("La categoría [%d] ha sido actualizada", selectedItem.getId()));
		// Actualizar la tabla de presupuestos
		budgetController.doUpdateTable();

	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		GlobalCategory.instance().remove(selectedItem.getId());
		categoryService.delete(selectedItem.getId());
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoría eliminada",
								"Categoría eliminada correctamente"));
	}

	public void doClearCombos(final ActionEvent actionEvent) {
		newItem = new CategoryData(new Category());
	}

	public LazyCategoryDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the newItem
	 */
	public CategoryData getNewItem() {
		return newItem;
	}

	public void setNewItem(final CategoryData newItem) {
		this.newItem = newItem;
	}

	/**
	 * @return the selectedItem
	 */
	public CategoryData getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(final CategoryData selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * @return the categoryTypes
	 */
	public List<CategoryTypeEnum> getCategoryTypes() {
		return Arrays.asList(CategoryTypeEnum.CATEGORY_INCOMES, CategoryTypeEnum.CATEGORY_EXPENSES);
	}
}
