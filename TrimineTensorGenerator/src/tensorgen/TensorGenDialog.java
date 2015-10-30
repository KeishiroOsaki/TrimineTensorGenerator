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
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.awt.Font;

class TensorGenDialog {

	private JFrame frame;
	private JComboBox cbSQLtype;
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
	private TensorGenerator tensorGenerator;
	private CSVLoader csvLoader;
	private DefaultTableModel tableModel;
	private String[] header;
	private DefaultTableModel tableModel_combi;
	private JLabel label_stts;

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
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("224px:grow"),
				ColumnSpec.decode("129px:grow"), FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("152px:grow"), FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("141px:grow"), }, new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("27px"),
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(18dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(17dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(18dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(24dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(19dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(103dlu;default):grow"), }));

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

		cbSQLtype = new JComboBox();
		cbSQLtype.setModel(new DefaultComboBoxModel(new String[] {
				"H2 Database", "PostgreSQL" }));
		panel_1.add(cbSQLtype, "8, 2, left, top");

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
		txtFilePath.setEditable(false);
		panel_1.add(txtFilePath, "2, 6, 4, 1, fill, default");
		txtFilePath.setColumns(10);

		JButton buttonFileRef = new JButton("CSVファイル参照");
		buttonFileRef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (csvRadioButton.isSelected()) {
					JFileChooser filechooser = new JFileChooser();
					filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					FileFilter filter = new FileNameExtensionFilter("CSVファイル", "csv");
					filechooser.addChoosableFileFilter(filter);

					int selected = filechooser.showOpenDialog(buttonFileRef);
					if (selected == JFileChooser.APPROVE_OPTION) {
						File file = filechooser.getSelectedFile();
						txtFilePath.setText(file.getPath());
						tensorGenerator = new TensorGenerator(txtFilePath.getText());
						csvLoader = tensorGenerator.getCSVLoader();
						label_stts.setText("CSVファイルをオープン。「見出しを取得」してください。");
					}
				}
			}
		});
		panel_1.add(buttonFileRef, "6, 6");

		JButton btnSqlCon = new JButton("SQLデータベースに接続");
		btnSqlCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sqlRadioButton.isSelected()) {
					tensorGenerator = new TensorGenerator(cbSQLtype
							.getSelectedIndex(), txtDburi.getText(),
							txtUsername.getText(), String.valueOf(pwdPassword
									.getPassword()));
					label_stts.setText("SQLデータベースに接続しました。「DBからヘッダー情報を取得」してください。");
				}
			}
		});
		panel_1.add(btnSqlCon, "8, 6");

		JLabel lblSql = new JLabel("SQLテーブル名：");
		panel_1.add(lblSql, "1, 8, center, default");

		txtTablename = new JTextField();
		txtTablename.setText("tableName");
		panel_1.add(txtTablename, "2, 8, 4, 1, fill, default");
		txtTablename.setColumns(10);

		JButton btngetHeader = new JButton("見出しを取得");
		btngetHeader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				while (tableModel.getRowCount() != 0) {
					tableModel.removeRow(0);
				}
				
				for (String hname : csvLoader.getHeader()) {
					String[] tmp = {hname,"VARCHAR"};
					tableModel.addRow(tmp);
				}
				label_stts.setText("見出しを取得しました。適切なデータ型を設定し、「CSVをDBに読み込」ませてください。");
			}
		});
		panel_1.add(btngetHeader, "1, 10");

		String[] columnNames = { "列名", "データ型" };
		tableModel = new DefaultTableModel(columnNames, 0);

		JButton btnCsvread = new JButton("CSVをDBに読み込み");
		btnCsvread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					list.add((String) tableModel.getValueAt(i, 1));
				}
				csvLoader.setDataType((String[]) list.toArray(new String[list.size()]));
			tensorGenerator.setDbCon(csvLoader.createDB());
			txtTablename.setText("TMP");
			txtDburi.setText("./temp");
			txtUsername.setText("sa");
			pwdPassword.setText("");
			label_stts.setText("CSVをDBに読み込みました。「DBからヘッダー情報を取得」してください。");
			}
			
		});
		panel_1.add(btnCsvread, "2, 10");
		
		JButton btnGetDbHeader = new JButton("DBからヘッダー情報取得");
		btnGetDbHeader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tensorGenerator.setTableName(txtTablename.getText());
				header = tensorGenerator.getHeader();
				//DefaultComboBoxModel<String> cBoxHeaderModel = new DefaultComboBoxModel<String>(header);
				cbActCol.setModel(new DefaultComboBoxModel<String>(header));
				cbCombiCol.setModel(new DefaultComboBoxModel<String>(header));
				cbObjCol.setModel(new DefaultComboBoxModel<String>(header));
				cbTimeCol.setModel(new DefaultComboBoxModel<String>(header));
				label_stts.setText("ヘッダー情報を取得しました。必要に応じて組み合わせ値を取得・グループを設定し、期間にイベントの時間的な範囲を入力してください。");
			}
		});
		panel_1.add(btnGetDbHeader, "6, 10");

		JLabel label_6 = new JLabel("組み合わせ値グループ設定");
		panel_1.add(label_6, "8, 10, center, default");

		dtypeSelecTable = new JTable(tableModel);
		// dtypeSelecTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPaneHead = new JScrollPane(dtypeSelecTable);
		scrollPaneHead.setPreferredSize(new Dimension(135, 200));

		panel_1.add(scrollPaneHead, "1, 12, 1, 7, fill, fill");
		// cbDtypeBox.setBorder(BorderFactory.createEmptyBorder());

		TableColumn colDataType = dtypeSelecTable.getColumnModel().getColumn(1);
		JComboBox cbDtypeBox = new JComboBox(new String[] { "VARCHAR", "DATE",
				"TIMESTAMP", "BIGINT" });
		colDataType.setCellEditor(new DefaultCellEditor(cbDtypeBox));

		JLabel label_2 = new JLabel("オブジェクト");
		panel_1.add(label_2, "2, 12, right, default");

		cbObjCol = new JComboBox();
		panel_1.add(cbObjCol, "6, 12, fill, default");

		String[] columnNames_combi = { "値", "グループ" };
		tableModel_combi = new DefaultTableModel(
				columnNames_combi, 0);

		combiGroupTable = new JTable(tableModel_combi);

		JScrollPane scrollPaneCombiGroup = new JScrollPane(combiGroupTable);
		panel_1.add(scrollPaneCombiGroup, "8, 12, 1, 7, fill, fill");

		combiGroupTable
				.getColumnModel()
				.getColumn(1)
				.setCellEditor(
						new DefaultCellEditor(new JComboBox(new String[] {
								"(not use)", "A", "B", "C", "D", "E", "F", "G",
								"H", "I", "J" })));

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
		buttonCombiListRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tensorGenerator.setCombifieldName((String) cbCombiCol.getSelectedItem());

				while (tableModel_combi.getRowCount() != 0) {
					tableModel_combi.removeRow(0);
				}
				
				for (String value : tensorGenerator.getCombiDistinctValues()) {
					String[] tmp = {value,"(not use)"};
					tableModel_combi.addRow(tmp);
				}
				label_stts.setText("組み合わせ先値を取得しました。「組み合わせグループをセット」してください。");
			}
		});
		panel_1.add(buttonCombiListRead, "1, 22, 2, 1");

		JButton buttonCombiGroupMapSet = new JButton("組み合わせグループセット");
		buttonCombiGroupMapSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (radioButtonCombiActor.isSelected() || radioButtonCombiObj.isSelected()) {
					Map<String,String> combimap = new HashMap<String,String>();
					for (int i = 0; i < tableModel_combi.getRowCount(); i++) {
						if (((String)tableModel_combi.getValueAt(i, 1)).equals("(not use)") == false) {
							combimap.put((String)tableModel_combi.getValueAt(i, 0), (String)tableModel_combi.getValueAt(i, 1));
						}
					}
					tensorGenerator.setCombiGroupMap(combimap);
					if (radioButtonCombiObj.isSelected()) {
						tensorGenerator.setCombiObject();
					} else {
						tensorGenerator.setCombiActor();
					}
					label_stts.setText("組み合わせ先グループをセットしました。期間を入力し、「開始」してください。");
				} else {
					label_stts.setText("どちらと組み合わせるのか、選択してください。");
				}
			}
		});
		panel_1.add(buttonCombiGroupMapSet, "6, 22, 3, 1");

		JLabel lblPeriod = new JLabel("期間");
		panel_1.add(lblPeriod, "1, 24, center, default");

		textStartPeriod = new JTextField();
		textStartPeriod.setToolTipText("DBの時間フィールドのデータ型がTIMESTAMPならYYYY-MM-DD hh:mm:ss形式、数値なら%d形式で入力してください");
		panel_1.add(textStartPeriod, "2, 24, fill, default");
		textStartPeriod.setColumns(10);

		JLabel label_1 = new JLabel("~");
		panel_1.add(label_1, "4, 24, right, default");

		textEndPeriod = new JTextField();
		textEndPeriod.setToolTipText("DBの時間フィールドのデータ型がTIMESTAMPならYYYY-MM-DD hh:mm:ss形式、数値なら%d形式で入力してください");
		panel_1.add(textEndPeriod, "6, 24, fill, default");
		textEndPeriod.setColumns(10);

		JButton buttonCombiCancel = new JButton("組み合わせを解除");
		buttonCombiCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tensorGenerator.setCombiCancel();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				combiSaki.clearSelection();
				label_stts.setText("組み合わせを解除しました。必要なら再度「グループをセット」してください。");
			}
		});
		panel_1.add(buttonCombiCancel, "8, 24");

		JLabel label_8 = new JLabel("時間の単位");
		panel_1.add(label_8, "1, 26, center, default");

		comboBox_TimeUnit = new JComboBox();
		comboBox_TimeUnit.setModel(new DefaultComboBoxModel(new String[] { "週",
				"日", "時" }));
		panel_1.add(comboBox_TimeUnit, "2, 26, fill, default");
		
				JButton buttonStart = new JButton("開始");
				buttonStart.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tensorGenerator.setfieldName((String)cbObjCol.getSelectedItem(), (String)cbActCol.getSelectedItem(), (String)cbTimeCol.getSelectedItem());
						
						tensorGenerator.setTimeRange(textStartPeriod.getText(), textEndPeriod.getText());
						tensorGenerator.setTimeUnit(comboBox_TimeUnit.getSelectedIndex()+1);
						tensorGenerator.startGenrerateTensor();
						label_stts.setText("テンソル生成を開始しました。完了するまでお待ち下さい。");
					}
				});
				panel_1.add(buttonStart, "6, 26");
		
				JButton buttonExit = new JButton("途中終了");
				buttonExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				panel_1.add(buttonExit, "8, 26");

		label_stts = new JLabel("ステータス");
		label_stts.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		panel_1.add(label_stts, "1, 28, 8, 1, center, default");

		progresListmodel = new DefaultListModel();
		JList progresList = new JList(progresListmodel);

		JScrollPane scrollPaneProgress = new JScrollPane();
		scrollPaneProgress
				.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(scrollPaneProgress, "1, 30, 8, 1, fill, fill");

		label_stts.setText("入力ソースを選択し、必要に応じて情報を入力・選択して、参照又は接続してください。");
		// tableModel.addRow(new String[] {"time","Timestamp"});
	}

}
