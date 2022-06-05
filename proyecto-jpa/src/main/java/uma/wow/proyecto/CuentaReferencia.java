package uma.wow.proyecto;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity

public class CuentaReferencia extends Cuenta {
	
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private String nombreBanco;
	@Column (nullable = true)
	private String sucursal;
	@Column (nullable = true)
	private String pais;
	@Column(nullable = false)
	private double saldo;
	@Column (nullable = true)
	private Date fechaApertura;
	@Column (nullable = true)
	private String estado;
	
	@ManyToOne
	private Divisa abreviatura;
	
	@OneToOne
	private Segregada cuentaSegregada;
	
	
	@OneToMany(mappedBy="cuentaReferencia")
	private List<DepositadaEn> depositadaEn;
	
	public CuentaReferencia() {
		
	}
	

	public String getNombreBanco() {
		return nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Divisa getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(Divisa abreviatura) {
		this.abreviatura = abreviatura;
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
		return "CuentaReferencia [nombreBanco=" + nombreBanco + ", sucursal=" + sucursal + ", pais=" + pais + ", saldo="
				+ saldo + ", fechaApertura=" + fechaApertura + ", estado=" + estado + ", abreviatura=" + abreviatura
				+ "]";
	}
	

}
