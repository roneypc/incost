/**
 * 
 */
package es.rikimred.incost.lazy.domain;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.primefaces.model.LazyDataModel;

import es.rikimred.incost.model.domain.GenericData;

/**
 * Modelo de datos generico
 * @author jrneyra
 */
public abstract class GenericLazyDataModel<E extends GenericData> extends LazyDataModel<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Class<E> dataClass;

	// Data Source for binding data to the DataTable
	private List<E> datasource;

	// Selected Page size in the DataTable
	private int pageSize;

	// Current row index number
	private int rowIndex;

	// Total row number
	private int rowCount;

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public GenericLazyDataModel() {
		this.dataClass =
				(Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
						.getActualTypeArguments()[0];
	}

	/**
	 * Gets the data object's primary key
	 * @param user
	 * @return Object
	 */
	@Override
	public Object getRowKey(final E data) {
		return ((GenericData) data).getId();
	}

	/**
	 * Checks if the row is available
	 * @return boolean
	 */
	@Override
	public boolean isRowAvailable() {
		if (datasource == null) {
			return false;
		}
		final int index = rowIndex % pageSize;
		return index >= 0 && index < datasource.size();
	}

	/**
	 * Returns the balance object at the specified position in datasource.
	 * @return
	 */
	@Override
	public E getRowData() {
		if (datasource == null) {
			return null;
		}
		final int index = rowIndex % pageSize;
		if (index > datasource.size()) {
			return null;
		}
		return datasource.get(index);
	}

	/**
	 * Returns the balance object that has the row key.
	 * @param rowKey
	 * @return
	 */
	@Override
	public E getRowData(final String rowKey) {
		if (datasource == null) {
			return null;
		}
		for (final E data : datasource) {
			if (((GenericData) data).getId().toString().equals(rowKey)) {
				return data;
			}
		}
		return null;
	}

	/*
	 * ===== Getters and Setters of GenericLazyDataModel fields
	 */

	/**
	 * 
	 * @param pageSize
	 */
	@Override
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Returns page size
	 * @return int
	 */
	@Override
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Returns current row index
	 * @return int
	 */
	@Override
	public int getRowIndex() {
		return this.rowIndex;
	}

	/**
	 * Sets row index
	 * @param rowIndex
	 */
	@Override
	public void setRowIndex(final int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * Sets row count
	 * @param rowCount
	 */
	@Override
	public void setRowCount(final int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Returns row count
	 * @return int
	 */
	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	/**
	 * Sets wrapped data
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setWrappedData(final Object list) {
		this.datasource = (List<E>) list;
	}

	/**
	 * Returns wrapped data
	 * @return
	 */
	@Override
	public Object getWrappedData() {
		return datasource;
	}

	/**
	 * Gets the data class.
	 * 
	 * @return the data class
	 */
	protected Class<E> getDataClass() {
		return dataClass;
	}
}
