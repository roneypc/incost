/**
 * 
 */
package es.rikimred.incost.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.rikimred.incost.entity.FixedData;
import es.rikimred.incost.entity.User;
import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.util.SessionUtils;

/**
 * Control de variables globales almacenadas en la sesión
 * @author roberto
 */
public class Global {

	private static Boolean firstBalanceLoad = Boolean.TRUE;

	private static Boolean parentBalanceChanged = Boolean.FALSE;

	// *********************
	// Objetos de la sesion
	// *********************

	/**
	 * Guardar el usuario activo
	 * @param user
	 */
	public static void setActiveUser(final User user) {
		SessionUtils.getSessionMap().put(SessionKeys.USER_ACTIVE.Key(), user);
	}

	/**
	 * Recupera el usuario activo
	 * @return
	 */
	public static User activeUser() {
		return (User) SessionUtils.getSessionMap().get(SessionKeys.USER_ACTIVE.Key());
	}

	/**
	 * 
	 * @param loadedBalance
	 */
	public static void setIdLoadedBalance(final Integer idLoadedBalance) {
		SessionUtils.getSessionMap().put(SessionKeys.BALANCE_LOADED.Key(), idLoadedBalance);
	}

	/**
	 * Invertir el valor de firstBalanceLoad
	 */
	public static void toggleFirstBalanceLoad() {
		firstBalanceLoad = !firstBalanceLoad;
	}

	/**
	 * Consulta el estado de firstBalanceLoad
	 * @return
	 */
	public static Boolean isFirstBalanceLoad() {
		return firstBalanceLoad;
	}

	/**
	 * Set del valor de parentBalanceChanged
	 */
	public static void parentBalanceChanged(final boolean value) {
		parentBalanceChanged = value;
	}

	/**
	 * Consulta del estado de parentBalanceChanged
	 * @return
	 */
	public static Boolean isParentBalanceChanged() {
		return parentBalanceChanged;
	}

	// *********************
	// Mapas de la sesion
	// *********************

	/*
	 * BOOK BALANCE
	 */

	/**
	 * Eliminar todos los book balances
	 */
	public static void bookBalanceClearAll() {
		final Map<Integer, List<BalanceData>> map = getBookBalanceMap();
		map.clear();
		SessionUtils.getSessionMap().put(SessionKeys.BOOK_BALANCE_MAP.Key(), map);
	}

	/**
	 * Agregar un book balance
	 * @param id
	 * @param bookBalance
	 */
	public static void bookBalancePut(final Integer idParent, final BalanceData bookBalance) {
		final Map<Integer, List<BalanceData>> map = getBookBalanceMap();
		// Actualizar mapa
		if (!map.containsKey(idParent)) {
			final List<BalanceData> localSubBalanceList = new ArrayList<BalanceData>();
			map.put(idParent, localSubBalanceList);
		}
		map.get(idParent).add(bookBalance);
		SessionUtils.getSessionMap().put(SessionKeys.BOOK_BALANCE_MAP.Key(), map);
	}

	/**
	 * Recupera un book balance list
	 * @param id
	 * @return
	 */
	public static List<BalanceData> bookBalanceListRetrive(final Integer idParent) {
		final Map<Integer, List<BalanceData>> map = getBookBalanceMap();
		if (map.containsKey(idParent)) {
			return map.get(idParent);
		}
		return new ArrayList<BalanceData>();
	}

	/**
	 * Remover un book balance
	 * @param bookBalance
	 */
	public static void bookBalanceRemove(final Integer idParent, final BalanceData bookBalance) {
		final Map<Integer, List<BalanceData>> map = getBookBalanceMap();
		if (map.containsKey(idParent)) {
			map.get(idParent).remove(bookBalance);
		}
		SessionUtils.getSessionMap().put(SessionKeys.BOOK_BALANCE_MAP.Key(), map);
	}

	/*
	 * SUB BALANCE
	 */

	/**
	 * Eliminar todos los sub balances
	 */
	public static void subBalanceClearAll() {
		final Map<Integer, List<BalanceData>> map = getSubBalanceMap();
		map.clear();
		SessionUtils.getSessionMap().put(SessionKeys.SUB_BALANCE_MAP.Key(), map);
	}

	/**
	 * Agregar un sub balance
	 * @param id
	 * @param subBalance
	 */
	public static void subBalancePut(final Integer idParent, final BalanceData subBalance) {
		final Map<Integer, List<BalanceData>> map = getSubBalanceMap();
		// Actualizar mapa
		if (!map.containsKey(idParent)) {
			final List<BalanceData> localSubBalanceList = new ArrayList<BalanceData>();
			map.put(idParent, localSubBalanceList);
		}
		map.get(idParent).add(subBalance);
		SessionUtils.getSessionMap().put(SessionKeys.SUB_BALANCE_MAP.Key(), map);
	}

	/**
	 * Recupera un sub balance list
	 * @param id
	 * @return
	 */
	public static List<BalanceData> subBalanceListRetrive(final Integer idParent) {
		final Map<Integer, List<BalanceData>> map = getSubBalanceMap();
		if (map.containsKey(idParent)) {
			return map.get(idParent);
		}
		return new ArrayList<BalanceData>();
	}

	/**
	 * Remover un sub balance
	 * @param subBalance
	 */
	public static void subBalanceRemove(final Integer idParent, final BalanceData subBalance) {
		final Map<Integer, List<BalanceData>> map = getSubBalanceMap();
		if (map.containsKey(idParent)) {
			map.get(idParent).remove(subBalance);
		}
		SessionUtils.getSessionMap().put(SessionKeys.SUB_BALANCE_MAP.Key(), map);
	}

	// *********************
	// Obtener los entities de los fixed's
	// *********************

	/**
	 * Lista de fixed's entities
	 * @param cType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<FixedData> fixedDataEntityList(final String key) {
		final List<FixedData> entityList = new ArrayList<>();
		final Map<Integer, FixedDataModel> fixedMap =
				(Map<Integer, FixedDataModel>) SessionUtils.getSessionMap().get(key);
		for (final FixedDataModel f : fixedMap.values()) {
			entityList.add(f.getEntity());
		}
		return entityList;
	}

	// *********************
	// Obtener los MAPAS
	// *********************

	/**
	 * Obtener el mapa de sub balances de la sesión
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<Integer, List<BalanceData>> getSubBalanceMap() {
		if (!SessionUtils.getSessionMap().containsKey(SessionKeys.SUB_BALANCE_MAP.Key())) {
			SessionUtils.getSessionMap().put(SessionKeys.SUB_BALANCE_MAP.Key(),
					new HashMap<Integer, List<BalanceData>>());
		}
		return (Map<Integer, List<BalanceData>>) SessionUtils.getSessionMap().get(SessionKeys.SUB_BALANCE_MAP.Key());
	}

	/**
	 * Obtener el mapa de book balances de la sesión
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<Integer, List<BalanceData>> getBookBalanceMap() {
		if (!SessionUtils.getSessionMap().containsKey(SessionKeys.BOOK_BALANCE_MAP.Key())) {
			SessionUtils.getSessionMap().put(SessionKeys.BOOK_BALANCE_MAP.Key(),
					new HashMap<Integer, List<BalanceData>>());
		}
		return (Map<Integer, List<BalanceData>>) SessionUtils.getSessionMap().get(SessionKeys.BOOK_BALANCE_MAP.Key());
	}

	/**
	 * Eliminar todos los mapas de la sesion
	 */
	public static void terminate() {
		firstBalanceLoad = Boolean.TRUE;
		SessionUtils.getSessionMap().clear();
	}
}
