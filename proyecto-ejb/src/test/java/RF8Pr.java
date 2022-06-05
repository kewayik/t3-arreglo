import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.ejb.*;

/*	La aplicación permitirá a un administrativo dar de baja a personas autorizadas 
 * a operar con cuentas cuyos clientes sean personas jurídicas. Estas personas no 
 * se eliminan del sistema, ya que podría ser necesario que la información conste 
 * para alguna auditoría o informe. Una persona autorizada que esté de baja no 
 * puede acceder a la cuenta en la que se encontraba autorizada.
*/

public class RF8Pr {
	
private static final Logger LOG = Logger.getLogger(RF6Pr.class.getCanonicalName());
	
	private static final String PERSONA_AUTORIZADA_EJB = "java:global/classes/PersonaAutorizadaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionPersonaAutorizada gestionPersonaAutorizada;
	
	@Before
	public void setup() throws NamingException{
		gestionPersonaAutorizada = (GestionPersonaAutorizada) SuiteTest.ctx.lookup(PERSONA_AUTORIZADA_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	
	//Comprobamos que las personas autorizadas se pueden dar de baja correctamente
	@Requisitos({"RF8"})
	@Test
	public void testBajaPersonaAutorizadaCorrecto(){
		
		Usuario usuarioPerAut = new Usuario ();
		usuarioPerAut.setNombreUsuario("JoseManuel");
		usuarioPerAut.setPassword("1234");
		usuarioPerAut.setTipo("NORMAL");
		
		PersonaAutorizada personaAutorizadaBaja = new PersonaAutorizada();
		personaAutorizadaBaja.setId("511155");
		personaAutorizadaBaja.setNombre("Pepe");
		personaAutorizadaBaja.setApellidos("Pelaez");
		personaAutorizadaBaja.setFechaNacimiento(null);
		personaAutorizadaBaja.setEstado("ACTIVO");
		personaAutorizadaBaja.setDireccion("Avda S");		
		personaAutorizadaBaja.setFechaInicio(null);
		personaAutorizadaBaja.setFechaFin(null);
		personaAutorizadaBaja.setAutori(null);		
		personaAutorizadaBaja.setUsuario(usuarioPerAut);
		usuarioPerAut.setPersonaAutorizada(personaAutorizadaBaja);
		
		try {			
			gestionPersonaAutorizada.borraPersonaAutorizada(personaAutorizadaBaja, usuarioPerAut);	
			PersonaAutorizada aux = gestionPersonaAutorizada.devolver(personaAutorizadaBaja.getId());
			
			assertEquals(aux.getId(),personaAutorizadaBaja.getId());
			
		}catch(PersonaAutorizadaEncontrada e) {
			//ok
			
		}catch(PersonaAutorizadaNoEncontrada e) {
			fail("Persona Autorizada no encontrada");
		}catch(ClienteNoEncontrado e) {
			fail("Cliente no encontrado");
		}catch(CuentaNoEncontrada e) {
			fail("Cuenta no encontrada");
		}catch(UsuarioNoEncontrado e) {
			fail("Usuario no encontrado");
		}catch(NoEsEmpresaException e) {
			fail("Usuario no es empresa");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}	
	//Comprobamos que si la persona autorizada no está en la base de datos salta una excepcion
	@Requisitos({"RF8"})
	@Test
	public void testBajaPersonaAutorizadaIncorrecto(){
		
		Usuario usuarioPerAut = new Usuario ();
		usuarioPerAut.setNombreUsuario("JoseManuel");
		usuarioPerAut.setPassword("1234");
		usuarioPerAut.setTipo("NORMAL");
		
		PersonaAutorizada personaAutorizadaBaja = new PersonaAutorizada();
		personaAutorizadaBaja.setId("51112");
		personaAutorizadaBaja.setNombre("Pepe");
		personaAutorizadaBaja.setApellidos("Pelaez");
		personaAutorizadaBaja.setFechaNacimiento(null);
		personaAutorizadaBaja.setEstado("ACTIVO");
		personaAutorizadaBaja.setDireccion("Avda S");		
		personaAutorizadaBaja.setFechaInicio(null);
		personaAutorizadaBaja.setFechaFin(null);
		personaAutorizadaBaja.setAutori(null);		
		personaAutorizadaBaja.setUsuario(usuarioPerAut);
		usuarioPerAut.setPersonaAutorizada(personaAutorizadaBaja);
		
		try {			
			gestionPersonaAutorizada.borraPersonaAutorizada(personaAutorizadaBaja, usuarioPerAut);	
			PersonaAutorizada aux = gestionPersonaAutorizada.devolver(personaAutorizadaBaja.getId());
			
			assertEquals(aux.getId(),personaAutorizadaBaja.getId());
			
		}catch(PersonaAutorizadaEncontrada e) {
			
			fail("Persona Autorizada encontrada");
		}catch(PersonaAutorizadaNoEncontrada e) {
			//ok
		}catch(ClienteNoEncontrado e) {
			fail("Cliente no encontrado");
		}catch(CuentaNoEncontrada e) {
			fail("Cuenta no encontrada");
		}catch(UsuarioNoEncontrado e) {
			fail("Usuario no encontrado");
		}catch(NoEsEmpresaException e) {
			fail("Usuario no es empresa");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
	
	}	
	
}
