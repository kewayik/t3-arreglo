package uma.wow.proyecto.ejb;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.*;


public interface GestionBloqueoCliente {
	
	public void bloqueoPersonaFisica(Individual c,Usuario admin) throws ClienteNoEncontrado, CuentaDeBaja, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void bloqueoAutorizado(PersonaAutorizada c,Usuario admin) throws ClienteNoEncontrado, CuentaDeBaja, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void bloqueoEmpresa(Empresa c,Usuario admin) throws ClienteNoEncontrado, CuentaDeBaja, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void desbloqueoPersonaFisica(Individual c,Usuario admin) throws ClienteNoEncontrado, CuentaDeBaja, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void desbloqueoAutorizado(PersonaAutorizada c,Usuario admin) throws ClienteNoEncontrado, CuentaDeBaja, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void desbloqueoEmpresa(Empresa c,Usuario admin) throws ClienteNoEncontrado, CuentaDeBaja, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;


}
