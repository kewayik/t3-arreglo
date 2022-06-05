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

/* 	La aplicación permitirá a un administrativo la apertura de una cuenta y asociarla a un cliente. 
 * La cuenta podrá ser agrupada (pooled) o segregada (segregated). En ambos casos la(s) cuenta(s) 
 * externa(s) asociada(s) se añade(n) como información, no se hace nada más. Será necesario que 
 * haya más de una cuenta externa en el caso de una cuenta agrupada con varias divisas.
*/

public class RF5Pr {
	
	private static final Logger LOG = Logger.getLogger(RF5Pr.class.getCanonicalName());
	
	private static final String CUENTA_EJB = "java:global/classes/CuentaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionCuenta gestionCuenta;
	
	@Before
	public void setup() throws NamingException{
		gestionCuenta = (GestionCuenta) SuiteTest.ctx.lookup(CUENTA_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	/*
	 * Vamos a abrir un tipo 'pooled(Individual)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al no haber ningun tipo de error, no salta ninguna exception
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaPooledIndividualCorrecto(){
		
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
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("1422224555528");
		pooled.setSwift("4892");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(individual);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-02-12"));
		pooled.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(pooled, individual, administrador);		
			
		}catch(CuentaEncontrada e) {
			fail("No debería saltar error, la cuenta es diferente");
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, cliente ya creado");		
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	/*
	 * Vamos a abrir un tipo 'pooled(Individual)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al haber una cuenta ya creada, salta la exception 'CuentaEncontrada'
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaPooledIndividualIncorrecto(){
		
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
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("1453134534528");
		pooled.setSwift("4512");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(individual);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-09-12"));
		pooled.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(pooled, individual, administrador);		
			
		}catch(CuentaEncontrada e) {
			//OK
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, cliente ya creado");		
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	/*
	 * Vamos a abrir un tipo 'pooled(Empresa)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al no haber ningun tipo de error, no salta ninguna exception
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaPooledEmpresaCorrecto() {
		
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
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("1422224555528");
		pooled.setSwift("4892");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(empresa);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-02-12"));
		pooled.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(pooled, empresa, administrador);		
			
		}catch(CuentaEncontrada e) {
			fail("No debería saltar error, la cuenta es diferente");
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, cliente ya creado");		
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
	}
	/*
	 * Vamos a abrir un tipo 'pooled(Empresa)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al noo haber un usuario con esa id salta la exception 'ClienteNoEncontrado'
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaPooledEmpresaIncorrecto() {
		
		Empresa empresa = new Empresa();
		empresa.setId("98558774526");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta("2021-07-16");
		empresa.setDireccion("Calle Vavia");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("29099");
		empresa.setPais("España");
		empresa.setRazon_Social("Ayudas");
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("1422224555528");
		pooled.setSwift("4892");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(empresa);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-02-12"));
		pooled.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(pooled, empresa, administrador);		
			
		}catch(CuentaEncontrada e) {
			fail("No debería saltar error, la cuenta es diferente");
		}catch(ClienteNoEncontrado e) {
			//OK	
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
	}
	/*
	 * Vamos a abrir un tipo 'segregada(Individual)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al no haber ningun tipo de error, no salta ninguna exception 
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaSegregadaIndividualCorrecto(){
		
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
		
		Segregada segregada = new Segregada();
		segregada.setIban("134538888");
		segregada.setSwift("6582");
		segregada.setClasificacion("SEGREGADA");
		segregada.setCliente(individual);
		segregada.setEstado("ABIERTA");
		segregada.setFechaApertura(Date.valueOf("2020-05-27"));
		segregada.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(segregada, individual, administrador);		
			
		}catch(CuentaEncontrada e) {
			fail("No debería saltar error, la cuenta es diferente");
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, cliente ya creado");		
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	/*
	 * Vamos a abrir un tipo 'segregada(Individual)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al haber una cuenta ya creada salta la exception 'CuentraEncontrada'
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaSegregadaIndividualIncorrecto(){
		
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
		
		Segregada segregada = new Segregada();
		segregada.setIban("1538888");
		segregada.setSwift("4582");
		segregada.setClasificacion("SEGREGADA");
		segregada.setCliente(individual);
		segregada.setEstado("ABIERTA");
		segregada.setFechaApertura(Date.valueOf("2020-05-27"));
		segregada.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(segregada, individual, administrador);		
			
		}catch(CuentaEncontrada e) {
			//OK
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, cliente ya creado");		
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	/*
	 * Vamos a abrir un tipo 'segregada(Empresa)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al no haber ningun tipo de error, no salta ninguna exception
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaIndividualEmpresaCorrecto() {
		
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
		
		Segregada segregada = new Segregada();
		segregada.setIban("1888588888");
		segregada.setSwift("78962");
		segregada.setClasificacion("SEGREGADA");
		segregada.setCliente(empresa);
		segregada.setEstado("ABIERTA");
		segregada.setFechaApertura(Date.valueOf("2020-05-27"));
		segregada.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(segregada, empresa, administrador);		
			
		}catch(CuentaEncontrada e) {
			fail("No debería saltar error, la cuenta es diferente");
		}catch(ClienteNoEncontrado e) {
			fail("No debería saltar error, cliente ya creado");		
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
	}
	/*
	 * Vamos a abrir un tipo 'segregada(Empresa)', para ello lo creamos y llamamos al metodo de 'creaCuenta' y 
     al no haber ningun cliente en la bsa de datos, salta la exception 'ClienteNoEncontrado'
	 */
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaSegregadaEmpresaIncorrecto() {
		
		Empresa empresa = new Empresa();
		empresa.setId("9852854756");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta("2021-07-16");
		empresa.setDireccion("Calle Bolsa");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("29099");
		empresa.setPais("España");
		empresa.setRazon_Social("Redes");
		
		Segregada segregada = new Segregada();
		segregada.setIban("596354538888");
		segregada.setSwift("4962");
		segregada.setClasificacion("SEGREGADA");
		segregada.setCliente(empresa);
		segregada.setEstado("ABIERTA");
		segregada.setFechaApertura(Date.valueOf("2020-05-27"));
		segregada.setFechaCierre(null);
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		try {			
			gestionCuenta.creaCuenta(segregada, empresa, administrador);		
			
		}catch(CuentaEncontrada e) {
			fail("No debería saltar error, la cuenta es diferente");
		}catch(ClienteNoEncontrado e) {
			//OK	
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
	}
	
}
