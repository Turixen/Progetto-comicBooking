package controller.implementazioni.AcquirenteController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controller.interfacce.iAcquirente.IPrenotaFumetto;
import model.fumetteria.Catalogo;
import model.fumetteria.Fumetteria;
import model.fumetteria.NumeroCopie;
import model.fumetto.Fumetto;
import model.prenotazioni.Prenotazione;
import model.user.Acquirente;
import model.user.Negoziante;

public class PrenotaFumettoController implements IPrenotaFumetto{

    @Override
    public boolean prenotaFumetto(Fumetto fu, Fumetteria f, Acquirente a) {
        
        Prenotazione p=new Prenotazione();
        p.setAcquirente(a);
        Catalogo cat = f.getCatalogo();
        List<NumeroCopie> listCopie = cat.getNumeroCopie();
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
        
        List<Fumetteria>fumetterie=new ArrayList<Fumetteria>();
        List<Negoziante> list=GestioneAcquirenteController.dataStore.getNegozianti();
       
        for(Negoziante n:list){
            int count=0;
           for(NumeroCopie copie:n.getFumetteria().getCatalogo().getNumeroCopie()){
                System.out.println(f==null);
                System.out.println(copie.getPrenotabile());
                System.out.println(copie.getDisponibilita());
                System.out.println(f.getTitolo());
                System.out.println(copie.getFumetto().getTitolo());
                System.out.println(f.getNumero());
                System.out.println(copie.getFumetto().getNumero());
                System.out.println(f.getDescrizione());
                System.out.println(copie.getFumetto().getDescrizione());
                System.out.println(f.getCasaEditrice());
                System.out.println(copie.getFumetto().getCasaEditrice());
                if(copie.getFumetto().equals(f) && copie.getPrenotabile() &&copie.getDisponibilita()>0  ){
                    
                    //controllo prenotazioni pending 
                    count=0;
                    for(Prenotazione p:copie.getPrenotazioni()){
                    
                        LocalDateTime tempo2=p.getDataPrenotazione().plusDays(copie.getTempoValidita());
                        boolean expired=LocalDateTime.now().isAfter(tempo2);
                        
                        if(!p.isCompletata() && !expired){
                            count++;
                        }
                    
                      
                    
                    }
                    if(count>=copie.getDisponibilita()){

                        System.out.println("non prenotabile");
    
                    }else{
                        fumetterie.add(n.getFumetteria());
                    }
                
                } 
                
            }
        
        }
        return fumetterie;
    }

    @Override
    public List<Fumetto> getFumetti() {
        
        List<Fumetto>fumetti=new ArrayList<Fumetto>();
        fumetti=GestioneAcquirenteController.dataStore.getFumetti();

        return fumetti;
    }
    
}
