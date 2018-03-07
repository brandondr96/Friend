import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class designed to gain data and mimic simple speech.
 * 
 * @author Brandon Dalla Rosa
 *
 */
public class neuroTester {
	ArrayList<WordData> words;
	Speech improv = new Speech();
	String in4speech = "";
	WordData prev;
	String[] keyWords = {"what","why","who","where","when"}; // These will be created by training eventually
	boolean hasinit = false;
	boolean add = true;
	
	neuroTester(){
		improv.getPrevData();
		prev = new WordData("");
		words = new ArrayList<WordData>();
		words.add(new WordData("I don't know how to respond."));
		getPrevData();
	}
	
	/**
	 * Method called to return the output given the input string. It then
	 * uses the data to calculate responses.
	 * 
	 * @param input
	 * @return
	 */
	public String respond(String input) {
		if(input.contains("--")) {
			return "";
		}
		in4speech = input.split(" ")[0];
		input = input.toLowerCase();
		String output = handleInput(input);	
		for(WordData w : words) {
			if(w.getWord().equals(output)) {
				prev = w;
			}
		}
		if(add) {
			prev.addPhrase(output);
			add = true;
		}
		return output;
	}
	
	/**
	 * Method called to handle the current string input from the
	 * data source.
	 * 
	 * @param current
	 */
	private String handleInput(String current) {
		current = current.toLowerCase();
		current = current.replace(")","");
		current = current.replace("(","");
		for(int i=0;i<10;i++) {
			current = current.replaceAll(""+i, "");
		}
		String output = getSimilarOutput(current);
		return output;
	}
	
	/**
	 * Method called to handle training the initial configuration
	 * 
	 * @param current
	 */
	private void handleTrain(String current) {
		current = current.toLowerCase();
		current = current.replace(")","");
		current = current.replace("(","");
		prev.addPhrase(current);
		if(!contains(current)) {
			WordData wd = new WordData(current);
			words.add(wd);
			prev = wd;
		}
		else {
			for(WordData w : words) {
				if(w.getWord().equals(current)) {
					prev = w;
				}
			}
		}
	}
	
	private String getSimilarOutput(String input) {
		double[] scores = new double[words.size()];
		for(int i=0;i<words.size();i++) {
			scores[i] = checkSimilarity(input,words.get(i).getWord());
		}
		double maxScore = 0;
		int maxLoc = 0;
		for(int i=0;i<scores.length;i++) {
			if(scores[i]>maxScore) {
				maxScore = scores[i];
				maxLoc = i;
			}
		}
		String output = words.get(maxLoc).nextWord();
		return output;
	}
	
	/**
	 * Method to check similarity to known responses
	 * 
	 * @param current
	 * @param other
	 * @return
	 */
	private double checkSimilarity(String current, String other) {
		double sim = 0;
		for(int i=0;i<keyWords.length;i++) {
			if(current.contains(keyWords[i])&& other.contains(keyWords[i])) {
				sim += 5;
			}
		}
		String[] currentWords = current.split(" ");
		for(int i=0;i<currentWords.length;i++) {
			if(other.contains(currentWords[i])) {
				sim+=1;
			}
		}
		sim += (double)(100 - Math.abs(current.length()-other.length()))/100;
		for(int i=0;i<26;i++) {
			char cur = 'a';
			String toCheck = ""+(char)(cur+i);
			if(current.contains(toCheck)&&other.contains(toCheck)) {
				sim += (double) 0.05;
			}
		}
		return sim;
	}
	/**
	 * Method called to see if the current string is already in the words data.
	 * 
	 * @param c
	 * @return
	 */
	private boolean contains(String c) {
		for(WordData w : words) {
			if(w.getWord().equals(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Class to hold the data of the current word and its possible next words.
	 * 
	 * @author Brandon Dalla Rosa
	 *
	 */
	private class WordData{
		String word;
		ArrayList<String> nextPhrases;
		WordData(String thisWord){
			nextPhrases = new ArrayList<String>();
			//nextPhrases.add(improv.respond(in4speech));
			word = thisWord;
		}
		public void addPhrase(String next) {
			nextPhrases.add(next);
		}
		public String getWord() {
			return word;
		}
		public String nextWord() {
			Random rand = new Random();
			if(nextPhrases.size()<1) {
				String nran = improv.respond(in4speech);
				WordData nprev = new WordData(nran);
				words.add(nprev);
				add = false;
				return nran;
			}
			int w = rand.nextInt(nextPhrases.size());
			String output = "";
			output = nextPhrases.get(w);
			return output;
		}
	}
	
	/**
	 * Method called to add common phrases to the memory list of
	 * the program.
	 */
	private void getCommon() {
		String current = "";
		InputStream inStream = Speech.class.getResourceAsStream("common.txt");
		Scanner scan = new Scanner(inStream);
		scan.useDelimiter("\n");
		while((current = scan.nextLine()) !=null && scan.hasNextLine()) {
			handleTrain(current);
		}
		scan.close();
	}
	
	/**
	 * Method called to add all the data into memory for access 
	 * by the program.
	 */
	public void getPrevData() {
		if(!hasinit) {
			hasinit = true;
			getCommon();
		}
	}
	
	/**
	 * Method called to reset the memory of the 
	 * program and also clear the screen.
	 */
	public void clearMemory() {
		words = new ArrayList<WordData>();
		hasinit = false;
	}
	

}
