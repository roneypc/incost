/**
 * 
 */
package es.rikimred.incost.global.domain;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.model.domain.GenericData;
import es.rikimred.incost.util.SessionUtils;

/**
 * Clase genérica para el manejo de los mapas de cada modelo
 * @author jrneyra
 */
public abstract class GenericGlobal<E extends GenericData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Class<E> dataClass;

	private final SessionKeys key;

	@SuppressWarnings("unchecked")
	public GenericGlobal(SessionKeys key) {
		this.dataClass =
				(Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.key = key;
	}

	/**
	 * Eliminar todos los data
	 */
	public void clear() {
		final Map<Integer, E> map = getMap();
		map.clear();
		SessionUtils.getSessionMap().put(key.Key(), map);
	}

	/**
	 * Agregar un data
	 * @param id
	 * @param data
	 */
	public void put(final E data) {
		final Map<Integer, E> map = getMap();
		map.put(data.getId(), data);
		SessionUtils.getSessionMap().put(key.Key(), map);
	}

	/**
	 * Recuperar un data
	 * @param id
	 * @return
	 */
	public E retrive(final Integer id) {
		final Map<Integer, E> map = getMap();
		if (map.containsKey(id)) {
			return map.get(id);
		}
		return null;
	}

	/**
	 * Remover un data
	 * @param data
	 */
	public void remove(final Integer id) {
		final Map<Integer, E> map = getMap();
		map.remove(id);
		SessionUtils.getSessionMap().put(key.Key(), map);
	}

	/**
	 * Total
	 * @return
	 */
	public int size() {
		return list().size();
	}

	/**
	 * Lista de datos
	 * @return
	 */
	public List<E> list() {
		return new ArrayList<E>(getMap().values());
	}

	/**
	 * Lista paginada
	 * @param start
	 * @param end
	 * @return
	 */
	public List<E> list(final int start, final int end) {
		return list().subList(start, Math.min(end, size()));
	}

	/**
	 * Obtener el mapa
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<Integer, E> getMap() {
		if (!SessionUtils.getSessionMap().containsKey(key.Key())) {
			SessionUtils.getSessionMap().put(key.Key(), new HashMap<Integer, E>());
		}
		return (Map<Integer, E>) SessionUtils.getSessionMap().get(key.Key());
	}

	/**
	 * Gets the data class.
	 * 
	 * @return the data class
	 */
	protected Class<E> getDataClass() {
		return dataClass;
	}
}
