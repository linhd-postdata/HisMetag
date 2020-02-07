/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;

import IOModule.Output;
import MedievalTextLexer.Lexer;
import Recognition.ElementsRecognition;
import Recognition.InfoFound;
import Recognition.Terms;
import Recognition.TermsRecognition;
import WordProcessing.WordTransformations;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author mluisadiez
 */
public class RoleTreeNode {
	public BagData root;
	public ArrayList<RoleTreeNode> pred;
	public ArrayList<RoleTreeNode> suc;
	public RoleTreeNode father = null;
	public static String salidaCompleta = " ";

	public RoleTreeNode() {
		root = new BagData();
		root.string = "";
		pred = new ArrayList<RoleTreeNode>();
		suc = new ArrayList<RoleTreeNode>();

	}

	public RoleTreeNode(String string) {
		root = new BagData();
		root.string = string;
		pred = new ArrayList<RoleTreeNode>();
		suc = new ArrayList<RoleTreeNode>();
	}
        public RoleTreeNode(String string, int position, int nWord) {
		root = new BagData();
		root.string = string;
                root.position=position;
                root.nword=nWord;
		pred = new ArrayList<RoleTreeNode>();
		suc = new ArrayList<RoleTreeNode>();
	}

	public RoleTreeNode(BagData _root) {
		
		root = _root;
		pred = new ArrayList<RoleTreeNode>();
		suc = new ArrayList<RoleTreeNode>();
	}

	public void addPred(RoleTreeNode _pred) {
		pred.add(_pred);
	}

	public void addSuc(RoleTreeNode _suc) {
		suc.add(_suc);
	}

	public String preOrden() {
		return null;
	}

