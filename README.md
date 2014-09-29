Annotazioni
=========

Ho "risolto" i problemi di classpath di pmd. Il riusltato è stata un'esecuzione dei casi di test durata 27 minuti con il filepercorsi di 1,21 GB. Gli altri files sono ok come dimensioni.
Ho poi fatto partire il statistics data orderer e calcolato il tempo necessario per ogni task con questi risultati:
> readAndOrderData 0.07  
> writeOrderedData 0.111  
> writeNumberOfLinesForMethod 0.222  
> writeCoveredConditions 22.38  

i tempi sono misurati in secondi.  
Manca il task finale **writePathsLength** perchè non finisce. Fa saltare questa eccezione  
```Exception in thread "AWT-EventQueue-0" java.lang.OutOfMemoryError: GC overhead limit exceeded```  
che fondamentalmente dice che ha problemi con il GarbageCollector perchè lo spazio in memoria allocato è troppo grande.

Ora salvo i dati e provo a far partire Junit e ti posto i risultati (se mai termina l'esecuzione)

--------

Sei riuscito a risolvere l'eccezione?

--------
No, credo che crei una hashmap/treemap/treeset o qualche struttura troppo grande in memoria visto il file di così grandi dimensioni. Poi guardo dopo aver provato anche con Junit. Il prof ha detto qualcos'altro sul vederci domani?

--------

Ha detto che iniziamo durante la pausa e poi finiamo subito dopo la sua lezione.

------

Ok grazie. Al momento ho un errore in preprocessing per Junit. (emptystackexception) sto cercando di risolvere.

Ho risolto l'errore sul preprocessing (a te non succedeva?) con qualche variabile booleana.  
Ho fatto partire le classi di test e ovviamente c'è qualche errore da qualche parte perchè finiscono in 8 secondi senza darmi alcuna eccezione e i risultati sono a dir poco scarsi (4% di copertura e 9 percorsi).  
Penso di sbagliare qualcosa nel mettere i path. Mi scriveresti quelli che usi tu così li confronto?
Questo è il globaldata che ottengo

> Total block code: 1831  
Total block code tested (cumulative): 714  
Total block code tested: 77  
Uncovered block: 1753  
Percentage test coverage: 4,2054%  
Percentage test uncovered: 95,74%  
>
Total number of path: 9  
Total number of path-block covered: 690  
Average path size: 76,6667  
>
Total time for testing 8.739999953657389 sec.  
Total number of test classes: 190  
Total number of tests: 2295  
Average tests for test class: 12,0789  
Average block covered by test class: 0,7947 (1,0321% of tested blocks and 0,0434% of total blocks)  


------

Non ho mai avuto eccezioni durante il preprocessing (a parte alcune cose in alcune classi che dovevo sistemare dopo aver fatto il preprocessing), magari l'ultima modifica che abbiamo fatto può averle generate.

I percorsi sono questi qui in ordine:


C:\Users\Jacopo\Desktop\junit-master  
C:\Users\Jacopo\Desktop\nn  


C:\Users\Jacopo\Desktop\files  
C:\Users\Jacopo\Desktop\junit-master\src\main\java  
C:\Users\Jacopo\Desktop\nn\src\main\java  


C:\Users\Jacopo\Desktop\nn\src\test\java  
C:\Users\Jacopo\Desktop\nn\target\test-classes  
C:\Users\Jacopo\Desktop\nn\target\classes  

------

Ok perfetto ora riprovo

Ma io non ho la cartella target...tu l'hai per caso compilato con maven o fatto qualcosa con maven? perchè i target sono generalmente di maven..o forse anche di ant.

-------

Possibile che lo abbia fatto all'inizio, ma non mi ricordo precisamente.

---------

Ok ho fatto compilare a maven. Ho anche io le classi target ecc. ma quando lo faccio partire i risultati sono gli stessi di prima: file piccoli e a quanto pare incompleti. Non so che farci

---------

Ho provato anch'io e mi dà la stessa cosa. Prima delle modifiche di questa settimana non terminava e ora termina subito, boh...
Non so cosa fare potrei cambiare programma, ma non so se il tutto funzionerebbe, inoltre anche su PMD ci sono problemi, quindi non so

Nella versione precedente andava tutto su PMD? Se così fosse potremmo ritornare indietro (non so come si fa di preciso) e non far presente al prof il casino. Ci mettimao d'accordo prima di incontrarlo? 

------

Il problema è che io PMD so come farlo andare come prima. Prima non leggeva tutto perchè mi dimenticavo di inserire una libreria di "jaxen" o qualcosa del genere. Quando ho iniziato a inserire le varie librerie (poi ci sono stati errori a catena quindi le ho inserite una ad una) ha iniziato a leggermi tutti i file di test e a fare i percorsi. Quindi anche con il rollback del lavoro se lascio le librerie dovrebbe fare lo stesso un bel bordello.

Ora sto guardando 2 cose: la prima è vedere se riesco a sistemare il metodo writepathlenght. Se riesco a capire dove crea la struttura dati enorme o se è veramente quello il problema allora si può vedere di fixare.  
La seconda cosa è il filePercorsi. Teoricamente so come renderlo grande diciamo la metà o comunque ridurne di molto le dimensione ma devo cambiare due cosine. Se per te va bene ci proverei.  
Per il prof va bene. Io credo di essere là una mezz'oretta prima della lezione perchè mia sorella inizia prima. Se vuoi ci possiamo sentire prima/durante la lezione oppure dimmi te.

--------

OK, allora vengo anch'io mezzoretta prima così ci mettimao d'accordo. Ci troviamo lì nella sede di Montegeneroso?

------
Io credo di essere lì mezz'ora prima ma non ne sono sicuro.  
Al massimo domani mattina ti mando un messaggio prima delle 8 e ti dico.  
Comunque le lezioni sono al morselli non è meglio se ci troviamo già lì?  
p.s. credo di aver trovato il modo per sistemare un po' la faccenda. Posso procedere o preferisci tenere così per evitrare magari altri problemi?

--------

Ah, ok scusa non avevo guardato, va bene al Morselli.
Non so, se vuoi vai pure avanti, al massimo si può tornare indietro.

-------

Ho quasi fatto però finisco domani pomeriggio e vedo come va, per il momento non diciamo niente al prof sull'errore dell Garbage collector. Ci sentiamo domani


TODO LIST
=========

####### Task principali (maggiore urgenza)

- [x] Trovare la giusta nomenclatura per i file sorgenti
- [x] Far fare all'interfaccia grafica il preprocessing
- [x] Risolvere errori per l'esecuzione test di JUnit
- [x] Gestire nel MyTracerClass i dati raccolti (tipo mostrarli in un formato leggibile o farci gli esperimenti statistici)

-------

####### Task secondari (non indispensabili)

- [ ] Gestire situazioni di errore interfaccia e non
- [x] Codice per contare le istruzioni all'interno dei metodi ma fuori dai blocchi
- [x] Codice per passare al myTracerClass il conteggio delle istruzioni
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

- [x] interfaccia grafica (4)

- [x] copertura dei casi di test (5)
	- [x] alla fine di ogni classe di test sapere quanto si è coperto del totale
	- [x] giocare con le statistiche



