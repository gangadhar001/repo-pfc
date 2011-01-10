package presentation.views;

import java.awt.Color;
import java.awt.Frame;

import org.eclipse.draw2d.GridData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;


public class UserInfView extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// row keys...
		String series1 = "First";
		String series2 = "Second";

		// column keys...
		String category1 = "Label 1";
		String category2 = "Label 2";
		String category3 = "Label  3";

		dataset.addValue(1.0, series1, category1);
		dataset.addValue(4.0, series1, category2);
		dataset.addValue(3.0, series1, category3);

		dataset.addValue(5.0, series2, category1);
		dataset.addValue(7.0, series2, category2);
		dataset.addValue(6.0, series2, category3);

		
		JFreeChart jfreechart = ChartFactory.createBarChart("", "l", "v", dataset, PlotOrientation.VERTICAL, false, true, false);
			
		CategoryPlot  plot = (CategoryPlot) jfreechart.getPlot();
		plot.setInsets(new RectangleInsets(0, 0, 0, 0));
			        plot.setBackgroundPaint(Color.lightGray);
					plot.setDomainGridlinePaint(Color.white);
					plot.setDomainGridlinesVisible(true);
					plot.setRangeGridlinePaint(Color.white);
			        /*final double angle = 290D;
			        pieplot3d.setStartAngle(angle);
			        pieplot3d.setDirection(Rotation.CLOCKWISE);*/
			       // final float foreground = 0.5F;
			       // pieplot3d.setForegroundAlpha(foreground);
		
			        // creates the chart component
			        Composite embeddedComposite = new Composite(parent, SWT.EMBEDDED);
			        embeddedComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			        Frame fileTableFrame = SWT_AWT.new_Frame(embeddedComposite);
		        ChartPanel panel = new ChartPanel(jfreechart);
			        panel.setPopupMenu(null);
		      fileTableFrame.add(panel);
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}