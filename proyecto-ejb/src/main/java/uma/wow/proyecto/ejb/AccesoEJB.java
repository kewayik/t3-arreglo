package uma.wow.proyecto.ejb;
import uma.wow.proyecto.ejb.exceptions.*;
import uma.wow.proyecto.Cliente;
import uma.wow.proyecto.Empresa;
import uma.wow.proyecto.Individual;
import uma.wow.proyecto.PersonaAutorizada;
import uma.wow.proyecto.Usuario;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless
public class AccesoEJB implements GestionAcceso{

	@PersistenceContext(unitName="WOWEJB")
	private EntityManager em;
	
	@Override
	public void loginAdministrador(Usuario usuario) throws UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException {
		
		Usuario u = em.find(Usuario.class, usuario.getNombreUsuario());
		
		//Comprobamos si existe el usuario
		
		if(u == null) {
			throw new UsuarioNoEncontrado();
		}
		
		//El usuario existe, comprobamos su contraseña
		
		if(!u.getPassword().equals(usuario.getPassword())) {
			throw new ContraseniaInvalida();
		}
		
		//Comprobamos si el usuario autenticado es administrador
		if(!u.getTipo().equals("ADMIN")) {
			throw new NoAdministradorException();
		}
		
	
	}

	@Override
    public void loginCliente(Usuario usuario) throws UsuarioNoEncontrado, ContraseniaInvalida, EsEmpresaException, CuentaBloqueada, CuentaDeBaja, ClienteNoEncontrado, EsAdministrador{

        Usuario u = em.find(Usuario.class, usuario.getNombreUsuario());

        //Comprobamos si existe el usuario

        if(u == null) {
            throw new UsuarioNoEncontrado();
        }

        //El usuario existe, comprobamos su contraseña

        if(!u.getPassword().equals(usuario.getPassword())) {
            throw new ContraseniaInvalida();
        }
        
        if(u.getTipo().equals("ADMIN")) {
        	throw new EsAdministrador();
        }

        //Comprobamos que no sea una persona jurídica

        String ID;
        if(u.getCliente()!=null) {
            ID = u.getCliente().getId();
        } else if (u.getPersonaAutorizada() != null) {
            ID = u.getPersonaAutorizada().getId();
        } else {
            throw new ClienteNoEncontrado();
        }

        Empresa empresa = em.find(Empresa.class,ID);
        Individual fisica = em.find(Individual.class, ID);
        PersonaAutorizada autorizado = em.find(PersonaAutorizada.class, ID);

        if(empresa != null) {
            throw new EsEmpresaException();

        } else if(fisica != null) {

            if(fisica.getEstado() == "BLOQUEADO") {
                throw new CuentaBloqueada();
            } else if(fisica.getEstado()=="BAJA") {
                throw new CuentaDeBaja();
            }

        } else if(autorizado != null){
            if(autorizado.getEstado() == "BLOQUEADO") {
                throw new CuentaBloqueada();
            } else if(autorizado.getEstado()=="BAJA") {
                throw new CuentaDeBaja();
            }

        } else {
            throw new ClienteNoEncontrado();
        }


    }
	
	
	

}
