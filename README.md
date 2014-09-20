Annotazioni
=========

Ok ora faccio un elenco dettagliato con cosa bisogna inserire nelle form:

- Source folder: la cartella da copiare. Quella che contiene tutto il progetto/programma. es. *C:\Users\Marco\Desktop\pmd-src-5.1.3\*
- Destination folder: cartella dove verrà ricopiato tutto il progetto/programma. es *C:\Users\Marco\Desktop\nn\*
- Additional file folder: cartella dove verranno salvati i file intermedi come MetodiTirocinio.txt, NumeroIstruzioni.txt (e spero di riuscire a metterci anche tutte le analisi statistiche). es. *C:\Users\Marco\Desktop\files*
- Original package folder: cartella "root" dove iniziano i package (nel progetto originario). es. *C:\Users\Marco\Desktop\files*
- Destination packages folder: come quella sopra solo che del progetto che è stato copiato. es. *C:\Users\Marco\Desktop\nn\src\main\java*
- Test folder: dove sono presenti i sorgenti dei test .java nel progetto copiato (sempre nella cartella più in alto possibile diciamo) es. *C:\Users\Marco\Desktop\nn\src\test\java*
- Compiled test folder: dove sono presenti i .class dei test es *C:\Users\Marco\Desktop\nn\target\test-classes*
- Compiled source code folder: dove sono presenti i .class dei sorgenti es. *C:\Users\Marco\Desktop\nn\target\classes*
- Additional folder or jars: qui premendo sul [+] si aggiungono campi extra dove bisogna inserire eventuali dipendenze da librerie esterne es. *C:\Users\Marco\.m2\repository\org\apache\ant\ant-testutil\1.7.1\ant-testutil-1.7.1.jar*

Ho una domanda: potresti spiegarmi in breve le dipendenze dei percorsi tra MyTracerClass e StatisticsDataOrder? Nel senso: siccome vorrei far mettere tutti i file nuovi che hai aggiunto tipo DatiStatistici.txt o FilePercorsi.txt in una cartella specificata volevo sapere dove dovevo cambiare i path e come interagivano tra loro (tipo: viene letto in questa classe due volte e viene invece scritto in questa con un buffered reader) così poi posso provare a metterli.

------
- Il file DatiStatistici viene scritto nella classe MyTracerClass e viene letto nella classe StatisticsDataOrderer per scrivere i dati in ordine alfabetico nel file DatiStatisticiOrdinati.
- Il file DatiStatisticiOrdinati viene scritto nella classe StatisticsDataOrderer.
- Il file NumeroIstruzioni viene scritto nella classe MyTracerClass e mostra il numero di istruzioni totali in un metodo.
- Il file NumeroIstruzioniTestatePerMetodo viene scritto nella classe StatisticsDataOrderer e contiene il numero di istruzioni eseguite in un metodo durante tutti i test.
- Il file FilePercorsi viene scritto nella classe MyTracerClass e viene letto nella classe StatisticsDataOrderer per scrivere il file CondizioniNonCoperte che dovrebbe contenere metodo-blocco e condizioni non coperte.
- Il file CondizioniNonCoperte viene scritto nella classe StatisticsDataOrderer.

-----

Ok perfetto grazie ora provo a vedere di fare qualcosa

-----

Ti dico un attimo come sono compisto i file FilePercorsi e CondizioniNonCoperte perché secondo me devo sistemare qualcosa.
Nel file FilePercorsi ogni riga è così composta:
- numero percorso;
- identificativo metodo-identificativo blocco
- valori booleani presenti nell'array passato al metodo tracer.
(Es.: percorso 1: org.junit.internal.requests.ClassRequest getRunner,Runner,; @1-true).

Se il matodo tracer viene invocato senza un array come parametro, non verrà scritta nessuna condizione dopo l'dentificativo del blocco.

Nel file CondizioniNonCoperte ogni riga è così composta:
- identificativo metodo-identificativo blocco
- condizioni non valutate durante l'esecuzione dei test per il corrispettivo metodo-blocco.
(Es.: junit.framework.Assert assertEquals,void,;String Object Object  @2 non valutate : false true;false false;true false;).

