/**
 * 
 */
package es.rikimred.incost.global;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.util.SessionUtils;

/**
 * @author jrneyra
 * 
 */
public class GlobalFixedExpenses extends GenericGlobal<FixedDataModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalFixedExpenses
	 */
	private final static GlobalFixedExpenses instance = new GlobalFixedExpenses();

	/**
	 * Constructor privado
	 */
	private GlobalFixedExpenses() {
		super(SessionKeys.FIXED_EXPENSES_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalFixedExpenses instance() {
		return instance;
	}

	/**
	 * 
	 * @param category
	 */
	public void setFixedExpensesCategory(final Category category) {
		SessionUtils.getSessionMap().put(SessionKeys.FIXED_EXPENSES_CATEGORY.Key(), category);
	}

	/**
	 * 
	 * @return
	 */
	public Category getFixedExpensesCategory() {
		return (Category) SessionUtils.getSessionMap().get(SessionKeys.FIXED_EXPENSES_CATEGORY.Key());
	}
}
