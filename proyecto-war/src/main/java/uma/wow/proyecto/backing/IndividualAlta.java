package uma.wow.proyecto.backing;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import uma.wow.proyecto.Individual;
import uma.wow.proyecto.Usuario;
import uma.wow.proyecto.ejb.GestionCliente;
import uma.wow.proyecto.ejb.exceptions.ClienteNoEncontrado;
import uma.wow.proyecto.ejb.exceptions.ClienteYaExistente;
import uma.wow.proyecto.ejb.exceptions.EJBException;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;


@Named(value = "individualAlta")
@SessionScoped
public class IndividualAlta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private InfoSesion sesion;
	
	@Inject
	private GestionCliente clienteEJB;	

	private Usuario usuario;	

	private Individual individual;

	public Usuario getUsuario() {
		return usuario;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}
	
	public IndividualAlta() {
		usuario = new Usuario();
		individual = new Individual();
	}

	public String individualAlta() {
		try {
			
			usuario = sesion.getUsuario();
			clienteEJB.altaCliente(individual, usuario);
			return "mainAdmin.xhtml";
			
		}catch (UsuarioNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("individualAlta", fm);
		}catch (ClienteNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Cliente no encontrado");
			FacesContext.getCurrentInstance().addMessage("individualAlta", fm);
		}catch (ClienteYaExistente e) {	
			FacesMessage fm = new FacesMessage("Cliente ya existente");
			FacesContext.getCurrentInstance().addMessage("individualAlta", fm);
		}catch (NoAdministradorException e) {
			FacesMessage fm = new FacesMessage("No tienes permiso");
			FacesContext.getCurrentInstance().addMessage("individualAlta", fm);
		}catch (EJBException e) {
			FacesMessage fm = new FacesMessage("Excepcion no controlada");
			FacesContext.getCurrentInstance().addMessage("individualAlta", fm);
		}
		
		return null;
	}
	
	

}
