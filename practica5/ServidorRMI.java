import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServidorRMI {
    public static void main (String[] args) throws Exception {
        LocateRegistry.createRegistry(50000);
        String url = "rmi://20.228.196.146:50000/prueba";
        ClaseRMI obj = new ClaseRMI();
        Naming.rebind(url, obj);
    }
}