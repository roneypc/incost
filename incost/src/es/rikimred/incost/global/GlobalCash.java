/**
 * 
 */
package es.rikimred.incost.global;

import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.CashData;

/**
 * @author jrneyra
 * 
 */
public class GlobalCash extends GenericGlobal<CashData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalCash
	 */
	private final static GlobalCash instance = new GlobalCash();

	/**
	 * Constructor privado
	 */
	private GlobalCash() {
		super(SessionKeys.CASH_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalCash instance() {
		return instance;
	}
}
