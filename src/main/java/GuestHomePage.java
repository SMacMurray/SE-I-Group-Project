import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GuestHomePage extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public GuestHomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(Main.WINDOW_OFFSET_X, Main.WINDOW_OFFSET_Y, Main.WINDOW_W, Main.WINDOW_H);
        setTitle(Main.APP_TITLE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        
	}
}
