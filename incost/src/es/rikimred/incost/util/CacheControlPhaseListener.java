/**
 * 
 */
package es.rikimred.incost.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jrneyra
 * 
 */
public class CacheControlPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	@Override
	public void afterPhase(final PhaseEvent event) {
	}

	@Override
	public void beforePhase(final PhaseEvent event) {
		final FacesContext facesContext = event.getFacesContext();
		final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		// Stronger according to blog comment below that references HTTP spec
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		// some date in the past
		response.addHeader("Expires", "Sun, 5 Nov 1978 05:30:00 GMT");
	}
}