	public String inOrden(Output output) throws java.io.IOException, java.io.FileNotFoundException {
		String initTag = "";
		String endTag = "";
		String salida = "";
          /*      System.out.println("la raiz del arbol es "+root.string+" "+suc.size());
                for (int i=0; i<suc.size();i++){
                    System.out.println("los uscesores son "+suc.get(i).root.string);
                }*/
//System.out.println("QQQQQQQQQQQQ"+root.string.charAt(root.string.length()-1));
		initTag = root.getInit();
		endTag = root.getEnd();
		// System.out.println("estoy ESCRIBIRNOD LA RAIZ"+root.string);
		if (root.string == " ")
			return "";
                
		// System.out.println("Entro por aqui "+this.root.string);
		if (root.type == Terms.Y)
			output.write(root.string);
		if (root.type.toString().contains("PLN")) {
			//System.out.println(root.string+"CCACCCACCCACCCCACCCCA");
			output.writePlaceData(this);

		}

		if (root.type.toString().contains("RN")) {
			output.writeRoleName(this);
		}
		if (root.type.toString().contains("ON")) {
			output.writeOrgData(this);
		}
                
		if (root.type.toString().contains("PN")) {
			output.writeProperData(this);
		}
                
		// System.out.println ("llegohats quie"+root.string);

		if (root.type == Terms.RNH && suc.size() == 0) {
			if (father != null) {
				initTag = "<roleName>";
				endTag = "</roleName>";
			} else {
				initTag = "";
				endTag = "";
			}
		}

		if (root.type == Terms.RNA && suc.size() == 0 && pred.size() == 0
				&& Data.StopWordsTable.contains(root.string.toLowerCase())) {

			initTag = "";
			endTag = "";

		}

		if (root.type == Terms.NPN) {
                    String nueva=root.string.replace("'", "");
			if (father == null) {
                            
				initTag = "<persName type ='nickName'>";
				endTag = "</persName>";
			} else {

				initTag = "<addName type ='nickName'>";
				endTag = "</addName>";
			}
		}

		if (root.type == Terms.NPD) {

			if (father == null) {
				initTag = "<persName type ='_nickName_deity'>";
				endTag = "</persName>";
			} else {
				initTag = "<addName type ='_nickName_deity'>";
				endTag = "</addName>";
			}
		}
		if (root.type == Terms.DPN) {
			if (this.pred.size() == 1 && this.pred.get(0).root.type == Terms.RNF) {
				root.string = this.pred.get(0).root.string + " " + root.string;
				this.pred.remove(0);
			}
		}
		if (root.type == Terms.PPN) {
			if (father != null && (father.root.type == Terms.PPN || father.root.type == Terms.DPN)) {
				initTag = "";
				endTag = "";
			}
			if (this.pred.size() > 0 && this.pred.get(0).root.type == Terms.RNS) {
				initTag = "<persName type ='_deity'>";
				endTag = "</persName>";
			}
			if (this.pred.size() > 0 && this.pred.get(0).root.type == Terms.PSTN) {
				initTag = "<persName type ='_deity'>";
				endTag = "</persName>";
			}
		}

		if (root.type == Terms.RNS) {
			if (father.root.type.toString().contains("PLN")) {
				initTag = "";
				endTag = "";
			}
		}

		if (root.type == Terms.NPLN) {

			initTag = "";
			endTag = "";

		}

		if (root.type == Terms.GPLN || root.type == Terms.FPLN) {
			if (this.pred.size() == 0 && this.suc.size() == 0
					&& (Data.GeographicNamesTable.contains(root.string) || Data.BuildingsTable.contains(root.string))) {
				initTag = "";
				endTag = "";
			}
		}

		if (root.type == Terms.UN)
			if (father != null) {
				initTag = "";
				endTag = "";
			}

		if (root.string.equals("\n")) {

			initTag = "";
			endTag = "";
		}
		if (root.string.equals("\r")) {

			initTag = "";
			endTag = "";
		}

		// System.out.println ("DE VERDAD QUE ESTOY PINTANDO "+root.type);
		salida += initTag;
		// System.out.print(initTag+"tengo en la salida "+salida+"
		// predeceosres"+pred.get(0).root.string);

		for (int i = 0; i < pred.size(); i++) {
			// System.out.println ("DE VERDAD QUE ESTOY PINTANDO "+root.type);
			// System.out.println ("pinto los predecesores "+pred.get(i).root.string);
			salida += pred.get(i).inOrden(output);
			// System.out.println("en la salida estas los predecseorss ");

		}
		if (root.string.equals("\n"))
			salida += root.string+"<br/>";
		else if (root.string.equals("\r"))
			salida += root.string+"<br/>";
		else{
                   	salida += root.string + " ";
			// System.out.println("wwwwwwww"+salida);

		}
		// salidaCompleta+=root.string+" ";

		for (int i = 0; i < suc.size(); i++) {

			// System.out.println("quienes son sus sucesores al pintar
			// "+suc.get(i).root.string);
			salida += suc.get(i).inOrden(output);

		}

		if (root.string.equals("\n")) {
			salida += endTag;
		} else if (root.string.equals("\r")) {
			salida += endTag;
		}

		else
			salida += endTag + " ";
		// System.out.print("salgo de escribir "+root.string);

		return salida;

	}

	public boolean hasHonorific(String s) {
		for (int i = 0; i < this.pred.size(); i++) {
			if (this.pred.get(i).root.string.equals(s))
				return true;
		}
		return false;
	}

	public boolean hasHonorific() {
		if (this.pred.size() > 0)
			return true;
		else
			return false;
	}
        

