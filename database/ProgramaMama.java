/**
 * @author Diego Gutiérrez
 */

package database;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

import com.mysql.cj.xdevapi.Table;

import net.proteanit.sql.DbUtils;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.spi.CharsetProvider;
import java.awt.Toolkit;

public class ProgramaMama {

	private static JFrame frmExpodent;
	private JButton btn_registro;
	private JButton btnExpediente;
	private JButton btnReceta;
	private JButton btnRespaldo;
	//private static String path= "/Users/diegogutierrez/Desktop/Images/";
	private static String path= "C:/Program Files (x86)/GTDent/Images/";
	Toolkit toolkit =  Toolkit.getDefaultToolkit();
	private Dimension screenSize = toolkit.getScreenSize();
	private double width = screenSize.width * 1.72;
	private double height = screenSize.height * 1.72;
	private int frame_width_size = (int)((width * 43.23) / 100) + 10;
	private int frame_height_size = (int)((height * 40.61) / 100) + 45;
	private int button_height = (int)((frame_height_size * 49.25) / 100);
	private int button_width = (int)((frame_width_size * 21.69) / 100);
	private int button_y = (int)((frame_height_size * 27.36) / 100);
	
 	 /**
	 *Se lanza la aplicacion
	 */
	public static void main(String[] args) throws Exception{
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					ProgramaMama window = new ProgramaMama();
					window.getFrmExpodent().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Se crea la ventana incializando
	 * la ventana con sus componentes
	 * @throws SQLException 
	 */
	public ProgramaMama() throws SQLException {
		Main.getConnection();
		Main.reseedValuesSequence();
		initialize();
	}

	/**
	 * Inicializar los contenidos the contents de la pantalla.
	 */
	private void initialize() {
		setFrmExpodent(new JFrame());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		getFrmExpodent().setBackground(new Color(253, 245, 230));
		//getFrmExpodent().setContentPane(new JLabel(new ImageIcon(path + "Pantalla Principal.png")));
		getFrmExpodent().getContentPane().setBackground(Color.WHITE);
		getFrmExpodent().setFont(new Font("Apple Braille", Font.PLAIN, 14));
		getFrmExpodent().setResizable(false);
		getFrmExpodent().setTitle("GT DENT");
		getFrmExpodent().setBounds(100, 100, frame_width_size, frame_height_size);
		ImageIcon icon = new ImageIcon(path + "Pantalla Principal.png");  
		Image newimg = icon.getImage().getScaledInstance(frame_width_size, frame_height_size, java.awt.Image.SCALE_SMOOTH);
		getFrmExpodent().setContentPane(new JLabel(new ImageIcon(newimg)));
		getFrmExpodent().setLocation(dim.width/2 - getFrmExpodent().getSize().width/2, dim.height/2- getFrmExpodent().getSize().height/2);
		getFrmExpodent().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmExpodent().getContentPane().setLayout(null);
		
	
		btn_registro = new JButton("");
		btn_registro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//se esconde la pantalla una vez que
					//se hace clic en algun boton
					getFrmExpodent().setVisible(false);
					//se genera un objeto de Ventana_registro
					//y se hace visible
					new Ventana_registro().setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		});
		btn_registro.setBounds((int)((frame_width_size * 9.64) / 100), button_y, button_width, button_height);
		ImageIcon icon_registro = new ImageIcon(path + "btn_registro bien.png");  
		Image newimg_registro = icon_registro.getImage().getScaledInstance(button_width, button_height, java.awt.Image.SCALE_SMOOTH);
		btn_registro.setIcon(new ImageIcon(newimg_registro));
		getFrmExpodent().getContentPane().add(btn_registro);
	
		
		btnExpediente = new JButton("");
		//btnExpediente.setIcon(new ImageIcon(path + "btn_exp bien.png"));
		btnExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFrmExpodent().setVisible(false);
				try {
					new Ventana_expediente().setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		btnExpediente.setBounds((int)((frame_width_size * 38.55) / 100), button_y, button_width, button_height);
		ImageIcon icon_exp = new ImageIcon(path + "btn_exp bien.png");  
		Image newimg_exp = icon_exp.getImage().getScaledInstance(button_width, button_height, java.awt.Image.SCALE_SMOOTH);
		btnExpediente.setIcon(new ImageIcon(newimg_exp));
		getFrmExpodent().getContentPane().add(btnExpediente);
		
		
		btnReceta = new JButton("");
		btnReceta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFrmExpodent().setVisible(false);
				try {
					new Ventana_receta().setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReceta.setBounds((int)((frame_width_size * 67.47) / 100), button_y, button_width, button_height);
		ImageIcon icon_receta = new ImageIcon(path + "btn_receta bien.png");  
		Image newimg_receta = icon_receta.getImage().getScaledInstance(button_width, button_height, java.awt.Image.SCALE_SMOOTH);
		btnReceta.setIcon(new ImageIcon(newimg_receta));
		getFrmExpodent().getContentPane().add(btnReceta);
		
		
		
		btnRespaldo = new JButton("");
		btnRespaldo.addActionListener(new ActionListener() {
			// Accionar el metodo para respaldar la informacion
			public void actionPerformed(ActionEvent e) {
				try {
					Main.respaldar();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRespaldo.setBounds((int)((frame_width_size * 38.55) / 100), (int)((frame_height_size * 84.82) / 100), (int)((frame_width_size * 24.1) / 100), (int)((frame_height_size * 10.94) / 100));
		ImageIcon icon_respaldo = new ImageIcon(path + "btn_respaldo bien.png");  
		Image newimg_respaldo = icon_respaldo.getImage().getScaledInstance(btnRespaldo.getWidth(), btnRespaldo.getHeight(), java.awt.Image.SCALE_SMOOTH);
		btnRespaldo.setIcon(new ImageIcon(newimg_respaldo));
		getFrmExpodent().getContentPane().add(btnRespaldo);
		
		
		frmExpodent.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	try {
					Main.connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
}

	/**
	 * metodo que llena una JTable con todos los resultados de la base de datos
	 * utilizando los Queries de la clase Main y aplicando los resultsets a la JTable
	 */
	public static void refreshTable(JTable tm) throws SQLException {
		ResultSet rs = Main.getLines();
		tm.setModel(DbUtils.resultSetToTableModel(rs));
	}
	
	/**
	 * metodo responsable de la busqueda de pacientes mediante su nombre o fecha
	 * de nacimiento utilizando como entrada la Tabla de pacientes, la seleccion de
	 * radio Button y el cuadro de texto de busqueda
	 * 
	 * Dependiendo de la seleccion se actualiza la JTable con el contenido de busqueda
	 */
	public static void lookFor(JTextField tx, JTable tm, int selection) throws SQLException {
		String contenido = tx.getText();
		
		if (selection == 0) {
			if (tx.getText().equals("") == true) {
				refreshTable(tm);
			} else {
				ResultSet rs = Main.refresh(contenido);
				tm.setModel(DbUtils.resultSetToTableModel(rs));
			}
		} else if (selection == 1) {
			if(tx.getText().equals("") == true) {
				refreshTable(tm);
			} else {
				ResultSet rs = Main.refreshConFecha(contenido);
				tm.setModel(DbUtils.resultSetToTableModel(rs));
			}
		}
	}
	
	
	public static JFrame getFrmExpodent() {
		return frmExpodent;
	}

	public void setFrmExpodent(JFrame frmExpodent) {
		ProgramaMama.frmExpodent = frmExpodent;
	}
}