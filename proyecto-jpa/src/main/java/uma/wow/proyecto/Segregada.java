package uma.wow.proyecto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity

public class Segregada extends CuentaFintech implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Column (nullable = true)
	private String comision;
	
	@OneToOne
	private CuentaReferencia cuentaReferencia;
	

	public String getComision() {
		return comision;
	}


	public void setComision(String comision) {
		this.comision = comision;
	}



	public CuentaReferencia getCuentaReferencia() {
		return cuentaReferencia;
	}


	public void setCuentaReferencia(CuentaReferencia cuentaReferencia) {
		this.cuentaReferencia = cuentaReferencia;
	}


	@Override
	public String toString() {
		return "Segregada [comision=" + comision + "]";
	}


	
	public Segregada() {
		
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
