package database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDPage; 
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageable;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.apache.pdfbox.PDFReader;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdfviewer.PageWrapper;

import com.mysql.cj.result.Field;
import com.spire.pdf.PdfDocument;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Ventana_receta extends JFrame {

	private JPanel contentPane;
	private JTextField textField_nombre;
	private JTextField textField_fecha;
	private JTextArea textArea_contenido;
	//private static String path = "/Users/diegogutierrez/Desktop/TEC 5/Info III/Imagenes Programa/";
	private static String path = "C:/Program Files (x86)/GTDent/Images/";
	private static String pathfile = "C:/Program Files (x86)/GTDent/Files/";
	private static String pathtmp = "C:/Program Files (x86)/GTDent/Temporary/";
	
	Toolkit toolkit =  Toolkit.getDefaultToolkit();
	private Dimension screenSize = toolkit.getScreenSize();
	private int resol = toolkit.getScreenResolution();
	private double width = screenSize.width * 1.72;
	private double height = screenSize.height * 1.72;
	private int frame_width_size = (int)((width * 57.08) / 100);
	private int frame_height_size = (int)((height * 52.61) / 100) + 30;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//se genera un objeto de Ventana_receta
					Ventana_receta frame = new Ventana_receta();
					//se hace visible
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	
	public Ventana_receta() throws IOException {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, frame_width_size, frame_height_size);
		setLocation(dim.width/2 - getSize().width/2, (dim.height/2- getSize().height/2)-20);
		/**
		 * tomado de... 
		 */
		contentPane = new JPanel() {
			public void paintComponent( Graphics g ){
				  super.paintComponent(g);
				  Graphics2D g2d = (Graphics2D) g;
				  try {
					InputStream image = new FileInputStream(path + "fondo blob.png");
					  BufferedImage src;
					  try {
						src = ImageIO.read(image);
						g2d.drawImage(src, 0, 0, frame_width_size, frame_height_size, null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_regresar = new JButton("<");
		btn_regresar.setForeground(new Color(255, 160, 122));
		btn_regresar.setBackground(Color.WHITE);
		btn_regresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ProgramaMama.getFrmExpodent().setVisible(true);
			}
		});
		btn_regresar.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btn_regresar.setBounds((int)((frame_width_size * 3.22) / 100), (int)((frame_height_size * 4.6) / 100), (int)((frame_width_size * 5.7) / 100), (int)((frame_height_size * 9.18) / 100));
		contentPane.add(btn_regresar);
		
		textField_nombre = new RoundJTextField(15);
		textField_nombre.setBounds((int)((frame_width_size * 31.02) / 100), (int)((frame_height_size * 24.44) / 100), (int)((frame_width_size * 14.66) / 100), (int)((frame_height_size * 3.42) / 100));
		contentPane.add(textField_nombre);
		textField_nombre.setColumns(10);
		Border borde = BorderFactory.createLineBorder(Color.black);
		textField_nombre.setBorder(borde);
		
		textField_fecha = new RoundJTextField(15);
		textField_fecha.setBounds((int)((frame_width_size * 54.62) / 100), (int)((frame_height_size * 20.81) / 100), (int)((frame_width_size * 8.64) / 100), (int)((frame_height_size * 3.42) / 100));
		contentPane.add(textField_fecha);
		textField_fecha.setColumns(10);
		textField_fecha.setBorder(borde);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setBackground(SystemColor.textHighlight);
		btnImprimir.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cargarPdf();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImprimir.setBackground(SystemColor.textHighlight);
		btnImprimir.setBounds((int)((frame_width_size * 71.9) / 100), (int)((frame_height_size * 36.29) / 100), (int)((frame_width_size * 20.19) / 100), (int)((frame_height_size * 18.57) / 100));
		contentPane.add(btnImprimir);
		
		JButton btnVaciar = new JButton("VACIAR");
		btnVaciar.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		btnVaciar.setBackground(Color.WHITE);
		btnVaciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_nombre.setText("");
				textField_fecha.setText("");
				textArea_contenido.setText("");
			}
		});
		btnVaciar.setBounds((int)((frame_width_size * 76.70) / 100), (int)((frame_height_size * 57.74) / 100), (int)((frame_width_size * 11.19) / 100), (int)((frame_height_size * 6.4) / 100));
		contentPane.add(btnVaciar);	
		
		textArea_contenido = new JTextArea();
		textArea_contenido.setBounds((int)((frame_width_size * 27.8) / 100), (int)((frame_height_size * 38.74) / 100), (int)((frame_width_size * 32.1) / 100), (int)((frame_height_size * 37.78) / 100));
		contentPane.add(textArea_contenido);
		textArea_contenido.setBorder(borde);
		
		JButton btnDaDeHoy = new JButton("día de hoy");
		btnDaDeHoy.setBackground(Color.WHITE);
		btnDaDeHoy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ponerFecha();
			}
		});
		btnDaDeHoy.setBounds((int)((frame_width_size * 79) / 100), (int)((frame_height_size * 65.42) / 100), (int)((frame_width_size * 7.12) / 100), (int)((frame_height_size * 3.1) / 100));
		contentPane.add(btnDaDeHoy);
	}
	
	/**
	 * metodo para cargar el pdf de la receta y asi mismo
	 * escribir el contenido en la receta con el uso de la
	 * libreria "PdfBox"
	 */
	private void cargarPdf() throws IOException {
		//guardar en variables los datos a poner en la receta
		String nombre = textField_nombre.getText();
		String fecha = textField_fecha.getText();
		String contenido = textArea_contenido.getText();
		
		//se carga el archivo .pdf para asi agregar el contenido de las variables
		File archivo = new File(pathfile + "Receta.pdf");
		PDDocument recetaArchivo = PDDocument.load(archivo);
		PDPage recetaPagina = recetaArchivo.getPage(0);
		PDPageContentStream escritura = new PDPageContentStream(recetaArchivo, recetaPagina, PDPageContentStream.AppendMode.APPEND, true, true);
		
		//se inicia el texto en el pdf en las cordenadas dadas
		escritura.beginText();
		escritura.newLineAtOffset(128, 607);
		escritura.setFont(PDType1Font.TIMES_ROMAN, 12);
		escritura.showText(nombre);
		
		//se inicia una nueva linea de texto en la nueva cordenada
		escritura.newLine();
		escritura.newLineAtOffset(338, 29);
		escritura.showText(fecha);
		escritura.newLine();
		escritura.setFont(PDType1Font.TIMES_ROMAN, 12);
		String[] textoDividido = contenido.split("\n");
		int x = 0;
		
		//finalmente se escriben las lineas separadas por espacios
		//en una nueva linea de texto en el pdf
		for (int i = 0; i < textoDividido.length; i++) {
			String texto = textoDividido[i];
			if(i == 1) {
				x-=13;
				escritura.newLineAtOffset(0, x);
			} else if (i > 1) {
				x-=1;
				escritura.newLineAtOffset(0, x);
			} else if (i == 0) {
				escritura.newLineAtOffset(-368, -139);
			}
			escritura.showText(texto);
		}
		
		//se finalizan y se cierran los cuadros de texto para guardarlos en el archivo
		escritura.endText();
		escritura.close();
		
		//se guarda el archivo editado a un archivo temporal
		recetaArchivo.save(pathtmp + "RecetaFINAL.pdf");

		//se manda a imprimir el archivo
		try {
			printPDF();
		} catch (PrinterException e) {
			e.printStackTrace();
		}

		//una vez impreso, se cierra el archivo para no generar errores
		recetaArchivo.close();
	}
		

	/**
	 * metodo para imprimir el archivo editado por el metodo cargarPdf()
	 */
	private static void printPDF() throws IOException, PrinterException {
		//se carga el archivo editado por el metodo cargarPdf()
		File f = new File(pathtmp + "RecetaFINAL.pdf");
		//f.setReadOnly();
		PDDocument docFinal = PDDocument.load(f);
		
		//se genera un trabajo de impreseion y se pide la informacion al cliente
	    PrinterJob job = PrinterJob.getPrinterJob();
	    PageFormat formato = job.getPageFormat(null);
	    Paper papel = formato.getPaper();
	    
	    //se posiciona el tamaño y configuracion de la pagina para que se logre una impresion completa
	    papel.setSize(612, 792);
	    papel.setImageableArea(0, 0, formato.getPaper().getWidth(), formato.getPaper().getHeight());
	    formato.setOrientation(PageFormat.PORTRAIT);
	    formato.setPaper(papel);
	    Book pageable = new Book();
	    pageable.append(new PDFPrintable(docFinal, Scaling.ACTUAL_SIZE, true, (float)500.0), formato, docFinal.getNumberOfPages());
	    job.setPageable(pageable);
	    job.printDialog();
	    PrintService impresora = job.getPrintService(); 
	    job.setPrintService(impresora);
	    job.setJobName("Receta Dra.Sandra");
	    job.setCopies(1);
	    
	    //se manda a imprimir la pagina
	    job.print();
	    
	    //se cierra el documento para evirar errores y se elimina el archivo temporal 
	    docFinal.close();
	    f.delete();
	}
	
	/**
	 * metodo para modificar la forma de los JTextFields
	 * tomado de: https://stackoverflow.com/a/8515677
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
	         g.setColor(getForeground());
	         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
	    }
	    public boolean contains(int x, int y) {
	         if (shape == null || !shape.getBounds().equals(getBounds())) {
	             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
	         }
	         return shape.contains(x, y);
	    }
	}
	
	/**
	 * metodo para crea la fecha del dia de hoy
	 * y pone la fecha en el TextField en donde va
	 * la fecha cuando se presiona el boton
	 */
	private void ponerFecha() {
		Date fecha = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		textField_fecha.setText(formatter.format(fecha));
	}
	
	public JTextField getTextFieldNombre() {
		return textField_nombre;
	}
	
	public JTextField getTextFieldFecha() {
		return textField_fecha;
	}
	
}
