/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.primefaces.event.SelectEvent;

import es.rikimred.incost.entity.Balance;
import es.rikimred.incost.entity.Book;
import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Cash;
import es.rikimred.incost.entity.Category;
import es.rikimred.incost.entity.Expenses;
import es.rikimred.incost.entity.Income;
import es.rikimred.incost.entity.User;
import es.rikimred.incost.enums.BalanceStateEnum;
import es.rikimred.incost.enums.BalanceStatusEnum;
import es.rikimred.incost.global.Global;
import es.rikimred.incost.global.GlobalBalance;
import es.rikimred.incost.global.GlobalBook;
import es.rikimred.incost.global.GlobalBudget;
import es.rikimred.incost.global.GlobalCash;
import es.rikimred.incost.global.GlobalExpenses;
import es.rikimred.incost.global.GlobalIncome;
import es.rikimred.incost.lazy.LazyBalanceDataModel;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.BudgetData;
import es.rikimred.incost.model.CashData;
import es.rikimred.incost.model.ExpensesData;
import es.rikimred.incost.model.IncomeData;
import es.rikimred.incost.service.BalanceService;
import es.rikimred.incost.service.BookService;
import es.rikimred.incost.service.BudgetService;
import es.rikimred.incost.service.CashService;
import es.rikimred.incost.service.CategoryService;
import es.rikimred.incost.service.ExpensesService;
import es.rikimred.incost.service.IncomeService;
import es.rikimred.incost.service.UserService;

/**
 * Controlador de 'balance.xhtml'
 * @author roberto
 */
