/**
 * 
 */
package es.rikimred.incost.model;

import java.math.BigDecimal;
import java.util.Date;

import es.rikimred.incost.entity.Cash;
import es.rikimred.incost.model.domain.GenericData;

/**
 * 
 * @author roberto
 */
public class CashData extends GenericData {

	// Entity
	private final Cash entity;

	/**
	 * @param entity
	 */
	public CashData(final Cash entity) {
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

	public Date getLastUpdate() {
		return entity.getLastUpdate();
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.entity.setLastUpdate(lastUpdate);
	}

	public Cash getEntity() {
		return entity;
	}
}
