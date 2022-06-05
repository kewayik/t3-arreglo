import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.ejb.*;

/*	La aplicación permitirá a un administrativo añadir personas autorizadas a las 
 * cuentas que pertenezcan a cliente que son personas jurídicas. Las personas 
 * autorizadas serán las que podrán entrar en la aplicación para realizar operaciones
 * con la cuenta.
*/

public class RF6Pr {
	
private static final Logger LOG = Logger.getLogger(RF6Pr.class.getCanonicalName());
	
	private static final String PERSONA_AUTORIZADA_EJB = "java:global/classes/PersonaAutorizadaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionPersonaAutorizada gestionPersonaAutorizada;
	
	@Before
	public void setup() throws NamingException{
		gestionPersonaAutorizada = (GestionPersonaAutorizada) SuiteTest.ctx.lookup(PERSONA_AUTORIZADA_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	//Comprobamos que las personas autorizadas son añadidas correctamente a la base de datos
	@Requisitos({"RF6"})
	@Test
	public void testAnyadirPersonaAutorizadaCorrecto(){
		
		Usuario usuarioEmpresa = new Usuario ();
		usuarioEmpresa.setNombreUsuario("Carniceria Paco");
		usuarioEmpresa.setPassword("vivalacomida");
		usuarioEmpresa.setTipo("NORMAL");
		
		Empresa empresaParaUsuario = new Empresa();
		empresaParaUsuario.setId("55555555");
		empresaParaUsuario.setTipoCliente("JURIDICO");
		empresaParaUsuario.setEstado("ACTIVO");
		empresaParaUsuario.setFechaAlta("2021-07-16");
		empresaParaUsuario.setDireccion("Calle Pamplona");
		empresaParaUsuario.setCiudad("Madrid");
		empresaParaUsuario.setCodigoPostal("27009");
		empresaParaUsuario.setPais("España");
		empresaParaUsuario.setRazon_Social("Comida");
		empresaParaUsuario.setUsuario(usuarioEmpresa);
		usuarioEmpresa.setCliente(empresaParaUsuario);
		
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

		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("16554");
		pooled.setSwift("4512");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(empresaParaUsuario);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-09-12"));
		pooled.setFechaCierre(null);
		
		
		try {			
					
			gestionPersonaAutorizada.anyadirPersonaAutorizada(pooled, personaAutorizadaBaja,usuarioEmpresa, "OPERAR");
			PersonaAutorizada aux = gestionPersonaAutorizada.devolver(personaAutorizadaBaja.getId());
			
			assertEquals(aux.getId(),personaAutorizadaBaja.getId());

		}catch(PersonaAutorizadaEncontrada e) {
			fail("No debería saltar error, la persona aun no esta autorizada");
			
		}catch(PersonaAutorizadaNoEncontrada e) {
			//ok la persona no esta autorizada todavia
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
	//Comprobamos la excepcion cuenta no encontrada a la hora de crear una persona autorizada
	@Requisitos({"RF6"})
	@Test
	public void testAnyadirPersonaAutorizadaIncorrecto(){
		
		
			
		Usuario usuarioEmpresa = new Usuario ();
		usuarioEmpresa.setNombreUsuario("Carniceria Paco");
		usuarioEmpresa.setPassword("vivalacomida");
		usuarioEmpresa.setTipo("NORMAL");
		
		Empresa empresaParaUsuario = new Empresa();
		empresaParaUsuario.setId("55555555");
		empresaParaUsuario.setTipoCliente("JURIDICO");
		empresaParaUsuario.setEstado("ACTIVO");
		empresaParaUsuario.setFechaAlta("2021-07-16");
		empresaParaUsuario.setDireccion("Calle Pamplona");
		empresaParaUsuario.setCiudad("Madrid");
		empresaParaUsuario.setCodigoPostal("27009");
		empresaParaUsuario.setPais("España");
		empresaParaUsuario.setRazon_Social("Comida");
		empresaParaUsuario.setUsuario(usuarioEmpresa);
		usuarioEmpresa.setCliente(empresaParaUsuario);
		
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

		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("167896554");
		pooled.setSwift("4512");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(empresaParaUsuario);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-09-12"));
		pooled.setFechaCierre(null);
		
		
		try {			
					
			gestionPersonaAutorizada.anyadirPersonaAutorizada(pooled, personaAutorizadaBaja,usuarioEmpresa, "OPERAR");
			PersonaAutorizada aux = gestionPersonaAutorizada.devolver(personaAutorizadaBaja.getId());
			
			assertEquals(aux.getId(),personaAutorizadaBaja.getId());

		}catch(PersonaAutorizadaEncontrada e) {
			fail("No debería saltar error, la persona aun no esta autorizada");
			
		}catch(PersonaAutorizadaNoEncontrada e) {
			//ok la persona no esta autorizada todavia
		}catch(ClienteNoEncontrado e) {
			fail("Cliente no encontrado");
		}catch(CuentaNoEncontrada e) {
			//OK
		}catch(UsuarioNoEncontrado e) {
			fail("Usuario no encontrado");
		}catch(NoEsEmpresaException e) {
			fail("Usuario no es empresa");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
		
	}	
	
}
