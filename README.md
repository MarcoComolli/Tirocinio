Annotazioni
=========

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



