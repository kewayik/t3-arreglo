package uma.wow.proyecto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity


public class PooledAccount extends CuentaFintech implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="pooledAccount")
	private List<DepositadaEn> depositaEn;

	
	public PooledAccount() {
		
	}

	public List<DepositadaEn> getDepositaEn() {
		return depositaEn;
	}

	public void setDepositaEn(List<DepositadaEn> depositaEn) {
		this.depositaEn = depositaEn;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "PooledAccount []";
	}
	
}