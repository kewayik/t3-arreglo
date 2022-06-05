package uma.wow.proyecto.ejb;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;


@Stateless
public class InformeEJB implements GestionInforme {

	
	@PersistenceContext(name = "WOWEJB")
	private EntityManager em;

	@Override
	public List<Segregada> devolverInformeHolandaProductoTodas(String IBAN) throws CuentaNoEncontrada {
		List<Segregada> listadoCuentas = new ArrayList<>();
		Segregada seg = em.find(Segregada.class, IBAN);
		if (seg == null) {
			throw new CuentaNoEncontrada();
		}
		listadoCuentas.add(seg);
		return listadoCuentas;
	}
	
	@Override
	public List<Segregada> devolverInformeHolandaProductoTodasSinIBAN() throws CuentaNoEncontrada {
		List<Segregada> listadoCuentas = new ArrayList<>();
		TypedQuery<Segregada> query = em.createQuery("SELECT c FROM Segregada c", Segregada.class);
		listadoCuentas= query.getResultList();
		if (listadoCuentas.size() == 0) {
			throw new CuentaNoEncontrada();
		}
		
		return listadoCuentas;
	}

	@Override
	public List<Segregada> devolverInformeHolandaProductoInactivas(String IBAN) throws CuentaNoEncontrada{

		Query query = em.createQuery("SELECT s FROM Segregada s where s.estado = :estado AND s.iban = :iban");
		query.setParameter("estado", "BAJA");
		query.setParameter("iban", IBAN);

		List<Segregada> listadoCuentas = query.getResultList();

		if (listadoCuentas.size() == 0) {
			throw new CuentaNoEncontrada();
		}

		return listadoCuentas;

	}

	@Override
	public List<Segregada> devolverInformeHolandaProductoActivas(String IBAN) throws CuentaNoEncontrada {

		Query query = em.createQuery("SELECT s FROM Segregada s where s.estado = :estado AND s.iban = :iban");
		query.setParameter("estado", "ABIERTA");
		query.setParameter("iban", IBAN);

		List<Segregada> listadoCuentas = query.getResultList();

		if (listadoCuentas.size() == 0) {
			throw new CuentaNoEncontrada();
		}

		return listadoCuentas;

	}

	@Override
	public List<Individual> devolverInformeHolandaClientes(String nombre, String apellidos, Date fechaAlta,
			Date fechaBaja)  {
		Query query = em.createQuery(
				"SELECT i FROM Individual i where i.nombre = :nombre AND i.apellido = :apellido AND i.fechaAlta = :fechaalta"
						+ " AND i.fechaBaja = :fechabaja");

		query.setParameter("nombre", nombre);
		query.setParameter("apellido", apellidos);
		query.setParameter("fechaalta", fechaAlta);
		query.setParameter("fechabaja", fechaBaja);
		

		List<Individual> listaClientes = query.getResultList();
		

		return listaClientes;
	}

	
	@Override
	public List<PersonaAutorizada> devolverInformeHolandaAutorizados(String nombre, String apellidos, Date fechaAlta, Date fechaBaja)  {
		Query query = em.createQuery(
				"SELECT i FROM PersonaAutorizada i where i.nombre = :nombre AND i.apellidos = :apellido AND i.fechainicio = :fechaalta"
						+ " AND i.fechafin = :fechabaja");

		query.setParameter("nombre", nombre);
		query.setParameter("apellido", apellidos);
		query.setParameter("fechaalta", fechaAlta);
		query.setParameter("fechabaja", fechaBaja);
		

		List<PersonaAutorizada> listaAutorizados = query.getResultList();
		

		return listaAutorizados;
	}
	public Path generarReporteInicialAlemania() {

		String nombre_archivo_csv;

		TypedQuery<Segregada> query1 = em.createQuery("SELECT c FROM Segregada c", Segregada.class);
		List<Segregada> cuentas = query1.getResultList();
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
	    nombre_archivo_csv = new String("FINTECH_IBAN_1");

	    Path fichero_temp = null;
	    
		try {
			fichero_temp = Files.createTempFile(nombre_archivo_csv, ".csv");
			BufferedWriter writer = Files.newBufferedWriter(fichero_temp);
			@SuppressWarnings("deprecation")
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name",
					"Firs_Name", "Street", "City", "Post_Code", "Country", "Identification_Number", "Date_Of_Birth"));
			
