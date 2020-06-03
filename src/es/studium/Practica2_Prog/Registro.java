package es.studium.Practica2_Prog;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;

public class Registro extends Frame implements ActionListener,WindowListener
{
	private static final long serialVersionUID = 1L;
	
	String usuario,clave,clave_cifrada,usuarioBD,claveBD,tipoUsuarioBD;
	
	Dialog incorrecto = new Dialog(this,"Datos incorrectos",true);
	
	Label lblogin1 = new Label ("Usuario:");
	Label lblogin2 = new Label ("Clave:");
	Button btnAceptar = new Button ("Aceptar");
	Button btnLimpiar = new Button ("Limpiar");
	TextField tx1 = new TextField (10);
	TextField tx2 = new TextField (10);
	
	Label lbDatos = new Label("Datos incorrectos");
	
	public Registro()
	{
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setSize(200,120);
		setIconImage(new ImageIcon("1.png").getImage());
		add(lblogin1);
		add(tx1);
		add(lblogin2);
		add(tx2);
		tx2.setEchoChar('*');
		add(btnAceptar);
		btnAceptar.addActionListener(this);
		add(btnLimpiar);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setResizable(false);
		setVisible(true);
		
		incorrecto.setLayout(new FlowLayout());
		incorrecto.setLocationRelativeTo(null);
		incorrecto.setSize(150,70);
		incorrecto.add(lbDatos);
		incorrecto.addWindowListener(this);
		incorrecto.setResizable(false);
		incorrecto.setVisible(false);
	
	}
	
	public static void main(String[] args)
	{
		new Registro();
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(btnLimpiar.equals(e.getSource()))
		{
			tx1.selectAll();
			tx2.selectAll();
			
			tx1.setText("");
			tx2.setText("");
		}
		else if(btnAceptar.equals(e.getSource()))
		{
			usuario = tx1.getText();
			clave = tx2.getText();
			clave_cifrada = getSHA256(clave);
			
			if(validacion(usuarioBD,claveBD,tipoUsuarioBD,clave_cifrada,usuario)==1)
			{
				setVisible(false);
				new GestionAdmin();
			}
			else if(validacion(usuarioBD,claveBD,tipoUsuarioBD,clave_cifrada,usuario)==2)
			{
				setVisible(false);
				new GestionUser();
			}
			else
			{
				incorrecto.setVisible(true);
			}
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		if(isActive())
		{
			System.exit(0);
		}
		else if(incorrecto.isActive())
		{
			incorrecto.setVisible(false);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
	
	public static String getSHA256(String clave)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(clave.getBytes());
			byte byteData[] = md.digest();
			for (int i = 0; i < byteData.length; i++)
			{
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static int validacion(String usuarioBD, String claveBD, String tipoUsuarioBD, String clave_cifrada, String usuario)
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendademusica?useSSL=false";
		String login = "root";
		String password = "Studium2020;";
		String sentencia = "SELECT * FROM tiendademusica.usuarios;";

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		int nValidacion=0;
		
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
			//Crear una sentencia
			statement = connection.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			do
			{
				rs.next();
				usuarioBD = rs.getString("nombreUsuario");
				claveBD = rs.getString("claveUsuario");
				tipoUsuarioBD = rs.getString("tipoUsuario");
				if((usuario.contentEquals(usuarioBD))&&(clave_cifrada.contentEquals(claveBD)))
				{
					if(tipoUsuarioBD.equals("administrador"))
					{
						nValidacion = 1;
					}
					else
					{
						nValidacion = 2;
					}
				}
				else
				{
					nValidacion = 0;
				}
			}while(!(usuario.contentEquals(usuarioBD))&&!(clave_cifrada.contentEquals(claveBD)));
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return nValidacion;
	}
}