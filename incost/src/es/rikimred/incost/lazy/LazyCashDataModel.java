/**
 * 
 */
package es.rikimred.incost.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.rikimred.incost.global.GlobalCash;
import es.rikimred.incost.lazy.domain.GenericLazyDataModel;
import es.rikimred.incost.model.CashData;

/**
 * Modelo de datos de Cash
 * @author jrneyra
 */
public class LazyCashDataModel extends GenericLazyDataModel<CashData> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 */
	public LazyCashDataModel() {
	}

	@Override
	public List<CashData> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder,
			final Map<String, String> filters) {
		setRowCount(GlobalCash.instance().size());
		return GlobalCash.instance().list(first, first + pageSize);
	}
}
