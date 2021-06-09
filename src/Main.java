package database;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Main {
	
	static Connection connection;
	//private static final String ACCESS_TOKEN = "yVe6P-Jb0gYAAAAAAAAAAc-q_s6wjHWZXMfv5UFeBVROzcIBQ8FLhK-fN0o0erq3";
	private static final String ACCESS_TOKEN = "t4qZWZFY57gAAAAAAAAAASmcH5IvDfn_xCU9tx_oqMyH6lIliDuCDQMoeTydXxIb";
	public static DbxClientV2 client;
	//private static String path = "/Users/diegogutierrez/Desktop/TEC 5/Info III/Imagenes Programa/";
	private static String path = "C:/Program Files (x86)/GTDent/Temporary/";
	

	/**
	 * El metodo main() inicializa las conexiones necesarias para el programa como lo es la conexion
	 * a la base de datos (Local) y la conexion a el Dropbox para el respaldo
	 */
	public static void main(String[] args) throws SQLException, DbxApiException, DbxException {
		getConnection();
		reseedValuesSequence();
	}

	
	public static void getConnection() {
		String url = "jdbc:sqlserver://localhost\\Consul:1433;databaseName=Consul";
		String username = "sa";
		String password = "reallyStrongPwd123";

		try  {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ProgramaMama.getFrmExpodent(), "No se pudo conectar a la base de datos"
					+ "\nCheque su base de datos");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			getDropBoxConnection();
		} catch (DbxException e) {
			JOptionPane.showMessageDialog(ProgramaMama.getFrmExpodent(), "No se pudo conectar a Dropbox"
					+ "\nCheque su conexion a internet");
			e.printStackTrace();
		}
	}

	
	public static void getDropBoxConnection() throws DbxApiException, DbxException {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("GT-DENT/1.0").build();
		client = new DbxClientV2(config, ACCESS_TOKEN);
	}
	
	public static void reseedValuesUno() throws SQLException {
		String sql = "DECLARE @MAXVALUE INT SELECT @MAXVALUE = MAX(#) FROM PacientesTEST DBCC CHECKIDENT (PacientesTEST, RESEED, @MAXVALUE);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
	}
	
	public static void reseedValuesDos() throws SQLException {
		String sql1 = "DECLARE @MAXVALUEUN INT SELECT @MAXVALUEUN = MAX(Id) FROM Expedientes DBCC CHECKIDENT (Expedientes, RESEED, @MAXVALUEUN);";
		PreparedStatement statement = connection.prepareStatement(sql1);
		statement.execute();
	}
	
	public static void reseedValuesTres() throws SQLException {
		String sql2 = "DECLARE @MAXVALUEDO INT SELECT @MAXVALUEDO = MAX(Id) FROM Odontograma DBCC CHECKIDENT (Odontograma, RESEED, @MAXVALUEDO);";
		PreparedStatement statement = connection.prepareStatement(sql2);
		statement.execute();
	}
	
	public static void reseedValuesSequence() throws SQLException {
		reseedValuesUno();
		reseedValuesUno();
		reseedValuesDos();
		reseedValuesDos();
		reseedValuesTres();
		reseedValuesTres();
	}
	
	/**
	 * metodo que recibe parametros para introducir a la base de datos
	 * y se introducen a la tabla de pacientes en la base de datos
	 * se introduce un expediente y odontograma vacio para el paciente
	 */
	public static void createLine(String name, String fNacimiento, String edad, String correo, String tel) throws SQLException {
		String sql = "INSERT INTO PacientesTEST (fechaNacimiento, correo, edad, Nombre, telefono)"
				   + " VALUES ('" +  fNacimiento + "', '" + correo + "', '" + edad + "', '" + name + "', '" + tel +"');";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		ExpedienteVacio();
		insertBlankOdontograma();
	}

	/**
	 * metodo que regresa un resultset con el contenido
	 * de la tabla de los pacientes
	 */
	public static ResultSet getLines() throws SQLException {
		String sql = "SELECT * FROM PacientesTEST";
		Statement statement = connection.createStatement(); 
		ResultSet result = statement.executeQuery(sql);
		return result;
	}
	
	/**
	 * metodo que regresa un resultset con el contenido
	 * de la tabla de pacientes limitados por ese nombre
	 */
	public static ResultSet refresh(String name) throws SQLException {
		String sql = "SELECT * FROM PacientesTEST WHERE Nombre LIKE '%" + name + "%';";
		Statement statement = connection.createStatement(); 
		ResultSet result = statement.executeQuery(sql);
		return result;
	}
	
	/**
	 * metodo que regresa un resultset con el contenido
	 * de la tabla de pacientres limitados por la fecha
	 * de nacimiento introducida
	 */
	public static ResultSet refreshConFecha(String fechaNacimiento) throws SQLException {
		String sql = "SELECT * FROM PacientesTEST WHERE fechaNacimiento LIKE '" + fechaNacimiento + "%';";
		Statement statement = connection.createStatement(); 
		ResultSet result = statement.executeQuery(sql);
		return result;
	}
	
	/**
	 * metodo que cambia los datos de cierto paciente cuando
	 * se pide algun cambio de dato
	 */
	public static void updatePatient(String name, String fNacimiento, String edad, String correo, String tel, String i) throws SQLException {
		String sql = "UPDATE PacientesTEST SET Nombre = '" + name + "', fechaNacimiento = '" + fNacimiento + "', edad = '" + edad + "', correo ='" + correo +  "', telefono ='" + tel +"' WHERE # = " + i +";";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	
	/**
	 * metodo que regresa el diagnostico del paciente
	 * dependido en el id del paciente y el diagnostico
	 * que se este desplegando
	 */
	public static String diagnostic(int Dnum, int CurPa) throws SQLException {
		String temp = "Diagnostico" + Dnum;
		String sql = "SELECT " + temp + " FROM Expedientes WHERE Id = '" + CurPa + "';";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);	
		result.next();
		return result.getString(temp);
	}
	
	/**
	 * metodo que regresa un string con el contenido
	 * de la proxima cita dependiendo el id del paciente
	 */
	public static String Citas(int CurPa) throws SQLException {
		String sql = "SELECT ProxCita FROM Expedientes WHERE Id = '" + CurPa + "';";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);	
		result.next();
		return result.getString("ProxCita");
	}
	
	/**
	 * metodo que regresa un string con el contenido
	 * de los antecedentes de un paciente dependiendo
	 * el id del paciente
	 */
	public static String Antecedentes(int CurPa) throws SQLException {
		String sql = "SELECT Antecedentes FROM Expedientes WHERE Id = '" + CurPa + "';";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);	
		result.next();
		return result.getString("Antecedentes");
	}
	
	/**
	 * metodo que introduce un expediente vacio para que
	 * los id's de los pacientes no queden mal enlazados
	 */
	private static void ExpedienteVacio() throws SQLException {
		String sql = "INSERT INTO Expedientes (Diagnostico1, Diagnostico2, Diagnostico3, Diagnostico4, ProxCita, Antecedentes)"
				+ " VALUES ('" +  "" + "', '" + "" + "', '" + "" + "', '" + "" + "', '" + "" + "', '" + "" + "');";
		System.out.println(sql);
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	
	/**
	 * metodo que guarda los datos de la proxima cita,
	 * los antecedentes en la base de datos dependiendo
	 * en el id del paciente seleccionado
	 */
	public static void saveCitaData(String prox, String Antece, int curPac) throws SQLException {
		String sql = "UPDATE Expedientes SET ProxCita = '" + prox + "', Antecedentes = '" + Antece + "' WHERE Id = '" + curPac + "';";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	
	/**
	 * metodo que guarda los datos de los diagnosticos
	 * del paciente en la base de datos depediendo en 
	 * el numero de diagnostico y el id de paciente
	 */
	public static void diagnosticInsert(String insertar, int diaNUM, int CurPac) throws SQLException{
		String sql = "UPDATE Expedientes SET Diagnostico" + diaNUM + " = '" + insertar + "' WHERE Id = '" + CurPac + "';";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	
	/**
	 * metodo que introduce una fila de odontograma
	 * vacia para que las tablas no queden mal enlazados
	 */
	public static void insertBlankOdontograma() throws SQLException {
		String sql = "INSERT INTO Odontograma DEFAULT VALUES;";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	
	/**
	 * metodo que regresa un resultset con el contenido
	 * del odontograma con el id del paciente 
	 */
	public static ResultSet getOdontograma(int curPac) throws SQLException {
		String sql = "SELECT * FROM Odontograma WHERE Id = '" + curPac + "';";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}
	
	/**
	 * metodo que guarda el odontograma del paciente
	 * donde toma el numero de odontograma y el id
	 * de paciente como parametro de entrada
	 */
	public static void guardarOdontograma(String curNum, int curPac, String texto) throws SQLException {
		String sql = "UPDATE Odontograma SET [" + curNum + "] = '" + texto + "' WHERE Id = '" + curPac + "';";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	
	/**
	 * metodo que regresa un resultset con el contenido
	 * de los pacientes en donde se revisa si ya hay
	 * un paciente con un correo igual al dado
	 */
	public static ResultSet revisarCorreo(String correo) throws SQLException{
		String sql = "SELECT * FROM PacientesTEST WHERE correo = '" + correo + "';";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}
	
	/**
	 * metodo que regresa un resultset con el contenido
	 * de los expedientes de todos los pacientes
	 */
	public static ResultSet getAllExpedientes() throws SQLException {
		String sql = "SELECT * FROM Expedientes;";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}
	
	/**
	 * metodo que regresa un resultset con el contenido
	 * de todos los odontogramas de todos los pacientes
	 */
	public static ResultSet getAllOdontogramas() throws SQLException {
		String sql = "SELECT * FROM Odontograma;";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}
	
	
	/**
	 * metodo que realiza todos los pasos para realizar
	 * el respaldo en el archivo de excel
	 */
	public static void respaldar() throws SQLException, IOException, URISyntaxException {
		//se envia el archivo de excel al metodo anterior para llenarlo de datos
		//junto con el resultset de los pacientes
		Workbook excel = WorkbookFactory.create(true);
		ResultSet pacientes = getLines();
		excel = archivoExcel(pacientes, excel, "Pacientes");
		
		//se envia el mismo archivo de excel, junto con el resultset
		//de los expedientes hacia el metodo anterior
		ResultSet expedientes = getAllExpedientes();
		excel = archivoExcel(expedientes, excel, "Expedientes");
		
		//se envia el mismo archivo de excel, junto con el resultset
		//de los odontogramas hacia el metodo anterior
		ResultSet odontogramas = getAllOdontogramas();
		excel = archivoExcel(odontogramas, excel, "Odontogramas");
		
		//ya que estan todos los datos en el excel
		//se genera un archivo temporal para enviarlo
		//a dropbox
		try(FileOutputStream archivo = new FileOutputStream(path + "prueba10.xlsx")){
			excel.write(archivo);
			archivo.flush();
			archivo.close();

			archivoDropBox(archivo);
		} catch (UploadErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * metodo para generar el archivo de respaldo en excel de la base de datos
	 * con el uso de la libreria Apache Poi utilizando un resultset, un archivo
	 * de excel y un titulo para realizarlo con esos datos
	 */
	public static Workbook archivoExcel(ResultSet result, Workbook excel, String titulo) 
			throws URISyntaxException, IOException, SQLException {

		//se obtiene los datos del ResultSet para asi escribirlos en el excel
		java.sql.ResultSetMetaData resultPacientes = result.getMetaData();
		List<String> columnas = new ArrayList<String>() {{

			//se itera por la el resultado de la base de datos para escribirlos
			//en la lista de strings
			for(int i = 1; i <= resultPacientes.getColumnCount(); i++) {
				add(resultPacientes.getColumnLabel(i));
			}
		}};

		//se genera una pagina en el archivo de excel para agregar la informacion
		Sheet paginaPacientes = excel.createSheet(titulo);
		Row tituloColumna = paginaPacientes.createRow(0);

		//se itera por la columnas y se agrega el titulo de cada columna
		for(int i = 0; i < columnas.size(); i++) {
			tituloColumna.createCell(i).setCellValue(columnas.get(i));
		}

		int filaActual = 0;
		//se itera por la columnas y se agregan los datos por cada nueva fila
		while(result.next()) {
			Row fila = paginaPacientes.createRow(++filaActual);
			for(int i = 0; i < columnas.size(); i++) {
				fila.createCell(i).setCellValue(Objects.toString(result.getObject(columnas.get(i)), ""));
			}
		}
		return excel; 	
	}
	
	/**
	 * metodo para enviar el archivo de respaldo de excel a 
	 * dropbox
	 */
	public static void archivoDropBox(FileOutputStream archivoFinal) 
			throws UploadErrorException, DbxException, IOException {
		//se genera una fecha para asi nombrar el archivo de ese día
		Date fecha = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy");
		String nombreArchivo = "Respaldo-GTDENT-" + formatter.format(fecha) + ".xlsx";

		//se accede a dropbox quitando el archivo anterior y agregando el nuevo con la
		//fecha nueva del día del respaldo
		InputStream in;
		try {
			client.files().deleteV2("/GT DENT");
			client.files().createFolderV2("/GT DENT");
			in = new FileInputStream(path + "prueba10.xlsx");
			client.files().uploadBuilder("/GT DENT/" + nombreArchivo).uploadAndFinish(in);
			JOptionPane.showMessageDialog(ProgramaMama.getFrmExpodent(), "Se respaldo correctamente");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//se carga el archivo de nuevo para eliminarlo por completo de la computadora
		File temp = new File(path + "prueba10.xlsx");
		temp.delete();
	}
}
