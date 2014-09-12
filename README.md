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

-------

Ok tranquillo. Uhm forse hai ragione per il while del do-while non ci avevo pensato se ci avanza del tempo provo a modificarlo vedere se ci sono risultati apprezzabili.  

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

Preferirei domani così oggi abbiamo tempo di sistemare i punti 1 e 2 e magari riuscire a fare il 3. Mi sembra comunque un ottimo progresso fare 3 punti in 3 giorni.

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

Però è un problema che penso si ponga più avanti. Adesso sarebbe già un buon risultato riuscire a contare le istruzione dei blocchi

------

Non ho ancora risolto la storia dei return ma ho una mezza idea di come fare (è un problema quando invece di **return** c'è un **throw** e basta che aggiungo quella parola chiave).
Ah ho cambiato una cosa:  
Nel booleanExpressionParser, alla fine di tutto, avevamo un array con le condizioni, costruivamo da questo una stringa e poi la splittavamo nell'altro metodo del fileParser. Ho tolto il passaggio intermedio quindi ora restituisce un array di stringhe che dovrebbe contenere già le varie condizioni separate. Non dovrebbe far casini visto che su pmd non mi segna più errori.
Posso fare il commit di quello che ho fatto? O preferisci fare tu prima e poi faccio il merge io?

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

Beh ma nel blocco if difatti c'è una sola istruzione che è int x = 0; l'altra, quella con l'operatore booleano, non te la conta perchè non è nel blocco ma è nel metodo...è il problema che dicevamo prima. Sisi committa pure (guardo poi più sul tardi che ora non posso).  
p.s. perchè hai dovuto rifare? Non bastava fare il merge?

------

Ho rifatto tutto da 0 perché con la mia soluzione non andava bene. 

------

Cavolo mi spiace! La prossima volta committa te per prima che al massimo mi adatto io.  
Ho guardato il codice aggiornato e mi sembra ok.  
Hai già testato su qualche classe per vedere se ha problemi? Nel frattempo mi sono accorto di altre due cosine da fare. Tu ricordi per caso se poi è stato sistemato il problema del do-while nel caso ci fosse un while al suo interno? Non mi ricordo più.  

------

Il passaggio dell'array al metodo tracer funziona sui file di prova. Il passaggio del conteggio elle istruzioni al tracer è più complicato, perchè il termine del conteggio fine effettuato a fine blocco con conseguente inserimento nell'hashMap mentre il metodo viene inserito a inizio blocco. Non saprei quando poter inserire il conteggio delle istruzioni nel metodo.
Magari si potrebbe accedere al numero di istruzioni durante l'esecuzione del metodo tracer, ma ciò richiederebbe salvare esternamente tutti i dati contenuti nell'HashMap.

------

Sposto qua sotto l'elenco delle cose da fare aggiornato (creo una sezione nuova così rimane sempre in fondo come una specie di elenco dei compiti più immediati):

TODO LIST
=========
- [x] Risolvere problemi per il condition coverage
- [ ] Risolvere gli errori dovuti al return per pmd (questo è per me)
- [ ] Ho notato ora che in alcuni metodi non viene inserito il MyTracerClass.tracer(...) all'inizio e bisogna capire il perchè
- [ ] Bisogna passare nel tracer() l'array di booleani appena creato
- [ ] Gestire nel MyTracerClass l'array di condizioni che viene passato
- [x] Codice per contare le istruzioni nei blocchi
- [ ] Testare codice per il conteggio delle istruzioni
- [ ] Codice per contare le istruzioni all'interno dei metodi ma fuori dai blocchi
- [ ] Passare al myTracerClass il conteggio delle istruzioni
- [ ] Gestire nel myTracerClass il conteggio istruzioni
 

 
Tirocinio
=========

parsing logical boolean expressions java --> stringa di ricerca


- [x] per ogni esecuzione di metodo tenere traccia dei vari cammini (1)
 	- [x] modificare il mytracer (inizia a registrare quando trovi una chiamata con codice -1)

- [x] condition coverage (2)
	- [x] cercare parser espressioni
	- [x] inserire istruzioni prima dei blocchi if per sapere il valore della condizione
	- [x] tenere traccia delle condizioni

- [ ] copertura delle istruzioni interne ai blocchi (3)
	- [x] cercare un tool che tenga conto del numero istruzione
	- [x] contare i punti e virgola in caso negativo
	- [ ] sapere quante istruzioni per blocco e tenerne traccia

- [ ] interfaccia grafica (4)

- [ ] copertura dei casi di test (5)
	- [ ] alla fine di ogni classe di test sapere quanto si è coperto del totale
	- [ ] giocare con le statistiche




