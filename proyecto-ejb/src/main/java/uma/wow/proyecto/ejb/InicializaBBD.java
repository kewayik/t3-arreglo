package uma.wow.proyecto.ejb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uma.wow.proyecto.Autorizacion;
import uma.wow.proyecto.AutorizacionPK;
import uma.wow.proyecto.CuentaFintech;
import uma.wow.proyecto.CuentaReferencia;
import uma.wow.proyecto.DepositaEnPK;
import uma.wow.proyecto.DepositadaEn;
import uma.wow.proyecto.Divisa;
import uma.wow.proyecto.Empresa;
import uma.wow.proyecto.Individual;
import uma.wow.proyecto.PersonaAutorizada;
import uma.wow.proyecto.PooledAccount;
import uma.wow.proyecto.Segregada;
import uma.wow.proyecto.Transaccion;
import uma.wow.proyecto.Usuario;

@Singleton
@Startup
public class InicializaBBD {
	
	@PersistenceContext(unitName="WOWEJB")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() {
		
		/*
		//Comprobamos que la BBDD no esté inicializada de antes
		Usuario comprobar = em.find(Usuario.class, "Alvaro");
		if(comprobar != null) {
			return;
		}
		
		Usuario administrador = new Usuario();
		administrador.setNombreUsuario("Alvaro");
		administrador.setPassword("perro");
		administrador.setTipo("ADMIN");
		
		em.persist(administrador);
		
		Usuario usuario = new Usuario ();
		usuario.setNombreUsuario("Carlos");
		usuario.setPassword("gato");
		usuario.setTipo("NORMAL");
		
	
		Individual individual = new Individual();
		individual.setId("654987");
		individual.setIdentificacion("8933533");
		individual.setTipoCliente("FISICA");
		individual.setEstado("ACTIVO");
		individual.setFechaAlta(Date.valueOf("2021-03-14"));
		individual.setFechaBaja(null);
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
		
		em.persist(usuario);
		em.persist(individual);
				
		Empresa empresa = new Empresa();
		empresa.setId("98756");
		empresa.setIdentificacion("45575233");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta(Date.valueOf("2021-07-16"));
		empresa.setFechaBaja(null);
		empresa.setDireccion("Calle España");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("29009");
		empresa.setPais("España");
		empresa.setRazon_Social("Ayudas");
		
		em.persist(empresa);
	
		
		CuentaReferencia cuentaLlena = new CuentaReferencia();
		cuentaLlena.setNombreBanco("Unicaja");
		cuentaLlena.setIban("9999");
		cuentaLlena.setSwift("4812");
		cuentaLlena.setSaldo(1000000);
		
		CuentaReferencia cuentaVacia = new CuentaReferencia();
		cuentaVacia.setIban("538888");
		cuentaVacia.setNombreBanco("Caixa");
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
		
		em.persist(cuentaVacia);
		em.persist(segregadaVacia);
		
		
		em.persist(cuentaLlena);
		
		CuentaReferencia cuentaVacia2 = new CuentaReferencia();
		cuentaVacia2.setIban("723888");
		cuentaVacia2.setNombreBanco("Capo");
		cuentaVacia2.setEstado(null);
		cuentaVacia2.setSucursal(null);
		cuentaVacia2.setFechaApertura(null);
		cuentaVacia2.setPais(null);
		cuentaVacia2.setSwift("482");
		cuentaVacia2.setSaldo(0);
		
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
		
		List<DepositadaEn> listado = new ArrayList<DepositadaEn>();
		listado.add(dep);
		pooledVacia.setDepositaEn(listado);
		
		em.persist(cuentaVacia2);
		em.persist(dep);
		em.persist(pooledVacia);
		
		Segregada segregada = new Segregada();
		segregada.setIban("1888134538888");
		segregada.setSwift("4582");
		segregada.setClasificacion("SEGREGADA");
		segregada.setCliente(individual);
		segregada.setEstado("ABIERTA");
		segregada.setFechaApertura(Date.valueOf("2020-05-27"));
		segregada.setFechaCierre(null);
		segregada.setCuentaReferencia(cuentaLlena);
		
		em.persist(segregada);
		
		Usuario usuarioPerAut = new Usuario ();
		usuarioPerAut.setNombreUsuario("JoseManuel");
		usuarioPerAut.setPassword("1234");
		usuarioPerAut.setTipo("NORMAL");
		
		PersonaAutorizada personaAutorizadaBaja = new PersonaAutorizada();
		personaAutorizadaBaja.setId("511155");
		personaAutorizadaBaja.setIdentificacion("0771");
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
		
		em.persist(usuarioPerAut);
		em.persist(personaAutorizadaBaja);
		
		Usuario usuarioIndividual = new Usuario ();
		usuarioIndividual.setNombreUsuario("Peter");
		usuarioIndividual.setPassword("1234");
		usuarioIndividual.setTipo("NORMAL");
		
		Individual individualParaUsuario = new Individual();
		individualParaUsuario.setId("9999999");
		individualParaUsuario.setIdentificacion("654987");
		individualParaUsuario.setTipoCliente("FISICA");
		individualParaUsuario.setEstado("ACTIVO");
		individualParaUsuario.setFechaAlta(Date.valueOf("2021-03-14"));
		individualParaUsuario.setFechaBaja(null);
		individualParaUsuario.setDireccion("Avenida Cos");
		individualParaUsuario.setCiudad("Malaga");
		individualParaUsuario.setCodigoPostal("2901");
		individualParaUsuario.setPais("España");
		individualParaUsuario.setNombre("Jamal");
		individualParaUsuario.setApellido("Peterh");
		individualParaUsuario.setFecha_nacimiento(null);
		
		usuarioIndividual.setCliente(individualParaUsuario);
		em.persist(usuarioIndividual);
		em.persist(individualParaUsuario);
		
		Usuario usuarioEmpresa = new Usuario ();
		usuarioEmpresa.setNombreUsuario("Carniceria Paco");
		usuarioEmpresa.setPassword("vivalacomida");
		usuarioEmpresa.setTipo("NORMAL");
		
		Empresa empresaParaUsuario = new Empresa();
		empresaParaUsuario.setId("55555555");
		empresaParaUsuario.setIdentificacion("789544");
		empresaParaUsuario.setTipoCliente("JURIDICO");
		empresaParaUsuario.setEstado("ACTIVO");
		empresaParaUsuario.setFechaAlta(Date.valueOf("2021-07-16"));
		empresaParaUsuario.setFechaBaja(null);
		empresaParaUsuario.setDireccion("Calle Pamplona");
		empresaParaUsuario.setCiudad("Madrid");
		empresaParaUsuario.setCodigoPostal("27009");
		empresaParaUsuario.setPais("España");
		empresaParaUsuario.setRazon_Social("Comida");
		empresaParaUsuario.setUsuario(usuarioEmpresa);
		usuarioEmpresa.setCliente(empresaParaUsuario);
				
		em.persist(usuarioEmpresa);
		em.persist(empresaParaUsuario);
		
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("16554");
		pooled.setSwift("4512");
		pooled.setClasificacion("POOLED");
		pooled.setCliente(empresaParaUsuario);
		pooled.setDepositaEn(null);
		pooled.setEstado("ABIERTA");
		pooled.setFechaApertura(Date.valueOf("2020-09-12"));
		pooled.setFechaCierre(null);
		
		
		DepositadaEn dep1 = new DepositadaEn();
		dep1.setId(pk);
		dep1.setId2(pooled);
		dep1.setId1(cuentaLlena);
		dep1.setSaldo(cuentaLlena.getSaldo());
		
		List<DepositadaEn> listaa = new ArrayList<DepositadaEn>();
		listaa.add(dep1);
		pooled.setDepositaEn(listaa);
		
		em.persist(pooled);
		em.persist(dep1);
		
		//************************************************
		Usuario userTrans = new Usuario ();
		userTrans.setNombreUsuario("Leopoldo");
		userTrans.setPassword("1124");
		userTrans.setTipo("NORMAL");
		
		PersonaAutorizada personaAutorizadaTrans = new PersonaAutorizada();
		personaAutorizadaTrans.setId("2216521981155");
		personaAutorizadaTrans.setIdentificacion("0771");
		personaAutorizadaTrans.setNombre("Pepe");
		personaAutorizadaTrans.setApellidos("Pelaez");
		personaAutorizadaTrans.setFechaNacimiento(null);
		personaAutorizadaTrans.setEstado("ACTIVO");
		personaAutorizadaTrans.setDireccion("Avda S");		
		personaAutorizadaTrans.setFechaInicio(null);
		personaAutorizadaTrans.setFechaFin(null);
		personaAutorizadaTrans.setAutori(null);
		
		Individual individualTrans = new Individual();
		individualTrans.setId("981841");
		individualTrans.setIdentificacion("654987");
		individualTrans.setTipoCliente("FISICA");
		individualTrans.setEstado("ACTIVO");
		individualTrans.setFechaAlta(Date.valueOf("2021-03-14"));
		individualTrans.setFechaBaja(null);
		individualTrans.setDireccion("Avenida Cos");
		individualTrans.setCiudad("Malaga");
		individualTrans.setCodigoPostal("2901");
		individualTrans.setPais("España");
		individualTrans.setNombre("Jamal");
		individualTrans.setApellido("Peterh");
		individualTrans.setFecha_nacimiento(null);
		
		List<CuentaFintech> listaTrans = new ArrayList<CuentaFintech>();
		CuentaFintech cuentaTrans = new CuentaFintech();
		cuentaTrans.setIban("795588");
		cuentaTrans.setEstado(null);
		cuentaTrans.setSwift("482");
		cuentaTrans.setFechaApertura(Date.valueOf("2021-03-14"));
		cuentaTrans.setFechaCierre(null);
		cuentaTrans.setClasificacion(null);
		
		listaTrans.add(cuentaTrans);
		individualTrans.setCuentas(listaTrans);
		*/
		
		Usuario comprobar = em.find(Usuario.class, "juan");
		if(comprobar != null) {
			return;
		}
		
		
		Empresa empresa = new Empresa();
		empresa.setId("P3310693A");
		empresa.setRazon_Social("Andamios Antonio");
		empresa.setTipoCliente("JURIDICO");
		empresa.setEstado("ACTIVO");
		empresa.setFechaAlta("2021-07-16");
		empresa.setDireccion("Alameda de Colón");
		empresa.setCiudad("Malaga");
		empresa.setCodigoPostal("25300");
		empresa.setPais("España");
		
		Individual individual = new Individual();
		individual.setId("63937528N");
		individual.setNombre("Paco");
		individual.setApellido("Gómez");
		individual.setTipoCliente("FISICA");
		individual.setEstado("ACTIVO");
		individual.setFechaAlta("2021-03-14");
		individual.setDireccion("Avenida Correcaminos");
		individual.setCiudad("Malaga");
		individual.setCodigoPostal("29001");
		individual.setPais("España");
		individual.setFecha_nacimiento(null);
		
		PersonaAutorizada autorizada = new PersonaAutorizada();
		autorizada.setId("Y4001267V");
		autorizada.setNombre("Pepe");
		autorizada.setApellidos("Pelaez");
		autorizada.setFechaNacimiento(null);
		autorizada.setEstado("ACTIVO");
		autorizada.setDireccion("Avda S");		
		autorizada.setFechaInicio(null);
		autorizada.setFechaFin(null);
		
		
		Autorizacion aut = new Autorizacion();
		AutorizacionPK id = new AutorizacionPK();
		id.setPersonaAutorizadaId("Y4001267V");
		id.setEmpresaId("P3310693A");
		aut.setId(id);
		aut.setEmpresa(empresa);
		aut.setIdAutorizada(autorizada);
		aut.setTipo("OPERAR");
		
		List<Autorizacion> lista = new ArrayList<>();
		lista.add(aut);
		
		autorizada.setAutori(lista);
				
		Divisa euro = new Divisa();
		euro.setAbreviatura("euro");
		euro.setNombre("euro");
		euro.setCambioEuro(1);
		euro.setSimbolo("€");
		em.merge(euro);

		Divisa dolar = new Divisa();
		dolar.setAbreviatura("dolar");
		dolar.setNombre("dolares");
		dolar.setCambioEuro(0.95);
		dolar.setSimbolo("$");
		em.merge(dolar);
		
		Divisa libra = new Divisa();
		libra.setAbreviatura("libra");
		libra.setNombre("libra");
		libra.setCambioEuro(1.17);
		libra.setSimbolo("£");
		em.merge(libra);
		
		Usuario juan = new Usuario();
		juan.setNombreUsuario("juan");
		juan.setPassword("juan");
		juan.setTipo("NORMAL");
		juan.setCliente(individual);
		individual.setUsuario(juan);
		em.merge(juan);
		
		Usuario ana = new Usuario();
		ana.setNombreUsuario("ana");
		ana.setPassword("ana");
		ana.setTipo("NORMAL");
		ana.setPersonaAutorizada(autorizada);
		autorizada.setUsuario(ana);
		em.merge(ana);
		
		Usuario ponciano = new Usuario();
		ponciano.setNombreUsuario("ponciano");
		ponciano.setPassword("ponciano");
		ponciano.setTipo("ADMIN");
		em.merge(ponciano);
		
		
		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("VG57DDVS5173214964983931");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombreBanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("ABIERTA");
		cuentaref2.setAbreviatura(dolar);
		
		em.merge(cuentaref2);
		
		CuentaReferencia cuentaref3 = new CuentaReferencia();
		cuentaref3.setIban("HN47QUXH11325678769785549996");
		cuentaref3.setSwift("2345");
		cuentaref3.setNombreBanco("Santander");
		cuentaref3.setSucursal("Plaza mayor");
		cuentaref3.setSaldo(45.0);
		cuentaref3.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref3.setEstado("ABIERTA");
		cuentaref3.setAbreviatura(dolar);
		
		em.merge(cuentaref3);
		
		CuentaReferencia cuentaref4 = new CuentaReferencia();
		cuentaref4.setIban("ES7121007487367264321882");
		cuentaref4.setSwift("2345");
		cuentaref4.setNombreBanco("Santander");
		cuentaref4.setSucursal("Plaza mayor");
		cuentaref4.setSaldo(100.0);
		cuentaref4.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref4.setEstado("ABIERTA");
		cuentaref4.setAbreviatura(euro);
		
		em.merge(cuentaref4);
		
		
		CuentaReferencia cuentaref5 = new CuentaReferencia();
		cuentaref5.setIban("VG88HBIJ4257959912673134");
		cuentaref5.setSwift("2345");
		cuentaref5.setNombreBanco("Santander");
		cuentaref5.setSucursal("Plaza mayor");
		cuentaref5.setSaldo(200.0);
		cuentaref5.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref5.setEstado("ABIERTA");
		cuentaref5.setAbreviatura(dolar);
		
		em.merge(cuentaref5);
		
		
		CuentaReferencia cuentaref6 = new CuentaReferencia();
		cuentaref6.setIban("GB79BARC20040134265953");
		cuentaref6.setSwift("2345");
		cuentaref6.setNombreBanco("Santander");
		cuentaref6.setSucursal("Plaza mayor");
		cuentaref6.setSaldo(134.0);
		cuentaref6.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref6.setEstado("ABIERTA");
		cuentaref6.setAbreviatura(libra);
		
		em.merge(cuentaref6);
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES8400817251647192321264");
		pooled.setCliente(individual);
		pooled.setClasificacion("POOLED");
		pooled.setEstado("ABIERTA");
		pooled.setSwift("2346");
		pooled.setFechaApertura(Date.valueOf("2021-05-22"));
		
		DepositadaEn depositaEn1 = new DepositadaEn();

		depositaEn1.setSaldo(100.0);
		depositaEn1.setId1(cuentaref4);
		depositaEn1.setId2(pooled);
		DepositaEnPK dep1_pk = new DepositaEnPK();
		dep1_pk.setCuentaReferenciaIban("ES7121007487367264321882");
		dep1_pk.setPooledAccountIban("ES8400817251647192321264");
		depositaEn1.setId(dep1_pk);
		em.merge(depositaEn1);  
		
		
		DepositadaEn depositaEn2 = new DepositadaEn();

		depositaEn2.setSaldo(200.0);
		depositaEn2.setId1(cuentaref5);
		depositaEn2.setId2(pooled);
		DepositaEnPK dep2_pk = new DepositaEnPK();
		dep2_pk.setCuentaReferenciaIban("VG88HBIJ4257959912673134");
		dep2_pk.setPooledAccountIban("ES8400817251647192321264");
		depositaEn2.setId(dep2_pk);
		
		em.merge(depositaEn2);
		
		
		DepositadaEn depositaEn3 = new DepositadaEn();

		depositaEn3.setSaldo(134.0);
		depositaEn3.setId1(cuentaref6);
		depositaEn3.setId2(pooled);
		DepositaEnPK dep3_pk = new DepositaEnPK();
		dep3_pk.setCuentaReferenciaIban("GB79BARC20040134265953");
		dep3_pk.setPooledAccountIban("ES8400817251647192321264");
		depositaEn3.setId(dep3_pk);
		
		em.merge(depositaEn3);
		
		List<DepositadaEn> listaDepo = new ArrayList<>();
		listaDepo.add(depositaEn1);
		listaDepo.add(depositaEn2);
		listaDepo.add(depositaEn3);
		
		pooled.setDepositaEn(listaDepo);
		
		em.merge(pooled);
		
		Segregada segregada1 = new Segregada();
		segregada1.setIban("NL63ABNA6548268733");
		segregada1.setCliente(empresa);
		segregada1.setEstado("ABIERTA");
		segregada1.setCuentaReferencia(cuentaref2);
		segregada1.setClasificacion("SEGREGADA");
		segregada1.setSwift("2347");
		segregada1.setFechaApertura(Date.valueOf("2022-04-25"));
		
		em.merge(segregada1);
		
		Segregada segregada2 = new Segregada();
		segregada2.setIban("FR5514508000502273293129K55");
		segregada2.setCliente(empresa);
		segregada2.setEstado("ABIERTA");
		segregada2.setCuentaReferencia(cuentaref3);
		segregada2.setClasificacion("SEGREGADA");
		segregada2.setSwift("2347");
		segregada2.setFechaApertura(Date.valueOf("2022-04-25"));
		
		em.merge(segregada2);
		
		Segregada segregada3 = new Segregada();
		segregada3.setIban("DE31500105179261215675");
		segregada3.setCliente(empresa);
		segregada3.setEstado("BAJA");
		segregada3.setClasificacion("SEGREGADA");
		segregada3.setSwift("2347");
		segregada3.setFechaApertura(Date.valueOf("2021-04-25"));
		segregada3.setFechaCierre(Date.valueOf("2021-09-01"));
		
		em.merge(segregada3);
		
		Transaccion transaccion = new Transaccion();
		transaccion.setIDunico("553");
		transaccion.setCantidad(200.0);
		transaccion.setComision(null);
		transaccion.setIban(segregada1);
		transaccion.setIban1(pooled);
		transaccion.setDivisa(dolar);
		transaccion.setDivisa1(dolar);
		transaccion.setFechaEjecucion(Date.valueOf("2022-04-25"));
		transaccion.setFechaInstruccion(Date.valueOf("2022-04-25"));
		transaccion.setInternacional(null);
		transaccion.setTipo("CD");
		em.merge(transaccion);
		
		List<CuentaFintech> cuentasEmpresa = new ArrayList<>();
		cuentasEmpresa.add(segregada1);
		cuentasEmpresa.add(segregada2);
		cuentasEmpresa.add(segregada3);
		
		empresa.setCuentas(cuentasEmpresa);
		
		List<CuentaFintech> cuentasIndividual = new ArrayList<>();
		cuentasIndividual.add(pooled);
		
		individual.setCuentas(cuentasIndividual);
		
		em.merge(individual);
		em.merge(empresa);
		em.merge(autorizada);
		
		
		
	}

}
