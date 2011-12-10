private JFreeChart generatePieChart(String title, DefaultPieDataset dataset, boolean showLegend) {
	final JFreeChart chart = ChartFactory.createPieChart(
			title,
			dataset,
			showLegend, // legend
			true, // tooltips
			false // URLs
		);
	return chart;
}