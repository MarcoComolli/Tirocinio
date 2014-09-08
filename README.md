Annotazioni
=========
Ho cercato un po' quella roba del parser delle espressioni booleane. Non ho trovato molto a parte questo che però non ho ancora provato:
http://www.hephy.at/project/cms/trigger/globalTrigger/setup_software/javadoc/gt/gtgui/BooleanExpressionParser.html#getExpressionOperands%28%29
potrebbe essere interessante il metodo getExpressionOperands().
In alternativa ho pensato un attimo a quello che aveva detto il prof sul prendere i token e ho fatto una piccola classe che estrae gli operatori booleani dalla riga del codice sorgente.
Non funziona ancora per i for però potrebbe essere uno spunto. 


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



