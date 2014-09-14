Annotazioni
=========

In pratica bisogna riuscire dal path del file a ottenere la nomenclatura della classe (in pratica bisogna riuscire a tirare fuori il risultato del metodo getClass() di un oggetto avendo solo il path del file del file sorgente di quell'oggetto). Server assolutamente per far partire i test (e secondariamente per risolvere un problemino con la nomenclatura dei file per pmd).  
In parole povere se ho ad esempio questo percorso: 

```C:\Users\Marco\Desktop\pmd-src-5.1.1\src\main\java\net\sourceforge\pmd\lang\java\typeresolution\rules\imports\UnusedImports.java ```

con questo path:

```/pmd/src/main/java/net/sourceforge/pmd/lang/java/typeresolution/rules/imports/UnusedImports.java ```

dovrei ottenere una stringa di questo tipo (l'ho presa dal metodo getClass()) 

```net.sourceforge.pmd.lang.java.typeresolution.rules.imports.UnusedImports ```

che corrisponde all'identificatore univoco della classe.
Parsare il path e sostutire i punti non penso sia un'opzione perchè il percorso/path varia ogni volta in base ai package e a dove è messo.

-----

Per ora non ho trovato niente, se il parsing non è fattibile e non c'è nessun metodo che fa questa cosa non saprei come fare. Forse bisogna cercare qualche altro metodo per far partire le classi di test per Junit.

------
Io forse ho trovato qualcosa. Devo testare per vedere se funziona. E ti faccio sapere.  
Se riesci a trovare un altro modo più facile per far andare i test di junit ben venga. Neanche a me piace tanto come funziona JunitCore

------
Mah no mi servirebbe una classe che non so dove trovare (il riferimento che stavo guardando è questo:  
http://stackoverflow.com/questions/3845823/getting-list-of-fully-qualified-names-from-a-simple-name)  
La classe sarebbe ClasspathPackageProvider che non ho capito dove pescarla. 
Tuttavia ho letto che il fully qualified name della classe, cioè quello che ci serve, è comunque sempre deducibile dal path. Il vero problema rimane quindi come capire quali sono package e quali sono cartelle nel path della classe. Una volta capito quello il gioco è fatto e basterebbe parsare semplicemente il path con solo i package.  
O in alternativa si potrebbe trovare qualcosa di alternativo a JunitCore ma non saprei dove sbattere la testa per questo.

Per chiarezza ho tolto tutta la parte iniziale del readMe ma siccome potrebbe essere comunque del materiale utile (magari possiamo attingere qualcosa o qualche fonte per la tesi) ho salvato tutto e l'ho messo in un file chiamato Annotazioni.txt all'interno del progetto. Se ti serve prendere/rivedere qualcosa è salvato tutto lì.

Idea! E' un po' brutto ma mi sembra l'unica soluzione al momento:  
ho guardato qua e là ma mi sembra che JunitCore sia l'unica classe in grado di far andare i test di Junit...comunque l'idea è di far specificare all'utente dove si trova il package generale dei source e poi della classe test.  
Quindi si ha così la prima parte di ogni path che andrà rimossa. Nella restante parte si sostituiscono i / con i . e il gioco è fatto. Il problema è che l'utente deve fare un po' più di cose tutto qui.
Sto cambiando l'interfaccia per permettere questo.

------

Ho inserito nella classe MyTracer la struttura dato che contiene il numero di istruzioni e ora ogni volta che viene eseguito il metodo tracer, viene anche stampato il numero di istruzioni nel blocco. Per ora se il metodo tracer viene eseguito con parametro metodo-idBlocco non presente nella struttura dati, il numero di istruzioni l'ho messo convenzionalmente a -1. Magari, se ci servirà, più avanti si potranno effettuare calcoli statistici sul numero di istruzioni anche sulla base del metodo (al posto del valore convenzionale -1).
Io ho modificato solo La classe MyTracerClass, quando vuoi dimmi se posso effettuare il commit, altrimenti lo farò dopo il tuo aggiornamento.

------

Ok. Nono committa pure..Io non sto lavorando sul MyTracerClass quindi non dovrebbero neanche esserci conflitti di merge. 

Problemi con il classLoader. Ora i nomi riesco a prenderli giusti ma sembra che bisogna avere un classLoader che carichi le classi dei test altrimenti JunitCore non li fa partire ..ci sto lavorando (poi però bisognerà specificare anche nel parenthesysAdder un nuovo path perchè tutti i nomi non saranno più packageesterno/packageinterno/classe ma sarà tutto l'elenco dei package: pack1.pack2.pack3.classe).
Vedo cosa si può fare per il ClassLoader

Ho trovato una classe che si chiama URLClassLoader in cui si può specificare un URL dove il nuovo loader andrà a pescare jar e directory però non funziona e non capisco perchè. Qualsiasi cosa gli passo nel costruttore lui con il metodo getResource() mi fa vedere che va sempre nella cartella bin di eclipse del progetto corrente (cioè quello del tirocinio) e ovviamente non trova le classi di test di PMD. Non so che fare. Non capisco se sbaglio io o se URLClassLoader è la classe sbagliata per lo scopo. Ora continuo a provare qualcosa. Se rimango senza idee faccio una domanda su StackOverflow perchè non so come andare avanti.

------
Sto testando tutto anche su Junit e sembra che funzioni, il numero di istruzioni mi sembra abbastanza impreciso, però almeno non dà probremi.

------
Credo di stare diventando matto.  
Scusami ti faccio una domanda banalissima da novellino java perchè sembra che il mio problema sia qui.  
Ho un file .java, una classe semplicissima che stampa il nome della classe. L'ho messa in questa directory:
```C:/java/test/sec/ ``` e l'ho chiamata **Test.java**. Ora, tramite linea di comando vado in quella directory e faccio ```javac Test.java ``` e mi compila il file. Mi crea il **Test.class**. Poi quando lo dovevo eseguire ho fatto ```java Test ``` sempre da dentro la directory e mi dice che non trova il file.  
Allora mi sposto su di una directory e provo ```java sec/Test ``` essendo in ```C:/java/test/ ``` e ancora niente e faccio così fino in ad arrivare in C ma niente. Come diavolo la eseguo da riga di comando?
Questa è la classe:  

``` Java
package test.sec; 
public class Test {

	public Test() { 
	} 
 
	public static void main(String[] args){
		System.out.println(new Test().getClass());
	}
}
```
E' un problema del mio pc o sono io che sono rimbambito?

------
Dopo un po' di tempo ci sono riuscito in questo modo(ho usato le tue stesse impostazioni di directory e il file che mi hai postato):
- ho compilato la classe come hai fatto tu.
- Poi sono andato nella directory java con il comando(cd \java) e qui ho eseguito il comando  ```java test.sec.Test ``` quindi mi ha stampato "class test.sec.Test".


TODO LIST
=========

####### Task principali (maggiore urgenza)

- [x] Trovare la giusta nomenclatura per i file sorgenti
- [ ] Gestire nel MyTracerClass i dati raccolti (tipo mostrarli in un formato leggibile o farci gli esperimenti statistici)

-------

####### Task secondari (non indispensabili)

- [ ] Testare codice per il conteggio delle istruzioni
- [ ] Codice per contare le istruzioni all'interno dei metodi ma fuori dai blocchi
- [ ] Codice per passare al myTracerClass il conteggio delle istruzioni
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




