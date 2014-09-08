Tirocinio
=========
parsing logical boolean expressions java --> stringa di ricerca

difficile
- condition coverage
	- cercare parser espressioni
	- inserire istruzioni prima dei blocchi if per sapere il valore della condizione
	- tenere traccia delle condizioni

normale
- per ogni esecuzione di metodo tenere traccia dei vari cammini
	- modificare il mytracer (inizia a registrare quando trovi una chiamata con codice -1)


- copertura delle istruzioni interne ai blocchi
	- cercare un tool che tenga conto del numero istruzione
	- contare i punti e virgola in caso negativo
	- sapere quante istruzioni per blocco e tenerne traccia

- copertura dei casi di test
	- alla fine di ogni classe di test sapere quanto si è coperto del totale
	- giocare con le statistiche

- interfaccia grafica
