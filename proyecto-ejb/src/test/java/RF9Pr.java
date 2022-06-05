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

/*	La aplicación permitirá a un administrativo cerrar una cuenta bancaria. 
 * Solo se puede cerrar una cuenta que tenga saldo 0 (en todas sus divisas). 
 * Una cuenta cerrada no se elimina, por si es necesario reportarla en algún informe.
*/

public class RF9Pr {
	
private static final Logger LOG = Logger.getLogger(RF9Pr.class.getCanonicalName());
	
	private static final String CUENTA_EJB = "java:global/classes/CuentaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "WOWEJBTest";
	
	private GestionCuenta gestionCuenta;
	
	@Before
	public void setup() throws NamingException{
		gestionCuenta = (GestionCuenta) SuiteTest.ctx.lookup(CUENTA_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
		
	}
	//Comprobamos que las cuentas son cerradas correctamente
	@Requisitos({"RF9"})
	@Test
	public void testCerrarCuentaCorrecto(){
		
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
		
		CuentaReferencia cuentaVacia = new CuentaReferencia();
		cuentaVacia.setIban("538888");
		cuentaVacia.setEstado(null);
		cuentaVacia.setSucursal(null);
		cuentaVacia.setFechaApertura(null);
		cuentaVacia.setPais(null);
		cuentaVacia.setSwift("482");
		cuentaVacia.setSaldo(0);
		
		Segregada segregadaVacia = new Segregada();
		segregadaVacia.setIban("88888");
		segregadaVacia.setSwift("4582");
		segregadaVacia.setClasificacion("SEGREGADA");
		segregadaVacia.setCliente(individual);
		segregadaVacia.setEstado("ABIERTA");
		segregadaVacia.setFechaApertura(Date.valueOf("2020-05-27"));
		segregadaVacia.setFechaCierre(null);
		segregadaVacia.setCuentaReferencia(cuentaVacia);
		
		CuentaReferencia cuentaVacia2 = new CuentaReferencia();
		cuentaVacia.setIban("723888");
		cuentaVacia.setNombreBanco("Capo");
		cuentaVacia.setEstado(null);
		cuentaVacia.setSucursal(null);
		cuentaVacia.setFechaApertura(null);
		cuentaVacia.setPais(null);
		cuentaVacia.setSwift("482");
		cuentaVacia.setSaldo(0);
		
		PooledAccount pooledVacia = new PooledAccount();
		pooledVacia.setIban("1453134534528");
		pooledVacia.setSwift("4512");
		pooledVacia.setClasificacion("POOLED");
		pooledVacia.setCliente(individual);
		pooledVacia.setDepositaEn(null);
		pooledVacia.setEstado("ABIERTA");
		pooledVacia.setFechaApertura(Date.valueOf("2020-09-12"));
		pooledVacia.setFechaCierre(null);
		
		
		DepositaEnPK pk = new DepositaEnPK();
		pk.setCuentaReferenciaIban(cuentaVacia2.getIban());
		pk.setPooledAccountIban(pooledVacia.getIban());
		
		DepositadaEn dep = new DepositadaEn();
		dep.setId(pk);
		dep.setId2(pooledVacia);
		dep.setId1(cuentaVacia2);
		dep.setSaldo(cuentaVacia2.getSaldo());
		
		List<DepositadaEn> lista = new ArrayList<DepositadaEn>();
		lista.add(dep);
		pooledVacia.setDepositaEn(lista);
		
		
		
		try {			
			gestionCuenta.cierraCuenta(pooledVacia,administrador);	
			gestionCuenta.cierraCuenta(segregadaVacia,administrador);
			CuentaFintech cuenta1 = gestionCuenta.devolverPooled(pooledVacia.getIban());
			CuentaFintech cuenta2 = gestionCuenta.devolverSegregada(segregadaVacia.getIban());
			assertEquals(cuenta1.getEstado(),"BAJA");
			assertEquals(cuenta2.getEstado(),"BAJA");
			
		}catch(CuentaNoEncontrada e) {			
			fail("No debería saltar error, la cuenta está a 0");
		}catch(SaldoException e) {
			fail("No debería saltar error, la cuenta está a 0");			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}
		
	}	
	//Comprobamos que si existe salndo en las cuentas estas no son borradas
	@Requisitos({"RF9"})
	@Test
	public void testCerrarCuentaIncorrecto(){
		
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
		
		CuentaReferencia cuentaLlena = new CuentaReferencia();
		cuentaLlena.setIban("9999");
		cuentaLlena.setSwift("4812");
		cuentaLlena.setSaldo(1000000);
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("1453134534528");
		pooled.setSwift("4512");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(individual);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-09-12"));
		pooled.setFechaCierre(null);
		
		
		DepositadaEn dep = new DepositadaEn();
		dep.setId2(pooled);
		dep.setId1(cuentaLlena);
		dep.setSaldo(cuentaLlena.getSaldo());
		
		List<DepositadaEn> lista = new ArrayList<DepositadaEn>();
		lista.add(dep);
		pooled.setDepositaEn(lista);
		
		Segregada segregada = new Segregada();
		segregada.setIban("1888134538888");
		segregada.setSwift("4582");
		segregada.setClasificacion("SEGREGADA");
		segregada.setCliente(individual);
		segregada.setEstado("ABIERTA");
		segregada.setFechaApertura(Date.valueOf("2020-05-27"));
		segregada.setFechaCierre(null);
		segregada.setCuentaReferencia(cuentaLlena);
		
		try {			
			gestionCuenta.cierraCuenta(pooled,administrador);	
			gestionCuenta.cierraCuenta(segregada,administrador);	
		}catch(CuentaNoEncontrada e) {			
			fail("No debería saltar error, la cuenta está a 0");
		}catch(SaldoException e) {
			//OK			
		}catch(EJBException e) {
			fail("Excepción no controlada");
		}	
	}
}
