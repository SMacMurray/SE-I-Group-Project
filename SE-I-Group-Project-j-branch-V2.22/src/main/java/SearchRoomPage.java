import javax.swing.*;
import java.awt.*;

public class SearchRoomPage extends JFrame{
	private static final long serialVersionUID = 1L;
    private JPanel mainPane;
    private JPanel contentPane;
    private JLabel titleLabel;
    
    SearchRoomPage() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize( Main.WINDOW_W + 500, Main.WINDOW_H + 50);
		
		mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		
		titleLabel = new JLabel("What type of room do you want?");
		JPanel titleWrapperPane = new JPanel();
		titleWrapperPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		titleWrapperPane.add(titleLabel, c);
		mainPane.add(titleWrapperPane, BorderLayout.PAGE_START);
		
		
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		mainPane.add(contentPane, BorderLayout.CENTER);
		
		
		
		c.insets = new Insets(0, 0, 0, 75); // right padding
		JLabel guestsLabel = new JLabel("Guests");
		contentPane.add(guestsLabel, c);
		JLabel designLabel = new JLabel("Design");
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 1;
		contentPane.add(designLabel, c);
		JLabel costLabel = new JLabel("Cost");
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 2;
		contentPane.add(costLabel, c);
		
		
		
		
		setContentPane(mainPane);
		setVisible(true);
    }
}
