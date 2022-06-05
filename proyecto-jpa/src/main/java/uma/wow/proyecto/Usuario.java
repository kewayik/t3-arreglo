package uma.wow.proyecto;

import java.util.Objects;
import javax.persistence.*;

@Entity
public class Usuario {

	
	@Id
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String contras;	
	@Column(nullable = false)
	private String tipo;
	
	@OneToOne
	private PersonaAutorizada autorizada;
	
	@OneToOne
	private Cliente cliente;

	public Usuario() {
		
	}
	
	public String getNombreUsuario() {
		return nombre;
	}

	public void setNombreUsuario(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return contras;
	}

	public void setPassword(String contras) {
		this.contras = contras;
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public PersonaAutorizada getPersonaAutorizada() {
		return autorizada;
	}

	public void setPersonaAutorizada(PersonaAutorizada autorizada) {
		this.autorizada = autorizada;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return "Usuario [nombre= " + nombre + ", tipo de usuario= "+tipo+"]";
	}
	
}