			for (CuentaFintech c : cuentas) {

				Empresa clienteEmpresa = em.find(Empresa.class, c.getCliente().getId());

				if (clienteEmpresa == null) {

					Individual clienteIndividual = em.find(Individual.class, c.getCliente().getId());

					String fecha_nacimiento;

					if (clienteIndividual.getFechaNacimiento() == null) {

						fecha_nacimiento = new String("noexistente");
					} else {

						fecha_nacimiento = clienteIndividual.getFechaNacimiento().toString();
					}
					
					csvPrinter.printRecord(c.getIban(), clienteIndividual.getApellido(), clienteIndividual.getNombre(),
							clienteIndividual.getDireccion(), clienteIndividual.getCiudad(),
							clienteIndividual.getCodigoPostal(), clienteIndividual.getPais(),
							clienteIndividual.getId(), fecha_nacimiento);
				} else {

					for (Autorizacion a : clienteEmpresa.getAutori()) {

						String fecha_nacimiento;

						if (a.getIdAutorizada().getEstado().equals("ACTIVO")) {

							if (a.getIdAutorizada().getFechaNacimiento() == null) {

								fecha_nacimiento = new String("noexistente");
							} else {

								fecha_nacimiento = a.getIdAutorizada().getFechaNacimiento().toString();
							}

							csvPrinter.printRecord(c.getIban(), a.getIdAutorizada().getApellidos(),
									a.getIdAutorizada().getNombre(), a.getIdAutorizada().getDireccion(),
									clienteEmpresa.getCiudad(), clienteEmpresa.getCodigoPostal(),
									clienteEmpresa.getPais(), clienteEmpresa.getId(), fecha_nacimiento);
						}
					}
				}
			}

			csvPrinter.print("\n" + "Ebury_IBAN_" + dtf2.format(LocalDateTime.now()).trim() + "\n");
			
			writer.close();
			csvPrinter.close();
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		return fichero_temp;
	}

	public Path generarReporteSemanalAlemania() {

		String nombre_archivo_csv;

		TypedQuery<Segregada> query1 = em.createQuery("SELECT s FROM Segregada s where s.estado = :festado", Segregada.class);
		query1.setParameter("festado", "ABIERTA");
		List<Segregada> cuentas = query1.getResultList();
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				
	    nombre_archivo_csv = new String("FINTECH_IBAN_2");
	    
	    Path fichero_temp = null;
	    
		try {
			fichero_temp = Files.createTempFile(nombre_archivo_csv, ".csv");
			BufferedWriter writer = Files.newBufferedWriter(fichero_temp);
			@SuppressWarnings("deprecation")
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name",
					"Firs_Name", "Street", "City", "Post_Code", "Country", "Identification_Number", "Date_Of_Birth"));
			for (CuentaFintech c : cuentas) {

					Empresa clienteEmpresa = em.find(Empresa.class, c.getCliente().getId());

					if (clienteEmpresa == null) {

						Individual clienteIndividual = em.find(Individual.class, c.getCliente().getId());

						String fecha_nacimiento;

						if (clienteIndividual.getFechaNacimiento() == null) {

							fecha_nacimiento = new String("noexistente");
						} else {

							fecha_nacimiento = clienteIndividual.getFechaNacimiento().toString();
						}

						csvPrinter.printRecord(c.getIban(), clienteIndividual.getApellido(),
								clienteIndividual.getNombre(), clienteIndividual.getDireccion(),
								clienteIndividual.getCiudad(), clienteIndividual.getCodigoPostal(),
								clienteIndividual.getPais(), clienteIndividual.getId(), fecha_nacimiento);

					} else {

						for (Autorizacion a : clienteEmpresa.getAutori()) {

							String fecha_nacimiento;

							if (a.getIdAutorizada().getEstado().equals("ACTIVO")) {

								if (a.getIdAutorizada().getFechaNacimiento() == null) {

									fecha_nacimiento = new String("noexistente");
								} else {

									fecha_nacimiento = a.getIdAutorizada().getFechaNacimiento().toString();
								}

								csvPrinter.printRecord(c.getIban(), a.getIdAutorizada().getApellidos(),
										a.getIdAutorizada().getNombre(), a.getIdAutorizada().getDireccion(),
										clienteEmpresa.getCiudad(), clienteEmpresa.getCodigoPostal(),
										clienteEmpresa.getPais(), clienteEmpresa.getId(), fecha_nacimiento);
							}
						}
					}
			}

			csvPrinter.print("\n" + "Ebury_IBAN_" + dtf2.format(LocalDateTime.now()).trim() + "\n");
			
			writer.close();
			csvPrinter.close();

		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		
		
		return fichero_temp;
	}
}
