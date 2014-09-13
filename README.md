Annotazioni
=========

IDEA PER IL PUNTO 1:
Nel MyTracerClass dobbiamo creare una nuova struttura dati per creare i cammini. Un cammino deve avere un identificativo che secondo me potrebbe essere qualcosa come CLASSE + NOME DEL METODO + NUMERO DELLA SUA ESECUZIONE e come dato per tenere in memoria il cammino si può tenere un'istanza di una list (linkedList, arrayList... ecc) contenente CLASSE/METODO + NUMERO DI BLOCCO.
Esempio:

BaseConstruct/MethodIf-3 --> BaseConstruct/MethodIf@1 || BaseConstruct/MethodIf@2 || BaseConstruct/MethodIf@5 || BaseConstruct/MethodIf@6

dove BaseConstruct/MethodIf-3 vuol dire cammino del MethodIf alla sua terza esecuzione. e Gli altri dopo la @ sono il numero dei blocchi.  
Io pensavo tipo ad un TreeMap o un Hashmap con key l'identificativo e come data una lista.
Altro problema che mi viene in mente. Se i percorsi dovessero risultare troppo grandi per la memoria si può, dopo un tot di dati aggiornare un file di testo con i cammini.  

----

All'inizio del metodo oltre alla chiamata al metodo tracer() ho messo anche una chiamata al metodo recordPath() che ho creato in MyTracerClass. Praticamente questo metodo setta a true una variabile booleana che indica quando deve registrare un percorso e inizializza la lista che dovrà tenere traccia dei blocchi e l'identificativo del percorso.
Da questo momento ogni volta che entra nel tracer gli faccio aggiungere alla lista il blocco chiamato.
Alla fine prendo la lista, ne faccio una copia (perchè penso che altrimenti mi passerebbe un riferimento a quella statica che viene azzerata ogni volta..penso) e inserisco nell'hashmap di percorsi.

-------

Dunque: quello che ora mi metto a fare è una roba di questo genere per il return:
se il metodo ha come return type il void allora uso il codice che ho appena scritto che dovrebbe funzionare
se invece il metodo ha un tipo diverso dal void inserisco la chiamata a quel metodo prima di ogni return in tutto il metodo.

-----

Per risolvere i conflitti del merge se ti interessa è spiegato tutto qua in modo semplice https://help.github.com/articles/resolving-merge-conflicts. 

----

Ho un problema con la rilevazione delle istruzioni. O meglio sul come passarle al tracer.  
Il programma scandisce linea per linea il codice però inserisce la chiamata al tracer all'inizio mentre io ho bisogno di arrivare alla fine del blocco per poter valutare quante istruzioni ci sono.  
Quindi stavo pensando di avere un'altra struttura dati nella classe FileParse in cui memorizzare il nome del blocco e quante istruzioni contiene e passarle alla classe MyTracerClass in un secondo momento. Ti può sembrare sensato?

-----

Allora sì, per la storia dei punti e virgola sarebbero da contare per tutti i blocchi. Ci avevo iniziato a lavorare e se vedi nel codice avevo aggiunto questo metodo findInstructions() che conta i punti e virgola nella linea che gli passi e restituisce l'intero (non conta quelli presenti nelle stringhe) e anche il campo currentIstructionCount. Puoi usare quelli se vuoi.  
Il problema stava, come avevo scritto un po' più in su, nel come passare i dati alla classe my tracer visto che processando riga per riga bisogna attendere la fine del blocco prima di sapere il numero effettivo. Ti cito qual'era il problema se ti sembra senstato
> Ho un problema con la rilevazione delle istruzioni. O meglio sul come passarle al tracer.  
Il programma scandisce linea per linea il codice però inserisce la chiamata al tracer all'inizio mentre io ho bisogno di arrivare alla fine del blocco per poter valutare quante istruzioni ci sono.  
Quindi stavo pensando di avere un'altra struttura dati nella classe FileParse in cui memorizzare il nome del blocco e quante istruzioni contiene e passarle alla classe MyTracerClass in un secondo momento. Ti può sembrare sensato?

------

Io pensavo di effettuare il conteggio con il metodo findInstructions() su ogni linea e incrementare il campo currentIstructionCount e questo lo faccio quando il file viene letto, il problema è quando inserire nella struttura dati(un HashMap per ora) l'identificativo del metodo-blocco e il conteggio delle istruzioni.

------

