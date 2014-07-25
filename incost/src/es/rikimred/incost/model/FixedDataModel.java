/**
 * 
 */
package es.rikimred.incost.model;

import java.math.BigDecimal;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.entity.FixedData;
import es.rikimred.incost.model.domain.GenericData;

/**
 * 
 * @author roberto
 */
public class FixedDataModel extends GenericData {

	// Entity
	private final FixedData entity;

	/**
	 * @param entity
	 */
	public FixedDataModel(final FixedData entity) {
		super(entity);
		this.entity = entity;
	}

	/**
	 * Recuperar el entity
	 * @return
	 */
	public FixedData getEntity() {
		return entity;
	}

	/*
	 * GETTERS & SETTERS
	 */

	/**
	 * 
	 */
	@Override
	public Integer getId() {
		return entity.getId();
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return entity.getCategory();
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(final Category category) {
		entity.setCategory(category);
	}

	public String getDescription() {
		return entity.getDescription();
	}

	public void setDescription(final String description) {
		this.entity.setDescription(description);
	}

	public BigDecimal getAmount() {
		return entity.getAmount();
	}

	public void setAmount(final BigDecimal amount) {
		this.entity.setAmount(amount);
	}
}
