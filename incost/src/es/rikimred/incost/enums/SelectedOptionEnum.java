/**
 * 
 */
package es.rikimred.incost.enums;

/**
 * @author jrneyra
 * 
 */
public enum SelectedOptionEnum {
	YES_OPTION("selected.option.yes"),
	NO_OPTION("selected.option.no"),
	CANCEL_OPTION("selected.option.cancel");

	private String key;

	SelectedOptionEnum(final String key) {
		this.key = key;
	}

	public String Key() {
		return key;
	}
}
