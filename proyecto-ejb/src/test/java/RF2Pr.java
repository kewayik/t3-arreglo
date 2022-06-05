import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.ejb.*;

public class RF2Pr {
	
private static final Logger LOG = Logger.getLogger(RF2Pr.class.getCanonicalName());
	
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String ACCESO_EJB = "java:global/classes/AccesoEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionCliente gestionCliente;
	private GestionAcceso gestionAcceso;
	
	@Before
	public void setup() throws NamingException{
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
		gestionAcceso = (GestionAcceso) SuiteTest.ctx.lookup(ACCESO_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	
	//Vamos a dar de alta a un tipo 'Individual', para ello lo creamos y llamamos al metodo de 'altaCliente'
	@Requisitos({"RF2"})
	@Test
	public void testDarAltaIndividualCorrecto(){
		
		Individual individual = new Individual();
		individual.setId("12345");
		individual.setTipoCliente("FISICA");
		individual.setEstado("ACTIVO");
		individual.setFechaAlta("2021-03-14");
		individual.setDireccion("Avenida Casa");
		individual.setCiudad("Barcelona");
		individual.setCodigoPostal("29000");
		individual.setPais("España");
		individual.setNombre("Pepe");
		individual.setApellido("Jose");
		individual.setFecha_nacimiento(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			
			gestionCliente.altaCliente(individual, administrador);		
			
		}catch(ClienteYaExistente e) {
			fail("No debería saltar error, el cliente es diferente");
			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}				
	}
	
	//Vamos a dar de alta a un tipo 'Empresa', para ello lo creamos y llamamos al metodo de 'altaCliente.'
	@Requisitos({"RF2"})
	@Test
	public void testDarAltaEmpresaCorrecto(){
		
		Empresa empresa = new Empresa();
		empresa.setId("35466");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta("2021-07-16");
		empresa.setDireccion("Calle Francia");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("29019");
		empresa.setPais("España");
		empresa.setRazon_Social("Anti-ayudas");
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			
			gestionCliente.altaCliente(empresa, administrador);		
			
		}catch(ClienteYaExistente e) {
			fail("No debería saltar error, el cliente es diferente");
			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}				
	}
	
	//Vamos a dar de alta a un tipo 'Individual', para ello lo creamos y llamamos al metodo de 'altaCliente.Como vemos funciona la exception 'ClienteYaExiste'
	@Requisitos({"RF2"})
	@Test
	public void testDarAltaIndividualIncorrecto(){
		
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
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			
			gestionCliente.altaCliente(individual, administrador);		
			
		}catch(ClienteYaExistente e) {
			//OK
			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}				
	}
	
	//Vamos a dar de alta a un tipo 'Empresa', para ello lo creamos y llamamos al metodo de 'altaCliente.Como vemos funciona la exception 'ClienteYaExiste'
	@Requisitos({"RF2"})
	@Test
	public void testDarAltaEmpresaIncorrecto(){
		
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
			
			gestionCliente.altaCliente(empresa, administrador);		
			
		}catch(ClienteYaExistente e) {
			//OK
			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}				
	}
}
