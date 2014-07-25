/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Category;
import es.rikimred.incost.enums.CategoryTypeEnum;
import es.rikimred.incost.global.GlobalBalance;
import es.rikimred.incost.global.GlobalBudget;
import es.rikimred.incost.global.GlobalCategory;
import es.rikimred.incost.global.GlobalFixedBudget;
import es.rikimred.incost.lazy.LazyBudgetDataModel;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.BudgetData;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.service.BudgetService;
import es.rikimred.incost.service.ExpensesService;

/**
 * Controlador de 'budget.xhtml'
 * @author jrneyra
 */
@Named
@SessionScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private BudgetService budgetService;

	@Inject
	private ExpensesService expensesService;

	@Inject
	private ViewController viewController;

	@Inject
	private BalanceController balanceController;

	// Lazy loading item list
	private LazyBudgetDataModel lazyModel;

	// Creating new item
	private BudgetData newItem = new BudgetData(new Budget());

	// Selected item that will be updated
	private BudgetData selectedItem;

	private Category selectedCategory;

	private FixedDataModel fixedBudget;

	/**
	 * 
	 */
	public BudgetController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando presupuestos");
		doUpdateTable();
	}

	/**
	 * Actualiza la tabla de budgets
	 */
	public void doUpdateTable() {
		logger.info("Actualizando tabla presupuestos");
		lazyModel = new LazyBudgetDataModel();
	}

	/**
	 * Ajusta todos los budgets
	 */
	public void doAdjustBudgets() {
		// Recorrer todos los budgets para ajustar
		for (final BudgetData b : GlobalBudget.instance().list()) {
			// Ajustar el monto
			b.setAmount(b.getAmount().subtract(b.getBudgetBalance()));
			// Actualizar balance
			b.setBudgetBalance(BigDecimal.ZERO);
			// Actualizar en BBDD
			budgetService.update(b.getEntity());
		}
		// Actualizar el item balance
		updateBalanceItem();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		Budget entity = newItem.getEntity();
		entity.setReadOnly(Boolean.FALSE);

		entity.setBalance(GlobalBalance.instance().getLoadedBalance().getEntity());
		entity = budgetService.create(entity);

		final BudgetData data = new BudgetData(entity);
		// Actualizar el mapa de budgets
		GlobalBudget.instance().put(data);

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha creado el presupuesto " + newItem.getDescription());
		newItem = new BudgetData(new Budget());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Presupuesto creado", "Presupuesto creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateItem(final ActionEvent actionEvent) {
		// Si el presupuesto no pertenece a un sub balance
		if (!selectedItem.isReadOnly()) {
			Budget entity = selectedItem.getEntity();
			entity = budgetService.update(entity);
			// / Actualizar el saldo del budget
			selectedItem.setBudgetBalance(selectedItem.getAmount().subtract(selectedItem.getTotalExpenses()));
			// Actualizar el mapa de budgets
			GlobalBudget.instance().put(selectedItem);

			// Actualizar el item balance
			updateBalanceItem();

			logger.info(String.format("El presupuesto [%d] ha sido actualizado", selectedItem.getId()));
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Presupuesto no editable",
							"No puede editar el presupuesto de un sub balance."));
		}
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		// Si se ha seleccionadp un elemento
		if (selectedItem != null) {
			// Si el presupuesto no pertenece a un sub balance
			if (!selectedItem.isReadOnly()) {
				logger.info(String.format("El presupuesto [%d] sera eliminado", selectedItem.getId()));
				GlobalBudget.instance().remove(selectedItem.getId());
				budgetService.delete(selectedItem.getId());

				// Actualizar el item balance
				updateBalanceItem();
			}
			else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Presupuesto no eliminable",
								"No puede eliminar el presupuesto de un sub balance."));
			}
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin selección",
							"Debe seleccionar un presupuesto para eliminar."));
		}
	}

	public void doCreateFromFixed(final ActionEvent actionEvent) {
		Budget entity = new Budget();
		entity.setAmount(fixedBudget.getAmount());
		entity.setCategory(fixedBudget.getCategory());
		entity.setDescription(fixedBudget.getDescription());
		entity.setBalance(GlobalBalance.instance().getLoadedBalance().getEntity());
		entity.setReadOnly(Boolean.FALSE);
		entity = budgetService.create(entity);

		final BudgetData data = new BudgetData(entity);
		// Actualizar el mapa de budgets
		GlobalBudget.instance().put(data);

		// Actualizar el item balance
		updateBalanceItem();

		fixedBudget = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Presupuesto creado", "Presupuesto creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doFilterBudgets(final ActionEvent actionEvent) {
		logger.info("Filtrando por categoría");
		if (selectedCategory != null) {
			GlobalBudget.instance().setBudgetFilteredCategory(selectedCategory);
			// Deshabilitar botones
			viewController.setBudgetPageEnableButtonsOnFilter(Boolean.TRUE);
		}
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doRefreshBudgets(final ActionEvent actionEvent) {
		GlobalBudget.instance().removeBudgetFilter();
		// Habilitar botones
		viewController.setBudgetPageEnableButtonsOnFilter(Boolean.FALSE);
	}

	public void doClearCombos(final ActionEvent actionEvent) {
		newItem = new BudgetData(new Budget());
	}

	/**
	 * @return the lazyModel
	 */
	public LazyBudgetDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the newItem
	 */
	public BudgetData getNewItem() {
		return newItem;
	}

	/**
	 * @param newItem the newItem to set
	 */
	public void setNewItem(final BudgetData newItem) {
		this.newItem = newItem;
	}

	/**
	 * @return the selectedItem
	 */
	public BudgetData getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(final BudgetData selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void setFixedBudget(final FixedDataModel fixedBudget) {
		this.fixedBudget = fixedBudget;
	}

	public FixedDataModel getFixedBudget() {
		return fixedBudget;
	}

	/**
	 * 
	 * @return
	 */
	public List<FixedDataModel> getFixedBudgetList() {
		return GlobalFixedBudget.instance().list();
	}

	/**
	 * @return the selectedCategory
	 */
	public Category getSelectedCategory() {
		return selectedCategory;
	}

	/**
	 * @param selectedCategory the selectedCategory to set
	 */
	public void setSelectedCategory(final Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	/**
	 * @return the categoryList
	 */
	public List<Category> getCategoryList() {
		return GlobalCategory.instance().entityList(CategoryTypeEnum.CATEGORY_EXPENSES);
	}

	public BigDecimal getTotalBudget() {
		// Actualizar el total de presupuestos
		BigDecimal totalBudget = BigDecimal.ZERO;
		for (final BudgetData b : GlobalBudget.instance().list()) {
			totalBudget = totalBudget.add(b.getAmount());
		}
		return totalBudget;
	}

	public BigDecimal getTotalBalance() {
		// Actualizar el total balance del presupuesto
		BigDecimal totalBalance = BigDecimal.ZERO;
		for (final BudgetData b : GlobalBudget.instance().list()) {
			totalBalance = totalBalance.add(b.getBudgetBalance());
		}
		return totalBalance;
	}

	/**
	 * 
	 */
	private void updateBalanceItem() {
		final BalanceData balanceData = GlobalBalance.instance().getLoadedBalance();
		// Actualizar el budget
		balanceData.setTotalBudget(getTotalBudget());
		// Actualizar el balance
		balanceData.calculateBalance();
		// Volverlo a guardar en el mapa
		GlobalBalance.instance().put(balanceData);

		// Si es un sub balance, actualizar el padre guardando el balance
		if (balanceData.getParent() != null) {
			balanceController.saveBalances();
		}

		// Actualizar los libros
		balanceController.updateBooks();
	}
}
