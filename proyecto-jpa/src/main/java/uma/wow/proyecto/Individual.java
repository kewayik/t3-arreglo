package uma.wow.proyecto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;


import javax.persistence.*;

@Entity
public class Individual extends Cliente implements Serializable  {
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String apellido;
	@Column (nullable=true)
	//@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
		
	public Individual() {
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Object getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFecha_nacimiento(String fechaNacimiento) {
		if(fechaNacimiento!=null) {
			this.fechaNacimiento = Date.valueOf(fechaNacimiento);
		}
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
		return "Individual [nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento + "]";
	}
	
}
