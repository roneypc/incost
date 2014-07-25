/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.global.GlobalCategory;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.CategoryData;

/**
 * 
 * @author roberto
 */
public class LazyCategoryDataModel extends GenericLazyDataModel<CategoryData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LazyCategoryDataModel() {
	}

	@Override
	public List<CategoryData> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, String> filters) {
		setRowCount(GlobalCategory.instance().size());
		return GlobalCategory.instance().list(first, first + pageSize);
	}
}
