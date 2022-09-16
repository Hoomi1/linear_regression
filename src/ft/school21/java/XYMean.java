package ft.school21.java;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import java.awt.*;

public class XYMean extends ApplicationFrame {

	public XYMean() {
		super("MSE");

		XYDataset dataset = DSGradientDescent.createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(XYDataset dataset) {

		final JFreeChart xylineChart = ChartFactory.createXYLineChart("MeanSquaredError",
				"Epochs",
				"Errors",
				dataset,
				PlotOrientation.VERTICAL,
				true, false, false);
		xylineChart.setBackgroundPaint(Color.white);

		final XYPlot plot = xylineChart.getXYPlot();
		plot.setBackgroundPaint(new Color(232, 232, 232));

		plot.setDomainGridlinePaint(Color.gray);
		plot.setRangeGridlinePaint(Color.gray);

		plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesShapesVisible(0, false);

		renderer.setSeriesPaint  (0, Color.blue);
		renderer.setSeriesStroke (0, new BasicStroke(2.5f));
		plot.setRenderer(renderer);

		return xylineChart;
	}
}
