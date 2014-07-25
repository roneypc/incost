/**
 * 
 */
package es.rikimred.incost.model;

import java.math.BigDecimal;
import java.util.Date;

import es.rikimred.incost.entity.Income;
import es.rikimred.incost.model.domain.GenericData;

/**
 * 
 * @author roberto
 */
public class IncomeData extends GenericData {

	// Entity
	private final Income entity;

	/**
	 * @param entity
	 */
	public IncomeData(final Income entity) {
		super(entity);
		this.entity = entity;
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

	public Date getIncomeDate() {
		return entity.getIncomeDate();
	}

	public void setIncomeDate(final Date incomeDate) {
		this.entity.setIncomeDate(incomeDate);
	}

	public Income getEntity() {
		return entity;
	}
}
