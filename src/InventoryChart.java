import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventoryChart extends JPanel {
    private List<Integer> books;
    private String title;

    public InventoryChart(List<Integer> books, String title) {
        this.books = books;
        this.title = title;
    }

    public void displayGraph() {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JPanel chartPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int numNonfiction = books.get(0);
                int numFiction = books.get(1);

                Dimension d = getSize();
                int clientWidth = d.width;
                int clientHeight = d.height;
                int barWidth = clientWidth / 2;

                Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
                FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

                int top = labelFontMetrics.getHeight() * 2;
                int bottom = labelFontMetrics.getHeight();
                if (numFiction == 0 && numNonfiction == 0)
                    return;
                double scale = (clientHeight - top - bottom) / Math.max(numFiction, numNonfiction);
                int y = clientHeight - labelFontMetrics.getDescent();

                if (numNonfiction > 0) {
                    int valueX = 1;
                    int valueY = top;
                    int height = (int) (numNonfiction * scale);

                    g.setColor(Color.red);
                    g.fillRect(valueX, valueY, barWidth - 2, height);
                    g.setColor(Color.black);
                    g.drawRect(valueX, valueY, barWidth - 2, height);
                    int labelWidth = labelFontMetrics.stringWidth("Non-fiction");
                    int x = valueX + (barWidth - labelWidth) / 2;
                    g.drawString("Non-fiction (" + numNonfiction + ")", x, y);
                }

                if (numFiction > 0) {
                    int valueX = clientWidth / 2 + 1;
                    int valueY = top;
                    int height = (int) (numFiction * scale);

                    g.setColor(Color.blue);
                    g.fillRect(valueX, valueY, barWidth - 2, height);
                    g.setColor(Color.black);
                    g.drawRect(valueX, valueY, barWidth - 2, height);
                    int labelWidth = labelFontMetrics.stringWidth("Fiction");
                    int x = valueX + (barWidth - labelWidth) / 2;
                    g.drawString("Fiction (" + numFiction + ")", x, y);
                }
            }
        };
        chartPanel.setPreferredSize(new Dimension(400, 400));
        chartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chartPanel);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public List<Integer> getBooks() {
        return books;
    }

    public void setBooks(List<Integer> books) {
        this.books = books;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}