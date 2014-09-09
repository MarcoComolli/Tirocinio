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

-------

Sì, l'idea mi sembra sensata, le strutture dati che hai detto secondo me vanno bene.
Per la memoria magari vediamo dopo quando riusciamo a far funzionare tutto. 

(cavolo, il mio ultimo commit  ha cancellato le righe che avevi scritto...le riscrivo..errore mio scusa)

Ho iniziato a buttar giù un po' di codice (lascia perdere le altre cose del commit che ho commentato dei println e spostato il global test nel package originale per fare un po' d'ordine). Ti spiego come funziona:

All'inizio del metodo oltre alla chiamata al metodo tracer() ho messo anche una chiamata al metodo recordPath() che ho creato in MyTracerClass. Praticamente questo metodo setta a true una variabile booleana che indica quando deve registrare un percorso e inizializza la lista che dovrà tenere traccia dei blocchi e l'identificativo del percorso.
Da questo momento ogni volta che entra nel tracer gli faccio aggiungere alla lista il blocco chiamato.
Alla fine prendo la lista, ne faccio una copia (perchè penso che altrimenti mi passerebbe un riferimento a quella statica che viene azzerata ogni volta..penso) e inserisco nell'hashmap di percorsi.
Il problema più grande che ho qui adesso è: come faccio a dirgli 'smettila di registrare il percorso?' cioè, arrivato a fine metodo dovrei inserire qualcosa che gli dica 'setta a false la variabile recordPath' però al momento non saprei come fare. O meglio si potrebbe mettere a fine di ogni metodo una chiamata tipo setFalse() però questo richiederebbe di ritoccare ancora il codice...rischioso.. se hai altre idee fammi sapere

--------
Ho provato a modifcare il codice per aggiungere alla fine quel metodo che ti dicevo. Sono riuscito a farlo ma ora ho un problema: questo mi mette le chiamate al metodo prima dell'ultima parentesi del metodo. Stupidamente ho dimenticato di considerare che se c'è un return il metodo non può essere eseguito. Quindi devo sistemare ancora questa cosa. Se vuoi dare un'occhiata nel frattempo committo quello che ho fatto. Oggi pomeriggio penso a come risolvere la cosa del return.

Tirocinio
=========

parsing logical boolean expressions java --> stringa di ricerca


- per ogni esecuzione di metodo tenere traccia dei vari cammini (1)
	- modificare il mytracer (inizia a registrare quando trovi una chiamata con codice -1)

- copertura dei casi di test (2)
	- alla fine di ogni classe di test sapere quanto si è coperto del totale
	- giocare con le statistiche

- condition coverage (3)
	- cercare parser espressioni
	- inserire istruzioni prima dei blocchi if per sapere il valore della condizione
	- tenere traccia delle condizioni

- copertura delle istruzioni interne ai blocchi (4)
	- cercare un tool che tenga conto del numero istruzione
	- contare i punti e virgola in caso negativo
	- sapere quante istruzioni per blocco e tenerne traccia

- interfaccia grafica (5)



