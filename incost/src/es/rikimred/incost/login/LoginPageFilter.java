package es.rikimred.incost.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prevenir que el usuario vaya 'atrás' hacía la página de Login, si el usuario ya ha iniciado
 * sesión
 * @author jrneyra
 */
public class LoginPageFilter implements Filter {
	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		// Si el usuario ya se ha autenticado
		if (request.getUserPrincipal() != null) {
			String navigateString = "";
			if (request.isUserInRole("Administrator")) {
				navigateString = "/admin/AdminHome.xhtml";
			}
			else if (request.isUserInRole("User")) {
				navigateString = "/user/balance.xhtml";
			}
			response.sendRedirect(request.getContextPath() + navigateString);
		}
		else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}
}