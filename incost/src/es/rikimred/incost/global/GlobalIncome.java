/**
 * 
 */
package es.rikimred.incost.global;

import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.IncomeData;

/**
 * @author jrneyra
 * 
 */
public class GlobalIncome extends GenericGlobal<IncomeData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalIncome
	 */
	private final static GlobalIncome instance = new GlobalIncome();

	/**
	 * Constructor privado
	 */
	private GlobalIncome() {
		super(SessionKeys.INCOME_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalIncome instance() {
		return instance;
	}
}
