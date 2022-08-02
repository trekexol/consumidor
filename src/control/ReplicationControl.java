package control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReplicationControl {
    // Enparejado con la lista de puerto, primera direccion con primer puerto
    private List<String> Servidores = Arrays.asList(
            "localhost", //Comsumidor
            "localhost" //Productor
    );

    private List<Integer> Puertos = Arrays.asList(
            2508,
            2509
    );

    public void Run() throws Exception {
        if(!(Servidores.size() == Puertos.size())){
            throw new Exception("Error en el numero de puertos y servidores");
        }
        List<PuertoConexion> conexiones = new ArrayList<>();
        for (int i=0; i<Servidores.size(); i++){
            conexiones.add(new PuertoConexion(Servidores.get(i), Puertos.get(i)));
            //Se consultan a todos los servidores de replicacion
            if(!conexiones.get(i).Recibir()){
                //Se recibio una respuesta negativa se procede a abortar
                for (int j=0; i<i; j++){
                    conexiones.get(j).EnviarBoolean(false);
                }
                return;
            }
        }
        for (int i=0; i<Servidores.size(); i++){
            String replica = "Test"; //= Pot.ToString()
            conexiones.get(i).EnviarReplica(replica);
            conexiones.get(i).Cerrar();
        }
    }
}
