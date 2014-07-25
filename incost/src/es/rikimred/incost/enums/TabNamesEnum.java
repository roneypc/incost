/**
 * 
 */
package es.rikimred.incost.enums;

/**
 * @author jrneyra
 * 
 */
public enum TabNamesEnum {
	TAB_BALANCE("Balance"),
	TAB_BUDGET("Budget"),
	TAB_INCOMES("Incomes"),
	TAB_EXPENSES("Expenses"),
	TAB_CATEGORY("Category"),
	TAB_FIXED_DATA("FixedData");

	private String name;

	TabNamesEnum(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
