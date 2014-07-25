/**
 * 
 */
package es.rikimred.incost.global;

import java.util.ArrayList;
import java.util.List;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.enums.CategoryTypeEnum;
import es.rikimred.incost.enums.SessionKeys;
import es.rikimred.incost.global.domain.GenericGlobal;
import es.rikimred.incost.model.CategoryData;

/**
 * @author jrneyra
 * 
 */
public class GlobalCategory extends GenericGlobal<CategoryData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia compartida de GlobalCategory
	 */
	private final static GlobalCategory instance = new GlobalCategory();

	/**
	 * Constructor privado
	 */
	private GlobalCategory() {
		super(SessionKeys.CATEGORY_MAP);
	}

	/**
	 * Obtiene la instancia de esta clase
	 * @return
	 */
	public static final GlobalCategory instance() {
		return instance;
	}

	/**
	 * 
	 * @param cType
	 * @return
	 */
	public List<Category> entityList(final CategoryTypeEnum cType) {
		final List<Category> entityList = new ArrayList<>();
		for (final CategoryData c : list()) {
			if (cType.equals(c.getCategoryType())) {
				entityList.add(c.getEntity());
			}
		}
		return entityList;
	}
}