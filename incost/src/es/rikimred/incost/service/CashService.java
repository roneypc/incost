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

import es.rikimred.incost.entity.Balance;
import es.rikimred.incost.entity.Cash;

/**
 * Session Bean implementation class CashService
 */
@Named
@Dependent
@Stateless
public class CashService extends DataAccessService<Cash> {

	/**
	 * Default constructor.
	 */
	public CashService() {
		super(Cash.class);
	}

	public List<Cash> getCashListByIdBalance(final Integer idBalance) {
		return getCashListByIdBalance(idBalance, -1, -1);
	}

	/**
	 * 
	 * @param idBalance
	 * @return
	 */
	public List<Cash>
			getCashListByIdBalance(final Integer idBalance, final int start, final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Cash> cq = cb.createQuery(Cash.class);
		final Root<Balance> root = cq.from(Balance.class);

		cq.where(cb.equal(root.get("id"), idBalance));
		final Join<Balance, Cash> answers = root.join("cashList");

		final CriteriaQuery<Cash> cqp = cq.select(answers);
		final TypedQuery<Cash> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Cash> result = query.getResultList();
		return result;
	}
}
