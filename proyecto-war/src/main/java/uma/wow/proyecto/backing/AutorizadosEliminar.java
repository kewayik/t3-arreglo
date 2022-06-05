package uma.wow.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import uma.wow.proyecto.PersonaAutorizada;
import uma.wow.proyecto.Usuario;
import uma.wow.proyecto.ejb.GestionPersonaAutorizada;
import uma.wow.proyecto.ejb.exceptions.ContraseniaInvalida;
import uma.wow.proyecto.ejb.exceptions.EJBException;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.PersonaAutorizadaNoEncontrada;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;

@Named(value = "autorizadosEliminar")
@RequestScoped
public class AutorizadosEliminar {
	
	@Inject
	private GestionPersonaAutorizada autorizadaEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private PersonaAutorizada personaAut;
	
	public AutorizadosEliminar() {
		
		usuario = new Usuario();
		personaAut = new PersonaAutorizada();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PersonaAutorizada getPersonaAut() {
		return personaAut;
	}

	public void setPersonaAut(PersonaAutorizada personaAut) {
		this.personaAut = personaAut;
	}
	
	public String autorizadosEliminar() {
		
		try {
			
			usuario = sesion.getUsuario();
			autorizadaEJB.borraPersonaAutorizada(getPersonaAut(), getUsuario());
			return "mainAdmin.xhtml";
		
		}catch(PersonaAutorizadaNoEncontrada e) {
			FacesMessage fm = new FacesMessage("Persona Autorizada no encontrada");
			FacesContext.getCurrentInstance().addMessage("AutorizadosModificar", fm);
		}catch (UsuarioNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("AutorizadosModificar", fm);
		}catch (ContraseniaInvalida e) {
			FacesMessage fm = new FacesMessage("Contraseina invalida");
			FacesContext.getCurrentInstance().addMessage("AutorizadosModificar", fm);
		}catch (NoAdministradorException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("AutorizadosModificar", fm);
			
		}catch (EJBException e) {
			FacesMessage fm = new FacesMessage("Excepcion no controlada");
			FacesContext.getCurrentInstance().addMessage("AutorizadosModificar", fm);
		}
		return null;
		
	}

}
