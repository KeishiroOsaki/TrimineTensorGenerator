package tensorgen;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

class TensorGenDialog {

	private JFrame frame;
	private JTextField txtDburi;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField txtFilePath;
	private JTextField txtTablename;
	private JTextField textField;
	private JTextField textField_1;

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
		frame.setBounds(900, 300, 721, 743);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("139px:grow"),
				ColumnSpec.decode("129px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("152px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("141px:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(18dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(17dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(18dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(24dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(103dlu;default):grow"),}));
		
		JLabel label = new JLabel("読み込み対象：");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label, "1, 2");
		
		JRadioButton sqlRadioButton = new JRadioButton("SQL");
		panel_1.add(sqlRadioButton, "2, 2, left, center");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"H2 Database", "PostgreSQL"}));
		panel_1.add(comboBox, "6, 2, left, top");
		
		JRadioButton csvRadioButton = new JRadioButton("CSV");
		panel_1.add(csvRadioButton, "8, 2, left, center");
		
		ButtonGroup bgDataSrc = new ButtonGroup();
		bgDataSrc.add(sqlRadioButton);
		bgDataSrc.add(csvRadioButton);
		
		JLabel lblNewLabel = new JLabel("SQLデータベース接続先：");
		panel_1.add(lblNewLabel, "1, 4, center, default");
		
		txtDburi = new JTextField();
		txtDburi.setText("DBURI");
		panel_1.add(txtDburi, "2, 4, 4, 1, fill, default");
		txtDburi.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setText("UserName");
		panel_1.add(txtUsername, "6, 4, fill, default");
		txtUsername.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password");
		panel_1.add(pwdPassword, "8, 4, fill, default");
		
		JLabel lblCsv = new JLabel("CSV読み込み先：");
		lblCsv.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblCsv, "1, 6, center, default");
		
		txtFilePath = new JTextField();
		panel_1.add(txtFilePath, "2, 6, 4, 1, fill, default");
		txtFilePath.setColumns(10);
		
		JButton buttonFileRef = new JButton("CSVファイル参照");
		panel_1.add(buttonFileRef, "6, 6");
		
		JButton btnSql = new JButton("SQLデータベースに接続");
		panel_1.add(btnSql, "8, 6");
		
		JLabel lblSql = new JLabel("SQLテーブル名：");
		panel_1.add(lblSql, "1, 8, center, default");
		
		txtTablename = new JTextField();
		txtTablename.setText("tableName");
		panel_1.add(txtTablename, "2, 8, 4, 1, fill, default");
		txtTablename.setColumns(10);
		
		JButton btngetHeader = new JButton("見出しを取得");
		btngetHeader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_1.add(btngetHeader, "1, 10");
		
		
		
		String[] columnNames = {"列名", "データ型"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		
		JButton btnCsvread = new JButton("CSVをDBに読み込み");
		panel_1.add(btnCsvread, "2, 10");
		
		JLabel label_6 = new JLabel("組み合わせ値グループ設定");
		panel_1.add(label_6, "8, 10, center, default");
		JTable dtypeSelecTable = new JTable(tableModel);
		dtypeSelecTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPaneHead = new JScrollPane(dtypeSelecTable);
		//scrollPaneHead.add(dtypeSelecTable);
		scrollPaneHead.setPreferredSize(new Dimension(135, 200));
		
		panel_1.add(scrollPaneHead, "1, 12, 1, 7, fill, fill");
		//cbDtypeBox.setBorder(BorderFactory.createEmptyBorder());
		
		TableColumn col = dtypeSelecTable.getColumnModel().getColumn(1);
		
		JLabel label_2 = new JLabel("オブジェクト");
		panel_1.add(label_2, "2, 12, right, default");
		
		JComboBox comboBox_1 = new JComboBox();
		panel_1.add(comboBox_1, "6, 12, fill, default");
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "8, 12, 1, 7, fill, fill");
		
		JLabel label_3 = new JLabel("アクター");
		panel_1.add(label_3, "2, 14, right, default");
		
		JComboBox comboBox_2 = new JComboBox();
		panel_1.add(comboBox_2, "6, 14, fill, default");
		
		JLabel label_4 = new JLabel("時間");
		panel_1.add(label_4, "2, 16, right, default");
		
		JComboBox comboBox_3 = new JComboBox();
		panel_1.add(comboBox_3, "6, 16, fill, default");
		
		JLabel label_5 = new JLabel("組み合わせ用フィールド");
		panel_1.add(label_5, "2, 18, right, default");
		
		JComboBox comboBox_4 = new JComboBox();
		panel_1.add(comboBox_4, "6, 18, fill, default");
		
		JLabel label_7 = new JLabel("組み合わせ先");
		panel_1.add(label_7, "2, 20, right, default");
		
		JRadioButton radioButtonCombiObj = new JRadioButton("オブジェクト");
		panel_1.add(radioButtonCombiObj, "6, 20");
		
		JRadioButton radioButtonCombiActor = new JRadioButton("アクター");
		panel_1.add(radioButtonCombiActor, "8, 20");
		
		ButtonGroup combiSaki = new ButtonGroup();
		combiSaki.add(radioButtonCombiActor);
		combiSaki.add(radioButtonCombiObj);
		
		JLabel lblPeriod = new JLabel("期間");
		panel_1.add(lblPeriod, "1, 22, center, default");
		
		textField = new JTextField();
		panel_1.add(textField, "2, 22, fill, default");
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("~");
		panel_1.add(label_1, "4, 22, right, default");
		
		textField_1 = new JTextField();
		panel_1.add(textField_1, "6, 22, fill, default");
		textField_1.setColumns(10);
		
		JComboBox cbDtypeBox = new JComboBox(new String[] {"VARCHAR","DATE","TIMESTAMP","BIGINT"});
		col.setCellEditor(new DefaultCellEditor(cbDtypeBox));
		
		
		tableModel.addRow(new String[] {"time","Timestamp"});
	}

}
