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
import es.rikimred.incost.entity.Book;
import es.rikimred.incost.entity.User;

/**
 * Session Bean implementation class BalanceService
 */
@Named
@Dependent
@Stateless
public class BalanceService extends DataAccessService<Balance> {

	/**
	 * 
	 */
	public BalanceService() {
		super(Balance.class);
	}

	public List<Balance> getBalancesByIdUser(final Integer idUser) {
		return getBalancesByIdUser(idUser, -1, -1);
	}

	public List<Balance> getBalancesByIdBook(final Integer idBook) {
		return getBalancesByIdBook(idBook, -1, -1);
	}

	/**
	 * 
	 * @param idUser
	 * @return
	 */
	public List<Balance> getBalancesByIdUser(final Integer idUser, final int start, final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Balance> cq = cb.createQuery(Balance.class);
		final Root<User> root = cq.from(User.class);

		cq.where(cb.equal(root.get("id"), idUser));
		final Join<User, Balance> answers = root.join("balances");

		final CriteriaQuery<Balance> cqp = cq.select(answers);

		final TypedQuery<Balance> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Balance> result = query.getResultList();
		return result;
	}

	public List<Balance> getSubBalances(final Integer idParent) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Balance> cq = cb.createQuery(Balance.class);
		final Root<Balance> root = cq.from(Balance.class);

		cq.where(cb.equal(root.get("id"), idParent));
		final Join<Balance, Balance> answers = root.join("subBalances");

		final CriteriaQuery<Balance> cqp = cq.select(answers);
		final TypedQuery<Balance> query = getEntityManager().createQuery(cqp);

		final List<Balance> result = query.getResultList();
		return result;
	}

	/**
	 * 
	 * @param idBook
	 * @return
	 */
	public List<Balance> getBalancesByIdBook(final Integer idBook, final int start, final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Balance> cq = cb.createQuery(Balance.class);
		final Root<Book> root = cq.from(Book.class);

		cq.where(cb.equal(root.get("id"), idBook));
		final Join<Book, Balance> answers = root.join("balances");

		final CriteriaQuery<Balance> cqp = cq.select(answers);

		final TypedQuery<Balance> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Balance> result = query.getResultList();
		return result;
	}
}
