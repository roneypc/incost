/**
 * 
 */
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
import es.rikimred.incost.entity.Budget;

/**
 * @author jrneyra
 * 
 */
@Named
@Dependent
@Stateless
public class BudgetService extends DataAccessService<Budget> {

	/**
	 * 
	 */
	public BudgetService() {
		super(Budget.class);
	}

	//
	// public Budget newBudget() {
	// return new Budget();
	// }

	/**
	 * 
	 * @param idBalance
	 * @return
	 */
	public List<Budget> getBudgetsByIdBalance(final Integer idBalance) {
		return getBudgetsByIdBalance(idBalance, -1, -1);
	}

	/**
	 * 
	 * @param idBalance
	 * @return
	 */
	public List<Budget> getBudgetsByIdBalance(final Integer idBalance, final int start,
			final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Budget> cq = cb.createQuery(Budget.class);
		final Root<Balance> root = cq.from(Balance.class);

		cq.where(cb.equal(root.get("id"), idBalance));
		final Join<Balance, Budget> answers = root.join("budgets");

		final CriteriaQuery<Budget> cqp = cq.select(answers);
		final TypedQuery<Budget> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Budget> result = query.getResultList();
		return result;
	}
	
	/**
	 * 
	 * @param idSubBalance
	 * @return
	 */
	public Budget getBudgetsByIdSubBalance(final Integer idSubBalance) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Budget> cq = cb.createQuery(Budget.class);
		final Root<Budget> root = cq.from(Budget.class);

		cq.where(cb.equal(root.get("idSubBalance"), idSubBalance));
		final TypedQuery<Budget> query = getEntityManager().createQuery(cq);

		final List<Budget> result = query.getResultList();
		if(result!=null)
			return result.get(0);
		return null;
	}
}
