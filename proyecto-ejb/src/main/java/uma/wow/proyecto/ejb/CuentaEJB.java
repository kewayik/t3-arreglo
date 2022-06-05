package uma.wow.proyecto.ejb;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.*;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CuentaEJB implements GestionCuenta {


    @PersistenceContext(unitName="WOWEJB")
    private EntityManager em;

    // R5
    //@Override
    public void creaCuenta(PooledAccount cuentaNueva, Empresa c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException {

        PooledAccount cuenta = em.find(PooledAccount.class, cuentaNueva.getIban());

        if(cuenta != null){
            throw new CuentaEncontrada();
        }

        Empresa cliente = em.find(Empresa.class, c.getId());

        if(cliente == null){
            throw new ClienteNoEncontrado();
        } else {
            List<CuentaFintech> listCuenta = cliente.getCuentas();
            listCuenta.add(cuentaNueva);
            cliente.setCuentas(listCuenta);
            em.persist(cuentaNueva);
        }
    }
    
    // R5
    //@Override
    public void creaCuenta(Segregada cuentaNueva, Empresa c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException {

        Segregada cuenta = em.find(Segregada.class, cuentaNueva.getIban());

        if(cuenta != null){
            throw new CuentaEncontrada();
        }

        Empresa cliente = em.find(Empresa.class, c.getId());

        if(cliente == null){
            throw new ClienteNoEncontrado();
        } else {
            List<CuentaFintech> listCuenta = cliente.getCuentas();
            listCuenta.add(cuentaNueva);
            cliente.setCuentas(listCuenta);
            em.persist(cuentaNueva);
        }
    }
    
    //R5
    @Override
    public void creaCuenta(PooledAccount cuentaNueva, Individual c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException {
   
        PooledAccount cuenta = em.find(PooledAccount.class, cuentaNueva.getIban());

        if(cuenta != null){
            throw new CuentaEncontrada();
        }

        Individual cliente = em.find(Individual.class, c.getId());

        if(cliente == null){
            throw new ClienteNoEncontrado();
        } else {
            List<CuentaFintech> listCuenta = cliente.getCuentas();
            listCuenta.add(cuentaNueva);
            cliente.setCuentas(listCuenta);
            em.persist(cuentaNueva);
        }
    }
    
    // R5
    //@Override
    public void creaCuenta(Segregada cuentaNueva, Individual c, Usuario usuario) throws CuentaEncontrada, ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException {
    
        Segregada cuenta = em.find(Segregada.class, cuentaNueva.getIban());

        if(cuenta != null){
            throw new CuentaEncontrada();
        }

        Individual cliente = em.find(Individual.class, c.getId());

        if(cliente == null){
            throw new ClienteNoEncontrado();
        } else {
            List<CuentaFintech> listCuenta = cliente.getCuentas();
            listCuenta.add(cuentaNueva);
            cliente.setCuentas(listCuenta);
            em.persist(cuentaNueva);
        }
    }
    
    

    // R9
    
    @Override
    public void cierraCuenta(Segregada c, Usuario admin) throws SaldoException, CuentaNoEncontrada, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException {
    
        Segregada cuenta = em.find(Segregada.class, c.getIban());
        if(cuenta == null){
            throw new CuentaNoEncontrada();
        }
        
        if(cuenta.getCuentaReferencia().getSaldo() > 0){
            throw new SaldoException();
        }

        cuenta.setEstado("BAJA");
    }
    
    public void cierraCuenta(PooledAccount c, Usuario admin) throws SaldoException, CuentaNoEncontrada, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException {
  
        PooledAccount cuenta = em.find(PooledAccount.class, c.getIban());
        if(cuenta == null){
            throw new CuentaNoEncontrada();
        }
        
		for (DepositadaEn de : cuenta.getDepositaEn()) {

			if (de.getSaldo() != 0) {

				throw new SaldoException();
			}
		}
        

        cuenta.setEstado("BAJA");
    }

    @Override
    public PooledAccount devolverPooled(String identificacion) throws CuentaNoEncontrada {
        PooledAccount pooled = em.find(PooledAccount.class, identificacion);
        if(pooled == null) {
            throw new CuentaNoEncontrada();
        }
        return pooled;
    }
    
    @Override
    public Segregada devolverSegregada(String identificacion) throws CuentaNoEncontrada {
        Segregada pooled = em.find(Segregada.class, identificacion);
        if(pooled == null) {
            throw new CuentaNoEncontrada();
        }
        return pooled;
    }
    
    @Override
	public List<Segregada> devolverSegregadasDeIndividual(String id) throws ClienteNoEncontrado{
		Individual individual = em.find(Individual.class, id);
		
			if(individual == null) {
				throw new ClienteNoEncontrado();
			}	
			
		Query q = em.createQuery("SELECT c FROM Segregada c");
		List<Segregada> cuentas = q.getResultList();
		List<Segregada> lista = new ArrayList<>();
		
		for(Segregada c : cuentas) {
			if(c.getCliente().getId().equals(id)) {
				lista.add(c);
			}
		}
		
		return lista;
	}
    
    @Override
	public List<Segregada> devolverSegregadasDeAutorizado(String id) throws PersonaAutorizadaNoEncontrada{
		
		
		PersonaAutorizada cliente = em.find(PersonaAutorizada.class, id);
		
			if(cliente == null) {
				throw new PersonaAutorizadaNoEncontrada();
			}
			
			
		Query q = em.createQuery("SELECT c FROM Segregada c");
		Query q2 = em.createQuery("SELECT c FROM Autorizacion c where c.personaAutorizada.identificacion = :id");
		q2.setParameter("id", id);
		
		List<Autorizacion> auto = q2.getResultList();
		List<Segregada> cuentas = q.getResultList();
		List<Segregada> lista = new ArrayList<>();
		
		for(Segregada seg : cuentas) {
			
			Cliente clienteseg = seg.getCliente();
			
			if(clienteseg.getTipoCliente().equals("JURIDICA")) {
				Empresa empresa = em.find(Empresa.class,clienteseg.getId());
				
				boolean esta = false;
				for(Autorizacion aut : auto) {
					if(aut.getEmpresa().getId().equals(empresa.getId())) {
						esta = true;
					}
					
				}
				
				if(esta) {
					lista.add(seg);
				}
			}
			
			
		}
		
		return lista;
	}
    
}