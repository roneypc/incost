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
import es.rikimred.incost.entity.Income;

/**
 * Session Bean implementation class IncomeService
 */
@Named
@Dependent
@Stateless
public class IncomeService extends DataAccessService<Income> {

	/**
	 * Default constructor.
	 */
	public IncomeService() {
		super(Income.class);
	}

	public List<Income> getIncomesByIdBalance(final Integer idBalance) {
		return getIncomesByIdBalance(idBalance, -1, -1);
	}

	/**
	 * 
	 * @param idBalance
	 * @return
	 */
	public List<Income> getIncomesByIdBalance(final Integer idBalance, final int start,
			final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Income> cq = cb.createQuery(Income.class);
		final Root<Balance> root = cq.from(Balance.class);

		cq.where(cb.equal(root.get("id"), idBalance));
		final Join<Balance, Income> answers = root.join("incomes");

		final CriteriaQuery<Income> cqp = cq.select(answers);
		final TypedQuery<Income> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Income> result = query.getResultList();
		return result;
	}
}
