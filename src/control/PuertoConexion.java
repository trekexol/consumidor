package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PuertoConexion {
    protected Socket socket;
    protected ObjectInputStream inputStream;
    protected ObjectOutputStream outputStream;

    public PuertoConexion(String servidor, int puerto){
        try {
            this.socket = new Socket(servidor, puerto);
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean Recibir() throws IOException {
        return this.inputStream.readBoolean();
    }

    public void EnviarBoolean(boolean mensaje) throws IOException {
        this.outputStream.writeBoolean(mensaje);
    }

    public void EnviarReplica(String replica) throws IOException {
        this.outputStream.writeUTF(replica);
    }

    public void Cerrar() throws IOException {
        this.outputStream.close();
    }

}
