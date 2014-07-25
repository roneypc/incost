/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.global.GlobalBudget;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.BudgetData;

/**
 * Modelo de datos de budget
 * @author jrneyra
 */
public class LazyBudgetDataModel extends GenericLazyDataModel<BudgetData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param balance
	 * @param crudService
	 */
	public LazyBudgetDataModel() {
	}

	@Override
	public List<BudgetData> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, String> filters) {
		setRowCount(GlobalBudget.instance().size());
		return GlobalBudget.instance().list(first, first + pageSize);
	}
}
