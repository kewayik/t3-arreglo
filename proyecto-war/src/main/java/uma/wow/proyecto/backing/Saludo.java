package uma.wow.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Saludo {
       
       public String getSaludo() {
               return "Hola mundo";
       }

}