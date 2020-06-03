package es.studium.Practica2_Prog;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import es.studium.Practica2_Prog.GestionUser;

public class GestionUser extends Frame implements ActionListener,WindowListener,ItemListener
{
	private static final long serialVersionUID = 1L;
	
	String idEmpleadoFK, contenidoLOG;
	BufferedImage imagen;
	//Pantallas
	Dialog seguroSalir = new Dialog(this,"¡Atención!",true);
		//Empleado
			//Alta empleado
			Dialog darAltaEmpleado = new Dialog(this,"Añadir empleado",true);
			Dialog altaEmpleadoCorrecto = new Dialog(darAltaEmpleado,"Estado",true);
			Dialog altaEmpleadoIncorrecto = new Dialog(darAltaEmpleado,"Estado",true);
			
		//Venta
			//Alta venta
			Dialog darAltaVenta = new Dialog(this,"Añadir venta",true);
			Dialog altaVentaCorrecto = new Dialog(darAltaVenta,"Estado",true);
			Dialog altaVentaIncorrecto = new Dialog(darAltaVenta,"Estado",true);
			
		//Productos
			//Alta producto
			Dialog darAltaProducto = new Dialog(this,"Añadir producto",true);
			Dialog altaProductoCorrecto = new Dialog(darAltaProducto,"Estado",true);
			Dialog altaProductoIncorrecto = new Dialog(darAltaProducto,"Estado",true);
			
	//Objetos para el menu
	MenuBar barraMenu = new MenuBar();
	Menu empleados = new Menu("Empleados");
	Menu ventas = new Menu("Ventas");
	Menu productos = new Menu("Productos");
	Menu otros = new Menu("Otros");
		//Items de Empleados
		MenuItem altaEmpleado = new MenuItem("Alta");
		//Items de Ventas
		MenuItem altaVentas = new MenuItem("Alta");
		//Items de Productos
		MenuItem altaProductos = new MenuItem("Alta");
		//Items de Otros
		MenuItem ayuda = new MenuItem("Ayuda");
	//SeguroSalir
	Label salir1 = new Label("¿Seguro que quieres salir?");
	Button btnSi = new Button("Sí");
	//Empleados
		//AltaEmpleados
		Label lbAltaEmpleado = new Label("Añadir un nuevo empleado");
		Label lbDniAltaEmpleado = new Label("DNI:");
		Label lbNombreAltaEmpleado = new Label("Nombre:");
		Label lbApellidoAltaEmpleado = new Label("Apellido:");
		TextField txDniAltaEmpleado = new TextField(10);
		TextField txNombreAltaEmpleado = new TextField(10);
		TextField txApellidoAltaEmpleado = new TextField(10);
		Button btnAplicarAltaEmpleado = new Button ("Añadir");
		Button btnLimpiarAltaEmpleado = new Button ("Limpiar");
			//estado altaEmpleado
			Label lbAltaEmpleadoCorrecto = new Label("¡El empleado ha sido registrado correctamente!");
			Label lbAltaEmpleadoIncorrecto = new Label("No se ha podido realizar correctamente :(");
	//Venta
		//AltaVenta
		Label lbAltaVenta = new Label("Añadir una nueva venta");
		Label lbFormaAltaVenta = new Label("Forma de pago:");
		Label lbFechaAltaVenta = new Label("Fecha (AAAA-MM-DD):");
		Label lbImporteAltaVenta = new Label("Importe:");
		TextField txFormaAltaVenta = new TextField(10);
		TextField txFechaAltaVenta = new TextField(10);
		TextField txImporteAltaVenta = new TextField(10);
		Choice listaAltaEmpleadoFK = new Choice();
		Button btnAplicarAltaVenta = new Button ("Añadir");
		Button btnLimpiarAltaVenta = new Button ("Limpiar");
		Panel panelAltaVenta = new Panel();
			//estado altaVenta
			Label lbAltaVentaCorrecto = new Label("¡La venta ha sido registrada correctamente!");
			Label lbAltaVentaIncorrecto = new Label("No se ha podido realizar correctamente :(");
	//Productos
		//AltaProducto
			Label lbAltaProducto = new Label("Añadir un nuevo producto");
			Label lbNombreAltaProducto = new Label("Nombre:");
			Label lbPrecioAltaProducto = new Label("Precio:");
			TextField txNombreAltaProducto = new TextField(10);
			TextField txPrecioAltaProducto = new TextField(10);
			Button btnAplicarAltaProducto = new Button ("Añadir");
			Button btnLimpiarAltaProducto = new Button ("Limpiar");
			Panel panelProducto = new Panel();
				//estado altaProducto
				Label lbAltaProductoCorrecto = new Label("¡El producto ha sido registrado correctamente!");
				Label lbAltaProductoIncorrecto = new Label("No se ha podido realizar correctamente :(");
	
