/**
 * 
 */
package es.rikimred.incost.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.ExpensesData;
import es.rikimred.incost.util.SessionUtils;

/**
 * @author jrneyra
 * 
 */
public class GlobalExpenses extends GenericGlobal<ExpensesData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalExpenses
	 */
	private final static GlobalExpenses instance = new GlobalExpenses();

	/**
	 * Constructor privado
	 */
	private GlobalExpenses() {
		super(SessionKeys.EXPENSES_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalExpenses instance() {
		return instance;
	}

	/**
	 * 
	 * @param filter
	 */
	@SuppressWarnings("unchecked")
	public void removeExpensesFilter() {
		if (SessionUtils.getSessionMap().containsKey(SessionKeys.EXPENSES_MAP_BACKUP.Key())) {
			// Limpieza del expenses map actual
			clear();
			// Recuperación del expenses map backup
			final HashMap<Integer, ExpensesData> expensesMapBackup =
					(HashMap<Integer, ExpensesData>) SessionUtils.getSessionMap().get(
							SessionKeys.EXPENSES_MAP_BACKUP.Key());
			// Reconstrucción del expenses map
			final HashMap<Integer, ExpensesData> expensesMap = new HashMap<Integer, ExpensesData>(expensesMapBackup);
			// Restauración del expenses map
			SessionUtils.getSessionMap().put(SessionKeys.EXPENSES_MAP.Key(), expensesMap);
		}
	}

	/**
	 * 
	 * @param budget
	 */
	public void setExpensesFilteredBudget(final Budget budget) {
		// Backup del expenses map actual
		final HashMap<Integer, ExpensesData> expensesMapBackup = new HashMap<Integer, ExpensesData>(getMap());
		SessionUtils.getSessionMap().put(SessionKeys.EXPENSES_MAP_BACKUP.Key(), expensesMapBackup);
		// Limpieza del expenses map
		clear();
		// recorrer el expenses map backup para construir el filtrado
		for (final ExpensesData i : expensesMapBackup.values()) {
			if (budget.equals(i.getBudget())) {
				put(i);
			}
		}
	}

	/**
	 * Lista de expenses pertenecientes al budget
	 * @param idBudget
	 * @return
	 */
	public List<ExpensesData> expensesByBudget(final Integer idBudget) {
		final List<ExpensesData> values = new ArrayList<ExpensesData>();
		for (final ExpensesData e : list()) {
			if (e.getBudget().getId().compareTo(idBudget) == 0) {
				values.add(e);
			}
		}
		return values;
	}
}
