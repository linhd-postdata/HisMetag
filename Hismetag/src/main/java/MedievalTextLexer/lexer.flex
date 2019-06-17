package MedievalTextLexer;


import java.io.*;

import java.util.*;
import ContextProcessing.*;
import IOModule.*;
import DataStructures.*;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;
import Recognition.*;

%%

/////////////////////////////////////////////////////////////////////////////
// declarations section

// lexical analyser name
%class Lexer
%type Token
%unicode
%16bit
%public




// class members
%{
		// place any extra class members here
		static public SemanticContext context;

		static public Stack<SemanticContext> previousContextStack;
		static public StringInProcess.TokenizedString currentString;
		
		static public String lastToken; 
                static public String path;
		static public int numCh;
                static public int numWord;
		static public String firstToken;
                static public boolean isTheFirst;
                static public ContextProcessing.PreviousProcess previousProcess;
                static public String wordBag;
                static public String currentWord;
                static public ArrayList<String> headList=new ArrayList<String>();
                static public ArrayList<String> wordList=new ArrayList<String>();
                static public String properNameFlag="";
                static public String articleFlag="";
                static public String prepositionFlag="";
                static public Data.Verbs verbsFlag=new Data.Verbs();
                static public boolean fin=false;
                static public DataStructures.WordBag wbag=new DataStructures.WordBag();
                static public DataStructures.BagData root=null;
                static public DataStructures.WordBag wordinbag=new DataStructures.WordBag();

%}

// constructor
%init{
		// place any extra initialisation code here
                   this.path=path;
		numCh=1;
                numWord=1;
		previousContextStack = new Stack<SemanticContext>();

		context=new GeneralContext(null,path);
                StringInProcess.TokenizedString currentString=new StringInProcess.TokenizedString(path);

		previousContextStack.add(context);
		lastToken="";
		firstToken="";
                wordBag="";
                isTheFirst=true;
                previousProcess=PreviousProcess.EMPTY;
%init}

%eof{
try{
//System.out.println("he terminado");
wbag.restart();


}catch (Exception e){;}
fin=true;
%eof}


