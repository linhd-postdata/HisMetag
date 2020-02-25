/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataAlgorithms;

import DataStructures.*;
import Recognition.ElementsRecognition;
import Recognition.Terms;
import MedievalTextLexer.Lexer;

import java.util.*;

/**
 *
 * @author mluisadiez
 */
public class DataAlgorithms {

	public static RolesTree roleAlgorithm(WordBag wbag, Lexer lexer) throws java.io.IOException, java.io.FileNotFoundException {

		BagItemRoles heads = new BagItemRoles();

		BagItemRoles wlist = new BagItemRoles();
		RolesTree rtree = new RolesTree();

		ListIterator<ItemRoles> ith = heads.bagIt.listIterator();

		ListIterator<ItemRoles> itw;
		
		//System.out.println("-------->"+wbag.tam());
		
		
		for (int i = 0; i < wbag.tam(); i++) {

			// System.out.println("LAS CABEZAS "+wbag.get(i).string);
			RoleTreeNode actual = new RoleTreeNode(wbag.get(i));
			ItemRoles it = new ItemRoles(actual);
			wlist.add(it);
			ith = heads.bagIt.listIterator();
			boolean estaenCabeza = false;

			while (ith.hasNext()) {

				RoleTreeNode aux = ith.next().node;
                        
				if (aux.dependOf(actual, true)) {
				//	System.out.println("LA CABEZA "+aux.root.string+" DEPENDE DE"+actual.root.string);
					// System.out.println ("los predecesores "+wlist.get(i).node.root.string);
					wlist.get(i).node.addPred(aux);
					aux.father = wlist.get(i).node;
					ith.remove();

					// System.out.println("sSE HA CAMBIADO EL PADRE DE "+actual.father+" EN
					// "+aux.root.string+" POR "+wlist.get(i).node.pred.get(0).root.string);
					estaenCabeza = true;

				}
			}
			// for (int j=0;j<heads.tam();j++)
			// System.out.println("el tamaño de las cabezas
			// "+heads.tam()+"eee"+heads.get(j).node.root.string+"
			// "+heads.get(j).node.pred.size()+" "+heads.get(j).node.suc.size());
			itw = wlist.bagIt.listIterator(wlist.tam() - 1);
			// System.out.println("he entrado por aqui"+wbag.tam());
			// System.out.println("EMPIEZO CON LOS PREVIOS "+itw.hasPrevious());
			if ((!estaenCabeza)) {
				while (itw.hasPrevious()) {
                                 
					// System.out.println("estoy entrando por el segundo bucle "+itw.previous());
					RoleTreeNode aux = itw.previous().node;
					RoleTreeNode auxNew;
					// System.out.println("ESTAMOS MIRANDO LOS PREVIOS DE "+actual.root.string+" CON
					// "+aux.root.string);
					if (ElementsRecognition.isCopulativeConjunction(actual.root.string).size() > 0) {
						// System.out.println("LA ACTUAL ES COPULATIVA "+actual.root.string+" CON EL
						// PREVIO "+aux.root.string);
						if ((auxNew = aux.dependCopulativeOf(itw, heads, wbag, i, lexer)) != null) {
							// System.out.println("Estas son las primeras
							// fraswes"+actual.root.string+"---"+auxNew.root.string);
							actual.pred.add(auxNew);

							// System.out.println("SE AÑADE UN NUEVO NODO "+auxNew.root.string+" PARA LA
							// ACTUAL "+actual.root.string);
							// System.out.println("LOS PREDECESORES DE LA COPULATIVA SON
							// "+actual.pred.get(0).root.string);

							actual.father = auxNew.father;

							// System.out.println("BUSCO DE QUIEN DEPENDE LA COPULATIVA ");

							// System.out.println("EL PADRE DE LA ACTUAL ES "+actual.father.root.string+" Y
							// EL DE LA ANTERIOR ES "+aux.father.root.string);
							if ((auxNew.father != null) && heads.esta(auxNew.root.string)) {
								// System.out.println("SI YA TIENE MAS CABEZAS SE ELIMINA DE CABEZA ");
								heads.remove(auxNew.root.string);
							} else if (auxNew.father != null && actual.root.type != Terms.Y) {
								// System.out.println("ek
								// "+aux.father.root.string+actual.father.root.string+auxNew.father.suc.size());
								auxNew.father.suc.remove(auxNew);
								auxNew.father.suc.add(actual);
								// actual.pred.add(actual);
							} else {
								// System.out.println("CUALES SON LO SUCESORES ");
								// auxNew.father.suc.remove(auxNew);
								// System.out.println("ENTRO POR ESTE CASO "+aux.root.string+"
								// "+actual.root.string);
								if (aux.father != null) {
									// System.out.println("cual voy a ñadir "+aux.father.root.string+"
									// "+actual.root.string);
									// aux.father.suc.add(actual);
								}

							}

							auxNew.father = actual;

							// System.out.println("los padres nuevos "+auxNew.father.root.string+"y el
							// actyal "+actual.root.string);

							// System.out.println("la cabea de
							// "+actual.pred.get(0).root.string+auxNew.father.root.string+aux.father.root.string+actual.root.string);

							// System.out.println("la ca"+verd);
							if (auxNew.father != null && aux.father != actual) {

								// System.out.println("RECOLOCAR LA RAIZ POR RAICES DUPLICADAS
								// "+auxNew.father.suc.size());
								// System.out.println("REMOVE "+auxNew.root.string+"
								// "+auxNew.father.suc.get(0).root.string);
								auxNew.father.suc.remove(auxNew);
								auxNew.father = actual;

								// System.out.println("las cabezas duplicadas "+heads.get(0).node.root.string);
								// actual.pred.add(auxNew);
							}
							heads.bagIt.remove(heads.tam() - 1);
							// heads.add(new ItemRoles(actual));
							// System.out.println("estoy con las cabezas"+heads.tam());
						} else {

							// System.out.println ("ENTRO PER ESTA OPCION DE ELSE ");
							if (heads.tam() > 0) {
								RoleTreeNode auxp = heads.bagIt.get(heads.tam() - 1).node;
								// System.out.println("las veces que miro "+auxp.root.string);
								heads.bagIt.remove(heads.tam() - 1);
								actual.pred.add(auxp);
								auxp.father = actual;
								// heads.add(new ItemRoles(actual));
								// System.out.println("po aquie dse"+heads.tam()+ actual.root.string+"
								// "+actual.father+actual.pred.size());
								// System.out.println ("ENTRO PER ESTA OPCION DE ELSE "+actual.root.string+"
								// "+heads.tam());

							}
						}
						// System.out.println("las cabezas despues de todo
						// "+heads.get(0).node.suc.size());
						break;

					} else {
						// System.out.println("NO ES COPULATIVA
						// "+actual.root.string+actual.root.position+" EL AUX
						// "+aux.root.string+aux.root.position);
						if (actual.dependOf(aux, false)) {
							// System.out.println("SI LA ACTUAL "+actual.root.string+" DEPENDE DE
							// "+aux.root.string);

							aux.addSuc(actual);

							actual.father = aux;
							if (aux.root.type == Terms.DE && aux.suc.size() > 0) {
								ArrayList<RoleTreeNode> pred = new ArrayList<RoleTreeNode>();
								for (int l = aux.suc.size() - 2; l >= 0; l--) {
									if (aux.suc.get(l).dependOf(actual, false)) {
										pred.add(aux.suc.get(l));

										aux.suc.remove(l);
									}
								}
								for (int k = pred.size() - 1; k >= 0; k--)
									actual.pred.add(pred.get(k));
							} else {

							}

							break;
						}
					}

					if (aux.root == heads.bagIt.get(heads.tam() - 1).node.root)
						break;
				}
			}

			// if (actual.father!=null) System.out.println("la actual es
			// "+actual.father.root.string);

			if (actual.father == null) {
				// System.out.println("SI LA ACTUAL NO TIENE CABEZA SE AÑADE A CABEZAS
				// "+actual.root.string);
				heads.add(new ItemRoles(actual));
			}

			if (heads.tam() == 0)
				heads.add(new ItemRoles(actual.father));
			// for (int z=0;z<heads.tam();z++){
			// System.out.println("LAS CABEZAS QUE QUEDAN SON
			// "+heads.tam()+heads.get(z).node.root.string+heads.get(z).node.pred.size());

			// if (heads.get(z).node.pred.size()>0)
			// System.out.println("elpredecesor"+heads.get(z).node.pred.get(0).root.string);
			// }
			// System.out.println("falla aqui ");
			// System.out.println("VOY A PINTAR EL ARBOL----------------------");
			//

			rtree.tree = heads.get(0).node;
			// rtree.inOrden();
			// System.out.println(" ");
		}
		if (heads.tam() > 1) {
			// System.out.println("LAS CABEZAS DQUEUUU");
			RoleTreeNode newNode = new RoleTreeNode("");
			for (int i = 0; i < heads.tam(); i++) {
				// System.out.println("las cabezas son las que stoy
				// busando"+heads.get(i).node.root.string);
				newNode.addSuc(heads.get(i).node);
			}
			rtree.tree = newNode;
		}

		// System.out.println("que ha pasado"+rtree.tree.inOrden());
		// System.out.println("AL FINAL LAS CABEZAS QUE QUEDAN SON ---"+heads.tam()+"
		// "+heads.get(0).node.root.string);
		// rtree.tree=heads.get(0).node;
		// System.out.println("LA RAIZ DEL ARBOL FINAL "+rtree.tree.root.string+"
		// "+rtree.tree.pred.get(0).root.string);
		//System.out.println(rtree.inOrden());
		return rtree;

	}
}
