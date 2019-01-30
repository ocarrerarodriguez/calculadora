/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author lacuatoris
 */
public class Servidor {
    ServerSocket socket;//puerto del servidor
    Socket cliente; //direcion y puerto del cliente
    DataInputStream Dis;
    DataOutputStream Dos;
    
    String comando="";
    ArrayList <Integer> num = new ArrayList<Integer>();
    
    public static void main (String [] args)
    {
        
        try {
            // Se instancia la clase Servidor para que haga 
            // la suma de cinco numeros pasados por el cliente 
            Servidor serv =new Servidor(5555);
            serv.start();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     public Servidor(int puerto) throws IOException
    {
            // Se crea un socket servidor atendiendo a un determinado puerto.
            // Por ejemplo, el 35557 en la variable puerto
            socket = new ServerSocket (puerto);
            System.out.println ("Servidor Levantado");
    }
    public void start() throws IOException
     {
        System.out.println ("Esperando cliente");
        cliente= socket.accept();
        System.out.println ("Conectado con cliente de " + cliente.getInetAddress()+":"+cliente.getPort());
        //instrucion que permite que el cierre del socket 
        //con el cliente no sea brusco y le de tiempo de recoger los datos de la ultima entrega
        cliente.setSoLinger (true, 10);
        // Se preparan los  flujo de entrada/salida de datos simples con el socket cliente
        Dos= new DataOutputStream (cliente.getOutputStream());//salida mando un mensaje al clinte
        Dis=new DataInputStream (cliente.getInputStream());//entrada recivo un mensaje del cliente
        //comando=Dis.readUTF();
        while(!comando.equalsIgnoreCase("salir"))
        {
            comando=Dis.readUTF();
            if (comando.equalsIgnoreCase("sum"))
            {
                System.out.println ("recivido comando sum");
                Dos.writeUTF ("ok");//ack para el cliente
               
                //genero el array num para almacenar los numeros enviados por el cliente
                
                 System.out.println ("mando comando ok");
                //leemos el primer numero y lo mandamos a la terminal
                
                
                 
                    
                
                
                
                this.num.add(Dis.readInt());
                System.out.println("Recibido num1= " + num.get(0));
                Dos.writeUTF ("ok0");//ack para el cliente
                this.num.add(Dis.readInt());
                System.out.println("Recibido num2= " + num.get(1));
                Dos.writeUTF ("ok1");//ack para el cliente
                this.num.add(Dis.readInt());
                System.out.println("Recibido num3= " + num.get(2)); 
                Dos.writeUTF ("ok2");//ack para el cliente
                this.num.add(Dis.readInt());
                System.out.println("Recibido num4= " + num.get(3));
                Dos.writeUTF ("ok3");//ack para el cliente
                this.num.add(Dis.readInt());
                System.out.println("Recibido num5= " + num.get(4)); 
                Dos.writeUTF ("ok4");//ack para el cliente
                System.out.println("envio al clinete el resulado de la operacion  " ); 
                //mando un mensaje al cliente mando la suma de los cinco numeros que se recivieron
                Dos.writeInt (num.get(0));
            }
            if (comando.equalsIgnoreCase("res"))
            {
                System.out.println ("recivido comando res");
                Dos.writeUTF ("ok");//ack para el cliente
               
                //genero el array num para almacenar los numeros enviados por el cliente
                int [] num = new int[5];
                 System.out.println ("mando comando ok");
                //leemos el primer numero y lo mandamos a la terminal
                num[0]=Dis.readInt();
                System.out.println("Recibido num1= " + num[0]);
                Dos.writeUTF ("ok0");//ack para el cliente
                num[1]=Dis.readInt();
                System.out.println("Recibido num2= " + num[1]);
                Dos.writeUTF ("ok1");//ack para el cliente
                num[2]=Dis.readInt();
                System.out.println("Recibido num3= " + num[2]); 
                Dos.writeUTF ("ok2");//ack para el cliente
                num[3]=Dis.readInt();
                System.out.println("Recibido num4= " + num[3]);
                Dos.writeUTF ("ok3");//ack para el cliente
                num[4]=Dis.readInt();
                System.out.println("Recibido num5= " + num[4]); 
                Dos.writeUTF ("ok4");//ack para el cliente
                //mando un mensaje al cliente mando la suma de los cinco numeros que se recivieron
                Dos.writeInt (num[0]-num[1]-num[2]-num[3]-num[4]);
            }
        }
        // Se cierra el socket con el cliente.
            // La llamada anterior a setSoLinger() hará
            // que estos cierres esperen a que el cliente retire los datos.
             System.out.println("cerrando el servidor");
            Dos.writeUTF ("cerrando el servidor");//ack para el cliente
            cliente.close();
            
            // Se cierra el socket encargado de aceptar clientes. Ya no
            // queremos más.
            socket.close();
     }
    
}
