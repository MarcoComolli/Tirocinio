Annotazioni
=========


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

-----

Sto facendo andare la classe StatisticDataOrderer da circa 4 ore, ma l'esecuzione non si è ancora arrestata, faccio andare tutta notte e poi vedo...
Alla fine il file CondizioniCoperte è di 32 KB. Credo ci abbia messo un po' più di 4 ore per terminare.
In tutto, tra test ed elaborazione per altri file, credo siano passate un po' più di 6 ore.

----

Decisamente troppo direi. Ci sarà qualche errore. Io ho sistemato quello che dovevo e fatto partire tutto: 
- Datistatistici ora ha poco più di 10000 righe e 1MB di dimensione
- FilePercorsi 23MB
- NumeroIstruzioni ha sempre 3300 righe. Quindi mi sa che non era quello l'errore
- GlobalData ora mi dice che c'è l'11% coperto e il 91% non coperto (mistero)
- Devo sistemare un errore su numeroIstruzioniperMetodo che mi da tutto a 0
- CondizioniCoperte 35kb e 336 righe

------

Scrivo ancora 2 cose sui miei file perché visto il tempo che ci vuole per ottenerli, mi sa che li tengo buoni per un po' e me li salvo da qualche parte (I miei file sono stati ottenuti fino al mio ultimo commit e non comprendono le tue ultime modifiche).

I file più grossi sono:

- DatiStatistici 396 MB
- DatiStatisticiOrdinati 279 MB
- FilePercorsi 607 MB

Si vede subito che i file DatiStatistici e DatiStatisticiOrdinati non hanno la stessa dimensione anche se quest'ultimo dovrebbe solo riordinare le stringhe.
Dei rimanenti il più grande è Blocks con 124 KB.
Il i dati del file GlobalData mi sembrano plausibili visto il tempo impiegato per effettuare i test e visto il numero cumulativo dei blocchi testati.
La classe StatisticDataOrdere ci ha messo 4 ore e l'esecuzione più lunga è stata quella del metodo writeCoveredConditions() in cui bisognava esaminare riga per riga il file FilePercorsi ed inserire nell'HashMap i metodi non presenti ed effettuare alcuni calcoli. FilePercorsi nel mio caso è enorme e penso ci abbia messo un sacco per leggerlo e poi per effettuare il controllo nell'HashMap se fosse o no presente un metodo avente stesso nome. La scrittura del file CondizioniCoperte penso sia avvenuta molto velocemente.

-------

Ok, si ti conviene tenerli. Con le mie modifiche il file dei DatiStatistici viene più o meno lungo quanto il file Blocks. PErò ti conviene aspettare che tutto sia sistemato prima di rieffettuare i test. Comunque sembra che non fosse quello il problema sul NumerIstruzioni. E' ancora corto. Devo pensare a qualcosa e sistemare anche gli altri (e capire perchè 91%+11% fa 102%. devo capire anche da dove esce quel 2% in più di blocchi.
A me DatiStatistici e DatiStatistici ordinati sono grandi uguali.

------

Ho tolto un bel po' di sysout e ho scoperto che quei *null* che mi dava nei file al posto del nome del metodo è il responsabile dell'errore sulla % errata nel global test.  
Il fatto che ci siano i null deriva dal fatto che ASTParser non legge le classi dichiarate come enumerativi (enum al posto di class) e tutti i metodi/blocchi al suo interno vengono contati null perchè non sono presenti nel file "metodiTirocinio" che fa ASTParser. Provo a vedere se riesco a fargli parsare gli enum.

---------

Sto provando a far andare i test, ma il FilePercorsi ogni tanto mi si azzera. Prima mi sa che non lo faceva. 

-----

Cavolo ora controllo. Sul mio ho provato e non lo fa non so cosa possa essere

-----

Può essere qualche eccezione? Anche se non penso perché i package-info li ho eliminati

------ 
Non lo so. Ho ricontrollato e non mi sembra ci siano errori. Però ho visto che ci siamo dimenticati di far riconoscere i block finally e i syncronized. Non penso sia per quello però dovrebbe trovare qualche blocco in più (p.s. i blocchi synchronized sono difficili da prendere perchè possono essere anche synchronized i metodi quindi li prende solo quando i blocchi hanno una parentesi tonda subito dopo il "synchronized".)
Ma ti si resetta una sola volta o tante? Con che frenquenza più o meno? Può essere che fosse qualcosa tipo quell'altro file che mi faceva la stessa cosa a me?

Son riuscito a far partire quella classe di test base che non riusciva a partire. I file son tutti i giusti tranne il FileIstruzioni che mi conta 2 istruzioni in un solo blocco quando in realtà è una in 1 blocco e l'altra in un'altro. A parte questo c'è un problema. Il FilePercorsi ogni tanto non viene creato, forse è relativo al tuo problema. Ora provo a vedere cosa lo causa.

-------

Mi si resetta tante volte, forse per ogni test, quindi abbastanza frequentemente. I test stanno andando da 4 ore (sono ritornato da poco) e non sono terminati ancora. Adesso vedo qual è il problema anche se dal nome del test mi pare che non possa risolvere.
I test base io li avevo fatti partire da eclipse direttamente perché da interfaccia non capivo bene come farli partire.

Boh, non capisco vermente dove sia il problema e perché ce l'ho solo io, non c'è nessuna eccezione e il file si azzera casualmente. In più, prima rimaneva fisso al 49% ora l'ho fatto ripartire ed è al 59%....

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



