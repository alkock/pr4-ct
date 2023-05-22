import java.util.ArrayList;
import java.util.List;

public abstract class MetrikObserver {

    static List<MetrikObserver> observers = new ArrayList<MetrikObserver>();


    public abstract void update(MetrikObserver vonWem, Metrik metrik, int[][] aufgabe);

    public void subscribe(MetrikObserver observer) {
        observers.add(observer);
    }

    public static void updateObservers(MetrikObserver duSelber, Metrik metrik, int[][] aufgabe) {
        for (MetrikObserver observer : observers) {
            observer.update(duSelber, metrik, aufgabe);
        }
    }

}
