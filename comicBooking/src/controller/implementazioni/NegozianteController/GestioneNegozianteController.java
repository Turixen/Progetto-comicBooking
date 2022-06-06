package controller.implementazioni.NegozianteController;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import controller.datastore.DataStoreControllerFactory;
import controller.interfacce.iNegoziante.IGestioneCatalogo;
import controller.interfacce.iNegoziante.IGestioneNegoziante;
import controller.interfacce.iPercistance.IDataStore;
import controller.interfacce.iPercistance.IDataStoreFactory;
import model.Wrapper;

public class GestioneNegozianteController extends HttpServlet implements IGestioneNegoziante {

    public static  IDataStore dataStore;
    @Override
    public void init(ServletConfig conf) throws ServletException {

        super.init(conf);
        IDataStoreFactory factory = new DataStoreControllerFactory();
         dataStore= factory.createInstance();
        
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException, NumberFormatException {
        Gson g= new Gson();
        String operazione=req.getParameter("operazione");
        switch (operazione) {
            case "catalogo":
                IGestioneCatalogo c=new GestioneCatalogoController();
                String username=(String) req.getSession().getAttribute("username"); 
               
                c.getCatalogo (dataStore.getNegoziante(username));
                Wrapper w= new Wrapper();
                w.setResult(c.getCatalogo (dataStore.getNegoziante(username)));
                w.setOperazione("catalogo");
                           
                resp.getWriter().println(g.toJson(w));
                break;

            case "changePrenotabile":
                
               
                break;
            case "aggiungiFumettoCatalogo":
                
                break;

            case "fumetteriePerPrenotazione":
               

                break;  

            case "rimuoviFumettoCatalogo":

               
                
                break;  


            default:
                break;
        }
        
}

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
