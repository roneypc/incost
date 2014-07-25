/**
 * 
 */
package es.rikimred.incost.model;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.enums.CategoryTypeEnum;
import es.rikimred.incost.model.domain.GenericData;

/**
 * 
 * @author roberto
 */
public class CategoryData extends GenericData {

	// Entity
	private final Category entity;

	/**
	 * Constructor a partir del entity
	 * @param entity
	 */
	public CategoryData(final Category entity) {
		super(entity);
		this.entity = entity;
	}

	public void setName(final String name) {
		entity.setName(name.toUpperCase());
	}

	public String getName() {
		return entity.getName();
	}

	public void setDescription(final String description) {
		entity.setDescription(description);
	}

	public String getDescription() {
		return entity.getDescription();
	}

	public void setCategoryType(final CategoryTypeEnum categoryType) {
		entity.setCategoryType(categoryType);
	}

	public CategoryTypeEnum getCategoryType() {
		return entity.getCategoryType();
	}

	/**
	 * Recuperar la entity
	 */
	public Category getEntity() {
		return entity;
	}
}
