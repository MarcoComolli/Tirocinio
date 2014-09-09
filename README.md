Annotazioni
=========
Ho cercato un po' quella roba del parser delle espressioni booleane. 


Ho testato il metodo getExpressionOperands().
Il risultato non è nulla di che:
se gli dai in pasto questa stringa

"else if( (y -3) != 4 || y == 2 && alfa != 9)";

ti fa un array contenete le seguenti entries:

[else, if, y, -3, !=, 4, ||, y, ==, 2, &&, alfa, !=, 9]

in pratica mi sembra uno string.split(" ");
Non è quello che ci serve.



In alternativa ho pensato un attimo a quello che aveva detto il prof sul prendere i token e ho fatto una piccola classe che estrae gli operatori booleani dalla riga del codice sorgente.
Non funziona ancora per i for però potrebbe essere uno spunto. 

------------------

Per quanto riguarda il conteggio delle istruzioni questo non mi da molte speranze
http://stackoverflow.com/questions/12517133/how-to-count-number-of-instructions-in-code-path
e anche da altre parti dicono tutti che per contare il numero di istruzioni bisogna analizzare il bytecode.
Quindi può essere che ci convenga contare semplicemente i punti e virgola per sapere le istruzioni. Se trovi qualcosa di meglio fammi sapere

Per il conteggio delle istruzioni neanche io ho trovato niente, solo cose riguardanti il bytecode.

Di tools che fanno quello che cerchiamo probabilmente non ce ne sono.



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