	public GestionUser()
	{
			//Menú
			setLayout(new FlowLayout());
			setTitle("Menú");
			setLocationRelativeTo(null);
			setSize(500,250);
			setIconImage(new ImageIcon("1.png").getImage());
				//barra del menú
				setMenuBar(barraMenu);
				barraMenu.add(empleados);
				barraMenu.add(ventas);
				barraMenu.add(productos);
				barraMenu.add(otros);
				empleados.add(altaEmpleado);
				altaEmpleado.addActionListener(this);
				ventas.add(altaVentas);
				altaVentas.addActionListener(this);
				productos.add(altaProductos);
				altaProductos.addActionListener(this);
				otros.add(ayuda);
				ayuda.addActionListener(this);
				//imagen para el menú
				try
				{
					imagen = ImageIO.read(new File("2.gif"));
				}
				catch(IOException ex)
				{
					System.out.println("Error con la imagen de fondo");
				}
			addWindowListener(this);
			setResizable(true);
			setVisible(true);
			//SeguroSalir
			seguroSalir.setLayout(new FlowLayout());
			seguroSalir.setSize(180,100);
			seguroSalir.setLocationRelativeTo(null);
			seguroSalir.add(salir1);
			seguroSalir.add(btnSi);
			btnSi.addActionListener(this);
			btnSi.requestFocus();
			seguroSalir.addWindowListener(this);
			seguroSalir.setVisible(false);
			seguroSalir.setResizable(false);
			//Empleados
				//Dar de alta Empleados
				darAltaEmpleado.setLayout(new FlowLayout());
				darAltaEmpleado.setSize(200,185);
				darAltaEmpleado.setLocationRelativeTo(null);
				darAltaEmpleado.add(lbAltaEmpleado);
				darAltaEmpleado.add(lbDniAltaEmpleado);
				darAltaEmpleado.add(txDniAltaEmpleado);
				darAltaEmpleado.add(lbNombreAltaEmpleado);
				darAltaEmpleado.add(txNombreAltaEmpleado);
				darAltaEmpleado.add(lbApellidoAltaEmpleado);
				darAltaEmpleado.add(txApellidoAltaEmpleado);
				darAltaEmpleado.add(btnAplicarAltaEmpleado);
				btnAplicarAltaEmpleado.addActionListener(this);
				darAltaEmpleado.add(btnLimpiarAltaEmpleado);
				btnLimpiarAltaEmpleado.addActionListener(this);
				darAltaEmpleado.addWindowListener(this);
				darAltaEmpleado.setVisible(false);
				darAltaEmpleado.setResizable(false);
					//Alta correcta
					altaEmpleadoCorrecto.setLayout(new FlowLayout());
					altaEmpleadoCorrecto.setSize(280,100);
					altaEmpleadoCorrecto.setLocationRelativeTo(null);
					altaEmpleadoCorrecto.add(lbAltaEmpleadoCorrecto);
					altaEmpleadoCorrecto.addWindowListener(this);
					altaEmpleadoCorrecto.setVisible(false);
					altaEmpleadoCorrecto.setResizable(false);
					//Alta incorrecta
					altaEmpleadoIncorrecto.setLayout(new FlowLayout());
					altaEmpleadoIncorrecto.setSize(280,100);
					altaEmpleadoIncorrecto.setLocationRelativeTo(null);
					altaEmpleadoIncorrecto.add(lbAltaEmpleadoIncorrecto);
					altaEmpleadoIncorrecto.addWindowListener(this);
					altaEmpleadoIncorrecto.setVisible(false);
					altaEmpleadoIncorrecto.setResizable(false);
			//Venta
				//Dar de alta Venta
				darAltaVenta.setLayout(new FlowLayout());
				darAltaVenta.setSize(250,220);
				darAltaVenta.setLocationRelativeTo(null);
				darAltaVenta.add(lbAltaVenta);
				darAltaVenta.add(lbFormaAltaVenta);
				darAltaVenta.add(txFormaAltaVenta);
				darAltaVenta.add(lbFechaAltaVenta);
				darAltaVenta.add(txFechaAltaVenta);
				darAltaVenta.add(lbImporteAltaVenta);
				darAltaVenta.add(txImporteAltaVenta);
				darAltaVenta.add(listaAltaEmpleadoFK);
				listaAltaEmpleadoFK.addItemListener(this);
				listaAltaEmpleadoFK.add("Empleado que realizó la venta");
				darAltaVenta.add(panelAltaVenta);
				panelAltaVenta.add(btnAplicarAltaVenta);
				btnAplicarAltaVenta.addActionListener(this);
				panelAltaVenta.add(btnLimpiarAltaVenta);
				btnLimpiarAltaVenta.addActionListener(this);
				darAltaVenta.addWindowListener(this);
				darAltaVenta.setVisible(false);
				darAltaVenta.setResizable(false);
					//Alta correcta
					altaVentaCorrecto.setLayout(new FlowLayout());
					altaVentaCorrecto.setSize(280,100);
					altaVentaCorrecto.setLocationRelativeTo(null);
					altaVentaCorrecto.add(lbAltaVentaCorrecto);
					altaVentaCorrecto.addWindowListener(this);
					altaVentaCorrecto.setVisible(false);
					altaVentaCorrecto.setResizable(false);
					//Alta incorrecta
					altaVentaIncorrecto.setLayout(new FlowLayout());
					altaVentaIncorrecto.setSize(280,100);
					altaVentaIncorrecto.setLocationRelativeTo(null);
					altaVentaIncorrecto.add(lbAltaVentaIncorrecto);
					altaVentaIncorrecto.addWindowListener(this);
					altaVentaIncorrecto.setVisible(false);
					altaVentaIncorrecto.setResizable(false);
			//Productos
				//Dar de alta Producto
				darAltaProducto.setLayout(new FlowLayout());
				darAltaProducto.setSize(160,240);
				darAltaProducto.setLocationRelativeTo(null);
				darAltaProducto.add(lbAltaProducto);
				darAltaProducto.add(lbNombreAltaProducto);
				darAltaProducto.add(txNombreAltaProducto);
				darAltaProducto.add(lbPrecioAltaProducto);
				darAltaProducto.add(txPrecioAltaProducto);
				panelProducto.add(btnAplicarAltaProducto);
				btnAplicarAltaProducto.addActionListener(this);
				panelProducto.add(btnLimpiarAltaProducto);
				btnLimpiarAltaProducto.addActionListener(this);
				darAltaProducto.add(panelProducto);
				darAltaProducto.addWindowListener(this);
				darAltaProducto.setVisible(false);
				darAltaProducto.setResizable(false);
					//Alta correcta
					altaProductoCorrecto.setLayout(new FlowLayout());
					altaProductoCorrecto.setSize(280,100);
					altaProductoCorrecto.setLocationRelativeTo(null);
					altaProductoCorrecto.add(lbAltaProductoCorrecto);
					altaProductoCorrecto.addWindowListener(this);
					altaProductoCorrecto.setVisible(false);
					altaProductoCorrecto.setResizable(false);
					//Alta incorrecta
					altaProductoIncorrecto.setLayout(new FlowLayout());
					altaProductoIncorrecto.setSize(280,100);
					altaProductoIncorrecto.setLocationRelativeTo(null);
					altaProductoIncorrecto.add(lbAltaProductoIncorrecto);
					altaProductoIncorrecto.addWindowListener(this);
					altaProductoIncorrecto.setVisible(false);
					altaProductoIncorrecto.setResizable(false);
					
					Date fecha = new Date();
					contenidoLOG = "["+fecha.toString()+"][Usuario1][Entra al sistema]";
	}
	
