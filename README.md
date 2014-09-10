Annotazioni
=========

Ok ora inizio a dare un occhio a come fare la roba dei path del punto 1

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
Ora che abbiamo i token basta fare una cosa di questo tipo secondo me:
- prendere la lunghezza dell'array con gli operatori booleani (es. ho 3 condizioni la lunghezza dello split sarà 3)
- creare un array di booleani di quella dimensione
- fare gli assegnamenti con gli operatori appena presi prima della dichiarazione dell'if o del blocco


quello che bisognerebbe cercare di fare è avere il codice alla fine con qualcosa del tipo  
> boolean[] boolArr = new boolArr[array.length];  
> boolArr[0] = cond1;  
> boolArr[1] = stack.isEmpty();  
> if(cond1 && stack.isEmpty()) { MyTracerClass.tracer(nomemetodo, blockCode, ID, boolArr);  
> .....  
> } 

così poi si prende come paramentro nel MytracerClass.

Un'alternativa che mi viene in mente potrebbe essere qualcosa del tipo
> boolean[] boolArr = new boolArr[array.length]{cond1,stack.isEmpty()};  
> if(cond1 && stack.isEmpty()) { MyTracerClass.tracer(nomemetodo, blockCode, ID, boolArr);  
> .....  
> }  

Così da aggiungere una sola linea invece che tante quante l'array.  
Stai comunque attentissimo a cercare di non modificare il numero di linee nel file originale perchè sennò è un bordello:  
infatti prima di applicare il processing memorizzo l'elenco dei metodi e a quale linea iniziano. Se col preprocessing i numeri di linea in cui iniziano i metodi cambiano penso succederebbe qualcosa di paragonabile all'apocalisse @.@

Per il punto due abbiamo bisogno dell'interfaccia grafica perchè è da lì che riesco a far partire i test classe per classe quindi bisognerebbe prima sistemare quella. 

----

Vorrei però se non ti dispiace fare una prova quando hai un attimo di tempo:
Volevo vedere git come si comporta nel caso ad esempio io faccio il commit e aggiungo le righe 33 34 e 35 nel file FileParser. Mettiamo che tu nel tuo progetto sul tuo pc avevi aggiunto qualcosa di tuo alla riga 33 e fai la sincronizzazione. Cosa succede? Ti cancella la linea 33 che hai aggiunto tu e sostituisce le mie o dà un avvertimento? 

Del mio ho un problema che potrei risolvere in modo che però in una particolare situazione causi un errore.  
Ho questo codice:  
> do{  
...  
) while(x > 0);  
return y;  

quando trova il *do* inserisce correttamente il tracer ma non riesce a capire che l'ultimo while fa parte dell'intero costrutto do-while e quindi mi cerca di mettere un tracer per il *while* come se fosse un ciclo separato.  
La mia soluzione che potrebbe andare bene ma che è abbastanza scorretta è di dargli un booleano che metto a _true_ quando incontro *do* e quando incontro il *while* successivo invece lo setto a _false_.  
Se questo booleano è settato a true non considero quel *while*.  
Il problema sarebbe se ci fosse un *while* dentro un *do-while*. Abbastanza raro ma non impossibile.  
Pensi che se per il momento lo lascio dentro potrebbe dare problemi in futuro?  
Perchè in questo modo risolvo per tutti i *do-while* tranne per quelli innestati mentre se non faccio nulla mi danno problemi tutti i *do-while* e di conseguenza non mi funziona il programma.

Ok, al momento sembra funzionare. Riesco a salvare il path dei vari metodi. Ho solo testato sulle classi di prova però, non ho ancora provato su pmd.

------

Scusami, ieri sera alla fine non ho fatto niente perché ero stanco ed è un momento un po' incasinato per me, stamattina faccio quello che ti avevo detto. In ogni caso farò il commit solo se avrò qualcosa di funzionante, per evitare casini visto che non sono molto pratico. Al massimo se ho qualcosa di buono ma che non funziona de tutto te lo passo in un altro modo.
Per il do-while secondo me potrebbe andare bene anche così, di while interni a do-while non ce ne dovrebbero essere in generale. L'unica cosa che mi viene in mente è se si può gestire il while del do-while in modo diverso dal while tradizionale tenendo conto del ; immediatamente successivo ad esso.

Sto provando ad aggiungere la stringa con l'array, forse aggiungere una sola riga mi dà problemi di compilazione una singola riga forse poi mi dà problemi di compilazione se ci sono più if else all'interno di un metodo. Mi sa che bisogna tenere conto del nome delle variabili degli array creati.

Per il nome degli array stavo risolvendo aggiungendo alla stringa boolArray un contatore statico della classe. Non è una bellissima soluzione, ma non mi viene in mente niente.

Per gli altri costrutti tipo while, for, bisogna fare la stessa cosa degli if (inizializzare un array prima del while per esempio)?

------

