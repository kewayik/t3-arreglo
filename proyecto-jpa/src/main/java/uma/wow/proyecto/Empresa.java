package uma.wow.proyecto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Empresa extends Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private String razonSocial;
	
	@OneToMany(mappedBy="empresaId")
	private List<Autorizacion> autori;
	
	
	public Empresa() {
		
	}

	public String getRazon_Social() {
		return razonSocial;
	}

	public void setRazon_Social(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public List<Autorizacion> getAutori() {
		return autori;
	}

	public void setAutori(List<Autorizacion> autori) {
		this.autori = autori;
	}

	@Override
	public String toString() {
		return "Empresa [razonSocial=" + razonSocial + "]";
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
