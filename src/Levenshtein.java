public class Levenshtein {

    public int minimumOperand(int suppression, int insertion, int substitution) {
        return Math.min(Math.min(suppression, insertion), substitution);
    }

    public int levenshteinCompute(CharSequence correctWord, CharSequence wrongWord) {
        int[][] distance = new int[correctWord.length() + 1][wrongWord.length() + 1];
        for (int i = 0; i <= correctWord.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= wrongWord.length(); j++)
            distance[0][j] = j;
        for (int i = 1; i <= correctWord.length(); i++)
            for (int j = 1; j <= wrongWord.length(); j++)
                distance[i][j] = minimumOperand(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((correctWord.charAt(i - 1) == wrongWord.charAt(j - 1)) ? 0 : 1));

        System.out.println("La distance est de : " + distance[correctWord.length()][wrongWord.length()]);

        return distance[correctWord.length()][wrongWord.length()];
    }

}