@Named
@SessionScoped
public class BalanceController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private BalanceService balanceService;

	@Inject
	private IncomeService incomeService;

	@Inject
	private UserService userService;

	@Inject
	private BudgetService budgetService;

	@Inject
	private BookService bookService;

	@Inject
	private ExpensesService expensesService;

	@Inject
	private CashService cashService;

	@Inject
	private CategoryService categoryService;

	@Inject
	private IncomeController incomeController;

	@Inject
	private BudgetController budgetController;

	@Inject
	private BookController bookController;

	// Lazy loading balance list
	private LazyBalanceDataModel lazyModel;

	// Creating new balance
	private BalanceData newItem = new BalanceData(new Balance());

	// Selected balance that will be updated
	private BalanceData selectedItem;

	private BalanceData selectedSubBalance;

	private BalanceData loadedBalance;

	private Balance selectedLinkBalance;

	/**
	 * 
	 */
	public BalanceController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando balances");

		// Cargar el modelo de datos
		lazyModel =
				new LazyBalanceDataModel(balanceService, bookService, incomeService, cashService, budgetService,
						expensesService);
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateBalance() {
		logger.info("YearMonth = " + newItem.getYearMonth());
		Balance entity = newItem.getEntity();
		// Completar los atributos
		entity.setInUse(Boolean.FALSE);
		entity.setHasSubBalance(Boolean.FALSE);
		entity.setBalanceState(BalanceStateEnum.OPENED);
		entity.setBalanceStatus(BalanceStatusEnum.UNDEFINED);
		entity.setLastUpdate(new Date());
		// Crear el balance en BBDD
		entity = balanceService.create(entity);
		logger.info("Se ha creado el balance " + entity.getName());

		// Obtener los balances del usuario
		final List<Balance> balanceList = balanceService.getBalancesByIdUser(Global.activeUser().getId());
		// Agregar el nuevo balance
		balanceList.add(entity);
		// Actualizar los balances del usuario
		final User user = Global.activeUser();
		user.setBalances(balanceList);
		// Actualizar el usuario en BBDD
		userService.update(user);
		// Actualizar el usuario en la sesion
		Global.setActiveUser(user);

		// Actualizar el mapa de balances
		GlobalBalance.instance().put(new BalanceData(entity));
		newItem = new BalanceData(new Balance());

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Balance creado", String.format(
						"Balance [%s] creado correctamente", entity.getName())));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateBalance(final ActionEvent actionEvent) {
		final Balance entity = balanceService.update(selectedItem.getEntity());
		// Actualizar el mapa de balances
		GlobalBalance.instance().put(new BalanceData(entity));
		logger.info("Se ha actualizado el balance " + selectedItem.getName());
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteBalance(final ActionEvent actionEvent) {
		// Verificar si el balance es eliminable (sin presupuestos y éstos sin gastos)
		balanceService.delete(selectedItem.getId());
		GlobalBalance.instance().remove(selectedItem.getId());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Balance eliminado", "Balance eliminado correctamente"));
	}

	/**
	 * Cargar un balance
	 * @param actionEvent
	 */
	public void doLoadBalance(final ActionEvent actionEvent) {
		if (selectedItem != null && selectedItem.getId() != null) {
			// Si ya hay un balance cargado, guardarlo
			saveBalances();

			// Set del balance seleccionado
			setLoadedBalance(selectedItem);

			// Recuperar los ingresos
			final List<Income> incomeList = incomeService.getIncomesByIdBalance(selectedItem.getId());
			// Limpiar el mapa de incomes
			GlobalIncome.instance().clear();
			// Construir el mapa de incomes
			for (final Income income : incomeList) {
				GlobalIncome.instance().put(new IncomeData(income));
			}

			// Recuperar los cash
			final List<Cash> cashList = cashService.getCashListByIdBalance(selectedItem.getId());
			// Limpiar el mapa de cash
			GlobalCash.instance().clear();
			// Construir el mapa de cash
			for (final Cash cash : cashList) {
				// Global.cashPut(new CashData(cash));
				GlobalCash.instance().put(new CashData(cash));
			}

			// Recuperar los budgets
			final List<Budget> budgetList = budgetService.getBudgetsByIdBalance(selectedItem.getId());
			// Limpiar el mapa de budgets
			GlobalBudget.instance().clear();
			// Limpiar el mapa de expenses
			GlobalExpenses.instance().clear();
			// Lista de expenses
			List<Expenses> expensesList;
			// Totalizador de expenses
			BigDecimal totalExpenses;
			// Data
			BudgetData bData;
			ExpensesData eData;
			// Construir el mapa de budgets
			for (final Budget budget : budgetList) {
				// Recuperar los expenses
				expensesList = expensesService.getExpensesByIdBudget(budget.getId());
				totalExpenses = BigDecimal.ZERO;
				// Construir el mapa de expenses
				for (final Expenses expenses : expensesList) {
					eData = new ExpensesData(expenses);
					GlobalExpenses.instance().put(eData);
					// Totalizar los expenses
					totalExpenses = totalExpenses.add(expenses.getAmount());
				}
				bData = new BudgetData(budget);
				bData.setTotalExpenses(totalExpenses);
				bData.setBudgetBalance(bData.getAmount().subtract(totalExpenses));
				GlobalBudget.instance().put(bData);
			}

			// Actualizar la tabla de ingresos
			incomeController.doUpdateTable();
			// Actualizar la tabla de budgets
			budgetController.doUpdateTable();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Balance cargado",
							"Se ha cargado con exito el balance " + selectedItem.getName()));
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No valido!", "Debe seleccionar un balance"));
		}
	}

	/**
	 * Crear un enlaze
	 * @param actionEvent
	 */
	public void doCreateLink(final ActionEvent actionEvent) {
		if (selectedLinkBalance != null) {
			// Obtener el balanceData del Global
			final BalanceData linkBalanceData = GlobalBalance.instance().retrive(selectedLinkBalance.getId());

			// Actualizar el padre
			selectedItem.setHasSubBalance(true);
			// Actualizar el sub balance
			linkBalanceData.setParent(selectedItem.getEntity());
			// Crear el enlace en BBDD
			balanceService.update(selectedItem.getEntity());
			balanceService.update(linkBalanceData.getEntity());

			// Obtener el budget relacionado
			// Obtener la categoría del tipo SUB-BALANCE
			final Category category = categoryService.getSubBalanceCategoryByIdUser(Global.activeUser().getId());
			// Agregar presupuesto del tipo SUB-BALANCE
			Budget budgetEntity = new Budget();
			budgetEntity.setAmount(linkBalanceData.getTotalBudget());
			String description =
					String.format("SUB%05d: %s - PRESUPUESTO", linkBalanceData.getId(), linkBalanceData.getName());
			budgetEntity.setDescription(description);
			budgetEntity.setCategory(category);
			budgetEntity.setBalance(selectedItem.getEntity());
			budgetEntity.setIdSubBalance(linkBalanceData.getId());
			budgetEntity.setReadOnly(true);
			budgetEntity = budgetService.create(budgetEntity);
			if (budgetEntity.getId() != null) {
				// Agregar el gasto del tipo SUB-BALANCE
				Expenses expensesEntity = new Expenses();
				expensesEntity.setAmount(linkBalanceData.getTotalExpenses());
				expensesEntity.setBudget(budgetEntity);
				description = String.format("SUB%05d: %s - GASTO", linkBalanceData.getId(), linkBalanceData.getName());
				expensesEntity.setDescription(description);
				expensesEntity.setExpensesDate(linkBalanceData.getLastUpdate());
				expensesEntity.setReadOnly(true);
				expensesEntity = expensesService.create(expensesEntity);
				if (expensesEntity.getId() != null) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Balance enlazado",
									"Se ha creado el sub balance " + linkBalanceData.getName() + " dentro del balance "
											+ selectedItem.getName()));
				}
			}

			// Actualizar mapa
			Global.subBalancePut(selectedItem.getId(), linkBalanceData);

			// Cargar el balance
			doLoadBalance(actionEvent);
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No valido!",
							"Debe seleccionar un balance para enlazar"));
		}
	}

	/**
	 * Eliminar un enlace
	 * @param actionEvent
	 */
	public void doDeleteLink(final ActionEvent actionEvent) {
		if (selectedSubBalance != null) {
			Balance parent = selectedSubBalance.getParent();
			// Obtener el budget relacionado
			final Budget budget = budgetService.getBudgetsByIdSubBalance(selectedSubBalance.getId());
			if (budget != null) {
				// Obtener los expenses relacionados
				final List<Expenses> expenses = expensesService.getExpensesByIdBudget(budget.getId());
				// Eliminarlos
				for (final Expenses e : expenses) {
					expensesService.delete(e.getId());
				}
				// Eliminar el budget
				budgetService.delete(budget.getId());

				// Actualizar el sub balance
				selectedSubBalance.setParent(null);
				balanceService.update(selectedSubBalance.getEntity());

				// Si el parent ya no tiene mas sub balances
				if (balanceService.getSubBalances(parent.getId()).isEmpty()) {
					parent.setHasSubBalance(false);
				}
				// Actualizar el parent
				parent = balanceService.update(parent);

				// Actualizar mapa
				Global.subBalanceRemove(parent.getId(), selectedSubBalance);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "SubBalance eliminado",
								"Se ha eliminado el sub balance " + selectedSubBalance.getName() + " del balance "
										+ parent.getName()));
				selectedSubBalance = null;
			}
		}

	}

	/**
	 * 
	 * @param event
	 */
	public void onBalanceRowSelect(final SelectEvent event) {
	}

	/**
	 * @return
	 */
	public BalanceData getSelectedItem() {
		return selectedItem;
	}

	/**
	 * 
	 * @param selectedBalance
	 */
	public void setSelectedItem(final BalanceData selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * @return the selectedSubBalance
	 */
	public BalanceData getSelectedSubBalance() {
		return selectedSubBalance;
	}

	/**
	 * @param selectedSubBalance the selectedSubBalance to set
	 */
	public void setSelectedSubBalance(final BalanceData selectedSubBalance) {
		this.selectedSubBalance = selectedSubBalance;
	}

	/**
	 * @return the selectedLinkBalance
	 */
	public Balance getSelectedLinkBalance() {
		return selectedLinkBalance;
	}

	/**
	 * @param selectedLinkBalance the selectedLinkBalance to set
	 */
	public void setSelectedLinkBalance(final Balance selectedLinkBalance) {
		this.selectedLinkBalance = selectedLinkBalance;
	}

	/**
	 * 
	 * @return
	 */
	public BalanceData getNewItem() {
		return newItem;
	}

	/**
	 * 
	 * @param newBalance
	 */
	public void setNewItem(final BalanceData newItem) {
		this.newItem = newItem;
	}

	/**
	 * 
	 * @return LazyDataModel
	 */
	public LazyBalanceDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the loadedBalance
	 */
	public BalanceData getLoadedBalance() {
		return GlobalBalance.instance().getLoadedBalance();
	}

	/**
	 * @param loadedBalance the loadedBalance to set
	 */
	public void setLoadedBalance(final BalanceData loadedBalance) {
		this.loadedBalance = loadedBalance;
		Global.setIdLoadedBalance(loadedBalance.getId());
	}

	/**
	 * 
	 * @return
	 */
	public String getLoadedBalanceName() {
		return loadedBalance.getName() + " - " + loadedBalance.getMonth() + "/" + loadedBalance.getYear();
	}

	/**
	 * @return the subBalanceList
	 */
	public List<BalanceData> getSubBalanceList() {
		if (selectedItem != null) {
			return Global.subBalanceListRetrive(selectedItem.getId());
		}
		return new ArrayList<BalanceData>();
	}

	/**
	 * Construir la lista de balances suceptibles de ser enlazados
	 * @return
	 */
	public List<Balance> getLinkBalanceList() {
		final List<Balance> linkBalanceList = new ArrayList<Balance>();
		if (selectedItem != null) {
			// Construir la lista de posibles subBalances excluyendo el seleccionado y a los
			// parents
			for (final BalanceData i : GlobalBalance.instance().list()) {
				// Se descartan los balances distintos al seleccionado y los que son parent
				if (!i.equals(selectedItem) && !i.hasSubBalance()) {
					// Si aún no están relacionados con un parent, se agregan directamente..
					if (i.getParent() == null) {
						linkBalanceList.add(i.getEntity());
					}
					else {
						// .. de lo contrario, agregar sólo si el parent es distinto al seleccionado
						if (i.getParent().getId().compareTo(selectedItem.getId()) != 0) {
							linkBalanceList.add(i.getEntity());
						}
					}
				}
			}
		}
		return linkBalanceList;
	}

	/**
	 * Guardar los últimos cambios del balance cargado
	 */
	public void saveBalances() {
		if (loadedBalance != null) {
			logger.info("Actualizando balance " + loadedBalance.getName() + " en base de datos");
			Balance balance = loadedBalance.getEntity();
			balance.setLastUpdate(new Date());
			balance = balanceService.update(balance);

			// Si es un sub balance
			if (balance.getParent() != null) {
				// Totalizadores
				BigDecimal totalBudget = BigDecimal.ZERO;
				BigDecimal totalExpenses = BigDecimal.ZERO;

				// Obtener los budgets del balance parent
				final List<Budget> parentBudgets = budgetService.getBudgetsByIdBalance(balance.getParent().getId());
				// Recorrer los budgets para actualizar el budget relacionado
				for (final Budget b : parentBudgets) {
					if (b.getIdSubBalance() != null && balance.getId().compareTo(b.getIdSubBalance()) == 0) {
						// Actualizar el budget
						b.setAmount(loadedBalance.getTotalBudget());
						budgetService.update(b);
						// Obtener los gastos
						final List<Expenses> expenses = expensesService.getExpensesByIdBudget(b.getId());
						// Ya que pertenece a un subbalance, sólo existe un gasto
						final Expenses e = expenses.get(0);
						e.setAmount(loadedBalance.getTotalExpenses());
						expensesService.update(e);
					}
					else {
						totalBudget = totalBudget.add(b.getAmount());
						// Obtener los gastos
						final List<Expenses> expensesList = expensesService.getExpensesByIdBudget(b.getId());
						for (final Expenses e : expensesList) {
							totalExpenses = totalExpenses.add(e.getAmount());
						}
					}
				}
				// Totalizar
				totalBudget = totalBudget.add(loadedBalance.getTotalBudget());
				totalExpenses = totalExpenses.add(loadedBalance.getTotalExpenses());
				// Actualizar el balance padre
				final BalanceData parentBalance = GlobalBalance.instance().retrive(balance.getParent().getId());
				parentBalance.setTotalBudget(totalBudget);
				parentBalance.setTotalExpenses(totalExpenses);
				parentBalance.calculateBalance();
				// Actualizar el mapa
				GlobalBalance.instance().put(parentBalance);
				logger.info("Se ha actualizado el balance padre correctamente");
			}
		}
	}

	/**
	 * 
	 */
	public void updateBooks() {
		Balance balance = loadedBalance.getEntity();
		// Recuperar los books a los que pertenece el balance
		List<Book> bookList = bookService.getBooksByIdBalance(balance.getId());
		// Si existen books..
		if (bookList != null && !bookList.isEmpty()) {
			// ..actualizarlos
			for (Book book : bookList) {
				bookController.updateBookItem(GlobalBook.instance().retrive(book.getId()));
			}
		}
	}
}
