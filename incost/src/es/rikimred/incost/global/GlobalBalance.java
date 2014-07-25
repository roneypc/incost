/**
 * 
 */
package es.rikimred.incost.global;

import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.util.SessionUtils;

/**
 * @author jrneyra
 * 
 */
public class GlobalBalance extends GenericGlobal<BalanceData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalBalance
	 */
	private final static GlobalBalance instance = new GlobalBalance();

	/**
	 * Constructor privado
	 */
	private GlobalBalance() {
		super(SessionKeys.BALANCE_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalBalance instance() {
		return instance;
	}

	/**
	 * Obtiene el balance cargado
	 * @return
	 */
	public BalanceData getLoadedBalance() {
		if (SessionUtils.getSessionMap().containsKey(SessionKeys.BALANCE_LOADED.Key())) {
			final Integer idLoadedBalance =
					(Integer) SessionUtils.getSessionMap().get(SessionKeys.BALANCE_LOADED.Key());
			return retrive(idLoadedBalance);
		}
		return null;
	}

}
