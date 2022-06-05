package uma.wow.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import uma.wow.proyecto.Usuario;
import uma.wow.proyecto.ejb.AccesoEJB;
import uma.wow.proyecto.ejb.GestionAcceso;
import uma.wow.proyecto.ejb.exceptions.ClienteNoEncontrado;
import uma.wow.proyecto.ejb.exceptions.ContraseniaInvalida;
import uma.wow.proyecto.ejb.exceptions.CuentaBloqueada;
import uma.wow.proyecto.ejb.exceptions.CuentaDeBaja;
import uma.wow.proyecto.ejb.exceptions.EsAdministrador;
import uma.wow.proyecto.ejb.exceptions.EsEmpresaException;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;

@Named
@RequestScoped
public class Login {
	
	@Inject
	private GestionAcceso acceso;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	public Login() {
		usuario = new Usuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String loginCliente() {
		
		try {
			acceso.loginCliente(usuario);
			sesion.setUsuario(usuario);
			return "mainUsuario.xhtml";
			
		} catch (UsuarioNoEncontrado e) {
			FacesMessage fm = new FacesMessage("No se encuentra el usuario");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);

		} catch (ContraseniaInvalida e) {
			FacesMessage fm = new FacesMessage("La contraseña introducida no es correcta");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
			
		} catch (EsEmpresaException e) {
			FacesMessage fm = new FacesMessage("Los clientes de tipo Empresa no pueden acceder a la aplicación");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
			
		} catch (CuentaBloqueada e) {
			FacesMessage fm = new FacesMessage("La cuenta asociada a este usuario está bloqueada");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
			
		} catch (CuentaDeBaja e) {
			FacesMessage fm = new FacesMessage("La cuenta asociada a este usuario ha sido dada de baja");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
			
		} catch (ClienteNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Este usuario no está asociado a ningún cliente");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
			
		} catch (EsAdministrador e) {
			FacesMessage fm = new FacesMessage("El usuario es un administrador");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
		}
		return null;
	}
	
	public String loginAdmin() {
		
		try {
			acceso.loginAdministrador(usuario);
			sesion.setUsuario(usuario);
			return "mainAdmin.xhtml";
			
		} catch (UsuarioNoEncontrado e) {
			
			FacesMessage fm = new FacesMessage("No se encuentra el usuario");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
			
		} catch (ContraseniaInvalida e) {
			FacesMessage fm = new FacesMessage("La contraseña introducida no es correcta");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
			
		} catch (NoAdministradorException e) {
			FacesMessage fm = new FacesMessage("El usuario no es un administrador");
			FacesContext.getCurrentInstance().addMessage("loginUser", fm);
		}
		
		return null;
		
	}



}
