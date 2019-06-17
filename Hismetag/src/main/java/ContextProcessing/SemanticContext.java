/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package ContextProcessing;

import Data.AuthorityNamesTable;
import Data.BuildingsTable;
import Data.GeographicNamesTable;
import Data.MedievalNewPlaceNamesTable;
import Data.SaintsTable;
import Data.TreatmentsTable;
import IOModule.Input;
import IOModule.Output;
import MedievalTextLexer.Lexer;
import Recognition.ElementsRecognition;
import Recognition.InfoFound;
import Recognition.NewTermsIdentification;
import Recognition.Terms;
import Recognition.TermsRecognition;
import Recognition.TypesTerms;
import Recognition.VerificationInfo;
import StringInProcess.TokenizedString;
import StringNgramms.Ngramms;
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;
import WordProcessing.LevenshteinDistance;
import DataStructures.*;

import java.util.*;

import jdk.nashorn.internal.runtime.Context;
import Data.Verbs;

/**
 *
 * @author mluisadiez
 */
abstract public class SemanticContext {

	public String readCleanString;
	

	public SemanticContext() {
		
	}

	public SemanticContext(SemanticContext previous) {
		// if (previous!=null);
		
		Lexer.previousContextStack.push(previous);

	}

	public final BagData typeContext(String string, SemanticContext previous)
			throws java.io.IOException, java.io.FileNotFoundException {
		// System.out.println("la palabra que he encontrado es "+string);

		// System.out.println("tipo context 1");
		// Lexer.wbag.escribir();
		if (Data.ContextPlaceNames.contains(string.toLowerCase())) {

			BagData bgd = new PlaceNameBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext(), false, true);
			bgd.type = Terms.NPLN;

			Lexer.context.changeContext(ContextualList.PLACE, Lexer.context, " ", string);
			return bgd;
		}

		// System.out.println("tipo context 2");
		// Lexer.wbag.escribir();

		if (Data.FamilyNamesTable.contains(string.toLowerCase())) {
			// System.out.println("ES UN HIJO");
			BagData bgd = new FamilyBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext());

			Lexer.context.changeContext(ContextualList.FAMILY, Lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 3");
		// Lexer.wbag.escribir();

		if (Data.AuthorityNamesTable.contains(string.toLowerCase())) {
			// Lexer.wbag.escribir();
			BagData bgd = new AuthorityBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext());

			DataStructures.BagData bd;
			Lexer.previousContextStack.push(previous);
			Lexer.context = new AuthorityContext(previous);
			return bgd;
		}
		// System.out.println("tipo context 4");
		// System.out.println ("estoy en la salida de geografic"+string);
		if (Data.GeographicNamesTable.contains(string.toLowerCase())) {

			BagData bgd = new GeographicBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext(), false);

			Lexer.context.changeContext(ContextualList.GEOGRAPHIC, Lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 5");
		if (Data.BuildingsTable.contains(string.toLowerCase())) {
			// System.out.println("he entrado por aqui con el building");
			BagData bgd = new BuildingBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext(), false);
			Lexer.context.changeContext(ContextualList.BUILDINGS, Lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 6");
		// System.out.println("antes de saint "+string+Lexer.context.getContext());
		if (Data.SaintsTable.contains(string.toLowerCase())) {
			BagData bgd = new SaintBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext());

			Lexer.context.changeContext(ContextualList.SAINT, Lexer.context, " ", string);

			return bgd;
		}

		// System.out.println("tipo context 7");
		if (Data.PreviousNamesTable.contains(string.toLowerCase())) {
			BagData bgd = new OrgBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext(), false);

			Lexer.context.changeContext(ContextualList.PREVIOUS, Lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 8");
		// System.out.println("despues de place");
		if (Data.TreatmentsTable.contains(string.toLowerCase())) {
			BagData bgd = new HonorificBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext());
			Lexer.context.changeContext(ContextualList.TREATMENT, Lexer.context, " ", string);

			return bgd;

		}

		// System.out.println("tipo context 9");
		if (Data.PosessiveTable.contains(string.toLowerCase())) {
                   // System.out.println("POSESIVO TAM DE WBAG"+Lexer.wbag.tam());
                    if (Lexer.wbag.tam()>0){
			BagData bgd = new PossesiveBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext());
			Lexer.context.changeContext(ContextualList.POSESSIVE, Lexer.context, " ", string);
			return bgd;}
		}

