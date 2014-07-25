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

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.entity.Expenses;

/**
 * 
 * @author roberto
 */
@Named
@Dependent
@Stateless
public class ExpensesService extends DataAccessService<Expenses> {

	public ExpensesService() {
		super(Expenses.class);
	}

	/**
	 * 
	 * @param idBudget
	 * @return
	 */
	public List<Expenses> getExpensesByIdBudget(final Integer idBudget) {
		return getExpensesByIdBudget(idBudget, -1, -1);
	}

	/**
	 * 
	 * @param idBudget
	 * @return
	 */
	public List<Expenses> getExpensesByIdBudget(final Integer idBudget, final int start,
			final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Expenses> cq = cb.createQuery(Expenses.class);
		final Root<Budget> root = cq.from(Budget.class);

		cq.where(cb.equal(root.get("id"), idBudget));
		final Join<Budget, Expenses> answers = root.join("expenses");

		final CriteriaQuery<Expenses> cqp = cq.select(answers);
		final TypedQuery<Expenses> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Expenses> result = query.getResultList();
		return result;
	}
}
