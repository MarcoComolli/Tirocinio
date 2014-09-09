Annotazioni
=========
Per il conteggio delle istruzioni neanche io ho trovato niente, solo cose riguardanti il bytecode.
Di tools che fanno quello che cerchiamo probabilmente non ce ne sono.

--------

Ok ora inizio a dare un occhio a come fare la roba dei path del punto 1

IDEA PER IL PUNTO 1:
Nel MyTracerClass dobbiamo creare una nuova struttura dati per creare i cammini. Un cammino deve avere un identificativo che secondo me potrebbe essere qualcosa come CLASSE + NOME DEL METODO + NUMERO DELLA SUA ESECUZIONE e come dato per tenere in memoria il cammino si può tenere un'istanza di una list (linkedList, arrayList... ecc) contenente CLASSE/METODO + NUMERO DI BLOCCO.
Esempio:

BaseConstruct/MethodIf-3 --> BaseConstruct/MethodIf@1 || BaseConstruct/MethodIf@2 || BaseConstruct/MethodIf@5 || BaseConstruct/MethodIf@6

dove BaseConstruct/MethodIf-3 vuol dire cammino del MethodIf alla sua terza esecuzione. e Gli altri dopo la @ sono il numero dei blocchi. Ti può sembrare sensata? Se si che struttura suggerisci?
Io pensavo tipo ad un TreeMap o un Hashmap con key l'identificativo e come data una lista.
Altro problema che mi viene in mente. Se i percorsi dovessero risultare troppo grandi per la memoria si può, dopo un tot di dati aggiornare un file di testo con i cammini.  
----
All'inizio del metodo oltre alla chiamata al metodo tracer() ho messo anche una chiamata al metodo recordPath() che ho creato in MyTracerClass. Praticamente questo metodo setta a true una variabile booleana che indica quando deve registrare un percorso e inizializza la lista che dovrà tenere traccia dei blocchi e l'identificativo del percorso.
Da questo momento ogni volta che entra nel tracer gli faccio aggiungere alla lista il blocco chiamato.
Alla fine prendo la lista, ne faccio una copia (perchè penso che altrimenti mi passerebbe un riferimento a quella statica che viene azzerata ogni volta..penso) e inserisco nell'hashmap di percorsi.
Il problema più grande che ho qui adesso è: come faccio a dirgli 'smettila di registrare il percorso?' cioè, arrivato a fine metodo dovrei inserire qualcosa che gli dica 'setta a false la variabile recordPath' però al momento non saprei come fare. O meglio si potrebbe mettere a fine di ogni metodo una chiamata tipo setFalse() però questo richiederebbe di ritoccare ancora il codice...rischioso.. se hai altre idee fammi sapere

-------

Dunque: quello che ora mi metto a fare è una roba di questo genere per il return:
se il metodo ha come return type il void allora uso il codice che ho appena scritto che dovrebbe funzionare
se invece il metodo ha un tipo diverso dal void inserisco la chiamata a quel metodo prima di ogni return in tutto il metodo. Mi sembra la via più fattibile anche se riconoscere tutti i return potrebbe essere difficile..mah ora vedo

Pensavo che per evitare di modificare codice condiviso che poi coi commit facciamo bordelli (non ho ancora capito come fare il merge se devo essere sincero bisognerà fare qualche prova) mentre provo con la storia dei return potresti provare a integrare quello del punto (3). Cioè, ho visto che hai sistemato la cosa del condition per il ciclo for quindi potresti fare così:
- o crei un'istanza della classe booleanoperatorparse e usi i metodi già fatti (magari cambiandoli non più statici)
- oppure copi quei due metodi nella classe FileParser e li chiami sul parse delle condizioni. 
Siccome mi sembra che la condizione specificata per il for sia l'unica, cioè che non ci sono altri casi particolari oltre il for, potresti fare magari un metodo a posta per il parse delle condizioni del for e poi invece di chiamare il solito metodo nella parte dove viene riconosciuto che c'è un ciclo for metti il nuovo metodo (lo so è un po' contorto)
Comunque prova a integrarlo e vedi se da un file di java normale riesce a tirare fuori gli operandi giusti.
Oppure prova a iniziare col punto 2 come preferisci.

-------
Sto provando a fare la storia degli if, ma il problema è che non so come copiare il blocco interno del if che vado a spezzettare in più if ed ad inserire gli else prima di essi.
Il parser per ora funziona con le condizioni, ma il problema grosso è quello lì.

-------
Non credo sia necessario spezettare il tutto come aveva detto. Ora che abbiamo i token basta fare una cosa di questo tipo secondo me:
- prendere la lunghezza dell'array con gli operatori booleani (es. ho 3 condizioni la lunghezza dello split sarà 3)
- creare un array di booleani di quella dimensione
- fare gli assegnamenti con gli operatori appena presi prima della dichiarazione dell'if o del blocco

Il problema più grosso sarebbe l'ultimo punto secondo me. Ti faccio un esempio:
supponiamo di avere:  
> if(cond1 && stack.isEmpty()) {  
> .....  
> } 

allora l'array sarà fatto di 2 entry in questo modo  
> array: [cond1][stack.isEmpty()]

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

Così da aggiungere una sola linea invece che tante quante l'array. Stai comunque attentissimo a cercare di non modificare il numero di linee nel file originale perchè sennò è un bordello:  
infatti prima di applicare il processing memorizzo l'elenco dei metodi e a quale linea iniziano. Se col preprocessing i numeri di linea in cui iniziano i metodi cambiano penso succederebbe qualcosa di paragonabile all'apocalisse @.@

-----
Ho fatto un po' di pulizia nel readMe e mi sono accorto di una cosa. Per il punto due abbiamo bisogno dell'interfaccia grafica perchè è da lì che riesco a far partire i test classe per classe quindi bisognerebbe prima sistemare quella. Ho quindi aggiornato le priorità qui sotto


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




