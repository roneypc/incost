/**
 * 
 */
package es.rikimred.incost.global;

import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.FixedDataModel;

/**
 * @author jrneyra
 * 
 */
public class GlobalFixedBudget extends GenericGlobal<FixedDataModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalFixedBudget
	 */
	private final static GlobalFixedBudget instance = new GlobalFixedBudget();

	/**
	 * Constructor privado
	 */
	private GlobalFixedBudget() {
		super(SessionKeys.FIXED_BUDGET_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalFixedBudget instance() {
		return instance;
	}
}
