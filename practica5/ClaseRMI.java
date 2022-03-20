import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI {
    public ClaseRMI() throws RemoteException {
        super();
    }

    public float[][] multiplica_matrices(float[][] A, float[][] B, int N) throws RemoteException {
        System.out.println("Realizando operacion ...");
        float[][] C = new float[N/4][N/4];
        for (int i=0; i<N/4; i++)
            for (int j=0; j<N/4; j++)
                for (int k=0; k<N; k++)
                    C[i][j] += A[i][k] * B[j][k];
        System.out.println("Operacion terminada ...");
        return C;
    }
}