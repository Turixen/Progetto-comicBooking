package controller.implementazioni;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controller.datastore.DataStoreControllerFactory;
import controller.interfacce.iAcquirente.IPrenotaFumetto;
import controller.interfacce.iPercistance.IDataStore;
import controller.interfacce.iPercistance.IDataStoreFactory;
import model.fumetteria.Fumetteria;
import model.fumetteria.NumeroCopie;
import model.fumetto.Fumetto;
import model.prenotazioni.Prenotazione;
import model.user.Acquirente;
import model.user.Negoziante;

public class PrenotaFumettoController implements IPrenotaFumetto{

    @Override
    public boolean prenotaFumetto(Fumetto fu, Fumetteria f, Acquirente a) {
        // TODO Auto-generated method stub
        Prenotazione p=new Prenotazione();
        p.setAcquirente(a);

        List<NumeroCopie> listCopie=f.getCatalogo().getNumeroCopie();
        List<Prenotazione> prenotazioni=a.getPrenotazioni();

        for(NumeroCopie n:listCopie){
            if(n.getFumetto().equals(fu)){
               p.setCopia(n);
               p.setDataPrenotazione(LocalDateTime.now());
               p.setCompletata(false);
               
               //aggiorni prenotazioni dell'utente
               prenotazioni.add(p);
               a.setPrenotazioni(prenotazioni);
               return true;
            }
        }
        
        return false;
    }

    @Override
    public List<Fumetteria> getFumetterie(Fumetto f) {
        // TODO Auto-generated method stub
        List<Fumetteria>fumetterie=new ArrayList<Fumetteria>();

        IDataStoreFactory factory = new DataStoreControllerFactory();
        IDataStore dataStore= factory.createInstance();
        List<Negoziante> list=dataStore.getNegozianti();
       
        for(Negoziante n:list){
            int count=0;
           for(NumeroCopie copie:n.getFumetteria().getCatalogo().getNumeroCopie()){
               
                if(copie.getFumetto().equals(f) && copie.getPrenotabile() &&copie.getDisponibilita()>0  ){
                    for(Prenotazione p:copie.getPrenotazioni()){
                    
                    LocalDateTime tempo2=p.getDataPrenotazione().plusDays(copie.getTempoValidita());
                    boolean expired=LocalDateTime.now().isAfter(tempo2);
                    if(!p.isCompletata() && !expired){
                        count++;
                    }
                }
                fumetterie.add(n.getFumetteria());
           } 
           if(count>=copie.getDisponibilita()){

               System.out.println("non prenotabile");

           }else{
               fumetterie.add(n.getFumetteria());
           }
        }
        
    }
    
   


        return fumetterie;
}

    @Override
    public List<Fumetto> getFumetti() {
        // TODO Auto-generated method stub
        List<Fumetto>fumetti=new ArrayList<Fumetto>();

        IDataStoreFactory factory = new DataStoreControllerFactory();
        IDataStore dataStore= factory.createInstance();
        fumetti=dataStore.getFumetti();

        return fumetti;
    }
    
}
