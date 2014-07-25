/**
 * 
 */
package es.rikimred.incost.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * @author jrneyra
 * 
 */
@Entity
@Table(name = "fixed_data")
public class FixedData extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "AMOUNT", precision = 12, scale = 2)
	private BigDecimal amount;

	private String description;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "ID_CATEGORY")
	private Category category;

	/**
	 * 
	 */
	public FixedData() {
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(final Category category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		// builder.append("FixedData [");
		if (description != null) {
			builder.append(description);
			builder.append(", ");
		}
		if (amount != null) {
			builder.append(amount);
		}
		// builder.append("]");
		return builder.toString();
	}

}