package main;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Edwar Mamani Flores
 *
 */
public class PageRank {
	private static final int MAX_OUT_LINKS = 50;
	private static final int MIN_OUT_LINKS = 5;
	private static final double DIFF_TOLERANCE = 0.001;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingresar el numero de paginas: ");
		int numPages = scanner.nextInt();

		double diff;
		double[][] r = initializeMatrix(numPages);
		
		double[][] matrixRandom = new double[numPages][numPages];
		double[] cMatrix = new double[numPages];
		
		/*double[][] matrixRandom = {
				{1,1,0},
				{1,0,1},
				{0,1,0}};
		double[] cMatrix = {2,2,1};*/
		
		Random rand = new Random();
		int numOutPages;
		int randOutPage;
		for(int i=0;i<matrixRandom.length;i++){
			numOutPages=rand.nextInt((MAX_OUT_LINKS-MIN_OUT_LINKS)+1)+MIN_OUT_LINKS;//NUMERO ALEATORIO DE PAGINAS DE SALIDA
			cMatrix[i]=numOutPages;//ALMACENADO EN VECTOR DE # PAGINAS DE SALIDA
			for(int j=0;j<numOutPages;j++){
				//POR CADA PAGINA DE SALIDA GENERAMOS ALEATORIAMENTE UN DESTINO DE NUESTRO LISTADO DE PAGINAS
				randOutPage=rand.nextInt(numPages);
				matrixRandom[i][randOutPage]+=1;
			}
		}
		printMatrix("matrix alatoria",matrixRandom);
		printVector("C",cMatrix);
		
		double[][] M = new double[numPages][numPages]; 
		for(int i=0;i<matrixRandom.length;i++){
			for(int j=0;j<matrixRandom[i].length;j++){
				if(matrixRandom[i][j]>0 && cMatrix[j]>0){
					M[i][j]=1/cMatrix[j];
				}
			}
		}
		
		printMatrix("R inicial",r);
		printMatrix("Matriz inicial",M);
		
		boolean next=true;
		int count=1;
		while(next){
			double[][] prevR = r.clone();
			// r(k+1)=Mr(k)
			r = multiplyMatrix(M,r);
			diff = SumResultdiffMatrix(r, prevR);
			if (diff<=DIFF_TOLERANCE){
				printMatrix("PageRank "+count+": ",r);
				System.out.println("----------------------------------------------");
				next=false;
			}
				
			count++;
		}
	}
	
	/** 
	 * @param rows
	 * @return Una matriz con valores iniciales en uno para la variable r
	 */
	public static double[][] initializeMatrix(int rows){
		double [][] response = new double[rows][1];
		for (int i=0;i<rows;i++){
			response[i][0]=1;
		}
		return response;
	}
	
	/**
	 * Imprime vector tabulado en la consola para visualizacion humana
	 * @param title
	 * @param matrix
	 */
	private static void printMatrix(String title, double[][] matrix) {
        System.out.println(title.toUpperCase());
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (col > 0) {
                    System.out.print("\t");
                }
                //DecimalFormat formatter = new DecimalFormat("###.####");
                //System.out.print(formatter.format(matrix[row][col]));
                System.out.print(matrix[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }
	
	/**
	 * Imprime vector en consola
	 * @param title
	 * @param vector
	 */
	private static void printVector(String title, double[] vector){
		System.out.println(title.toUpperCase());
        for (int row = 0; row < vector.length; row++) {
        	DecimalFormat formatter = new DecimalFormat("###.####");
            System.out.print(formatter.format(vector[row]));
            System.out.println();
        }
        System.out.println();
	}
	
	/**
	 * @param a
	 * @param b
	 * @return La matriz producto de la multiplicacion de a y b
	 */
	public static double[][] multiplyMatrix(double[][] a, double[][] b){
		int row_a = a.length;
		int col_a = a[0].length;
		int row_b = b.length;
		int col_b = b[0].length;
		if (col_a != row_b)
			throw new RuntimeException("Cannot multiply matrix a*b");
		double[][] response = new double[row_a][col_b];
		for (int x=0; x < response.length; x++) {
		    for (int y=0; y < response[x].length; y++) {
		    		for (int z=0; z<col_a; z++) {
		    			response [x][y] += a[x][z]*b[z][y];
		    		}
		    }
		}
		return response;
	}
	
	
	/**
	 * @param a
	 * @param b
	 * @return La sumatoriatoria de todos los elementos de la matriz resultante de la diferencia de a y b 
	 */
	public static double SumResultdiffMatrix(double[][] a,double[][] b) {
		double response = 0;
		for (int x=0; x < a.length; x++) {
            for (int y=0; y < a[x].length; y++) {
            	response+= Math.abs(a[x][y] - b[x][y]);
            }
		}
		return response;
	}
}