		// System.out.println("tipo context 10");
		// System.out.println("el contexto es SALIDA DE DETERMINE
		// CONTEXT"+Lexer.context.getContext()+" "+string);
		return null;
		// Lexer.context.getContext();

	}

	public final ContextualList determineContext(String string, SemanticContext previous)
			throws java.io.IOException, java.io.FileNotFoundException {
		System.out.println("la palabra que he encontrado es "+string);
                
		if (Data.ContextPlaceTerms.contains(string.toLowerCase())) {

			//System.out.println("EL COMPLEMEBTO DEL VERBO "+string+" verbo"+Lexer.isTheFirst);
			// "+Lexer.verbsFlag+Lexer.isTheFirst);
			// System.out.println("HE ENTRADO POR EL CONTEXTO PLACE"+string+ "
			// "+Lexer.verbsFlag.verb+" "+Lexer.isTheFirst);
			Lexer.prepositionFlag = string;
			if (Lexer.isTheFirst) {
				Lexer.wbag.restart();
				Output.write(new RoleTreeNode(string));
				Lexer.isTheFirst = false;
				return ContextualList.PLACE;
			}
                        
                        
			if (Lexer.verbsFlag != null && Lexer.verbsFlag.verb!=" ") {
//System.out.println("no llego PROPROOPPROOOAQUI"+Lexer.verbsFlag.verb);
				ArrayList<String> lista = Data.VerbsTable.getComplements(Lexer.verbsFlag.verb);

				if (lista.contains(string.toLowerCase()) || lista.contains("N")) {
					// System.out.println("si la contien"+Lexer.verbsFlag.type);
					if (Lexer.verbsFlag.type.equals("PL")) {
						Lexer.wbag.restart();
						// System.out.println("ESTIY EBN CONTEXTOOOOOOOOOO");
						Output.write(new RoleTreeNode(string));
						return ContextualList.PLACE;
					}
				}

				else {
					// System.out.println("salgo por aqui con el verbo buscado");
					return ContextualList.SAME;

					// System.out.println("FLAG VERB "+Lexer.verbsFlag.verb);
				}
			} else { //System.out.println("lallalllalllallallaPREOPOA");
				// Output.write(new RoleTreeNode(string));
				if (Lexer.isTheFirst == true) {
					Output.write(new RoleTreeNode(string));
					return ContextualList.PLACE;
				}

				return ContextualList.SAME;
			}

			// Lexer.context=new PlaceContext(previous);

		}

		if (Data.ContextPlaceNames.contains(string.toLowerCase())) {
			Lexer.wbag.restart();
			BagData bgd = new PlaceNameBagData(string, TypesTerms.PPT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), Lexer.context.getContext(), false, true);
			bgd.type = Terms.NPLN;
			Lexer.wbag.put(bgd);

			return ContextualList.PLACE;
		}

		if (Data.FamilyNamesTable.contains(string.toLowerCase())) {
			// System.out.println("ES UN HIJO");

			Lexer.lastToken = string;

			return ContextualList.FAMILY;
		}
		// System.out.println("ANTES DE AUTHORITY "+string);
		if (Data.AuthorityNamesTable.contains(string.toLowerCase())) {
                        if (Lexer.wbag.containsTypeTerms(Terms.ART)){
                          Lexer.wbag.borrar(Terms.ART, Lexer.wbag.tam()-1);
                            
                         
                        }
			return ContextualList.AUTHORITY;
		}
		// System.out.println ("estoy en la salida de geografic"+string);
		if (Data.GeographicNamesTable.contains(string.toLowerCase())) {

			Lexer.lastToken = string;

			return ContextualList.GEOGRAPHIC;
		}

		if (Data.BuildingsTable.contains(string.toLowerCase())) {
			// System.out.println("he entrado por aqui con el building");
			Lexer.lastToken = string;

			return ContextualList.BUILDINGS;
		}

		// System.out.println("antes de saint "+string+Lexer.context.getContext());
		if (Data.SaintsTable.contains(string.toLowerCase())) {
			Lexer.lastToken = string;
			// System.out.println("he netrado por aqui en los dantos");

			return ContextualList.SAINT;
		}
		if (Data.PreviousNamesTable.contains(string.toLowerCase())) {

			Lexer.lastToken = string;

			return ContextualList.PREVIOUS;
		}
		// System.out.println("despues de place");
		if (Data.TreatmentsTable.contains(string.toLowerCase())) {
			// System.out.println("treatent"+string);
			DataStructures.BagData bd = new HonorificBagData(string, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new Recognition.InfoFound(), ContextualList.TREATMENT);
			Lexer.wbag.put(bd);

			return ContextualList.TREATMENT;
		}
		if (Data.PosessiveTable.contains(string.toLowerCase())) {
			// System.out.println("EL COBTEXTO POSESIVO");
			// Lexer.wbag.restart();
			// Lexer.wordBag=string;
                    /*if (Lexer.wbag.tam()>0){
			Lexer.wbag.put(new PossesiveBagData(string, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + string.length(), new InfoFound(), ContextualList.POSESSIVE));
			return ContextualList.POSESSIVE;
                    }*/
                    Output.write(new RoleTreeNode(string));
                    return ContextualList.SAME;
		}
		// System.out.println("el contexto es SALIDA DE DETERMINE
		// CONTEXT"+Lexer.context.getContext()+" "+string);
		return ContextualList.SAME;
		// Lexer.context.getContext();

	}

	public ContextualList getContext() {
		return ContextualList.INITIAL;
	}

	public void setContext() {
		Lexer.context = Lexer.previousContextStack.pop();
	}

	public String checkEntity(String word) {
		String salida = "";
		InfoFound info = TermsRecognition.existMedievalPlaceName(word);
		if (info != null)
			salida += 1;
		else
			salida += 0;

		boolean isProperName = TermsRecognition.isProperName(word.toLowerCase());
		if (isProperName)
			salida += 1;
		else
			salida += 0;
		boolean isCommonName = TermsRecognition.isCommonName(word);
		if (isCommonName)
			salida += 1;
		else
			salida += 0;
		return salida;
	}

	public boolean checkVerb(String word) throws java.io.IOException {
		Verbs elVerb = null;
		// System.out.println ("como llega el verbo"+word.toLowerCase());
		elVerb = Data.VerbsTable.get(word.toLowerCase());

		// if (elVerb!=null)System.out.println("cual es el verbo que se acumula
		// "+elVerb.verb+" "+elVerb.current);

		// System.out.println(word.toLowerCase()+"el verbo encontrado al principio
		// "+elVerb.verb+" "+elVerb.current);
		InfoFound info = TermsRecognition.findPlaceName(word);
		// System.out.println ("EL VERBO QUE ESTOY MIRANDO"+elVerb.verb);
		boolean isProperName = TermsRecognition.isProperName(word);
		boolean isCommonName = TermsRecognition.isCommonName(word);

		if (elVerb != null) {

			// System.out.println("entro por los verbos"+elVerb.current+"
			// "+elVerb.verb+word);
			if (isProperName) {
				// System.out.println("estoy mirando por aqui en el verbo porper"+word);
				// return false;
			} else if (info != null && !(info.gazetteer.contains("Geonames"))) {
				// System.out.println("estoy mirando por aqui en el verbo "+word+"
				// "+info.gazetteer);
				return false;
			} else {
				// System.out.println("es un verbo"+elVerb.verb+" "+word.toLowerCase());

				if (elVerb.verb.equals(word.toLowerCase())) {
                                    //System.out.println("porque estoyRRRRRRRRRRRRRverbo"+word+"sss");
					// System.out.println ("EL VERBO ES EL MISMO "+word+" "+elVerb.verb);
					Lexer.wbag.restart();
					Lexer.verbsFlag = elVerb;
					// System.out.println("EL FLAG DE VERB ES "+Lexer.verbsFlag.verb);
					Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
					// System.out.println("He salido por el verbo que he encontrado"+word);
					return true;

				} else {
                                    
					// System.out.println("entro
					// levennsnns"+WordProcessing.LevenshteinDistance.computeLevenshteinDistance(word.toLowerCase(),elVerb.verb));
					// if (Lexer.verbsFlag!=null)System.out.println("el flag del verbo es Levens
					// "+Lexer.verbsFlag.verb);
					if (WordProcessing.LevenshteinDistance.computeLevenshteinDistance(word, elVerb.verb) < 2) {
						Lexer.wbag.restart();
						// System.out.println("estoy leyendo en verbo check "+word);

						// System.out.println("lllallallllla "+word+" el verb "+elVerb.type);
						Lexer.verbsFlag = elVerb;
						// System.out.println("estoy leyendo en check 9 "+word+Lexer.verbsFlag.raiz);
						// System.out.println("entrando de verbos ffff");
						// System.out.println("he entrado por el verbo"+Lexer.verbsFlag.complement+"
						// "+Lexer.verbsFlag.verb+" "+word);

						Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
						return true;
					}

				}
				// System.out.println ("uqe esta pasando por aqui ");
			}
		}

		// System.out.println("estoy en medio de los verbos");
		String extractionverb = ElementsRecognition.verbExtraction(word.toLowerCase());

		Verbs elVerbWithSuffixes = null;
		if (extractionverb != null) {
			// System.out.println ("NO FUNIOCNA EL VERBO "+elVerb+extractionverb);
			elVerbWithSuffixes = Data.VerbsTable.get(extractionverb);
			// System.out.println("el verbo sin sufihos es "+elVerbWithSuffixes);
		}

		// if (Lexer.verbsFlag!=null)System.out.println("el flag del verbo es
		// "+Lexer.verbsFlag.verb+elVerbWithSuffixes);
		// if (elVerbWithSuffixes!=null)System.out.println("el flag del verbo es
		// "+Lexer.verbsFlag.verb+elVerbWithSuffixes.verb);

		if (elVerbWithSuffixes != null) {

			Lexer.wbag.restart();
			Lexer.verbsFlag = elVerbWithSuffixes;
			Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
			// System.out.println("He salido por el verbo que he encontrado"+word);
			return true;
		} else {

			// System.out.println("HE ENTRADO POR EL VERBO CON APOSTROFE");

			if (word.contains("'")) {
				// System.out.println("HE ENTRADO POR EL VERBO CON APOSTROFE");
				String newword = word.replace("'", "");
				// System.out.println(newword.toLowerCase());
				// System.out.println("no he encontrado el
				// verbo"+Data.VerbsTable.get(newword.toLowerCase()));
				if (Data.VerbsTable.get(newword.toLowerCase()) != null) {

					// System.out.println ("el verbo encontrado
					// "+Data.VerbsTable.get(newword.toLowerCase()).verb+" "+word.toLowerCase());
					Lexer.verbsFlag = elVerb;
					Lexer.wbag.restart();
                                        
					Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
					// Lexer.wbag.escribir();
					return true;
				}
				// System.out.println("la contiene");
			}
		}

		// System.out.println ("no la contiene fin");
		return false;

	}

	public boolean check(String word) {

		try {
			//System.out.println("estoy leyendo en check el verbo llegado"+word);
			Verbs elVerb;
			if (Data.FamilyNamesTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO PO FAMILY");

				// if (Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.PPN)
				// position=Lexer.wbag.get(Lexer.wbag.tam()-1).position+1;
				Lexer.wbag.put(new FamilyBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
						Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext()));
				return true;
			}
			if (Data.MythologicalPlaceNameTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO PO FAMILY");

				// if (Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.PPN)
				// position=Lexer.wbag.get(Lexer.wbag.tam()-1).position+1;
				Lexer.wbag.put(new PlaceNameMythologicalBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
						Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext(), false));
				return true;
			}
			//System.out.println("estoy leyendo en check 2 "+word);
			if (Data.PrepositionsTable.contains(word.toLowerCase())) {
				//System.out.println("estoy leyendo en preposiciones check "+word);
				// Lexer.wbag.escribir();
				Lexer.prepositionFlag = word;
				Lexer.wbag.restart();

				Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
				return true;
			}
			//System.out.println("estoy leyendo en check 3 "+word);
			if (Data.OrgCollectiveTable.contains(word.toLowerCase())) {
				BagData bgd = new OrgBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
						Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext(), false);
				Lexer.wbag.put(bgd);
				bgd.type = Terms.ONC;
				return true;
			}
			if (Data.ArticleTable.contains(word.toLowerCase())) {
				// System.out.println("he entrado por el articulo "+word);
				Lexer.wbag.put(new ArticleBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext()));
				// System.out.println("estoy leyendo el stop articulo "+word);
				// System.out.println("he enecontrado un aeticuko ");Lexer.wbag.escribir();
                                
				Lexer.articleFlag = word;
				return true;
			}

			//System.out.println("estoy leyendo en check 4 "+word);

			if (Recognition.ElementsRecognition.isCopulativeConjunction(word.toLowerCase()).size() > 0) {
				//System.out.println("copulativa "+word+Lexer.context);
				if (Recognition.ElementsRecognition.hasCapitalleter(word)) {
					Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
					return true;
				} else {
					// System.out.println("entro por auqi en la COPULATIVA "+word+Lexer.wbag.tam());
					if (Lexer.wbag.tam() > 0) {
						Lexer.wbag.put(new CopulativeBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
								Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext()));
						return true;
					} else {
						Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
						return true;
					}
				}
			}
			//System.out.println("estoy leyendo en check 5 "+word);

			if (Data.PosessiveTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO POR EL POSSSSSSSSSSSSSS "+word);
				// Lexer.wbag.put(new
				// PossesiveBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new
				// InfoFound(),Lexer.context.getContext()));
				// System.out.println("En el posesiovo "+Lexer.wbag.tam());
				Lexer.wordBag = word;
				return true;
			}
			//System.out.println("estoy leyendo en check 6 "+word);
			if (Data.DeityTable.contains(word.toLowerCase())) {
				Lexer.wbag.put(new DeityBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
						Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext()));
				return true;
			}
			//System.out.println("estoy leyendo en check 7 "+word);
			if (Data.StopWordsTable.contains(word.toLowerCase())) {
                           // System.out.println("estoy leyendo en check 7 "+word);
				if (Data.ProperNamesTable.contains(word.toLowerCase()) && Character.isUpperCase(word.charAt(0)))
					return false;
                              //  System.out.println("estoy leyendo en check 7 "+word);
				if (Data.ProperNamesTable.contains(word.toLowerCase()))
					return false;
				//System.out.println("estoy leyendo en check 7 "+word);

				// System.out.println("estoy leyendo en stop check las cosas de la blosa ");
				/*
				 * if (Lexer.wbag.tam()>0 && Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.DE)
				 * { Lexer.wbag.put(new
				 * ProperNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+
				 * word.length(),new InfoFound(),Lexer.context.getContext())); //
				 * System.out.println("estoy en esta situacion "); }
				 */
				Lexer.wbag.restart();
				// System.out.println("prueba de stop");
				// Output.write(word);
				Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
				// System.out.println("la bolsa de salida esta "+Output.dataOut);

				return true;
			}

			//System.out.println("estoy leyendo en check 8 "+word);

			// System.out.println("estoy leyendo en check 9 "+word+Lexer.verbsFlag.raiz);

			// System.out.println("estoy saliendo en check "+word);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public boolean checkSpecialContext(String word) {

		try {
			// System.out.println("estoy leyendo en check "+word);
			Verbs elVerb;
			if (Data.FamilyNamesTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO PO FAMILY");
				int position = 1;
				Lexer.lastToken = "";
				// if (Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.PPN)
				// position=Lexer.wbag.get(Lexer.wbag.tam()-1).position+1;
				Output.write(new RoleTreeNode(Lexer.lastToken));
				Lexer.wbag.put(new FamilyBagData(word, TypesTerms.FT, position, 1, 1, new InfoFound(),
						Lexer.context.getContext()));
				return true;
			}
			// System.out.println("estoy leyendo en check 2 check "+word);
			if (Data.PrepositionsTable.contains(word.toLowerCase())) {
				// System.out.println("estoy leyendo en preposiciones check "+word);
				Lexer.prepositionFlag = word;
				Lexer.wbag.restart();

				Output.write(new RoleTreeNode(Lexer.lastToken));
				Lexer.lastToken = "";
				Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
				return true;
			}
			// System.out.println("estoy leyendo en check 3 "+word);

			// System.out.println("estoy leyendo en check 4 special "+word);

			if (Recognition.ElementsRecognition.isCopulativeConjunction(word.toLowerCase()).size() > 0) {
				// System.out.println("copulativa "+word);
				if (Recognition.ElementsRecognition.hasCapitalleter(word)) {
					Output.write(new RoleTreeNode(Lexer.lastToken));
					Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
					return true;
				} else {
					// System.out.println("entro por auqi en la copulativa "+word);
					if (Lexer.wbag.tam() > 0) {
						Output.write(new RoleTreeNode(Lexer.lastToken));
						Lexer.wbag.put(new CopulativeBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
								Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext()));
						return true;
					}
				}
			}
			// System.out.println("estoy leyendo en check 5 special "+word);

			if (Data.PosessiveTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO POR EL POSSSSSSSSSSSSSS "+word);
				// Lexer.wbag.put(new
				// PossesiveBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new
				// InfoFound(),Lexer.context.getContext()));
				// System.out.println("En el posesiovo "+Lexer.wbag.tam());
				Lexer.wordBag = word;
				return true;
			}
			// System.out.println("estoy leyendo en check 6 special "+word);
			if (Data.DeityTable.contains(word.toLowerCase())) {
				Output.write(new RoleTreeNode(Lexer.lastToken));
				Lexer.wbag.put(new DeityBagData(word, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
						Lexer.numCh + word.length(), new InfoFound(), Lexer.context.getContext()));
				return true;
			}
			// System.out.println("estoy leyendo en check 7 "+word);
			if (Data.StopWordsTable.contains(word.toLowerCase())) {
				// System.out.println("estoy leyendo en stop check "+word);

				for (int i = 0; i < Lexer.wbag.tam(); i++) {
					// System.out.println ("las bosaa en check "+Lexer.wbag.get(i).string);
				}
				// System.out.println("estoy leyendo en stop check las cosas de la blosa
				// especial");
				Lexer.wbag.restart();

				// System.out.println("prueba de stop"+Lexer.lastToken);
				Output.write(new RoleTreeNode(Lexer.lastToken));
				Lexer.lastToken = "";

				Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
				// System.out.println("la bolsa de salida esta "+Output.dataOut);

				return true;
			}

			// System.out.println("estoy leyendo en check special 8 "+word);
			elVerb = Data.VerbsTable.get(word.toLowerCase());
			InfoFound info = TermsRecognition.findPlaceName(word);

			boolean isProperName = TermsRecognition.isProperName(word);
			boolean isCommonName = TermsRecognition.isCommonName(word);

			if (elVerb != null) {
				// System.out.println("entro por los verbos"+elVerb);
				if (info != null || isProperName || isCommonName) {
					// System.out.println("estoy mirando por aqui en el verbo "+word);
					return false;
				}

				else {
					// System.out.println ("EL VERBO ES EL MISMO "+elVerb.verb+"");
					if (elVerb.verb.equals(word)) {
						Lexer.wbag.restart();
						Lexer.verbsFlag = elVerb;
						Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));

						return true;

					} else {

						if (WordProcessing.LevenshteinDistance.computeLevenshteinDistance(word, elVerb.verb) < 2) {
							Lexer.wbag.restart();
							// System.out.println("estoy leyendo en verbo check "+word);

							// System.out.println("lllallallllla "+word+" el verb "+elVerb.type);
							Lexer.verbsFlag = elVerb;
							// System.out.println("estoy leyendo en check 9 "+word+Lexer.verbsFlag.raiz);
							// System.out.println("entrando de verbos ffff");
							// System.out.println("he entrado por el verbo"+Lexer.verbsFlag.complement+"
							// "+Lexer.verbsFlag.verb+" "+word);
							Output.write(word);
							Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
							return true;
						} else {
							// System.out.println("estoy leyendo en el else de los verbos "+word);
							return false;
						}
					}
				}
			}

			// System.out.println("estoy leyendo en check special 9
			// "+word+Lexer.verbsFlag.raiz);

			// System.out.println("estoy saliendo en check "+word);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public final SemanticContext changeContext(ContextualList newContext, SemanticContext previous, String strProcess,
			String propia) {
		// System.out.println ("me esta dando error por
		// aqui"+newContext+Lexer.previousContextStack.size()+"el nuevo
		// contexto"+newContext+"el previo"+previous.getContext()+" la cadena
		// "+strProcess);

		if (newContext == ContextualList.AUTHORITY) {

			DataStructures.BagData bd;
			Lexer.wbag.put(bd = new AuthorityBagData(propia, TypesTerms.FT, Lexer.numCh, Lexer.numWord,
					Lexer.numCh + propia.length(), new Recognition.InfoFound(), ContextualList.AUTHORITY));
			Lexer.previousContextStack.push(previous);
			Lexer.context = new AuthorityContext(previous);
			// Lexer.currentString.stringInProcess=strProcess;

		} else if (newContext == ContextualList.BUILDINGS) {
			Lexer.context = new BuildingsContext(previous);
			Lexer.currentString.stringInProcess = strProcess;
			Lexer.previousContextStack.push(previous);
		} else if (newContext == ContextualList.GEOGRAPHIC) {
			DataStructures.BagData bd;
			// Lexer.wbag.put(bd=new
			// GeographicBagData(propia,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+propia.length(),new
			// Recognition.InfoFound(),ContextualList.GEOGRAPHIC,false));

			Lexer.context = new GeographicNameContext(previous);
			Lexer.previousContextStack.push(previous);
			Lexer.currentString.stringInProcess = strProcess;

		} else if (newContext == ContextualList.PLACE) {
			Lexer.context = new PlaceContext(previous);
			Lexer.previousContextStack.push(previous);
			Lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.PREVIOUS) {
			Lexer.context = new PreviousNameContext(previous);
			Lexer.previousContextStack.push(previous);
			Lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.SAINT) {
			Lexer.context = new SaintContext(previous);
			Lexer.previousContextStack.push(previous);
			Lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.TREATMENT) {
			// System.out.println("no estoy por trear");
			Lexer.context = new TreatmentContext(previous);
			Lexer.previousContextStack.push(previous);
			Lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.POSESSIVE) {
			Lexer.previousContextStack.clear();

			Lexer.context = new PosessiveContext(previous);

			Lexer.previousContextStack.push(previous);

			// Lexer.currentString.stringInProcess=strProcess;
			// System.out.println("entro por el posesivo de cambio");
		} else if (newContext == ContextualList.FAMILY) {

			Lexer.context = new ContextProcessing.FamiliyContext(previous);

			Lexer.previousContextStack.push(previous);

			// Lexer.currentString.stringInProcess=strProcess;
			// System.out.println("entro por el family de cambio");
		} else {
			// System.out.println("Intento cabiar a inicial");
			Lexer.context = new GeneralContext(previous);
			Lexer.previousContextStack.push(previous);
			// Lexer.currentString.stringInProcess=strProcess;
		}
		return Lexer.context;
	}

	public String determineFirstWord() {
		return Lexer.currentString.stringInProcess.substring(0, Lexer.currentString.stringInProcess.indexOf(" "));
	}

	public SemanticContext processingPoint(String text) {
		try {
			// System.out.println ("ESTOY EN EL ÛNTO "+Lexer.lastToken);
			Output.write(new RoleTreeNode(Lexer.lastToken));
			Lexer.lastToken = "";
			Lexer.wbag.restart();
			Output.write(new RoleTreeNode(text));
			Lexer.prepositionFlag = "";
			// System.out.println("estoy en el proceso"+Lexer.context);
			return Lexer.context;
		} catch (Exception e) {// System.out.println("en el proceso");
			return null;
		}
	}

	public abstract SemanticContext checkLowerCaseWord(String word);

	public abstract ContextualList checkCapitalLettersWord(String word);

	public abstract ContextualList wordListProcessing(String string);

	public abstract ContextualList nounPhraseProcessing(String string);

	public abstract ContextualList prepositionalSyntagmsListProcessing(String string);

}