	//Base de datos
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tiendademusica?useSSL=false";
	String login = "root";
	String password = "Studium2020;";
	
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	
	//Función para actualizar el fichero log
	public void movimientosLog(String f)
	{
		try
		{
			// Destino de los datos
			FileWriter fw = new FileWriter("movimientos.log",true);
			// Buffer de escritura
			BufferedWriter bw = new BufferedWriter(fw);
			// Objeto para la escritura
			PrintWriter salida = new PrintWriter(bw);
			//Guardamos la primera línea
			salida.println(f);
			//Cerrar el objeto salida, el objeto bw y el fw
			salida.close();
			bw.close();
			fw.close();
			System.out.println("¡Archivo creado correctamente!");
		}
		catch(IOException i)
		{
			System.out.println("Se produjo un error de Archivo");
		}
	}
	public void paint(Graphics arg0)
	{
		super.paint(arg0);
		arg0.drawImage(imagen,0,0,getWidth(),getHeight(),null);
	}
		
		
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		if(isActive())
		{
			seguroSalir.setVisible(true);
		}
		else if (seguroSalir.isActive())
		{
			seguroSalir.setVisible(false);
		}
		//Empleado
			//AltaEmpleado
			else if(darAltaEmpleado.isActive())
			{
				darAltaEmpleado.setVisible(false);
			}
			else if(altaEmpleadoCorrecto.isActive())
			{
				altaEmpleadoCorrecto.setVisible(false);
			}
			else if(altaEmpleadoIncorrecto.isActive())
			{
				altaEmpleadoIncorrecto.setVisible(false);
			}
		//Venta
			//AltaVenta
			else if(darAltaVenta.isActive())
			{
				darAltaVenta.setVisible(false);
			}
			else if(altaVentaCorrecto.isActive())
			{
				altaVentaCorrecto.setVisible(false);
			}
			else if(altaVentaIncorrecto.isActive())
			{
				altaVentaIncorrecto.setVisible(false);
			}
		//Productos
			//AltaProducto
			else if(darAltaProducto.isActive())
			{
				darAltaProducto.setVisible(false);
			}
			else if(altaProductoCorrecto.isActive())
			{
				altaProductoCorrecto.setVisible(false);
			}
			else if(altaProductoIncorrecto.isActive())
			{
				altaProductoIncorrecto.setVisible(false);
			}
		else
		{
			System.exit(0);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(btnSi.equals(e.getSource()))
		{
			Date fecha = new Date();
			contenidoLOG = contenidoLOG + "\n" + "["+fecha.toString()+"][Usuario1][Sale del sistema]";
			movimientosLog(contenidoLOG);
			System.exit(0);
		}
		//Acceder a ayuda desde barra
		else if(ayuda.equals(e.getSource()))
		{
			try
			{
				Runtime.getRuntime().exec("hh.exe ayuda.chm");
			}
			catch (IOException IOE)
			{
				IOE.printStackTrace();
			}
		}
		//Acceder a Empleado desde barra
		else if(altaEmpleado.equals(e.getSource()))
		{
			darAltaEmpleado.setVisible(true);
		}
		
		//Acceder a Ventas desde la barra
		else if(altaVentas.equals(e.getSource())) 
		{
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				rs= statement.executeQuery("SELECT * FROM empleados");
				while(rs.next())
				{
				String x = rs.getString("idEmpleado");
				x = x + "-"+ rs.getString("dniEmpleado");
				x = x + "-"+ rs.getString("nombreEmpleado");
				x = x + "-"+ rs.getString("apellidoEmpleado");
				listaAltaEmpleadoFK.add(x);
				}
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			darAltaVenta.setVisible(true); 
		}
		//Acceder a Productos desde la barra
		else if(altaProductos.equals(e.getSource()))
		{
			darAltaProducto.setVisible(true);
		}
		//Botones limpiar
			//Empleado
		else if(btnLimpiarAltaEmpleado.equals(e.getSource()))
		{
			txDniAltaEmpleado.selectAll();
			txNombreAltaEmpleado.selectAll();
			txApellidoAltaEmpleado.selectAll();
			
			txDniAltaEmpleado.setText("");
			txNombreAltaEmpleado.setText("");
			txApellidoAltaEmpleado.setText("");
		}
			//Venta
		else if(btnLimpiarAltaVenta.equals(e.getSource()))
		{
			txFormaAltaVenta.selectAll();
			txFechaAltaVenta.selectAll();
			txImporteAltaVenta.selectAll();
			
			txFormaAltaVenta.setText("");
			txFechaAltaVenta.setText("");
			txImporteAltaVenta.setText("");
		}
			//Productos
		else if(btnLimpiarAltaProducto.equals(e.getSource()))
		{
			txNombreAltaProducto.selectAll();
			txPrecioAltaProducto.selectAll();
			
			txNombreAltaProducto.setText("");
			txPrecioAltaProducto.setText("");
		}
		//Botones aplicar
			//Empleado
		else if(btnAplicarAltaEmpleado.equals(e.getSource()))
		{
			String dniEmpleado = txDniAltaEmpleado.getText();
			String nombreEmpleado = txNombreAltaEmpleado.getText();
			String apellidoEmpleado = txApellidoAltaEmpleado.getText();
				
				try
				{
					//Cargar los controladores para el acceso a la BD
					Class.forName(driver);
					//Establecer la conexión con la BD Empresa
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

					String sentencia = "INSERT INTO empleados(dniEmpleado,nombreEmpleado,apellidoEmpleado) VALUES ('"+dniEmpleado+"','"+nombreEmpleado+"','"+apellidoEmpleado+"')";
					int resp = statement.executeUpdate(sentencia);
					
			        if (resp > 0) 
			        {
			            altaEmpleadoCorrecto.setVisible(true);
			            Date fecha = new Date();
						contenidoLOG = contenidoLOG + "\n" + "["+fecha.toString()+"][Usuario1][Alta empleado: "+sentencia+"]";
			        }
			        else
			        {
			        	altaEmpleadoIncorrecto.setVisible(true);
			        }
					txDniAltaEmpleado.setText("");
					txNombreAltaEmpleado.setText("");
					txApellidoAltaEmpleado.setText("");
					
				}
				catch (ClassNotFoundException cnfe)
				{
					System.out.println("Error 1-"+cnfe.getMessage());
					altaEmpleadoIncorrecto.setVisible(true);
				}
				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
					altaEmpleadoIncorrecto.setVisible(true);
				}
		}
		//Venta
		else if(btnAplicarAltaVenta.equals(e.getSource()))
		{
			String formaPagoVenta = txFormaAltaVenta.getText();
			String fechaVenta = txFechaAltaVenta.getText();
			String importeVenta = txImporteAltaVenta.getText().replace(',','.');
				
			
				try
				{
					//Cargar los controladores para el acceso a la BD
					Class.forName(driver);
					//Establecer la conexión con la BD Empresa
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

					String sentencia = "INSERT INTO ventas(formaPagoVenta,fechaVenta,importeVenta,idEmpleadoFK) VALUES ('"+formaPagoVenta+"','"+fechaVenta+"','"+importeVenta+"','"+idEmpleadoFK+"')";
					int resp = statement.executeUpdate(sentencia);
					
			        if (resp > 0) 
			        {
			            altaVentaCorrecto.setVisible(true);
			            Date fecha = new Date();
						contenidoLOG = contenidoLOG + "\n" + "["+fecha.toString()+"][Usuario1][Alta venta: "+sentencia+"]";
			        }
			        else
			        {
			        	altaVentaIncorrecto.setVisible(true);
			        }
			        txFormaAltaVenta.setText("");
			        txFechaAltaVenta.setText("");
			        txImporteAltaVenta.setText("");
					
				}
				catch (ClassNotFoundException cnfe)
				{
					System.out.println("Error 1-"+cnfe.getMessage());
					altaVentaIncorrecto.setVisible(true);
				}
				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
					altaVentaIncorrecto.setVisible(true);
				}
		}
		//Productos
		else if(btnAplicarAltaProducto.equals(e.getSource()))
		{
			String nombreProducto = txNombreAltaProducto.getText();
			String precioProducto = txPrecioAltaProducto.getText().replace(',','.');
				
				try
				{
					//Cargar los controladores para el acceso a la BD
					Class.forName(driver);
					//Establecer la conexión con la BD Empresa
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

					String sentencia = "INSERT INTO productos(nombreProducto,precioProducto) VALUES ('"+nombreProducto+"','"+precioProducto+"')";
					int resp = statement.executeUpdate(sentencia);
					
			        if (resp > 0) 
			        {
			            altaProductoCorrecto.setVisible(true);
			            Date fecha = new Date();
						contenidoLOG = contenidoLOG + "\n" + "["+fecha.toString()+"][Usuario1][Alta producto: "+sentencia+"]";
			        }
			        else
			        {
			        	altaProductoIncorrecto.setVisible(true);
			        }
					txNombreAltaProducto.setText("");
					txPrecioAltaProducto.setText("");
					
				}
				catch (ClassNotFoundException cnfe)
				{
					System.out.println("Error 1-"+cnfe.getMessage());
					altaProductoIncorrecto.setVisible(true);
				}
				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
					altaProductoIncorrecto.setVisible(true);
				}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
			try
			{
				String[] array = arg0.getItem().toString().split("-");
				idEmpleadoFK = array[0];
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
	}
}