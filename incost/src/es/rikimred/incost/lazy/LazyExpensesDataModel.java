/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.global.GlobalExpenses;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.ExpensesData;

/**
 * Modelo de datos de expenses
 * @author jrneyra
 */
public class LazyExpensesDataModel extends GenericLazyDataModel<ExpensesData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param balance
	 * @param crudService
	 */
	public LazyExpensesDataModel() {
	}

	@Override
	public List<ExpensesData> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, String> filters) {
		setRowCount(GlobalExpenses.instance().size());
		return GlobalExpenses.instance().list(first, first + pageSize);
	}
}
