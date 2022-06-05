package uma.wow.proyecto.ejb;
import uma.wow.proyecto.*;
import uma.wow.proyecto.ejb.exceptions.*;
import java.util.List;





public interface GestionCliente {
	
	/*//Dar de alta un cliente de tipo persona jurídica
	public void altaCliente(Empresa cliente) throws UsuarioException;
	
	//Dar de alta un cliente de tipo persona física
	public void altaCliente(Individual cliente)throws UsuarioException;
	
	public void modificaCliente(Empresa cliente) throws ClienteNoEncontrado;
	
	public void modificaCliente(Individual cliente) throws ClienteNoEncontrado,DatosException;
	*/

	    public void altaCliente(Empresa cliente, Usuario usuario) throws UsuarioException, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException, ClienteNoEncontrado, ClienteYaExistente;
	    public void altaCliente(Individual cliente, Usuario usuario) throws UsuarioException, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException, ClienteNoEncontrado, ClienteYaExistente;
	    public void modificaCliente(Empresa cliente, Usuario usuario) throws ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	    public void modificaCliente(Individual cliente, Usuario usuario) throws ClienteNoEncontrado, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	    public void bajaCliente(Individual c, Usuario usuario) throws ClienteNoEncontrado, CuentasActivas, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	    public void bajaCliente(Empresa c, Usuario usuario) throws ClienteNoEncontrado, CuentasActivas, UsuarioNoEncontrado, ContraseniaInvalida, NoAdministradorException;
	    public List<Individual> devolverTodosClientesIndividuales();
	    public List<Empresa> devolverTodosClientesEmpresa();
	    public Empresa devolverEmpresa(String identificacion) throws ClienteNoEncontrado;
	    public Individual devolverIndividual(String identificacion) throws ClienteNoEncontrado;

}