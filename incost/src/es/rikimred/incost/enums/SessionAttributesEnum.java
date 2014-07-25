/**
 * 
 */
package es.rikimred.incost.enums;

/**
 * @author roberto
 * 
 */
public enum SessionAttributesEnum {
	USERNAME("username"),
	USERID("userid"),
	BALANCEID("balanceid");

	private String attribute;

	SessionAttributesEnum(final String attribute) {
		this.attribute = attribute;
	}

	public String getName() {
		return attribute;
	}
}
