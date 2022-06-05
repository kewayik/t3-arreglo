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
public class RF7Pr {
	
	
private static final Logger LOG = Logger.getLogger(RF2Pr.class.getCanonicalName());
	
	private static final String PERSONA_AUTORIZADA_EJB = "java:global/classes/PersonaAutorizadaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionPersonaAutorizada gestionAutorizada;
	
	
	@Before
	public void setup() throws NamingException{
		gestionAutorizada = (GestionPersonaAutorizada) SuiteTest.ctx.lookup(PERSONA_AUTORIZADA_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	//Comprobamos que los datos de una persona autorizada son modificados correctamente
	@Requisitos({"RF7"})
	@Test
	public void testModificaAutorizado() throws PersonaAutorizadaNoEncontrada, ContraseniaInvalida, UsuarioNoEncontrado, NoAdministradorException{
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
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
				
			gestionAutorizada.modificaPersonaAutorizada(personaAutorizadaBaja, administrador);
			PersonaAutorizada persA = gestionAutorizada.devolver(personaAutorizadaBaja.getId());
			
			assertEquals(personaAutorizadaBaja.getApellidos(),persA.getApellidos());
			
		}catch(PersonaAutorizadaNoEncontrada e) {
			fail("Persona Autorizada no encontrada");
		}catch(EJBException e) {
			fail("Excepcion no controlada");
		}
		
		
	}

}