Per ora in questo file per ogni metodo-blocco risulta comparire la condizione false o false false o false false false... come non valutata perché esso viene scritto leggendo dal file FilePercorsi quali condizioni erano presenti nell'array.
Le condizioni false, false false.. (a seconda di quanti booleani prendono i controlli condizionali per entrare nel blocco) vorrebbero dire che il test non è entrato nel blocco e quindi non lo ha testato. Stavo pensando ad un modo per togliere questo tipo di condizioni dal file se i test non sono entrati nel blocco. 
Forse bisognerebbe controllare per ogni percorso del file FilePercorsi se durante l'esecuzione dal blocco iniziale a quello finale non è presente la stringa avente identificativo metodo-blocco o forse abbiamo già qualcosa di pronto.

Per far andare i test tramite interfaccia per pmd, dove hai rintracciato la posizione dei jars necessari mancanti?

-----
Hai ragione sarebbe un problema. Però teoricamente se in effetti le condizioni erano false-false per esempio e non è entrato nel blocco allora vuol dire che comunque lui le ha testate. Forse dovremmo fare in modo di mettere la cosa dei booleani prima dell'esecuzione del blocco.  
Però pensandoci meglio mi sembra abbastanza infattibile perchè il file procede riga per riga e non torna mai indietro quindi nel caso di espressioni su più righe saremmo fregati.
Però, se ci ragioniamo un attimo, è più o meno la stessa storia degli switch no? A noi serve sapere, quando entriamo nel blocco, le condizioni che sono verificate quando siamo al suo interno. Cioè con quali condizioni ci stiamo accedendo.  Quando non ci accediamo è ovvio che il predicato in sè è falso.  
Quindi secondo te possiamo lasciare così e far vedere le condizioni solo quando siamo dentro al metodo? Perchè altrimenti pensavo che in un predicato del genere  
```if(true && false){...}```  
il blocco non viene eseguito nonostante sia appunto sia true-false la condizione e non false-false. Quindi mi sa che invece di condizioni non testate è meglio mettere solo le condizioni testate per quel blocco. Cosa ne pensi?

Per quanto riguarda i jar li ho scoperti dalle eccezioni *NoClassDefFound*. Nel mio caso era solo una e sono andato nel build path di eclipse a vedere dove si trovava e ho messo quel percorso. 

-----
OK, ora il file CondizioniCoperte contiene metodi-blocchi con condizioni coperte, il file CondizioniNonCoperte non viene più scritto.
Ora ripasso un attimo per domani che ho un po' di ansia, se ci dovesse essere qualche problema con qualsiasi file(se scrivono dati sbagliati o altro), dimmelo pure perché per ora non ho ho effettuato l'esecuzione completa con tutti i test, ma con solo poche classi.

------
Son tornato poco fa, sisi non ti preoccupare ripassa pure ora guardo un po' i casi di test e vedo di fare qualcosa.  
Ho notato che c'è qualche problema con la storia dei path dei file che hai aggiunto da interfaccia. Se uno setta manualmente il percorso all'inizio del file va bene quindi per il momento bisogna farlo manualmente. Quando avremo finito tutto provvedo a sistemarlo.

