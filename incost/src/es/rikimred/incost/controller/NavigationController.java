/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import es.rikimred.incost.util.SessionUtils;

/**
 * Controlador de navegacion entre paginas
 * @author jrneyra
 */
@Named
@SessionScoped
public class NavigationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	/**
	 * Pagina de navegacion actual
	 */
	private String pageName = "/login";

	public NavigationController() {
	}

	public void doNav() {
		final String str =
				SessionUtils.getExternalContext().getRequestParameterMap().get("targetPage");
		this.pageName = str;
		try {
			SessionUtils.getExternalContext().redirect(
					SessionUtils.getRequest().getContextPath() + str);
		}
		catch (final IOException e) {
			final String msg = String.format("Error de navegacion redirigendo a [%s]", str);
			logger.error(msg);
			SessionUtils.getContext().addMessage(null, new FacesMessage("Error!", msg));
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * 
	 * @param pageName
	 */
	public void setPageName(final String pageName) {
		this.pageName = pageName;
	}
}