Per il tuo problema stavo pensando....dunque, visto così ti serve sicuramente un booleano (qualcosa del tipo countInstruction o startCount) che quando è settato a true dice che bisogna contare i punti e virgola, mentre quando è settato a false non bisogna contare. Quindi la chiamata a findInstruction() sarà effettuata solo quando questo campo è true.  
Il problema ora è: quando farlo? Secondo me sarebbe il caso di farlo dopo aver inserito un tracer che non sia il tracer iniziale del metodo (infatti il tracer indica l'inizio del blocco ma bisogna stare attenti a fare in modo di non contare la linea del tracer (con il tracer già aggiunto) altrimenti conterebbe anche i punti e virgola del codice che abbiamo aggiunto noi).  
Settarlo invece a false quando il blocco finisce (qui non saprei si potrebbe vedere qualcosa con le parentesi graffe...al momento non mi viene in mente niente).  
Quindi il procedimento sarebbe: 
> boolean = false --> trovo metodo -> trovo inizio blocco -> inserisco tracer del blocco -> boolean = true -> trovo la fine del blocco -> boolean = false -> inserisco il numero di istruzioni, il metodo corrente con il numero di blocco corrente nella hashmap -> inizializzo a 0 il conteggio delle istruzioni

Ci sarebbe un altro problema però in questo modo. Se c'è un'istruzione fuori da un blocco il metodo non la conterebbe. Per esempio:

```Java
public void method(){ //qua viene inserito il tracer metodo
	System.out.println("Io non vengo contata");  // <--- Questa istruzione non viene contata perchè non è in nessun blocco ma appartiene al metodo 
	if(x== 0){
		//interno del blocco
	}

}
```
------


Sto provando quello che mi hai detto, un problema è che tenendo conto della parentesi graffa chiusa per un blocco succede che mi conta 1 sola istruzione per un blocco di questo tipo anzichè 2:
```Java
if (y == 0) {boolean[] ilMioArrayDiBooleani4 ={y == 0};  MyTracerClass.tracer("src/originalFiles/BaseConstructDue methodIf,void,.",0,6);
			int x = 0;
		}

		x = y > 3 ? 5 : 2;
	 MyTracerClass.endRecordPath("src/originalFiles/BaseConstructDue methodIf,void,.");}
```

Come hai detto tu, per ora le istruzioni prima dei blocchi non vengono contate.
Nel caso eventuale in cui non ce la facessimo a trovare un modo per avere il numero preciso delle istruzioni potremmo sparare un po' a caso, tenendo buono il conteggio molto approssimato.

Se vuoi faccio il commit, dovrei aver lavorato sulla versione tua ultima aggiornata, perché ho rifatto tutto.
Per ora non funziona molto bene perché ha i problemi che ho detto e forse altri.

------

Il passaggio dell'array al metodo tracer funziona sui file di prova. Il passaggio del conteggio elle istruzioni al tracer è più complicato, perchè il termine del conteggio fine effettuato a fine blocco con conseguente inserimento nell'hashMap mentre il metodo viene inserito a inizio blocco. Non saprei quando poter inserire il conteggio delle istruzioni nel metodo.
Magari si potrebbe accedere al numero di istruzioni durante l'esecuzione del metodo tracer, ma ciò richiederebbe salvare esternamente tutti i dati contenuti nell'HashMap.

------

Ok, ho testato su pmd il tuo codice è non mi dà errori.  
Per quanto riguarda il salvataggio del conteggio delle istruzioni si potrebbero salvare in 2 modi secondo me:  
O mettendo poco prima della chiamata endRecordPath() una chiamata ad un metodo in modo tale che aggiunga tutti i dati nuovi appena raccolti nella struttura dati del MyTracer  
oppure come dici tu si può salvare alla fine del preprocessing l'hashmap su file, parsare il file e inserirlo nel my tracer.  
Oppure magari si può fare qualcosa tipo che a fine esecuzione la mappa che abbiamo salvato va passata al mytracer direttamente nel parenthesysAdder ma non so se è fattibile questo.
Ho notato un altro problemino (che palle lo so). Quando c'è una classe anonima interna i metodi di quella classe sono salvati nel myTracer con il nome della classe più esterna. Non so se questo potrebbe generare un conflitto o poca chiarezza nel "report" finale. Ora mi dedico ai throw

------

Il numero delle istruzioni interne ad un blocco dovrei inserirlo nell'HashMap countMap della classe MyTracer, giusto? In questo caso posso cambiare l'HashMap da (String,Integer) ad uno con chiave String e con valore una struttura dati che mi permetta di salvare due interi (uno per il numero di volte del blocco eseguito e uno per il numero di istruzioni all'interno del blocco)?
Purtroppo per il problema dei throw, per ora non ti so aiutare.

-----
Sisi si può fare in 2 modi.  
O Crei un'altra mappa apposta per le istruzioni del blocco (così poi ne abbiamo 2, una per il numero delle volte e una per il numero di istruzioni)  
oppure fai come hai detto tu.  
A dirti la verità non ho idea di quale delle due richieda più memoria, se 2 hashmap semplici oppure 1 hashmap più complessa. A questo punto direi di fare come preferisci per me è uguale.  
p.s. cerca di non usare le parentesi angolari '<' e '>' qua perchè mi sa che è un simbolo per nascondere il contenuto.

Per quanto riguarda il throw ci sto lavorando da qualche ora e ho scoperto casini che neanche ti immagini. Ho tra l'altro scoperto perchè non metteva alcuni tracer all'inizio e perchè alcuni erano sbagliati. E' tutto sbagliato l'ordine in cui legge la riga all'inizia e incrementa il contatore di linea...pure l'inserimento dei tracer si riferiscono a righe sbagliate senza i commenti rimossi e fanno macello.  
Ora ho risolto in parte questo problema e quello dei throw. Ma cambiando lì mi ha ovviamente portato fuori altri errori.
Per la risoluzione dei throw senti qua la follia che mi è venuta in mente:  
per sapere se inserirlo o no alla fine mi serve sapere qual'è l'ultima istruzione appartenente al metodo. Allora ho tenuto in una stringa temporanea l'ultima riga di codice appartenente al metodo e a nessun blocco (l'ho calcolato in base al numero di parentesi { spaiate) Se arrivo in fondo al metodo guardo l'ultima riga salvata e calcolo l'ultima istruzione. Se è un throw o un return allora non inserisco l'endOfPath().  
C'ero quasi riuscito solo che in un paio di casi su tutto il programma (mannaggia a loro) il throw era dentro un blocco catch alla fine del metodo e sono arrivato fino a questo punto. Devo sbrogliare questo problema e con i throw/return sono a posto. 
Quando hai qualcosa ti conviene fare subito il commit perchè quando farò io (se mai riesco a risolvere) ci sono un patafracco di cambiamenti al fileParser e non vorrei che poi ti tocchi fare il merge di tutto sto bordello.

Altro errore di cui mi sono accorto: ASTParser non tiene conto dei metodi dichiarati all'interno di un'istruzione...tipo

```Java
protected void indexNodes(List<Node> nodes, RuleContext ctx) {
		PLSQLParserVisitor plsqlParserVistor = new PLSQLParserVisitorAdapter() {
			@Override
			public Object visit(PLSQLNode node, Object data) {
				return super.visit(node, data);
			}
		};
		LOGGER.exiting(CLASS_NAME, "indexNodes");
}
```
Legge il metodo indexNodes() ma il visit() dentro non lo conta. E' un problema tralasciabile nel senso che non crea conflitto ma alla fine il programma non tiene conto della copertura di quei metodi.  
Lo lascio come un punto da fare ma se ci avanza tempo

-----
Mi sa che forse inserire il numero di istruzioni nella struttura dati del MyTracer prima dell'inserimento della chiamata endRecordPath() non è possibile. Non vorrei dire una cazzata, vediamo se ho capito bene. 
In pratica prima di inserire la chiamata al metodo endRecordPath(), nella classe FileParser, dovrei prendere dall'HashMap il numero di istruzioni di un blocco ed inserirle all'interno dell'HashMap della classe MyTracer.
Il problema è che il metodo tracer viene eseguito dopo aver effettuato il preprocessing e quando esso viene eseguito, nella strttura dati che dovrebbe contenere i numeri di istruzioni inseriti precedente, non c'è più niente.
L'ho spiegato male e probabilmente mi sto sbagliando.
Delle volte, quando dovrei inserire il numero di struzioni nell'HashMap della classe MyTracer incontro delle nullPointer perché cerco il valore di una chiave(metodo-blocco) che non è presente nell'hashMap che contiene il numero di istruzioni appartenente alla classe fileParser.

-----

Sisi ho capito il problema. Hai ragione tu non si può.  
Dobbiamo per forza passare tutto nel preprocessing e non aspettare che venga eseguito.  
Difatti il numero di istruzioni deve essere presente prima dell'esecuzione dei test.  
A questo punto direi o di salvare il numero di istruzioni in qualche variabile e nella chiamata dell'endprocess() passare tutto al nel MyTracerClass (ma è abbastanza incasinato perchè verrebbe un metodo lunghissimo a seconda del numero di blocchi nel metodo)  
Oppure conviene salvare il tutto su un file separato e poi quando è necessario uno va a prenderlo. Tipo il file MetodiTirocinio che fa ASTParser

--------

Quasi risolto tutto. 
Mi rimangono solo 2 errori in tutto il progetto di cui 1 si risolverà quando iniziamo a pensare all'interfaccia grafica perchè servirà trovare un modo per identificare la classe (questo problema era un conflitto di 2 nomi uguali in 2 package diversi)  
Il secondo è un problema che deriva solo da un try-catch e devo pensare come risolverlo.  
Una volta fatto questo le endRecordPath() dovrebbero essere tutte a posto e sarei pronto per il commit.

-----
Ok risolto anche l'ultimo problema...Posso fare il commit o devi committare qualcosa prima?
p.s. ho visto la stampa su file. Secondo me ti conviene metterla in un formato facilmente parsabile tipo invece del 
```blocco()  Numero di istruzioni:  10```
si potrebbe mettere al posto di "numero di istruzioni:" un carattere speciale a scelta così quando facciamo il parsing ci basterà splittare con quel carattere
```blocco()#10 ``` o roba simile.

-----
Quando vuoi, fai pure il commit.

-------
Ho dato un ordine di priorità alla todoList. Se trovi qualcosa di incongruente cambia/aggiungi pure.  
Ora mi stavo dedicando un po' all'interfaccia grafica faccio il commit di quel poco che ho fatto così evitiamo commit troppo grossi che sfalsano tutto.
C'è una cosa che bisogna fare assolutamente (finito di mangiare provo a guardare su internet se c'è qualcosa di già fatto) se hai già idea di come fare o lo sai scrivi pure.
In pratica bisogna riuscire dal path del file a ottenere la nomenclatura della classe (in pratica bisogna riuscire a tirare fuori il risultato del metodo getClass() di un oggetto avendo solo il path del file del file sorgente di quell'oggetto). Server assolutamente per far partire i test (e secondariamente per risolvere un problemino con la nomenclatura dei file per pmd).  
In parole povere se ho ad esempio questo percorso: 

```C:\Users\Marco\Desktop\pmd-src-5.1.1\src\main\java\net\sourceforge\pmd\lang\java\typeresolution\rules\imports\UnusedImports.java ```

con questo path:

```/pmd/src/main/java/net/sourceforge/pmd/lang/java/typeresolution/rules/imports/UnusedImports.java ```

dovrei ottenere una stringa di questo tipo (l'ho presa dal metodo getClass()) 

```net.sourceforge.pmd.lang.java.typeresolution.rules.imports.UnusedImports ```

che corrisponde all'identificatore univoco della classe.
Parsare il path e sostutire i punti non penso sia un'opzione perchè il percorso/path varia ogni volta in base ai package e a dove è messo.

-----

Per ora non ho trovato niente, se il parsing non è fattibile e non c'è nessun metodo che fa questa cosa non saprei come fare. Forse bisogna cercare qualche altro metodo per far partire le classi di test per Junit.


TODO LIST
=========

####### Task principali (maggiore urgenza)

- [ ] Trovare la giusta nomenclatura per i file sorgenti
- [ ] Gestire nel MyTracerClass i dati raccolti

-------

####### Task secondari (non indispensabili

- [ ] Testare codice per il conteggio delle istruzioni
- [ ] Codice per contare le istruzioni all'interno dei metodi ma fuori dai blocchi
- [ ] Codice per passare al myTracerClass il conteggio delle istruzioni
- [ ] Gestire ASTParser per fargli riconoscere i metodi dichiarati internamente a istruzioni

 
------
 
Tirocinio
=========

parsing logical boolean expressions java --> stringa di ricerca


- [x] per ogni esecuzione di metodo tenere traccia dei vari cammini (1)
 	- [x] modificare il mytracer (inizia a registrare quando trovi una chiamata con codice -1)

- [x] condition coverage (2)
	- [x] cercare parser espressioni
	- [x] inserire istruzioni prima dei blocchi if per sapere il valore della condizione
	- [x] tenere traccia delle condizioni

- [x] copertura delle istruzioni interne ai blocchi (3)
	- [x] cercare un tool che tenga conto del numero istruzione
	- [x] contare i punti e virgola in caso negativo
	- [x] sapere quante istruzioni per blocco e tenerne traccia

- [ ] interfaccia grafica (4)

- [ ] copertura dei casi di test (5)
	- [ ] alla fine di ogni classe di test sapere quanto si è coperto del totale
	- [ ] giocare con le statistiche




