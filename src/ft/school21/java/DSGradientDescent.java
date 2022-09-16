package ft.school21.java;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DSGradientDescent {

	private static double theta0, theta1;
	public static int gEpo;
	public static List<Double> listError = new ArrayList<>();
	public static TreeMap<Double, Double> data = new TreeMap<>();
	private static String nameFile;

	public static XYDataset createDataset()
	{
		nameFile = Training.nameFile;
		ParsingFile(nameFile);
		listError = Training.listError;
		gEpo = Training.gEpo;
		theta0 = Training.theta0;
		theta1 = Training.theta1;

		final XYSeries MeanSquaredError = new XYSeries("Mean Squared Error");

		if (!data.isEmpty())
		{
			for (int i = 0; i < gEpo; i++) {
				MeanSquaredError.add(i, (double) listError.get(i));
			}
//			linearRegression.add((double) data.firstKey(), theta1 * data.firstKey() + theta0);
//			linearRegression.add((double) data.lastKey(), theta1 * data.lastKey() + theta0);
		}


		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(MeanSquaredError);
		return dataset;
	}

	private static void ParsingFile(String file)
	{
		String[] num = null;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String row = "";
			int i = 0;
			while ((row = bufferedReader.readLine()) != null) {
				num = row.split(",");
				if (i != 0) {
					data.put(Double.parseDouble(num[0]), Double.parseDouble(num[1]));
				}
				++i;
			}
			fileReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
