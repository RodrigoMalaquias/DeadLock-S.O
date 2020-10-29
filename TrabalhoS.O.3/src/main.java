import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Encontrar Deadlock - Trabalho 3
 * 
 * @author Rodrigo Malaquias
 *
 */

public class main {
	public static void main(String[] args) throws IOException {
		try {
			FileReader arq = new FileReader("deadlockRead.txt");
			BufferedReader lerArq = new BufferedReader(arq);

			String[] firstLine = (lerArq.readLine()).split(" "); // Lendo a primeira linha e pegando variaveis entre os
																	// espaços.
			int x; // Quantidade de processos existente no sistema
			int y; // Quantidade de recursos.

			// Armazenando x e y da primeira linha do arquivo.
			x = Integer.parseInt(firstLine[0]);
			y = Integer.parseInt(firstLine[1]);

			String[] vetorLeft = new String[x];
			String[] vetorRight = new String[x];
			String[] readLine;
			for (int i = 0; i < x; i++) { // Percorrendo o txt
				readLine = (lerArq.readLine()).split(";");
				vetorLeft[i] = (readLine[0]); // armazenando recursos alocados para o processo
				vetorRight[i] = (readLine[1]); // armazenando recursos requisitados pelo processo
				// System.out.println(vetorLeft[i]+" "+vetorRight[i]);

			}

			ArrayList<String> sequence = new ArrayList<String>();

			for (int i = 0; i < x; i++) {

				for (int j = 0; j < x; j++) {
					if (vetorRight[i].length() == 2) { // Quando se pede apenas 1 requisito
						System.out.println("Processo :" + (i + 1) + " Quer :" + vetorRight[i]);
						if (vetorRight[i].trim().equals(vetorLeft[j].trim())) {
							System.out.println("Processo " + (j + 1) + " Tem " + vetorRight[i]);
							sequence.add(Integer.toString((i + 1)));
							sequence.add(vetorRight[i]);
						} else {
							System.out.println("Processo " + (j + 1) + " Não Tem " + vetorRight[i]);
						}
					} else {
						int amountRequirements = (vetorRight[i].length() / 2);
						int begin = 0;
						int end = 2;
						for (int z = 0; z < amountRequirements; z++) { // Quando se pede apenas 2 ou mais requisito
							System.out
									.println("Processo :" + (i + 1) + " Quer :" + vetorRight[i].substring(begin, end));
							if (vetorRight[i].substring(begin, end).trim().equals(vetorLeft[j].trim())) {
								System.out
										.println("Processo " + (j + 1) + "Tem " + vetorRight[i].substring(begin, end));
								sequence.add(Integer.toString((i + 1)));
								sequence.add(vetorRight[i].substring(begin, end));
							} else {
								System.out.println("Processo " + (j + 1) + "Não Tem "
										+ vetorRight[i].substring(begin, end).trim());
							}
							System.out.println();
							begin += 2;
							end += 2;
						}

					}

				}
				System.out.println();
			}
			String getLetter = sequence.get(sequence.size() - 1);
			System.out.println("Ultima letra :" + getLetter);
			int controller = 0;

			for (int i = 0; i < x; i++) {
				if (vetorLeft[i].trim().equals(getLetter.trim())) {
					controller = i + 1;
					System.out.println("Controlador: " + (controller));
				}
			}

			System.out.println();

			int beginCycle = 0;
			beginCycle = indexOfArray(Integer.toString(controller), sequence);
			System.out.println("Index do controlador: " + indexOfArray(Integer.toString(controller), sequence));

			System.out.println(sequence.toString()+" Sequencia sem Ciclo!");
			System.out.println(sequence.subList(beginCycle, sequence.size())+" Ciclo!");


			FileWriter fw = new FileWriter("saida.txt");
			BufferedWriter a = new BufferedWriter(fw);

			for (String e : sequence.subList(beginCycle, sequence.size())) {

				a.write(e.trim()+" ");
			}
			

			a.close();
			fw.close();

			lerArq.close();
		} catch (

		IOException a) {
			System.err.printf("Erro ao abrir o arquivo de leitura.\n" + a.getMessage());
		}

	}

	public static int indexOfArray(String input, ArrayList<String> a) {
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).equals(input)) {
				return i;
			}
		}
		return -1; //Se no texto nao é encontrado o que voce deseja
	}
}