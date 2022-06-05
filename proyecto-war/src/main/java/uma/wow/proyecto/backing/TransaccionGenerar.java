package uma.wow.proyecto.backing;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import uma.wow.proyecto.Empresa;
import uma.wow.proyecto.Individual;
import uma.wow.proyecto.Usuario;
import uma.wow.proyecto.ejb.GestionCliente;
import uma.wow.proyecto.ejb.GestionTransaccion;
import uma.wow.proyecto.ejb.exceptions.ClienteNoEncontrado;
import uma.wow.proyecto.ejb.exceptions.ClienteSinAutorizacion;
import uma.wow.proyecto.ejb.exceptions.ClienteYaExistente;
import uma.wow.proyecto.ejb.exceptions.CuentaNoEncontrada;
import uma.wow.proyecto.ejb.exceptions.EJBException;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.PermisoSoloDeLecturaException;
import uma.wow.proyecto.ejb.exceptions.SaldoInsuficiente;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;


@Named(value = "transaccionGenerar")
@SessionScoped
public class TransaccionGenerar implements Serializable{
	
	@Inject
	private InfoSesion sesion;
	
	@Inject
	private GestionTransaccion transaccionEJB;
	
	private Usuario usuario;
	private String iban1;
	private String iban2;
	private double dinero;
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIban1() {
		return iban1;
	}

	public String getIban2() {
		return iban2;
	}

	public double getDinero() {
		return dinero;
	}

	public void setIban1(String iban1) {
		this.iban1 = iban1;
	}

	public void setIban2(String iban2) {
		this.iban2 = iban2;
	}

	public void setDinero(double dinero) {
		this.dinero = dinero;
	}

	public TransaccionGenerar() {
		usuario = new Usuario();
	}

	public String transaccionGenerar() {
		try {
			
			usuario = sesion.getUsuario();
			transaccionEJB.transaccion(usuario, iban1, iban2, dinero);
			return "mainUsuario.xhtml";
			
		}catch (UsuarioNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("transaccionGenerar", fm);
		}catch (ClienteNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Cliente no encontrado");
			FacesContext.getCurrentInstance().addMessage("transaccionGenerar", fm);
		}catch (ClienteSinAutorizacion e) {	
			FacesMessage fm = new FacesMessage("Cliente sin autorizacion");
			FacesContext.getCurrentInstance().addMessage("transaccionGenerar", fm);
		}catch (CuentaNoEncontrada e) {
			FacesMessage fm = new FacesMessage("Cuenta no encontrada");
			FacesContext.getCurrentInstance().addMessage("transaccionGenerar", fm);
		}catch (SaldoInsuficiente e) {
			FacesMessage fm = new FacesMessage("Saldo insuficiente");
			FacesContext.getCurrentInstance().addMessage("transaccionGenerar", fm);
		}catch (PermisoSoloDeLecturaException e) {
			FacesMessage fm = new FacesMessage("Permiso solo de lectura");
			FacesContext.getCurrentInstance().addMessage("transaccionGenerar", fm);
		}catch (EJBException e) {
			FacesMessage fm = new FacesMessage("Excepcion no controlada");
			FacesContext.getCurrentInstance().addMessage("transaccionGenerar", fm);
		}
		
		return null;
	}
	
	

}
