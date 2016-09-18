/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cimek;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Andor Kovács
 */
public class Cimek {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

//        1. Olvassa be az ip.txt állományban talált adatokat, s annak felhasználásával oldja meg a következő feladatokat! 
        BufferedReader bufferedReader = new BufferedReader(new FileReader("ip.txt"));
        ArrayList<IpCim> ipCimek = new ArrayList<>();
        String beolvasasSor; // Miért kell beletenni változóba?

        int dokumentaciosCimDarab = 0;
        int globalisEgyediCimDarab = 0;
        int helyiEgyediCimDarab = 0;

//        ArrayList<String> sokNullasIpCimek = new ArrayList<>();
        while ((beolvasasSor = bufferedReader.readLine()) != null) {
            ipCimek.add(new IpCim(beolvasasSor));

            char[] karakterek = ipCimek.get(ipCimek.size() - 1).cim.toCharArray();
            int karakterekSzama = 0;
            for (char c : karakterek) {
                if (c == '0') {
                    karakterekSzama++;
                }
            }
            ipCimek.get(ipCimek.size() - 1).nullasKarakterekSzama = karakterekSzama;
            if (karakterekSzama > 17) {
                ipCimek.get(ipCimek.size() - 1).sokNulla = true;
            }

            if (ipCimek.get(ipCimek.size() - 1).cim.startsWith("2001:0db8")) {
                ipCimek.get(ipCimek.size() - 1).tipus = Tipus.DOKUMENTACIOS_CIM;
                dokumentaciosCimDarab++;
            } else if (ipCimek.get(ipCimek.size() - 1).cim.startsWith("2001:0e")) {
                ipCimek.get(ipCimek.size() - 1).tipus = Tipus.GLOBALIS_EGYEDI_CIM;
                globalisEgyediCimDarab++;
            } else {
                ipCimek.get(ipCimek.size() - 1).tipus = Tipus.HELYI_EGYEDI_CIM;
                helyiEgyediCimDarab++;
            }
        }

        String legalasonyabbIpCim = ipCimek.get(0).cim;
        for (IpCim ipCim : ipCimek) {
            if (legalasonyabbIpCim.compareTo(ipCim.cim) > 0) {
                legalasonyabbIpCim = ipCim.cim;
            }
        }

        for (IpCim ipCim : ipCimek) {
            System.out.println(ipCim.nullasKarakterekSzama + " " + ipCim.sokNulla + " " + ipCim.cim + " " + ipCim.tipus);
        }
//        2. Határozza meg és írja a képernyőre, hogy hány adatsor van az állományban! 
        System.out.println("2. feladat:");
        System.out.println("Az állományban " + ipCimek.size() + " darab adatsor van.");

//        3. Írja a képernyőre az állományban található legalacsonyabb IP-címet! A megoldásában felhasználhatja, hogy a betűk ASCII-kódjai a számok ASCII-kódjai után találhatók a kódtáblában. 
        System.out.println("3. feladat:");
        System.out.println("A legalacsonyabb tárolt IP-cím:");
        System.out.println(legalasonyabbIpCim);

        //    4. Határozza meg, hogy az állományban hány darab IP-cím van az egyes fajtákból! Az eredményt jelenítse meg a képernyőn a mintának megfelelően! 
        System.out.println("4. feladat:");
        System.out.println("Dokumentációs cím: " + dokumentaciosCimDarab);
        System.out.println("Globális egyedi cím: " + globalisEgyediCimDarab);
        System.out.println("Helyi egyedi cím: " + helyiEgyediCimDarab);

//        5. Gyűjtse ki a sok.txt állományba azokat az IP-címeket, melyek legalább 18 nullát tartalmaznak! A fájlban minden sor elején szerepeljen az eredeti állományból a cím sorszáma! Ezt kövesse egy szóközzel elválasztva a cím az ip.txt állományban szereplő alakjával! 
        System.out.println("5. feladat:");
        PrintWriter output = new PrintWriter(new FileWriter("sok.txt"));

        for (int i = 0; i < ipCimek.size(); i++) {
            if (ipCimek.get(i).sokNulla) {
                output.println((i + 1) + " " + ipCimek.get(i).cim);
            }
        }
        output.close();
        System.out.println("A \"sok.txt\" fájl elkészítve.");

//        6. Kérjen be a felhasználótól egy sorszámot! Az állományban a megadott sorszámon található IP-címet rövidítse a csoportokon belüli bevezető nullák elhagyásával! Az állományban található alakot és a rövidített változatot írja a képernyőre egymás alá!
        System.out.println("6. feladat:");
        System.out.println(ipCimek.size());
        int userInput = Integer.parseInt(getInput("Kérek egy sorszámot: "));
        System.out.println(ipCimek.get(userInput-1).cim);
        
        ArrayList<String> kivalasztottIpCimCsoportjai = new ArrayList<>();
        for (String string : ipCimek.get(userInput-1).cim.split(":")) {
            if (string.startsWith("000")) {
                string = string.replaceFirst("000", "");
            } else if (string.startsWith("00")) {
                string = string.replaceFirst("00", "");
            } else if (string.startsWith("0")) {
                string = string.replaceFirst("0", "");
            }
            kivalasztottIpCimCsoportjai.add(string);
        }
        String newString = "";
//        for (String string : kivalasztottIpCimCsoportjai) {
//            newString += string;
//            newString += ":";
//        }
        newString = String.join(":", kivalasztottIpCimCsoportjai);
        System.out.println(newString);


//          7. Az előző feladatban használt IP-címet rövidítse tovább az egymást követő nullás csoportok rövidítésére vonatkozó szabályoknak megfelelően! Az eredményt jelenítse meg a képernyőn! Amennyiben nem rövidíthető, írja ki: „Nem rövidíthető tovább.”! 
        System.out.println("7. feladat:");
        boolean marModositva = false;
        boolean uzenet = false;
        if (newString.contains(":0:0:0:")) {
            newString = newString.replaceFirst(":0:0:0:", "::");
            marModositva = true;
        }
        if (marModositva) {
            uzenet = true;
        } else {
            newString = newString.replaceFirst(":0:0:", "::");
        }
        System.out.println(newString);
        if (uzenet) {
            System.out.println("Nem rövidíthető tovább.");
        }
    }
    
    private static String getInput(String prompt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush();

        try {
            return stdin.readLine();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static class IpCim {

        String cim;
        Tipus tipus;
        boolean sokNulla = false;
        int nullasKarakterekSzama;

        public IpCim(String cim) {
            this.cim = cim;
        }
    }

    private enum Tipus {
        DOKUMENTACIOS_CIM,
        GLOBALIS_EGYEDI_CIM,
        HELYI_EGYEDI_CIM
    }
}
