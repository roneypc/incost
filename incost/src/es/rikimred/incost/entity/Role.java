/**
 * 
 */
package es.rikimred.incost.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * @author roberto
 * 
 */
@Entity
public class Role extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roledesc;

	private String rolename;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public Role() {
	}

	public Role(final Integer roleid, final String rolename) {
		this.rolename = rolename;
	}

	/**
	 * @return the roledesc
	 */
	public String getRoledesc() {
		return roledesc;
	}

	/**
	 * @param roledesc the roledesc to set
	 */
	public void setRoledesc(final String roledesc) {
		this.roledesc = roledesc;
	}

	/**
	 * @return the rolename
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * @param rolename the rolename to set
	 */
	public void setRolename(final String rolename) {
		this.rolename = rolename;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(final List<User> users) {
		this.users = users;
	}
}
