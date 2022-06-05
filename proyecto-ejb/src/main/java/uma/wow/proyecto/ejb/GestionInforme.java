package uma.wow.proyecto.ejb;
import java.nio.file.Path;
import java.sql.Date;
import java.util.List;

import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.CuentaNoEncontrada;
import uma.wow.proyecto.ejb.exceptions.NoAdministradorException;

public interface GestionInforme {
	
	public List<Segregada>devolverInformeHolandaProductoTodas(String IBAN) throws CuentaNoEncontrada;
	
	public List<Segregada> devolverInformeHolandaProductoInactivas(String IBAN) throws CuentaNoEncontrada;
	
	public List<Segregada> devolverInformeHolandaProductoActivas(String IBAN) throws CuentaNoEncontrada;
	
	public List<Individual> devolverInformeHolandaClientes(String nombre, String apellidos, Date fechaAlta, Date fechaBaja);
	
	List<Segregada> devolverInformeHolandaProductoTodasSinIBAN() throws CuentaNoEncontrada;
	
	List<PersonaAutorizada> devolverInformeHolandaAutorizados(String nombre, String apellidos, Date fechaAlta, Date fechaBaja);
	
	public Path generarReporteInicialAlemania();
	
	public Path generarReporteSemanalAlemania();

}
