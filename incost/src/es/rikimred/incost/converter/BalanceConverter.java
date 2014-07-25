/**
 * 
 */
package es.rikimred.incost.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.rikimred.incost.entity.Balance;
import es.rikimred.incost.service.BalanceService;

/**
 * @author jrneyra
 * 
 */
@FacesConverter(value = "balanceConverter", forClass = Balance.class)
public class BalanceConverter implements Converter {

	@Inject
	private BalanceService balanceService;

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if ((value == null) || (value.length() == 0)) {
			return null;
		}
		final int id = Integer.parseInt(value);
		final Balance entity = balanceService.find(id);
		return entity;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value.toString().equals("")) {
			return "";
		}
		return value instanceof Balance ? ((Balance) value).getId().toString() : "";
	}

}
