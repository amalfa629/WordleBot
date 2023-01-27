//Largely the same as v1, however recommendation function is based off of entire dictionary and comparing to letters not deleted or in word


import java.io.*;
import java.util.*;
import javax.swing.*;

public class WordleElim_v3 {
	public static ArrayList<String> getInput() {
			ArrayList<String> possible=new ArrayList<String>();
			try {
				BufferedReader 
					br=new BufferedReader(new FileReader("C:\\Users\\amalf\\Code\\Java\\wordleWords.txt"));
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
	public static void print(ArrayList<String> possible) {
		for(int n=0; n<possible.size(); n++) {
			System.out.println(possible.get(n));
		}
	}
	public static void main(String[] a) {
		ArrayList<String> dictionary=getInput();
		ArrayList<String> possible =  new ArrayList<String>();
		possible.addAll(dictionary);
		Scanner scn = new Scanner(System.in);
		String inp; //input string
		char cmd; //input
		boolean quit = false; //exit clause
		int x=1;
		int total=possible.size();
		ArrayList<Character> word = new ArrayList<Character>();
		while(quit==false) {  //as long as command 'Q' is not run
			ArrayList<String> temp =  new ArrayList<String>();
			temp.addAll(possible);
			System.out.println(total+" words remaining.");
			inp = scn.nextLine().toUpperCase(); //gets new input line
			cmd = inp.charAt(0); //gets new input command
			if (cmd=='I'||cmd=='C'||cmd=='D'||cmd=='E'||cmd=='H') { //if a string parameter is needed
				while(inp.charAt(x)!=' ') { //searches for end of input word
					x++;
				}
				while(inp.charAt(x)==' ') { //finds beginning of input string
					x++;
				}
				inp=inp.substring(x);
			}
			switch(cmd) {
				case 'D': //does not contain letter
					int remove=0;
					for(int n=0; n<possible.size(); n++) {
						boolean exit=false;
						for(int i=0; (!exit)&&(i<inp.length()); i++) {
							if(possible.get(n).contains(Character.toString(inp.charAt(i)))) {
								temp.remove(possible.get(n));
								exit=true;
								remove++;
							}
						}
					}
					possible=temp;
					total=total-remove;
					System.out.println(remove+" words removed.");
					break;
				case 'C': //does contain letter but not here; input formatted with . as blank space and letter in "yellow" space
					int remove2=0;
					for(int n=0; n<possible.size(); n++) {
						boolean exit=false;
						for(int i=0; i<inp.length(); i++) {
							if(!exit&&(inp.charAt(i)!='.')&&((!possible.get(n).contains(Character.toString(inp.charAt(i))))||((possible.get(n)).charAt(i)==inp.charAt(i)))) {
								temp.remove(possible.get(n));
								remove2++;
								exit=true;
							}
						}
					}
					for(int i=0; i<inp.length(); i++) {
						if((inp.charAt(i)!='.')&&!word.contains(inp.charAt(i))) {
							word.add(inp.charAt(i));
						}
					}
					possible=temp;
					total=total-remove2;
					System.out.println(remove2+" words removed.");
					break;
				case 'E': //does contain letter and it is here; input formatted with . as blank space and letter in "green" space
					int remove3=0;
					for(int n=0; n<possible.size(); n++) {
						boolean exit=false;
						for(int i=0; i<inp.length(); i++) {
							if(!exit&&(inp.charAt(i)!='.')&&((possible.get(n)).charAt(i)!=inp.charAt(i))) {
								temp.remove(possible.get(n));
								remove3++;
								exit=true;
							}
						}
					}
					for(int i=0; i<inp.length(); i++) {
						if((inp.charAt(i)!='.')&&!word.contains(inp.charAt(i))) {
							word.add(inp.charAt(i));
						}
					}
					possible=temp;
					total=total-remove3;
					System.out.println(remove3+" words removed.");
					break;
				case 'R':	//recommends word based on rating system
					int[] alpha = new int[26];
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
						int index=0;
						for(int n=0; n<dictionary.size(); n++) {
							if(rate[n]>rate[index]) {
								index=n;
							}
						}
						System.out.println(dictionary.get(index)+" is the recommended word.");
					}
					else {
						int[] rate = new int[possible.size()];
						for(int n=0; n<possible.size(); n++) {
							for(int i=0; i<possible.get(n).length(); i++) {
								alpha[(int)(possible.get(n).charAt(i))-65]++;
							}
						}
						/*for(int n=0; n<alpha.length; n++) {
							System.out.println((char)(n+65)+": "+alpha[n]);
						}*/
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
						int index=0;
						for(int n=0; n<possible.size(); n++) {
							if(rate[n]>rate[index]) {
								index=n;
							}
						}
						System.out.println(possible.get(index)+" is the recommended word.");
					}
					break;
				case 'A': //resets for new game
					dictionary=getInput();
					possible.clear();
					word.clear();
					possible.addAll(dictionary);
					total=possible.size();
					break;
				case 'P': //prints remaining words
					print(possible);
					break;
				case 'Q': //exits program
					quit=true;
					break;
				default: //input is not formatted correctly
					JFrame frame = new JFrame("ERROR");
					JOptionPane.showMessageDialog(frame, "Input Not Recognized");
					break;
			}
			x=1;
		}
	}
}

//combine c and h functions -> DONE
//figure out double letters
//reccomend word function, based upon most unique letters+most reocurring letters -> Mostly completed