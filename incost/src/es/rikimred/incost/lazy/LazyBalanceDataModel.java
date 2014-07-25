/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.entity.Balance;
import es.rikimred.incost.entity.Book;
import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Cash;
import es.rikimred.incost.entity.Expenses;
import es.rikimred.incost.entity.Income;
import es.rikimred.incost.global.Global;
import es.rikimred.incost.global.GlobalBalance;
import es.rikimred.incost.global.GlobalBook;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.BookData;
import es.rikimred.incost.service.BalanceService;
import es.rikimred.incost.service.BookService;
import es.rikimred.incost.service.BudgetService;
import es.rikimred.incost.service.CashService;
import es.rikimred.incost.service.ExpensesService;
import es.rikimred.incost.service.IncomeService;

/**
 * Modelo de datos de Balance
 * @author roberto
 */
public class LazyBalanceDataModel extends GenericLazyDataModel<BalanceData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Referencia a bookService
	private final BookService bookService;

	// Referencia a incomeService
	private final IncomeService incomeService;

	// Referencia a cashService
	private final CashService cashService;

	// Referencia a balanceService
	private final BalanceService balanceService;

	// Referencia a budgetService
	private final BudgetService budgetService;

	// Referencia a expensesService
	private final ExpensesService expensesService;

	/**
	 * @param crudService
	 */
	public LazyBalanceDataModel(final BalanceService balanceService, final BookService bookService,
			final IncomeService incomeService, CashService cashService, final BudgetService budgetService,
			final ExpensesService expensesService) {
		this.balanceService = balanceService;
		this.bookService = bookService;
		this.incomeService = incomeService;
		this.cashService = cashService;
		this.budgetService = budgetService;
		this.expensesService = expensesService;
	}

	@Override
	public List<BalanceData> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, String> filters) {
		// Si es la primera vez que se cargan los balances
		if (Global.isFirstBalanceLoad()) {
			final List<Balance> entityList = balanceService.getBalancesByIdUser(Global.activeUser().getId());

			List<Income> incomeList;
			List<Budget> budgetList;
			List<Expenses> expensesList;

			// Totalizar los ingresos
			BigDecimal totalIncome;
			// Totalizar el efectivo
			BigDecimal totalCash;
			// Totalizar los presupuestos
			BigDecimal totalBudget;
			// Totalizar los gastos
			BigDecimal totalExpenses;

			// Limpiar el mapa de balances
			GlobalBalance.instance().clear();
			// Limpiar el mapa de sub.balances
			Global.subBalanceClearAll();
			BalanceData data;
			for (final Balance entity : entityList) {
				data = new BalanceData(entity);
				// Agregar los subBalances al mapa
				if (entity.getParent() != null) {
					final Integer idParent = entity.getParent().getId();
					Global.subBalancePut(idParent, data);
				}

				// Recuperar los ingresos
				incomeList = incomeService.getIncomesByIdBalance(entity.getId());
				totalIncome = BigDecimal.ZERO;
				for (final Income i : incomeList) {
					totalIncome = totalIncome.add(i.getAmount());
				}
				data.setTotalIncome(totalIncome);

				// Recuperar los cash
				final List<Cash> cashList = cashService.getCashListByIdBalance(entity.getId());
				totalCash = BigDecimal.ZERO;
				for (final Cash cash : cashList) {
					totalCash = totalCash.add(cash.getAmount());
				}
				data.setTotalCash(totalCash);

				// Recuperar los presupuestos
				budgetList = budgetService.getBudgetsByIdBalance(entity.getId());
				totalBudget = BigDecimal.ZERO;
				totalExpenses = BigDecimal.ZERO;
				for (final Budget budget : budgetList) {
					totalBudget = totalBudget.add(budget.getAmount());
					expensesList = expensesService.getExpensesByIdBudget(budget.getId());
					budget.setExpenses(expensesList);
					for (final Expenses e : expensesList) {
						totalExpenses = totalExpenses.add(e.getAmount());
					}
				}
				data.setTotalBudget(totalBudget);
				data.setTotalExpenses(totalExpenses);
				data.calculateBalance();

				//
				GlobalBalance.instance().put(data);
			}

			// Carga de books
			List<Book> bookList = bookService.getBooksByIdUser(Global.activeUser().getId());
			// Limpiar el mapa de books
			GlobalBook.instance().clear();
			// Limpiar el mapa de book balances
			Global.bookBalanceClearAll();

			BigDecimal bookBalance;
			BalanceData balanceData;
			List<Balance> balanceList;
			// Construir el mapa de books
			for (final Book book : bookList) {
				totalIncome = BigDecimal.ZERO;
				totalBudget = BigDecimal.ZERO;
				totalExpenses = BigDecimal.ZERO;
				bookBalance = BigDecimal.ZERO;

				// Contruir la lista de book balances
				balanceList = balanceService.getBalancesByIdBook(book.getId());

				for (Balance balanceEntity : balanceList) {
					balanceData = GlobalBalance.instance().retrive(balanceEntity.getId());
					totalIncome = totalIncome.add(balanceData.getTotalIncome());
					totalBudget = totalBudget.add(balanceData.getTotalBudget());
					totalExpenses = totalExpenses.add(balanceData.getTotalExpenses());
					bookBalance = bookBalance.add(balanceData.getBalanceResult());
					Global.bookBalancePut(book.getId(), balanceData);
				}
				book.setTotalIncome(totalIncome);
				book.setTotalBudget(totalBudget);
				book.setTotalExpenses(totalExpenses);
				book.setBookBalance(bookBalance);

				GlobalBook.instance().put(new BookData(book));
			}

			// No se vuelve a entrar en este bloque
			Global.toggleFirstBalanceLoad();
		}

		setRowCount(GlobalBalance.instance().size());
		return GlobalBalance.instance().list(first, first + pageSize);

	}
}
