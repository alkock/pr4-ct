import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KonsolenUI extends MetrikObserver{
    Map<Metrik, Boolean> welcheMetriken;

    public KonsolenUI() {
        observers.add(this);
        welcheMetriken = new HashMap<Metrik, Boolean>();
        welcheMetriken.put(Metrik.MMBÜ, false);
        welcheMetriken.put(Metrik.MCDC, false);
        welcheMetriken.put(Metrik.EBÜ, false);

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
                System.out.println("Keine Korrekte Eingabe bitte erneut versuchen! Format .md wird benötigt!");
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
                uebergebeDaten(eingabe);
            }
            else {
                System.out.println("Keine Korrekte Eingabe bitte erneut versuchen! Format .md wird benötigt!");
                System.out.println("");
                System.out.print("Eingabe:");

            }
        }
    }
    public void uebergebeDaten(String eingabe) {
        IOWriter ioWriter = new IOWriter();
        try {
            int[][] zuErarbeitendesArray = IOWriter.readMarkdownFile(eingabe);
            for(Metrik metrik : welcheMetriken.keySet())
            {
                if(welcheMetriken.get(metrik))
                {
                    MetrikObserver.updateObservers(this, metrik,zuErarbeitendesArray);
                }
            }


        } catch (IOException io)
        {
            System.out.println("Datei konnte nicht gefunden werden!");
            System.out.println("Bitte erneut versuchen!");
            dateiauswahl();
        }
    }

    @Override
    public void update(MetrikObserver vonWem, Metrik metrik, int[][] aufgabe) {
        if(vonWem == this) return;

    }

}
