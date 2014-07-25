/**
 * 
 */
package es.rikimred.incost.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.entity.User;
import es.rikimred.incost.enums.CategoryTypeEnum;

/**
 * @author roberto
 */
@Named
@Dependent
@Stateless
public class CategoryService extends DataAccessService<Category> {

	public CategoryService() {
		super(Category.class);
	}

	//
	// public Category newCategory() {
	// return new Category();
	// }

	/**
	 * 
	 * @param idUser
	 * @return
	 */
	public List<Category> getCategoriesByIdUser(final Integer idUser) {
		return getCategoriesByIdUser(idUser, -1, -1);
	}

	/**
	 * 
	 * @param idUser
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Category>
			getCategoriesByIdUser(final Integer idUser, final int start, final int end) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		final Root<User> root = cq.from(User.class);

		cq.where(cb.equal(root.get("id"), idUser));
		final Join<User, Category> answers = root.join("categories");

		final CriteriaQuery<Category> cqp = cq.select(answers);
		final TypedQuery<Category> query = getEntityManager().createQuery(cqp);

		if (start > -1 && end > -1) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);
		}

		final List<Category> result = query.getResultList();
		return result;
	}

	/**
	 * Categorías del tipo INCOME del usuario
	 * @param idUser
	 * @return
	 */
	public Category getIncomeCategoryByIdUser(final Integer idUser) {
		final CategoryCriteria criteria = new CategoryCriteria();
		criteria.setIdUser(idUser);
		criteria.setCategoryType(CategoryTypeEnum.CATEGORY_INCOMES);
		final List<Category> categories = getCategoriesByCriteria(criteria);
		return categories.get(0);
	}

	/**
	 * Categorías del tipo EXPENSES del usuario
	 * @param idUser
	 * @return
	 */
	public List<Category> getExpensesCategoryByIdUser(final Integer idUser) {
		final CategoryCriteria criteria = new CategoryCriteria();
		criteria.setIdUser(idUser);
		criteria.setCategoryType(CategoryTypeEnum.CATEGORY_EXPENSES);
		return getCategoriesByCriteria(criteria);
	}

	/**
	 * Categorías del tipo SUB.BALANCE del usuario
	 * @param idUser
	 * @return
	 */
	public Category getSubBalanceCategoryByIdUser(final Integer idUser) {
		final CategoryCriteria criteria = new CategoryCriteria();
		criteria.setIdUser(idUser);
		criteria.setCategoryType(CategoryTypeEnum.CATEGORY_SUB_BALANCE);
		final List<Category> categories = getCategoriesByCriteria(criteria);
		return categories.get(0);
	}

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	private List<Category> getCategoriesByCriteria(final CategoryCriteria criteria) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		final Root<Category> root = cq.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();

		// La selección del usuario es obligatoria
		if (criteria.getIdUser() != null) {
			final Join<Category, User> userJoin = root.join("user");
			predicates.add(cb.equal(userJoin.get("id"), criteria.getIdUser()));

			// Agregar la condición 'categoryType'
			if (criteria.getCategoryType() != null) {
				predicates.add(cb.equal(root.get("categoryType"), criteria.getCategoryType()));
			}

			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			try {
				final TypedQuery<Category> tq = getEntityManager().createQuery(cq);
				final List<Category> result = tq.getResultList();
				return result;
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Criterios para consulta de categorías
	 * @author roberto
	 */
	private class CategoryCriteria {

		private Integer idUser;

		private CategoryTypeEnum categoryType;

		/**
		 * @return the idUser
		 */
		public Integer getIdUser() {
			return idUser;
		}

		/**
		 * @param idUser the idUser to set
		 */
		public void setIdUser(final Integer idUser) {
			this.idUser = idUser;
		}

		/**
		 * @return the categoryType
		 */
		public CategoryTypeEnum getCategoryType() {
			return categoryType;
		}

		/**
		 * @param categoryType the categoryType to set
		 */
		public void setCategoryType(final CategoryTypeEnum categoryType) {
			this.categoryType = categoryType;
		}
	}
}
