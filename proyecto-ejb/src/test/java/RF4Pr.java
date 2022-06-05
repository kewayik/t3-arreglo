import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.ejb.*;

public class RF4Pr {
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionCliente gestionCliente;
	
	@Before
	public void setup() throws NamingException{
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	/*
	 * Vamos a dar de baja un tipo 'Individual', para ello lo creamos y llamamos al metodo de 'bajaCliente' y 
     al estar todo correcto, fuinciona sin problemas
	 */
	@Requisitos({"RF4"})
	@Test
	public void testDarBajaIndividualCorrecto() {
		
		Usuario usuario = new Usuario ();
		usuario.setNombreUsuario("Carlos");
		usuario.setPassword("1234");
		usuario.setTipo("NORMAL");
		
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
		individual.setUsuario(usuario);
		List<CuentaFintech> lista = new ArrayList<CuentaFintech>();
		individual.setCuentas(lista);		
		usuario.setCliente(individual);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {
			gestionCliente.bajaCliente(individual, administrador);
			Cliente cliente = gestionCliente.devolverIndividual(individual.getId());
			assertEquals(cliente.getEstado(),"BAJA");
		}catch(CuentasActivas e) {
			fail("No debería saltar error, el cliente no tiene cuentas activas");
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, el cliente está creado");			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	
	/*
	 * Vamos a dar de baja un tipo 'Empresa', para ello lo creamos y llamamos al metodo de 'bajaCliente' y 
     al estar todo correcto, fuinciona sin problemas
	 */
	
	@Requisitos({"RF4"})
	@Test
	public void testDarBajaEmpresaCorrecto() {
		
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
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {
			gestionCliente.bajaCliente(empresa, administrador);
		}catch(CuentasActivas e) {
			fail("No debería saltar error, el cliente no tiene cuentas activas");
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, el cliente está creado");			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}		
	}
	
	/*
	 * Vamos a dar de baja un tipo 'Empresa', para ello lo creamos y llamamos al metodo de 'bajaCliente' y 
     al no haber ningun tipo empresa con esa misma ID en la base de datos, salta la exception 'ClienteNoEncontrado'
	 */
	@Requisitos({"RF4"})
	@Test
	public void testDarBajaEmpresaClienteNoEncontrado() {
		
		Empresa empresa = new Empresa();
		empresa.setId("63554");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta("2021-07-16");
		empresa.setDireccion("Calle Alemania");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("29059");
		empresa.setPais("España");
		empresa.setRazon_Social("Agua");
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {
			gestionCliente.bajaCliente(empresa, administrador);
		}catch(CuentasActivas e) {
			fail("No debería saltar error, el cliente no tiene cuentas activas");
		}catch(ClienteNoEncontrado e) {
			//OK			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}		
	}
	
	/*
	 * Vamos a dar de baja un tipo 'Individual', para ello lo creamos y llamamos al metodo de 'bajaCliente' y 
     al no haber ningun tipo empresa con esa misma ID en la base de datos, salta la exception 'ClienteNoEncontrado'
	 */
	
	@Requisitos({"RF4"})
	@Test
	public void testDarBajaIndividualClienteNoEncontrado() {
		
		Individual individual = new Individual();
		individual.setId("452757");
		individual.setTipoCliente("FISICA");
		individual.setEstado("ACTIVO");
		individual.setFechaAlta("2021-03-14");
		individual.setDireccion("Avenida Puerta");
		individual.setCiudad("Malaga");
		individual.setCodigoPostal("29071");
		individual.setPais("España");
		individual.setNombre("Arturo");
		individual.setApellido("Martinez");
		individual.setFecha_nacimiento(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {
			gestionCliente.bajaCliente(individual, administrador);
		}catch(CuentasActivas e) {
			fail("No debería saltar error, el cliente no tiene cuentas activas");
		}catch(ClienteNoEncontrado e) {
			//OK			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
}
