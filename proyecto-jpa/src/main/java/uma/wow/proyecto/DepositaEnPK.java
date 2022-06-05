package uma.wow.proyecto;

import java.io.Serializable;
import javax.persistence.*;


@Embeddable
public class DepositaEnPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="POOLED_ACCOUNT_IBAN", insertable=false, updatable=false)
	private String pooledAccount;

	@Column(name="CUENTA_REFERENCIA_IBAN", insertable=false, updatable=false)
	private String cuentaReferencia;

	public DepositaEnPK() {
	}
	public String getPooledAccountIban() {
		return this.pooledAccount;
	}
	public void setPooledAccountIban(String pooledAccountIban) {
		this.pooledAccount = pooledAccountIban;
	}
	public String getCuentaReferenciaIban() {
		return this.cuentaReferencia;
	}
	public void setCuentaReferenciaIban(String cuentaReferenciaIban) {
		this.cuentaReferencia= cuentaReferenciaIban;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DepositaEnPK)) {
			return false;
		}
		DepositaEnPK castOther = (DepositaEnPK)other;
		return 
			this.pooledAccount.equals(castOther.pooledAccount)
			&& this.cuentaReferencia.equals(castOther.cuentaReferencia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pooledAccount.hashCode();
		hash = hash * prime + this.cuentaReferencia.hashCode();
		
		return hash;
	}
}
