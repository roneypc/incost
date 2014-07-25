/**
 * 
 */
package es.rikimred.incost.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.service.CategoryService;

/**
 * 
 * @author roberto
 */
@FacesConverter(value = "categoryConverter", forClass = Category.class)
public class CategoryConverter implements Converter {

	@Inject
	private CategoryService categoryService;

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component,
			final String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		final int id = Integer.parseInt(value);
		return categoryService.find(id);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component,
			final Object value) {
		if (value.toString().equals("")) {
			return "";
		}
		return value instanceof Category ? ((Category) value).getId().toString() : "";
	}
}
