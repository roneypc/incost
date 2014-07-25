/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.global.GlobalBook;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.BookData;

/**
 * Modelo de datos de Book
 * @author jrneyra
 */
public class LazyBookDataModel extends GenericLazyDataModel<BookData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LazyBookDataModel() {
	}

	@Override
	public List<BookData> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder,
			final Map<String, String> filters) {
		setRowCount(GlobalBook.instance().size());
		return GlobalBook.instance().list(first, first + pageSize);
	}
}
