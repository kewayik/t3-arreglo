package uma.wow.proyecto.ejb;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.*;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TransaccionEJB implements GestionTransaccion{


	
    @PersistenceContext(unitName="WOWEJB")
    private EntityManager em;

    //R14
    public void transaccion(Usuario user, String IBANorigen, String IBANdestino, double dinero) throws EJBException {
    	
    	Usuario usuario = em.find(Usuario.class, user.getNombreUsuario());
    	if(usuario == null) {
            throw new UsuarioNoEncontrado();
        }
    	
    	Cliente cliente = usuario.getCliente();
    	PersonaAutorizada autorizado = usuario.getPersonaAutorizada();
    	
    	if(cliente != null) {
    		transaccionCliente(cliente,IBANorigen,IBANdestino, dinero);
    		
    	} else if (autorizado != null) {
    		transaccionAutorizado(autorizado,IBANorigen,IBANdestino,dinero);
    	} else {
    		throw new EJBException("El usuario no está asociado con ningún cliente");
    	}
    	
    }
    
    public void transaccionCliente(Cliente c, String IBANorigen, String IBANdestino, double dinero) throws ClienteSinAutorizacion, CuentaNoEncontrada, SaldoInsuficiente {
    	Individual cliente = em.find(Individual.class, c.getId());
    	
    	boolean tieneAcceso = false;
    	for(CuentaFintech acc :cliente.getCuentas()) {
    		if(acc.getIban().equals(IBANorigen)) {
    			tieneAcceso = true;
    		}
    	}
    	
    	if(!tieneAcceso) throw new ClienteSinAutorizacion();
    	
    	PooledAccount pooledOrigen = em.find(PooledAccount.class, IBANorigen);
    	Segregada segOrigen = em.find(Segregada.class, IBANorigen);
		PooledAccount pooledDestino = em.find(PooledAccount.class, IBANdestino);
    	Segregada segDestino = em.find(Segregada.class, IBANdestino);
    	
    	if(pooledOrigen != null) {
    		DepositadaEn saldoOrigen = em.find(DepositadaEn.class,pooledOrigen.getDepositaEn().get(0).getId());
        	if(pooledDestino != null) {
        		
        		DepositadaEn saldoDestino = em.find(DepositadaEn.class,pooledDestino.getDepositaEn().get(0).getId());
        		
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        			Transaccion transaccion = new Transaccion();

        		} else {
        			throw new SaldoInsuficiente();
        		}
        		
        	} else if (segDestino != null) {
        		
        		CuentaReferencia saldoDestino = em.find(CuentaReferencia.class, segDestino.getCuentaReferencia().getIban());
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        		} else {
        			throw new SaldoInsuficiente();
        		}
        		
        	} else {
        		throw new CuentaNoEncontrada();
        	}
        	
    	} else if(segOrigen != null) {
    		CuentaReferencia saldoOrigen = em.find(CuentaReferencia.class, segOrigen.getCuentaReferencia().getIban());
    		
    		if(pooledDestino != null) {
    			DepositadaEn saldoDestino = em.find(DepositadaEn.class,pooledDestino.getDepositaEn().get(0).getId());
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        		} else {
        			throw new SaldoInsuficiente();
        		}
    			
    		} else if(segDestino != null) {
    			CuentaReferencia saldoDestino = em.find(CuentaReferencia.class, segDestino.getCuentaReferencia().getIban());
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        		} else {
        			throw new SaldoInsuficiente();
        		}
    		} else {
    			throw new CuentaNoEncontrada();
    		}
    		
    	} else {
    		throw new CuentaNoEncontrada();
    	}
    }
    
    public void transaccionAutorizado(PersonaAutorizada c, String IBANorigen, String IBANdestino, double dinero) throws PermisoSoloDeLecturaException, ClienteSinAutorizacion, SaldoInsuficiente, CuentaNoEncontrada {
    	PersonaAutorizada cliente = em.find(PersonaAutorizada.class, c.getId());
    	boolean tieneAcceso = false;
    	for(Autorizacion a:cliente.getAutori()) {
    		Empresa empresa = em.find(Empresa.class, a.getEmpresa().getId());
    		for(CuentaFintech acc :empresa.getCuentas()) {
        		if(acc.getIban().equals(IBANorigen)) {
        			tieneAcceso = true;
        			Autorizacion aut = em.find(Autorizacion.class, a.getId());
        			if(aut.getTipo().equals("LECTURA")){ //DISTINGUIMOS ENTRE AUTORIZADOS QUE PUEDEN VER LAS CUENTAS (LECTURA) y los que además pueden operar con ellas (OPERAR)
        				throw new PermisoSoloDeLecturaException();
        			}
        		}
    		}
    	}
    	
    	if(!tieneAcceso) throw new ClienteSinAutorizacion();
    	
    	PooledAccount pooledOrigen = em.find(PooledAccount.class, IBANorigen);
    	Segregada segOrigen = em.find(Segregada.class, IBANorigen);
		PooledAccount pooledDestino = em.find(PooledAccount.class, IBANdestino);
    	Segregada segDestino = em.find(Segregada.class, IBANdestino);
    	
    	if(pooledOrigen != null) {
    		DepositadaEn saldoOrigen = em.find(DepositadaEn.class,pooledOrigen.getDepositaEn().get(0).getId());
        	if(pooledDestino != null) {
        		
        		DepositadaEn saldoDestino = em.find(DepositadaEn.class,pooledDestino.getDepositaEn().get(0).getId());
        		
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        		} else {
        			throw new SaldoInsuficiente();
        		}
        		
        	} else if (segDestino != null) {
        		
        		CuentaReferencia saldoDestino = em.find(CuentaReferencia.class, segDestino.getCuentaReferencia().getIban());
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        		} else {
        			throw new SaldoInsuficiente();
        		}
        		
        	} else {
        		throw new CuentaNoEncontrada();
        	}
        	
    	} else if(segOrigen != null) {
    		CuentaReferencia saldoOrigen = em.find(CuentaReferencia.class, segOrigen.getCuentaReferencia().getIban());
    		
    		if(pooledDestino != null) {
    			DepositadaEn saldoDestino = em.find(DepositadaEn.class,pooledDestino.getDepositaEn().get(0).getId());
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        		} else {
        			throw new SaldoInsuficiente();
        		}
    			
    		} else if(segDestino != null) {
    			CuentaReferencia saldoDestino = em.find(CuentaReferencia.class, segDestino.getCuentaReferencia().getIban());
        		if(dinero <= saldoOrigen.getSaldo()) {
        			saldoOrigen.setSaldo(saldoOrigen.getSaldo()-dinero);
        			saldoDestino.setSaldo(saldoDestino.getSaldo()+dinero);
        		} else {
        			throw new SaldoInsuficiente();
        		}
    		} else {
    			throw new CuentaNoEncontrada();
    		}
    		
    	} else {
    		throw new CuentaNoEncontrada();
    	}
    	
    	
    	
    	
    }




}