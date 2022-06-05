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
import uma.wow.proyecto.ejb.exceptions.ContraseniaInvalida;
import uma.wow.proyecto.ejb.exceptions.CuentasActivas;
import uma.wow.proyecto.ejb.exceptions.EJBException;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;


@Named(value = "individualEliminar")
@SessionScoped
public class IndividualEliminar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private InfoSesion sesion;	
	@Inject
	private GestionCliente clienteEJB;	
	
	private Individual individual;
	private Usuario usuario;

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}
	
	public IndividualEliminar() {
		individual = new Individual();
		usuario = new Usuario();
	}

	public String individualEliminar() {
		try {	
			usuario = sesion.getUsuario();
			clienteEJB.bajaCliente(individual, usuario);
			return "mainAdmin.xhtml";
			
		}catch (UsuarioNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("individualEliminar", fm);
		}catch (ClienteNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Cliente no encontrado");
			FacesContext.getCurrentInstance().addMessage("individualEliminar", fm);
		}catch (ContraseniaInvalida e) {	
			FacesMessage fm = new FacesMessage("Contrasenia invalida");
			FacesContext.getCurrentInstance().addMessage("individualEliminar", fm);
		}catch (NoAdministradorException e) {
			FacesMessage fm = new FacesMessage("No tienes permiso");
			FacesContext.getCurrentInstance().addMessage("individualEliminar", fm);
		}catch (CuentasActivas e) {
			FacesMessage fm = new FacesMessage("El cliente tiene cuentas activas");
			FacesContext.getCurrentInstance().addMessage("individualEliminar", fm);
		}catch (EJBException e) {
			FacesMessage fm = new FacesMessage("Excepcion no controlada");
			FacesContext.getCurrentInstance().addMessage("individualEliminar", fm);
		}
		
		return null;
	}
	
	

}