Ho fatto partire tutto (tutti i casi di test di PMD e poi dopo ho fatto partire il StatistcDataOrderer) ma ci sono delle cose che non mi quadrano (magari è colpa di pmd per come è fatto):
- Nel NumeroIstruzioni.txt ci sono alcune righe che mi danno null come metodo (ho controllato in MetodiTirocinio e non ci sono quindi non penso derivi da lì l'errore) ti faccio un esempio di un pezzetto di file:  
```  
net.sourceforge.pmd.dcd.UsageNodeVisitor visit,Object,;MethodNode Object @10#1
net.sourceforge.pmd.dcd.UsageNodeVisitor visit,Object,;ClassNode Object @2#2
net.sourceforge.pmd.dcd.UsageNodeVisitor visit,Object,;ClassNode Object @4#2
null@22#1
null@12#1
null@1#1
null@2#1
null@20#1
```  
- **DatiStatistici** e **FilePercorsi** mi vengono file txt di 12MB l'uno è normale? (anche quelli ordinati ma è normale visto che sono delle "permutazioni" di quelli originali). Per **FilePercorsi.txt** non ho trovato stranezze mentre in **DatiStatisticiOrdinati** e quindi anche nel suo originale penso ci sia qualcosa di strano ti posto alcune righe:  
```  
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 1 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 10 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 100 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 1000 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 1001 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 1002 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 101 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 102 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 103 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 104 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 105 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 106 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 107 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 108 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 109 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 11 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 110 #i-1
net.sourceforge.pmd.AbstractPropertySource checkValidPropertyDescriptor,void,;PropertyDescriptor<?>  #c-1 @0 #v 111 #i-1
```  
e questo primo metodo va avanti ancora per un bel po' in questo modo. Dai un'occhio al parametro #v, è l'unico che cambia. Sono andato a vedere e #v dovrebbe essere il numero di volte che viene eseguito (quindi penso dovrebbe esserci una sola entry per quel metodo) invece qua mi sembra una specie di concatenazione di stringhe miste ad un incremento. E' giusto che faccia così?

- Ultima cosa è che ho aspettato un paio di minuti ma l'esecuzione del *StatistcsDataOrderer* non termina e vedo che il file **NumeroIstruzioniTestatePermetodo.txt** continua ad andare da 0kb a 23kb...poi torna a 0..poi va a 36kb..poi 0 ancora e poi di nuovo a 27kb. Quando interrompo manualmente l'esecuzione il file ha dentro una ventina di kilobyte di dati. Questo mi sembra abbastanza anomalo invece.
Fai pure con calma, dai un'occhiata più avanti e pensa all'esame di domani che questo non è urgente. Intanto provo a vedere qualcosa per il punto 5.

-----

I file DatiStatistici e DatiStatisticiOrdinati possono essere molto grossi perché sono i dati che venivano stampati a video originariamente durante l'esecuzione del metodo tracer. Le altre cose sono anomale e domani vedo di sistemarle, ma prima devo far funzionare i test via interfaccia per avere i file finali. Domani provo ad aggiustarli.

Sto eseguendo tutti i test da interfaccia, ma mi sa che il prolema non era dovuto a jars mancanti, ma forse a classi di test denominate in questo modo: package-info.java che contengono solo un commento e la dichiarazione del package (tipo package junit.samples;). 
Ho cancellato i file di questo tipo e l'esecuzione procede senza eccezioni. Il problema è che sto eseguendo i test da 20 minuti ed ora l'esecuzione è ferma al 33esimo package di test che contiene una classe chiamata FailOnTimeoutTest che contiene questo campo:

    private static final int DURATION_THAT_EXCEEDS_TIMEOUT = 60 * 60 * 1000; // 1
                                                                             // hour
non vorrei che il test durasse un'ora, ma può essere. Mancano ancora 9 package, ma in questo modo non posso testare i file che stavo costruendo quindi forse per ora elimino la classe e provo a sistemare i file eseguendo gli altri 9 package di test.

-----

Ok com'è andato l'esame?
ora faccio un commit. Ho aggiunto una copertura dei test, cioè il numero di blocchi testati da quella classe di test. L'ho messa nel file TestCoverage.txt. E cambiato una cosa:
nel file percorsi è sbagliato come viene caratterizzato un percorso. Un percorso è caratterizzato sì dal numero di esecuzione ma anche dal particolare metodo da cui parte l'esecuzione (ti faccio un esempio)  
Se due metodi A e B vengono chiamati essi fanno partire un percorso. Se supponiamo che il metodo A viene chiamato 3 volte alla terza volta la dicitura del percorso attuale sarà percorso 3: bla bla bla...ma se anche B viene chiamato 3 volte il percorso sarà percorso 3: ble ble ble. Quindi sarebbero percorsi chiamati uguali ma son diversi. Ho aggiunto in modo tale che sia percoso A3 o percorso B3. (ovviamente aggiungendo altre parti il file viene ancora decisamente più grande (tipo 20 MB a me).  
Ora committo.

Per il problema del timeout non so come aiutarti. A me con pmd funziona (più o meno) tutto e in un minutino me li fa tutti.  
Sto cercando di collezionare dati sui test e stavo pensando: alla fine dei conti ci serve sapere (oltre alle altre millemila cose) il totale dei blocchi che abbiamo nel codice, il totale dei metodi e quali metodi/blocchi sono testati.  
Allora ho pensato di usare il countMap.size() per sapere quanti blocchi ci sono nel codice ma poi se ci pensi nel countMap non ci sono tutti i metodi e i blocchi del codice. Infatti questa viene aggiornata solo quando viene chiamato il metodo tracer. E il metodo tracer viene chiamato dai test e solo quando questi vengono eseguiti o passano di là. Quindi in fin dei conti i file che abbiamo estrapolato non tengono conto di tutte le condizioni di tutti i blocchi ma solo quelli registrati dai test. Mi sbaglio? Allora ho pensato di inserire una lista con tutti i blocchi a partire dal preprocessing e non nella classe MyTracerClass. Per ora mi sto muovendo in questa direzione. Quindi bisogna stare attenti anche se gli altri file si riferiscono solo ai dati di test o ai dati globali del codice.  
p.s. forse ho capito perchè mi dava *null* su alcuni metodi/blocchi. Suppongo dipenda da PMD (o ma va toh che novità..) visto che ogni tanto mi da questa eccezione: 
```Java
net.sourceforge.pmd.RuleSetNotFoundException: Can't find resource 'null' for rule 'java-basic'.  Make sure the resource is a valid file or URL and is on the CLASSPATH. Here's the current classpath: F:\Documenti - Marco\... eccetra eccetra
```
Non so magari dipende da questo.

------

OK, ora i test scrivono, ma ad un certo ppunto si bloccano e non scrivono più sui file (da interfaccia il bottone Run Tests rimane premuto quindi non dovrebbe aver terminato l'esecuzione).
Stavo mettendo a posto il metodo per il file CondizioniCoperte che elaborando i dati del file FilePercorsi (4,4 MB) scrive un file di 5,78 KB (CondizioniCoperte.txt) non so quanto sia possibile, se vuoi faccio il commit così lo provi anche tu visto che i miei file di dati derivanti dai test dovrebbero essere incompleti.


------

C'è qualcosa che non va. Allora fammi fare un riepilogo completo così ho un quadro chiaro dei file e cosa contengono (e quanto ci aspettiamo siano grandi):  

- **MetodiTirocinio.txt** contiene l'elenco di tutti i metodi rilevati da ASTParser. E' scritto nella forma:  
 ```Nome metodo```  
Ci si aspetta che contenga una entry per ogni metodo. PMD ha quasi 6000 metodi (da metrics). Quello che rilevo dopo il test sono quasi 5300 righe. Viene scritto da ASTParser ogni volta che incontra la dichiarazione di un metodo (non tiene conto delle dichiarazioni di metodi interni ad altri metodi). *Dimensione: 457Kb*
- **Blocks.txt** contiene l'elenco di tutti i blocchi. E' scritto nella forma:  
 ```Nome metodo + @ + numero blocco```  
Ci si aspetta che contenga una entry per ogni blocco con il tracer dentro. PMD ha quasi 6000 metodi, contati 5300. Quello che rilevo dopo il test sono quasi 10000 righe. Con una media di 1,88 blocchi per ogni metodo potrebbe sarebbe fattibile. Viene scritto nel MyTracerClass ogni volta che viene inserito un tracer() nel codice. *Dimensione: 907Kb*
- **NumeroIstruzioni.txt** contiene il numero di istruzioni che finiscono con il ; per ogni blocco di codice. E' scritto nella forma:  
 ```Nome metodo + @ + numero blocco + # + numero di istruzioni```  
Ci si aspetta che contenga una entry per ogni blocco con il tracer dentro. Secondo il file precedente sono almeno 10000 blocchi. Quello che rilevo dopo il test sono poco meno di 3300 righe. Dovrebbero essere almeno tanti quanti i blocchi nel file Blocks se conta anche i blocchi con 0 istruzioni. Viene scritto nel FileParser ogni volta che finisce il preprocessing di un file. *Dimensione: 313Kb*
- **FilePercorsi.txt** contiene i blocchi percorsi per ogni cammino, dove un cammino è originato da ogni esecuzione di ogni metodo chiamato dai test. E' scritto nella forma:  
 ```"percorso" + nome metodo  che ha originato il percorso + * + n-esima volta che viene eseguito quel metodo + : + nome del metodo eseguito + @ + numero blocco + - + valore delle condizioni che hanno permesso l'entrata nel blocco (se presenti)```  
Indefinito il numero di percorsi, soprattutto se un metodo viene eseguito in un ciclo dando origine più percorsi uguali. Quello che rilevo dopo il test sono poco meno di 130'000 righe.Non si sa quanto dovrebbero essere. Viene scritto in 2 metodi nel MyTracerClass. *Dimensione: quasi 20Mb*
- **TestCoverage.txt** contiene il numero di blocchi di codice testati per ogni classe di test eseguita. E' scritto nella forma:  
 ```Nome classe + #c + numero di blocchi attraversati```  
Ci si aspetta che contenga una entry per ogni classe di test. Secondo metrics PMD ha 256 classi. Quello che rilevo dopo il test sono 229 righe. Viene scritto nel MyTracerClass ogni volta che finisce l'esecuzione di una classe di test da interfaccia. *Dimensione: 15kb*
- **FileStatistici.txt** contiene per ogni blocco il cui tracer viene eseguito,il tipo di blocco, il numero di istruzioni e il numero di volte che viene eseguito. E' scritto nella forma:  
 ```Nome metodo + #c + tipo di blocco + @ + numero blocco + #v + numetro di volte eseguito + #i+ numero di istruzioni```  
Ci si aspetta che contenga una entry per ogni blocco con il tracer dentro. Secondo i file precedenti sono circa 10000 blocchi. Quello che rilevo dopo il test sono poco meno di 135'000 righe. Dovrebbero essere tanti quanti i blocchi nel file Blocks. Viene scritto nel MyTracerClass ogni volta che viene eseguito il metodo tracer() dai test. *Dimensione: quasi 12Mb*
- **FileStatisticiOrdinati.txt** contiene ciò che è contenuto nel file precedente ma in ordine alfabetco. E' scritto nella forma:  
 ```Nome metodo + #c + tipo di blocco + @ + numero blocco + #v + numetro di volte eseguito + #i+ numero di istruzioni```  
Ci si aspetta che contenga una entry per ogni riga del file FileStatistici. Nel file precedente ho poco meno di 135'000 righe. Quello che rilevo dopo il test sono poco meno di 135'000 righe. Viene scritto nel StatisticDataOrderer. *Dimensione: quasi 12Mb*
- **NumeroIstruzioniTestatePerMetodo.txt** contiene per ogni metodo il cui tracer viene eseguito dai test il numero di istruzioni dei blocchi eseguiti. E' scritto nella forma:  
 ```Nome metodo + "numero: " + numero di istruzioni testate per quel metodo```  
Ci si aspetta che contenga una entry per ogni metodo testato. Secondo i file precedenti sono circa 5300 metodi. Quello che rilevo dopo il test sono poco più di 3600 righe. Dovrebbero essere al massimo 5300 righe. Viene scritto nel StatisticDataOrderer. *Dimensione: 62kb*
- **GlobalData.txt** contiene la percentuale e il numero dei blocchi testati e non rispetto al totale. E' scritto nella forma:  
```
Total block code: + numero blocchi totali
Total block code tested (cumulative): + numero di blocchi testati in totale dai test
Total block code tested: + numero di blocchi unici testati dai test
Uncovered block: + numero di blocchi mai testati dai test
% test coverage: + percentuale di copertura
% test uncovered: + percentuale di non copertura
```
Ci si aspetta che i dati siano verosimili. Viene scritto nel MyTracerClass alla fine dell'esecuzione dei test *Dimensione: 1kb*
- **CondizioniCoperte.txt** contiene l'elenco delle condizioni per entrare in alcuni blocchi, che sono state testate dai test. E' scritto nella forma:  
 ```Nome metodo + @ + numero blocco + " valutate: " + elenco condizioni valutate```  
Ci si aspetta che contenga una entry per ogni blocco che è stato testato che con il metodo tracer(...., booleanarray). Ci sono al massimo 10000 blocchi. Quello che rilevo dopo il test sono quasi 2300 righe. Dovrebbero essere al massimo tanti quanti i blocchi totali. Viene scritto nel StatisticDataOrderer elaborando il FilePercorsi. *Dimensione: 34Kb*.

Come vedi ci sono un bel po' di incongruenze. L'errore che mi è saltato all'occhio è sul FileStatistici. E' sbagliato dove viene scritto perchè ogni volta che viene eseguito un tracer fa una riga ma se il tracer di quel metodo viene eseguito tipo 10 volte questo fa 10 righe con "esecuzione 1, esecuzione 2, esecuzione 3... ecc" . Quindi bisogna farlo scrivere alla fine di tutto quando il numero di esecuzioni è stato stabilito.  
AstParser non legge tutti i metodi di Metrics però ce se ne può fare una ragione.  
Guarda anche il NumeroIstruzioni. Fa un terzo delle righe che dovrebbe fare da me. (anche se ho visto che c'è una condizione che mette solo le righe con + di 0 istruzioni ci sono righe con 0 istruzioni contate quindi non so se è giusto così o no).  
Un altro errore è sul NumeroIstruzioniTestatePerMetodo.txt non capisco perchè ma continua a fare quella stranezza che sembra impallarsi e ci mette una vita a scrivere (fa al solito 0kb...20kb...0kb...36kb ecc). L'ho lasciato andare un paio di minuti e poi ha finito con il file che ti ho detto. Non so ancora perchè.  
Per quanto riguarda invece il CondizioniCoperte potrebbe anche essere giusto se vedi il numero delle righe. Dipende da come sono i blocchi ma bisognerebbe testare con qualcosa di semplice.  
Se puoi leggi tutto quello che ho scritto sopra e correggi/edita pure quello che vedi di sbagliato perchè sui file che hai messo tu non sono sicuro di aver capito se quello che devono fare è proprio quello che ho scritto.

-------

Finalmente dopo 2 ore è finita l'esecuzione delle classi di test.
Il FilePercorsi occupa 607 MB il file DatiStatistici occupa 396 MB.

Ho dei dati sorprendenti:

- Total block code: 1636
- Total block code tested (cumulative): 3928427
- Total block code tested: 1402
- Uncovered block: 231
- % test coverage: 85.69682151589242
- % test uncovered: 14.119804400977994

--------
Ahah ma va? Allora può anche darsi che funziona il calcolo e che quelli di PMD abbiano fatto poco nulla di testing.  
Mi sembra comunque ragionevole che quelli di Junit facciano un lavoro fatto meglio sui test. 85% se è veramente così è buona buona.  
Certo che 2 ore di esecuzione di test mi sembrano belle toste. Ci sarà qualcosa veramente tipo sul timeout (magari prova a settarlo a 1 minuto invece che un'ora, giusto per curiosità XD).  
Mi sa che dovremo fare qualcosa per quei file..miseria son troppo grandi!
Quel lavoro lo faccio domani alla fine che oggi non ho più avuto tempo.

TODO LIST
=========

####### Task principali (maggiore urgenza)

- [x] Trovare la giusta nomenclatura per i file sorgenti
- [x] Far fare all'interfaccia grafica il preprocessing
- [x] Risolvere errori per l'esecuzione test di JUnit
- [ ] Gestire nel MyTracerClass i dati raccolti (tipo mostrarli in un formato leggibile o farci gli esperimenti statistici)

-------

####### Task secondari (non indispensabili)

- [ ] Gestire situazioni di errore interfaccia e non
- [ ] Codice per contare le istruzioni all'interno dei metodi ma fuori dai blocchi
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

- [ ] interfaccia grafica (4)

- [ ] copertura dei casi di test (5)
	- [ ] alla fine di ogni classe di test sapere quanto si è coperto del totale
	- [ ] giocare con le statistiche




