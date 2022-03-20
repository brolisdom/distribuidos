import java.rmi.Naming;

public class ClienteRMI extends Thread {
    static int N = 8;
    static float[][][] Ax = new float[4][N/4][N];
    static float[][][] Bx = new float[4][N/4][N];
    static float[][][] Cx = new float[16][N/4][N/4];

    int n;
    public ClienteRMI(String str, int n) {
        super(str);
        this.n = n;
    }

    public void run() {
        try{
            InterfaceRMI nodo = (InterfaceRMI)Naming.lookup(getName());
            for (int i=(n-1)*4; i<n*4; i++) 
                for (int j=0; j<4; j++) 
                    Cx[i] = nodo.multiplica_matrices(Ax[n-1], Bx[j], N);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    static float[][] separa_matriz(float[][] M, int inicio) {
        float [][] Mx = new float[N/4][N];
        for (int i=0; i<N/4; i++)
            for (int j=0; j<N; j++)
                Mx[i][j] = M[i+inicio][j];
        return Mx;
    }

    static void acomoda_matriz(float[][] C, float[][] M, int renglon, int columna) {
        for (int i=0; i<N/4; i++) for (int j=0; j<N/4; j++) C[i+renglon][j+columna] = M[i][j];
    }

    static void imprimir_matriz(float[][] M, int filas, int columnas) {
        for (int i=0; i<filas; i++) {
            for (int j=0; j<columnas; j++) 
                System.out.print(M[i][j] + " ");
            System.out.println();
        }
    }

    static float calcular_checksum(float[][] M, int filas, int columnas) {
        float checksum = 0;
        for (int i=0; i<filas; i++)
            for (int j=0; j<columnas; j++)
                checksum = M[i][j];
        return checksum;
    }

    static float[][] matriz_A() {
        float [][] A = new float[N][N];
        for (int i=0; i<N; i++)
            for (int j=0; j<N; j++)
                A[i][j] = i + 2 * j;
        return A;
    }

    static float[][] matriz_B_transpuesta() {
        float [][] B = new float[N][N];
        for (int i=0; i<N; i++)
            for (int j=0; j<N; j++)
                B[i][j] = 3 * i - j;
        for (int i=0; i<N; i++)
            for (int j=0; j<i; j++) {
                float x = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = x;
            }
        return B;
    }

    public static void main(String args[]) throws Exception {
        float checksum = 0;
        float [][] A = matriz_A();
        float [][] B = matriz_B_transpuesta();
        float [][] C = new float[N][N];
        for (int i=0; i<4; i++) Ax[i] = separa_matriz(A, i*N/4);
        for (int i=0; i<4; i++) Bx[i] = separa_matriz(B, i*N/4);
        ClienteRMI nodo1 = new ClienteRMI("rmi://20.25.61.12:50000/prueba", 1); 
        ClienteRMI nodo2 = new ClienteRMI("rmi://20.25.61.12:50000/prueba", 2); 
        ClienteRMI nodo3 = new ClienteRMI("rmi://20.228.179.19:50000/prueba", 3); 
        ClienteRMI nodo4 = new ClienteRMI("rmi://20.228.179.19:50000/prueba", 4); 

        nodo1.start();
        nodo2.start();
        nodo3.start();
        nodo4.start();

        nodo1.join();
        nodo2.join();
        nodo3.join();
        nodo4.join();

        int aux = 0;
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++){
                acomoda_matriz(C, Cx[aux], i*N/4, j*N/4);
                aux++;
            }
        }

        if (N==8) {
            System.out.println("Matriz A");
            imprimir_matriz(A, N, N);
            System.out.println("Matriz B (transpuesta)");
            imprimir_matriz(B, N, N);
            System.out.println("Matriz C");
            imprimir_matriz(C, N, N);
        }
        checksum = calcular_checksum(C, N, N);
        System.out.println("\nChecksum: "+checksum);
    }
}