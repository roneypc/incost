package es.rikimred.incost.util;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.rikimred.incost.enums.SessionAttributesEnum;

/**
 * Utilidades de sesion
 * @author roberto
 */
public class SessionUtils {

	/**
	 * Retorna la sesion del request
	 * @return
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * Retorna el request del contexto
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	/**
	 * Retorna el mapa de la sesion del contexto
	 * @return
	 */
	public static Map<String, Object> getSessionMap() {
		return getExternalContext().getSessionMap();
	}

	/**
	 * Retorna el contexto externo
	 * @return
	 */
	public static ExternalContext getExternalContext() {
		return getContext().getExternalContext();
	}

	/**
	 * Retorna el contexto
	 */
	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Retorna el nombre de usuario que ha iniciado sesion
	 * @return
	 */
	public static String getUserName() {
		final HttpSession session = getSession();
		return session.getAttribute(SessionAttributesEnum.USERNAME.getName()).toString();
	}

	/**
	 * Retorna el id del usuario que ha iniciado sesion
	 * @return
	 */
	public static Integer getUserId() {
		final HttpSession session = getSession();
		if (session != null) {
			final String userId =
					(String) session.getAttribute(SessionAttributesEnum.USERID.getName());
			return Integer.valueOf(userId);
		}
		else {
			return null;
		}
	}

	/**
	 * Retorna el id del balance que se ha cargado en la sesion
	 * @return
	 */
	public static Integer getLoadedBalanceId() {
		final HttpSession session = getSession();
		if (session != null) {
			final String userId =
					(String) session.getAttribute(SessionAttributesEnum.BALANCEID.getName());
			return Integer.valueOf(userId);
		}
		else {
			return null;
		}
	}
}