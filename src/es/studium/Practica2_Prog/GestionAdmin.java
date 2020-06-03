package es.studium.Practica2_Prog;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.studium.Practica2_Prog.GestionAdmin;

public class GestionAdmin extends Frame implements ActionListener,WindowListener,ItemListener
{
	private static final long serialVersionUID = 1L;
	
	String idEmpleadoFK,idModificacionVenta,contenidoLOG,idVenta;
	BufferedImage imagen;
	//Pantallas
	Dialog seguroSalir = new Dialog(this,"¡Atención!",true);
		//Empleado
			//Alta empleado
			Dialog darAltaEmpleado = new Dialog(this,"Añadir empleado",true);
			Dialog altaEmpleadoCorrecto = new Dialog(darAltaEmpleado,"Estado",true);
			Dialog altaEmpleadoIncorrecto = new Dialog(darAltaEmpleado,"Estado",true);
			//Baja empleado
			Dialog darBajaEmpleado = new Dialog(this,"Dar de baja",true);
			Dialog bajaEmpleadoCorrecto = new Dialog(darBajaEmpleado,"Estado",true);
			Dialog bajaEmpleadoIncorrecto = new Dialog(darBajaEmpleado,"Estado",true);
			Dialog seguroBajaEmpleado = new Dialog(darBajaEmpleado,"Confirmar Baja",true);
			Dialog empleadoEnUso = new Dialog(darBajaEmpleado,"No se puede borrar",true);
			//Modificacion empleado
			Dialog darModificacionEmpleado = new Dialog(this,"Modificar datos del empleado",true);
			Dialog modificacionEmpleadoCorrecto = new Dialog(darModificacionEmpleado,"Estado",true);
			Dialog modificacionEmpleadoIncorrecto = new Dialog(darModificacionEmpleado,"Estado",true);
			//Consulta empleado
			Dialog darConsultaEmpleado = new Dialog(this,"Consultar datos del empleado",true);
		//Venta
			//Alta venta
			Dialog darAltaVenta = new Dialog(this,"Añadir venta",true);
			Dialog altaVentaCorrecto = new Dialog(darAltaVenta,"Estado",true);
			Dialog altaVentaIncorrecto = new Dialog(darAltaVenta,"Estado",true);
			//Baja empleado
			Dialog darBajaVenta = new Dialog(this,"Dar de baja",true);
			Dialog bajaVentaCorrecto = new Dialog(darBajaEmpleado,"Estado",true);
			Dialog bajaVentaIncorrecto = new Dialog(darBajaEmpleado,"Estado",true);
			Dialog seguroBajaVenta = new Dialog(darBajaEmpleado,"Confirmar Baja",true);
			//Modificacion venta
			Dialog darModificacionVenta = new Dialog(this,"Modificar datos de la venta",true);
			Dialog modificacionVentaCorrecto = new Dialog(darModificacionVenta,"Estado",true);
			Dialog modificacionVentaIncorrecto = new Dialog(darModificacionVenta,"Estado",true);
			//Consulta venta
			Dialog darConsultaVenta = new Dialog(this,"Consultar datos de la venta",true);
		//Productos
			//Alta producto
			Dialog darAltaProducto = new Dialog(this,"Añadir producto",true);
			Dialog altaProductoCorrecto = new Dialog(darAltaProducto,"Estado",true);
			Dialog altaProductoIncorrecto = new Dialog(darAltaProducto,"Estado",true);
			//Baja producto
			Dialog darBajaProducto = new Dialog(this,"Eliminar producto",true);
			Dialog bajaProductoCorrecto = new Dialog(darBajaProducto,"Estado",true);
			Dialog bajaProductoIncorrecto = new Dialog(darBajaProducto,"Estado",true);
			Dialog seguroBajaProducto = new Dialog(darBajaEmpleado,"Confirmar Baja",true);
			//Modificacion producto
			Dialog darModificacionProducto = new Dialog(this,"Modificar datos del producto",true);
			Dialog modificacionProductoCorrecto = new Dialog(darModificacionProducto,"Estado",true);
			Dialog modificacionProductoIncorrecto = new Dialog(darModificacionProducto,"Estado",true);
			//Consulta producto
			Dialog darConsultaProducto = new Dialog(this,"Consultar datos del producto",true);
	//Objetos para el menu
	MenuBar barraMenu = new MenuBar();
	Menu empleados = new Menu("Empleados");
	Menu ventas = new Menu("Ventas");
	Menu productos = new Menu("Productos");
	Menu otros = new Menu("Otros");
		//Items de Empleados
		MenuItem altaEmpleado = new MenuItem("Alta");
		MenuItem bajaEmpleado = new MenuItem("Baja");
		MenuItem modificacionEmpleado = new MenuItem("Modificación");
		MenuItem consultaEmpleado = new MenuItem("Consulta");
		//Items de Ventas
		MenuItem altaVentas = new MenuItem("Alta");
		MenuItem bajaVentas = new MenuItem("Baja");
		MenuItem modificacionVentas = new MenuItem("Modificación");
		MenuItem consultaVentas = new MenuItem("Consulta");
		//Items de Productos
		MenuItem altaProductos = new MenuItem("Alta");
		MenuItem bajaProductos = new MenuItem("Baja");
		MenuItem modificacionProductos = new MenuItem("Modificación");
		MenuItem consultaProductos = new MenuItem("Consulta");
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
		//BajaEmpleados
		Label lbBajaEmpleado = new Label ("Introduzca el empleado que se quiere eliminar:");
		Label lbDniBajaEmpleado = new Label ("DNI:");
		Label lbNombreBajaEmpleado = new Label ("Nombre:");
		TextField txDniBajaEmpleado = new TextField(10);
		TextField txNombreBajaEmpleado = new TextField(10);
		Choice listaBajaEmpleados = new Choice();
		Button btnAplicarBajaEmpleado = new Button("Aplicar");
		Button btnLimpiarBajaEmpleado = new Button("Limpiar");
			//estado bajaEmpleado
			Label lbBajaEmpleadoCorrecto = new Label("¡El empleado ha sido borrado correctamente!");
			Label lbBajaEmpleadoIncorrecto = new Label("No se ha podido realizar correctamente :(");
			//seguro bajaEmpleado
			Label lbSeguroBajaEmpleado = new Label("¿Seguro que quiere borrar?");
			Button btnSeguroBajaEmpleado = new Button("Aceptar");
			//empleado en uso
			Label lbEmpleadoEnUso = new Label("Este empleado esta relaccionado con una venta");
		//ModificacionEmpleados
		Label lbModificacionEmpleado = new Label("Introduzca los nuevos datos:");
		Label lbDniModificacionEmpleado2 = new Label("DNI:");
		Label lbNombreModificacionEmpleado2 = new Label("Nombre:");
		Label lbApellidoModificacionEmpleado2 = new Label("Apellido:");
		TextField txDniModificacionEmpleado2 = new TextField(10);
		TextField txNombreModificacionEmpleado2 = new TextField(10);
		TextField txApellidoModificacionEmpleado2 = new TextField(10);
		Choice listaEmpleados = new Choice();
		Button btnAplicarModificacionEmpleado = new Button("Aplicar");
		Button btnLimpiarModificacionEmpleado = new Button("Limpiar");
			//Estado modificacionEmpleado
			Label lbModificacionEmpleadoCorrecto = new Label("¡Se ha modificado correctamente!");
			Label lbModificacionEmpleadoIncorrecto = new Label("No se ha podido realizar correctamente :(");
		//ConsultaEmpleados
		Label lbConsultaEmpleado = new Label("Datos de empleados");
		TextArea txConsultaEmpleado = new TextArea(11,55);
		Button exportarPDF = new Button("Exportar a PDF");
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
		//Baja venta
		Label lbBajaVenta = new Label ("Introduzca la venta que se quiere eliminar:");
		Label lbFormaPagoBajaVenta = new Label ("Forma de Pago:");
		Label lbFechaBajaVenta = new Label ("                Fecha:");
		Label lbImporteBajaVenta = new Label ("              Importe:");
		TextField txFormaPagoBajaVenta = new TextField(10);
		TextField txFechaBajaVenta = new TextField(10);
		TextField txImporteBajaVenta = new TextField(10);
		Choice listaBajaVenta = new Choice();
		Button btnAplicarBajaVenta = new Button("Aplicar");
		Button btnLimpiarBajaVenta = new Button("Limpiar");
			//estado bajaEmpleado
			Label lbBajaVentaCorrecto = new Label("¡La venta ha sido borrado correctamente!");
			Label lbBajaVentaIncorrecto = new Label("No se ha podido realizar correctamente :(");
			//seguro bajaEmpleado
			Label lbSeguroBajaVenta1 = new Label("¡Atención!");
			Label lbSeguroBajaVenta2 = new Label("borrar una venta podría no ser legal");
			Label lbSeguroBajaVenta3 = new Label("¿Seguro que quiere borar?");
			Button btnSeguroBajaVenta = new Button("Aceptar");
		//ModificacionVenta
		Label lbModificacionVenta = new Label("Introduzca los nuevos datos:");
		Label lbFormaModificacionVenta2 = new Label("Forma de pago:");
		Label lbFechaModificacionVenta2 = new Label("Fecha (AAAA-MM-DD):");
		Label lbImporteModificacionVenta2 = new Label("Importe:");
		TextField txFormaModificacionVenta2 = new TextField(10);
		TextField txFechaModificacionVenta2 = new TextField(10);
		TextField txImporteModificacionVenta2 = new TextField(10);
		Choice listaVenta = new Choice();
		Button btnAplicarModificacionVenta = new Button("Aplicar");
		Button btnLimpiarModificacionVenta = new Button("Limpiar");
		Panel panelVenta1 = new Panel();
		Panel panelVenta2 = new Panel();
		Panel panelVenta3 = new Panel();
			//Estado modificacionVenta
			Label lbModificacionVentaCorrecto = new Label("¡Se ha modificado correctamente!");
			Label lbModificacionVentaIncorrecto = new Label("No se ha podido realizar correctamente :(");
		//ConsultaVenta
		Label lbConsultaVenta = new Label("Datos de ventas:");
		TextArea txConsultaVenta = new TextArea(11,55);
		Button exportarPDF2 = new Button("Exportar a PDF");
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
		//BajaProducto
		Label lbBajaProducto = new Label ("Introduzca el producto que se quiere eliminar:");
		Label lbNombreBajaProducto = new Label ("Nombre:");
		TextField txNombreBajaProducto = new TextField(10);
		Choice listaBajaProducto = new Choice();
		Button btnAplicarBajaProducto = new Button("Aplicar");
		Button btnLimpiarBajaProducto = new Button("Limpiar");
		Panel panelProducto2 = new Panel();
			//estado bajaProducto
			Label lbBajaProductoCorrecto = new Label("¡El producto ha sido borrado correctamente!");
			Label lbBajaProductoIncorrecto = new Label("No se ha podido realizar correctamente :(");
			//seguro bajaProducto
			Label lbSeguroBajaProducto = new Label("¿Seguro que quiere borrar?");
			Button btnSeguroBajaProducto = new Button("Aceptar");
		//ModificacionProducto
		Label lbModificacionProducto = new Label("Introduzca los nuevos datos:");
		Label lbNombreModificacionProducto2 = new Label("Nombre:");
		Label lbPrecioModificacionProducto2 = new Label("Precio:");
		TextField txNombreModificacionProducto2 = new TextField(10);
		TextField txPrecioModificacionProducto2 = new TextField(10);
		Choice listaProducto = new Choice();
		Button btnAplicarModificacionProducto = new Button("Aplicar");
		Button btnLimpiarModificacionProducto = new Button("Limpiar");
			//Estado modificacionProducto
			Label lbModificacionProductoCorrecto = new Label("¡Se ha modificado correctamente!");
			Label lbModificacionProductoIncorrecto = new Label("No se ha podido realizar correctamente :(");
		//ConsultaProducto
		Label lbConsultaProducto = new Label("Datos de productos:");
		TextArea txConsultaProducto = new TextArea(11,55);
		Button exportarPDF3 = new Button("Exportar a PDF");
	
		
		
		
		
		
		
