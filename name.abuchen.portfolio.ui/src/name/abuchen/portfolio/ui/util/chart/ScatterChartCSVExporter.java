package name.abuchen.portfolio.ui.util.chart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import name.abuchen.portfolio.ui.Messages;
import name.abuchen.portfolio.ui.util.AbstractCSVExporter;

import org.apache.commons.csv.CSVPrinter;
import org.eclipse.swt.widgets.Control;
import org.swtchart.ISeries;

public class ScatterChartCSVExporter extends AbstractCSVExporter
{
    private final ScatterChart chart;

    private NumberFormat valueFormat = new DecimalFormat("#,##0.00"); //$NON-NLS-1$

    public ScatterChartCSVExporter(ScatterChart viewer)
    {
        this.chart = viewer;
    }

    public void setValueFormat(NumberFormat valueFormat)
    {
        this.valueFormat = valueFormat;
    }

    @Override
    protected Control getControl()
    {
        return chart;
    }

    @Override
    protected void writeToFile(File file) throws IOException
    {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))
        {
            CSVPrinter printer = new CSVPrinter(writer);
            printer.setStrategy(STRATEGY);

            // write header
            printer.print(Messages.ColumnDataSeries);
            printer.print(chart.getAxisSet().getXAxis(0).getTitle().getText());
            printer.print(chart.getAxisSet().getYAxis(0).getTitle().getText());
            printer.println();

            // write body
            ISeries[] series = chart.getSeriesSet().getSeries();
            
            for (ISeries serie : series)
            {
                printer.print(serie.getId());
                printer.print(valueFormat.format(serie.getXSeries()[0]));
                printer.print(valueFormat.format(serie.getYSeries()[0]));
                printer.println();
            }
        }
    }

}