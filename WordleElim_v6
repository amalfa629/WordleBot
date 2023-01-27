package archiveSolver;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Runner {
	public static WebDriver driver = null;
	public static String[][] evaluationKey = {{"absent","GRAY"},{"present","YELLOW"},{"correct","GREEN"}};
	public static ArrayList<String> dictionary = new ArrayList<String>();
	public static ArrayList<String> possible = new ArrayList<String>();
	public static ArrayList<Character> word = new ArrayList<Character>();
	public static int total = 0;
	public static int correctNum = 0;
	public static int totalGuessNum = 0;
	public static int puzzleNum=0;
	public static ArrayList<String> getInput() {
		ArrayList<String> possible=new ArrayList<String>();
		try {
			BufferedReader 
				br=new BufferedReader(new FileReader(".\\supplemental\\wordleWords.txt"));
			String s;
			while(((s=br.readLine())!=null)) {
				if(s.length()==5) {
					possible.add(s);
				}
			}
			br.close();
		} catch(Exception e) {System.out.println(e);}
		return possible;
	}
	public static ArrayList<Character> addToWord(ArrayList<Character> word, char c) {
		if(!word.contains(c)) {
			word.add(c);
		}
		return word;
	}
	public static String recommendWord() {
		int[] alpha = new int[26];
		int index = 0;
		for(int n=0; n<possible.size(); n++) {
			for(int i=0; i<possible.get(n).length(); i++) {
				char c = possible.get(n).charAt(i);
				if(!word.contains(c)) {
					alpha[(int)(c)-65]++;
				}
			}
		}
		if(word.size()<3) {
			int[] rate = new int[dictionary.size()];
			for(int n=0; n<dictionary.size(); n++) {
				char[] observe = dictionary.get(n).toCharArray();
				for(int b=1; b<observe.length; b++) {
					for(int c=0; c<b; c++) {
						if(observe[b]==observe[c]) {
							observe[c]='.';
						}
					}
				}
				String o = "";
				for(int c=0; c<observe.length; c++) {
					if(observe[c]!='.') {
						o=o+observe[c];
					}
				}
				for(int i=0; i<o.length(); i++) {
					rate[n]=rate[n]+alpha[(int)(o.charAt(i))-65];
				}
			}
			for(int n=0; n<dictionary.size(); n++) {
				if(rate[n]>rate[index]) {
					index=n;
				}
			}
			System.out.println(dictionary.get(index)+" is the recommended word.");
			return dictionary.get(index);
		}
		else {
			int[] rate = new int[possible.size()];
			for(int n=0; n<possible.size(); n++) {
				for(int i=0; i<possible.get(n).length(); i++) {
					alpha[(int)(possible.get(n).charAt(i))-65]++;
				}
			}
			for(int n=0; n<possible.size(); n++) {
				char[] observe = possible.get(n).toCharArray();
				for(int b=1; b<observe	.length; b++) {
					for(int c=0; c<b; c++) {
						if(observe[b]==observe[c]) {
							observe[c]='.';
						}
					}
				}
				String o = "";
				for(int c=0; c<observe.length; c++) {
					if(observe[c]!='.') {
						o=o+observe[c];
					}
				}
				for(int i=0; i<o.length(); i++) {
					rate[n]=rate[n]+alpha[(int)(o.charAt(i))-65];
				}
			}
			for(int n=0; n<possible.size(); n++) {
				if(rate[n]>rate[index]) {
					index=n;
				}
			}
			System.out.println(possible.get(index)+" is the recommended word.");
		}
		return possible.get(index);
	}
	public static void eliminateWords(String guess, Character[] delete, Character[] contains, Character[] exact) {
		ArrayList<String> temp = new ArrayList<String>();
		temp.clear();
		temp.addAll(possible);
		for(int n=0; n<5; n++) {
			if(contains[n]!='.') {
				word = addToWord(word, guess.charAt(n));
			}
			else if(exact[n]!='.') {
				word = addToWord(word, guess.charAt(n));
			}
		}
		int remove=0;
		for(int n=0; n<possible.size(); n++) {
			boolean exit=false;
			for(int i=0; (!exit)&&(i<5); i++) {
				if(((Arrays.asList(delete).contains(possible.get(n).charAt(i)))&&(!word.contains(possible.get(n).charAt(i))))||(delete[i]==possible.get(n).charAt(i))||((contains[i]!='.')&&((!possible.get(n).contains(Character.toString(contains[i])))||((possible.get(n)).charAt(i)==contains[i])))||((exact[i]!='.')&&((possible.get(n)).charAt(i)!=exact[i]))) {
					temp.remove(possible.get(n));
					exit=true;
					remove++;
				}
			}
		}
		possible=temp;
		total=total-remove;
		System.out.println(remove+" words were removed.");
	}
	public static void main(String[] args) {
		dictionary=getInput();
		possible.addAll(dictionary);
		total=possible.size();
		System.setProperty("webdriver.chrome.driver",".\\drivers\\chromedriver.exe");
	    ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().implicitlyWait(100,TimeUnit.MILLISECONDS);
	    for(int p=0; p<=473; p++) {
	    	puzzleNum=p;
		    driver.navigate().to("https://wordlearchive.com/"+p);
		    //if(p==1) {driver.findElement(By.xpath("/html/body/div[4]/div/div/div/button")).click();}
		    boolean correct = false;
		    for(int w=0; w<6&&!correct; w++) {
		    	String r = recommendWord();
		    	//driver.findElement(By.xpath("/html/body")).click();
		    	for(int l=0; l<5; l++) {
		    		driver.findElement(By.xpath("/html/body")).sendKeys(Character.toString(r.charAt(l)));
		    	}
		    	driver.findElement(By.xpath("/html/body")).sendKeys(Keys.ENTER);
		    	Character[] delete = {'.','.','.','.','.'};
				Character[] contains = {'.','.','.','.','.'};
				Character[] exact = {'.','.','.','.','.'};
			    for(int n=1; n<=5; n++) {
			    	String evaluation = driver.findElement(By.xpath("//*[@id=\"board\"]/game-row[1]//div/game-tile[1]//div")).getAttribute("data-state");
			    	System.out.println(evaluation);
			    	//int i=0;
			    	//while(color.charAt(i)!=')') {i++;}
			    	//color=color.substring(0,i+1);
			    	for(int a=0; a<evaluationKey.length;a++) {
			    		if(evaluationKey[a][0].equals(evaluation)) {
			    			switch(evaluationKey[a][1]) {
								case "GREEN":
									exact[n-1]=r.charAt(n-1);
									word = addToWord(word, r.charAt(n-1));
									break;
								case "YELLOW":
									contains[n-1]=r.charAt(n-1);
									word = addToWord(word, r.charAt(n-1));
									break;
								case "GRAY":
									delete[n-1]=r.charAt(n-1);
									break;
							}
			    		}
			    	}
			    }
			    if(Arrays.asList(exact).contains('.')) {
			    	eliminateWords(r, delete, contains, exact);
			    }
			    else {
			    	correct=true;
			    	totalGuessNum=totalGuessNum+w+1;
			    	correctNum++;
			    }
			}
		    dictionary=getInput();
			possible.clear();
			word.clear();
			possible.addAll(dictionary);
			total=possible.size();
			if(correctNum>0) {
	    		System.out.println("Average guesses per correct answer: "+((double)totalGuessNum/(double)correctNum));
	    		System.out.println("Percent of correct guesses: "+((((double)correctNum/(double)puzzleNum))*(double)100)+"%");
	    	}
		}
	}
}
