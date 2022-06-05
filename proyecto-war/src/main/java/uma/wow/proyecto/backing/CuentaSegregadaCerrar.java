package uma.wow.proyecto.backing;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import uma.wow.proyecto.Segregada;
import uma.wow.proyecto.Usuario;
import uma.wow.proyecto.ejb.GestionCuenta;
import uma.wow.proyecto.ejb.exceptions.ContraseniaInvalida;
import uma.wow.proyecto.ejb.exceptions.CuentaNoEncontrada;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.SaldoException;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;

@Named(value = "cuentaSegregadaCerrar")
@RequestScoped
public class CuentaSegregadaCerrar {
	
	@Inject
	InfoSesion sesion;
	
	@Inject
	GestionCuenta cuentaEJB;
	
	private Usuario usuario;
	
	private String iban ;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public String cuentaSegregadaCerrar() {
		
		try {
			
			usuario = sesion.getUsuario();
			Segregada seg = cuentaEJB.devolverSegregada(getIban());
			
			cuentaEJB.cierraCuenta(seg, usuario);
			
		} catch (CuentaNoEncontrada e) {
			FacesMessage fm = new FacesMessage("Cuenta no encontrada");
			FacesContext.getCurrentInstance().addMessage("CuentaSegregadaCerrar", fm);
		
		} catch (SaldoException e) {
			FacesMessage fm = new FacesMessage("Saldo erroneo");
			FacesContext.getCurrentInstance().addMessage("CuentaSegregadaCerrar", fm);
		
		} catch (UsuarioNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("CuentaSegregadaCerrar", fm);
		
		} catch (ContraseniaInvalida e) {
			FacesMessage fm = new FacesMessage("Contraseina invalida");
			FacesContext.getCurrentInstance().addMessage("CuentaSegregadaCerrar", fm);
		
		} catch (NoAdministradorException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrador");
			FacesContext.getCurrentInstance().addMessage("CuentaSegregadaCerrar", fm);
		
		} catch(EJBException e) {
			FacesMessage fm = new FacesMessage("Exception no controlada");
			FacesContext.getCurrentInstance().addMessage("CuentaSegregadaCerrar", fm);
		}
		
		return null;	
	}

}
