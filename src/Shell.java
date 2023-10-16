import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
    public static void main(String[] args) throws IOException {
        //creamos buffer reader
        BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
        //variable donde guardaremos el ultimo comando
        Command lastCommand=null;
        //booleano para salir del bucle
        boolean salir=false;

        while (salir==false){
            System.out.println(">");
            try {
                String comandoUsuario = teclado.readLine().trim();//leemos variable(trim)elimina espacios en blanco inicio y fin

                //si el usuario introduce exit saldremos del programa
                if (comandoUsuario.equals("exit")) {
                    salir = true;
                } else if (comandoUsuario.equals("last-command")) {//si introduze last command
                    if (lastCommand == null) {//si es nulo mostraremos que no hay informacion
                        System.out.println("No se a ejecutado ningun commando");
                    } else {//si no es nulo mostraremos las caracteristicas del ultimo comando utilizado
                        System.out.println("Informacion del ultimo comando");
                        System.out.println(lastCommand.toString());
                    }
                } else {//creamos el comando
                    Command comando = new Command(comandoUsuario);//creamos el comando
                    String salida = comando.ejecutar();//ejecutamos el comando i guardamos

                    if (!comando.getSalidaRedireccionada().isEmpty()) {
                        //si la salida esta redirigida no mostramos nada
                    } else {
                        //si no esta redirigida mostramos la ejecuccion del processo
                        System.out.println(salida);
                    }
                    lastCommand = comando;//guardamos el ultimo comando ejecutado

                }
            }catch (IOException e) {
                e.printStackTrace();
            }

        }

        teclado.close();//cerramos el teclado

        

    }
}
