/**
 * 
 */
package es.rikimred.incost.model.domain;

import es.rikimred.incost.entity.domain.GenericEntity;

/**
 * @author roberto
 * 
 */
public abstract class GenericData {

	private final GenericEntity genericEntity;

	/**
	 * 
	 */
	public GenericData(final GenericEntity genericEntity) {
		this.genericEntity = genericEntity;
	}

	public Integer getId() {
		return genericEntity.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (genericEntity == null ? 0 : genericEntity.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GenericData)) {
			return false;
		}
		final GenericData other = (GenericData) obj;
		if (genericEntity == null) {
			if (other.genericEntity != null) {
				return false;
			}
		}
		else if (!genericEntity.equals(other.genericEntity)) {
			return false;
		}
		return true;
	}
}
