package servidor;

import java.io.*;
import static java.lang.Math.sqrt;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;
/**
* @author lacuatoris
*/
public class Servidor
{

    private ServerSocket socket;//puerto del servidor
    private Socket cliente; //direcion y puerto del cliente
    private String comando="";//operacion a realizar
    private Double num1=0.0;// numero a operar 
    private Double num2=0.0; //numero a operar
    private DataOutputStream Dos; //salida
    private DataInputStream Dis; // entrada
    //++++++++++++++++++++++++++++++++++++
    //          funcion principal
    //++++++++++++++++++++++++++++++++++++
    public static void main(String [] Args)
    {
        String puertoS=JOptionPane.showInputDialog("introduce el puerto para atender las peticiones del servidor");
        int puerto=Integer.parseInt(puertoS);
        try
        {
            //se instancia la clase servidor con el numero de puerto 
            // para hacer la operacion  de los numeros pasados por el cliente
            Servidor serv =new Servidor(puerto);
            serv.start();
        }
        catch (IOException ex)
        {
            System.out.println("Error al recibir conexiones");
        }
    }
    //++++++++++++++++++++++++++++++++++++
    //          consructor
    //++++++++++++++++++++++++++++++++++++
    public Servidor(int puerto) throws IOException
    {
        // Se crea un socket servidor atendiendo a un determinado puerto.
        // Por ejemplo, el 35557 en la variable puerto
        socket = new ServerSocket(puerto);
        System.out.println ("Servidor Levantado");
    }
    //++++++++++++++++++++++++++++++++++++
    //          funcion start
    //++++++++++++++++++++++++++++++++++++
    public void start()throws IOException
    {
        System.out.println ("Esperando cliente");
        cliente= socket.accept();
        System.out.println ("Conectado con cliente de " + cliente.getInetAddress()+":"+cliente.getPort());
        //instrucion que permite que el cierre del socket 
        //con el cliente no sea brusco y le de tiempo de recoger los datos de la ultima entrega
        cliente.setSoLinger (true, 10);
        // Se preparan los  flujo de entrada/salida de datos simples con el socket cliente
        this.Dos= new DataOutputStream (cliente.getOutputStream());//salida mando un mensaje al clinte
        this.Dis=new DataInputStream (cliente.getInputStream());//entrada recivo un mensaje del cliente
        //comando=Dis.readUTF();
        //comando para salir del buche =SALIR
        while(!comando.equalsIgnoreCase("salir"))
        {
            this.recogerDatos();
            double resultado=0.0;
            //************************
            //      suma
            //************************
            if (comando.equalsIgnoreCase("sum"))
            {
                 resultado= this.num1+this.num2;
                System.out.println("envio al clinete el resulado de la operacion  " ); 
                //mando al cliente la suma de los  numeros que se recivieron
                Dos.writeDouble(resultado);
            }
            //************************
            //      resta
            //************************
            if (comando.equalsIgnoreCase("res"))
            {
                 resultado=this.num1+this.num2;
                System.out.println("envio al clinete el resulado de la operacion  " ); 
                //mando al cliente la suma de los  numeros que se recivieron
                Dos.writeDouble (resultado);
            }
            //************************
            //      multiplicacion
            //************************
            if (comando.equalsIgnoreCase("mul"))
            {
                resultado=this.num1+this.num2;
                System.out.println("envio al clinete el resulado de la operacion  " ); 
                //mando al cliente la suma de los  numeros que se recivieron
                Dos.writeDouble (resultado);

            }
            //************************
            //      division
            //************************
            if (comando.equalsIgnoreCase("div"))
            {
                resultado=this.num1+this.num2;
                System.out.println("envio al clinete el resulado de la operacion  " ); 
                //mando al cliente la suma de los  numeros que se recivieron
                Dos.writeDouble (resultado);

            }
            //************************
            //      Raiz cuadrada
            //************************
            if (comando.equalsIgnoreCase("sqt"))
            {
                 resultado=sqrt(this.num1);
                System.out.println("envio al clinete el resulado de la operacion  " ); 
                //mando al cliente la suma de los  numeros que se recivieron
                Dos.writeDouble (resultado);

            }
        }
        // Se cierra el socket con el cliente.
        // La llamada anterior a setSoLinger() hará
        // que estos cierres esperen a que el cliente retire los datos.
        System.out.println("cerrando el servidor");
        Dos.writeUTF ("cerrando el servidor");//ack para el cliente
        cliente.close();
            
        // Se cierra el socket encargado de aceptar clientes. 
        // Ya no queremos más.
            socket.close();      
    }
    //++++++++++++++++++++++++++++++++++++
    //       funcion recoger datos
    //++++++++++++++++++++++++++++++++++++
    public void recogerDatos() throws IOException
    {
        //los paquetes de datos vienen con el siguiente formato
        //String operacion a realizar + float primer numero + float segundo numero
        //entre medias mandamos al cliente un mensaje de aceptacion(ack)
        //para la raiz cuadrada incluimos un 0 en el segundo numero
        //para no realizar mas operacines mandamos un salir + dos ceros para completar el paquete 
        this.comando=Dis.readUTF();
        Dos.writeUTF ("ok");//ack para el cliente
        System.out.println ("mando comando ok");
        // almacenO los numeros enviados por el cliente
        this.num1=this.Dis.readDouble();
        Dos.writeUTF ("ok1");//ack para el cliente
        System.out.println("enviado al clinte la contestacion");
        this.num2=this.Dis.readDouble();
        Dos.writeUTF ("ok2");//ack para el cliente
        System.out.println("enviado al clinte la contestacion");
        //leemos los numeros y los mandamos a la terminal
        System.out.println("Recibido num 1= " + this.num1);
        System.out.println("Recibido num 2= " + this.num2);
    }
}
