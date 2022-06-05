package uma.wow.proyecto.backing;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import uma.wow.proyecto.Cliente;
import uma.wow.proyecto.DepositaEnPK;
import uma.wow.proyecto.DepositadaEn;
import uma.wow.proyecto.Empresa;
import uma.wow.proyecto.Individual;
import uma.wow.proyecto.PooledAccount;
import uma.wow.proyecto.Segregada;
import uma.wow.proyecto.Usuario;
import uma.wow.proyecto.ejb.GestionCliente;
import uma.wow.proyecto.ejb.GestionCuenta;
import uma.wow.proyecto.ejb.exceptions.ClienteNoEncontrado;
import uma.wow.proyecto.ejb.exceptions.ContraseniaInvalida;
import uma.wow.proyecto.ejb.exceptions.CuentaEncontrada;
import uma.wow.proyecto.ejb.exceptions.CuentaNoEncontrada;
import uma.wow.proyecto.ejb.exceptions.EJBException;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;
import uma.wow.proyecto.ejb.exceptions.UsuarioNoEncontrado;

@Named(value = "cuentaSegregadaAbrir")
@RequestScoped
public class CuentaSegregadaAbrir {
	
	@Inject
	private InfoSesion sesion;
	
	@Inject
	private GestionCuenta cuentaEJB;
	
	@Inject
	private GestionCliente clienteEJB;
	
	private Segregada seg;
	
	private Usuario usuario;
	

	public Segregada getSeg() {
		return seg;
	}

	public void setSeg(Segregada seg) {
		this.seg = seg;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	public String cuentaSegregadaAbrir(){
			
			try {
				usuario = sesion.getUsuario();
				
				
				if(usuario.getCliente().getTipoCliente()=="FISICA") {
					
				Individual cliente = clienteEJB.devolverIndividual(usuario.getCliente().getId());
					cuentaEJB.creaCuenta(getSeg(), cliente, usuario);
				}else {
					
				Empresa	cliente = clienteEJB.devolverEmpresa(usuario.getCliente().getId());
					cuentaEJB.creaCuenta(getSeg(), cliente, usuario);
				}
	
				return "mainAdmin.xhtml";
				
			}catch (ClienteNoEncontrado e) {
				FacesMessage fm = new FacesMessage("El cliente no existe");
				FacesContext.getCurrentInstance().addMessage("cuentaSegregadaAbrir", fm);
			} catch (NoAdministradorException e) {
				FacesMessage fm = new FacesMessage("El usuario no es administrativo");
				FacesContext.getCurrentInstance().addMessage("cuentaSegregadaAbrir", fm);
			} catch (CuentaEncontrada e) {
				FacesMessage fm = new FacesMessage("Cuenta encontrada");
				FacesContext.getCurrentInstance().addMessage("cuentaSegregadaAbrir", fm);
			} catch (UsuarioNoEncontrado e) {
				FacesMessage fm = new FacesMessage("No se encuentra el usuario");
				FacesContext.getCurrentInstance().addMessage("cuentaSegregadaAbrir", fm);
			} catch (ContraseniaInvalida e) {
				FacesMessage fm = new FacesMessage("Contraseina invalida");
				FacesContext.getCurrentInstance().addMessage("cuentaSegregadaAbrir", fm);
			} catch (EJBException e) {
				FacesMessage fm = new FacesMessage("Excepcion no controlada");
				FacesContext.getCurrentInstance().addMessage("cuentaSegregadaAbrir", fm);
			}
			
			return null;
			
		
	}

}
