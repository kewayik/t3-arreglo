package uma.wow.proyecto.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import uma.wow.proyecto.Cliente;
import uma.wow.proyecto.Empresa;
import uma.wow.proyecto.Individual;
import uma.wow.proyecto.PersonaAutorizada;
import uma.wow.proyecto.Segregada;
import uma.wow.proyecto.ejb.GestionCliente;
import uma.wow.proyecto.ejb.GestionCuenta;
import uma.wow.proyecto.ejb.GestionInforme;
import uma.wow.proyecto.ejb.exceptions.EJBException;
import uma.wow.proyecto.modelos.PeticionClientes;

import uma.wow.proyecto.modelos.*;


@Path("")
public class ApiREST {
	
	@EJB
	private GestionInforme informe;
	
	@EJB
	private GestionCliente cliente;
	
	@EJB
	private GestionCuenta cuenta;
	
	@Context
	private UriInfo uriInfo;
	
	@HeaderParam("User-auth")
	private String autorizacion;
	
	@Path("/healthcheck")
	@GET
	public Response getHealthcheck() {
		
			return Response.ok().build();
		
	}
	

	@Path("/clients")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response peticionClientes(PeticionClientes pc) {
		
		
		try {
		String fechainicio = pc.getStartPeriod();
		String fechafin = pc.getEndPeriod();
		String nombre = pc.getName().getFirstName();
		String apellidos = pc.getName().getLastName();
		
		
			List<Individual> listaIndividuales = informe.devolverInformeHolandaClientes(nombre, apellidos, Date.valueOf(fechainicio), Date.valueOf(fechafin));
			
			RespuestaIndividual rc = new RespuestaIndividual();
			List<Individuales> personas = new ArrayList<>();
			
			
			if(listaIndividuales.size() != 0) {
				
				for(Individual i : listaIndividuales) {
					Individuales individuales = new Individuales();
					List<Segregada> cuentas = cuenta.devolverSegregadasDeIndividual(i.getId());
					List<ProductoCliente> productos = new ArrayList<>();
					for(Segregada cf : cuentas) {
						ProductoCliente prodCl = new ProductoCliente();
						prodCl.setProductNumber(cf.getIban());
						prodCl.setStatus(cf.getEstado());
						prodCl.setStatus("propietaria");
						productos.add(prodCl);
						
						
					}
					
					individuales.setProducts(productos);
					individuales.setActiveCostumer(i.getEstado().equals("ACTIVA"));
					individuales.setDateOfBirth(i.getFechaNacimiento().toString());
					NombreCliente name = new NombreCliente();
					name.setFirstName(i.getNombre());
					name.setLastName(i.getApellido());
					individuales.setName(name);
					DireccionCliente direccion = new DireccionCliente();
					direccion.setCity(i.getCiudad());
					direccion.setCountry(i.getPais());
					direccion.setPostalCode(i.getCodigoPostal().toString());
					direccion.setStreetNumber(i.getDireccion());
					individuales.setAddress(direccion);
					personas.add(individuales);
				}
			}
			
			List<PersonaAutorizada> listaAutorizados = informe.devolverInformeHolandaAutorizados(nombre, apellidos, Date.valueOf(fechainicio), Date.valueOf(fechafin));
			if(listaAutorizados.size()!=0) {
				for(PersonaAutorizada i : listaAutorizados) {
					Individuales individuales = new Individuales();
					List<Segregada> cuentas = cuenta.devolverSegregadasDeAutorizado(i.getId());
					List<ProductoCliente> productos = new ArrayList<>();
					for(Segregada cf : cuentas) {
						ProductoCliente prodCl = new ProductoCliente();
						prodCl.setProductNumber(cf.getIban());
						prodCl.setStatus(cf.getEstado());
						prodCl.setStatus("autorizada");
						productos.add(prodCl);
						
					}
					individuales.setProducts(productos);
					individuales.setActiveCostumer(i.getEstado().equals("ACTIVA"));
					individuales.setDateOfBirth(i.getFechaNacimiento().toString());
					NombreCliente name = new NombreCliente();
					name.setFirstName(i.getNombre());
					name.setLastName(i.getApellidos());
					individuales.setName(name);
					DireccionCliente direccion = new DireccionCliente();
					//Estos campos no existen en person autorizada
					direccion.setCity("non-existent");
					direccion.setCountry("non-existent");
					direccion.setPostalCode("non-existent");
					direccion.setStreetNumber(i.getDireccion());
					individuales.setAddress(direccion);
					personas.add(individuales);
				}
			}
			
			
		rc.setIndividual(personas);
		return Response.ok(rc).build();
		}catch(EJBException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	@Path("/products")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response peticionProductos(PeticionProductos pp) {
		
		try {
		List<Segregada> cuentas;
		if(pp.getProductNumber() != null) {
		
		if(pp.getStatus().equals("active")) {
			cuentas = informe.devolverInformeHolandaProductoActivas(pp.getProductNumber());
		}else if(pp.getStatus().equals("inactive")) {
			cuentas = informe.devolverInformeHolandaProductoInactivas(pp.getProductNumber());
		}else {
			cuentas = informe.devolverInformeHolandaProductoTodas(pp.getProductNumber());
		}
		
		}else {
			cuentas = informe.devolverInformeHolandaProductoTodasSinIBAN();
		}
		
		
		RespuestaProductos rp = new RespuestaProductos();
		List<Producto> listaProductos = new ArrayList<>();
		
		for(Segregada s : cuentas) {
			Producto p = new Producto();
			p.setProductNumber(s.getIban());
			p.setStartDate(s.getFechaApertura().toString());
			if(p.getEndDate() == null) {
				p.setEndDate("non-existent");
			}else {
				p.setEndDate(s.getFechaCierre().toString());
			}
			
			
			AccountHolder ah;
		
			Cliente cl = s.getCliente();
			if(cl.getTipoCliente().equals("FISICA")) {
				ah = new AccountHolderIndividual();
				ah.setAccounttype("FISICA");
				ah.setActiveCostumer(s.getEstado().equals("ACTIVA"));
				Individual ind = (Individual) cliente.devolverIndividual(cl.getId());
				NombreCliente nc = new NombreCliente();
				nc.setFirstName(ind.getNombre());
				nc.setLastName(ind.getApellido());
				((AccountHolderIndividual) ah).setName(nc);
				 
				
			}else {
				ah = new AccountHolderEmpresa();
				ah.setAccounttype("Empresa");
				ah.setActiveCostumer(s.getEstado().equals("ACTIVA"));
				Empresa emp = (Empresa) cliente.devolverEmpresa(cl.getId());
				
				
				
				((AccountHolderEmpresa) ah).setName(emp.getRazon_Social());
				
			}
			
				DireccionCliente direccion = new DireccionCliente();
				direccion.setCity(cl.getCiudad());
				direccion.setCountry(cl.getPais());
				direccion.setStreetNumber(cl.getDireccion());
				direccion.setPostalCode(cl.getCodigoPostal().toString());
			ah.setAddress(direccion);
			p.setAccountHolder(ah);
			listaProductos.add(p);
		}
		
		
			rp.setProducts(listaProductos);
		
		
			
			
			
			return Response.ok(rp).build();
			
		}catch(EJBException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
}
