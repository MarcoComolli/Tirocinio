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

------

Commit con commenti fatto.
Il problema con gli else if è che non so bene dove mettere la dichiarazione dell'array prima delle else. Se per esempio ho
> if (x % 2 == 0 && x == 2) {
			y++;
		} else if (x == 0) {
			y--;
		}
		
non so dove mettere la dichiarazione per la condizione del secondo if, perchè se la mettessi prima dell'else, verrebbe eseguita solo se il blocco del primo if fosse eseguito.
Per il for il problema è che la condizione interna molte volte dipende da una variabile dichiarata internamente al for.

tipo:
> for (int i = 0; i < 10; i++) {
			y++;
		}

in questo caso per la condizione i<0 nella dichiarazione di un array, la i me a dà come non dichiarata.

-----

Se ti interessa è spiegato tutto qua in modo semplice https://help.github.com/articles/resolving-merge-conflicts. 

Hai ragione non ci avevo proprio pensato.
Mi è venuta un'idea. Mettili prima del tracer. Ti faccio un esempio (per quanto riguarda if ed else intendo).'

> if (x % 2 == 0 && x == 2) { boolean[] arr1 = new boolean[2]{x % 2 == 0,x == 2};MyTracerClass.tracer(....., arr);  
			y++;  
		} else if (x == 0) { boolean[] arr1 = new boolean[1]{x== 0};MyTracerClass.tracer(....., arr);  
			y--;  
		}  

Così teoricamente dovrebbe andare.

-----
Per if, if-else e for dovrei esserci. Per while funziona, ma micrea un problema per gli switch, credo sia dovuto al parser. Per il do-while non so bene come far a prendere la riga del while inerente al do, per lo switch non ho ancora provato, ma è un bel casino

Ok, ora ti invio la classe modificata, per gli switch attualmente non so se si può fare il tutto perché bisognerebbe salvare il valore inizial della stringa o carattere e poi valutare le condizioni con == o equals per ogni caso.
Il problema con gli switch dovuto al cambiamento del codice per i while per ora rimane ancora.

----
Beh ma comunque a questo punto potrebbe non essere necessaria la valutazione delle condizioni nello switch:  
se ci pensi la valutazione delle condizioni a noi serve solo quando ci sono più condizioni nello stesso predicato perchè se ne abbiamo una sola allora questa è verificata cioè è vera per ogni volta che si entra nel blocco.  
Quindi se entriamo nel blocco deve per forza essere vera l'unica condizione, cioè che sia == o equals. Secondo me quindi per gli switch potrebbe non essere necessario.  

Ho un problema con la rilevazione delle istruzioni. O meglio sul come passarle al tracer.  
Il programma scandisce linea per linea il codice però inserisce la chiamata al tracer all'inizio mentre io ho bisogno di arrivare alla fine del blocco per poter valutare quante istruzioni ci sono.  
Quindi stavo pensando di avere un'altra struttura dati nella classe FileParse in cui memorizzare il nome del blocco e quante istruzioni contiene e passarle alla classe MyTracerClass in un secondo momento. Ti può sembrare sensato?

Altro problema su cui sto lavorando adesso. Ho provato a testare la storia dei path su pmd.. risultato -> 236 errori. Inizio a correggerli piano piano e che dio me la mandi buona

------

Aggiornamento: mi dava qualche conflitto con i file caricati ma penso di aver sistemato adesso. Quindi teoricamente dovremmo avere ora gli stessi files.
Ho provato a far partire il tutto su pmd. Mi da una indexOutOfBoundException su una condizione if (indice -1) ora provo a controllare dove lo fa.

L'errore credo sia nei cicli for-each che cerca una condizione booleana all'interno del for e la trova. Ma non capisco perchè la trova infatti non ci sono punti e virgola. Mah ora provo a sistemare e se riesco faccio il commit

------

Ho aggiunto i cambiamenti sui file per inserire le stringhe di creazione degli array, sui file di prova dovrebbe funzionare tutto, comunque ora controllo meglio. Spero non ci siano problemi di conflitto tra i file.

-------

Ecco la linea con l'errore

> if (!CollectionUtil.areEqual(entry.getKey().defaultValue(),  
					entry.getValue())) {
					
lo dà giustamente perchè non abbiamo considerato espressioni booleane multi-linea.
Altre due cose:

- Sarebbe meglio mettere per tutti i costrutti prima del tracer invece che prima del costrutto, cioè dentro l'if prima  del tracer() e non sopra così non si sfalsano le righe
-  Ho capito perchè fa casini con il for-each. Praticamente lui vede il for e viene chiamato il metodo che cerca i gli opeatori booleani della classe BooleanExpressionParse lui arriva in fondo e vede che non c'è condizione con ; quindi pensa di essere in un'espressione normale e considera un operatore booleano tutto quello tra parentesi.  

Dobbiamo trovare un modo per distinguere i cicli for dai cicli for each. E non fargli parsare gli operatori quando ci sono i for-each. Ora come ora mi verrebbe in mente di identificare i for each dala presenza dei due punti **:** però bisogna tener conto che non devono essere in una stringa.

Sistemato il readMe. Faccio un elenco delle cose che dobbiamo fare ora così c'è più chiarezza:
- [x] Considerare la possibilità di espressioni booleane su più righe
- [x] Trovare una differenza tra for e for-each e fare una distinzione quando si parsano i valori booleani
- [x] Mettere le creazioni degli array su una sola riga prima della chiamata MyTracerClass.tracer(...)
- [ ] Risolvere gli errori dovuti al return per pmd (questo è per me)
- [ ] Ho notato ora che in alcuni metodi non viene inserito il MyTracerClass.tracer(...) all'inizio e bisogna capire il perchè

-----
Tu ora cosa stai facendo? Ho visto che hai sistemato i for-each e messo i cosi sulla stessa riga dei tracer. Stavo pensando di lavorare suli predicati su più righe ma non voglio creare conflitti se ci stai già pensando tu o se hai già trovato qualche soluzione

-----
Per ora non sto lavorando sui predicati

------

Ok però stasera non so se ci riesco a lavorare magari più sul tardi vedrò

-----

Mi sembra di aver risolto il problema delle condizioni su linee diverse. Il che ha aperto qualche altro problema ma poi penso a risolvere anche quelli.
Ho ritoccato tutti i metodi e spero di non aver creato conflitti con quello che avevi fatto tu. Al momento non mi sembra però non si può mai sapere.  
Ho aggiunto un metodo apposta per gli else-if diversificato dall'else e ho spostato il codice che avevi fatto per adattare l'else-if lì dentro. Dovrebbe funzionare.
Nel BooleanExpressionParser ho dovuto aggiungere una condizione un po' strana che sennò dava errore. Infatti quei brillantoni che hanno scritto PMD hanno inserito un ciclo infinito di questo tipo for(;;) e ovviamente non trovava la condizione in mezzo perchè non c'era (un while(true) era troppo banale per loro si vede vabbè..)  
Ultima cosa: sempre in quella classe lì ho marchiato con il TODO che bisogna sistemare quelle due condizioni perchè in righe come questa crede che ci sia un for-each perchè vede i **:** 
> if (s.startsWith("::")) {

e fa questo scherzetto  
> if (s.startsWith("::")) {forEach  MyTracerClass.tracer("rule/basic/AvoidUsingHardCodedIPRule isIPv6,boolean,.char String boolean boolean ",0,4);

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




