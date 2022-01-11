package database;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;
import com.mysql.cj.xdevapi.Table;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class Ventana_expediente extends JFrame {
	private JPanel contentPane;
	private JTable table_odontograma;
	private JTextField textField_Ndiagnostico;
	private int current_expediente = 1;
	private JButton button_right;
	private JButton button_left;
	private JTextField textField_busqueda;
	private JTable table_1;
	private JTextField textField_prox_cita;
	private JTextField textField_cuidados;
	private ArrayList<String> diagnosticos = new ArrayList<String>();
	private JTextField textField;
	private JTextArea textArea_diagnostico;
	private JLabel lblPaciente;
	private JRadioButton radioBtnNombre;
	private JRadioButton radioBtnNacimiento;
	//private static String path = "/Users/diegogutierrez/Desktop/Images/";
	private static String path = "C:/Program Files (x86)/GTDent/Images/";
	
	Toolkit toolkit =  Toolkit.getDefaultToolkit();
	private Dimension screenSize = toolkit.getScreenSize();
	private int resol = toolkit.getScreenResolution();
	private double width = screenSize.width * 1.72;
	private double height = screenSize.height * 1.72;
	private int frame_width_size = (int)((width * 57.08) / 100);
	private int frame_height_size = (int)((height * 52.61) / 100) + 30;

	/**
	 * Lanzar la aplicacion
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//se genera un objeto de Ventana_expediente
					Ventana_expediente frame = new Ventana_expediente();
					//se hace visible
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crear la pantalla con sus respectiva informacion y botones
	 */
	public Ventana_expediente() throws SQLException {
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("EXPEDIENTES");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, frame_width_size, frame_height_size);
		setLocation(dim.width/2 - getSize().width/2, (dim.height/2- getSize().height/2)-20);
		contentPane = new JPanel() {

			private static final long serialVersionUID = 1L;

			public void paintComponent( Graphics g ) {
				  super.paintComponent(g);
				  Graphics2D g2d = (Graphics2D) g;
				  try {
					  InputStream image = new FileInputStream(path + "fondo 1.png");
					  BufferedImage src;
					  try {
						src = ImageIO.read(image);
						g2d.drawImage(src, 0, 0, frame_width_size, frame_height_size, null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds((int)((frame_width_size * 6.1) / 100), (int)((frame_height_size * 15.37) / 100), (int)((frame_width_size * 28.47) / 100), (int)((frame_height_size * 46.1) / 100));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		
		textArea_diagnostico = new JTextArea();
		textArea_diagnostico.setDocument(new JTextFIeldLimit(2000));
		textArea_diagnostico.setWrapStyleWord(true);
		textArea_diagnostico.setLineWrap(true);
		textArea_diagnostico.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		scrollPane.setViewportView(textArea_diagnostico);
		
		JLabel lblDiagnostico = new JLabel("DIAGNOSTICO");
		lblDiagnostico.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblDiagnostico.setBounds((int)((frame_width_size * 17.1) / 100), (int)((frame_height_size * 10.67) / 100), (int)((frame_width_size * 7.1) / 100) + 7, (int)((frame_height_size * 3.4) / 100));
		contentPane.add(lblDiagnostico);
		
		button_right = new JButton(">");
		button_right.setFont(new Font("Lucida Grande", Font.PLAIN, 6));
		button_right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().isEmpty()) {
					try {
						UpdateDNadd();
					} catch (NumberFormatException | SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Seleccione un paciente primero");
				}
			}
		});
		button_right.setBounds((int)((frame_width_size * 32.54) / 100), (int)((frame_height_size * 12.27) / 100) - 4, (int)((frame_width_size * 1.52) / 100)+8, (int)((frame_height_size * 2.03) / 100) + 7);
		contentPane.add(button_right);
		
		button_left = new JButton("<");
		button_left.setFont(new Font("Lucida Grande", Font.PLAIN, 6));
		button_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UpdateDNsubstract();
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_left.setBounds((int)((frame_width_size * 28.28) / 100)-8, (int)((frame_height_size * 12.27) / 100) - 4, (int)((frame_width_size * 1.52) / 100)+8, (int)((frame_height_size * 2.03) / 100) + 7);
		contentPane.add(button_left);
		button_left.setVisible(false);
		
		textField_Ndiagnostico = new JTextField();
		textField_Ndiagnostico.setEditable(false);
		textField_Ndiagnostico.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Ndiagnostico.setText("1/4");
		textField_Ndiagnostico.setBounds((int)((frame_width_size * 29.98) / 100), (int)((frame_height_size * 11.63) / 100), (int)((frame_width_size * 2.43) / 100), (int)((frame_height_size * 2.77) / 100));
		contentPane.add(textField_Ndiagnostico);
		textField_Ndiagnostico.setColumns(10);
		
		JLabel lblProximaCita = new JLabel("PROXIMA CITA");
		lblProximaCita.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblProximaCita.setHorizontalAlignment(SwingConstants.CENTER);
		lblProximaCita.setBounds((int)((frame_width_size * 6.08) / 100), (int)((frame_height_size * 65.74) / 100), (int)((frame_width_size * 5.96) / 100)+10, (int)((frame_height_size * 1.7) / 100));
		contentPane.add(lblProximaCita);
		
		JLabel lblCuidados = new JLabel("ANTECEDENTES");
		lblCuidados.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblCuidados.setBounds((int)((frame_width_size * 6.27) / 100), (int)((frame_height_size * 74.92) / 100), (int)((frame_width_size * 6.08) / 100)+17, (int)((frame_height_size * 1.71) / 100));
		contentPane.add(lblCuidados);
		
		table_odontograma = new JTable(17, 4) {
			
			private static final long serialVersionUID = 1L;
			
			//generar el odontograma con los colores especificos
			//inspirado de: 
			public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int colIndex) {
				Component component = super.prepareRenderer(renderer, rowIndex, colIndex);

				Color blue_color = new Color(73, 168, 242);
				Color color_salmon = new Color(255, 148, 94);


				if (colIndex == 3 && rowIndex == 8 || colIndex == 1 && rowIndex == 8) { 
					component.setBackground(blue_color);
				} else {
					component.setBackground(Color.white);
				}

				for (int i = 0; i < 17; i++) {
					if (colIndex == 0 && i < 8 || colIndex == 0 && i > 8) {
						component.setBackground(color_salmon);
					}

					if (colIndex == 2 && i < 8 || colIndex == 2 && i > 8) {
						component.setBackground(color_salmon);
					} 
				}

				if (colIndex == 0 && rowIndex == 8 || colIndex == 2 && rowIndex == 8) {
					component.setBackground(blue_color);
				}

				return component;
			}
		};
		
		table_odontograma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				table_odontograma.clearSelection();
			}
		});
		
		table_odontograma.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				table_odontograma.clearSelection();
			}
		});
		
		table_odontograma.setShowGrid(true);
		table_odontograma.setGridColor(Color.BLACK);
		table_odontograma.setBounds((int)((frame_width_size * 48.66) / 100), (int)((frame_height_size * 41.52) / 100) - 10, (int)((frame_width_size * 39.84) / 100), (int)((frame_height_size * 48.77) / 100) + 40);
		contentPane.add(table_odontograma);
		table_odontograma.setColumnSelectionAllowed(true);
		table_odontograma.setCellSelectionEnabled(true);
		table_odontograma.getColumnModel().getColumn(0).setPreferredWidth(35);
		table_odontograma.getColumnModel().getColumn(2).setPreferredWidth(30);
		table_odontograma.getColumnModel().getColumn(1).setPreferredWidth(310);
		table_odontograma.getColumnModel().getColumn(3).setPreferredWidth(292);
		table_odontograma.setRowHeight(23);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table_odontograma.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table_odontograma.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
			for (int j = 1; j < 9; j++) {
				table_odontograma.setValueAt("1." + j, j - 1, 0);
				table_odontograma.setValueAt("4." + j, j + 8, 0);
				table_odontograma.setValueAt("2." + j, j - 1, 2);
				table_odontograma.setValueAt("3." + j, j + 8, 2);
			}

		JLabel lblOdnontograma = new JLabel("ODONTOGRAMA");
		lblOdnontograma.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblOdnontograma.setBounds((int)((frame_width_size * 64.9) / 100), (int)((frame_height_size * 38.1) / 100)-8, (int)((frame_width_size * 9) / 100), (int)((frame_height_size * 1.81) / 100));
		contentPane.add(lblOdnontograma);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds((int)((frame_width_size * 48.66) / 100), (int)((frame_height_size * 15.47) / 100), (int)((frame_width_size * 39.84) / 100), (int)((frame_height_size * 20.49) / 100));
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable(){
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table_1.setRowSelectionAllowed(false);
		table_1.setGridColor(Color.BLACK);
		table_1.setBackground(Color.WHITE);
		
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (textField.getText().equals("") == false) {
					saveToArray();
					Main.saveCitaData(textField_prox_cita.getText(), textField_cuidados.getText(), Integer.parseInt(textField.getText()));
					finalSaveDiagnosticos();
					guardarOdontograma();
					}
					
					while(current_expediente != 1) {
						UpdateDNsubstract();
					}
					
					patientClick();
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		ProgramaMama.refreshTable(table_1);
		table_1.getColumnModel().getColumn(2).setHeaderValue("fecha de nacimiento");
		table_1.setRowHeight(25);
		resizeColumns();
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblBuscarPaciente = new JLabel("Buscar Paciente");
		lblBuscarPaciente.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblBuscarPaciente.setBounds((int)((frame_width_size * 54.93) / 100), (int)((frame_height_size * 12.49) / 100), (int)((frame_width_size * 8.2) / 100), (int)((frame_height_size * 1.71) / 100));
		contentPane.add(lblBuscarPaciente);
		
		textField_busqueda = new JTextField();
		textField_busqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (radioBtnNombre.isSelected()) {
					try {
						ProgramaMama.lookFor(textField_busqueda, table_1, 0);
						resizeColumns();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (radioBtnNacimiento.isSelected()) {
					try {
						ProgramaMama.lookFor(textField_busqueda, table_1, 1);
						resizeColumns();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		});
		textField_busqueda.setBounds((int)((frame_width_size * 63.5) / 100), (int)((frame_height_size * 11.95) / 100), (int)((frame_width_size * 13.69) / 100), (int)((frame_height_size * 2.99) / 100));
		contentPane.add(textField_busqueda);
		textField_busqueda.setColumns(10);
		
		JButton btnBack = new JButton("<");
		btnBack.setForeground(new Color(255, 160, 122));
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField.getText().equals("") == false) {
						saveToArray();
						Main.saveCitaData(textField_prox_cita.getText(), textField_cuidados.getText(), Integer.parseInt(textField.getText()));
						finalSaveDiagnosticos();
						guardarOdontograma();
					}
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}
				dispose();
				ProgramaMama.getFrmExpodent().setVisible(true);	
			}
		});
		btnBack.setBounds((int)((frame_width_size * 2.13) / 100), (int)((frame_height_size * 2.03) / 100), (int)((frame_width_size * 3.65) / 100), (int)((frame_height_size * 6.08) / 100));
		contentPane.add(btnBack);
		
		textField_prox_cita = new JTextField();
		textField_prox_cita.setBounds((int)((frame_width_size * 6.08) / 100), (int)((frame_height_size * 67.34) / 100), (int)((frame_width_size * 28.47) / 100), (int)((frame_height_size * 5.34) / 100));
		contentPane.add(textField_prox_cita);
		textField_prox_cita.setColumns(10);
		
		textField_cuidados = new JTextField();
		textField_cuidados.setBounds((int)((frame_width_size * 6.08) / 100), (int)((frame_height_size * 76.84) / 100), (int)((frame_width_size * 28.47) / 100), (int)((frame_height_size * 6.08) / 100));
		contentPane.add(textField_cuidados);
		textField_cuidados.setColumns(10);
		
		JButton btnCrear = new JButton("GUARDAR");
		btnCrear.setBackground(Color.WHITE);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField.getText().equals("")) {
						JOptionPane.showMessageDialog(contentPane, "Seleccione un paciente primero");
					} else {
						saveToArray();
						Main.saveCitaData(textField_prox_cita.getText(), textField_cuidados.getText(), Integer.parseInt(textField.getText()));
						finalSaveDiagnosticos();
						guardarOdontograma();
						JOptionPane.showMessageDialog(contentPane, "¡Se guardo exitosamente!");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCrear.setBounds((int)((frame_width_size * 13.87) / 100), (int)((frame_height_size * 85.91) / 100) - 10, (int)((frame_width_size * 10.28) / 100), (int)((frame_height_size * 6.72) / 100));
		contentPane.add(btnCrear);
		textField = new JTextField(0);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		textField.setEditable(false);
		textField.setBounds((int)((frame_width_size * 91.18) / 100), (int)((frame_height_size * 4.7) / 100), (int)((frame_width_size * 3.35) / 100), (int)((frame_height_size * 4.27) / 100));
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblPaciente = new JLabel("# paciente");
		lblPaciente.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblPaciente.setBounds((int)((frame_width_size * 85.82) / 100), (int)((frame_height_size * 5.44) / 100), (int)((frame_width_size * 4.62) / 100)+5, (int)((frame_height_size * 2.77) / 100));
		contentPane.add(lblPaciente);
		
		radioBtnNombre = new JRadioButton("Nombre");
		radioBtnNombre.setBackground(getForeground());
		radioBtnNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioBtnNacimiento.setSelected(false);
			}
		});
		radioBtnNombre.setSelected(true);
		radioBtnNombre.setBounds((int)((frame_width_size * 78.28) / 100), (int)((frame_height_size * 10.46) / 100), (int)((frame_width_size * 8.58) / 100), (int)((frame_height_size * 2.45) / 100));
		contentPane.add(radioBtnNombre);
		
		radioBtnNacimiento = new JRadioButton("Fecha Nacimiento");
		radioBtnNacimiento.setBackground(getForeground());
		radioBtnNacimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					radioBtnNombre.setSelected(false);
			}
		});
		radioBtnNacimiento.setBounds((int)((frame_width_size * 78.28) / 100), (int)((frame_height_size * 12.8) / 100), (int)((frame_width_size * 9) / 100), (int)((frame_height_size * 2.45) / 100));
		contentPane.add(radioBtnNacimiento);
		
		
		ButtonGroup botones = new ButtonGroup();
		botones.add(radioBtnNombre);
		botones.add(radioBtnNacimiento);
		
		JButton btnReceta = new JButton("RECETA");
		btnReceta.setBackground(Color.WHITE);
		btnReceta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Seleccione un paciente primero");
				} else {
					try {
						crearReceta();
					} catch (NumberFormatException | IOException | SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnReceta.setBounds((int)((frame_width_size * 15.45) / 100), (int)((frame_height_size * 93.92) / 100) - 15, (int)((frame_width_size * 7.12) / 100), (int)((frame_height_size * 3.1) / 100));
		contentPane.add(btnReceta);
	}
	
	/**
	 * metodo que maneja los botones botones para cambiar 
	 * de diagnostico, este metodo se encarga de manejar
	 * el boton derecho
	 */
	private void UpdateDNadd() throws NumberFormatException, SQLException {
		if(current_expediente < 4) {
			saveToArray();
			current_expediente++;
			textField_Ndiagnostico.setText(current_expediente + "/4");
			textArea_diagnostico.setText(getDiagnosticoI());
			if (current_expediente - 1 == 1) {
				button_left.setVisible(true);
			}
		}
		
		if (current_expediente == 4) {
			button_right.setVisible(false);
		}
	}
	
	
	/**
	 * metodo que maneja los botones botones para cambiar 
	 * de diagnostico, este metodo se encarga de manejar
	 * el boton izquierdo
	 */
	private void UpdateDNsubstract() throws NumberFormatException, SQLException {
		if (current_expediente > 1) {
			saveToArray();
			current_expediente--;
			textField_Ndiagnostico.setText(current_expediente + "/4");
			textArea_diagnostico.setText(getDiagnosticoI());
			if (current_expediente == 1) {
				button_left.setVisible(false);
			}
		}
		
		if (current_expediente != 4) {
			button_right.setVisible(true);
		}
	}
	
	
	/**
	 * metodo que se encarga de cambiar la informacion de la pagina
	 * a la informacion del paciente seleccionado
	 */
	private void patientClick() throws NumberFormatException, SQLException {
		int i = table_1.getSelectedRow();
		TableModel model = table_1.getModel();
		textField.setText(model.getValueAt(i, 0).toString());
		diagnosticos.clear();
		inicializarExpediente();
		actualizarOdontograma();
		textArea_diagnostico.setText(getDiagnosticoI());
		textField_prox_cita.setText(getCita());
		textField_cuidados.setText(getAntecedentes());
	}
	
	/**
	 * metodo encargado de guardar el odontogrma del paciente a la
	 * base de datos
	 */
	private void finalSaveDiagnosticos() throws NumberFormatException, SQLException {	
		for (int i = 1; i < 5; i++) {
			Main.diagnosticInsert(diagnosticos.get(i - 1), i, Integer.parseInt(textField.getText()));
		}
	}
	
	private String getDiagnosticoI() throws NumberFormatException, SQLException {
		return diagnosticos.get(current_expediente - 1);
	}
	
	private String getCita() throws NumberFormatException, SQLException {
		return Main.Citas(Integer.parseInt(textField.getText()));
	}
	
	private String getAntecedentes() throws NumberFormatException, SQLException {
		return Main.Antecedentes(Integer.parseInt(textField.getText()));
	}
	
	/**
	 * metodo para guardar en el ArrayList los expedientes antes de borrarlos
	 */
	private void saveToArray() {
			diagnosticos.set(current_expediente - 1, textArea_diagnostico.getText());	
	}
	
	/**
	 * metodo para inicializar el expediente llenando el ArrayList con la informacion
	 * previa del paciente
	 */
	private void inicializarExpediente() throws NumberFormatException, SQLException {
		for(int i = 1; i < 5; i++) {
			diagnosticos.add(Main.diagnostic(i, Integer.parseInt(textField.getText())));
		}
	}
	
	/**
	 * metodo para actualizar y llenar el odontograma con la informacion del paciente
	 */
	private void actualizarOdontograma() throws NumberFormatException, SQLException {
		ResultSet rs = Main.getOdontograma(Integer.parseInt(textField.getText()));
		rs.next();
		String nombreColumna = " ";
		for (int j = 1; j < 9; j++) {
			nombreColumna = "1." + j;
			table_odontograma.setValueAt(rs.getString(nombreColumna), j - 1, 1);
			nombreColumna = "4." + j;
			table_odontograma.setValueAt(rs.getString(nombreColumna), j + 8, 1);
			nombreColumna = "2." + j;
			table_odontograma.setValueAt(rs.getString(nombreColumna), j - 1, 3);
			nombreColumna = "3." + j;
			table_odontograma.setValueAt(rs.getString(nombreColumna), j + 8, 3);
		}
	}
	
	/**
	 * metodo par guardar el odontorgrama editado en la base de datos
	 */
	private void guardarOdontograma() throws SQLException{
		if (table_odontograma.isEditing() == true) {
			table_odontograma.getCellEditor().stopCellEditing();
		}
		table_odontograma.getSelectionModel().clearSelection();
		int curPac = Integer.parseInt(textField.getText());
		String columna = " ";
		String insertar = " ";
		
		for (int j = 1; j < 9; j++) {
			insertar = checarNulo((String) table_odontograma.getValueAt(j - 1, 1));
			columna = "1." + j;
			Main.guardarOdontograma(columna, curPac, insertar);
			
			insertar = checarNulo((String)table_odontograma.getValueAt(j + 8, 1));
			columna = "4." + j;
			Main.guardarOdontograma(columna, curPac, insertar);
			
			insertar = checarNulo((String)table_odontograma.getValueAt(j - 1, 3));
			columna = "2." + j;
			Main.guardarOdontograma(columna, curPac, insertar);
			
			insertar = checarNulo((String)table_odontograma.getValueAt(j + 8, 3));
			columna = "3." + j;
			Main.guardarOdontograma(columna, curPac, insertar);
		}
	}
	
	private String checarNulo(String checar) {
		if (checar == null) {
			return " ";
		}
		return checar;
	}
	
	public void resizeColumns() {
		table_1.getColumnModel().getColumn(2).setHeaderValue("fecha de nacimiento");
		table_1.getColumnModel().getColumn(0).setPreferredWidth(34);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(126);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(96);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(20);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(96);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(126);
	}
	
	
	/**
	 * metodo para crear la receta de paciente sleccionando, llenando la fecha y 
	 * poniendo el nombre del paciente directamente en la receta
	 */
	public void crearReceta() throws IOException, NumberFormatException, SQLException {
		Ventana_receta receta = new Ventana_receta();
		
		int i = table_1.getSelectedRow();
		TableModel model = table_1.getModel();
		
		receta.getTextFieldNombre().setText(model.getValueAt(i, 1).toString());
		Date fecha = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		receta.getTextFieldFecha().setText(formatter.format(fecha));
		
		saveToArray();
		Main.saveCitaData(textField_prox_cita.getText(), textField_cuidados.getText(), Integer.parseInt(textField.getText()));
		finalSaveDiagnosticos();
		guardarOdontograma();
		
		receta.setVisible(true);
		this.dispose();
		
	}
	
	
	class JTextFIeldLimit extends PlainDocument {
		private int limit;
		JTextFIeldLimit(int limit) {
			super();
			this.limit = limit;
		}
		
		JTextFIeldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}
		
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;
			
			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
}
