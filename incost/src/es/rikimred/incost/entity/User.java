package es.rikimred.incost.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "User.populateUsers", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.countUsersTotal", query = "SELECT COUNT(u) FROM User u") })
public class User extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String ALL = "User.populateUsers";

	public final static String TOTAL = "User.countUsersTotal";

	private String email;

	private String password;

	private String login;

	@Column(name = "NAME")
	private String name;

	@Column(name = "LAST_NAME")
	private String lastName;

	// bi-directional many-to-many association to Balance
	@ManyToMany
	@JoinTable(name = "user_balance", joinColumns = { @JoinColumn(name = "ID_USER") }, inverseJoinColumns = { @JoinColumn(name = "ID_BALANCE") })
	private List<Balance> balances;

	// bi-directional many-to-one association to Category
	@OneToMany(mappedBy = "user")
	private List<Category> categories;

	// bi-directional many-to-one association to Book
	@OneToMany(mappedBy = "user")
	private List<Book> books;

	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "ID_USER") }, inverseJoinColumns = { @JoinColumn(name = "ID_ROLE") })
	private List<Role> roles;

	public User() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public List<Balance> getBalances() {
		return this.balances;
	}

	public void setBalances(final List<Balance> balances) {
		this.balances = balances;
	}

	/**
	 * @return the categories
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(final List<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(final Category category) {
		getCategories().add(category);
		category.setUser(this);

		return category;
	}

	public Category removeCategory(final Category category) {
		getCategories().remove(category);
		category.setUser(null);

		return category;
	}

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(final List<Book> books) {
		this.books = books;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
}