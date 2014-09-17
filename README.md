Annotazioni
=========


Se vuoi fai pure il commit così lo vedo anch'io.
Intanto qualcuno risponde così su stackoverflow:
Seeems I'm having some problem: Which problem? –  JB Nizet 4 mins ago

-----
Praticamente ora tutti i file vengono salvati con il loro FQN (Fully qualified name) ma per fare questo ho bisogno di fare come ti avevo detto: specificare il path originale del package più esterno così da poterlo sottrarre al path completo del file.  
Quindi nel ParenthesysAdder va specificato nel main  
```String rootPath = "C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java"; ```  
Questo è praticamente dove iniziano i miei package di pmd. Questo path verrò poi chiesto di essere specificato dall'utente nell'interfaccia (se non capisci qualcosa nell'interfaccia prova a vedere il tooltip sulle Label).
Se hai qualche problema fammi sapere.

-----

Testando mi sembra di avere il tuo stesso problema ClassNotFoundException. Non so se ho capito tutti percorsi esatti da inserire nei campi dell'interfaccia (te li scrivo un attimo per vedere se ho capito).
- Source folder: dove sono presenti i file sorgente es: C:\Users\Jacopo\Desktop\junit-master\src
- Destination folder: la directory di destinazione della copia.
- Additionals file folder: la directory dove verranno salvati file addizionali.
- Packages folder: la prima cartella contenente packages-. es: C:\Users\Jacopo\Desktop\junit-master\src\main\java

- Test folder: dove si trova la directory contenente i test. es: C:\Users\Jacopo\Desktop\junit-master\src\test
- Compiled test folder: dove si trovano i .class dei file di test. es: C:\Users\Jacopo\Desktop\junit-master\bin\junit\tests

-----

Si esatto... i file addizionali sarebbero quelli tipo MetodiTirocinio.txt o NumeroIstruzioni.txt.  
*Packages folder* è quella fino al primo package. Si per junit è corretta quella perchè i packages iniziano tutti o con org.qualcosa.ecc oppure con junit.qualcosa.ecc
per sapere se è corretto dovresti provare a far partire tutto (non da interfaccia che il preprocessing non lo fa ancora) ma normalmente dal parenthesysAdder. Se i file come metodiTirocinio o NumeroIstruzioni sono corretti (ossia non hanno null al posto del nome del metodo) allora dovrebbe essere corretto.  
Test folder dovrebbe essere sbagliata. Nel mio Junit dovrebbe essere *C:\Users\Marco\Desktop\junit-master\junit-master\src\test\java* perchè tutti i packages iniziano con junit.blablabla o con org.blablabla quindi bisogna includere anche *\java*.  
Compiled test folder dovrebbe essere giusta.  
O almeno, non capisco quest'ultima ho provato con diversi livelli (nel tuo caso sarebbe da provare tipo arrivare fino \junit-master\bin\junit\ e provare poi fare fino \junit-master\bin\ e provare o andare anche più avanti) di path ma non capisco se si trova lì l'errore.  
Prova comunque a cambiare il test folder come ti ho detto. (se guardi il fullname dovrebbe essere corretto. Se non è corretto è sbagliata la "root" dei package: in questo caso quindi la *Test folder*).

-----
Mi hanno risposto su stackoverflow e credo di aver capito il problema. Ora riesco a far partire alcuni test!  
Ci sto lavorando. Il problema è come diceva lui che non trova le classi usate dai test non quelle dei test in sè. Ho aggiornato il Classpath e aggiunto un secondo bin con le classi dei file e riesce a eseguire qualcosa ma da ancora qualche eccezione che ora vedo di risolvere.

----
Boh sto provando...ho risolto un bel po' di problemi con sta cosa ma ora arriva ad un punto e si ferma. Non va avanti e non da eccezioni nè stackOverflowException niente di niente. Si impalla tutto e basta..sto provando a vedere cos'è ma si ferma sempre al solito punto...0 eccezioni mah.  
Vedremo.  
Ho da chiederti una cosa: puoi fare in modo che ogni volta che si fa ripartire tipo il parenthesysadder il file NumeroIstruzioni.txt venga ricreato da 0? Perchè continuandolo ad eseguire aggiunge sempre i dati a quello vecchio che lo fa diventare lunghissimo e con ripetizioni.  
Se non riesci non importa, piuttosto che fare casini meglio averlo così.

----

Cosa potrei fare io ora? Potrei iniziare a vedere come gestire i dati raccolti? Magari possiamo metterci d'accordo sui dati da raccogliere e sulla modalità di raggruppamento dei dati in modo tale da poter incominciare a farci qualcosa.

---------

Allora ti dico quello che al momento sto facendo io:  
prima dovrei risolvere la faccenda dell'esecuzione dei test che si blocca. Devo arrivare a far funzionare tutti i test in modo corretto.  
Fatto questo mi dedico al far partire il preprocessing da interfaccia grafica (che avevo già inziato) e al far fare all'interfaccia una cosuccia che se mi viene dà un bell'effetto. (praticamente siccome per i test è necessario specificare tutti i path delle classi o delle librerie esterne usate volevo far apparire dinamicamente a seconda della scelta dell'utente un numero arbitrario di caselle di testo in cui inserire i path specificati. Poi questi saranno gli argomenti dell'URLClassloader).  
Al momento mi sto dedicando a questo.  
Sì, ci sarebbe da "elaborare" i dati raccolti fino ad adesso. Fin'ora abbiamo: i path di esecuzione, i vari blocchi, il numero di volte che un metodo viene eseguito e il numero di istruzioni per blocco.  
Al momento l'unica cosa che facciamo è farli stampare a video ma bisognerebbe farci qualcos'altro. Per esempio io pensavo di creare un file html in cui i risultati sono formattati in una tabella e suddivisi tipo per classe. (poi questo file si potrà far vedere anche da interfaccia grafica volendo).
Oppure ci sono per l'appunto da fare un po' di calcoli che aveva richiesto il prof. Tipo la storia della casualità, del procedere e tutte le altre cose che ora non mi vengono in mente.
Infine dobbiamo considerare che manca ancora l'ultimo punto (il 5) cioè dobbiamo ancora raccogliere quanta copertura ha una classe di test (questo però va fatto dentro al ciclo for dell'interfaccia. Cioè prima dell'istruzione ```Result result = junit.run(cl);```  andrebbe messo un metodo che dica di iniziare a registrare la copertura e dopo averlo eseguito uno che dica di finire di registrare) Però non mi sembra il caso di iniziare a verificare la copertura dei test se non riusciamo/riesco ancora a farli partire tutti in modo decente.  
Dimmi tu cosa preferisci fare.

Domani vuoi che scriviamo una mail al prof che gli diciamo come stanno andando le cose?  
Altra domanda: tu giovedì vai a vedere le lauree?

---------

Per ora sul file viene scritto quello che veniva stampato a video.
Stavo pensando a come ordinare i dati per classi, i dati vengono stampati a seconda di dove vengono eseguiti i test e per ordinare il file, si potrebbe fare un altro file che viene ordinato ogni volta al momento dell'esecuzione del metodo tracer oltre a quello attuale.  
Altrimenti bisognerebbe analizzare il file con i dati in un'altra esecuzione.
Per i dati che si potrebbe raccogliere sono: 
- quanti metodi e quali metodi vengono eseguiti di una classe, 
- quante istruzioni di un metodo vengono eseguite durante un cammino, 
- quali blocchi e quanti blocchi vengono eseguiti di un metodo  

Poi bisogna ancora analizzare l'array di booleani.
La mail si può scrivere, poi ci sentiamo per metterla giù.
Giovedì penso di non esserci, mi mette un po' ansia vedere le lauree e poi mi fa anche un pochino arrabbiare per come è andata.

------
Ok. Per la mail magari ci sentiamo oggi pomeriggio?  
Ah ok, no anche a me gira un po' per le lauree però era giusto per vedere come ci si comportava. Però non penso di esserci boh vedrò giovedì.  
Sembra che abbia risolto la faccenda del test. In realtà non si impallava tutto ma quando andava ad eseguire una classe particolare dei test di pmd (e ora però mi fanno anche girare un po' i cosiddetti quelli di pmd) per come è scritta questa classe devia tutto l'output in una cartella del progetto che crea e che mi fa un file txt di più di 9 Megabyte. Una volta rimossa quella classe di test tutto funziona. Però non so come gestirla altrimenti. Se dovessi lasciarla questa fa delle robe con questo codice:
```Java 
public class CLITest {
	private static final String TEST_OUPUT_DIRECTORY = "target/cli-tests/";

	// Points toward a folder without any source files, to avoid actually PMD
	// and slowing down tests
	private static final String SOURCE_FOLDER = "src/main/resources";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty(PMDCommandLineInterface.NO_EXIT_AFTER_RUN, "true");
		File testOuputDir = new File(TEST_OUPUT_DIRECTORY);
		if (!testOuputDir.exists()) {
			assertTrue("failed to create output directory for test:"
					+ testOuputDir.getAbsolutePath(), testOuputDir.mkdirs());
		}
	}

	private void createTestOutputFile(String filename) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(filename));
			System.setOut(out);
			System.setErr(out);
		} catch (FileNotFoundException e) {
			fail("Can't create file " + filename + " for test.");
		}
	}
```

Per il momento la tolgo. Ho quasi finito quella cosa che ti dicevo dell'interfaccia. Ora mi manca da far partire il preprocessing da interfaccia con tutti i dati specificati.
Per quanto riguarda quello che hai detto secondo me far partire un'altra esecuzione che scandisca il file finale e lo "riordini" è molto meglio e si può fare facilmente (basta aggiungere uno step all'interfaccia con un pulsante ed è fatta) rispetto al riordinare ogni volta che viene chiamato il tracer che la cosa si farebbe piuttosto pesante penso.  
Altra cosa che mi viene in mente da fare poi in seguito è gestire le situazioni in cui l'utente sbaglia a inserire qualcosa (tipo path scorretti o directory non esistenti) mostrando gli errori e catturando con eccezioni nel codice.  
Però è secondario, prima cerchiamo di ottenere tutto con i dati giusti.

------

Ci ho messo più del previsto (ovviamente problemi su problemi) però ora sembra che funzioni. Spero non incasini tutto ora faccio il commit.

------

Sono riuscito a far andare la copia e il preprocessing.

------
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


TODO LIST
=========

####### Task principali (maggiore urgenza)

- [x] Trovare la giusta nomenclatura per i file sorgenti
- [ ] Far fare all'interfaccia grafica il preprocessing
- [ ] Risolvere errori per l'esecuzione test di JUnit
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




