package cliente;

import java.io.*;
import java.net.*;

import java.util.logging.*;
import javax.swing.JOptionPane;
/**
* @author lacuatoris
*/
public class Cliente
{
    private Socket cliente; //direcion y puerto del cliente
    private String operacion="";//operacion a realizar
    private double num1=0.0;// numero a operar 
    private double num2=0.0; //numero a operar
    private DataOutputStream Dos; //salida
    private DataInputStream Dis; // entrada

    //++++++++++++++++++++++++++++++++++++
    //          funcion principal
    //++++++++++++++++++++++++++++++++++++
    public static void main (String [] args)
    {  
        String ipS=JOptionPane.showInputDialog("introduce la direcion ip : 192.168.0.127 // localhost");
        String puertoS=JOptionPane.showInputDialog("introduce el puerto para hacer las llamadas al servidor");
        int puerto=Integer.parseInt(puertoS);
         try {
         Cliente cli= new Cliente(ipS,puerto);
         cli.start();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //++++++++++++++++++++++++++++++++++++
    //          consructor
    //++++++++++++++++++++++++++++++++++++
    public Cliente(String direcion ,int puerto) throws IOException
    {

            recoger_datos();//recogida d datos
            this.cliente= new Socket (direcion, puerto);
            System.out.println ("conectado al servidor");
       
    }
    public void start()throws IOException
    {
        // Se preparan los  flujo de entrada/salida de datos simples con el socket cliente
        Dos= new DataOutputStream (cliente.getOutputStream());//salida mando un mensaje al servidor
        Dis=new DataInputStream (cliente.getInputStream());//entrada recivo un mensaje del servidor
        if(operacion.equals("sum")||operacion.equals("res")||operacion.equals("mul")||operacion.equals("div")||operacion.equals("sqt")||operacion.equals("salir"))
        {
            System.out.println ("comienzo el envio de datos:");
            System.out.println ("envio el comando "+this.operacion+" al servidor");
            Dos.writeUTF(this.operacion);
            if(Dis.readUTF().equalsIgnoreCase("ok"))
            {
                System.out.println ("comando "+this.operacion+" aceptado");
                //envio los datos al servidor
                Dos.writeDouble(this.num1);
                if(Dis.readUTF().equalsIgnoreCase("ok"))
                {
                    Dos.writeDouble(this.num2);                    
                }
            }
            System.out.println("Enviado num 1= " + this.num1);
            System.out.println("Enviado num 2= " + this.num2);
        }
            System.out.println ("el resultado arrojado por el servidor es :"+Dis.readFloat());
            Dos.writeUTF("salir");//comando que cierra el servidor
            System.out.println (Dis.readUTF());
    }

      

     public void recoger_datos() throws IOException
     {
         String cadena=JOptionPane.showInputDialog(
            "Introduce una operacion. (sum,res,mul,div,sqt,salir)");
        //if que comprueva la operacion    
        if(cadena.equals("sum")||cadena.equals("res")||cadena.equals("mul")||cadena.equals("div"))
        {
            this.operacion=cadena;

            String num1S=JOptionPane.showInputDialog(
            "Introduce el primer numero. ");
            this.num1=Float.parseFloat(num1S);

            String num2S=JOptionPane.showInputDialog(
            "Introduce el segundo numero. ");
            this.num2=Float.parseFloat(num2S);
            
         }
         if(cadena.equals("sqt"))
         {
            this.operacion=cadena;
            String num1S=JOptionPane.showInputDialog(
            "Introduce el primer numero. ");
            this.num1=Float.parseFloat(num1S);
            this.num2=0.0;
         }
         if (cadena.equals("salir"))
         {
            this.operacion=cadena;
            this.num1=0.0;
            this.num2=0.0;
         }
         
     }
}