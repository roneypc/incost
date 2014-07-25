/**
 * 
 */
package es.rikimred.incost.converter;

import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.rikimred.incost.enums.CategoryTypeEnum;

/**
 * @author roberto
 */
@FacesConverter(value = "cTypeConverter", forClass = CategoryTypeEnum.class)
public class CategoryTypeConverter implements Converter {

	private static HashMap<String, CategoryTypeEnum> map = new HashMap<String, CategoryTypeEnum>();

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component,
			final String value) {
		if (value.trim().equals("")) {
			return null;
		}
		else {
			return map.get(value);
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component,
			final Object value) {
		if (value.toString().trim().equals("")) {
			return "";
		}
		else {
			final CategoryTypeEnum cType = (CategoryTypeEnum) value;
			map.put(cType.getName(), cType);
			return cType.getName();
		}
	}

}
