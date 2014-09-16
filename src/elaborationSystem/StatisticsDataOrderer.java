package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;

public class StatisticsDataOrderer {
	private static HashMap<String, Integer> totalInstructionsNumber = new HashMap<String, Integer>();
	private static Integer numberInstructions = 0;

	public static void main(String[] args) throws IOException {

		TreeSet<String> treeSet = readAndOrderData();

		writeOrderedData(treeSet);
		writeNumberOfTestedLinesForMethod(treeSet);
	}

	/**
	 * Scrive file contenente strighe del tipo: metodo-numero di istruzioni
	 * testate in totale all'interno del metodo.
	 * 
	 * @param treeSet
	 * @throws IOException
	 */
	private static void writeNumberOfTestedLinesForMethod(
			TreeSet<String> treeSet) throws IOException {
		String[] arrayLine = null;
		String[] numberInstructionsArray;

		for (String line : treeSet) {
			arrayLine = line.split("#c");
			numberInstructionsArray = line.split("#i");

			System.out.println(numberInstructionsArray[1]
					+ " numero istruzioni");
			if (totalInstructionsNumber.containsKey(arrayLine[0])) {
				numberInstructions = totalInstructionsNumber.get(arrayLine[0]);
				System.out.println(numberInstructions + " " + arrayLine[0]);
				if (Integer.parseInt(numberInstructionsArray[1]) > 0) {
					numberInstructions += Integer
							.parseInt(numberInstructionsArray[1]);
				}
			}
			totalInstructionsNumber.put(arrayLine[0], numberInstructions);
			numberInstructions = 0;
			PrintWriter printWriter;
			String numberOfInstructionsFilePath = "C:/Users/Jacopo/Desktop/NumeroIstruzioniTestatePerMetodo.txt";
			printWriter = new PrintWriter(new FileWriter(
					numberOfInstructionsFilePath));

			for (Entry<String, Integer> entry : totalInstructionsNumber
					.entrySet()) {
				printWriter.println(entry.getKey() + " numero : "
						+ entry.getValue());
				printWriter.flush();
			}

			printWriter.close();
		}
	}

	/**
	 * Inserisce le linee ordinate in un nuovo file.
	 * @param treeSet
	 * @throws IOException
	 */
	private static void writeOrderedData(TreeSet<String> treeSet) throws IOException {
		PrintWriter printWriter;
		String numberOfInstructionsFilePath = "C:/Users/Jacopo/Desktop/DatiStatisticiOrdinati.txt";
		printWriter = new PrintWriter(new FileWriter(
				numberOfInstructionsFilePath));

		for (String line : treeSet) {
			printWriter.println(line);

			printWriter.flush();
		}

		printWriter.close();
	}

	/**
	 * Legge il file e ordina le stringhe inserendole in un TreeSet.
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static TreeSet<String> readAndOrderData() throws FileNotFoundException,
			IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"C:/Users/Jacopo/Desktop/DatiStatistici.txt"));
		TreeSet<String> treeSet = new TreeSet<String>();
		try {
			String line = br.readLine();
			while (line != null) {
				System.err.println(line);
				treeSet.add(line);
				line = br.readLine();
			}

		} finally {
			br.close();
		}
		return treeSet;
	}

}
