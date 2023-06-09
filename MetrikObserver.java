import java.util.ArrayList;
import java.util.List;

public abstract class MetrikObserver {

    public static List<MetrikObserver> observers = new ArrayList<MetrikObserver>();


    public abstract void update(MetrikObserver vonWem, Metrik metrik, int[][] aufgabe);


    public static void updateObservers(MetrikObserver duSelber, Metrik metrik, int[][] aufgabe) {
        for (MetrikObserver observer : observers) {
            observer.update(duSelber, metrik, aufgabe);
        }
    }

}
