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

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Expenses;
import es.rikimred.incost.global.GlobalBalance;
import es.rikimred.incost.global.GlobalBudget;
import es.rikimred.incost.global.GlobalExpenses;
import es.rikimred.incost.global.GlobalFixedExpenses;
import es.rikimred.incost.lazy.LazyExpensesDataModel;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.BudgetData;
import es.rikimred.incost.model.ExpensesData;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.service.ExpensesService;

/**
 * Controlador de 'expenses.xhtml'
 * @author jrneyra
 */
@Named
@SessionScoped
public class ExpensesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private ExpensesService expensesService;

	@Inject
	private ViewController viewController;

	@Inject
	private BalanceController balanceController;

	// Lazy loading item list
	private LazyExpensesDataModel lazyModel;

	// Creating new item
	private ExpensesData newItem = new ExpensesData(new Expenses());

	// Selected item that will be updated
	private ExpensesData selectedItem;

	private FixedDataModel fixedExpenses;

	private Budget selectedBudget;

	private BigDecimal remainingBalance;

	/**
	 * 
	 */
	public ExpensesController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando gastos");
		doUpdateTable();
	}

	/**
	 * Actualiza la tabla de expenses
	 */
	public void doUpdateTable() {
		logger.info("Actualizando tabla gastos");
		lazyModel = new LazyExpensesDataModel();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		Expenses entity = newItem.getEntity();
		entity.setReadOnly(Boolean.FALSE);
		entity = expensesService.create(entity);

		final ExpensesData data = new ExpensesData(entity);
		// Actualizar el mapa de expenses
		GlobalExpenses.instance().put(data);

		// Actualizar el item budget
		updateBudgetItem(newItem.getBudget().getId());

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha creado el gasto " + newItem.getDescription());
		newItem = new ExpensesData(new Expenses());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Gasto creado", "Gasto creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateItem(final ActionEvent actionEvent) {
		// Si el gasto no pertenece a un sub balance
		if (!selectedItem.isReadOnly()) {
			Expenses entity = selectedItem.getEntity();
			entity = expensesService.update(entity);

			// Actualizar el mapa de expenses
			GlobalExpenses.instance().put(selectedItem);

			// Actualizar el item budget
			updateBudgetItem(selectedItem.getBudget().getId());

			// Actualizar el item balance
			updateBalanceItem();

			logger.info(String.format("El gasto [%d] ha sido actualizado", selectedItem.getId()));
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gasto no editable",
							"No puede editar el gasto de un sub balance."));
		}
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		// Si se ha seleccionadp un elemento
		if (selectedItem != null) {
			// Si el gasto no pertenece a un sub balance
			if (!selectedItem.isReadOnly()) {
				logger.info(String.format("El gasto [%d] será eliminado", selectedItem.getId()));
				GlobalExpenses.instance().remove(selectedItem.getId());
				expensesService.delete(selectedItem.getId());

				// Actualizar el item budget
				updateBudgetItem(selectedItem.getBudget().getId());

				// Actualizar el item balance
				updateBalanceItem();

				selectedItem = null;
			}
			else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gasto no eliminable",
								"No puede eliminar el gasto de un sub balance."));
			}
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin selección",
							"Debe seleccionar un gasto para eliminar."));
		}
	}

	public void doCreateFromFixed(final ActionEvent actionEvent) {
		Expenses entity = new Expenses();
		entity.setAmount(fixedExpenses.getAmount());
		entity.setDescription(fixedExpenses.getDescription());
		entity.setExpensesDate(new Date());
		entity.setBudget(selectedBudget);

		entity.setReadOnly(Boolean.FALSE);
		entity = expensesService.create(entity);

		final ExpensesData data = new ExpensesData(entity);
		// Actualizar el mapa de expenses
		GlobalExpenses.instance().put(data);

		// Actualizar el item budget
		updateBudgetItem(selectedBudget.getId());

		// Actualizar el item balance
		updateBalanceItem();

		logger.info("Se ha creado el gasto " + entity.getDescription());
		entity = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Gasto creado", "Gasto creado correctamente"));
	}

	public void doFilterExpenses(final ActionEvent actionEvent) {
		logger.info("Filtrando por presupuesto");
		if (selectedBudget != null) {
			GlobalExpenses.instance().setExpensesFilteredBudget(selectedBudget);
			// Deshabilitar botones
			viewController.setExpensesPageEnableButtonsOnFilter(Boolean.TRUE);
		}
	}

	public void doRefreshExpenses(final ActionEvent actionEvent) {
		logger.info("Refrescando expenses");
		GlobalExpenses.instance().removeExpensesFilter();
		// Habilitar botones
		viewController.setExpensesPageEnableButtonsOnFilter(Boolean.FALSE);
	}

	public void doClearCombos(final ActionEvent actionEvent) {
		remainingBalance = BigDecimal.ZERO;
		newItem = new ExpensesData(new Expenses());
	}

	/**
	 * @return the lazyModel
	 */
	public LazyExpensesDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the newItem
	 */
	public ExpensesData getNewItem() {
		return newItem;
	}

	/**
	 * @param newItem the newItem to set
	 */
	public void setNewItem(final ExpensesData newItem) {
		this.newItem = newItem;
	}

	/**
	 * @return the selectedItem
	 */
	public ExpensesData getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(final ExpensesData selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void setFixedExpenses(final FixedDataModel fixedExpenses) {
		this.fixedExpenses = fixedExpenses;
	}

	public FixedDataModel getFixedExpenses() {
		return fixedExpenses;
	}

	public void setSelectedBudget(final Budget selectedBudget) {
		this.selectedBudget = selectedBudget;
	}

	public Budget getSelectedBudget() {
		return selectedBudget;
	}

	/**
	 * @return the budgetList
	 */
	public List<Budget> getBudgetList() {
		return GlobalBudget.instance().entityList();
	}

	/**
	 * 
	 * @return
	 */
	public List<FixedDataModel> getFixedExpensesList() {
		return GlobalFixedExpenses.instance().list();
	}

	public BigDecimal getTotalExpenses() {
		// Actualizar el total de gastos
		BigDecimal totalExpenses = BigDecimal.ZERO;
		for (final ExpensesData b : GlobalExpenses.instance().list()) {
			totalExpenses = totalExpenses.add(b.getAmount());
		}
		return totalExpenses;
	}

	/**
	 * @return the remainingBalance
	 */
	public BigDecimal getRemainingBalance() {
		return remainingBalance;
	}

	/**
	 * @return the remainingBalance
	 */
	public BigDecimal getSelectedRemainingBalance() {
		BigDecimal selectedRemainingBalance = BigDecimal.ZERO;
		if (selectedItem != null) {
			BudgetData budgetData = GlobalBudget.instance().retrive(selectedItem.getBudget().getId());
			selectedRemainingBalance = budgetData.getBudgetBalance();
		}
		return selectedRemainingBalance;
	}

	/**
	 * 
	 * @param idBudget
	 * @param amount
	 */
	private void updateBudgetItem(final Integer idBudget) {
		// Recuperar el budget del expenses
		final BudgetData budgetData = GlobalBudget.instance().retrive(idBudget);
		final List<ExpensesData> expensesList = GlobalExpenses.instance().expensesByBudget(idBudget);
		BigDecimal totalExpenses = BigDecimal.ZERO;
		for (final ExpensesData e : expensesList) {
			totalExpenses = totalExpenses.add(e.getAmount());
		}
		// Actualizar el expenses
		budgetData.setTotalExpenses(totalExpenses);
		// Volverlo a guardar en el mapa
		GlobalBudget.instance().put(budgetData);
	}

	/**
	 * 
	 */
	private void updateBalanceItem() {
		final BalanceData balanceData = GlobalBalance.instance().getLoadedBalance();
		// Actualizar el expenses
		balanceData.setTotalExpenses(getTotalExpenses());
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

	public void onBudgetComboSelection() {
		// Recuperar el budget del expenses
		if (newItem.getBudget() != null) {
			final BudgetData budgetData = GlobalBudget.instance().retrive(newItem.getBudget().getId());
			this.remainingBalance = budgetData.getBudgetBalance();
		}
		else {
			remainingBalance = BigDecimal.ZERO;
		}
	}
}
