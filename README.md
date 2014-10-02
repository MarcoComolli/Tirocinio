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

Stavo scrivendo in segreteria ma non ricordo più se il modulo che abbiamo compilato era questo o no (per dargli un riferimento corretto)
http://ws1-va.ateneo.uninsubria.it/uninsubria/allegati/pagine/14035/Richiesta_stage_interno_itinerante.pdf
ricordi quando l'abbiamo consegnato?

--------

Sì, il modulo era quello.

-------

Ho riscritto il tutto...con i file di prova funziona..ma indovina un po'...pmd si interrompe con un'eccezione..sui test però neanche nel statistic data orderer...e credo di aver capito perchè ha così poco test pmd. In realtà hanno messo un po' di file esterni (tipo txt o formati che sanno solo loro) da provare che ovviamente non vengono letti dai test. Quindi non leggendeli i test non fanno niente e non testano niente.  
Non so più che fare oramai mi sono proprio rotto...problemi ovunque...mai una cosa che va per il verso giusto..

-------

Ora secondo me bisogna incominciare a scrivere e tener buoni dei dati, se pensi che si debba tornare indietro con le versioni, facciamolo. Altrimenti non riusciamo più ad andare avanti.
Non si può più continuare con il codice, al massimo gli si può dire al prof che su alcune cose non siamo sicuri che tutto vada per il verso giusto, per il resto credo non si possa più continuare con le modifiche.

------------

Eh ma se i dati sono totalmente sbagliati cosa scriviamo nella tesi? Che pmd è stato testato all'11% quando in realtà magari è oltre il 50%? Poi ora il test dà problemi di sè...cioè i test danno un'eccezione non più il codice che abbiamo fatto noi. Più aggiungo classi e jar che mi richiede nel classpath e più fa casini.  
Ora sto provando a commentare i test che impallano l'esecuzione ma sono tanti e tutti uguali. Praticamente non hanno test dentro ma un solo metodo setUp() che è composto da tanti addRule(.. , ..) in sequenza. Se riesco a far andare il metodo addRule dovrei in teoria risolvere qualcosa però ogni addRule richiede come paramentro un file .xml che non trova nel classpath e va all'infinito. Ora ho terminato io l'esecuzione perchè il filepercorsi arrivava oltre i due giga e mezzo. Sto provando ad aggiungere un po' di tutto al classpath vedere se risolve.

--------

Lo so, ma ora abbiamo detto al prof che funziona e che iniziavamo a scrivere la tesi, quindi o gli diciamo tutto o incominciamo al più presto a scrivere se no non ci prende neanche più sul serio.

---------

Se gli diciamo dei problemi che abbiamo è facile che non ci fa neanche laureare quest'anno. Il problema è che funziona se il programma testato fosse un normale programma come ad esempio i nostri. Ma se tipo pmd fa in modo che tutti i casi di test provino su file xml e i file xml non li legge allora è normale che non testi quasi nulla.  
Comunque io sto scrivendo la tesi (ho iniziato con la prima pagina di intestazione) e intanto sto provando questo. Se riesco a risolvere tanto di guadagnato. Sennò usiamo i dati fittizi.

####NOVITA'!
Credo di aver capito perchè pmd non finisce/testa poco. Ti spiego tutti i passaggi che ho fatto così magari si capisce meglio:  
- Ho preso in considerazione 1 classe di test di quelle che davano problemi (quelle che prendevano gli xml e testavano) e ho provato a farla andare con Junit da eclipse dal progetto originale non modificato e andava tutto liscio. Eseguiva 70 casi di test senza nessun fail.
- L'ho fatta partire sul progetto modificato sempre con lo stesso metodo e invece si impiantava
- Allora ho fatto in modo di fargli stampare prima dell'esecuzione il classpath che usava nel progetto originario e ho copiato tutte le classi nel classpath nuovo. Ho fatto ripartire ma l'errore era lo stesso. Non è quindi un problema di classpath.
- Dopo un po' di esperimenti ho provato a commentare tutti i metodi della classe MyTracerClass in modo tale che quella classe non facesse nulla. Ho fatto partire e tutto ha funzionato (ovviamente senza dati)
- Allora ho scommentato uno alla volta i metodi del MyTracerClass fino ad arrivare al succo del problema.
- I metodi che fanno casino alla fine di tutto sono i metodi writePathsFile() sia quello normale che quello col booleano. Lasciandoli commentati, e con solo 1 classe di test i dati che mi venivano fuori erano abbastanza sorprendenti: 70 casi di test eseguiti, 10,9% blocchi testati del totale, i blocchi coperti sono 1'850'000 circa su quasi 2400 percorsi. Il tutto testato in poco più di 2 secondi.
- Ho provato a lasciar andare l'esecuzione con i metodi che scrivono sul file percorsi. L'esecuzione è terminata dopo 4 minuti con un file con 1'850'000 righe (giustamente) e di dimensione 158 Mb. Tutto questo per una sola classe di test.

