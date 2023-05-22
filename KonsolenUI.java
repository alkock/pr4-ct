import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KonsolenUI extends MetrikObserver {
    Map<Metrik, Boolean> welcheMetriken;
    Map<Metrik, Boolean> metrikenBerechnet;

    Map<Metrik, int[][]> berchneteMetriken;
    String aktuellerDateiname;


    public KonsolenUI() {
        observers.add(this);
        welcheMetriken = new HashMap<Metrik, Boolean>();
        welcheMetriken.put(Metrik.MMBÜ, false);
        welcheMetriken.put(Metrik.MCDC, false);
        welcheMetriken.put(Metrik.EBÜ, false);
        metrikenBerechnet = new HashMap<Metrik, Boolean>();
        metrikenBerechnet.put(Metrik.MMBÜ, false);
        metrikenBerechnet.put(Metrik.MCDC, false);
        metrikenBerechnet.put(Metrik.EBÜ, false);
        berchneteMetriken = new HashMap<Metrik, int[][]>();
        aktuellerDateiname = "";


    }

    public void consoleClear() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public void hauptmenu() {
        consoleClear();

        System.out.println("\n" +
                "    __  ___     __       _ __  \n" +
                "   /  |/  /__  / /______(_) /__\n" +
                "  / /|_/ / _ \\/ __/ ___/ / //_/\n" +
                " / /  / /  __/ /_/ /  / / ,<   \n" +
                "/_/  /_/\\___/\\__/_/  /_/_/|_|  \n" +
                "                               \n");
        System.out.println("Erstellt von Leon, Marvin und Ansgar");
        System.out.println(
                "+---------------------------------------------------------------------------------------------------------------+-------+\n"
                        + "| Auswahl                        								                |Eingabe|       |Ausgewählt|\n"
                        + "+---------------------------------------------------------------------------------------------------------------+-------+\n"
                        + "| MMBÜ 							                                    		 	| 1     |       | " + (((boolean) welcheMetriken.get(Metrik.MMBÜ)) ? "ja" : "nein") + " |   \n"
                        + "| MCDC									                                		| 2     |       | " + (((boolean) welcheMetriken.get(Metrik.MCDC)) ? "ja" : "nein") + " |   \n"
                        + "| EBÜ    										                            	| 3     |       | " + (((boolean) welcheMetriken.get(Metrik.EBÜ)) ? "ja" : "nein") + " |   \n"
                        + "| Weiter						                                                | 4     |\n"
                        + "| Beende das Programm										            		| 5     |\n"
                        + "+---------------------------------------------------------------------------------------------------------------+-------+");
        int loop = 0;
        System.out.print("Eingabe:");
        while (loop == 0) {
            Scanner sc = new Scanner(System.in);
            String eingabe = sc.next();
            if (eingabe.matches("[1-5]")) {
                loop = Integer.parseInt(eingabe);
            } else {
                System.out.println("Keine Korrekte Eingabe bitte erneut versuchen! Format 1-5 wird benötigt!");
                System.out.println("");
                System.out.print("Eingabe:");

            }

        }
        switch (loop) {
            case 1:
                welcheMetriken.put(Metrik.MMBÜ, !welcheMetriken.get(Metrik.MMBÜ));
                hauptmenu();
                break;
            case 2:
                welcheMetriken.put(Metrik.MCDC, !welcheMetriken.get(Metrik.MCDC));
                hauptmenu();
                break;
            case 3:
                welcheMetriken.put(Metrik.EBÜ, !welcheMetriken.get(Metrik.EBÜ));
                hauptmenu();
                break;
            case 4:
                dateiauswahl();
            case 5:
                System.exit(0);

        }
    }

    public void dateiauswahl() {
        consoleClear();

        System.out.println("\n" +
                "    __  ___     __       _ __  \n" +
                "   /  |/  /__  / /______(_) /__\n" +
                "  / /|_/ / _ \\/ __/ ___/ / //_/\n" +
                " / /  / /  __/ /_/ /  / / ,<   \n" +
                "/_/  /_/\\___/\\__/_/  /_/_/|_|  \n" +
                "                               \n");
        System.out.println("Erstellt von Leon, Marvin und Ansgar");
        System.out.println("Bitte gebe nun den Dateinamen der Datei im Verzeichnis an:");
        int loop = 0;
        System.out.print("Eingabe:");
        while (loop == 0) {
            Scanner sc = new Scanner(System.in);
            String eingabe = sc.next();
            if (eingabe.contains(".md")) {
                loop = 1;
                aktuellerDateiname = eingabe;
                uebergebeDaten(eingabe);
            } else {
                System.out.println("Keine Korrekte Eingabe bitte erneut versuchen! Format .md wird benötigt!");
                System.out.println("");
                System.out.print("Eingabe:");

            }
        }
    }

    public void uebergebeDaten(String eingabe)   {
        IOWriter ioWriter = new IOWriter();
        try {
            int[][] zuErarbeitendesArray = IOWriter.readMarkdownFile(eingabe);
            for (Metrik metrik : welcheMetriken.keySet()) {
                if (welcheMetriken.get(metrik)) {
                    MetrikObserver.updateObservers(this, metrik, zuErarbeitendesArray);
                }
            }


        } catch (IOException io) {
            System.out.println("Datei konnte nicht gefunden werden!");
            System.out.println("Bitte erneut versuchen!");
            dateiauswahl();
        }

        if(welcheMetriken.keySet().isEmpty())
        {
            System.out.println("Keine Metrik ausgewählt! Von daher bin ich jetzt fertig! :-)");
            try{
                Thread.sleep(5000);
            }
            catch(InterruptedException io){

            }

            hauptmenu();
        }
    }

    @Override
    public void update(MetrikObserver vonWem, Metrik metrik, int[][] aufgabe) {
        if (vonWem == this) return;

        if (metrik == Metrik.MMBÜ && vonWem == null) {
            metrikenBerechnet.put(Metrik.MMBÜ, true);
            berchneteMetriken.put(Metrik.MMBÜ, aufgabe);
            checkObAllesReturnt();
        }
        if (metrik == Metrik.EBÜ && vonWem == null) {
            metrikenBerechnet.put(Metrik.EBÜ, true);
            berchneteMetriken.put(Metrik.EBÜ, aufgabe);
            checkObAllesReturnt();
        }
        if (metrik == Metrik.MCDC && vonWem == null) {
            metrikenBerechnet.put(Metrik.MCDC, true);
            berchneteMetriken.put(Metrik.MCDC, aufgabe);
            checkObAllesReturnt();
        }
    }

    public void checkObAllesReturnt() {
        if (welcheMetriken.equals(metrikenBerechnet)) {
            System.out.println("Alle Metriken wurden berechnet!");
            IOWriter.write(berchneteMetriken, aktuellerDateiname);
        }

    }
}
