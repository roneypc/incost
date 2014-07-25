package es.rikimred.incost.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.entity.FixedData;

/**
 * Session Bean implementation class FixedDataService
 * @author roberto
 */
@Named
@Dependent
@Stateless
public class FixedDataService extends DataAccessService<FixedData> {

	/**
	 * Default constructor.
	 */
	public FixedDataService() {
		super(FixedData.class);
	}

	/**
	 * 
	 * @param idCategory
	 * @return
	 */
	public List<FixedData> getFixedDataByIdCategory(final Integer idCategory) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<FixedData> cq = cb.createQuery(FixedData.class);
		final Root<Category> root = cq.from(Category.class);

		cq.where(cb.equal(root.get("id"), idCategory));
		final Join<Category, FixedData> answers = root.join("fixedData");

		final CriteriaQuery<FixedData> cqp = cq.select(answers);
		final TypedQuery<FixedData> query = getEntityManager().createQuery(cqp);

		final List<FixedData> result = query.getResultList();
		return result;
	}
}
