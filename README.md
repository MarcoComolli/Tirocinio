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




