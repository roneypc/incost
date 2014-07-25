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
 * Session Bean implementation class BookService
 */
@Named
@Dependent
@Stateless
public class BookService extends DataAccessService<Book> {

	public BookService() {
		super(Book.class);
	}

	/**
	 * 
	 * @param idUser
	 * @return
	 */
	public List<Book> getBooksByIdUser(final Integer idUser) {
		return getBooksByIdUser(idUser, -1, -1);
	}

	/**
	 * 
	 * @param idBalance
	 * @return
	 */
	public List<Book> getBooksByIdBalance(final Integer idBalance) {
		return getBooksByIdBalance(idBalance, -1, -1);
	}

	/**
	 * 
	 * @param idUser
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Book> getBooksByIdUser(final Integer idUser, final int start, final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		final Root<User> root = cq.from(User.class);

		cq.where(cb.equal(root.get("id"), idUser));
		final Join<User, Book> answers = root.join("books");

		final CriteriaQuery<Book> cqp = cq.select(answers);
		final TypedQuery<Book> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Book> result = query.getResultList();
		return result;
	}

	/**
	 * 
	 * @param idBalance
	 * @return
	 */
	public List<Book> getBooksByIdBalance(final Integer idBalance, final int start, final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		final Root<Balance> root = cq.from(Balance.class);

		cq.where(cb.equal(root.get("id"), idBalance));
		final Join<Balance, Book> answers = root.join("books");

		final CriteriaQuery<Book> cqp = cq.select(answers);

		final TypedQuery<Book> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Book> result = query.getResultList();
		return result;
	}
}
