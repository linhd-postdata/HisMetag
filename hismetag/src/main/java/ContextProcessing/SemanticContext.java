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

	protected String readCleanString;
	protected Lexer lexer;
	protected Output output;

	public SemanticContext(Lexer lexer, Output output) {
		this.lexer = lexer;
		this.output = output;
	}

	public SemanticContext(SemanticContext previous, Lexer lexer, Output output) {
		// if (previous!=null);
		this.lexer = lexer;
		this.lexer.addPreviousContext(previous);
		this.output = output;
	}

	public final BagData typeContext(String string, SemanticContext previous)
			throws java.io.IOException, java.io.FileNotFoundException {
		// System.out.println("la palabra que he encontrado es "+string);

		// System.out.println("tipo context 1");
		// this.lexer.wbag.escribir();
		if (Data.ContextPlaceNames.contains(string.toLowerCase())) {

			BagData bgd = new PlaceNameBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext(), false, true);
			bgd.type = Terms.NPLN;

			this.lexer.context.changeContext(ContextualList.PLACE, this.lexer.context, " ", string);
			return bgd;
		}

		// System.out.println("tipo context 2");
		// this.lexer.wbag.escribir();

		if (Data.FamilyNamesTable.contains(string.toLowerCase())) {
			// System.out.println("ES UN HIJO");
			BagData bgd = new FamilyBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext());

			this.lexer.context.changeContext(ContextualList.FAMILY, this.lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 3");
		// this.lexer.wbag.escribir();

		if (Data.AuthorityNamesTable.contains(string.toLowerCase())) {
			// this.lexer.wbag.escribir();
			BagData bgd = new AuthorityBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext());

			DataStructures.BagData bd;
			this.lexer.addPreviousContext(previous);
			this.lexer.context = new AuthorityContext(previous, this.lexer, this.output);
			return bgd;
		}
		// System.out.println("tipo context 4");
		// System.out.println ("estoy en la salida de geografic"+string);
		if (Data.GeographicNamesTable.contains(string.toLowerCase())) {

			BagData bgd = new GeographicBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext(), false);

			this.lexer.context.changeContext(ContextualList.GEOGRAPHIC, this.lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 5");
		if (Data.BuildingsTable.contains(string.toLowerCase())) {
			// System.out.println("he entrado por aqui con el building");
			BagData bgd = new BuildingBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext(), false);
			this.lexer.context.changeContext(ContextualList.BUILDINGS, this.lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 6");
		// System.out.println("antes de saint "+string+this.lexer.context.getContext());
		if (Data.SaintsTable.contains(string.toLowerCase())) {
			BagData bgd = new SaintBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext());

			this.lexer.context.changeContext(ContextualList.SAINT, this.lexer.context, " ", string);

			return bgd;
		}

		// System.out.println("tipo context 7");
		if (Data.PreviousNamesTable.contains(string.toLowerCase())) {
			BagData bgd = new OrgBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext(), false);

			this.lexer.context.changeContext(ContextualList.PREVIOUS, this.lexer.context, " ", string);

			return bgd;
		}
		// System.out.println("tipo context 8");
		// System.out.println("despues de place");
		if (Data.TreatmentsTable.contains(string.toLowerCase())) {
			BagData bgd = new HonorificBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext());
			this.lexer.context.changeContext(ContextualList.TREATMENT, this.lexer.context, " ", string);

			return bgd;

		}

		// System.out.println("tipo context 9");
		if (Data.PosessiveTable.contains(string.toLowerCase())) {
                   // System.out.println("POSESIVO TAM DE WBAG"+this.lexer.wbag.tam());
                    if (this.lexer.wbag.tam()>0){
			BagData bgd = new PossesiveBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext());
			this.lexer.context.changeContext(ContextualList.POSESSIVE, this.lexer.context, " ", string);
			return bgd;}
		}

		// System.out.println("tipo context 10");
		// System.out.println("el contexto es SALIDA DE DETERMINE
		// CONTEXT"+this.lexer.context.getContext()+" "+string);
		return null;
		// this.lexer.context.getContext();

	}

	public final ContextualList determineContext(String string, SemanticContext previous)
			throws java.io.IOException, java.io.FileNotFoundException {
		System.out.println("la palabra que he encontrado es "+string);
                
		if (Data.ContextPlaceTerms.contains(string.toLowerCase())) {

			//System.out.println("EL COMPLEMEBTO DEL VERBO "+string+" verbo"+this.lexer.isTheFirst());
			// "+this.lexer.verbsFlag+this.lexer.isTheFirst());
			// System.out.println("HE ENTRADO POR EL CONTEXTO PLACE"+string+ "
			// "+this.lexer.verbsFlag.verb+" "+this.lexer.isTheFirst());
			this.lexer.prepositionFlag = string;
			if (this.lexer.isTheFirst()) {
				this.lexer.wbag.restart();
				this.output.write(new RoleTreeNode(string));
				this.lexer.setTheFirst(false);
				return ContextualList.PLACE;
			}
                        
                        
			if (this.lexer.verbsFlag != null && this.lexer.verbsFlag.verb!=" ") {
//System.out.println("no llego PROPROOPPROOOAQUI"+this.lexer.verbsFlag.verb);
				ArrayList<String> lista = Data.VerbsTable.getComplements(this.lexer.verbsFlag.verb);

				if (lista.contains(string.toLowerCase()) || lista.contains("N")) {
					// System.out.println("si la contien"+this.lexer.verbsFlag.type);
					if (this.lexer.verbsFlag.type.equals("PL")) {
						this.lexer.wbag.restart();
						// System.out.println("ESTIY EBN CONTEXTOOOOOOOOOO");
						this.output.write(new RoleTreeNode(string));
						return ContextualList.PLACE;
					}
				}

				else {
					// System.out.println("salgo por aqui con el verbo buscado");
					return ContextualList.SAME;

					// System.out.println("FLAG VERB "+this.lexer.verbsFlag.verb);
				}
			} else { //System.out.println("lallalllalllallallaPREOPOA");
				// this.output.write(new RoleTreeNode(string));
				if (this.lexer.isTheFirst()) {
					this.output.write(new RoleTreeNode(string));
					return ContextualList.PLACE;
				}

				return ContextualList.SAME;
			}

			// this.lexer.context=new PlaceContext(previous);

		}

		if (Data.ContextPlaceNames.contains(string.toLowerCase())) {
			this.lexer.wbag.restart();
			BagData bgd = new PlaceNameBagData(string, TypesTerms.PPT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), this.lexer.context.getContext(), false, true);
			bgd.type = Terms.NPLN;
			this.lexer.wbag.put(bgd);

			return ContextualList.PLACE;
		}

		if (Data.FamilyNamesTable.contains(string.toLowerCase())) {
			// System.out.println("ES UN HIJO");

			this.lexer.setLastToken(string);

			return ContextualList.FAMILY;
		}
		// System.out.println("ANTES DE AUTHORITY "+string);
		if (Data.AuthorityNamesTable.contains(string.toLowerCase())) {
                        if (this.lexer.wbag.containsTypeTerms(Terms.ART)){
                          this.lexer.wbag.borrar(Terms.ART, this.lexer.wbag.tam()-1);
                            
                         
                        }
			return ContextualList.AUTHORITY;
		}
		// System.out.println ("estoy en la salida de geografic"+string);
		if (Data.GeographicNamesTable.contains(string.toLowerCase())) {

			this.lexer.setLastToken(string);

			return ContextualList.GEOGRAPHIC;
		}

		if (Data.BuildingsTable.contains(string.toLowerCase())) {
			// System.out.println("he entrado por aqui con el building");
			this.lexer.setLastToken(string);

			return ContextualList.BUILDINGS;
		}

		// System.out.println("antes de saint "+string+this.lexer.context.getContext());
		if (Data.SaintsTable.contains(string.toLowerCase())) {
			this.lexer.setLastToken(string);
			// System.out.println("he netrado por aqui en los dantos");

			return ContextualList.SAINT;
		}
		if (Data.PreviousNamesTable.contains(string.toLowerCase())) {

			this.lexer.setLastToken(string);

			return ContextualList.PREVIOUS;
		}
		// System.out.println("despues de place");
		if (Data.TreatmentsTable.contains(string.toLowerCase())) {
			// System.out.println("treatent"+string);
			DataStructures.BagData bd = new HonorificBagData(string, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new Recognition.InfoFound(), ContextualList.TREATMENT);
			this.lexer.wbag.put(bd);

			return ContextualList.TREATMENT;
		}
		if (Data.PosessiveTable.contains(string.toLowerCase())) {
			// System.out.println("EL COBTEXTO POSESIVO");
			// this.lexer.wbag.restart();
			// this.lexer.setWordBag(string);
                    /*if (this.lexer.wbag.tam()>0){
			this.lexer.wbag.put(new PossesiveBagData(string, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + string.length(), new InfoFound(), ContextualList.POSESSIVE));
			return ContextualList.POSESSIVE;
                    }*/
                    this.output.write(new RoleTreeNode(string));
                    return ContextualList.SAME;
		}
		// System.out.println("el contexto es SALIDA DE DETERMINE
		// CONTEXT"+this.lexer.context.getContext()+" "+string);
		return ContextualList.SAME;
		// this.lexer.context.getContext();

	}

	public ContextualList getContext() {
		return ContextualList.INITIAL;
	}

	public void setContext() {
		this.lexer.context = this.lexer.getPreviousContext();
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
					this.lexer.wbag.restart();
					this.lexer.verbsFlag = elVerb;
					// System.out.println("EL FLAG DE VERB ES "+this.lexer.verbsFlag.verb);
					this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
					// System.out.println("He salido por el verbo que he encontrado"+word);
					return true;

				} else {
                                    
					// System.out.println("entro
					// levennsnns"+WordProcessing.LevenshteinDistance.computeLevenshteinDistance(word.toLowerCase(),elVerb.verb));
					// if (this.lexer.verbsFlag!=null)System.out.println("el flag del verbo es Levens
					// "+this.lexer.verbsFlag.verb);
					if (WordProcessing.LevenshteinDistance.computeLevenshteinDistance(word, elVerb.verb) < 2) {
						this.lexer.wbag.restart();
						// System.out.println("estoy leyendo en verbo check "+word);

						// System.out.println("lllallallllla "+word+" el verb "+elVerb.type);
						this.lexer.verbsFlag = elVerb;
						// System.out.println("estoy leyendo en check 9 "+word+this.lexer.verbsFlag.raiz);
						// System.out.println("entrando de verbos ffff");
						// System.out.println("he entrado por el verbo"+this.lexer.verbsFlag.complement+"
						// "+this.lexer.verbsFlag.verb+" "+word);

						this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
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

		// if (this.lexer.verbsFlag!=null)System.out.println("el flag del verbo es
		// "+this.lexer.verbsFlag.verb+elVerbWithSuffixes);
		// if (elVerbWithSuffixes!=null)System.out.println("el flag del verbo es
		// "+this.lexer.verbsFlag.verb+elVerbWithSuffixes.verb);

		if (elVerbWithSuffixes != null) {

			this.lexer.wbag.restart();
			this.lexer.verbsFlag = elVerbWithSuffixes;
			this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
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
					this.lexer.verbsFlag = elVerb;
					this.lexer.wbag.restart();
                                        
					this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
					// this.lexer.wbag.escribir();
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

				// if (this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.PPN)
				// position=this.lexer.wbag.get(this.lexer.wbag.tam()-1).position+1;
				this.lexer.wbag.put(new FamilyBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
						this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext()));
				return true;
			}
			if (Data.MythologicalPlaceNameTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO PO FAMILY");

				// if (this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.PPN)
				// position=this.lexer.wbag.get(this.lexer.wbag.tam()-1).position+1;
				this.lexer.wbag.put(new PlaceNameMythologicalBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
						this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext(), false));
				return true;
			}
			//System.out.println("estoy leyendo en check 2 "+word);
			if (Data.PrepositionsTable.contains(word.toLowerCase())) {
				//System.out.println("estoy leyendo en preposiciones check "+word);
				// this.lexer.wbag.escribir();
				this.lexer.prepositionFlag = word;
				this.lexer.wbag.restart();

				this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
				return true;
			}
			//System.out.println("estoy leyendo en check 3 "+word);
			if (Data.OrgCollectiveTable.contains(word.toLowerCase())) {
				BagData bgd = new OrgBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
						this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext(), false);
				this.lexer.wbag.put(bgd);
				bgd.type = Terms.ONC;
				return true;
			}
			if (Data.ArticleTable.contains(word.toLowerCase())) {
				// System.out.println("he entrado por el articulo "+word);
				this.lexer.wbag.put(new ArticleBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext()));
				// System.out.println("estoy leyendo el stop articulo "+word);
				// System.out.println("he enecontrado un aeticuko ");this.lexer.wbag.escribir();
                                
				this.lexer.articleFlag = word;
				return true;
			}

			//System.out.println("estoy leyendo en check 4 "+word);

			if (Recognition.ElementsRecognition.isCopulativeConjunction(word.toLowerCase()).size() > 0) {
				//System.out.println("copulativa "+word+this.lexer.context);
				if (Recognition.ElementsRecognition.hasCapitalleter(word)) {
					this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
					return true;
				} else {
					// System.out.println("entro por auqi en la COPULATIVA "+word+this.lexer.wbag.tam());
					if (this.lexer.wbag.tam() > 0) {
						this.lexer.wbag.put(new CopulativeBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
								this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext()));
						return true;
					} else {
						this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
						return true;
					}
				}
			}
			//System.out.println("estoy leyendo en check 5 "+word);

			if (Data.PosessiveTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO POR EL POSSSSSSSSSSSSSS "+word);
				// this.lexer.wbag.put(new
				// PossesiveBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new
				// InfoFound(),this.lexer.context.getContext()));
				// System.out.println("En el posesiovo "+this.lexer.wbag.tam());
				this.lexer.setWordBag(word);
				return true;
			}
			//System.out.println("estoy leyendo en check 6 "+word);
			if (Data.DeityTable.contains(word.toLowerCase())) {
				this.lexer.wbag.put(new DeityBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
						this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext()));
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
				 * if (this.lexer.wbag.tam()>0 && this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.DE)
				 * { this.lexer.wbag.put(new
				 * ProperNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+
				 * word.length(),new InfoFound(),this.lexer.context.getContext())); //
				 * System.out.println("estoy en esta situacion "); }
				 */
				this.lexer.wbag.restart();
				// System.out.println("prueba de stop");
				// this.output.write(word);
				this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
				// System.out.println("la bolsa de salida esta "+this.output.dataOut);

				return true;
			}

			//System.out.println("estoy leyendo en check 8 "+word);

			// System.out.println("estoy leyendo en check 9 "+word+this.lexer.verbsFlag.raiz);

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
				this.lexer.setLastToken("");
				// if (this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.PPN)
				// position=this.lexer.wbag.get(this.lexer.wbag.tam()-1).position+1;
				this.output.write(new RoleTreeNode(this.lexer.getLastToken()));
				this.lexer.wbag.put(new FamilyBagData(word, TypesTerms.FT, position, 1, 1, new InfoFound(),
						this.lexer.context.getContext()));
				return true;
			}
			// System.out.println("estoy leyendo en check 2 check "+word);
			if (Data.PrepositionsTable.contains(word.toLowerCase())) {
				// System.out.println("estoy leyendo en preposiciones check "+word);
				this.lexer.prepositionFlag = word;
				this.lexer.wbag.restart();

				this.output.write(new RoleTreeNode(this.lexer.getLastToken()));
				this.lexer.setLastToken("");
				this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
				return true;
			}
			// System.out.println("estoy leyendo en check 3 "+word);

			// System.out.println("estoy leyendo en check 4 special "+word);

			if (Recognition.ElementsRecognition.isCopulativeConjunction(word.toLowerCase()).size() > 0) {
				// System.out.println("copulativa "+word);
				if (Recognition.ElementsRecognition.hasCapitalleter(word)) {
					this.output.write(new RoleTreeNode(this.lexer.getLastToken()));
					this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
					return true;
				} else {
					// System.out.println("entro por auqi en la copulativa "+word);
					if (this.lexer.wbag.tam() > 0) {
						this.output.write(new RoleTreeNode(this.lexer.getLastToken()));
						this.lexer.wbag.put(new CopulativeBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
								this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext()));
						return true;
					}
				}
			}
			// System.out.println("estoy leyendo en check 5 special "+word);

			if (Data.PosessiveTable.contains(word.toLowerCase())) {
				// System.out.println("HE ENTRADO POR EL POSSSSSSSSSSSSSS "+word);
				// this.lexer.wbag.put(new
				// PossesiveBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new
				// InfoFound(),this.lexer.context.getContext()));
				// System.out.println("En el posesiovo "+this.lexer.wbag.tam());
				this.lexer.setWordBag(word);
				return true;
			}
			// System.out.println("estoy leyendo en check 6 special "+word);
			if (Data.DeityTable.contains(word.toLowerCase())) {
				this.output.write(new RoleTreeNode(this.lexer.getLastToken()));
				this.lexer.wbag.put(new DeityBagData(word, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
						this.lexer.numCh + word.length(), new InfoFound(), this.lexer.context.getContext()));
				return true;
			}
			// System.out.println("estoy leyendo en check 7 "+word);
			if (Data.StopWordsTable.contains(word.toLowerCase())) {
				// System.out.println("estoy leyendo en stop check "+word);

				for (int i = 0; i < this.lexer.wbag.tam(); i++) {
					// System.out.println ("las bosaa en check "+this.lexer.wbag.get(i).string);
				}
				// System.out.println("estoy leyendo en stop check las cosas de la blosa
				// especial");
				this.lexer.wbag.restart();

				// System.out.println("prueba de stop"+this.lexer.getLastToken());
				this.output.write(new RoleTreeNode(this.lexer.getLastToken()));
				this.lexer.setLastToken("");

				this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
				// System.out.println("la bolsa de salida esta "+this.output.dataOut);

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
						this.lexer.wbag.restart();
						this.lexer.verbsFlag = elVerb;
						this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));

						return true;

					} else {

						if (WordProcessing.LevenshteinDistance.computeLevenshteinDistance(word, elVerb.verb) < 2) {
							this.lexer.wbag.restart();
							// System.out.println("estoy leyendo en verbo check "+word);

							// System.out.println("lllallallllla "+word+" el verb "+elVerb.type);
							this.lexer.verbsFlag = elVerb;
							// System.out.println("estoy leyendo en check 9 "+word+this.lexer.verbsFlag.raiz);
							// System.out.println("entrando de verbos ffff");
							// System.out.println("he entrado por el verbo"+this.lexer.verbsFlag.complement+"
							// "+this.lexer.verbsFlag.verb+" "+word);
							this.output.write(word);
							this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
							return true;
						} else {
							// System.out.println("estoy leyendo en el else de los verbos "+word);
							return false;
						}
					}
				}
			}

			// System.out.println("estoy leyendo en check special 9
			// "+word+this.lexer.verbsFlag.raiz);

			// System.out.println("estoy saliendo en check "+word);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public final SemanticContext changeContext(ContextualList newContext, SemanticContext previous, String strProcess,
			String propia) {
		// System.out.println ("me esta dando error por
		// aqui"+newContext+this.lexer.previousContextStack.size()+"el nuevo
		// contexto"+newContext+"el previo"+previous.getContext()+" la cadena
		// "+strProcess);

		if (newContext == ContextualList.AUTHORITY) {

			DataStructures.BagData bd;
			this.lexer.wbag.put(bd = new AuthorityBagData(propia, TypesTerms.FT, this.lexer.numCh, this.lexer.numWord,
					this.lexer.numCh + propia.length(), new Recognition.InfoFound(), ContextualList.AUTHORITY));
			this.lexer.addPreviousContext(previous);
			this.lexer.context = new AuthorityContext(previous, this.lexer, this.output);
			// this.lexer.currentString.stringInProcess=strProcess;

		} else if (newContext == ContextualList.BUILDINGS) {
			this.lexer.context = new BuildingsContext(previous, this.lexer, this.output);
			this.lexer.currentString.stringInProcess = strProcess;
			this.lexer.addPreviousContext(previous);
		} else if (newContext == ContextualList.GEOGRAPHIC) {
			DataStructures.BagData bd;
			// this.lexer.wbag.put(bd=new
			// GeographicBagData(propia,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+propia.length(),new
			// Recognition.InfoFound(),ContextualList.GEOGRAPHIC,false));

			this.lexer.context = new GeographicNameContext(previous, this.lexer, this.output);
			this.lexer.addPreviousContext(previous);
			this.lexer.currentString.stringInProcess = strProcess;

		} else if (newContext == ContextualList.PLACE) {
			this.lexer.context = new PlaceContext(previous, this.lexer, this.output);
			this.lexer.addPreviousContext(previous);
			this.lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.PREVIOUS) {
			this.lexer.context = new PreviousNameContext(previous, this.lexer, this.output);
			this.lexer.addPreviousContext(previous);
			this.lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.SAINT) {
			this.lexer.context = new SaintContext(previous, this.lexer, this.output);
			this.lexer.addPreviousContext(previous);
			this.lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.TREATMENT) {
			// System.out.println("no estoy por trear");
			this.lexer.context = new TreatmentContext(previous, this.lexer, this.output);
			this.lexer.addPreviousContext(previous);
			this.lexer.currentString.stringInProcess = strProcess;
		} else if (newContext == ContextualList.POSESSIVE) {
			this.lexer.clearPreviousContext();

			this.lexer.context = new PosessiveContext(previous, this.lexer, this.output);

			this.lexer.addPreviousContext(previous);

			// this.lexer.currentString.stringInProcess=strProcess;
			// System.out.println("entro por el posesivo de cambio");
		} else if (newContext == ContextualList.FAMILY) {

			this.lexer.context = new ContextProcessing.FamiliyContext(previous, this.lexer, this.output);

			this.lexer.addPreviousContext(previous);

			// this.lexer.currentString.stringInProcess=strProcess;
			// System.out.println("entro por el family de cambio");
		} else {
			// System.out.println("Intento cabiar a inicial");
			this.lexer.context = new GeneralContext(previous, this.lexer, this.output);
			this.lexer.addPreviousContext(previous);
			// this.lexer.currentString.stringInProcess=strProcess;
		}
		return this.lexer.context;
	}

	public String determineFirstWord() {
		return this.lexer.currentString.stringInProcess.substring(0, this.lexer.currentString.stringInProcess.indexOf(" "));
	}

	public SemanticContext processingPoint(String text) {
		try {
			// System.out.println ("ESTOY EN EL ÛNTO "+this.lexer.getLastToken());
			this.output.write(new RoleTreeNode(this.lexer.getLastToken()));
			this.lexer.setLastToken("");
			this.lexer.wbag.restart();
			this.output.write(new RoleTreeNode(text));
			this.lexer.prepositionFlag = "";
			// System.out.println("estoy en el proceso"+this.lexer.context);
			return this.lexer.context;
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
