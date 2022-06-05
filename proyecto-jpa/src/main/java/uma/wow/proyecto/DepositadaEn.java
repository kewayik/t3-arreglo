package uma.wow.proyecto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class DepositadaEn implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private double saldo;
	
	
	@EmbeddedId
	private DepositaEnPK id;
	

	@ManyToOne
	@JoinColumn(name="CUENTA_REFERENCIA_IBAN", nullable = false)
	@MapsId("cuentaReferencia")
	private CuentaReferencia cuentaReferencia;
	
	@ManyToOne
	@JoinColumn(name="POOLED_ACCOUNT_IBAN", nullable = false)
	@MapsId("pooledAccount")
	private PooledAccount pooledAccount;
	

	
	public DepositadaEn() {
		
	}
	
	public DepositaEnPK getId() {
		return id;
	}

	public void setId(DepositaEnPK id) {
		this.id = id;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	public CuentaReferencia getId1() {
		return cuentaReferencia;
	}

	public void setId1(CuentaReferencia id1) {
		this.cuentaReferencia = id1;
	}

	public PooledAccount getId2() {
		return this.pooledAccount;
	}

	public void setId2(PooledAccount id2) {
		this.pooledAccount = id2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DepositadaEn other = (DepositadaEn) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DepositadaEn [saldo=" + saldo + ", id=" + id +"]";
	}




	
	
	
}
