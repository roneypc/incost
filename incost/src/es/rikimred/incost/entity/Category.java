package es.rikimred.incost.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import es.rikimred.incost.entity.domain.GenericEntity;
import es.rikimred.incost.enums.CategoryTypeEnum;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
// @NamedQueries({ @NamedQuery(name = "Category.totalByIdUser",
// query = "SELECT COUNT(c) FROM User u JOIN u.categories c WHERE u.id = :idUser") })
public class Category extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public static final String TOTALbyIDUSER = "Category.totalByIdUser";

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "ID_USER")
	private User user;

	private String name;

	private String description;

	// bi-directional many-to-one association to Budget
	@OneToMany(mappedBy = "category")
	private List<Budget> budgets;

	// bi-directional many-to-one association to FixedData
	@OneToMany(mappedBy = "category")
	private List<FixedData> fixedData;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY_TYPE")
	private CategoryTypeEnum categoryType;

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

	public Category() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<Budget> getBudgets() {
		return this.budgets;
	}

	public void setBudgets(final List<Budget> budgets) {
		this.budgets = budgets;
	}

	public Budget addBudgets(final Budget budgets) {
		getBudgets().add(budgets);
		budgets.setCategory(this);

		return budgets;
	}

	public Budget removeBudgets(final Budget budgets) {
		getBudgets().remove(budgets);
		budgets.setCategory(null);

		return budgets;
	}

	/**
	 * @return the fixedData
	 */
	public List<FixedData> getFixedData() {
		return fixedData;
	}

	/**
	 * @param fixedData the fixedData to set
	 */
	public void setFixedData(final List<FixedData> fixedData) {
		this.fixedData = fixedData;
	}

	public FixedData addFixedData(final FixedData fixedData) {
		getFixedData().add(fixedData);
		fixedData.setCategory(this);

		return fixedData;
	}

	public FixedData removeFixedData(final FixedData fixedData) {
		getFixedData().remove(fixedData);
		fixedData.setCategory(null);

		return fixedData;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}
}