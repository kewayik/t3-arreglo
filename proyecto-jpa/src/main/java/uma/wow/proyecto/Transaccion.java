package uma.wow.proyecto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Transaccion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private String idUnico;
	@Column (nullable = false) @Temporal(TemporalType.DATE)
	private Date fechaInstruccion;
	@Column (nullable = false)
	private double cantidad;
	@Column (nullable = true) @Temporal(TemporalType.DATE) 
	private Date fechaEjecucion;
	@Column (nullable = false)
	private String tipo;
	@Column (nullable = true)
	private String comision;
	@Column (nullable = true)
	private String internacional;
	
	@ManyToOne
	@JoinColumn (name="CUENTA_IBAN1",nullable = false)
	private Cuenta iban;
	
	@ManyToOne
	@JoinColumn (name="CUENTA_IBAN2",nullable = false)
	private Cuenta iban1;
	
	@ManyToOne
	@JoinColumn (name="DIVISA1",nullable = false)
	private Divisa divisa;
	
	@ManyToOne
	@JoinColumn (name="DIVISA2",nullable = false)
	private Divisa divisa1;
	

	public Transaccion() {
		super();
	}

	@Override
	public String toString() {
		return "Transaccion [idUnico=" + idUnico + ", fechaInstruccion=" + fechaInstruccion + ", cantidad=" + cantidad
				+ ", fechaEjecucion=" + fechaEjecucion + ", tipo=" + tipo + ", comision=" + comision
				+ ", internacional=" + internacional + "]";
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(idUnico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaccion other = (Transaccion) obj;
		if (idUnico == null) {
			if (other.idUnico != null)
				return false;
		} else if (!idUnico.equals(other.idUnico))
			return false;
		return true;
	}

	public String getIDunico() {
		return idUnico;
	}

	public Date getFechaInstruccion() {
		return fechaInstruccion;
	}

	public double getCantidad() {
		return cantidad;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public String getTipo() {
		return tipo;
	}

	public String getComision() {
		return comision;
	}

	public String getInternacional() {
		return internacional;
	}

	public void setIDunico(String idUnico) {
		this.idUnico = idUnico;
	}

	public void setFechaInstruccion(Date fechaInstruccion) {
		this.fechaInstruccion = fechaInstruccion;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public void setInternacional(String internacional) {
		this.internacional = internacional;
	}

	public Cuenta getIban() {
		return iban;
	}

	public Cuenta getIban1() {
		return iban1;
	}

	public Divisa getDivisa() {
		return divisa;
	}

	public Divisa getDivisa1() {
		return divisa1;
	}

	public void setIban(Cuenta iban) {
		this.iban = iban;
	}

	public void setIban1(Cuenta iban1) {
		this.iban1 = iban1;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public void setDivisa1(Divisa divisa1) {
		this.divisa1 = divisa1;
	}

	

	

}