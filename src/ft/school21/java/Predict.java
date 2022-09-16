package ft.school21.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Predict {

	private static double theta0;
	private static double theta1;

	public static void main(String[] args) {

		readFile();
		for(;;){
			System.out.print("Enter KM: ");
			Scanner scanner = new Scanner(System.in);
			try{
				String command = scanner.nextLine();
				double res = 0;
				if (!command.equals("q"))
					res = theta0 + (theta1 * Double.parseDouble(command));
				else
					break;
				System.out.println(String.format("Price: %.3f$", res));
			}
			catch (NumberFormatException e) {
				continue;
			}
		}
	}

	private static void readFile() {

		try {
			FileReader fileReader = new FileReader("values.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String row = "";
			String[] arr;
			int i = 0;
			while ((row = bufferedReader.readLine()) != null) {
				arr = row.split(" ");
				if (i == 0) {
					String str = null;
					if (arr[1].indexOf(',') > 0) {
						str = arr[1].replace(',', '.');
						theta0 = Double.parseDouble(str);
					}
					else
						theta0 = Double.parseDouble(arr[1]);
					i++;
				}
				else {
					String str = null;
					if (arr[1].indexOf(',') > 0) {
						str = arr[1].replace(',', '.');
						theta1 = Double.parseDouble(str);
					}
					else
						theta1 = Double.parseDouble(arr[1]);
					break;
				}
			}
		} catch (IOException e) {
			theta1 = 0;
			theta0 = 0;
		}
	}
}
