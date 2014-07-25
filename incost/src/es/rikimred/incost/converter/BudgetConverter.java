/**
 * 
 */
package es.rikimred.incost.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.rikimred.incost.entity.Budget;
import es.rikimred.incost.service.BudgetService;

/**
 * 
 * @author roberto
 */
@FacesConverter(value = "budgetConverter", forClass = Budget.class)
public class BudgetConverter implements Converter {

	@Inject
	private BudgetService budgetService;

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component,
			final String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		final int id = Integer.parseInt(value);
		final Budget entity = budgetService.find(id);
		return entity;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component,
			final Object value) {
		if (value.toString().equals("")) {
			return "";
		}
		return value instanceof Budget ? ((Budget) value).getId().toString() : "";
	}
}
