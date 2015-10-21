package tensorgen;

import java.awt.EventQueue;

import javax.swing.JFrame;

class TensorGenDialog {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TensorGenDialog window = new TensorGenDialog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TensorGenDialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(900, 300, 572, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
