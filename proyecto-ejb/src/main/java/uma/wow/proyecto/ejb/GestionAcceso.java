package uma.wow.proyecto.ejb;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.Usuario;
public interface GestionAcceso {
	
	public void loginAdministrador(Usuario usuario) throws UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	
	public void loginCliente(Usuario usuario) throws UsuarioNoEncontrado, ContraseniaInvalida, EsEmpresaException, CuentaBloqueada, CuentaDeBaja, ClienteNoEncontrado, EsAdministrador;

}
