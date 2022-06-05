import static org.junit.Assert.fail;

import java.sql.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.ejb.*;

public class RF10Pr {
	
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String ACCESO_EJB = "java:global/classes/AccesoEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionAcceso gestionAcceso;
	
	@Before
	public void setup() throws NamingException{
		gestionAcceso = (GestionAcceso) SuiteTest.ctx.lookup(ACCESO_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	//Comprobamos que los usuarios se logean correctamente
	@Requisitos({"RF10"})
	@Test
	public void testAccesoUsuario() {
		
		Usuario usuarioIndividual = new Usuario ();
		usuarioIndividual.setNombreUsuario("Peter");
		usuarioIndividual.setPassword("1234");
		usuarioIndividual.setTipo("NORMAL");
		
		Individual individualParaUsuario = new Individual();
		individualParaUsuario.setId("9999999");
		individualParaUsuario.setTipoCliente("FISICA");
		individualParaUsuario.setEstado("ACTIVO");
		individualParaUsuario.setFechaAlta("2021-03-14");
		individualParaUsuario.setDireccion("Avenida Cos");
		individualParaUsuario.setCiudad("Malaga");
		individualParaUsuario.setCodigoPostal("2901");
		individualParaUsuario.setPais("España");
		individualParaUsuario.setNombre("Jamal");
		individualParaUsuario.setApellido("Peterh");
		individualParaUsuario.setFecha_nacimiento(null);
		individualParaUsuario.setUsuario(usuarioIndividual);
		usuarioIndividual.setCliente(individualParaUsuario);
		
		
		try {
			gestionAcceso.loginCliente(usuarioIndividual);
		}catch(UsuarioNoEncontrado e) {
			fail("El usuario está creado, debería encontrarse");
		}catch(ContraseniaInvalida e) {
			fail("La contraseña es correcta");
		}catch(EsEmpresaException e) {
			fail("Es un Invividual");
		}catch(CuentaBloqueada e) {
			fail("La cuenta no está bloqueada");
		}catch(CuentaDeBaja e) {
			fail("La cuenta está activa");
		}catch(ClienteNoEncontrado e) {
			fail("El cliente está creado");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	//Comprobamos que salta un error si introducimos una contraseña incorrecta
	@Requisitos({"RF10"})
	@Test
	public void testAccesoUsuarioContraseñaErronea() {
		
		Usuario usuarioIndividual = new Usuario ();
		usuarioIndividual.setNombreUsuario("Peter");
		usuarioIndividual.setPassword("17877774");
		usuarioIndividual.setTipo("NORMAL");
		
		Individual individualParaUsuario = new Individual();
		individualParaUsuario.setId("9999999");
		individualParaUsuario.setTipoCliente("FISICA");
		individualParaUsuario.setEstado("ACTIVO");
		individualParaUsuario.setFechaAlta("2021-03-14");
		individualParaUsuario.setDireccion("Avenida Cos");
		individualParaUsuario.setCiudad("Malaga");
		individualParaUsuario.setCodigoPostal("2901");
		individualParaUsuario.setPais("España");
		individualParaUsuario.setNombre("Jamal");
		individualParaUsuario.setApellido("Peterh");
		individualParaUsuario.setFecha_nacimiento(null);
		individualParaUsuario.setUsuario(usuarioIndividual);
		usuarioIndividual.setCliente(individualParaUsuario);
		
		
		try {
			gestionAcceso.loginCliente(usuarioIndividual);
		}catch(UsuarioNoEncontrado e) {
			fail("El usuario está creado, debería encontrarse");
		}catch(ContraseniaInvalida e) {
			//OK
		}catch(EsEmpresaException e) {
			fail("Es un Invividual");
		}catch(CuentaBloqueada e) {
			fail("La cuenta no está bloqueada");
		}catch(CuentaDeBaja e) {
			fail("La cuenta está activa");
		}catch(ClienteNoEncontrado e) {
			fail("El cliente está creado");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
	//Comprobamos que los usuarios empresa pueden logearse sin problemas
	@Requisitos({"RF10"})
	@Test
	public void testAccesoUsuarioEmpresa() {
		
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
		
		
		try {
			gestionAcceso.loginCliente(usuarioEmpresa);
		}catch(UsuarioNoEncontrado e) {
			fail("El usuario está creado, debería encontrarse");
		}catch(ContraseniaInvalida e) {
			fail("La contraseña es correcta");
		}catch(EsEmpresaException e) {
			//OK
		}catch(CuentaBloqueada e) {
			fail("La cuenta no está bloqueada");
		}catch(CuentaDeBaja e) {
			fail("La cuenta está activa");
		}catch(ClienteNoEncontrado e) {
			fail("El cliente está creado");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}
}
