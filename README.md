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

Ho fatto partire tutto ma ci sono delle cose che non mi quadrano (magari è colpa di pmd per come è fatto):
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