	public boolean dependOf(RoleTreeNode _rtn, boolean cabeza) {
		if ((this.root.type == Terms.PPN && _rtn.root.type == Terms.RNF) && this.root.position > _rtn.root.position
				&& cabeza == false)
			return true;
		if ((this.root.type == Terms.PPN && _rtn.root.type == Terms.RNF) && cabeza == true) {

			// System.out.println ("ESTOY CONSULTANDO ESTO OTRA VWEZ");
			return false;
		}
		// if ((this.root.type=="PPN" && _rtn.root.type=="RNH") &&
		// this.root.position>_rtn.root.position)return true;
		if ((this.root.type == Terms.PPN && _rtn.root.type == Terms.Y) && cabeza == false)
			return true;
		if ((this.root.type == Terms.PPN && _rtn.root.type == Terms.DE) && this.root.position > _rtn.root.position
				&& _rtn.father != null)
			return true;
		if (this.root.type == Terms.RNA && _rtn.root.type == Terms.PPN)
			return true;
		if (this.root.type == Terms.RNF && _rtn.root.type == Terms.PPN && this.root.position > _rtn.root.position)
			return true;
		if ((this.root.type == Terms.RNH && _rtn.root.type == Terms.PPN) && cabeza == true) {
			// System.out.println ("EL HONORIFICO "+this.root.string+" "+_rtn.root.string+"
			// "+_rtn.pred.size());
			if (_rtn.hasHonorific(this.root.string))
				return false;

			return true;
		}
		// if ((this.root.type==Terms.RNA && _rtn.root.type==Terms.Y) ) return true;
		if (this.root.type == Terms.PLN && _rtn.root.type == Terms.GPLN)
			return true;
		if (this.root.type == Terms.Y && _rtn.root.type == Terms.Y && cabeza == false)
			return true;
		if (this.root.type == Terms.RNH && _rtn.root.type == Terms.RNA)
			return true;
		if (this.root.type == Terms.ART && _rtn.root.type == Terms.NPN && this.root.position < _rtn.root.position)
			return true;
		if (this.root.type == Terms.ART && _rtn.root.type == Terms.DE && this.root.position > _rtn.root.position)
			return true;
		if (this.root.type == Terms.UN && _rtn.root.type == Terms.PLN)
			return true;
		// if (this.root.type==Terms.PLN && _rtn.root.type==Terms.UN) return true;
		if (this.root.type == Terms.UN && _rtn.root.type == Terms.PPN)
			return true;
		// if (this.root.type==Terms.PPN && _rtn.root.type==Terms.UN) return true;
		if (this.root.type == Terms.PSS && _rtn.root.type == Terms.PPN)
			return true;
                if ((this.root.type == Terms.PPN && _rtn.root.type == Terms.DPN) && cabeza == false)
                    return false;
		if ((this.root.type == Terms.PPN && _rtn.root.type == Terms.PPN) && cabeza == false) {
			// System.out.println ("EL HONORIFICO de PPN "+this.root.string+"
			// "+_rtn.root.string+" "+this.pred.size());
			if (this.hasHonorific())
				return false;
                        

			return true;
		}
		if ((this.root.type == Terms.RNF && _rtn.root.type == Terms.PPN) && cabeza == false
				&& this.root.position < _rtn.root.position)
			return true;
		if ((this.root.type == Terms.RNF && _rtn.root.type == Terms.DPN))
			return true;
		if (this.root.type == Terms.PLN && _rtn.root.type == Terms.RNA)
			return false;
		if (this.root.type == Terms.PLN && _rtn.root.type == Terms.RNF)
			return true;
		if (this.root.type == Terms.DE && _rtn.root.type == Terms.PLN && this.root.position > _rtn.root.position) {
			// System.out.println("he entrado por qui para ver el role
			// node"+this.root.string);
			return false;
		}
		if (this.root.type == Terms.PLN && _rtn.root.type == Terms.DE && this.root.position > _rtn.root.position) {
			// System.out.println("he entrado por qui para ver el role
			// node"+this.root.string);
			return true;
		}
		/*
		 * if (this.root.type==Terms.DE && _rtn.root.type==Terms.DE && cabeza ==false){
		 * //System.out.println("he entrado por qui para ver el role node"+this.root.
		 * string); return false; }
		 */
		if (this.root.type == Terms.DE && _rtn.root.type == Terms.DE && cabeza == true) {
			// System.out.println("he entrado por qui para ver el role
			// node"+this.root.string);
			return false;
		}

		if (this.root.type == Terms.PLN && _rtn.root.type == Terms.DE && cabeza == true) {
			// System.out.println("he entrado por qui para ver el role
			// node"+this.root.string);
			return true;
		}
		if (this.root.type == Terms.APLN && _rtn.root.type == Terms.DE) {
			//System.out.println("he entrado por qui para vROLEROLEROLEROLE node"+this.root.string);
			return true;
		}
               /* if (this.root.type == Terms.PPN && _rtn.root.type == Terms.DE) {
			System.out.println("he entrado por qui para vROLEROLEROLEROLE node"+this.root.string);
			return true;
		}*/
                
               
		if (this.root.type == Terms.NPLN && _rtn.root.type == Terms.PLN && cabeza == true) {
			// System.out.println("he entrado por qui para ver el role
			// node"+this.root.string);
			return true;
		}
		// if (this.root.type==Terms.PPN && _rtn.root.type==Terms.NPN) return false;
		if (this.root.type == Terms.NPN && _rtn.root.type == Terms.PPN)
			return true;
		if (this.root.type == Terms.NPN && _rtn.root.type == Terms.NPN && cabeza == false)
			return true;
		if (this.root.type == Terms.NPD && _rtn.root.type == Terms.DPN)
			return true;
		if (this.root.type == Terms.RNH && _rtn.root.type == Terms.NPD)
			return true;
		if ((this.root.type == Terms.PLN && _rtn.root.type == Terms.Y) && this.father == null)
			return true;
		if (this.root.type == Terms.DE && _rtn.root.type == Terms.PPN && cabeza == false)
			return true;
		if (this.root.type == Terms.DE && _rtn.root.type == Terms.RNA && cabeza == false)
			return true;
		if (this.root.type == Terms.RNA && _rtn.root.type == Terms.DE && this.root.position > _rtn.root.position
				&& _rtn.father != null) {

			return true;
		}
		// if (this.root.type==Terms.DE)System.out.println("DEDEDEDEDE");
		// if (this.root.type==Terms.PLN && _rtn.root.type==Terms.FPLN && cabeza==false)
		// return true;
		if (this.root.type == Terms.DE && _rtn.root.type == Terms.FPLN && cabeza == false) {

			// System.out.println("he netrado por facility)");
			return true;
		}

		if (this.root.type == Terms.DE && _rtn.root.type == Terms.Y & this.root.position < _rtn.root.position)
			return true;

		if (this.root.type == Terms.DE && _rtn.root.type == Terms.GPLN && cabeza == false) {

			// System.out.println("he netrado por facility)");
			return true;
		}

		// if (this.root.type==Terms.ON && _rtn.root.type==Terms.DE &&
		// this.root.position>_rtn.root.position) return true;
		if (this.root.type == Terms.DE && _rtn.root.type == Terms.ON && cabeza == false) {

			// System.out.println("he netrado por facility)");
			return true;
		}
		// if (this.root.type==Terms.PLN && _rtn.root.type==Terms.FPLN && cabeza==false)
		// return true;
		if (this.root.type == Terms.DE && _rtn.root.type == Terms.RNF && this.root.position > _rtn.root.position) {

			return true;
		}
		if (this.root.type == Terms.RNS && _rtn.root.type == Terms.PLN) {
			return true;
		}

		if (this.root.type == Terms.RNS && _rtn.root.type == Terms.PPN) {
			return true;
		}

		if (this.root.type == Terms.NPN && _rtn.root.type == Terms.PPN && cabeza == true)
			return true;
		// System.out.println("he entrado por "+_rtn.root.string);

		// if (this.root.type==Terms.PPN && _rtn.root.type==Terms.DE && cabeza==false)
		// return true;
		if (this.root.type == Terms.PSS && _rtn.root.type == Terms.NPN)
			return true;
		if (this.root.type == Terms.AFPLN && _rtn.root.type == Terms.DE)
			return true;
		if (this.root.type == Terms.AFPLN && _rtn.root.type == Terms.AFPLN & this.root.position < _rtn.root.position)
			return true;
		if (this.root.type == Terms.AFPLN && _rtn.root.type == Terms.FPLN && cabeza == false)
			return true;

		if (this.root.type == Terms.AGPLN && _rtn.root.type == Terms.DE)
			return true;
		if (this.root.type == Terms.AGPLN && _rtn.root.type == Terms.AGPLN)
			return true;
		if (this.root.type == Terms.AGPLN && _rtn.root.type == Terms.GPLN && cabeza == false)
			return true;

		if (this.root.type == Terms.AON && _rtn.root.type == Terms.DE)
			return true;
		if (this.root.type == Terms.AON && _rtn.root.type == Terms.AON)
			return true;
		if (this.root.type == Terms.AON && _rtn.root.type == Terms.ON && cabeza == false)
			return true;

		if (this.root.type == Terms.ART && _rtn.root.type == Terms.APLN)
			return true;
                if (this.root.type == Terms.ART && _rtn.root.type == Terms.RNA)
			return true;
                
               
		if (this.root.type == Terms.PPN && _rtn.root.type == Terms.DPN)
			return true;

		if (this.root.type == Terms.PSTN && _rtn.root.type == Terms.PPN)
			return true;
                if (this.root.type==Terms.ADD && _rtn.root.type==Terms.PPN)
                    return true;

		return false;

	}


