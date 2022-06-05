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

/*	La aplicación permitirá a un usuario administrativo bloquear a un cliente o 
 * autorizado de manera temporal (no es lo mismo que una baja). En el caso de que 
 * el cliente bloqueado sea una persona jurídica, sus autorizados no podrán operar 
 * con al cuenta de dicho cliente. Si esa cuenta es la única a la que tienen acceso, 
 * la persona autorizada tampoco podrá acceder a la aplicación. Si el cliente es 
 * una persona física, esta no podrá acceder a la aplicación. La aplicación también 
 * permitirá a los usuarios administrativos desbloquear a los usuarios.
*/

public class RF16Pr {
	
private static final Logger LOG = Logger.getLogger(RF16Pr.class.getCanonicalName());
	
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String PERSONA_AUTORIZADA_EJB = "java:global/classes/PersonaAutorizadaEJB";
	private static final String BLOQUEO_CLIENTE_EJB = "java:global/classes/BloqueoClienteEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionBloqueoCliente gestionBloqueoCliente;
	private GestionCliente gestionCliente;
	private GestionPersonaAutorizada gestionPersonaAutorizada;
	
	@Before
	public void setup() throws NamingException{
		gestionBloqueoCliente = (GestionBloqueoCliente) SuiteTest.ctx.lookup(BLOQUEO_CLIENTE_EJB);
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
		gestionPersonaAutorizada = (GestionPersonaAutorizada) SuiteTest.ctx.lookup(PERSONA_AUTORIZADA_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	
	//Para pruebo los 3 diferentes tipos de clientes en esta misma clase
	
	@Requisitos({"RF16"})
	@Test
	public void testBloquearClientesCorrecto(){
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		Individual individual = new Individual();
		individual.setId("654987");
		individual.setTipoCliente("FISICA");
		individual.setEstado("ACTIVO");
		individual.setFechaAlta("2021-03-14");
		individual.setDireccion("Avenida Correcaminos");
		individual.setCiudad("Malaga");
		individual.setCodigoPostal("29001");
		individual.setPais("España");
		individual.setNombre("Jammal");
		individual.setApellido("Hasbullah");
		individual.setFecha_nacimiento(null);
		
		Usuario usuarioEmpresa = new Usuario ();
		usuarioEmpresa.setNombreUsuario("Carniceria Paco");
		usuarioEmpresa.setPassword("vivalacomida");
		usuarioEmpresa.setTipo("NORMAL");
		
		Empresa empresa = new Empresa();
		empresa.setId("98756");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta("2021-07-16");
		empresa.setDireccion("Calle España");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("29009");
		empresa.setPais("España");
		empresa.setRazon_Social("Ayudas");
		empresa.setUsuario(usuarioEmpresa);
		
		
		PersonaAutorizada personaAutorizada = new PersonaAutorizada();
		personaAutorizada.setApellidos("Pelaez");
		personaAutorizada.setAutori(null);
		personaAutorizada.setDireccion("Avda S");
		personaAutorizada.setEstado("ACTIVO");
		personaAutorizada.setFechaInicio(Date.valueOf("2020-03-24"));
		personaAutorizada.setFechaFin(null);
		personaAutorizada.setId("511155");
		personaAutorizada.setNombre(usuarioEmpresa.getNombreUsuario());
		personaAutorizada.setUsuario(usuarioEmpresa);
		
		try {			
			gestionBloqueoCliente.bloqueoPersonaFisica(individual, administrador);

			gestionBloqueoCliente.bloqueoAutorizado(personaAutorizada, administrador);

			gestionBloqueoCliente.bloqueoEmpresa(empresa, administrador);

			assertEquals(gestionCliente.devolverIndividual(individual.getId()).getEstado(),"BLOQUEADO");
			assertEquals(gestionPersonaAutorizada.devolver(personaAutorizada.getId()).getEstado(),"BLOQUEADO");
			assertEquals(gestionCliente.devolverEmpresa(empresa.getId()).getEstado(),"BLOQUEADO");

			
		}catch(ClienteNoEncontrado e) {			
			fail("No debería saltar error, el cliente está en la BD");
		}catch(CuentaDeBaja e){
			fail("No debería dar error, ninguna cuenta está dada de baja");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	
	@Requisitos({"RF16"})
	@Test
	public void testBloquearClientesIncorrecto(){
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		Individual individual = new Individual();
		individual.setId("65165196157");
		individual.setTipoCliente("FISICA");
		individual.setEstado("ACTIVO");
		individual.setFechaAlta("2021-03-14");
		individual.setDireccion("Avenida Plato");
		individual.setCiudad("Malaga");
		individual.setCodigoPostal("29085");
		individual.setPais("España");
		individual.setNombre("Jammal");
		individual.setApellido("Hasbullah");
		individual.setFecha_nacimiento(null);
		
		Usuario usuarioEmpresa = new Usuario ();
		usuarioEmpresa.setNombreUsuario("Carniceria Paco");
		usuarioEmpresa.setPassword("vivalacomida");
		usuarioEmpresa.setTipo("NORMAL");
		
		Empresa empresa = new Empresa();
		empresa.setId("98756");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta("2021-07-16");
		empresa.setDireccion("Calle España");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("29009");
		empresa.setPais("España");
		empresa.setRazon_Social("Ayudas");
		empresa.setUsuario(usuarioEmpresa);
		
		
		PersonaAutorizada personaAutorizada = new PersonaAutorizada();
		personaAutorizada.setApellidos("Pelaez");
		personaAutorizada.setAutori(null);
		personaAutorizada.setDireccion("Avda S");
		personaAutorizada.setEstado(null);
		personaAutorizada.setFechaInicio(Date.valueOf("2020-03-24"));
		personaAutorizada.setFechaFin(null);
		personaAutorizada.setId("511155");
		personaAutorizada.setNombre(usuarioEmpresa.getNombreUsuario());
		personaAutorizada.setUsuario(usuarioEmpresa);
		
		try {			
			gestionBloqueoCliente.bloqueoPersonaFisica(individual, administrador);
			gestionBloqueoCliente.bloqueoAutorizado(personaAutorizada, administrador);
			gestionBloqueoCliente.bloqueoEmpresa(empresa, administrador);						
			
		}catch(ClienteNoEncontrado e) {			
			//OK
		}catch(CuentaDeBaja e){
			fail("No debería dar error, ninguna cuenta está dada de baja");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
}
