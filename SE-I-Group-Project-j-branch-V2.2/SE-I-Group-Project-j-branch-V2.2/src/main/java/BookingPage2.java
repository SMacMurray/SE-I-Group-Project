import javax.swing.*;
import javax.swing.table.*; // Includes DefaulTableModel

import net.coderazzi.filters.OrFilter;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.LooseParserModel;
import net.coderazzi.filters.gui.TableFilterHeader;

import java.awt.*; // Includes BorderLayout.*,
import java.io.*; // Includes File
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;


public class BookingPage2 extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JPanel homePane;
	private JScrollPane scrollPane;
	private JPanel alignScrollPane;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTable table;
	private JPanel descriptionPane;
	
	private String[] columnNames = {
			"Room Number",
			"Beds",
			"Max Number of Guests",
			"Base Daily Rate",
			"Smoking Status",
			"Bed Sizes",
			"Quality Level",
			"Room Size"
	};
	
	public Object[][] initArray() {
		List<List<Object>> csvData = new ArrayList<>();
		
        try (Scanner scanner = new Scanner(new File("reserve.csv")) ) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
            	String[] spl = scanner.nextLine().split("\t");
            	
            	for( int i = spl.length - 8; i > 0; --i) {
            		spl[5] = spl[5] + "," + spl[5 + i];
            		System.out.println("rich");
            	}
            	csvData.add(new ArrayList<>(Arrays.asList(
            			Integer.parseInt(spl[0]),
            			Integer.parseInt(spl[1]),
            			Integer.parseInt(spl[2]),
            			Double.parseDouble(spl[3]),
            			spl[4],
            			spl[5].replaceAll("\"", ""),
            			spl[spl.length - 2],
            			spl[spl.length - 1]
            			
            	)));
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        }

        
        return csvData.stream()
                .map(l -> l.stream().toArray(Object[]::new))
                .toArray(Object[][]::new);
    }
	
	
	public BookingPage2() {
		Object[][] array = initArray();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize( Main.WINDOW_W + 300, Main.WINDOW_H + 300);
		
		mainPane = new JPanel();
		setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout());
		
		descriptionPane = new JPanel();
		descriptionPane.setBackground(Color.RED);
		
		
		final Class<?>[] columnClass = new Class[] {
                Integer.class, Integer.class, Integer.class, Double.class, String.class,
                String.class, String.class, String.class
        };
		DefaultTableModel model = new DefaultTableModel(array, columnNames) {
			@Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return columnClass[columnIndex];
            }
		};
		sorter = new TableRowSorter<DefaultTableModel>(model);
		table = new JTable(model);
		table.setRowSorter(sorter);
		TableFilterHeader filterHeader = new TableFilterHeader(table, new LooseParserModel(), AutoChoices.ENABLED);
		filterHeader.getParserModel().setIgnoreCase(true);
//		filterHeader.addFilter(new OrFilter());
		table.setPreferredScrollableViewportSize(new Dimension(750, 300));
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		alignScrollPane = new JPanel();
		alignScrollPane.setLayout(new FlowLayout());
		alignScrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		alignScrollPane.add(scrollPane);
		
		JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 140, 22);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage newFrame = new HomePage(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        JPanel alignHomePane = new JPanel();
        alignHomePane.setLayout(new FlowLayout(FlowLayout.LEFT));
        alignHomePane.add(homeButton);
        mainPane.add(alignHomePane, BorderLayout.PAGE_START);
		
		
		mainPane.add(alignScrollPane, BorderLayout.LINE_START);
		mainPane.add(descriptionPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
}
