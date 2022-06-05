package uma.wow.proyecto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private String id;
	*/
	
	@Id
	//@Column (unique=true, nullable = false)
	private String identificacion;
	
	@Column(nullable = false)
	private String tipoCliente;
	@Column(nullable = false)
	private String estado;
	//@Temporal (TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaAlta;
	@Column (nullable=true)
	//@Temporal(TemporalType.DATE)
	private Date fechaBaja;
	@Column(nullable = false)
	private String direccion;
	@Column(nullable = false)
	private String ciudad;
	@Column(nullable = false)
	private String codigoPostal;
	@Column(nullable = false)
	private String pais;
	@OneToOne(mappedBy = "cliente")
	@JoinColumn(nullable = false)
	private Usuario usuario;
	
	
	@OneToMany(cascade = {CascadeType.MERGE},mappedBy="cliente")
	private List<CuentaFintech> cuentas;
	
	public Cliente() {
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/*
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	*/

	public String getId() {
		return identificacion;
	}

	public void setId(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Object getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		
		if(fechaAlta!=null) {
			this.fechaAlta = Date.valueOf(fechaAlta);
		}
	}
	

	public Object getFechaBaja() {
		return fechaBaja;
	}

	
	public void setFechaBaja(String fecha) {
		this.fechaBaja = Date.valueOf(fecha);
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<CuentaFintech> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<CuentaFintech> cuentas) {
		this.cuentas = cuentas;
	}


	@Override
	public int hashCode() {
		return Objects.hash(identificacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(identificacion , other.identificacion);
	}

	@Override
	public String toString() {
		return "Cliente [identificacion=" + identificacion + ", tipoCliente=" + tipoCliente + ", estado="
				+ estado + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", direccion=" + direccion
				+ ", ciudad=" + ciudad + ", codigoPostal=" + codigoPostal + ", pais=" + pais + ", cuentas=" + cuentas
				+ "]";
	}
	
	
}