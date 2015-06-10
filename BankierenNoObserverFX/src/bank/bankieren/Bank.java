package bank.bankieren;

import bank.centralebank.ICentraleBank;
import fontys.util.*;
import java.rmi.AccessException;
import java.rmi.NotBoundException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import observer.BasicPublisher;
import observer.RemotePropertyListener;
import observer.RemotePublisher;

public class Bank extends UnicastRemoteObject implements IBank, IBankForCentrale, RemotePublisher {

    /**
     *
     */
    private static final long serialVersionUID = -8728841131739353765L;
    private ICentraleBank remoteCentraleBank;
    private Registry registry;
    private Map<Integer, IRekeningTbvBank> accounts;
    private Collection<IKlant> clients;
    private int nieuwReknr;
    private String name;
    private BasicPublisher publisher;

    public Bank(String name) throws RemoteException {
        accounts = new HashMap<Integer, IRekeningTbvBank>();
        clients = new ArrayList<IKlant>();
        nieuwReknr = 100000000;
        this.name = name;
        this.publisher = new BasicPublisher(new String[]{});
        bindCentraleBank();
        remoteCentraleBank.bindBanken();
    }

    public void bindCentraleBank(){
        try {
            registry = LocateRegistry.getRegistry("localhost",1100);
            remoteCentraleBank = (ICentraleBank) registry.lookup("Centrale");
        } catch (RemoteException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int openRekening(String name, String city) {
        if (name.equals("") || city.equals("")) {
            return -1;
        }

        IKlant klant = getKlant(name, city);
        int newRek = -1;
        try {
            newRek = remoteCentraleBank.getNieuwRekNR();
        } catch (RemoteException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("newRek: " + newRek);
        if(newRek == -1){
            return -1;
        }
        
        IRekeningTbvBank account = new Rekening(newRek, klant, Money.EURO);
        accounts.put(newRek, account);
        return newRek;
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
        boolean success = false;
        
        if (!rekeningVanBank(source)||!rekeningVanBank(destination)) {
            try {
                success = remoteCentraleBank.transactieTussenBanken(source, destination, money);
            } catch (RemoteException ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
        Money negative = Money.difference(new Money(0, money.getCurrency()),money);
        
        
        success = Afschrijven(source, negative);

        if (success){
            success = Bijschrijven(destination, money);
        }

        if (!success) // rollback
        {
            ((IRekeningTbvBank) getRekening(source)).muteer(money);
            return false;
        }
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
        publisher.addProperty(Integer.toString(nrVan));
        publisher.inform(this, Integer.toString(nrVan), null, getRekening(nrVan).getSaldo());
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
        publisher.addProperty(Integer.toString(nrNaar));
        publisher.inform(this, Integer.toString(nrNaar), null, getRekening(nrNaar).getSaldo());
        return success;
    }

    @Override
    public boolean rekeningVanBank(int rekNr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
        System.out.println("Listener toegevoegd aan bank: " + property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

}