	public RoleTreeNode dependCopulativeOf(ListIterator<ItemRoles> it, BagItemRoles hword, WordBag wb, int i, Lexer lexer) {

		// if (Lexer.verbsFlag!=null) System.out.println("el verbo previo
		// copulativa"+Lexer.verbsFlag.current);
		boolean previousSingularVerb = false;
		if (lexer.verbsFlag != null) {
			previousSingularVerb = ElementsRecognition.isSingular(lexer.verbsFlag.verb);
		}
		// System.out.println("HE ENTRADO POR LA DEPENDENCIA DE LA COPULATIVA CON EL
		// VERBO PREVIO "+Lexer.verbsFlag.verb+" ACTUAL "+this.root.string+" TIPO
		// "+this.root.type+" SIGUIENTE "+it.next().node.root.type);
		// System.out.println("heads"+hword.tam()+previousSingularVerb);
		if (i == wb.wbag.size() - 1)
			return null;
		BagData next = wb.wbag.get(i + 1);

		// System.out.println("LA PALABRA SIGUIENTE ES "+next.string+" A VER
		// "+this.root.string+next.type+this.root.type);
		if (this.root.type == next.type && this.root.type == Terms.PLN) {
			// System.out.println("la busuqeda copulativa 1");
			if (previousSingularVerb) {
				// System.out.println("la busuqeda copulativa 11");
				return this;
			} else {
				// System.out.println("la busuqeda copulativa 12");
				if (hword.tam() > 0)
					return hword.get(0).node;
				else
					return null;
			}
		}

		if (this.root.type == next.type && this.root.type == Terms.PPN) {

			if (previousSingularVerb) {
				// System.out.println("la busuqeda copulativa 20");
				return this;

			} else {

				return hword.get(hword.tam() - 1).node;

			}
		}

		if (this.root.context == next.context && wb.get(i + 1).type == Terms.RNH) {
			// System.out.println("la busuqeda copulativa 2");
			if (previousSingularVerb) {

				return this;
			} else {
				return hword.get(hword.tam() - 1).node;
			}
		}

		if (wb.get(i + 1).type == Terms.DE) {
			// System.out.println("la busuqeda copulativa 3");
			ListIterator<ItemRoles> auxit = it;
			ItemRoles selectNode = auxit.next();
			while (selectNode.node.root.type != Terms.DE) {
				// System.out.println("estoy buscando el nodo
				// seleccionado"+selectNode.node.root.string);
				selectNode = auxit.previous();
			}
			// System.out.println("estoy buscando el nodo finl
			// seleccionado"+selectNode.node.root.string+selectNode.node.father.root.string);
			return selectNode.node;
		}
		if (this.root.type == wb.get(i + 1).type && this.root.type == Terms.RNA) {
			// System.out.println("la busuqeda copulativa 4");
			return this;
		}

		if (this.root.type == wb.get(i + 1).type && this.root.type == Terms.PPN) {
			// System.out.println("la busuqeda copulativa 5");
			if (this.father == null) {
				return null;
			} else {
				return this.dependCopulativeOf(it, hword, wb, i, lexer);
			}
		}
		// System.out.println("la busuqeda copulativa 6");
		// System.out.println("NO HE ENCINTRADO NINGUNA DEPENDENCIA");
		return null;
	}

}
