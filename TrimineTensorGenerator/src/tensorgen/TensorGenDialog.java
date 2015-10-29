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
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ListModel;

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
import javax.swing.border.LineBorder;

import java.awt.Color;

class TensorGenDialog {

	private JFrame frame;
	private JTextField txtDburi;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField txtFilePath;
	private JTextField txtTablename;
	private JTextField textStartPeriod;
	private JTextField textEndPeriod;
	private JComboBox comboBox_TimeUnit;
	private JComboBox cbCombiCol;
	private JComboBox cbTimeCol;
	private JComboBox cbActCol;
	private JComboBox cbObjCol;
	private JRadioButton radioButtonCombiObj;
	private JRadioButton radioButtonCombiActor;
	private JRadioButton sqlRadioButton;
	private JRadioButton csvRadioButton;
	private JTable dtypeSelecTable;
	private JTable combiGroupTable;
	private DefaultListModel progresListmodel;

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
		frame.setBounds(900, 300, 797, 749);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("224px:grow"),
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(24dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(19dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(103dlu;default):grow"),}));
		
		JLabel label = new JLabel("読み込み対象：");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label, "1, 2");
		
		csvRadioButton = new JRadioButton("CSV");
		panel_1.add(csvRadioButton, "2, 2, left, center");
		
		
		ButtonGroup bgDataSrc = new ButtonGroup();
		
		sqlRadioButton = new JRadioButton("SQL");
		panel_1.add(sqlRadioButton, "6, 2, left, center");
		bgDataSrc.add(csvRadioButton);
		bgDataSrc.add(sqlRadioButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"H2 Database", "PostgreSQL"}));
		panel_1.add(comboBox, "8, 2, left, top");
		
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
		
		dtypeSelecTable = new JTable(tableModel);
		//dtypeSelecTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPaneHead = new JScrollPane(dtypeSelecTable);
		scrollPaneHead.setPreferredSize(new Dimension(135, 200));
		
		panel_1.add(scrollPaneHead, "1, 12, 1, 7, fill, fill");
		//cbDtypeBox.setBorder(BorderFactory.createEmptyBorder());
		
		TableColumn colDataType = dtypeSelecTable.getColumnModel().getColumn(1);
		JComboBox cbDtypeBox = new JComboBox(new String[] {"VARCHAR","DATE","TIMESTAMP","BIGINT"});
		colDataType.setCellEditor(new DefaultCellEditor(cbDtypeBox));
		
		JLabel label_2 = new JLabel("オブジェクト");
		panel_1.add(label_2, "2, 12, right, default");
		
		cbObjCol = new JComboBox();
		panel_1.add(cbObjCol, "6, 12, fill, default");
		
		String[] columnNames_combi = {"値", "グループ"};
		DefaultTableModel tableModel_combi = new DefaultTableModel(columnNames_combi, 0);
		
		combiGroupTable = new JTable(tableModel_combi);
		
		JScrollPane scrollPaneCombiGroup = new JScrollPane(combiGroupTable);
		panel_1.add(scrollPaneCombiGroup, "8, 12, 1, 7, fill, fill");
		
		combiGroupTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new String[] {"(not use)","A","B","C","D","E","F","G","H","I","J"})));
		
		JLabel label_3 = new JLabel("アクター");
		panel_1.add(label_3, "2, 14, right, default");
		
		cbActCol = new JComboBox();
		panel_1.add(cbActCol, "6, 14, fill, default");
		
		JLabel label_4 = new JLabel("時間");
		panel_1.add(label_4, "2, 16, right, default");
		
		cbTimeCol = new JComboBox();
		panel_1.add(cbTimeCol, "6, 16, fill, default");
		
		JLabel label_5 = new JLabel("組み合わせ用フィールド");
		panel_1.add(label_5, "2, 18, right, default");
		
		cbCombiCol = new JComboBox();
		panel_1.add(cbCombiCol, "6, 18, fill, default");
		
		JLabel label_7 = new JLabel("組み合わせ先");
		panel_1.add(label_7, "2, 20, right, default");
		
		radioButtonCombiObj = new JRadioButton("オブジェクト");
		panel_1.add(radioButtonCombiObj, "6, 20");
		
		radioButtonCombiActor = new JRadioButton("アクター");
		panel_1.add(radioButtonCombiActor, "8, 20");
		
		ButtonGroup combiSaki = new ButtonGroup();
		combiSaki.add(radioButtonCombiActor);
		combiSaki.add(radioButtonCombiObj);
		
		JButton buttonCombiListRead = new JButton("組み合わせ先値リスト読み込み");
		panel_1.add(buttonCombiListRead, "1, 22, 2, 1");
		
		JButton buttonCombiGroupMapSet = new JButton("組み合わせグループセット");
		panel_1.add(buttonCombiGroupMapSet, "6, 22, 3, 1");
		
		JLabel lblPeriod = new JLabel("期間(YYYY-MM-DD hh:mm:ss形式)");
		panel_1.add(lblPeriod, "1, 24, center, default");
		
		textStartPeriod = new JTextField();
		panel_1.add(textStartPeriod, "2, 24, fill, default");
		textStartPeriod.setColumns(10);
		
		JLabel label_1 = new JLabel("~");
		panel_1.add(label_1, "4, 24, right, default");
		
		textEndPeriod = new JTextField();
		panel_1.add(textEndPeriod, "6, 24, fill, default");
		textEndPeriod.setColumns(10);
		
		JButton buttonCombiCancel = new JButton("組み合わせを解除");
		panel_1.add(buttonCombiCancel, "8, 24");
		
		JLabel label_8 = new JLabel("時間の単位");
		panel_1.add(label_8, "1, 26, center, default");
		
		comboBox_TimeUnit = new JComboBox();
		comboBox_TimeUnit.setModel(new DefaultComboBoxModel(new String[] {"週", "日", "時"}));
		panel_1.add(comboBox_TimeUnit, "2, 26, fill, default");
		
		JLabel label_stts = new JLabel("ステータス");
		panel_1.add(label_stts, "1, 28, 4, 1, center, default");
		
		JButton buttonStart = new JButton("開始");
		panel_1.add(buttonStart, "6, 28");
		
		JButton buttonExit = new JButton("途中終了");
		panel_1.add(buttonExit, "8, 28");
		
		progresListmodel = new DefaultListModel();
		JList progresList = new JList(progresListmodel);
		
		JScrollPane scrollPaneProgress = new JScrollPane();
		scrollPaneProgress.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(scrollPaneProgress, "1, 30, 8, 1, fill, fill");
		
		
		
		
		tableModel.addRow(new String[] {"time","Timestamp"});
	}

}
