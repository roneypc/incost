/**
 * 
 */
package es.rikimred.incost.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.rikimred.incost.entity.User;

/**
 * 
 * @author roberto
 */
public abstract class DataAccessService<T> {

	@PersistenceContext
	private EntityManager em;

	private final Class<T> type;

	// public DataAccessService() {
	// }

	/**
	 * Default constructor
	 * 
	 * @param type entity class
	 */
	public DataAccessService(final Class<T> type) {
		this.type = type;
	}

	/**
	 * Stores an instance of the entity class in the database
	 * @param T Object
	 * @return
	 */
	public T create(final T t) {
		this.em.persist(t);
		this.em.flush();
		this.em.refresh(t);
		return t;
	}

	/**
	 * Retrieves an entity instance that was previously persisted to the database
	 * @param T Object
	 * @param id
	 * @return
	 */
	public T find(final Object id) {
		return this.em.find(this.type, id);
	}

	/**
	 * Removes the record that is associated with the entity instance
	 * @param type
	 * @param id
	 */
	public void delete(final Object id) {
		final Object ref = this.em.getReference(this.type, id);
		this.em.remove(ref);
	}

	/**
	 * Removes the number of entries from a table
	 * @param <T>
	 * @param items
	 * @return
	 */
	public boolean deleteItems(final T[] items) {
		for (final T item : items) {
			if (item instanceof User) {
				final User user = (User) item;
				if (user.getId() == 1) {
					continue;
				}
			}
			em.remove(em.merge(item));
		}
		return true;
	}

	/**
	 * Updates the entity instance
	 * @param <T>
	 * @param t
	 * @return the object that is updated
	 */
	public T update(final T item) {
		if (item instanceof User) {
			final User user = (User) item;
			if (user.getId() == 1) {
				return item;
			}
		}
		return this.em.merge(item);
	}

	/**
	 * Returns the number of records that meet the criteria
	 * @param namedQueryName
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findWithNamedQuery(final String namedQueryName) {
		return this.em.createNamedQuery(namedQueryName).getResultList();
	}

	/**
	 * Returns the number of records that meet the criteria
	 * @param namedQueryName
	 * @param parameters
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findWithNamedQuery(final String namedQueryName, final Map parameters) {
		return findWithNamedQuery(namedQueryName, parameters, 0);
	}

	/**
	 * Returns the number of records with result limit
	 * @param queryName
	 * @param resultLimit
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findWithNamedQuery(final String queryName, final int resultLimit) {
		return this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
	}

	/**
	 * Returns the number of records that meet the criteria
	 * @param <T>
	 * @param sql
	 * @param type
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByNativeQuery(final String sql) {
		return this.em.createNativeQuery(sql, type).getResultList();
	}

	/**
	 * Returns the number of total records
	 * @param namedQueryName
	 * @return int
	 */
	public int countTotalRecord(final String namedQueryName) {
		final Query query = em.createNamedQuery(namedQueryName);
		final Number result = (Number) query.getSingleResult();
		return result.intValue();
	}

	/**
	 * Returns the number of total records
	 * @param namedQueryName
	 * @return int
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int countTotalRecord(final String namedQueryName, final Map parameters) {
		final Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
		final Query query = this.em.createNamedQuery(namedQueryName);

		for (final Map.Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		final Number result = (Number) query.getSingleResult();
		return result.intValue();
	}

	/**
	 * Returns the number of records that meet the criteria with parameter map and result limit
	 * @param namedQueryName
	 * @param parameters
	 * @param resultLimit
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findWithNamedQuery(final String namedQueryName, final Map parameters,
			final int resultLimit) {
		final Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
		final Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0) {
			query.setMaxResults(resultLimit);
		}
		for (final Map.Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	/**
	 * Returns the number of records that will be used with lazy loading / pagination
	 * @param namedQueryName
	 * @param start
	 * @param end
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findWithNamedQuery(final String namedQueryName, final int start, final int end) {
		final Query query = this.em.createNamedQuery(namedQueryName);
		query.setMaxResults(end - start);
		query.setFirstResult(start);
		return query.getResultList();
	}

	/**
	 * 
	 * @param namedQueryName
	 * @param parameters
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findWithNamedQuery(final String namedQueryName, final Map parameters,
			final int start, final int end) {
		final Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
		final Query query = this.em.createNamedQuery(namedQueryName);

		for (final Map.Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setMaxResults(end - start);
		query.setFirstResult(start);
		return query.getResultList();
	}

	/**
	 * 
	 * @return
	 */
	protected EntityManager getEntityManager() {
		return this.em;
	}
}
