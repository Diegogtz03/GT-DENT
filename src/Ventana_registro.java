package database;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Window.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.SwingConstants;
import java.awt.Canvas;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JRadioButton;

public class Ventana_registro extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_search;
	private JTextField textField_nombre;
	private JTextField textField_nacimiento;
	private JTextField textField_edad;
	private JTextField textField_correo;
	private JTextField textField_telefono;
	private JRadioButton radioBtnFecha;
	private JRadioButton radioBtnNombre;
	private JTable table;
	//private static String path = "/Users/diegogutierrez/Desktop/TEC 5/Info III/Imagenes Programa/";
	private static String path = "C:/Program Files (x86)/GTDent/Images/";
	
	Toolkit toolkit =  Toolkit.getDefaultToolkit();
	private Dimension screenSize = toolkit.getScreenSize();
	private double width = screenSize.width * 1.72;
	private double height = screenSize.height * 1.72;
	private int frame_width_size = (int)((width * 57.08) / 100);
	private int frame_height_size = (int)((height * 52.05) / 100) + 30;
	private int search_box_w = (int)((frame_width_size * 13.26) / 100);
	private int search_box_h = (int)((frame_height_size * 4.8) / 100);
	private int btn_w = (int)((frame_width_size * 7.6) / 100);
	private int btn_h = (int)((frame_height_size * 7.04) / 100);

	/**
	 * Lanzar la aplicacion
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//se genera un objeto de Ventana_registro
					Ventana_registro frame = new Ventana_registro();
					//se hace visible una vez creado
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crear la pantalla con su respectiva informacion y botones
	 */
	public Ventana_registro() throws SQLException, IOException {
		setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		setBackground(Color.LIGHT_GRAY);
		setBackground(Color.LIGHT_GRAY);
		setTitle("REGISTRO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, frame_width_size, frame_height_size);
		setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);
		contentPane = new JPanel() {

			private static final long serialVersionUID = 1L;

			public void paintComponent( Graphics g ) {
				  super.paintComponent(g);
				  Graphics2D g2d = (Graphics2D) g;
				  try {
					  InputStream image = new FileInputStream(path + "fondo 3.png");
					  BufferedImage src;
					  try {
						src = (BufferedImage) ImageIO.read(image);
						g2d.drawImage(src, 0, 0, frame_width_size, frame_height_size, null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};

		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblNombre.setBounds((int)((frame_width_size * 8.33) / 100), (int)((frame_height_size * 27.85) / 100), (int)((frame_width_size * 4.68) / 100), (int)((frame_height_size * 1.92) / 100));
		contentPane.add(lblNombre);
		
		JScrollPane scrollPane_registro = new JScrollPane();
		scrollPane_registro.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY, Color.WHITE));
		scrollPane_registro.setBounds((int)((frame_width_size * 39.9) / 100), (int)((frame_height_size * 18.78) / 100), (int)((frame_width_size * 58.21) / 100), (int)((frame_height_size * 63.1) / 100));
		contentPane.add(scrollPane_registro);
		
		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table.setBackground(Color.WHITE);
		table.setRowSelectionAllowed(false);
		table.setGridColor(Color.BLACK);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patientClick();
			}
		});
		//cargar el contenido de la base de datos a la tabla
		ProgramaMama.refreshTable(table);
		table.setRowHeight(25);
		resizeColumns();
		//hacer la tabla visible
		scrollPane_registro.setViewportView(table);
		
		JLabel lblBuscar = new JLabel("Buscar Paciente");
		lblBuscar.setFont(new Font("Apple Braille", Font.PLAIN, 14));
		lblBuscar.setBounds((int)((frame_width_size * 50.3) / 100), (int)((frame_height_size * 15.05) / 100), (int)((frame_width_size * 7.73) / 100), (int)((frame_height_size * 1.92) / 100));
		contentPane.add(lblBuscar);
		
		textField_search = new RoundJTextField(15);
		textField_search.addKeyListener(new KeyAdapter() {
			@Override
			// lanza busqueda por paciente
			public void keyTyped(KeyEvent e) {
				if (radioBtnNombre.isSelected()) {
					try {
						ProgramaMama.lookFor(textField_search, table, 0);
						resizeColumns();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (radioBtnFecha.isSelected()) {
					try {
						ProgramaMama.lookFor(textField_search, table, 1);
						resizeColumns();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		textField_search.setBounds((int)((frame_width_size * 58.39) / 100), (int)((frame_height_size * 13.45) / 100), (int)((frame_width_size * 28.89) / 100), search_box_h);
		contentPane.add(textField_search);
		textField_search.setColumns(10);
		
		JLabel lblPaciente = new JLabel("PACIENTE");
		lblPaciente.setForeground(new Color(0, 0, 0));
		lblPaciente.setFont(new Font("Mishafi Gold", Font.PLAIN, 30));
		lblPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaciente.setBounds((int)((frame_width_size * 12.89) / 100), (int)((frame_height_size * 15.05) / 100), (int)((frame_width_size * 11.5) / 100), (int)((frame_height_size * 5.34) / 100));
		contentPane.add(lblPaciente);
		
		textField_nombre = new RoundJTextField(15);
		textField_nombre.setBackground(new Color(255, 235, 205));
		textField_nombre.setBounds((int)((frame_width_size * 13.75) / 100), (int)((frame_height_size * 26.25) / 100), search_box_w, search_box_h);
		contentPane.add(textField_nombre);
		textField_nombre.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Fecha de Nacimiento");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		lblNewLabel.setBounds((int)((frame_width_size * 4.01) / 100), (int)((frame_height_size * 33.4) / 100), (int)((frame_width_size * 9.43) / 100), (int)((frame_height_size * 4.80) / 100));
		contentPane.add(lblNewLabel);
		
		textField_nacimiento = new RoundJTextField(15);
		textField_nacimiento.setBackground(new Color(255, 235, 205));
		textField_nacimiento.setBounds((int)((frame_width_size * 13.75) / 100), (int)((frame_height_size * 33.51) / 100), search_box_w, search_box_h);
		contentPane.add(textField_nacimiento);
		textField_nacimiento.setColumns(10);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblEdad.setBounds((int)((frame_width_size * 9.12) / 100), (int)((frame_height_size * 42.48) / 100), (int)((frame_width_size * 3.71) / 100), (int)((frame_height_size * 1.81) / 100));
		contentPane.add(lblEdad);
		
		textField_edad = new RoundJTextField(15);
		textField_edad.setBackground(new Color(255, 235, 205));
		textField_edad.setBounds((int)((frame_width_size * 13.67) / 100), (int)((frame_height_size * 40.88) / 100), search_box_w, search_box_h);
		contentPane.add(textField_edad);
		textField_edad.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Correo");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_1.setBounds((int)((frame_width_size * 8.52) / 100), (int)((frame_height_size * 49.95) / 100), (int)((frame_width_size * 3.71) / 100) + 15, (int)((frame_height_size * 1.81) / 100));
		contentPane.add(lblNewLabel_1);
		
		textField_correo = new RoundJTextField(15);
		textField_correo.setBackground(new Color(255, 235, 205));
		textField_correo.setBounds((int)((frame_width_size * 13.56) / 100), (int)((frame_height_size * 48.77) / 100), search_box_w, search_box_h);
		contentPane.add(textField_correo);
		textField_correo.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblTelefono.setBounds((int)((frame_width_size * 7.48) / 100), (int)((frame_height_size * 58.27) / 100), (int)((frame_width_size * 4.68) / 100) + 10, (int)((frame_height_size * 1.8) / 100));
		contentPane.add(lblTelefono);
		
		textField_telefono = new RoundJTextField(15);
		textField_telefono.setBackground(new Color(255, 235, 205));
		textField_telefono.setBounds((int)((frame_width_size * 13.50) / 100), (int)((frame_height_size * 56.88) / 100), search_box_w, search_box_h);
		contentPane.add(textField_telefono);
		textField_telefono.setColumns(10);
		
		JButton btnRegistar = new JButton("REGISTRAR");
		btnRegistar.setBackground(Color.WHITE);
		btnRegistar.setBackground(UIManager.getColor("Button.background"));
		btnRegistar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						insertarPaciente();
					} catch (Exception e1) {
				}
			}
		});
		btnRegistar.setBounds((int)((frame_width_size * 4.44) / 100), (int)((frame_height_size * 68.41) / 100), btn_w, btn_h);
		contentPane.add(btnRegistar);
		
		JButton btnAcrualizar = new JButton("ACTUALIZAR");
		btnAcrualizar.setBackground(Color.WHITE);
		btnAcrualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//se valida que un paciente ya este seleccionado
				//para no generar ningun error
				if (!table.getSelectionModel().isSelectionEmpty()) {
					try {
						TableModel model = table.getModel();
						int i = table.getSelectedRow();
						int ii = (int) model.getValueAt(i, 0);
						patientUpdate(Integer.toString(ii));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Seleccione un paciente primero");
				}
			}
		});
		btnAcrualizar.setBounds((int)((frame_width_size * 15.69) / 100), (int)((frame_height_size * 68.41) / 100), btn_w+5, btn_h);
		contentPane.add(btnAcrualizar);
		
		JButton btnNuevo = new JButton("NUEVO");
		btnNuevo.setBackground(Color.WHITE);
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NuevoPaciente();
			}
		});
		btnNuevo.setBounds((int)((frame_width_size * 26.82) / 100), (int)((frame_height_size * 68.41) / 100), btn_w, btn_h);
		contentPane.add(btnNuevo);
		
		JButton btnNewButton = new JButton("<");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton.setForeground(new Color(255, 160, 122));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ProgramaMama.getFrmExpodent().setVisible(true);
			}
		});
		btnNewButton.setBounds((int)((frame_width_size * 2.13) / 100), (int)((frame_height_size * 2.45) / 100), (int)((frame_width_size * 3.71) / 100), (int)((frame_height_size * 6.4) / 100));
		contentPane.add(btnNewButton);
		
		JLabel lblPacientesRegistrados = new JLabel("PACIENTES REGISTRADOS");
		lblPacientesRegistrados.setForeground(new Color(255, 255, 224));
		lblPacientesRegistrados.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblPacientesRegistrados.setBounds((int)((frame_width_size * 60.1) / 100), (int)((frame_height_size * 5.12) / 100)+8, (int)((frame_width_size * 25.42) / 100), btn_h);
		contentPane.add(lblPacientesRegistrados);
		
		radioBtnNombre = new JRadioButton("Nombre");
		radioBtnNombre.setBackground(getForeground());
		radioBtnNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioBtnFecha.setSelected(false);
			}
		});
		radioBtnNombre.setSelected(true);
		radioBtnNombre.setBounds((int)((frame_width_size * 88.38) / 100), (int)((frame_height_size * 12.81) / 100), (int)((frame_width_size * 8.58) / 100), (int)((frame_height_size * 2.45) / 100));
		contentPane.add(radioBtnNombre);
		
		radioBtnFecha = new JRadioButton("Fecha Nacimiento");
		radioBtnFecha.setBackground(getForeground());
		radioBtnFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioBtnNombre.setSelected(false);	
			}
		});
		radioBtnFecha.setBounds((int)((frame_width_size * 88.38) / 100), (int)((frame_height_size * 15.8) / 100), (int)((frame_width_size * 9.73) / 100), (int)((frame_height_size * 2.45) / 100));
		contentPane.add(radioBtnFecha);
		
		ButtonGroup botones = new ButtonGroup();
		botones.add(radioBtnNombre);
		botones.add(radioBtnFecha);		
	}
	
	/**
	 * metodo que inserta un nuevo paciente a la base de datos
	 * revisando que no halla un paciente repetido en la base
	 * de datos
	 */
	private void insertarPaciente() throws SQLException {
		String name = textField_nombre.getText();
		String fNacimiento = textField_nacimiento.getText();
		String edad = textField_edad.getText();
		String correo = textField_correo.getText();
		String tel = textField_telefono.getText();
		boolean validacion = validacion(name, fNacimiento, edad, correo, tel);
		boolean validacion2 = true;
		
		if (!validacion) {
			validacion2 = revisarRepetido();
		}
		if (validacion == false && validacion2 == false) {
			NuevoPaciente();
			Main.createLine(name, fNacimiento, edad, correo, tel);
			ProgramaMama.refreshTable(table);
			resizeColumns();
		}
	}
	
	/**
	 * metodo que actualiza la informacion del paciente
	 * utilizando como entrada su id para hacer el cambio
	 * directo en la base de datos
	 */
	private void patientUpdate(String id) throws SQLException {
		String name = textField_nombre.getText();
		String fNacimiento = textField_nacimiento.getText();
		String edad = textField_edad.getText();
		String correo = textField_correo.getText();
		String tel = textField_telefono.getText();
		
		boolean validacion = validacion(name, fNacimiento, edad, correo, tel);
		
		if(validacion == false) {
		NuevoPaciente();
		Main.updatePatient(name, fNacimiento, edad, correo, tel, id);
		ProgramaMama.refreshTable(table);
		resizeColumns();
		}
	}
	
	/**
	 * metodo que actualiza los TextField con los datos del paciente
	 * cuando se hace click sobre el paciente en la tabla
	 */
	private void patientClick() {
		int i = table.getSelectedRow();
		TableModel model = table.getModel();
		textField_nombre.setText(model.getValueAt(i, 1).toString());
		textField_nacimiento.setText(model.getValueAt(i, 2).toString());
		textField_edad.setText(model.getValueAt(i, 3).toString());
		textField_correo.setText(model.getValueAt(i, 4).toString());
		textField_telefono.setText(model.getValueAt(i, 5).toString());
	}
	
	/**
	 * metodo que borra todos los datos introducidos a
	 * los espacios de texto (TextFields)
	 */
	private void NuevoPaciente() {
		textField_nombre.setText("");
		textField_nacimiento.setText("");
		textField_edad.setText("");
		textField_correo.setText("");
		textField_telefono.setText("");
	}
	
	
	/**
	 * metodo que actualiza el tamaño de las colomnas de la tabla
	 * al tamaño preferido y evitar que se muevan
	 */
	public void resizeColumns() {
		table.getColumnModel().getColumn(2).setHeaderValue("fecha de nacimiento");
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(166);
		table.getColumnModel().getColumn(2).setPreferredWidth(186);
		table.getColumnModel().getColumn(3).setPreferredWidth(29);
		table.getColumnModel().getColumn(4).setPreferredWidth(176);
		table.getColumnModel().getColumn(4).setPreferredWidth(216);
	}
	
	
	/**
	 * metodo para la validacion de datos que se introducen en 
	 * el registro de los pacientes que regresa true cuando
	 * uno de los datos esta mal o false cuando todo esta bien
	 */
	public boolean validacion(String nombre, String fNacimiento, String edad, String correo, String telefono) {
		//variable booleana para identifica si los datos estan correctos o no al final de la validacion
		boolean validacion = false;
		
		//validacion del campo del nombre checando que no este vacio
		if(!nombre.isEmpty()) {
			for(char letra: nombre.toCharArray()) {
				if(Character.isDigit(letra)) { // se valida que el nombre no contenga numeros
					JOptionPane.showMessageDialog(contentPane, "Revise que el nombre no contenga numeros");
					validacion = true;
				}
			}
		} else { //si no esta lleno, se pide que se llene
			JOptionPane.showMessageDialog(contentPane, "Porfavor llene el nombre");
			validacion = true;
		}
		
		//validacion del campo del correo solo si el nombre no tiene errores
		if(validacion == false) {
			if(!correo.isEmpty()) {
				//se valida que el correo tenga los datos correspondientes como el @ y el .com
				if(!correo.contains("@") && !correo.contains(".com") || !correo.contains("@") && !correo.contains(".mx")) {
					int result = JOptionPane.showConfirmDialog(contentPane, "El correo puede contener errores,\n¿Desea continuar?", "alerta", JOptionPane.YES_NO_OPTION);
					//se puede dejar el correo con el error en caso de un correo que detecte errores
					if (result == JOptionPane.NO_OPTION) {
						validacion = true;
					} else if (result == JOptionPane.YES_OPTION) {
						validacion = false;
					}
				}
			} else { // si es que esta vacio se puede elegir entre dejarlo vacio o no
				int result = JOptionPane.showConfirmDialog(contentPane, "El correo está vacío,\n¿Desea continuar?", "alerta", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.NO_OPTION) {
					validacion = true;
				} else if (result == JOptionPane.YES_OPTION) {
					validacion = false;
				}
			}
		}
		
		//validacion del campo de fecha de nacimientos solo si lo anterior no tiene errores
		if(validacion == false) {
			if(fNacimiento.isEmpty()) {
				JOptionPane.showMessageDialog(contentPane, "Porfavor llene la fecha de nacimiento");
				validacion = true;
			}
			
			//se checa que la fecha quede con el formato dd/mmm/aaaa (mes con letras)
			if(fNacimiento.trim().toCharArray().length != 11 && !validacion) {
				int result = JOptionPane.showConfirmDialog(contentPane, "La fecha puede estar en otro formato al conocido,\n¿Desea continuar?", "alerta", JOptionPane.YES_NO_OPTION);
				
				//si se elige dejar el formato erroneo se puede seleccionar
				if(result == JOptionPane.NO_OPTION) {
					validacion = true;
				} else if(result == JOptionPane.YES_OPTION) {
					validacion = false;
				}
			}
		}

		//validacion del campo de la edad
		if(validacion == false) {
			//se checa que la edad no este vacia
			if(!edad.isEmpty()) {
				//se checa que la edad no este mayor de lo que deberia de ser
				if(Integer.parseInt(edad) >= 100 || Integer.parseInt(edad) <= 0) {
					int result = JOptionPane.showConfirmDialog(contentPane, "La edad puede contener errores,\n¿Desea continuar?", "alerta", JOptionPane.YES_NO_OPTION);

					if (result == JOptionPane.NO_OPTION) {
						validacion = true;
					} else if (result == JOptionPane.YES_OPTION) {
						validacion = false;
					}
				}
			} else { // se puede elegir dejar la edad vacia
				int result = JOptionPane.showConfirmDialog(contentPane, "La edad está vacía,\n¿Desea continuar?", "alerta", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.NO_OPTION) {
					validacion = true;
				} else if (result == JOptionPane.YES_OPTION) {
					validacion = false;
				}
			}
		}

		//validacion del campo de telefono que no contenga letras
		if(validacion == false) {
			if(!telefono.isEmpty()) {
				int loop = 0;
				for(char letra: telefono.toCharArray()) {
					if(!Character.isDigit(letra) && loop == 0) {
						JOptionPane.showMessageDialog(contentPane, "Revise que el telefono no tenga letras");
						validacion = true;
						loop++;
					}
				}
			} else { //validacion que no este vacio
				JOptionPane.showMessageDialog(contentPane, "Porfavor llene el telefono");
				validacion = true;
			}
		}
		return validacion;
	}
	
	
	/**
	 * metodo que revisa si un paciente no ha sido previamente
	 * registrado utilizando el correco electronico como medida
	 * de prevencion
	 */
	private boolean revisarRepetido() throws SQLException {
		boolean test = false;
		ResultSet resultado = Main.revisarCorreo(textField_correo.getText());
		if ((resultado.next()) && (!resultado.getString(4).equalsIgnoreCase(""))) {
			int result = JOptionPane.showConfirmDialog(contentPane, "Ya existe un paciente con el correo \n'" 
						+ resultado.getString("correo") +"' ¿Desea registrarlo?", "alerta", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				test = false;
			} else if (result == JOptionPane.NO_OPTION){
				test = true;
			}
		} else if (resultado.next() == false) {
			test = false;
		}
		return test;
	}
	
	/**
	 * metodo que redondea los TextFields para una mejor apariencia
	 * obtenido de: https://stackoverflow.com/a/8515677
	 */
	public class RoundJTextField extends JTextField {
		private Shape shape;
		public RoundJTextField(int size) {
			super(size);
			setOpaque(false);
		}
		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
			super.paintComponent(g);
		}
		protected void paintBorder(Graphics g) {
			g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
		}
		public boolean contains(int x, int y) {
			if (shape == null || !shape.getBounds().equals(getBounds())) {
				shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
			}
			return shape.contains(x, y);
		}
	}
}

