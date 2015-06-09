package bank.bankieren;

import fontys.util.*;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank implements IBank, IBankForCentrale {

    /**
     *
     */
    private static final long serialVersionUID = -8728841131739353765L;
    private Map<Integer, IRekeningTbvBank> accounts;
    private Collection<IKlant> clients;
    private int nieuwReknr;
    private String name;

    public Bank(String name) {
        accounts = new HashMap<Integer, IRekeningTbvBank>();
        clients = new ArrayList<IKlant>();
        nieuwReknr = 100000000;
        this.name = name;
    }

    public int openRekening(String name, String city) {
        if (name.equals("") || city.equals("")) {
            return -1;
        }

        IKlant klant = getKlant(name, city);
        IRekeningTbvBank account = new Rekening(nieuwReknr, klant, Money.EURO);
        accounts.put(nieuwReknr, account);
        nieuwReknr++;
        return nieuwReknr - 1;
    }

    private IKlant getKlant(String name, String city) {
        for (IKlant k : clients) {
            if (k.getNaam().equals(name) && k.getPlaats().equals(city)) {
                return k;
            }
        }
        IKlant klant = new Klant(name, city);
        clients.add(klant);
        return klant;
    }

    public IRekening getRekening(int nr) {
        return accounts.get(nr);
    }

    public boolean maakOver(int source, int destination, Money money)
            throws NumberDoesntExistException {
        if (source == destination) {
            throw new RuntimeException(
                    "cannot transfer money to your own account");
        }
        if (!money.isPositive()) {
            throw new RuntimeException("money must be positive");
        }

        Money negative = Money.difference(new Money(0, money.getCurrency()),money);
        boolean success;
        
        success = Afschrijven(source, negative);

        if (success){
            success = Bijschrijven(destination, money);
        }

        if (!success) // rollback
        {
            ((IRekeningTbvBank) getRekening(source)).muteer(money);
            return false;
        }
        return success;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean Afschrijven(int nrVan, Money amount) {
        boolean success = true;
        IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(nrVan);
        if (source_account == null) {
            try {
                throw new NumberDoesntExistException("account " + nrVan
                        + " unknown at " + getName());
            } catch (NumberDoesntExistException ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Race condition
        synchronized (getRekening(nrVan)) {
            success = source_account.muteer(amount);
        }

        if (!success) {
            return false;
        }
        return true;
    }

    @Override
    public boolean Bijschrijven(int nrNaar, Money amount) {
        boolean success = false;
        IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(nrNaar);
        if (dest_account == null) {
            try {
                throw new NumberDoesntExistException("account " + nrNaar
                        + " unknown at " + name);
            } catch (NumberDoesntExistException ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Race condition
        synchronized (getRekening(nrNaar)) {
            success = dest_account.muteer(amount);
        }
        return success;
    }

    @Override
    public boolean rekeningVanBank(int rekNr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
