/**
 * @author Diego Guti�rrez
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
	private static String path = "/Users/diegogutierrez/Desktop/TEC 5/Info III/Imagenes Programa/";
	

	/**
	 * Se lanza la aplicacion
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
		initialize();
	}

	/**
	 * Inicializar los contenidos the contents de la pantalla.
	 */
	private void initialize() {
		setFrmExpodent(new JFrame());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		getFrmExpodent().setBackground(new Color(253, 245, 230));
		getFrmExpodent().setContentPane(new JLabel(new ImageIcon(path + "Pantalla Principal.png")));
		getFrmExpodent().getContentPane().setBackground(Color.WHITE);
		getFrmExpodent().setFont(new Font("Apple Braille", Font.PLAIN, 14));
		getFrmExpodent().setResizable(false);
		getFrmExpodent().setTitle("GT DENT");
		getFrmExpodent().setBounds(100, 100, 1240, 755);
		//getFrmExpodent().setBounds(100, 100, 587, 323);
		getFrmExpodent().setLocation(dim.width/2 - getFrmExpodent().getSize().width/2, dim.height/2- getFrmExpodent().getSize().height/2);
		getFrmExpodent().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmExpodent().getContentPane().setLayout(null);
		
	
		btn_registro = new JButton("");
		btn_registro.setIcon(new ImageIcon(path + "btn_registro bien.png"));
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
		btn_registro.setBounds(120, 200, 270, 360);
		getFrmExpodent().getContentPane().add(btn_registro);
	
		
		btnExpediente = new JButton("");
		btnExpediente.setIcon(new ImageIcon(path + "btn_exp bien.png"));
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
		btnExpediente.setBounds(480, 200, 270, 360);
		getFrmExpodent().getContentPane().add(btnExpediente);
		
		
		btnReceta = new JButton("");
		btnReceta.setIcon(new ImageIcon(path + "btn_receta bien.png"));
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
		btnReceta.setBounds(840, 200, 270, 360);
		getFrmExpodent().getContentPane().add(btnReceta);
		
		
		
		btnRespaldo = new JButton("");
		btnRespaldo.setIcon(new ImageIcon(path + "btn_respaldo bien.png"));
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
		btnRespaldo.setBounds(480, 620, 300, 80);
		getFrmExpodent().getContentPane().add(btnRespaldo);
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
