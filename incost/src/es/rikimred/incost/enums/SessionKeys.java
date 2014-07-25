/**
 * 
 */
package es.rikimred.incost.enums;

/**
 * @author roberto
 * 
 */
public enum SessionKeys {
	USER_ACTIVE("user.active"),
	BALANCE_LOADED("balance.loaded"),
	EXPENSES_CATEGORIES("expenses.categories"),
	FIXED_INCOME_CATEGORY("fixed.income.category"),
	FIXED_BUDGET_CATEGORY("fixed.budget.category"),
	FIXED_EXPENSES_CATEGORY("fixed.expenses.category"),
	FIXED_CASH_CATEGORY("fixed.cash.category"),
	BOOK_MAP("book.map"),
	BOOK_BALANCE_MAP("book.balance.map"),
	BALANCE_MAP("balance.map"),
	SUB_BALANCE_MAP("sub.balance.map"),
	INCOME_MAP("income.map"),
	BUDGET_MAP("budget.map"),
	EXPENSES_MAP("expenses.map"),
	CASH_MAP("cash.map"),
	CATEGORY_MAP("category.map"),
	FIXED_INCOME_MAP("fixed.income.map"),
	FIXED_BUDGET_MAP("fixed.budget.map"),
	FIXED_EXPENSES_MAP("fixed.expenses.map"),
	FIXED_CASH_MAP("fixed.cash.map"),
	BUDGET_MAP_BACKUP("budget.map.backup"),
	EXPENSES_MAP_BACKUP("expenses.map.backup");

	private String key;

	SessionKeys(final String key) {
		this.key = key;
	}

	public String Key() {
		return key;
	}
}
