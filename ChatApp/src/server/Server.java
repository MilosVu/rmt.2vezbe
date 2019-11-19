package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Server {
	public static LinkedList<ServerNit> klijenti = new LinkedList<>();
	public static void main(String[] args){
		
		try {
			ServerSocket serverSoket = new ServerSocket(14000);
			Socket soketZaKomunikaciju = null;
			while(true) {
				System.out.println("Cekam konekciju...");
				soketZaKomunikaciju = serverSoket.accept();
				System.out.println("Prihvacena konekcija...");
				
				ServerNit noviKlijent = new ServerNit(soketZaKomunikaciju);
				klijenti.add(noviKlijent);
				
				noviKlijent.start();
			}
			
		} catch (IOException e) {
			System.out.println("Greska prilikom pokretanja servera... " + e.getMessage());
		}
		
	}
	
}
