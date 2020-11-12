import java.io.File;

public class Test {
    public static void main(String[] args) {
        File dictionnary = new File("dico.txt");
        File mistakes = new File("fautes.txt");
        Dico dico = new Dico();
        dico.readDico(dictionnary);
        dico.readMistakes(mistakes);
        System.out.println(dico.bestTrigrams("logarytmique"));
    }
}