/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lacuatoris
 */
public class Cliente {
    
    Socket socket;
    DataInputStream Dis;
    DataOutputStream Dos;
    List <Integer> num = new ArrayList<Integer>();
    String operacion;
    String ack="";
    Scanner sc ;
    
    
   
    /** Programa principal, crea el socket cliente */
     public static void main (String [] args)
     {
         try {
         Cliente cli= new Cliente("localhost",5555);
         cli.start();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    private Object lista;
     public Cliente(String direcion ,int puerto) throws IOException
     {
        //funcon en la que instanciamos los componentes para el soquet
        
        this.sc = new Scanner(System.in);
          String cadena="";
          System.out.println("Introduce una operacion. ");
          System.out.println("sum para sumar numeros");
          System.out.println("res para restar numeros");
          System.out.println("mul para multiplicar numeros");
          System.out.println("div para dividir numeros");
          System.out.println("sqr para la raiz cuadrada de un numero");
          
          System.out.println("terminar para salir");
          
         cadena=sc.nextLine();
         if( 
                 cadena.equals("sum")||
                 cadena.equals("res")||
                 cadena.equals("mul")||
                 cadena.equals("div")||
                 cadena.equals("sqr")
         )
         {
             //cadena.equals("abs")||
             //cadena.equals("mod")
           this.operacion=cadena;
           this. recoger_datos();//recogida d datos
            socket = new Socket (direcion, puerto);
            System.out.println ("conectado al servidor");
         }
       
     }
     public void start()throws IOException
     {
          // Se preparan los  flujo de entrada/salida de datos simples con el socket cliente
        Dos= new DataOutputStream (socket.getOutputStream());//salida mando un mensaje al servidor
        Dis=new DataInputStream (socket.getInputStream());//entrada recivo un mensaje del servidor
        if(operacion.equals("sum")||operacion.equals("res")||operacion.equals("mul")||operacion.equals("div")){
            this.envio_operacion();
        }
        if(operacion.equals("sqr")){
            this.envio_sqrt();
        }
     }
     public void envio_operacion()throws IOException      
     {
        System.out.println ("comienzo el envio de datos:");
        System.out.println ("envio el comando "+this.operacion+" al servidor");
        Dos.writeUTF(this.operacion);
        if(Dis.readUTF().equalsIgnoreCase("ok"))
        {
            System.out.println ("comando "+operacion+" aceptado");
            int i=0;
            for(i=0;i<num.size();i--)
            {
                while(!ack.equalsIgnoreCase("ok"+i))
                {

                    this.envio_num(i);
                    ack=Dis.readUTF();
                }
                System.out.println ("Aceptado");
            }
            System.out.println ("el resultado arrojado por el servidor es :"+Dis.readInt());
            Dos.writeUTF("salir");//comando que cierra el servidor
            System.out.println (Dis.readUTF());
        }
     }
     public void envio_sqrt()throws IOException      
     {
        System.out.println ("comienzo el envio de datos:");
        System.out.println ("envio el comando "+this.operacion+" al servidor");
        Dos.writeUTF(this.operacion);
        if(Dis.readUTF().equalsIgnoreCase("ok"))
        {
            while(!ack.equalsIgnoreCase("ok0"))
            {

                this.envio_num(0);
                ack=Dis.readUTF();
            }
            System.out.println ("Aceptado");
            System.out.println ("el resultado arrojado por el servidor es :"+Dis.readInt());
        }
            
        Dos.writeUTF("salir");//comando que cierra el servidor
        System.out.println (Dis.readUTF());
    }
     
    public void envio_num(int i) throws IOException
    {
         this.Dos.writeInt (num.get(i));
         System.out.println ("Enviado: "+num.get(i));
    }
     
    public void recoger_datos()
    {
       System.out.println("Introduce números.  cero para salir");			
		
		int numero = 0;
		
		do {			
			
			try {
				numero = this.sc.nextInt();
				this.num.add(numero);
			} catch (InputMismatchException ime){
				System.out.println("¡Cuidado! Solo puedes insertar números. ");
				// Eliminamos el valor que no queríamos
				sc.next();
			}
						
			
		} while (numero!=0);
    }
}
