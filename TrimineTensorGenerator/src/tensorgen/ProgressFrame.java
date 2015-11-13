package tensorgen;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JProgressBar;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

class ProgressFrame extends JFrame {

	private JPanel contentPane;
	JProgressBar progressBar;
	private JList list;
	DefaultListModel<StringBuilder> pListModel;
	ArrayList<StringBuilder> pArrayList = new ArrayList<>();
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ProgressFrame frame = new
	 * ProgressFrame(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public ProgressFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 678, 255);
		contentPane = new JPanel(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 55, 0, 3 };
		gbl_contentPane.rowHeights = new int[] { 187, 2 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		pListModel = new DefaultListModel<StringBuilder>();

		progressBar = new JProgressBar();
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 0;
		contentPane.add(progressBar, gbc_progressBar);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		list = new JList(pListModel);
		scrollPane.setViewportView(list);

		Timer t = new Timer();
		t.schedule(new Update(), 0, 25);
	}

	synchronized void progvalIncrement() {
		progressBar.setValue(progressBar.getValue() + 1);
	}

	synchronized void addList(StringBuilder sb) {
		pListModel.add(0, sb);
	}

	synchronized void removeList(StringBuilder sb) {
		pListModel.removeElement(sb);
	}

	private class Update extends TimerTask {
		public void run() {

			list.updateUI();

		}
	}

}