Ok tranquillo. Uhm forse hai ragione per il while del do-while non ci avevo pensato se ci avanza del tempo provo a modificarlo vedere se ci sono risultati apprezzabili.  
Perchè ti dà problemi se ci sono più if-else all'interno di un metodo?  
Sisi credo vada benissimo la storia della varibile (magari non statica che tanto di FileParse ne viene creato uno per classe) e magari cambia il nome invece che boolArray che potrebbe magari essere un nome frequente mettere qualcosa di più infrequente tipo anche in italiano **ilMioArrayDiBooleani** (o qualcosa di inventato che non sia tipo **array** che magari è dichiarato da qualche parte nella classe e andrebbero in conflitto).  
Sisi credo proprio di sì. L'unica cosa da stare attenti è il for...e forse il do-while appunto che ha la condizione alla fine e non all'inizio. Se ci sono problemi scrivi pure che possiamo vedere assieme.
Quando leggi fammi un favore se puoi. Ho messo la classe **ClassediTestPerGit** nel baseConstruct. Se puoi scrivi due o tre linee di codice commentato tra la riga 5 e la riga 6 (tra i due commenti) e se riesci committa solo quel file così poi provo anche io a modificare quelle righe vedere cosa succede.
Ora faccio anche io un po' pulizia sul readMe e inizio magari a pensare qualcosa per il punto (3).

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

Ho provato con a mettere la roba del test dei conflitti e se c'è un conflitto ti avvisa e non te lo carica. Anzi ti aggiunge un'annotazione proprio nel codice per fartelo risolvere manualmente.  
Se ti interessa è spiegato tutto qua in modo semplice https://help.github.com/articles/resolving-merge-conflicts. 

Hai ragione non ci avevo proprio pensato.
Mi è venuta un'idea. Mettili prima del tracer. Ti faccio un esempio (per quanto riguarda if ed else intendo).'

> if (x % 2 == 0 && x == 2) { boolean[] arr1 = new boolean[2]{x % 2 == 0,x == 2};MyTracerClass.tracer(....., arr);  
			y++;  
		} else if (x == 0) { boolean[] arr1 = new boolean[1]{x== 0};MyTracerClass.tracer(....., arr);  
			y--;  
		}  

Così teoricamente dovrebbe andare.
Per il for prova con la stessa cosa cioè a metterlo dentro altrimenti non saprei.

-----
Per if, if-else e for dovrei esserci. Per while funziona, ma micrea un problema per gli switch, credo sia dovuto al parser. Per il do-while non so bene come far a prendere la riga del while inerente al do, per lo switch non ho ancora provato, ma è un bel casino

Per il do-while forse ho risolto, sto provando il tutto su una copia di ieri perché non vorrei fare casino tra le versioni, se vuoi ti invio la classe che ho modificato.

Ok, ora ti invio la classe modificata, per gli switch attualmente non so se si può fare il tutto perché bisognerebbe salvare il valore inizial della stringa o carattere e poi valutare le condizioni con == o equals per ogni caso.
Il problema con gli switch dovuto al cambiamento del codice per i while per ora rimane ancora.

----
Beh ma comunque a questo punto potrebbe non essere necessaria la valutazione delle condizioni nello switch:  
se ci pensi la valutazione delle condizioni a noi serve solo quando ci sono più condizioni nello stesso predicato perchè se ne abbiamo una sola allora questa è verificata cioè è vera per ogni volta che si entra nel blocco.  
Quindi se entriamo nel blocco deve per forza essere vera l'unica condizione, cioè che sia == o equals. Secondo me quindi per gli switch potrebbe non essere necessario.  
Per le classi che mi hai inviato ora le guardo però tu prova lo stesso a fare il commit di quello che hai fatto vedere se te lo fa fare e non credo di ci siano problemi.  
Tu stavi lavorando con la versione con già le modifiche che avevo fatto io per risolvere il problema dei path?

Ho un problema con la rilevazione delle istruzioni. O meglio sul come passarle al tracer.  
Il programma scandisce linea per linea il codice però inserisce la chiamata al tracer all'inizio mentre io ho bisogno di arrivare alla fine del blocco per poter valutare quante istruzioni ci sono.  
Quindi stavo pensando di avere un'altra struttura dati nella classe FileParse in cui memorizzare il nome del blocco e quante istruzioni contiene e passarle alla classe MyTracerClass in un secondo momento. Ti può sembrare sensato?


Tirocinio
=========

parsing logical boolean expressions java --> stringa di ricerca


- per ogni esecuzione di metodo tenere traccia dei vari cammini (1)
	- modificare il mytracer (inizia a registrare quando trovi una chiamata con codice -1)

- condition coverage (2)
	- cercare parser espressioni
	- inserire istruzioni prima dei blocchi if per sapere il valore della condizione
	- tenere traccia delle condizioni

- copertura delle istruzioni interne ai blocchi (3)
	- cercare un tool che tenga conto del numero istruzione
	- contare i punti e virgola in caso negativo
	- sapere quante istruzioni per blocco e tenerne traccia

- interfaccia grafica (4)

- copertura dei casi di test (5)
	- alla fine di ogni classe di test sapere quanto si è coperto del totale
	- giocare con le statistiche