Il problema quindi è che la scrittura su stream oltre a diventare una cosa enorme (io fermavo a 2,5GB ma probabilmente se lasciavo andare (non so quanto) avrebbe terminato con un file di molto più grande) è lentissima rispetto all'esecuzione. Quindi anche i tempi si allungano eccessivamente. Il problema ora diventa quindi come evitare il filePercorsi perchè comunque tutti gli altri dati riesco a ottenerli anche senza questo (da questo file dipenderebbero le condizioni coperte e il file lunghezza cammini).  

***possibile soluzione:***
Ogni volta che venivano chiamati i metodi inizializzava un printwriter e poi lo chiudeva. Gliel'ho fatto fare una sola volta (inizializzandolo in un blocco *static*) e chiudendolo alla fine dei test. Il tempo di esecuzione è passato da 4 minuti a 10 secondi...la grandezza è ovviamente rimasta invariata.  
Per la grandezza si potrebbe assegnare ad ogni blocco un codice numerico. Essendo i blocchi al max 10000 i caratteri scritti sarebbero al massimo 5 per ogni metodo invece che 50-80 riducendo di più di 10 volte la dimensione. I nomi dei blocchi verrebbero poi mappati in una mappa. Che ne pensi? Così tipo possiamo raccogliere le info e poi eliminare il filePercorsi che invece di 4 giga sarebbe 400 mega tipo...mi sembra un buon improovement

Altra cosa, sto cercando dei progetti alternativi a Junit ma tutti quelli che guardo o non c'è il codice o quando c'è mancano i test) sei riuscito a trovare qualcosa tu?

---------

Io ho trovato programmi con codice e test, ma tutti hanno problemi di compilazione quando vengono importati con eclipse. In pratica faccio il build con ant o maven e vengono scaricate tutte le librerie, ma quando li importo su eclipse i problemi con le librerie rimangono.

Questi tre programmi avevano test e sorgenti: dependometer-java-1.2.5, Jalopy2, jfreechart-1.0.19, ma hanno un bel po' di problemi. Del primo eseguo il build con maven e tutto è a posto, ma importandolo su eclipse ci sono problemi cpn dipendenze,  inoltre ha source folders sparse di test. Il secondo stessa cosa del primo anche se le cartelle dei test sembrerebbero più gestibili. L'ultimo eseguo il build di ant, ma con eclipse non me l'ho fa importare e non ha un pom di maven. Quindi ho provato a copiarlo nel wrkspace e a creare un progetto con lo stesso nome, ma le source folders me le rieva come normali cartelle.

Magari il problema dei metodi writePathsFile() era anche di JUnit, forse si potrebbe provare ad eseguire ancora i test in questo modo.

Per diminuire la grandezza dei file fai pure come vuoi tu.

---- 
Ahah adesso ti faccio ridere...esecuzione ferma al 91% e 26GB di filePercorsi, in aumento. Ho terminato e vedo se riesco a fare qualcosa tipo commentare qualche classe particolarmente ostica. Comunque con i dati presi fin'ora ho fatto un calcolo approssimativo e la % di test copre più del 30/40%. Ti informo per altri risultati.

Per quanto riguarda i programmi, per jfreechart basta inserire le cartelle nel buildpath e prenderle come source code. Per quello coi test sparsi direi di lasciar perdere che è troppo difficile arrivare a mettere dentro tutti i path giusti.  
Provo poi a scaricare e guardare il secondo e il terzo vedere se ache a me da gli stessi errori.

---------

Oddio 26 GB! La percentuale almeno è aumentata.

Per jfreechart-1.0.19 ho provato a fare il build con maven ma fallisce, poi lo importo con eclipse e ha giustamente problemi di dipendenze. Ho provato anche ad aggiungere le cartelle al path, ma l'opzione dal menu non era disponibile.

Ti metto i link se vuoi provare:

http://sourceforge.net/projects/jfreechart/files/

https://github.com/notzippy/JALOPY2-MAIN

http://source.valtech.com/display/dpm/Downloads



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



