package es.rikimred.incost.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import es.rikimred.incost.entity.User;

/**
 * Session Bean implementation class UserService
 */
@Named
@Dependent
@Stateless
public class UserService extends DataAccessService<User> {

	public UserService() {
		super(User.class);
	}

	/**
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	public User findUserByLoginAndPassword(final String login, final String password) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> cq = cb.createQuery(User.class);
		final Root<User> root = cq.from(User.class);
		cq.select(root);
		cq.where(cb.equal(root.get("login"), login), cb.equal(root.get("password"), password));

		final TypedQuery<User> tq = getEntityManager().createQuery(cq);
		final List<User> result = tq.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}
}
