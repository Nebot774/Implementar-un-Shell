import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Command {

    //array de argumentos de tipo cadena donde almacenamos los argumentos del comando ej: "ls", "-la", ">", "/home/usuario/fichero.txt"
    //Los argumentos son las partes individuales que componen el comando
    //cada elemento del array almacena un elemento individual
    private String[] argumentos;
    //es una cadena que almacena la redireccion de la salida del comando,si el comando se ejecuta ila salida redirige a un archivo
    //la variable almacena el nombre del archivo,si no hay redireccion la variable esta vacia
    // esta variable contendría "/home/usuario/fichero.txt" debido a la redirección "> /home/usuario/fichero.txt".
    private String salidaRedireccionada;


    public Command(String[] argumentos,String salidaRedireccionada) {
        this.argumentos = argumentos;
        this.salidaRedireccionada=salidaRedireccionada;
    }

    /**
     * Constructor de la clase Command utilizado para creear instancias de Command a partir de @param salidaRedireccionada
     * La logica se divide en ods partes:los argumentos del comandondo y la redireccion de la salida estandar
     *
     */
    public Command(String salidaRedireccionada) {
        // Divide la cadena en dos mediante ">" la parte izquierda contendra los argumentos del comando y la derecha
        //si existe la redireccion de salida estandar
        String[] partes = salidaRedireccionada.split(" > ");

        // toma la primera parte del argumento y elimina cualquier espacio en blanco
        String argumentosString = partes[0].trim();
        //la parte de argumentos se divide en un array de cadenas utilizando el espacio como separador para obtener los
        //elementos individuales del comando
        this.argumentos = argumentosString.split(" ");

        //comprobamos si hay dos partes dento de partes(array)
        if (partes.length > 1) {
            this.salidaRedireccionada = partes[1].trim();//si ha yalmacenamos la ruta eliminando espacio en blanco
        } else {
            this.salidaRedireccionada = ""; // Si no no establecemos ruta
        }
    }

    public String ejecutar(){
       try{
           //Creamos un nuevo proceso con los argumentos proporcionados
           ProcessBuilder processBuilder=new ProcessBuilder(argumentos);
           Process process=processBuilder.start();

           //configureamos lector para capturar la salida del processo
           BufferedReader lector=new BufferedReader(new InputStreamReader(process.getInputStream()));
           StringBuilder salida=new StringBuilder();//guarmados en formato String la salida del processo para mostrar por pantalla
           String linia;

           //leemos la salida del processo linea a linea y la almacenamos en salida
           while ((linia= lector.readLine())!=null){
               salida.append(linia).append("\n");
           }

           //esperamos a que termine el processo y obtenemos su codigo de salida
           int salidaProcesso=process.waitFor();

           //si no se registra una salida imprimimos la informacion del processo
           if(salidaRedireccionada.isEmpty()){
            System.out.println("PID:"+process.pid());//imprimimos el identificador de processo
               System.out.println("Salida del processo:"+salida.toString());
               System.out.println("Codigo de salida:"+salidaProcesso);//muestra si se ejecuto del proc fue exitosa
           }

           return  salida.toString();


       }catch (IOException | InterruptedException e) {
           e.printStackTrace();
           return "";
       }

    }


    //metodo toString que mostramos la informacion del comando ejecutado el numero de parametros que tiene
    //los parametros y la salida
    @Override
    public String toString() {
        return "Comando:"+String.join(" ",argumentos)+
                "\n Numero de parametros:"+argumentos.length+
                "\n Parametros:"+String.join(",",argumentos)+
                "\n Salida:"+salidaRedireccionada;
    }

    //getters y setters

    public String[] getArgumentos() {
        return argumentos;
    }

    public void setArgumentos(String[] argumentos) {
        this.argumentos = argumentos;
    }

    public String getSalidaRedireccionada() {
        return salidaRedireccionada;
    }

    public void setSalidaRedireccionada(String salidaRedireccionada) {
        this.salidaRedireccionada = salidaRedireccionada;
    }
}
