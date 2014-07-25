/**
 * 
 */
package es.rikimred.incost.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author jrneyra
 */
public enum CategoryTypeEnum {
	CATEGORY_INCOMES("category.type.incomes"),
	CATEGORY_EXPENSES("category.type.expenses"),
	CATEGORY_SUB_BALANCE("category.type.subbalance"),
	FIXED_INCOME("category.type.fixed.income"),
	FIXED_BUDGET("category.type.fixed.budget"),
	FIXED_EXPENSES("category.type.fixed.expenses"),
	FIXED_CASH("category.type.fixed.cash"),
	ALL_CATEGORIES("category.type.all"),
	NOT_FOUND("category.type.not_found");

	private String name;

	CategoryTypeEnum(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public List<CategoryTypeEnum> operativeCategoryTypes() {
		return Arrays.asList(CATEGORY_INCOMES, CATEGORY_EXPENSES);
	}
}
