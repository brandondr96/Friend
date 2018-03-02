import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class designed to gain data and mimic simple speech.
 * 
 * @author Brandon Dalla Rosa
 *
 */
public class Speech {
	ArrayList<WordData> words;
	String[] poeText = {"alone","assignat","atale","balloon","berenice","blackcat","cask","eleonora","fallushr","maelstrm","masque_r","raven","rue","telltale","the_pit","usher"};
	boolean hasinit = false;
	Speech(){
		words = new ArrayList<WordData>();
		words.add(new WordData("hello"));
	}
	
	/**
	 * Method called to return the output given the input string. It initializes on the first call and then
	 * uses the data to calculate responses.
	 * 
	 * @param input
	 * @return
	 */
	public String respond(String input) {
		if(!hasinit) {
			for(int i=0;i<poeText.length;i++) {
				initialize(poeText[i]);
			}
			hasinit = true;
		}
		String[] in = input.split(" ");
		String output = "";
		String t = in[in.length-1];
		boolean plz = false;
		for(WordData w : words) {
			if(w.getWord().equals(t)) {
				plz=true;
			}
		}
		String next = "";
		WordData current = new WordData("");
		Random rand = new Random();
		if(plz) {
			next = t;
		}
		else {
			int w = rand.nextInt(words.size());
			current = words.get(w);
			output += current.getWord()+" ";
			next = current.nextWord();
		}
		for(int i=0;i<20;i++) {
			for(WordData data : words) {
				String c = data.getWord();
				if(next.equals(c)) {
					current = data;
				}
			}
			if(!current.nextWord().equals(next)) {
				output += current.getWord()+" ";
				next = current.nextWord();
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
	 * Method called to initialize the data in the program for speech generation. It is only
	 * called a single time.
	 * 
	 * @param word
	 */
	public void initialize(String word) {
		try {
			URL website = new URL("http://www.textfiles.com/etext/AUTHORS/POE/"+word+".txt");
			InputStream inst = website.openStream();
			InputStreamReader red = new InputStreamReader(inst);
			BufferedReader buf = new BufferedReader(red);
			String current = "";
			while((current = buf.readLine())!=null) {
				if(current.contains(" ")&& !current.contains("<") && !current.contains(">") && !current.contains("/")&& !current.contains("%")&& !current.contains(":")) {
					handleInput(current);
				}
			}
			inst.close();
			red.close();
			buf.close();
		}
		catch (MalformedURLException error) {
			System.out.println("URL error.");
		}
		catch (IOException error) {
			System.out.println("IO error.");
		}
	}
	
	/**
	 * Method called to handle the current string input from the online
	 * datasource.
	 * 
	 * @param current
	 */
	private void handleInput(String current) {
		current = current.replaceAll("[^a-zA-Z]"," ");
		current = current.replace("px", " ");
		current = current.replace(" y ", " ");
		current = current.replace(" x ", " ");
		current = current.replace("xlink"," ");
		current = current.replace(" org ", " ");
		current = current.replace(" w "," ");
		current = current.replace(" www "," ");
		current = current.replace(" http "," ");
		current = current.replace(" xmlns "," ");
		current = current.replace(" svg "," ");
		current = current.replace(" Capa "," ");
		String[] parts = current.split(" ");
		for(int i=0;i<parts.length;i++) {
			WordData next = new WordData("");
			words.add(next);
			if(!contains(parts[i]) && !parts[i].equals("dead")) {
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
				nextWords.add("dead");
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
	

}
