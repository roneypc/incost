/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import es.rikimred.incost.service.UserService;

/**
 * @author roberto
 * 
 */
@Named
@SessionScoped
public class UserController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private UserService userService;

	private String label;

	/**
	 * 
	 */
	public UserController() {
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	// public String send() {
	// System.out.println("Enviando USER CONTROLLER");
	// userService.newUser();
	// return "/login.xhtml";
	// }

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

}
