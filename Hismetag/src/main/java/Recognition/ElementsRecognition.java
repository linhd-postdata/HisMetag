/*
 * Copyright (C) 2017 mluisadiez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Recognition;

import java.io.BufferedReader;

import Data.*;
import MedievalTextLexer.Lexer;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author M Luisa Díez Platas
 * 
 *         elements recognition in a string
 * 
 */
public class ElementsRecognition {

	public static String hasAfixesOfName(String string)
			throws java.io.FileNotFoundException, java.io.IOException {
		/*File archivo = new File(path + "dataFiles/afixes.txt");
		FileReader fr = new FileReader(archivo);
		BufferedReader br = new BufferedReader(fr);
		String line;
		String pattern = "\"";
		while ((line = br.readLine()) != null) {
			pattern += " " + line + " |";
		}
		br.close();
		pattern.replaceAll("|$", "");
		pattern += "\"";
		Pattern patternCompile = Pattern.compile(pattern);

		Matcher encaja = patternCompile.matcher(string);
		if (encaja.find()) {

			return encaja.group();
		}*/
		return null;

	}

	public static boolean isPlural(String string) {

		return (string.charAt(string.length() - 1) == 's');
	}

	public static ArrayList<String> hasArticle(String string) {
		try {
                    String articlesList[]={"la","La","el","El","los","Los","las","Las"};

 			// System.out.println("el path en articulos es "+path);
			ArrayList<String> articles = new ArrayList<String>();
			/*File archivo = new File(path + "dataFiles/articles.txt");
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String pattern;

			while ((line = br.readLine()) != null) {*/
                    for (int i=0;i<articlesList.length;i++){
				if (string.contains(" " + articlesList[i] + " "))
					articles.add(articlesList[i]);

			}
			//br.close();
			return articles;
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isDeterminantPrep(String string) {

		return (string.equals("del") || string.equals("de"));

	}

	public static boolean isSingular(String string) {
		Verbs elVerb = Data.VerbsTable.contains(string);
		// System.out.println("el verbo"+elVerb+string);
		String desinencia = " ";
		String modo = " ";
		if (elVerb != null) {
			desinencia = elVerb.desinencia;
		}
		if (desinencia != " ") {
			modo = Data.DesiTable.getMode(desinencia);
		}

		// System.out.println("la desinencia modo"+modo);
		if (modo == "SL")
			return true;

		return false;
	}

	public static boolean isArticle(String string) {
		try {
                    String articlesList[]={"la","La","el","El","los","Los","las","Las"};

			/*File archivo = new File(path + "dataFiles/articles.txt");
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String pattern;*/
                        
                        for (int i=0;i<articlesList.length;i++){
				if (string.contains(" " + articlesList[i] + " "))
					return true;
                                if (string.contains(articlesList[i]))
					return true;
			}
			/*while ((line = br.readLine()) != null) {
				if (string.contains(" " + line + " "))
					return true;
				if (string.contains(line))
					return true;

			}
			br.close();*/
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static ArrayList<String> hasTreatment(String string) {
		try {
                    String treatmentList[]={"don","donna","sennor","sennora","senor","senyor"};
			ArrayList<String> treatment = new ArrayList<String>();
			/*File archivo = new File(path + "dataFiles/treatment-data.txt");
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String pattern;

			while ((line = br.readLine()) != null) {
				if (string.contains(line))
					articles.add(line);

			}
			br.close();*/
                        for (int i=0;i<treatmentList.length;i++){
				if (string.contains(treatmentList[i]))
					treatment.add(treatmentList[i]);

			}
			return treatment;
		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList<String> hasCopulativeConjunction(String string) {
		try {
			String conjunctionsList[]={"e","et","y","o","&"};
                        ArrayList<String> conjunctions = new ArrayList<String>();
			/*File archivo = new File(path + "dataFiles/copulative-conjunctions.txt");
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				if (string.contains(line)) {
					conjunctions.add(line);

				}

			}

			br.close();*/
                        for (int i=0;i<conjunctionsList.length;i++){
				if (string.contains( conjunctionsList[i]))
					conjunctions.add(conjunctionsList[i]);

			}
			return conjunctions;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getPreposition(String string) {
		String[] newString = string.split(" ");
		for (int i = 0; i < newString.length; i++) {
			if (ElementsRecognition.isDeterminantPrep(newString[i]))
				return newString[i];
		}
		return "";
	}

	public static ArrayList<String> isCopulativeConjunction(String string) {
		try {

                    String conjunctionsList[]={"e","et","y","o","&"};
                        ArrayList<String> conjunctions = new ArrayList<String>();
			// System.out.println("no se que esta psando en las copulativas");
			 for (int i=0;i<conjunctionsList.length;i++){
				if (string.equals(conjunctionsList[i]))
					conjunctions.add(conjunctionsList[i]);

			}
			// System.out.println( "dataFiles/copulative-conjunctions.txt");
		/*	File archivo = new File(path + "dataFiles/copulative-conjunctions.txt");
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				// System.out.println("la tama de la copulativa");

				if (string.equals(line)) {
					conjunctions.add(line);
				}

			}
			br.close();*/
			// System.out.println("la tama de la copulativa");
			// System.out.println(conjunctions.size());
			return conjunctions;
		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList<String> hasAppositionPrepositions(String string) {
		try {
			String prepositionsList[]={"del","Del","de","De","delos"};
                        ArrayList<String> prepositions = new ArrayList<String>();
			/*File archivo = new File(path + "dataFiles/apposition-prepositions.txt");
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				if (string.contains(line))
					prepositions.add(line);

			}
			br.close();*/
                        for (int i=0;i<prepositionsList.length;i++){
				if (string.contains(prepositionsList[i]))
					prepositions.add(prepositionsList[i]);

			}
			return prepositions;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Check if a word is capitalized
	 * 
	 * @param word
	 * @return
	 */
	static public boolean hasCapitalleter(String word) {

		return Character.isUpperCase(word.charAt(0));
	}

	static public boolean endsWithAuthority(String[] array) {
		return Recognition.TermsRecognition.isAuthority(array[array.length - 1].toLowerCase());
	}

	static public String verbExtraction(String word) {
		try {
			String suffixes[]={"las","los","la","lo","le","se","les"};
                       /* File archivo = new File(path + "dataFiles/verbs-suffixes.txt");

			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				int index = word.lastIndexOf(line);

				if (index > -1) {
					// res=verb.substring(0,index)+"-"+desinencia;
					int total = index + line.length();
					if (total == word.length()) {
						String verbo = word.substring(0, index);

						return verbo;
					}

				}

			}
			fr.close();*/
                        
                        for (int i=0; i<suffixes.length; i++){
                            int index = word.lastIndexOf(suffixes[i]);

				if (index > -1) {
					// res=verb.substring(0,index)+"-"+desinencia;
					int total = index + suffixes[i].length();
					if (total == word.length()) {
						String verbo = word.substring(0, index);

						return verbo;
					}

				}
                        }
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
