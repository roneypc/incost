/**
 * 
 */
package es.rikimred.incost.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.rikimred.incost.entity.FixedData;
import es.rikimred.incost.service.FixedDataService;

/**
 * 
 * @author roberto
 */
@FacesConverter(value = "fixedDataConverter", forClass = FixedData.class)
public class FixedDataConverter implements Converter {

	@Inject
	private FixedDataService fixedDataService;

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component,
			final String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		final int id = Integer.parseInt(value);
		return fixedDataService.find(id);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component,
			final Object value) {
		if (value.toString().equals("")) {
			return "";
		}
		return value instanceof FixedData ? ((FixedData) value).getId().toString() : "";
	}
}
