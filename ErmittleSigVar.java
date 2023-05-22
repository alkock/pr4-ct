import java.util.Arrays;

public class ErmittleSigVar {


    public static void main(String[] args) {
        int[][] table = {
                {0, 0, 0, 1},
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 1},
                {1, 0, 0, 0},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 1}
        };

        ErmittleSigVar esv = new ErmittleSigVar();
        esv.ermittleSignifikanteVariablen(table);

    }


    public int[][] ermittleSignifikanteVariablen(int[][] aufgabe)
    {

        int counter = 0;
        int[][] vars = new int[aufgabe.length][aufgabe[0].length-1];
        int[][] vars2 = new int[aufgabe.length][aufgabe[0].length-1];
        int[][] aufgabe2 = Arrays.copyOf(aufgabe, aufgabe.length);
        int[] cond = new int[aufgabe.length];

        for(int i = 0; i < aufgabe.length; i++)
        {
            for(int j = 0; j < aufgabe[0].length-1; j++)
            {
                vars[i][j] = aufgabe2[i][j];
                vars2[i][j] = aufgabe2[i][j];
            }
            cond[i] = aufgabe2[i][aufgabe[0].length-1];
        }

        System.out.println("vars: ");
        for(int i = 0; i < vars.length; i++)
        {
            for(int j = 0; j < vars[0].length; j++)
            {
                System.out.print(vars[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("cond: ");
        for(int i = 0; i < cond.length; i++)
        {
            System.out.println(cond[i] + " ");
        }

        int[][] result = new int[aufgabe.length][aufgabe[0].length-1];

        for(int y = 0; y < vars[0].length; y++)
        {
            counter = 1;
            int[] checked = new int[vars.length];
            for(int x = 0; x < vars.length; x++)
            {
                int[] target = Arrays.copyOf(vars[x], vars[x].length);
                target[y] = (target[y] == 0) ? 1 : 0;
                int match = findMatch(vars2, target);
                if(cond[x] == cond[match])
                {
                    result[x][y] = 0;
                    checked[x] = 1;
                    checked[match] = 1;
                }
                else
                {
                    if(checked[x] == 0 && checked[match] == 0){
                    result[x][y] = counter;
                    result[match][y] = counter;
                    checked[x] = 1;
                    checked[match] = 1;
                    System.out.println("match between " + x + " and " + match + " at " + y + " with counter " + counter);
                    counter++;
                }}
            }
        }

        System.out.println("result: ");
        for(int i = 0; i < result.length; i++)
        {
            for(int j = 0; j < result[0].length; j++)
            {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        return result;
    }


    public int findMatch(int[][] table, int[] target) {
        for (int i = 0; i < table.length; i++) {
            int diffCount = 0;
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] != target[j]) {
                    diffCount++;
                }
            }
            if (diffCount == 0) {
                return i;
            }
        }
        return -1;
    }

}

