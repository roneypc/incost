/**
 * 
 */
package es.rikimred.incost.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.rikimred.incost.entity.Book;
import es.rikimred.incost.service.BookService;

/**
 * 
 * @author roberto
 */
@FacesConverter(value = "bookConverter", forClass = Book.class)
public class BookConverter implements Converter {

	@Inject
	private BookService bookService;

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		final int id = Integer.parseInt(value);
		final Book entity = bookService.find(id);
		return entity;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value.toString().equals("")) {
			return "";
		}
		return value instanceof Book ? ((Book) value).getId().toString() : "";
	}
}
