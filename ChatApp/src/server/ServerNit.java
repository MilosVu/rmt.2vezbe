package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerNit extends Thread {

	Socket soketZaKomunikaciju = null;
	BufferedReader tokOdKlijenta = null;
	PrintStream tokKaKlijentu = null;
	String korisnickoIme = null;

	public ServerNit(Socket soketZaKomunikaciju) {
		this.soketZaKomunikaciju = soketZaKomunikaciju;
	}

	public void run() {
		try {
			tokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			tokKaKlijentu = new PrintStream(soketZaKomunikaciju.getOutputStream());
			
			boolean isValid = false;
			
			do {
				tokKaKlijentu.println("Unesi korisnicko ime: ");
				korisnickoIme = tokOdKlijenta.readLine();
				if(korisnickoIme.contains(" ")) {
					tokKaKlijentu.println("Korisnicko ime ne sme da ima prazno mesto!");
				}else {
					isValid = true;
					tokKaKlijentu.println(">>> Dobrodosao " + korisnickoIme + 
							"\nZa izlazak unesite: ***quit");
				}
				
			}while(!isValid);
			
			for (ServerNit sn : Server.klijenti) {
				if(sn != this) {
					sn.tokKaKlijentu.println(">>> Korisnik "
							+ korisnickoIme + " je usao u chat sobu!");
				}
			}
			
			String porukaOdKlijenta = null;
			
			while(true) {
				porukaOdKlijenta = tokOdKlijenta.readLine();
				if(porukaOdKlijenta.equals("***quit")) 
					break;
				for(ServerNit sn : Server.klijenti) {
					if(sn!=this)
						sn.tokKaKlijentu.println("[" + this.korisnickoIme +"] " + porukaOdKlijenta);
				}
			}
			tokKaKlijentu.println(">>> Dovidjenja " + korisnickoIme);
			for (ServerNit sn : Server.klijenti) {
				if(sn != this) {
					sn.tokKaKlijentu.println(">>> Korisnik "
							+ korisnickoIme + " napistio chat sobu!");
				}
			}
			Server.klijenti.remove(this);
			
		} catch (IOException e) {
			for (ServerNit sn : Server.klijenti) {
				if(sn != this) {
					sn.tokKaKlijentu.println(">>> Korisnik "
							+ korisnickoIme + " napistio chat sobu!");
				}
			}
			Server.klijenti.remove(this);
			e.printStackTrace();
		}
	}

}
