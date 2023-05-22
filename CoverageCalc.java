public class CoverageCalc extends MetrikObserver{

    CoverageCalc()
    {
        observers.add(this);
    }

    public static void main(String[] args) {

    }

    public int ermittleMcDc(int[][] array) {
        int count = 0;
        array = ArrayErweitern(array);

        for (int i = 0; i < array.length; i++) {
            boolean hatNull = false;

            for (int j = 0; j < array[i].length-1; j++) {
                if (array[i][j] == 0) {
                    hatNull = true;
                    array[i][array[i].length-1] = 0;
                    break;
                }
                if(!hatNull)
                {
                    array[i][array[i].length-1] = 1;
                }
            }
            if (!hatNull) {
                count++;
            }
        }

        // Ausgabe des erweiterten Arrays
        for (int[] row : array) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }

        updateObservers(this,Metrik.MCDC, array);
        return count;
    }

    public int ermittleMMBÜ(int[][] array) {
        int count = 0;
        array = ArrayErweitern(array);

        for (int i = 0; i < array.length; i++) {
            boolean hatNull = false;

            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != 0) {
                    hatNull = true;
                    array[i][array[i].length-1] = 1;
                    break;
                }
            }

            if (hatNull) {
                count++;
            }
        }

        updateObservers(this,Metrik.MMBÜ, array);
        return count;
    }

    public static int[][] ArrayErweitern(int[][] array) {
        int numRows = array.length;
        int numCols = array[0].length;

        int[][] expandedArray = new int[numRows][numCols + 1];

        for (int i = 0; i < numRows; i++) {
            expandedArray[i] = new int[numCols + 1];

            for (int j = 0; j < numCols; j++) {
                expandedArray[i][j] = array[i][j];
            }

            // Füge den Wert 0 am Ende der Zeile hinzu
            expandedArray[i][numCols] = 0;
        }

        return expandedArray;
    }

    @Override
    public void update(MetrikObserver vonWem, Metrik metrik, int[][] aufgabe) {
        if (vonWem == this) return;

        ErmittleSigVar esv = new ErmittleSigVar();
        int[][] sigVar = esv.ermittleSignifikanteVariablen(aufgabe);

        if (metrik == Metrik.MMBÜ)
        {
            ermittleMMBÜ(sigVar);
        }
        if (metrik == Metrik.MCDC)
        {
            ermittleMcDc(sigVar);
        }
    }
}