Letter=[a-zA-Z]
Digit=[0-9]
Character = [^ \t\[\]\&\/\.\n,:]|'~'|'''

WhiteSpace = [ \t]+

CapitalLetter = [A-Z-]|\u00C7

LowerCaseLetter = [a-z~’'-]|\u00e1|\u00E7|\u00e9|\u00ed|\u00f3|\u00fa|\u00f1



                

salto = [\n\r]+

blancoosalto =[ \t\r\n]+
Number = [0-9]+


TranscriptionChar=[<>\(\)\^?\[\]]



Folio = fol.{WhiteSpace}{Number}{Character}

AppositionPrepositions = ("de"|"del"|"De"|"Del")

CopulativeConjunction = ("et"|"y"|"e"|"o"|"&")


LowerCaseWord = {LowerCaseLetter}+({LowerCaseLetter})*


CapitalizeWord = {CapitalLetter}+{LowerCaseLetter}*|{CapitalLetter}+

Numeralm="."(({LowerCaseLetter}|{Digit})(".")?)+
Numeral="."(({Letter}|{Digit})(".")?)+

TruncatedCapitalWord={CapitalLetter}+({LowerCaseLetter}|"-\n")*

TruncatedLowerCaseWord={LowerCaseLetter}({LowerCaseLetter}|"-\n")*|{CapitalLetter}+

TranscriptionCapitalizeWord ={CapitalLetter}+({LowerCaseLetter}|{TranscriptionChar})*

TranscriptionLowerCaseWord ={LowerCaseLetter}({LowerCaseLetter}|{TranscriptionChar})*
StringwithArticlesOrTreatment= ({Article}{WhiteSpace}|{Treatment}{WhiteSpace})*{CapitalLetter}{LowerCaseLetter}+

StringwithArticles= ({Article}{WhiteSpace})?{CapitalLetter}+{LowerCaseLetter}*


PrepositionalSyntagmsList = {AppositionPrepositions}{WhiteSpace}{StringwithArticles}+(({WhiteSpace}{CopulativeConjunction})?({WhiteSpace}{AppositionPrepositions})?{WhiteSpace}{StringwithArticles})*

WordList ={StringwithArticlesOrTreatmentC}(({WhiteSpace}("et"|"y"|"e"))?{WhiteSpace}{StringwithArticlesOrTreatmentC})*

NounPhrasewithPrepositionalComplement ={StringwithArticlesOrTreatment}({WhiteSpace}{StringwithArticles})*{WhiteSpace}{AppositionPrepositions}{WhiteSpace}{StringwithArticles}({WhiteSpace}{StringwithArticles})? 

Article = ("La"|"El"|"Las"|"Los")

Treatment= ("Don"|"Donna"|"don"|"donna")

TreatmentC  =("Don"|"Donna")

StringwithArticlesOrTreatmentC = ({Article}{blancoosalto}|{TreatmentC}{WhiteSpace})*{CapitalLetter}{LowerCaseLetter}+


%state RUB








// place any declarations here

%%

/////////////////////////////////////////////////////////////////////////////
// rules section

// place your Lex rules here


//transcripcion

<YYINITIAL>{

"{"   {//System.out.println("he reconocido la llave");

}
"}"                         {System.out.println("la llavae qu");}
\[\^{Number}                        {;}
\]                         {;}
"["fol.{WhiteSpace}{Number}{Character}"]"  {Output.write(yytext());}
"{CB"{Number}"."{blancoosalto}*                {System.out.println ("estoy oir CB");}
"{RUB\."{WhiteSpace}         {System.out.println("he entrado por la rubrica");Output.write(new RoleTreeNode(yytext())); yybegin(RUB);}

"{IN"{Number}".}"             {Output.write(yytext());}
"&"                         {String word=yytext();
               currentWord=yytext();
                    context=context.checkLowerCaseWord(word);
		
				numCh+=yylength();numWord++;}
"["{WhiteSpace}"]"          {;}
"("{WhiteSpace}")"          {;}
\(							{;}
\)							{;}
"[%"{Digit}?"]"              {Output.write(yytext());}
"{BLNK.}"                   {Output.write(yytext());}
"{RMK:"{WhiteSpace}[^\}]*"}"         {System.out.println("el contexto inicial es"+context.getContext());System.out.println("entro por RMK  "+yytext());isTheFirst=true;Output.write(yytext());}
{Numeral}                   {Lexer.wbag.restart();Output.write(new RoleTreeNode(yytext()));}




[ \t]+  {try {Output.write(" ");numCh+=1;}catch(Exception e){;}}

\n|\r   {try{ System.out.println("la bolsa en el salto ");Lexer.wbag.escribir();

              if (Lexer.lastToken==""){
                  System.out.println("lallalllallla-saltodos");
              if (Lexer.wbag.tam()>0) {
                BagData ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
                  ultimo.string+=yytext();
              }else{
               Output.write(new RoleTreeNode(yytext()));
                    Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", " ");
              }
            }
              else {
                  System.out.println("la salto de "+Lexer.wbag.tam());
                  Lexer.wbag.put(new SaltoBagData(yytext(),TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
              }
              Lexer.isTheFirst=true;numCh+=1;
 System.out.println("SALTOOOOO_por_aqui");Lexer.isTheFirst=true;char salto[]={'\n'};Output.write(new RoleTreeNode(yytext()));numCh+=1;Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", " ");

}catch(Exception e){;}}




"<pb"{WhiteSpace}"n=\""{Folio}"=\">"  {;}

"<"{Character}+">"  {;}

"</"{Character}+">"  {;}

"/"2"/" {;}
"?" {;}
"^"  {;}
"#" {;}
"^"{Number}"#" {;}
"+" {;}
"/" {try{context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path);}catch(Exception e){;} }






{Number} {try{Output.write(yytext()); numCh+=yylength();numWord++;}catch(Exception e){;} }

","  {try{context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path);}catch(Exception e){;} }

";"	 {try{context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path);}catch(Exception e){;} }
":"  {isTheFirst=true; try{
if (Lexer.lastToken!=""){
                  System.out.println("lallalllallla-dospuntos");
              if (Lexer.wbag.tam()>0) {
                  System.out.println("la bolsa es por aqui"); Lexer.wbag.escribir();
                  Lexer.wbag.restart();
                    Output.write(new RoleTreeNode(Lexer.lastToken));
                    Lexer.lastToken="";
              }
              else  {
                  Output.write(new RoleTreeNode(Lexer.lastToken));
                  Lexer.lastToken="";
              }}
              else {
                  System.out.println("la salto de "+Lexer.wbag.tam());
                  Lexer.wbag.restart();
              }

context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path); System.out.println("he entrado por dos puntos");}catch(Exception e){;} }

"."{blancoosalto}  {
System.out.println("estoy en el punto");wbag.escribir();Lexer.verbsFlag=null;
isTheFirst=true;firstToken="";context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path); wordBag="";}

"." {System.out.println("estoy en el punto");wbag.escribir();Lexer.verbsFlag=null;
try{isTheFirst=true; firstToken=""; context.processingPoint(yytext());numCh+=yylength(); context=new GeneralContext(path); wordBag=""; }catch(Exception e){;}}

"por la gracia de Dios" { System.out.println("he reconocido porla gracia"); 
if (wbag.wbag.size()>0) wbag.put(new DataStructures.AddBagData(yytext(),TypesTerms.FT,numCh,numWord,numCh+21, new Recognition.InfoFound(),Lexer.context.getContext()));  numWord=numWord+5;numCh=numCh+21;  }
{salto} {try{Output.write("\n");numCh+=1;}catch(Exception e){;};}


{LowerCaseWord}  {

String word=yytext();
System.out.println("la entrada es de lowercase"+word);
               currentWord=yytext();
              // currentWord=WordProcessing.WordTransformations.replaceCharacters(word);
                
                    context=context.checkLowerCaseWord(currentWord);
		
numCh+=yylength();numWord++;
		          }

{CapitalizeWord} {System.out.println("he entrado por las mayuascuy");String word=yytext();
                    		  
currentWord=yytext();System.out.println("estoy en el contexto de "+context.getContext());
context.checkCapitalLettersWord(word).toString();
				           
                            numCh+=yylength();numWord++;
				  }


{TruncatedCapitalWord} {System.out.println("he entrado por las truncadas");
                            String word=yytext();
                            currentWord=yytext();
                              String nueva= word.replaceAll("-\n","");
                              nueva=nueva.replaceAll("n~","ñ");
                            
                    		  context.checkCapitalLettersWord(nueva).toString();
				           
                            numCh+=yylength()-1;numWord++;
				  }

{TruncatedLowerCaseWord} {System.out.println("he entrado por las truncadas");
                            String word=yytext();
                            currentWord=yytext();
                              String nueva= word.replaceAll("-\n","");
                              
                              nueva=nueva.replaceAll("n~","ñ");
                            System.out.println("he entrado por una minuscula truncada"+word);
                    		  context.checkLowerCaseWord(nueva).toString();
				           
                            numCh+=yylength()-1;numWord++;
				  }

{TranscriptionCapitalizeWord}  {
String word=yytext();
                String nueva= word.replaceAll("<","");
                nueva= nueva.replaceAll(">","");
                context.checkCapitalLettersWord(nueva);
                System.out.println("he reconocido la palabra contranscripcion "+yytext());numWord++;numCh+=yylength()-2;}

{TranscriptionLowerCaseWord}  {
String word=yytext();
  String nueva= word.replaceAll("<","");
                nueva= nueva.replaceAll(">","");
                context.checkLowerCaseWord(nueva);
System.out.println("he reconocido la palabra contranscripcion "+yytext());numWord++;numCh+=yylength()-2;}
{WordList} {

String newContext=context.wordListProcessing(yytext()).toString();

}
{NounPhrasewithPrepositionalComplement} {

String newContext=context.nounPhraseProcessing(yytext()).toString();

}


{PrepositionalSyntagmsList} {
System.out.println("he entrado por preposicional");
String newContext=context.prepositionalSyntagmsListProcessing(yytext()).toString();

}

. {System.out.println("llll"+yytext());}
}
<RUB>{
"/" {try{context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path);}catch(Exception e){;} }

"+" {;}
[ \t]+  {try { System.out.println("he visto un blanco en estado RUB");Output.write(" ");numCh+=1;}catch(Exception e){;}}

\n|\r   {try{ System.out.println("la bolsa en el salto ");Lexer.wbag.escribir();
Lexer.verbsFlag=null;
              if (Lexer.lastToken!=""){
                  System.out.println("lallalllallla-saltouno");
              if (Lexer.wbag.tam()>0) {
                  System.out.println("la bolsa es por aqui"); Lexer.wbag.escribir();
                  Lexer.wbag.restart();
                    Output.write(new RoleTreeNode(Lexer.lastToken));
                    Lexer.lastToken="";
              }
              else  {
                  Output.write(new RoleTreeNode(Lexer.lastToken));
                  Lexer.lastToken="";
              }}
              else {
                  System.out.println("la salto de "+Lexer.wbag.tam());
                  Lexer.wbag.restart();
              }
              System.out.println("SALTOOOOO_por_aqui");Lexer.isTheFirst=true;char salto[]={'\n'};Output.write(new RoleTreeNode(yytext()));numCh+=1;Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", " ");}catch(Exception e){;}}



\}             { System.out.println("he entrado por la ulitm llava");Output.write(new RoleTreeNode(yytext()));yybegin(YYINITIAL);}




{Number} {try{Output.write(yytext()); numCh+=yylength();numWord++;}catch(Exception e){;} }

","  {try{context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path);}catch(Exception e){;} }

";"	 {try{context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path);}catch(Exception e){;} }
":"  {isTheFirst=true; try{
if (Lexer.lastToken!=""){
                  System.out.println("lallalllallla-dospuntosdos");
              if (Lexer.wbag.tam()>0) {
                  System.out.println("la bolsa es por aqui"); Lexer.wbag.escribir();
                  Lexer.wbag.restart();
                    Output.write(new RoleTreeNode(Lexer.lastToken));
                    Lexer.lastToken="";
              }
              else  {
                  Output.write(new RoleTreeNode(Lexer.lastToken));
                  Lexer.lastToken="";
              }}
              else {
                  System.out.println("la salto de "+Lexer.wbag.tam());
                  Lexer.wbag.restart();
              }

context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path); System.out.println("he entrado por dos puntos");}catch(Exception e){;} }

"."{blancoosalto}  {
System.out.println("estoy en el punto");wbag.escribir();
isTheFirst=true;firstToken="";context.processingPoint(yytext());numCh+=yylength();context=new GeneralContext(path); wordBag="";}

"." {System.out.println("estoy en el punto");wbag.escribir();
try{isTheFirst=true; firstToken=""; context.processingPoint(yytext());numCh+=yylength(); context=new GeneralContext(path); wordBag=""; }catch(Exception e){;}}

"por la gracia de Dios" { System.out.println("he reconocido porla gracia"); 
if (wbag.wbag.size()>0) wbag.put(new DataStructures.AddBagData(yytext(),TypesTerms.FT,numCh,numWord,numCh+21, new Recognition.InfoFound(),Lexer.context.getContext()));  numWord=numWord+5;numCh=numCh+21;  }
 {salto} {try{Output.write("\n");numCh+=1;}catch(Exception e){;};}


{LowerCaseWord}  {

String word=yytext();
System.out.println("la entrada es de lowercase"+word);
               currentWord=yytext();
               
                    context=context.checkLowerCaseWord(word);
		
numCh+=yylength();numWord++;
		          }

{CapitalizeWord} {System.out.println("he entrado por las mayuascuy");String word=yytext();
                    		  
currentWord=yytext();System.out.println("estoy en el contexto de "+context.getContext());
context.checkCapitalLettersWord(word).toString();
				           
                            numCh+=yylength();numWord++;
				  }


{TruncatedCapitalWord} {System.out.println("he entrado por las truncadas");
                            String word=yytext();
                            currentWord=yytext();
                              String nueva= word.replaceAll("-\n","");
                              nueva=nueva.replaceAll("n~","ñ");
                            
                    		  context.checkCapitalLettersWord(nueva).toString();
				           
                            numCh+=yylength()-1;numWord++;
				  }

{TruncatedLowerCaseWord} {System.out.println("he entrado por las truncadas");
                            String word=yytext();
                            currentWord=yytext();
                              String nueva= word.replaceAll("-\n","");
                              
                              nueva=nueva.replaceAll("n~","ñ");
                            System.out.println("he entrado por una minuscula truncada"+word);
                    		  context.checkLowerCaseWord(nueva).toString();
				           
                            numCh+=yylength()-1;numWord++;
				  }

{TranscriptionCapitalizeWord}  {
String word=yytext();
                String nueva= word.replaceAll("<","");
                nueva= nueva.replaceAll(">","");
                context.checkCapitalLettersWord(nueva);
                System.out.println("he reconocido la palabra contranscripcion "+yytext());numWord++;numCh+=yylength()-2;}

{TranscriptionLowerCaseWord}  {
String word=yytext();
  String nueva= word.replaceAll("<","");
                nueva= nueva.replaceAll(">","");
                context.checkLowerCaseWord(nueva);
System.out.println("he reconocido la palabra contranscripcion "+yytext());numWord++;numCh+=yylength()-2;}
{WordList} {

String newContext=context.wordListProcessing(yytext()).toString();

}
{NounPhrasewithPrepositionalComplement} {

String newContext=context.nounPhraseProcessing(yytext()).toString();

}


{PrepositionalSyntagmsList} {
System.out.println("he entrado por preposicional");
String newContext=context.prepositionalSyntagmsListProcessing(yytext()).toString();

}

}