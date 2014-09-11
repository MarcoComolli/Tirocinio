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

Per il ciclo for quindi potresti fare così:
- o crei un'istanza della classe booleanoperatorparse e usi i metodi già fatti (magari cambiandoli non più statici)
- oppure copi quei due metodi nella classe FileParser e li chiami sul parse delle condizioni. 
Siccome mi sembra che la condizione specificata per il for sia l'unica, cioè che non ci sono altri casi particolari oltre il for, potresti fare magari un metodo a posta per il parse delle condizioni del for e poi invece di chiamare il solito metodo nella parte dove viene riconosciuto che c'è un ciclo for metti il nuovo metodo (lo so è un po' contorto)
Comunque prova a integrarlo e vedi se da un file di java normale riesce a tirare fuori gli operandi giusti.

-------

Stai comunque attentissimo a cercare di non modificare il numero di linee nel file originale perchè sennò è un bordello:  
infatti prima di applicare il processing memorizzo l'elenco dei metodi e a quale linea iniziano. Se col preprocessing i numeri di linea in cui iniziano i metodi cambiano penso succederebbe qualcosa di paragonabile all'apocalisse @.@

------

Ok tranquillo. Uhm forse hai ragione per il while del do-while non ci avevo pensato se ci avanza del tempo provo a modificarlo vedere se ci sono risultati apprezzabili.  

-----

Per risolvere i conflitti del merge se ti interessa è spiegato tutto qua in modo semplice https://help.github.com/articles/resolving-merge-conflicts. 

----
Beh ma comunque a questo punto potrebbe non essere necessaria la valutazione delle condizioni nello switch:  
se ci pensi la valutazione delle condizioni a noi serve solo quando ci sono più condizioni nello stesso predicato perchè se ne abbiamo una sola allora questa è verificata cioè è vera per ogni volta che si entra nel blocco.  
Quindi se entriamo nel blocco deve per forza essere vera l'unica condizione, cioè che sia == o equals. Secondo me quindi per gli switch potrebbe non essere necessario.  

Ho un problema con la rilevazione delle istruzioni. O meglio sul come passarle al tracer.  
Il programma scandisce linea per linea il codice però inserisce la chiamata al tracer all'inizio mentre io ho bisogno di arrivare alla fine del blocco per poter valutare quante istruzioni ci sono.  
Quindi stavo pensando di avere un'altra struttura dati nella classe FileParse in cui memorizzare il nome del blocco e quante istruzioni contiene e passarle alla classe MyTracerClass in un secondo momento. Ti può sembrare sensato?

------

Faccio un elenco delle cose che dobbiamo fare ora così c'è più chiarezza:
- [x] Considerare la possibilità di espressioni booleane su più righe
- [x] Trovare una differenza tra for e for-each e fare una distinzione quando si parsano i valori booleani
- [x] Mettere le creazioni degli array su una sola riga prima della chiamata MyTracerClass.tracer(...)
- [ ] Risolvere gli errori dovuti al return per pmd (questo è per me)
- [ ] Ho notato ora che in alcuni metodi non viene inserito il MyTracerClass.tracer(...) all'inizio e bisogna capire il perchè

-----

Mi sembra di aver risolto il problema delle condizioni su linee diverse. Il che ha aperto qualche altro problema ma poi penso a risolvere anche quelli.
Ho ritoccato tutti i metodi e spero di non aver creato conflitti con quello che avevi fatto tu. Al momento non mi sembra però non si può mai sapere.  
Ho aggiunto un metodo apposta per gli else-if diversificato dall'else e ho spostato il codice che avevi fatto per adattare l'else-if lì dentro. Dovrebbe funzionare.
Nel BooleanExpressionParser ho dovuto aggiungere una condizione un po' strana che sennò dava errore. Infatti quei brillantoni che hanno scritto PMD hanno inserito un ciclo infinito di questo tipo for(;;) e ovviamente non trovava la condizione in mezzo perchè non c'era (un while(true) era troppo banale per loro si vede vabbè..)  
Ultima cosa: sempre in quella classe lì ho marchiato con il TODO che bisogna sistemare quelle due condizioni perchè in righe come questa crede che ci sia un for-each perchè vede i **:**  

``` if (s.startsWith("::")) {```

e fa questo scherzetto  

```Java
if (s.startsWith("::")) {forEach  MyTracerClass.tracer("rule/basic/AvoidUsingHardCodedIPRule isIPv6,boolean,.char String boolean boolean ",0,4);
```

Quindi poi domani bisogna darci un'occhiata (non dovrebbe essere molto difficile, avevo già fatto un metodo che si chiamava qualcosa tipo checkInString o qualcosa del genere poi domani ci penso)

-----

Intanto io cosa potrei fare? Dici che posso iniziare con il punto 3? I punti virgola in questo caso sarebbero da contare per ogni singolo blocco, giusto?
Altra cosa, magari oggi o domani potremmo aggiornare il prof sull'andamento del tirocinio. 

-----

Allora sì, per la storia dei punti e virgola sarebbero da contare per tutti i blocchi. Ci avevo iniziato a lavorare e se vedi nel codice avevo aggiunto questo metodo findInstructions() che conta i punti e virgola nella linea che gli passi e restituisce l'intero (non conta quelli presenti nelle stringhe) e anche il campo currentIstructionCount. Puoi usare quelli se vuoi.  
Il problema stava, come avevo scritto un po' più in su, nel come passare i dati alla classe my tracer visto che processando riga per riga bisogna attendere la fine del blocco prima di sapere il numero effettivo. Ti cito qual'era il problema se ti sembra senstato
> Ho un problema con la rilevazione delle istruzioni. O meglio sul come passarle al tracer.  
Il programma scandisce linea per linea il codice però inserisce la chiamata al tracer all'inizio mentre io ho bisogno di arrivare alla fine del blocco per poter valutare quante istruzioni ci sono.  
Quindi stavo pensando di avere un'altra struttura dati nella classe FileParse in cui memorizzare il nome del blocco e quante istruzioni contiene e passarle alla classe MyTracerClass in un secondo momento. Ti può sembrare sensato?

Preferirei domani così oggi abbiamo tempo di sistemare i punti 1 e 2 e magar riuscire a fare il 3. Mi sembra comunque un ottimo progresso fare 3 punti in 3 giorni. Intanto ora inizio a pensare ai problemi che ti avevo citato sopra

------

Risolto il problema delle condizioni stringhe/char ma mi sono accorto che dà problemi con predicati complessi rinchiusti tra parentesi tonde come qua ad esempio:

```Java
if (!node.isInterface() && node.isNested()
				&& (node.isPublic() || node.isStatic())) {
```				
la dichiarazione dell'array diventa così:
				
```Java
boolean[] ilMioArrayDiBooleani0 ={!node.isInterface() , node.isNested(), (node.isPublic() , node.isStatic())};  
```

dando un errore perchè in una condizione (node.isPublic() ha la parentesi aperta poi c'è la virgola che separa l'altra condizione con la parentesi chiusa ed è un problema. 
Hai in mente un modo per risolverlo?

------
Non so, forse bisognerebbe tenere conto dell'OR e considerarli come una sola condizione però aquesto punto non avremmo le valutazioni possibili delle due o più condizioni. In caso volessimo tenere anche le due condizioni singole si potrebbe fare così, ma non so se ha senso ed è fattibile:

```Java
boolean[] ilMioArrayDiBooleani0 ={!node.isInterface() , node.isNested(), (node.isPublic() || node.isStatic()), node.isPublic(), node.isStatic() };  
```
------
Beh penso che le tenere tutte le condizioni semplici senza anche la composizione di queste sia più che sufficiente. Il problema rimane comunque come tenere separate quelle unite dalle parentesi.
Penso che agirò direttamente nella classe BooleanExpressionParser. Qualcosa tipo cercare nella condizione booleana più grossa se ci sono delle parentesi tonde che han dentro &&,&,||,| e spezzettare anche quelle...boh ci penso dopo pranzo

------
Per ora purtroppo non ho risultati positivi. Non capisco bene ancora dove effettuare il conteggio delle istruzioni, dove incrementare questo campo e dove azzerarlo per far in modo che sia disponibile per un altro blocco.
Io pensavo di effettuare il conteggio con il metodo findInstructions() su ogni linea e incrementare il campo currentIstructionCount e questo lo faccio quando il file viene letto, il problema è quando inserire nella struttura dati(un HashMap per ora) l'identificativo del metodo-blocco e il conteggio delle istruzioni.

------
Anche qua niente di che ma ci sto lavorando...in qualche modo verrà fuori qualcosa di contorto come al solito.

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

Ce l'ho fatta! O almeno, su pmd, non mi da più errori per gli array booleani. Ci ho messo una vita cavolo! E ovviamente è tutto un po' contorto. Non ho ancora risolto la storia dei return ma ho una mezza idea di come fare (è un problema quando invece di **return** c'è un **throw** e basta che aggiungo quella parola chiave).
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
	- [ ] contare i punti e virgola in caso negativo
	- [ ] sapere quante istruzioni per blocco e tenerne traccia

- [ ] interfaccia grafica (4)

- [ ] copertura dei casi di test (5)
	- [ ] alla fine di ogni classe di test sapere quanto si è coperto del totale
	- [ ] giocare con le statistiche




