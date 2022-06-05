package uma.wow.proyecto.ejb;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.*;


public interface GestionTransaccion {
	
	public void transaccion(Usuario user, String IBANorigen, String IBANdestino, double dinero) throws EJBException;
	public void transaccionCliente(Cliente c, String IBANorigen, String IBANdestino, double dinero) throws ClienteSinAutorizacion, CuentaNoEncontrada, SaldoInsuficiente;
	public void transaccionAutorizado(PersonaAutorizada c, String IBANorigen, String IBANdestino, double dinero) throws PermisoSoloDeLecturaException, ClienteSinAutorizacion, SaldoInsuficiente, CuentaNoEncontrada;

}