package uma.wow.proyecto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Autorizacion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AutorizacionPK id;
	
	@ManyToOne
	@JoinColumn(name = "EmpresaID",nullable = false)
	@MapsId("empresaId")
	private Empresa empresaId;
	
	@ManyToOne
	@JoinColumn(name = "AutorizadaID",nullable = false)
	@MapsId("idAutorizada")
	private PersonaAutorizada idAutorizada;
	
	@Column(nullable = false)
	private String tipo;
	
	public Autorizacion() {
		
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public Empresa getEmpresa() {
		return empresaId;
	}
	public void setEmpresa(Empresa id) {
		this.empresaId = id;
	}
	public PersonaAutorizada getIdAutorizada() {
		return idAutorizada;
	}
	public void setIdAutorizada(PersonaAutorizada id_autorizada) {
		this.idAutorizada = id_autorizada;
	}
	
	public AutorizacionPK getId() {
		return id;
	}

	public void setId(AutorizacionPK id) {
		this.id = id;
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
		Autorizacion other = (Autorizacion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Autorizacion [tipo=" + tipo + "]";
	}
	
	

	
	
	
}