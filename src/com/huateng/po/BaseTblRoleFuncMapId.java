package com.huateng.po;

import java.io.Serializable;

import com.huateng.system.util.CommonFunction;

public abstract class BaseTblRoleFuncMapId implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private String keyId;

	private String valueId;

	public BaseTblRoleFuncMapId() {
	}

	public String getKeyId() {
		return CommonFunction.fillString(keyId, '0', 4, false);
	}

	public void setKeyId(String keyId) {
		this.keyId = CommonFunction.fillString(keyId, '0', 4, false);
	}

	public String getValueId() {
		return CommonFunction.fillString(valueId, '0', 4, false);
	}

	public void setValueId(String valueId) {
		this.valueId = CommonFunction.fillString(valueId, '0', 4, false);
	}

	public BaseTblRoleFuncMapId(String keyId, String valueId) {
		super();
		this.keyId = CommonFunction.fillString(keyId, '0', 4, false);
		this.valueId = CommonFunction.fillString(valueId, '0', 4, false);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyId == null) ? 0 : keyId.hashCode());
		result = prime * result + ((valueId == null) ? 0 : valueId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseTblRoleFuncMapId other = (BaseTblRoleFuncMapId) obj;
		if (keyId == null) {
			if (other.keyId != null)
				return false;
		} else if (!keyId.equals(other.keyId))
			return false;
		if (valueId == null) {
			if (other.valueId != null)
				return false;
		} else if (!valueId.equals(other.valueId))
			return false;
		return true;
	}

}