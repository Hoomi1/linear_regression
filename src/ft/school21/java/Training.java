package ft.school21.java;

import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Training {

    public static List<Double> listX = new ArrayList<>();
    public static List<Double> listY = new ArrayList<>();
    public static TreeMap<Double, Double> data = new TreeMap<>();
    public static int gEpo;
    public static List<Double> listError = new ArrayList<>();
    public static double theta0;
    public static double theta1;
    public static String nameFile = "data.csv";

    //бонусы
    //1,2) рисовалка regression
    //3)MSE ошибка
    //4)вывод ошибок в файл
    //5)рисовалка MSE


    public static void main(String[] args) {
        parsingFile(nameFile);
        if (args.length == 0) {
            initPoint();
            PrintGraph("L");
        } else if (args[0].equals("-gd") || args[0].equals("-g")) {
            ParsingFile(nameFile);
            GradientDescent(Mashtab());
            PrintGraph("M");
        }
        fileWrite();

        System.out.println("\nY = " + String.format("%.5f", theta1) +
                "*X + " + String.format("%.0f", theta0));
    }

    private static ArrayList<Double> Mashtab() {

        ArrayList<Double> newListX = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            newListX.add((listX.get(i) - data.firstEntry().getKey()) /
                    (data.lastEntry().getKey() - data.firstEntry().getKey()));
        }
        System.out.println(data.firstEntry().getKey() + " " + data.lastEntry().getKey());
        return newListX;
    }

    private static double[] GradientStep(double[] ab, ArrayList<Double> newlistX) {
        double learningRate = 0.1;
        double a_step = 0;
        double b_step = 0;

        for (int j = 0; j < newlistX.size(); j++) {
            a_step += (newlistX.get(j) * ((ab[0] * newlistX.get(j) + ab[1]) - listY.get(j)));
            b_step += ((ab[0] * newlistX.get(j) + ab[1]) - listY.get(j));
        }
        ab[0] -= a_step * (learningRate / listX.size());
        ab[1] -= b_step * (learningRate / listX.size());

        double[] res = {ab[0], ab[1]};
        return res;

    }

    private static double getMeanSquaredError(double a, double b, ArrayList<Double> newlistX) {
        double error = 0;
        for (int i = 0; i < newlistX.size(); i++) {
            error += Math.pow(listY.get(i) - (a * newlistX.get(i) + b), 2);
        }
        listError.add(error / newlistX.size());
        return error / newlistX.size();
    }

    private static void GradientDescent(ArrayList<Double> newlistX) {
        double a = 0;
        double b = 0;
        int epochs = 1615;
        gEpo = epochs;
        double[] ab = {a, b};
        try {
            FileWriter fileWriter = new FileWriter("errors.txt", false);

            for (int i = 0; i < epochs; i++) {
                ab = GradientStep(ab, newlistX);
                String str = String.format("Epoch %d: %.0f\n", (i + 1), getMeanSquaredError(ab[0], ab[1], newlistX));
                fileWriter.write(str);
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        theta0 = ab[1];
        theta1 = ab[0];
        theta1 = theta1 / (data.lastEntry().getKey() - data.firstEntry().getKey());
    }


    private static void PrintGraph(String command) {
        if (command.equals("L")) {
            XYLinearGraph chart = new XYLinearGraph();
            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);
        } else {
            XYMean chart = new XYMean();
            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);
        }
    }

    private static void fileWrite() {
        try {
            FileWriter fileWriter = new FileWriter("values.txt", false);
            String str = "theta0: " + String.format("%.0f", theta0) + "\n" +
                    "theta1: " + String.format("%.5f", theta1);
            fileWriter.write(str);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static double average(List<Double> list) {
        double x = 0;
        for (int i = 0; i < list.size(); i++) {
            x += list.get(i);
        }
        return x / list.size();
    }

    private static void initPoint() {
        double averageX = average(listX);
        double averageY = average(listY);
        double diffY = 0;
        double diffX = 0;
        double sumDiffXY = 0;
        double sumX = 0;
        for (int i = 0; i < listX.size(); i++) {
            diffY = listY.get(i) - averageY;
            diffX = listX.get(i) - averageX;
            sumDiffXY += diffY * diffX;
            sumX += (listX.get(i) - averageX) * (listX.get(i) - averageX);
        }
        theta1 = sumDiffXY / sumX;
        theta0 = averageY - theta1 * averageX;
    }

    private static void ParsingFile(String file) {
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

    private static void parsingFile(String file) {
        String[] data = null;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String row = "";
            int i = 0;
            while ((row = bufferedReader.readLine()) != null) {
                data = row.split(",");
                if (i != 0) {
                    listX.add(Double.parseDouble(data[0]));
                    listY.add(Double.parseDouble(data[1]));
                }
                ++i;
            }
            fileReader.close();
            bufferedReader.close();
            checkData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkData() {
        if (listX.size() != listY.size()) {
            System.err.println("Must have equal X and Y data points");
            System.exit(1);
        }
    }
}
