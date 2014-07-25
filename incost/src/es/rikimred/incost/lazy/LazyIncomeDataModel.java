/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.global.GlobalIncome;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.IncomeData;

/**
 * Modelo de datos de Income
 * @author jrneyra
 */
public class LazyIncomeDataModel extends GenericLazyDataModel<IncomeData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param balance
	 * @param crudService
	 */
	public LazyIncomeDataModel() {
	}

	@Override
	public List<IncomeData> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, String> filters) {
		setRowCount(GlobalIncome.instance().size());
		return GlobalIncome.instance().list(first, first + pageSize);
	}
}
