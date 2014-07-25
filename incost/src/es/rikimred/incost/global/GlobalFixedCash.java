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
public class GlobalFixedCash extends GenericGlobal<FixedDataModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalFixedCash
	 */
	private final static GlobalFixedCash instance = new GlobalFixedCash();

	/**
	 * Constructor privado
	 */
	private GlobalFixedCash() {
		super(SessionKeys.FIXED_CASH_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalFixedCash instance() {
		return instance;
	}

	/**
	 * 
	 * @param category
	 */
	public void setFixedCashCategory(final Category category) {
		SessionUtils.getSessionMap().put(SessionKeys.FIXED_CASH_CATEGORY.Key(), category);
	}

	/**
	 * 
	 * @return
	 */
	public Category getFixedCashCategory() {
		return (Category) SessionUtils.getSessionMap().get(SessionKeys.FIXED_CASH_CATEGORY.Key());
	}
}
