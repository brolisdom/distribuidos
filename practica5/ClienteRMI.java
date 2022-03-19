import java.rmi.Naming;

public class ClienteRMI {
    public static void main(String args[]) throws Exception {
        String url = "";
        url = "rmi://20.228.196.65:50000/prueba";
        InterfaceRMI nodo1 = (InterfaceRMI)Naming.lookup(url);
        System.out.println(nodo1.mayusculas("hola"));

        url = "rmi://20.228.196.65:50000/prueba";
        InterfaceRMI nodo2 = (InterfaceRMI)Naming.lookup(url);
        System.out.println("suma="+nodo2.suma(10,20));

        url = "rmi://20.228.196.65:50000/prueba";
        InterfaceRMI nodo3 = (InterfaceRMI)Naming.lookup(url);
        int [][] m = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        System.out.println("checksum="+nodo3.checksum(m));
    }
}