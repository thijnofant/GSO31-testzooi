package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import observer.BasicPublisher;
import observer.RemotePropertyListener;
import observer.RemotePublisher;

public class Bankiersessie extends UnicastRemoteObject implements
		IBankiersessie, RemotePropertyListener, RemotePublisher {

	private static final long serialVersionUID = 1L;
	private long laatsteAanroep;
	private int reknr;
	private IBank bank;
        private BasicPublisher publisher;

	public Bankiersessie(int reknr, IBank bank) throws RemoteException {
		laatsteAanroep = System.currentTimeMillis();
		this.reknr = reknr;
		this.bank = bank;
                this.publisher = new BasicPublisher(new String[]{});
                this.bank.addListener(this, Integer.toString(reknr));
		
	}

	public boolean isGeldig() {
		return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
	}

	@Override
	public boolean maakOver(int bestemming, Money bedrag)
			throws NumberDoesntExistException, InvalidSessionException,
			RemoteException {
		
		updateLaatsteAanroep();
		
		if (reknr == bestemming)
			throw new RuntimeException(
					"source and destination must be different");
		if (!bedrag.isPositive())
			throw new RuntimeException("amount must be positive");
		
		return bank.maakOver(reknr, bestemming, bedrag);
	}

	private void updateLaatsteAanroep() throws InvalidSessionException {
		if (!isGeldig()) {
			throw new InvalidSessionException("session has been expired");
		}
		
		laatsteAanroep = System.currentTimeMillis();
	}

	@Override
	public IRekening getRekening() throws InvalidSessionException,
			RemoteException {

		updateLaatsteAanroep();
		
		return bank.getRekening(reknr);
	}

	@Override
	public void logUit() throws RemoteException {
		UnicastRemoteObject.unexportObject(this, true);
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        publisher.inform(this, Integer.toString(reknr), null, (Money)evt.getNewValue());
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addProperty(property);
        publisher.addListener(listener, property);
        System.out.println("Listener toegevoegd aan banksessie: " + property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeProperty(property);
        this.bank.removeListener(this, property);
        publisher.removeListener(listener, property);
    }
}
