import java.io.*;
import java.util.*;

public class Spellword {
    Hashtable<String,String> dictionary;   // To store all the words of the dictionary
    boolean suggestWord ;           // To indicate whether the word is spelled correctly or not.
     boolean correct =false;
Scanner scan = new Scanner(System.in);
    public static void main(String [] args)
    {
        Spellword checker = new Spellword();

    }

    public Spellword() {
        dictionary = new Hashtable<String, String>();
        System.out.print("Enter a word or sentence : ");
        try {

            //Read and store the words of the dictionary
            BufferedReader dictReader = new BufferedReader(new FileReader("Dictionary.txt"));
            Scanner sc = new Scanner(System.in);
            while (dictReader.ready()) {
                String dictInput = dictReader.readLine();
                String[] dict = dictInput.split("\\s");

                for (int i = 0; i < dict.length; i++) {
                    // key and value are identical
                    dictionary.put(dict[i], dict[i]);
                }
            }
            dictReader.close();


            // Initialising a spelling suggest object
            suggestword suggest = new suggestword("Dictionary.txt");

            // Reads input lines one by one

                while (sc.hasNextLine() != correct) {

                    String s = sc.next();
                    String output="";
                    String[] result = s.split("\\s");

                    for (int x = 0; x < result.length; x++) {
                        suggestWord = true;
                        String outputWord = checkWord(result[x]);

                        int count=0;
                        String con="a";

                        if (suggestWord) {

                           do{
                               if(suggest.correct(outputWord, count).equals("")){
                                   System.out.println("Sorry but no possible corrections found!");

                               }else{
                                System.out.println("Did you mean : " + suggest.correct(outputWord, count) + " (y/n) ");
                               con = scan.nextLine();
                                count++;}
                            }  while(con.equals("n"));

                        }
                    }

                }


            }
        catch(IOException e)
            {

                e.printStackTrace();

            }
        }


    public String checkWord(String wordToCheck)
    {
        String wordCheck, unpunctWord;
        String word = wordToCheck.toLowerCase();


        // if word is found in dictionary then it is spelt correctly, so return as it is.
        //note: inflections like "es","ing" provided in the dictionary itself.

        if ((wordCheck = (String)dictionary.get(word)) != null)
        {
            suggestWord = false;            // no need to ask for suggestion for a correct word.
            System.out.println(wordCheck + " is spelled correctly");

            return wordCheck;

        }

        // Removing punctuations at end of word and giving it a shot ("." or "." or "?!")
        int length = word.length();


        //Checking for the beginning of quotes(example: "she )
        if (length > 1 && word.substring(0,1).equals("\""))
        {
            unpunctWord = word.substring(1, length);

            if ((wordCheck = (String)dictionary.get(unpunctWord)) != null)
            {
                suggestWord = false;            // no need to ask for suggestion for a correct word.
                return wordCheck ;
            }
            else // not found
                return unpunctWord;                  // removing the punctuations and returning
        }

        // Checking if "." or ",",etc.. at the end is the problem(example: book. when book is present in the dictionary).
        if( word.substring(length - 1).equals(".")  || word.substring(length - 1).equals(",") ||  word.substring(length - 1).equals("!")
                ||  word.substring(length - 1).equals(";") || word.substring(length - 1).equals(":"))
        {
            unpunctWord = word.substring(0, length-1);

            if ((wordCheck = (String)dictionary.get(unpunctWord)) != null)
            {
                suggestWord = false;            // no need to ask for suggestion for a correct word.
                return wordCheck ;
            }
            else
            {
                return unpunctWord;                  // removing the punctuations and returning
            }
        }

        // Checking for "!\"",etc ...  in the problem (example: watch!" when watch is present in the dictionary)
        if (length > 2 && word.substring(length-2).equals(",\"")  || word.substring(length-2).equals(".\"")
                || word.substring(length-2).equals("?\"") || word.substring(length-2).equals("!\"") )
        {
            unpunctWord = word.substring(0, length-2);

            if ((wordCheck = (String)dictionary.get(unpunctWord)) != null)
            {
                suggestWord = false;            // no need to ask for suggestion for a correct word.
                return wordCheck ;
            }
            else // not found
                return unpunctWord;                  // removing the inflections and returning
        }

        // After all these checks too, word could not be corrected, hence it must be misspelled word. return and ask for suggestions
        return word;
    }

}
