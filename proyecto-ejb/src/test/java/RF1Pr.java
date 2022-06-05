import static org.junit.Assert.fail;

import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.ejb.*;


public class RF1Pr {
	
	
	private static final Logger LOG = Logger.getLogger(RF1Pr.class.getCanonicalName());
	
	private static final String ACCESO_EJB = "java:global/classes/AccesoEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionAcceso gestionAcceso;
	
	@Before
	public void setup() throws NamingException{
		gestionAcceso = (GestionAcceso) SuiteTest.ctx.lookup(ACCESO_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	
	//comprobamos que el administrador tiene acceso de manera correcta
	@Requisitos({"RF1"})
	@Test
	public void testComprobarAdministradorCorrecto() throws NoAdministradorException{
		
		Usuario u1 = new Usuario();
		u1.setNombreUsuario("Alvaro");
		u1.setPassword("perro");
		u1.setTipo("ADMIN");
		
		try {
			gestionAcceso.loginAdministrador(u1);
		}catch(NoAdministradorException e) {
			fail("No debería saltar error, el usuario es admin");
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
				
	}
	
	
	//comprobamos que la exception 'NoAdministradorExcption' funciona ya que la password es incorrecta
	@Requisitos({"RF1"})
	@Test
	public void testComprobarAdministradorIncorrecto() throws NoAdministradorException{
		
		Usuario u2 = new Usuario ();
		u2.setNombreUsuario("Carlos");
		u2.setPassword("1234");
		u2.setTipo("NORMAL");
		
		
		try {
			gestionAcceso.loginAdministrador(u2);
		}catch(NoAdministradorException e) {
			//OK
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
				
	}
	
	
	
}
