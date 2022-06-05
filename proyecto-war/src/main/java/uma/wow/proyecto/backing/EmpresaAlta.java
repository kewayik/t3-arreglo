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
import uma.wow.proyecto.ejb.exceptions.ClienteNoEncontrado;
import uma.wow.proyecto.ejb.exceptions.ClienteYaExistente;
import uma.wow.proyecto.ejb.exceptions.EJBException;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;


@Named(value = "empresaAlta")
@SessionScoped
public class EmpresaAlta implements Serializable{
	
	@Inject
	private InfoSesion sesion;
	
	@Inject
	private GestionCliente clienteEJB;
	
	private Usuario usuario;	
	private Empresa empresa;

	public Usuario getUsuario() {
		return usuario;
	}

	
	public Empresa getEmpresa() {
		return empresa;
	}



	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
	public EmpresaAlta() {
		usuario = new Usuario();
		empresa = new Empresa();
	}

	public String empresaAlta() {
		try {
			
			usuario = sesion.getUsuario();
			clienteEJB.altaCliente(empresa, usuario);;
			return "mainAdmin.xhtml";
			
		}catch (UsuarioNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("empresaAlta", fm);
		}catch (ClienteNoEncontrado e) {
			FacesMessage fm = new FacesMessage("Cliente no encontrado");
			FacesContext.getCurrentInstance().addMessage("empresaAlta", fm);
		}catch (ClienteYaExistente e) {	
			FacesMessage fm = new FacesMessage("Cliente ya existente");
			FacesContext.getCurrentInstance().addMessage("empresaAlta", fm);
		}catch (NoAdministradorException e) {
			FacesMessage fm = new FacesMessage("No tienes permiso");
			FacesContext.getCurrentInstance().addMessage("empresaAlta", fm);
		}catch (EJBException e) {
			FacesMessage fm = new FacesMessage("Excepcion no controlada");
			FacesContext.getCurrentInstance().addMessage("empresaAlta", fm);
		}
		
		return null;
	}
	
	

}
