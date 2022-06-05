package uma.wow.proyecto.ejb;
import uma.wow.proyecto.ejb.exceptions.*;

import java.util.List;

import uma.wow.proyecto.*;


public interface GestionCuenta {
	
	public void creaCuenta(PooledAccount cuentaNueva, Individual c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void creaCuenta(PooledAccount cuentaNueva, Empresa c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void creaCuenta(Segregada cuentaNueva, Individual c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void creaCuenta(Segregada cuentaNueva, Empresa c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void cierraCuenta(Segregada c, Usuario admin) throws SaldoException, CuentaNoEncontrada, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public void cierraCuenta(PooledAccount c, Usuario admin) throws SaldoException, CuentaNoEncontrada, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	public PooledAccount devolverPooled(String identificacion) throws CuentaNoEncontrada;
	public Segregada devolverSegregada(String identificacion) throws CuentaNoEncontrada;
	public List<Segregada> devolverSegregadasDeIndividual(String id) throws ClienteNoEncontrado;
	public List<Segregada> devolverSegregadasDeAutorizado(String id) throws PersonaAutorizadaNoEncontrada;

}