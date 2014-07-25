/**
 * 
 */
package es.rikimred.incost.enums;

/**
 * 
 * @author jrneyra
 */
public enum BalanceStateEnum {
	OPENED(Boolean.FALSE),
	CLOSED(Boolean.TRUE);

	private Boolean value;

	BalanceStateEnum(final Boolean value) {
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}
}