	public GestionAdmin()
	{
			//Menú
			setLayout(new FlowLayout());
			setTitle("Menú");
			setLocationRelativeTo(null);
			setSize(500,270);
			setIconImage(new ImageIcon("1.png").getImage());
				//barra del menú
				setMenuBar(barraMenu);
				barraMenu.add(empleados);
				barraMenu.add(ventas);
				barraMenu.add(productos);
				barraMenu.add(otros);
				otros.add(ayuda);
				ayuda.addActionListener(this);
				empleados.add(altaEmpleado);
				altaEmpleado.addActionListener(this);
				empleados.add(bajaEmpleado);
				bajaEmpleado.addActionListener(this);
				empleados.add(modificacionEmpleado);
				modificacionEmpleado.addActionListener(this);
				empleados.add(consultaEmpleado);
				consultaEmpleado.addActionListener(this);
				ventas.add(altaVentas);
				altaVentas.addActionListener(this);
				ventas.add(bajaVentas);
				bajaVentas.addActionListener(this);
				ventas.add(modificacionVentas);
				modificacionVentas.addActionListener(this);
				ventas.add(consultaVentas);
				consultaVentas.addActionListener(this);
				productos.add(altaProductos);
				altaProductos.addActionListener(this);
				productos.add(bajaProductos);
				bajaProductos.addActionListener(this);
				productos.add(modificacionProductos);
				modificacionProductos.addActionListener(this);
				productos.add(consultaProductos);
				consultaProductos.addActionListener(this);
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
				//Baja Empleado
				darBajaEmpleado.setLayout(new FlowLayout());
				darBajaEmpleado.setSize(320,150);
				darBajaEmpleado.setLocationRelativeTo(null);
				darBajaEmpleado.add(listaBajaEmpleados);
				listaBajaEmpleados.addItemListener(this);
				darBajaEmpleado.add(lbBajaEmpleado);
				darBajaEmpleado.add(lbDniBajaEmpleado);
				darBajaEmpleado.add(txDniBajaEmpleado);
				txDniBajaEmpleado.setEditable(false);
				darBajaEmpleado.add(lbNombreBajaEmpleado);
				darBajaEmpleado.add(txNombreBajaEmpleado);
				txNombreBajaEmpleado.setEditable(false);
				darBajaEmpleado.add(btnAplicarBajaEmpleado);
				btnAplicarBajaEmpleado.addActionListener(this);
				darBajaEmpleado.add(btnLimpiarBajaEmpleado);
				btnLimpiarBajaEmpleado.addActionListener(this);
				darBajaEmpleado.addWindowListener(this);
				darBajaEmpleado.setVisible(false);
				darBajaEmpleado.setResizable(false);
					//Baja correcta
					bajaEmpleadoCorrecto.setLayout(new FlowLayout());
					bajaEmpleadoCorrecto.setSize(280,100);
					bajaEmpleadoCorrecto.setLocationRelativeTo(null);
					bajaEmpleadoCorrecto.add(lbBajaEmpleadoCorrecto);
					bajaEmpleadoCorrecto.addWindowListener(this);
					bajaEmpleadoCorrecto.setVisible(false);
					bajaEmpleadoCorrecto.setResizable(false);
					//Baja incorrecta
					bajaEmpleadoIncorrecto.setLayout(new FlowLayout());
					bajaEmpleadoIncorrecto.setSize(280,100);
					bajaEmpleadoIncorrecto.setLocationRelativeTo(null);
					bajaEmpleadoIncorrecto.add(lbBajaEmpleadoIncorrecto);
					bajaEmpleadoIncorrecto.addWindowListener(this);
					bajaEmpleadoIncorrecto.setVisible(false);
					bajaEmpleadoIncorrecto.setResizable(false);
					//Seguro baja
					seguroBajaEmpleado.setLayout(new FlowLayout());
					seguroBajaEmpleado.setSize(200,100);
					seguroBajaEmpleado.setLocationRelativeTo(null);
					seguroBajaEmpleado.add(lbSeguroBajaEmpleado);
					seguroBajaEmpleado.add(btnSeguroBajaEmpleado);
					btnSeguroBajaEmpleado.addActionListener(this);
					seguroBajaEmpleado.addWindowListener(this);
					seguroBajaEmpleado.setVisible(false);
					seguroBajaEmpleado.setResizable(false);
				//Modificacion Empleado
				darModificacionEmpleado.setLayout(new FlowLayout());
				darModificacionEmpleado.setSize(200,210);
				darModificacionEmpleado.setLocationRelativeTo(null);
				darModificacionEmpleado.add(listaEmpleados);
				listaEmpleados.addItemListener(this);
				listaEmpleados.add("Seleccionar un empleado");
				darModificacionEmpleado.add(lbModificacionEmpleado);
				darModificacionEmpleado.add(lbDniModificacionEmpleado2);
				darModificacionEmpleado.add(txDniModificacionEmpleado2);
				darModificacionEmpleado.add(lbNombreModificacionEmpleado2);
				darModificacionEmpleado.add(txNombreModificacionEmpleado2);
				darModificacionEmpleado.add(lbApellidoModificacionEmpleado2);
				darModificacionEmpleado.add(txApellidoModificacionEmpleado2);
				darModificacionEmpleado.add(btnAplicarModificacionEmpleado);
				btnAplicarModificacionEmpleado.addActionListener(this);
				darModificacionEmpleado.add(btnLimpiarModificacionEmpleado);
				btnLimpiarModificacionEmpleado.addActionListener(this);
				darModificacionEmpleado.addWindowListener(this);
				darModificacionEmpleado.setVisible(false);
				darModificacionEmpleado.setResizable(false);
					//Modificacion correcta
					modificacionEmpleadoCorrecto.setLayout(new FlowLayout());
					modificacionEmpleadoCorrecto.setSize(280,100);
					modificacionEmpleadoCorrecto.setLocationRelativeTo(null);
					modificacionEmpleadoCorrecto.add(lbModificacionEmpleadoCorrecto);
					modificacionEmpleadoCorrecto.addWindowListener(this);
					modificacionEmpleadoCorrecto.setVisible(false);
					modificacionEmpleadoCorrecto.setResizable(false);
					//Modificacion incorrecta
					modificacionEmpleadoIncorrecto.setLayout(new FlowLayout());
					modificacionEmpleadoIncorrecto.setSize(280,100);
					modificacionEmpleadoIncorrecto.setLocationRelativeTo(null);
					modificacionEmpleadoIncorrecto.add(lbModificacionEmpleadoIncorrecto);
					modificacionEmpleadoIncorrecto.addWindowListener(this);
					modificacionEmpleadoIncorrecto.setVisible(false);
					modificacionEmpleadoIncorrecto.setResizable(false);
				//Consulta Empleados
				darConsultaEmpleado.setLayout(new FlowLayout());
				darConsultaEmpleado.setSize(470,320);
				darConsultaEmpleado.setLocationRelativeTo(null);
				darConsultaEmpleado.add(lbConsultaEmpleado);
				darConsultaEmpleado.add(txConsultaEmpleado);
				darConsultaEmpleado.add(exportarPDF);
				exportarPDF.addActionListener(this);
				darConsultaEmpleado.addWindowListener(this);
				darConsultaEmpleado.setVisible(false);
				darConsultaEmpleado.setResizable(true);
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
					//Empleado en uso
					empleadoEnUso.setLayout(new FlowLayout());
					empleadoEnUso.setSize(280,80);
					empleadoEnUso.setLocationRelativeTo(null);
					empleadoEnUso.add(lbEmpleadoEnUso);
					empleadoEnUso.addWindowListener(this);
					empleadoEnUso.setVisible(false);
					empleadoEnUso.setResizable(false);
				//Baja Venta
				darBajaVenta.setLayout(new FlowLayout());
				darBajaVenta.setSize(260,200);
				darBajaVenta.setLocationRelativeTo(null);
				darBajaVenta.add(listaBajaVenta);
				listaBajaVenta.addItemListener(this);
				darBajaVenta.add(lbBajaVenta);
				darBajaVenta.add(lbFormaPagoBajaVenta);
				darBajaVenta.add(txFormaPagoBajaVenta);
				txFormaPagoBajaVenta.setEditable(false);
				darBajaVenta.add(lbFechaBajaVenta);
				darBajaVenta.add(txFechaBajaVenta);
				txFechaBajaVenta.setEditable(false);
				darBajaVenta.add(lbImporteBajaVenta);
				darBajaVenta.add(txImporteBajaVenta);
				txImporteBajaVenta.setEditable(false);
				darBajaVenta.add(btnAplicarBajaVenta);
				btnAplicarBajaVenta.addActionListener(this);
				darBajaVenta.add(btnLimpiarBajaVenta);
				btnLimpiarBajaVenta.addActionListener(this);
				darBajaVenta.addWindowListener(this);
				darBajaVenta.setVisible(false);
				darBajaVenta.setResizable(false);
					//Baja correcta
					bajaVentaCorrecto.setLayout(new FlowLayout());
					bajaVentaCorrecto.setSize(280,100);
					bajaVentaCorrecto.setLocationRelativeTo(null);
					bajaVentaCorrecto.add(lbBajaVentaCorrecto);
					bajaVentaCorrecto.addWindowListener(this);
					bajaVentaCorrecto.setVisible(false);
					bajaVentaCorrecto.setResizable(false);
					//Baja incorrecta
					bajaVentaIncorrecto.setLayout(new FlowLayout());
					bajaVentaIncorrecto.setSize(280,100);
					bajaVentaIncorrecto.setLocationRelativeTo(null);
					bajaVentaIncorrecto.add(lbBajaVentaIncorrecto);
					bajaVentaIncorrecto.addWindowListener(this);
					bajaVentaIncorrecto.setVisible(false);
					bajaVentaIncorrecto.setResizable(false);
					//Seguro baja
					seguroBajaVenta.setLayout(new FlowLayout());
					seguroBajaVenta.setSize(230,150);
					seguroBajaVenta.setLocationRelativeTo(null);
					seguroBajaVenta.add(lbSeguroBajaVenta1);
					seguroBajaVenta.add(lbSeguroBajaVenta2);
					seguroBajaVenta.add(lbSeguroBajaVenta3);
					seguroBajaVenta.add(btnSeguroBajaVenta);
					btnSeguroBajaVenta.addActionListener(this);
					seguroBajaVenta.addWindowListener(this);
					seguroBajaVenta.setVisible(false);
					seguroBajaVenta.setResizable(false);
				//Modificacion Venta
				darModificacionVenta.setLayout(new FlowLayout());
				darModificacionVenta.setSize(250,300);
				darModificacionVenta.setLocationRelativeTo(null);
				darModificacionVenta.add(listaVenta);
				listaVenta.addItemListener(this);
				
				darModificacionVenta.add(lbModificacionVenta);
				darModificacionVenta.add(lbFormaModificacionVenta2);
				darModificacionVenta.add(txFormaModificacionVenta2);
				darModificacionVenta.add(lbFechaModificacionVenta2);
				darModificacionVenta.add(txFechaModificacionVenta2);
				darModificacionVenta.add(lbImporteModificacionVenta2);
				darModificacionVenta.add(txImporteModificacionVenta2);
				panelVenta3.add(btnAplicarModificacionVenta);
				btnAplicarModificacionVenta.addActionListener(this);
				panelVenta3.add(btnLimpiarModificacionVenta);
				btnLimpiarModificacionVenta.addActionListener(this);
				darModificacionVenta.add(panelVenta3);
				darModificacionVenta.addWindowListener(this);
				darModificacionVenta.setVisible(false);
				darModificacionVenta.setResizable(false);
					//Modificacion correcta
					modificacionVentaCorrecto.setLayout(new FlowLayout());
					modificacionVentaCorrecto.setSize(280,100);
					modificacionVentaCorrecto.setLocationRelativeTo(null);
					modificacionVentaCorrecto.add(lbModificacionVentaCorrecto);
					modificacionVentaCorrecto.addWindowListener(this);
					modificacionVentaCorrecto.setVisible(false);
					modificacionVentaCorrecto.setResizable(false);
					//Modificacion incorrecta
					modificacionVentaIncorrecto.setLayout(new FlowLayout());
					modificacionVentaIncorrecto.setSize(280,100);
					modificacionVentaIncorrecto.setLocationRelativeTo(null);
					modificacionVentaIncorrecto.add(lbModificacionVentaIncorrecto);
					modificacionVentaIncorrecto.addWindowListener(this);
					modificacionVentaIncorrecto.setVisible(false);
					modificacionVentaIncorrecto.setResizable(false);
				//Consulta Venta
				darConsultaVenta.setLayout(new FlowLayout());
				darConsultaVenta.setSize(470,320);
				darConsultaVenta.setLocationRelativeTo(null);
				darConsultaVenta.add(lbConsultaVenta);
				darConsultaVenta.add(txConsultaVenta);
				darConsultaVenta.add(exportarPDF2);
				exportarPDF2.addActionListener(this);
				darConsultaVenta.addWindowListener(this);
				darConsultaVenta.setVisible(false);
				darConsultaVenta.setResizable(true);
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
				//Baja Producto
				darBajaProducto.setLayout(new FlowLayout());
				darBajaProducto.setSize(280,150);
				darBajaProducto.setLocationRelativeTo(null);
				darBajaProducto.add(listaBajaProducto);
				listaBajaProducto.addItemListener(this);
				darBajaProducto.add(lbBajaProducto);
				darBajaProducto.add(lbNombreBajaProducto);
				darBajaProducto.add(txNombreBajaProducto);
				txNombreBajaProducto.setEditable(false);
				panelProducto2.add(btnAplicarBajaProducto);
				btnAplicarBajaProducto.addActionListener(this);
				panelProducto2.add(btnLimpiarBajaProducto);
				btnLimpiarBajaProducto.addActionListener(this);
				darBajaProducto.add(panelProducto2);
				darBajaProducto.addWindowListener(this);
				darBajaProducto.setVisible(false);
				darBajaProducto.setResizable(false);
					//Baja correcta
					bajaProductoCorrecto.setLayout(new FlowLayout());
					bajaProductoCorrecto.setSize(280,100);
					bajaProductoCorrecto.setLocationRelativeTo(null);
					bajaProductoCorrecto.add(lbBajaProductoCorrecto);
					bajaProductoCorrecto.addWindowListener(this);
					bajaProductoCorrecto.setVisible(false);
					bajaProductoCorrecto.setResizable(false);
					//Baja incorrecta
					bajaProductoIncorrecto.setLayout(new FlowLayout());
					bajaProductoIncorrecto.setSize(280,100);
					bajaProductoIncorrecto.setLocationRelativeTo(null);
					bajaProductoIncorrecto.add(lbBajaProductoIncorrecto);
					bajaProductoIncorrecto.addWindowListener(this);
					bajaProductoIncorrecto.setVisible(false);
					bajaProductoIncorrecto.setResizable(false);
					//Seguro baja
					seguroBajaProducto.setLayout(new FlowLayout());
					seguroBajaProducto.setSize(200,100);
					seguroBajaProducto.setLocationRelativeTo(null);
					seguroBajaProducto.add(lbSeguroBajaProducto);
					seguroBajaProducto.add(btnSeguroBajaProducto);
					btnSeguroBajaProducto.addActionListener(this);
					seguroBajaProducto.addWindowListener(this);
					seguroBajaProducto.setVisible(false);
					seguroBajaProducto.setResizable(false);
				//Modificacion Producto
				darModificacionProducto.setLayout(new FlowLayout());
				darModificacionProducto.setSize(200,240);
				darModificacionProducto.setLocationRelativeTo(null);
				darModificacionProducto.add(listaProducto);
				listaProducto.addItemListener(this);
				darModificacionProducto.add(lbModificacionProducto);
				darModificacionProducto.add(lbNombreModificacionProducto2);
				darModificacionProducto.add(txNombreModificacionProducto2);
				darModificacionProducto.add(lbPrecioModificacionProducto2);
				darModificacionProducto.add(txPrecioModificacionProducto2);
				darModificacionProducto.add(btnAplicarModificacionProducto);
				btnAplicarModificacionProducto.addActionListener(this);
				darModificacionProducto.add(btnLimpiarModificacionProducto);
				btnLimpiarModificacionProducto.addActionListener(this);
				darModificacionProducto.addWindowListener(this);
				darModificacionProducto.setVisible(false);
				darModificacionProducto.setResizable(false);
					//Modificacion correcta
					modificacionProductoCorrecto.setLayout(new FlowLayout());
					modificacionProductoCorrecto.setSize(280,100);
					modificacionProductoCorrecto.setLocationRelativeTo(null);
					modificacionProductoCorrecto.add(lbModificacionProductoCorrecto);
					modificacionProductoCorrecto.addWindowListener(this);
					modificacionProductoCorrecto.setVisible(false);
					modificacionProductoCorrecto.setResizable(false);
					//Modificacion incorrecta
					modificacionProductoIncorrecto.setLayout(new FlowLayout());
					modificacionProductoIncorrecto.setSize(280,100);
					modificacionProductoIncorrecto.setLocationRelativeTo(null);
					modificacionProductoIncorrecto.add(lbModificacionProductoIncorrecto);
					modificacionProductoIncorrecto.addWindowListener(this);
					modificacionProductoIncorrecto.setVisible(false);
					modificacionProductoIncorrecto.setResizable(false);
				//Consulta Producto
				darConsultaProducto.setLayout(new FlowLayout());
				darConsultaProducto.setSize(470,320);
				darConsultaProducto.setLocationRelativeTo(null);
				darConsultaProducto.add(lbConsultaProducto);
				darConsultaProducto.add(txConsultaProducto);
				darConsultaProducto.add(exportarPDF3);
				exportarPDF3.addActionListener(this);
				darConsultaProducto.addWindowListener(this);
				darConsultaProducto.setVisible(false);
				darConsultaProducto.setResizable(true);
				
				Date fecha = new Date();
				contenidoLOG = "["+fecha.toString()+"][Administrador][Entra al sistema]";
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
			//BajaEmpleado
			else if(darBajaEmpleado.isActive())
			{
				darBajaEmpleado.setVisible(false);
			}
			else if(bajaEmpleadoCorrecto.isActive())
			{
				bajaEmpleadoCorrecto.setVisible(false);
			}
			else if(bajaEmpleadoIncorrecto.isActive())
			{
				bajaEmpleadoIncorrecto.setVisible(false);
			}
			else if(seguroBajaEmpleado.isActive())
			{
				seguroBajaEmpleado.setVisible(false);
			}
			else if(empleadoEnUso.isActive())
			{
				empleadoEnUso.setVisible(false);
			}
			//ModificacionEmpleado
			else if(darModificacionEmpleado.isActive())
			{
				darModificacionEmpleado.setVisible(false);
			}
			else if(modificacionEmpleadoCorrecto.isActive())
			{
				modificacionEmpleadoCorrecto.setVisible(false);
			}
			else if(modificacionEmpleadoIncorrecto.isActive())
			{
				modificacionEmpleadoIncorrecto.setVisible(false);
			}
			//ConsultaEmpleado
			else if(darConsultaEmpleado.isActive())
			{
				darConsultaEmpleado.setVisible(false);
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
			//BajaVenta
			else if(darBajaVenta.isActive())
			{
				darBajaVenta.setVisible(false);
			}
			else if(bajaVentaCorrecto.isActive())
			{
				bajaVentaCorrecto.setVisible(false);
			}
			else if(bajaVentaIncorrecto.isActive())
			{
				bajaVentaIncorrecto.setVisible(false);
			}
			//ModificacionVenta
			else if(darModificacionVenta.isActive())
			{
				darModificacionVenta.setVisible(false);
			}
			else if(modificacionVentaCorrecto.isActive())
			{
				modificacionVentaCorrecto.setVisible(false);
			}
			else if(modificacionVentaIncorrecto.isActive())
			{
				modificacionVentaIncorrecto.setVisible(false);
			}
			//ConsultaVenta
			else if(darConsultaVenta.isActive())
			{
				darConsultaVenta.setVisible(false);
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
			//BajaProducto
			else if(darBajaProducto.isActive())
			{
				darBajaProducto.setVisible(false);
			}
			else if(bajaProductoCorrecto.isActive())
			{
				bajaProductoCorrecto.setVisible(false);
			}
			else if(bajaProductoIncorrecto.isActive())
			{
				bajaProductoIncorrecto.setVisible(false);
			}
			else if(seguroBajaProducto.isActive())
			{
				seguroBajaProducto.setVisible(false);
			}
			//ModificacionProducto
			else if(darModificacionProducto.isActive())
			{
				darModificacionProducto.setVisible(false);
			}
			else if(modificacionProductoCorrecto.isActive())
			{
				modificacionProductoCorrecto.setVisible(false);
			}
			else if(modificacionProductoIncorrecto.isActive())
			{
				modificacionProductoIncorrecto.setVisible(false);
			}
			//ConsultaProducto
			else if(darConsultaProducto.isActive())
			{
				darConsultaProducto.setVisible(false);
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
			contenidoLOG = contenidoLOG + "\n" + "["+fecha.toString()+"][Administrador][Sale del sistema]";
			movimientosLog(contenidoLOG);
			System.exit(0);
		}
		
		
		//Ayuda de barra menú
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
		else if(bajaEmpleado.equals(e.getSource()))
		{
			listaBajaEmpleados.removeAll();
			listaBajaEmpleados.add("Selecciona un empleado");
			txDniBajaEmpleado.setText("");
			txNombreBajaEmpleado.setText("");
			
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
					String s = rs.getString("idEmpleado");
					s = s + "-"+ rs.getString("dniEmpleado");
					s = s + "-"+ rs.getString("nombreEmpleado");
					listaBajaEmpleados.add(s);
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
			darBajaEmpleado.setVisible(true);
		}
		else if(modificacionEmpleado.equals(e.getSource()))
		{
			listaEmpleados.removeAll();
			listaEmpleados.add("Selecciona un empleado");
			txDniModificacionEmpleado2.setText("");
			txNombreModificacionEmpleado2.setText("");
			txApellidoModificacionEmpleado2.setText("");
			
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
					String s = rs.getString("dniEmpleado");
					s = s + "-"+ rs.getString("nombreEmpleado");
					s = s + "-"+ rs.getString("apellidoEmpleado");
					listaEmpleados.add(s);
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
			darModificacionEmpleado.setVisible(true);
		}
		else if(consultaEmpleado.equals(e.getSource()))
		{
			try
			{
				String consulta="";
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				rs= statement.executeQuery("SELECT * FROM empleados");
				while(rs.next())
				{
				String x = "Nº" + rs.getString("idEmpleado");
				x = x + ", dni:"+ rs.getString("dniEmpleado");
				x = x + ", Nombre:"+ rs.getString("nombreEmpleado");
				x = x + ", Apellido: "+ rs.getString("apellidoEmpleado");
				consulta = consulta + x + "\n";
				}
				txConsultaEmpleado.setText(consulta);
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			darConsultaEmpleado.setVisible(true);
		}
		//Acceder a Ventas desde la barra
		else if(altaVentas.equals(e.getSource())) 
		{
			try
			{
				listaAltaEmpleadoFK.removeAll();
				listaAltaEmpleadoFK.add("Selecciona un empleado");
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
		else if(bajaVentas.equals(e.getSource()))
		{
			listaBajaVenta.removeAll();
			listaBajaVenta.add("Selecciona una venta");
			txFormaPagoBajaVenta.setText("");
			txFechaBajaVenta.setText("");
			txImporteBajaVenta.setText("");
			
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				rs= statement.executeQuery("SELECT * FROM ventas");
				while(rs.next())
				{
					String s = rs.getString("idVenta");
					s = s + "-"+ rs.getString("formaPagoVenta");
					s = s + "-"+ rs.getString("fechaVenta");
					s = s + "-"+ rs.getString("importeVenta");
					listaBajaVenta.add(s);
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
			darBajaVenta.setVisible(true);
		}
		else if(modificacionVentas.equals(e.getSource()))
		{
			listaVenta.removeAll();
			listaVenta.add("Selecciona una venta");
			txImporteModificacionVenta2.setText("");
			txFechaModificacionVenta2.setText("");
			txFormaModificacionVenta2.setText("");
			
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				rs= statement.executeQuery("SELECT * FROM ventas");
				while(rs.next())
				{
					String s = rs.getString("idVenta");
					s = s + "/"+ rs.getString("fechaVenta");
					s = s + "/"+ rs.getString("formaPagoVenta");
					s = s + "/"+ rs.getString("importeVenta");
					listaVenta.add(s);
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
			darModificacionVenta.setVisible(true);
		}
		else if(consultaVentas.equals(e.getSource()))
		{
			try
			{
				String consulta = "";
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				rs= statement.executeQuery("SELECT idVenta,formaPagoVenta,fechaVenta,importeVenta,idEmpleadoFK,idEmpleado,nombreEmpleado FROM ventas, empleados where ventas.idEmpleadoFK=empleados.idEmpleado");
				while(rs.next())
				{
				String x = "Nº" + rs.getString("idVenta");
				x = x + ", Tipo de pago:"+ rs.getString("formaPagoVenta");
				x = x + ", fecha:"+ rs.getString("fechaVenta");
				x = x + ", importe:"+ rs.getString("importeVenta")+"€";
				x = x + " - Empleado que hizo la venta: " + rs.getString("nombreEmpleado");
				consulta = consulta + x + "\n";
				}
				txConsultaVenta.setText(consulta); 
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			darConsultaVenta.setVisible(true);
		}
		//Acceder a Productos desde la barra
		else if(altaProductos.equals(e.getSource()))
		{
			darAltaProducto.setVisible(true);
		}
		else if(bajaProductos.equals(e.getSource()))
		{
			listaBajaProducto.removeAll();
			listaBajaProducto.add("Selecciona un producto");
			txNombreBajaProducto.setText("");
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				rs= statement.executeQuery("SELECT * FROM productos");
				while(rs.next())
				{
					String s = rs.getString("idProducto");
					s = s + "-"+ rs.getString("nombreProducto");
					s = s + "-"+ rs.getString("precioProducto")+"€";
					listaBajaProducto.add(s);
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
			darBajaProducto.setVisible(true);
		}
		else if(modificacionProductos.equals(e.getSource()))
		{
			listaProducto.removeAll();
			listaProducto.add("Selecciona un producto");
			txNombreModificacionProducto2.setText("");
			txPrecioModificacionProducto2.setText("");
			
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				rs= statement.executeQuery("SELECT * FROM productos");
				while(rs.next())
				{
					String s = rs.getString("idProducto");
					s = s + "-"+ rs.getString("nombreProducto");
					s = s + "-"+ rs.getString("precioProducto");
					listaProducto.add(s);
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
			darModificacionProducto.setVisible(true);
		}
		else if(consultaProductos.equals(e.getSource()))
		{
			try
			{
				String consulta = "";
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				rs= statement.executeQuery("SELECT * FROM productos");
				while(rs.next())
				{
				String x = "Nº" + rs.getString("idProducto");
				x = x + ", Nombre:"+ rs.getString("nombreProducto");
				x = x + ", Precio: "+ rs.getString("precioProducto")+"€";
				consulta = consulta + x + "\n";
				}
				txConsultaProducto.setText(consulta);
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			darConsultaProducto.setVisible(true);
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
		else if (btnLimpiarBajaEmpleado.equals(e.getSource()))
		{
			txDniBajaEmpleado.selectAll();
			txNombreBajaEmpleado.selectAll();
			
			txDniBajaEmpleado.setText("");
			txNombreBajaEmpleado.setText("");
		}
		else if (btnLimpiarModificacionEmpleado.equals(e.getSource()))
		{
			txDniModificacionEmpleado2.selectAll();
			txNombreModificacionEmpleado2.selectAll();
			txApellidoModificacionEmpleado2.selectAll();
			
			txDniModificacionEmpleado2.setText("");
			txNombreModificacionEmpleado2.setText("");
			txApellidoModificacionEmpleado2.setText("");
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
		else if(btnLimpiarBajaVenta.equals(e.getSource()))
		{
			txFormaPagoBajaVenta.selectAll();
			txFechaBajaVenta.selectAll();
			txImporteBajaVenta.selectAll();
			
			txFormaPagoBajaVenta.setText("");
			txFechaBajaVenta.setText("");
			txImporteBajaVenta.setText("");
		}
		else if (btnLimpiarModificacionVenta.equals(e.getSource()))
		{
			txFormaModificacionVenta2.selectAll();
			txFechaModificacionVenta2.selectAll();
			txImporteModificacionVenta2.selectAll();
			
			txFormaModificacionVenta2.setText("");
			txFechaModificacionVenta2.setText("");
			txImporteModificacionVenta2.setText("");
		}
			//Productos
		else if(btnLimpiarAltaProducto.equals(e.getSource()))
		{
			txNombreAltaProducto.selectAll();
			txPrecioAltaProducto.selectAll();
			
			txNombreAltaProducto.setText("");
			txPrecioAltaProducto.setText("");
		}
		else if (btnLimpiarBajaProducto.equals(e.getSource()))
		{
			txNombreBajaEmpleado.selectAll();
			
			txNombreBajaEmpleado.setText("");
		}
		else if (btnLimpiarModificacionProducto.equals(e.getSource()))
		{
			txNombreModificacionProducto2.selectAll();
			txPrecioModificacionProducto2.selectAll();
			
			txNombreModificacionProducto2.setText("");
			txPrecioModificacionProducto2.setText("");
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
						contenidoLOG = contenidoLOG +"\n"+ "["+fecha.toString()+"][Administrador][Alta empleado: "+sentencia+"]";
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
		else if(btnAplicarBajaEmpleado.equals(e.getSource()))
		{
			seguroBajaEmpleado.setVisible(true);
		}
		else if(btnSeguroBajaEmpleado.equals(e.getSource())) 
		{
			String dniEmpleado = txDniBajaEmpleado.getText();
			String nombreEmpleado = txNombreBajaEmpleado.getText();
			
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				String sentencia = "DELETE FROM empleados WHERE dniEmpleado='"+dniEmpleado+"' and nombreEmpleado='"+nombreEmpleado+"'";
	
				int resp = statement.executeUpdate(sentencia);
				
		        if (resp > 0) 
		        {
		            bajaEmpleadoCorrecto.setVisible(true);
		            Date fecha = new Date();
					contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Baja empleado: "+sentencia+"]";
		        }
		        else
		        {
		        	bajaEmpleadoIncorrecto.setVisible(true);
		        }
				txDniBajaEmpleado.setText("");
				txNombreBajaEmpleado.setText("");
				
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
				bajaEmpleadoIncorrecto.setVisible(true);
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
				empleadoEnUso.setVisible(true);
			}
			seguroBajaEmpleado.setVisible(false);
		}
		else if(btnAplicarModificacionEmpleado.equals(e.getSource()))
		{
			String dniEmpleado = txDniModificacionEmpleado2.getText();
			String nombreEmpleado = txNombreModificacionEmpleado2.getText();
			String apellidoEmpleado = txApellidoModificacionEmpleado2.getText();
				
				try
				{
					String sentencia = "UPDATE empleados SET dniEmpleado ='"+dniEmpleado+"',nombreEmpleado='"+nombreEmpleado+"',apellidoEmpleado='"+apellidoEmpleado+"' WHERE dniEmpleado='"+txDniModificacionEmpleado2.getText()+"';";
					int resp = statement.executeUpdate(sentencia);
					
			        if (resp > 0) 
			        {
			            modificacionEmpleadoCorrecto.setVisible(true);
			            Date fecha = new Date();
						contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Modificación empleado: "+sentencia+"]";
			        }
			        else
			        {
			        	modificacionEmpleadoIncorrecto.setVisible(true);
			        }
					txDniModificacionEmpleado2.setText("");
					txNombreModificacionEmpleado2.setText("");
					txApellidoModificacionEmpleado2.setText("");
					
				}
				
				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
					modificacionEmpleadoIncorrecto.setVisible(true);
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
						contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Alta venta: "+sentencia+"]";
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
		else if(btnAplicarBajaVenta.equals(e.getSource()))
		{
			seguroBajaVenta.setVisible(true);
		}
		else if(btnSeguroBajaVenta.equals(e.getSource())) 
		{	
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				String sentencia = "DELETE FROM ventas WHERE idVenta='"+idVenta+"'";
	
				int resp = statement.executeUpdate(sentencia);
				
		        if (resp > 0) 
		        {
		            bajaVentaCorrecto.setVisible(true);
		            Date fecha = new Date();
					contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Baja Venta: "+sentencia+"]";
		        }
		        else
		        {
		        	bajaVentaIncorrecto.setVisible(true);
		        }
		        
				txFormaPagoBajaVenta.setText("");
				txFechaBajaVenta.setText("");
				txImporteBajaVenta.setText("");
				
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
				bajaVentaIncorrecto.setVisible(true);
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			seguroBajaVenta.setVisible(false);
		}
		else if(btnAplicarModificacionVenta.equals(e.getSource()))
		{
			String formaPagoVenta = txFormaModificacionVenta2.getText();
			String fechaVenta = txFechaModificacionVenta2.getText();
			String importeVenta = txImporteModificacionVenta2.getText();
				
				try
				{
					String sentencia = "UPDATE ventas SET formaPagoVenta ='"+formaPagoVenta+"',fechaVenta='"+fechaVenta+"',importeVenta='"+importeVenta+"' WHERE idVenta='"+idModificacionVenta+"';";
					int resp = statement.executeUpdate(sentencia);
					
			        if (resp > 0) 
			        {
			            modificacionVentaCorrecto.setVisible(true);
			            Date fecha = new Date();
						contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Modificación venta: "+sentencia+"]";
			        }
			        else
			        {
			        	modificacionVentaIncorrecto.setVisible(true);
			        }
					txFormaModificacionVenta2.setText("");
					txFechaModificacionVenta2.setText("");
					txImporteModificacionVenta2.setText("");
					
				}
				
				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
					modificacionVentaIncorrecto.setVisible(true);
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
						contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Alta producto: "+sentencia+"]";
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
		else if(btnAplicarBajaProducto.equals(e.getSource()))
		{
			seguroBajaProducto.setVisible(true);
		}
		else if(btnSeguroBajaProducto.equals(e.getSource()))
		{
			String nombreProducto = txNombreBajaProducto.getText();
			
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(driver);
				//Establecer la conexión con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				String sentencia = "DELETE FROM productos WHERE nombreProducto='"+nombreProducto+"'";
				int resp = statement.executeUpdate(sentencia);
				
		        if (resp > 0) 
		        {
		            bajaProductoCorrecto.setVisible(true);
		            Date fecha = new Date();
					contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Baja producto: "+sentencia+"]";
		        }
		        else
		        {
		        	bajaProductoIncorrecto.setVisible(true);
		        }
				txNombreBajaProducto.setText("");
				
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
				bajaProductoIncorrecto.setVisible(true);
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
				bajaProductoIncorrecto.setVisible(true);
			}
			seguroBajaProducto.setVisible(false);
		}
		else if(btnAplicarModificacionProducto.equals(e.getSource()))
		{
			String nombreProducto = txNombreModificacionProducto2.getText();
			String precioProducto = txPrecioModificacionProducto2.getText();
				
				try
				{
					String sentencia = "UPDATE productos SET nombreProducto='"+nombreProducto+"',precioProducto='"+precioProducto+"' WHERE nombreProducto='"+txNombreModificacionProducto2.getText()+"';";
					int resp = statement.executeUpdate(sentencia);
					
			        if (resp > 0) 
			        {
			            modificacionProductoCorrecto.setVisible(true);
			            Date fecha = new Date();
						contenidoLOG = contenidoLOG +"\n"+"["+fecha.toString()+"][Administrador][Modificación producto: "+sentencia+"]";
			        }
			        else
			        {
			        	modificacionProductoIncorrecto.setVisible(true);
			        }
					txNombreModificacionProducto2.setText("");
					txPrecioModificacionProducto2.setText("");
					
				}
				
				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
					modificacionProductoIncorrecto.setVisible(true);
				}
		}
		
		else if(exportarPDF.equals(e.getSource()))
		{
			//Se crea el documento
	        Document documento = new Document();
	        File path = new File ("empleados.pdf");
	        
	        try 
	        {
                //Se crea el OutPutStream para el fichero donde queremos dejar el pdf.
                FileOutputStream ficheroPdf = new FileOutputStream("empleados.pdf");
                //Se asocia el documento al OutputStream y se indica que el espacio entre
                //lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
                PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
                //Se abre el documento.
                documento.open();
                documento.add(new Paragraph(txConsultaEmpleado.getText()));
                documento.close();
                //Y ahora abrimos el fichero para mostrarlo
                Desktop.getDesktop().open(path);
                
            }
	        catch(FileNotFoundException error)
	        {
	        	System.out.println("Archivo no encontrado");
                System.out.println(error.getMessage());
            }
	        catch(IOException IOE) 
	        {
	        	System.out.println(IOE.getMessage());
            }
	        catch (Exception er) 
	        {
                er.printStackTrace();
            }
		}
		else if(exportarPDF2.equals(e.getSource()))
		{
			//Se crea el documento
	        Document documento = new Document();
	        File path = new File ("ventas.pdf");
	        
	        try 
	        {
                //Se crea el OutPutStream para el fichero donde queremos dejar el pdf.
                FileOutputStream ficheroPdf = new FileOutputStream("ventas.pdf");
                //Se asocia el documento al OutputStream y se indica que el espacio entre
                //lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
                PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
                //Se abre el documento.
                documento.open();
                documento.add(new Paragraph(txConsultaVenta.getText()));
                documento.close();
                //Y ahora abrimos el fichero para mostrarlo
                Desktop.getDesktop().open(path);
                
            }
	        catch(FileNotFoundException error)
	        {
	        	System.out.println("Archivo no encontrado");
                System.out.println(error.getMessage());
            }
	        catch(IOException IOE) 
	        {
	        	System.out.println(IOE.getMessage());
            }
	        catch (Exception er) 
	        {
                er.printStackTrace();
            }
		}
		else if(exportarPDF3.equals(e.getSource()))
		{
			//Se crea el documento
	        Document documento = new Document();
	        File path = new File ("productos.pdf");
	        
	        try 
	        {
                //Se crea el OutPutStream para el fichero donde queremos dejar el pdf.
                FileOutputStream ficheroPdf = new FileOutputStream("productos.pdf");
                //Se asocia el documento al OutputStream y se indica que el espacio entre
                //lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
                PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
                //Se abre el documento.
                documento.open();
                documento.add(new Paragraph(txConsultaProducto.getText()));
                documento.close();
                //Y ahora abrimos el fichero para mostrarlo
                Desktop.getDesktop().open(path);
                
            }
	        catch(FileNotFoundException error)
	        {
	        	System.out.println("Archivo no encontrado");
                System.out.println(error.getMessage());
            }
	        catch(IOException IOE) 
	        {
	        	System.out.println(IOE.getMessage());
            }
	        catch (Exception er) 
	        {
                er.printStackTrace();
            }
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
			//Empleados
			try
			{
				String[] array = arg0.getItem().toString().split("-");
				txDniBajaEmpleado.setText(array[1]);
				txNombreBajaEmpleado.setText(array[2]);
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
		
			try
			{
				String[] array = arg0.getItem().toString().split("-");
				txDniModificacionEmpleado2.setText(array[0]);
				txNombreModificacionEmpleado2.setText(array[1]);
				txApellidoModificacionEmpleado2.setText(array[2]);
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
			
			try
			{
				String[] array = arg0.getItem().toString().split("-");
				idEmpleadoFK = array[0];
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
			
			//Ventas
			try
			{
				String[] array = arg0.getItem().toString().split("-");
				idVenta = array[0];
				txFormaPagoBajaVenta.setText(array[1]);
				txFechaBajaVenta.setText(array[2]);
				txImporteBajaVenta.setText(array[3]);
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
			
			try
			{
				String[] array = arg0.getItem().toString().split("/");
				idModificacionVenta = array[0];
				txFormaModificacionVenta2.setText(array[2]);
				txFechaModificacionVenta2.setText(array[1]);
				txImporteModificacionVenta2.setText(array[3]);
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
			
			//Productos
			try
			{
				String[] array = arg0.getItem().toString().split("-");
				txNombreBajaProducto.setText(array[1]);
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
		
			try
			{
				String[] array = arg0.getItem().toString().split("-");
				txNombreModificacionProducto2.setText(array[1]);
				txPrecioModificacionProducto2.setText(array[2]);
			}
			catch (ArrayIndexOutOfBoundsException AIOB)
			{
				System.out.println("Error: "+AIOB.getMessage());
			}
		
	}
}