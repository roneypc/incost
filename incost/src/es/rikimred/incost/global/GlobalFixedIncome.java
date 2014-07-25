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
public class GlobalFixedIncome extends GenericGlobal<FixedDataModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalFixedIncome
	 */
	private final static GlobalFixedIncome instance = new GlobalFixedIncome();

	/**
	 * Constructor privado
	 */
	private GlobalFixedIncome() {
		super(SessionKeys.FIXED_INCOME_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalFixedIncome instance() {
		return instance;
	}

	/**
	 * 
	 * @param category
	 */
	public void setFixedIncomeCategory(final Category category) {
		SessionUtils.getSessionMap().put(SessionKeys.FIXED_INCOME_CATEGORY.Key(), category);
	}

	/**
	 * 
	 * @return
	 */
	public Category getFixedIncomeCategory() {
		return (Category) SessionUtils.getSessionMap().get(SessionKeys.FIXED_INCOME_CATEGORY.Key());
	}
}
