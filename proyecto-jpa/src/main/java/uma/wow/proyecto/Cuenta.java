package uma.wow.proyecto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;

@Entity

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)


public class Cuenta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id 
	private String iban;
	@Column (nullable = true)
	private String swift;
	

	@OneToMany(mappedBy="iban")
	private List<Transaccion> transaccion;

	@OneToMany(mappedBy="iban1")
	private List<Transaccion> transaccion1;
	
	
	public Cuenta() {
		
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}


	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	@Override
	public int hashCode() {
		return Objects.hash(iban);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return Objects.equals(iban, other.iban);
	}

	@Override
	public String toString() {
		return "Cuenta [iban=" + iban + ", swift=" + swift + "]";
	}
	
	
}
