/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import java.rmi.Remote;

/**
 *
 * @author Thijn
 */
public interface IBankForCentrale extends Remote {
    /**
     * Deze methode Zorgt dat de bank het bedrag afschrijft van de meegegeven rekening.
     * @param nrVan het rekeningsNummer van de rekening waar het geld van komt.
     * @param amount De hoeveelheid geld dat overgeschreven gaat worden.
     * @return true als het gelukt is, false als het niet gelukt is.
     */
    public boolean Afschrijven(int nrVan, Money amount);
    
    /**
     * Deze methode Zorgt dat de bank het bedrag bijschrijft naar de meegegeven rekening.
     * @param nrNaar het rekeningsNummer van de rekening waar het geld heen gaat.
     * @param amount De hoeveelheid geld dat overgeschreven gaat worden.
     * @return true als het gelukt is, false als het niet gelukt is.
     */
    public boolean Bijschrijven(int nrNaar, Money amount);
    
    /**
     * Deze methode controleerd of de rekening bij deze bank hoort
     * @param rekNr
     * @return true als de rekening bekend is bij die bank, false als hij niet bij deze bank hoort
     */
    public boolean rekeningVanBank(int rekNr);
}
