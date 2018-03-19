import java.util.ArrayList;
import java.util.Random;

/**
 * Class designed to gain data and mimic simple speech.
 * 
 * @author Brandon Dalla Rosa
 *
 */
public class ResponseSpeech {
	ArrayList<WordData> words;
	boolean hasinit = false;
	String[] keyWords = {"what","why","who","where","when"};
	ArrayList<String> starters = new ArrayList<String>();
	ResponseSpeech(){
		words = new ArrayList<WordData>();
		words.add(new WordData("hello"));
	}
	public void addInfo(String input) {
		input = input.toLowerCase();
		handleInput(input);	
	}
	public void addInput(String input) {
		input = input.toLowerCase();
		String[] in = input.split(" ");
		for(int i=0;i<keyWords.length;i++) {
			for(int ii=0;ii<in.length;ii++) {
				if(in[ii].equals(keyWords[0])) {
					starters.add("The");
				}
				if(in[ii].equals(keyWords[1])) {
					starters.add("Because");
				}
				if(in[ii].equals(keyWords[2])) {
					starters.add("They");
				}
				if(in[ii].equals(keyWords[3])) {
					starters.add("The");
				}
				if(in[ii].equals(keyWords[4])) {
					starters.add("The");
				}
			}
		}	
	}
	/**
	 * Method called to return the output given the input string. It then
	 * uses the data to calculate responses.
	 * 
	 * @param input
	 * @return
	 */
	public String respond() {
		
		String output = "";
		String next = "";
		if(starters.size()>0) {
			next = starters.get(0);
		}
		else {
			next = "I";
			if(words.size()>2) {
				next = words.get(2).getWord();
			}
		}
		WordData current = new WordData(next);
		current.addWord("am");
		current.addWord("hate");
		current.addWord("enjoy");
		current.addWord("think");
		for(WordData w : words) {
			if(w.getWord().equals(next)) {
				current = w;
			}
		}
		output += current.getWord()+" ";
		next = current.nextWord();
		Random rand = new Random();
		for(int i=0;i<20;i++) {  //20
			boolean nextInWords = false;
			for(WordData data : words) {
				String c = data.getWord();
				if(next.equals(c)) {
					current = data;
					nextInWords = true;
				}
			}
			if(!current.nextWord().equals(next) && nextInWords) {
				output += current.getWord()+" ";
				next = current.nextWord();
				if(current.getWord().contains(".") || current.getWord().contains("!")|| current.getWord().contains("?")) {
					break;
				}
			}
			else {
				int w = rand.nextInt(words.size());
				current = words.get(w);
				next = current.nextWord();
			}
		}
		output = output.replace("  "," ");
		char p = output.charAt(0);
		if(p>96) {
			p = (char) (output.charAt(0)-32);
		}
		output = p+output.substring(1, output.length()-1);
		return output;
	}
	
	/**
	 * Method called to handle the current string input from the
	 * data source.
	 * 
	 * @param current
	 */
	private void handleInput(String current) {
		current = current.toLowerCase();
		current = current.replace(")","");
		current = current.replace("(","");
		for(int i=0;i<10;i++) {
			current = current.replaceAll(""+i, "");
		}
		String[] parts = current.split(" ");
		for(int i=0;i<parts.length;i++) {
			WordData next = new WordData("");
			words.add(next);
			if(!contains(parts[i]) && !parts[i].equals("hubabababa")) {
				next = new WordData(parts[i]);
				words.add(next);
			}
			else {
				for(int ii=0;ii<words.size();ii++) {
					if(words.get(ii).getWord().equals(parts[i])) {
						next = words.get(ii);
					}
				}
			}
			if(i!=parts.length-1) {
				next.addWord(parts[i+1]);
			}
		}
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
		ArrayList<String> nextWords;
		WordData(String thisWord){
			nextWords = new ArrayList<String>();
			word = thisWord;
		}
		public void addWord(String next) {
			nextWords.add(next);
		}
		public String getWord() {
			return word;
		}
		public String nextWord() {
			Random rand = new Random();
			if(nextWords.size()<1) {
				nextWords.add("hubabababa");
			}
			int w = rand.nextInt(nextWords.size());
			String output = "";
			if(nextWords.size()>0) {
				output = nextWords.get(w);
			}
			else {
				w = rand.nextInt(words.size());
				output = words.get(w).getWord();
			}
			return output;
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
