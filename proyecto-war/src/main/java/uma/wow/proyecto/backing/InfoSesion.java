package uma.wow.proyecto.backing;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import uma.wow.proyecto.Usuario;


@Named(value = "infoSesion")
@SessionScoped
public class InfoSesion implements Serializable{
	

	private Usuario usuario;

	
	public synchronized void setUsuario(Usuario usuario) {
	        this.usuario = usuario;
	}
	public synchronized boolean getAdmin() {
		boolean admin = getUsuario().getTipo().equals("ADMIN");
		return admin;
	}
	
    public synchronized Usuario getUsuario() {
        return usuario;
    }
    	
    public synchronized String invalidarSesion()
    {
        if (usuario != null)
        {
            usuario = null;
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }
        return "index.xhtml";
    }
	

}
