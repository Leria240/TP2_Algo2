import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Dico {
    HashMap<String, ArrayList<String>> dico = new HashMap<>();

    /**
     * @brief : read the dico
     * @param file
     */
    public void readDico (File file) {
        ArrayList<String> words = reader(file);
        long start = System.nanoTime();
        for(String word : words) {
            this.dico.put(word, makeTrigramme(word));
        }
        long end = System.nanoTime();
        System.out.println("Le temps d'exécution est de  : " + ((end - start)/Math.pow(10, 9)) + " secondes");
    }

    /**
     * @brief : read the mistakes
     * @param file
     */
    public void readMistakes (File file) {
        ArrayList<String> words = reader(file);
        long start = System.nanoTime();
        for(String word : words) {
            bestTrigrams(word);
        }
        long end = System.nanoTime();
        System.out.println("Le temps d'exécution est de  : " + ((end - start)/Math.pow(10, 9)) + " secondes");
    }

    /**
     * @brief : split the word into a trigram
     * @param mot
     */
    public ArrayList<String> makeTrigramme (String mot){
        ArrayList<String> trigramme = new ArrayList<>();
        for (int i = 0 ; i < mot.length()-2; i++){
            trigramme.add(i, mot.substring(i, i+3));
        }
        System.out.println(trigramme);
        return trigramme;
    }

    /**
     * @brief : descending HashMap sorting
     * @param hashMap
     */
    public HashMap<String, Integer> descendantSort(HashMap<String, Integer> hashMap) {
        hashMap = hashMap.entrySet().stream().sorted(HashMap.Entry.comparingByValue(Collections.reverseOrder())).collect(Collectors.toMap(
                HashMap.Entry::getKey,
                HashMap.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new));

        return hashMap;
    }

    /**
     * @brief : ascending HashMap sorting
     * @param hashMap
     */
    public HashMap<String, Integer> ascendantSort(HashMap<String, Integer> hashMap) {
        hashMap = hashMap.entrySet().stream().sorted(HashMap.Entry.comparingByValue()).collect(Collectors.toMap(
                HashMap.Entry::getKey,
                HashMap.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new));

        return hashMap;
    }

    /**
     * @brief : only i words of the HashMap
     * @param hashMap
     * @param size
     */
    public HashMap<String, Integer> limitHashMap(HashMap<String, Integer> hashMap, int size) {
        int i = 0;
        HashMap<String, Integer> sortedOccurences = new HashMap<>();
        for (HashMap.Entry<String, Integer> lines : hashMap.entrySet()) {
            if (i == size) break;
            sortedOccurences.put(lines.getKey(), lines.getValue());
            i += 1;
        }
        return sortedOccurences;
    }

    /**
     * @brief : test if the word is in the dico
     * @param word
     */
    public ArrayList<String> bestTrigrams(String word) {
        ArrayList<String> listCommonTrigramsL = new ArrayList<>();
        //Instruction 1
        if (this.dico.containsKey(word)) {
            return null;
        } else {
            //Instruction 2
            ArrayList<String> listTrigramsM = makeTrigramme(word);
            HashMap<String, Integer> nbOccurrences = new HashMap<>();
            //Instruction 3
            for (HashMap.Entry<String, ArrayList<String>> lines : dico.entrySet()) {
                int cpt = 0;
                for (int i = 0; i < listTrigramsM.size()-1; i++) {
                    if (lines.getValue().contains(listTrigramsM.get(i))) {
                        listCommonTrigramsL.add(lines.getKey());
                        cpt += 1;
                    }
                }
                //Instruction 3/4
                if(cpt > 0){
                    nbOccurrences.put(lines.getKey(), cpt);
                }

            }
            //Instruction 5
            nbOccurrences = descendantSort(nbOccurrences);
            nbOccurrences = limitHashMap(nbOccurrences,100);

            //Instruction 6
            HashMap<String, Integer> fiveSelectedWords = new HashMap<>();
            Levenshtein levenshtein = new Levenshtein();
            for (String key : nbOccurrences.keySet()) {
                fiveSelectedWords.put(key, levenshtein.levenshteinCompute(word, key));
            }
            fiveSelectedWords = ascendantSort(fiveSelectedWords);
            fiveSelectedWords = limitHashMap(fiveSelectedWords, 5);

            Set<String> keySet = fiveSelectedWords.keySet();
            return new ArrayList<String>(keySet);
        }
    }

    /**
     * @brief : read a file
     * @param file
     */
    public ArrayList<String> reader (File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> words = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null ) {
                words.add(line);
            }
            br.close();
            return words;

        } catch (FileNotFoundException e){
            System.out.println("No file");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

