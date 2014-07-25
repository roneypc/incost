/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.global.GlobalFixedCash;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.FixedDataModel;

/**
 * Modelo de datos de Fixed Cash
 * @author jrneyra
 */
public class LazyFixedCashDataModel extends GenericLazyDataModel<FixedDataModel> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param balance
	 * @param crudService
	 */
	public LazyFixedCashDataModel() {
	}

	@Override
	public List<FixedDataModel> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, String> filters) {
		setRowCount(GlobalFixedCash.instance().size());
		return GlobalFixedCash.instance().list(first, first + pageSize);
	}
}
