/**
 * 
 */
package es.rikimred.incost.enums;

/**
 * 
 * @author jrneyra
 */
public enum BalanceStatusEnum {
	UNDEFINED("balance.status.undefined"),
	WARNING("balance.status.warning"),
	NEGATIVE("balance.status.negative"),
	POSITIVE("balance.status.positive");

	private String name;

	BalanceStatusEnum(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
