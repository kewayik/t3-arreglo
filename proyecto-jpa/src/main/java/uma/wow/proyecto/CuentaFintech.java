package uma.wow.proyecto;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.*;


@Entity


public class CuentaFintech extends Cuenta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private String estado;
	
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private  Date fechaApertura;
	
	@Column (nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaCierre;
	@Column (nullable = true)
	private String clasificacion;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;
	
	
	
	public CuentaFintech() {
		
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Date getFechaApertura() {
		return fechaApertura;
	}


	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}


	public Date getFechaCierre() {
		return fechaCierre;
	}


	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}


	public String getClasificacion() {
		return clasificacion;
	}


	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		return "CuentaFintech [estado=" + estado + ", fechaApertura=" + fechaApertura + ", fechaCierre=" + fechaCierre
				+ ", clasificacion=" + clasificacion + ", getIban()=" + getIban() + ", getSwift()=" + getSwift() + "]";
	}

	

}
