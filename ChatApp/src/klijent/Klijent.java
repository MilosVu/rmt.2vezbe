package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Klijent implements Runnable{

	//Paralelono rade
	//Main i run
	
	static BufferedReader tokOdServera = null;
	static PrintStream tokKaServeru = null;
	static Socket soketZaKomunikaciju = null;
	static BufferedReader konzola = null;
	
	public static void main(String[] args) {
		try {
			soketZaKomunikaciju = new Socket("10.30.20.31", 14000);
			
			tokKaServeru = new PrintStream(soketZaKomunikaciju.getOutputStream());
			tokOdServera = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			
			konzola = new BufferedReader(new InputStreamReader(System.in));
			
			String porukaOdServera;
			new Thread(new Klijent()).start();
			while(true) {
				porukaOdServera = tokOdServera.readLine();
				System.out.println(porukaOdServera);
				
				if(porukaOdServera.startsWith(">>> Dovidjenja")) 
					break;
			}
			soketZaKomunikaciju.close();
		} catch (UnknownHostException e) {
			System.out.println("Server nije pdoignut...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greska prilikom pokretanja klijenta");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String porukaKaServeru;
		
		try {
			
			while(true) {
			
			porukaKaServeru = konzola.readLine();
			tokKaServeru.println(porukaKaServeru);
			if(porukaKaServeru.equals("***quit")) 
				break;
			}
		} catch (IOException e) {
			System.out.println("Greska");
			e.printStackTrace();
		}
	}
}
