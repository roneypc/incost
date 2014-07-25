/**
 * 
 */
package es.rikimred.incost.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Category;
import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.BudgetData;
import es.rikimred.incost.util.SessionUtils;

/**
 * @author jrneyra
 * 
 */
public class GlobalBudget extends GenericGlobal<BudgetData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalBudget
	 */
	private final static GlobalBudget instance = new GlobalBudget();

	/**
	 * Constructor privado
	 */
	private GlobalBudget() {
		super(SessionKeys.BUDGET_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalBudget instance() {
		return instance;
	}

	/**
	 * 
	 * @return
	 */
	public List<Budget> entityList() {
		final List<Budget> entityList = new ArrayList<>();
		for (final BudgetData c : list()) {
			entityList.add(c.getEntity());
		}
		return entityList;
	}

	/**
	 * 
	 * @param category
	 */
	public void setBudgetFilteredCategory(final Category category) {
		// Backup del budget map actual
		final HashMap<Integer, BudgetData> budgetMapBackup = new HashMap<Integer, BudgetData>(getMap());
		SessionUtils.getSessionMap().put(SessionKeys.BUDGET_MAP_BACKUP.Key(), budgetMapBackup);
		// Limpieza del budget map
		clear();
		// recorrer el budget map backup para construir el filtrado
		for (final BudgetData i : budgetMapBackup.values()) {
			if (category.equals(i.getCategory())) {
				put(i);
			}
		}
	}

	/**
	 * 
	 * @param filter
	 */
	@SuppressWarnings("unchecked")
	public void removeBudgetFilter() {
		if (SessionUtils.getSessionMap().containsKey(SessionKeys.BUDGET_MAP_BACKUP.Key())) {
			// Limpieza del budget map actual
			clear();
			// Recuperación del budget map backup
			final HashMap<Integer, BudgetData> budgetMapBackup =
					(HashMap<Integer, BudgetData>) SessionUtils.getSessionMap()
							.get(SessionKeys.BUDGET_MAP_BACKUP.Key());
			// Reconstrucción del budget map
			final HashMap<Integer, BudgetData> budgetMap = new HashMap<Integer, BudgetData>(budgetMapBackup);
			// Restauración del budget map
			SessionUtils.getSessionMap().put(SessionKeys.BUDGET_MAP.Key(), budgetMap);
		}
	}
}
