/**
 * 
 */
package es.rikimred.incost.global;

import java.util.ArrayList;
import java.util.List;

import es.rikimred.incost.entity.Book;
import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.BookData;

/**
 * @author jrneyra
 * 
 */
public class GlobalBook extends GenericGlobal<BookData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalBook
	 */
	private final static GlobalBook instance = new GlobalBook();

	/**
	 * Constructor privado
	 */
	private GlobalBook() {
		super(SessionKeys.BOOK_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalBook instance() {
		return instance;
	}

	/**
	 * 
	 * @return
	 */
	public List<Book> entityList() {
		final List<Book> entityList = new ArrayList<>();
		for (final BookData c : list()) {
			entityList.add(c.getEntity());
		}
		return entityList;
	}
}
