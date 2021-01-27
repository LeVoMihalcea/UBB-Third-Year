import domain.Polynomial;
import multiplicationTypes.Multiplication1;
import multiplicationTypes.Multiplication2;
import multiplicationTypes.Multiplication3;
import multiplicationTypes.Multiplication4;

public class Main {
    public static void main(String[] args) {
        int DEGREE = 100;

        Polynomial firstPolynomial = new Polynomial(DEGREE);
        Polynomial secondPolynomial = new Polynomial(DEGREE);

//        System.out.println(firstPolynomial);
//        System.out.println(secondPolynomial);

        try{
            // O(n2) algorithm - sequential
            Multiplication1 multiplication1 = new Multiplication1(firstPolynomial, secondPolynomial);

            // O(n2) algorithm - parallelized
            Multiplication2 multiplication2 = new Multiplication2(firstPolynomial, secondPolynomial);

            // Karatsuba's algorithm - sequential
            Multiplication3 multiplication3 = new Multiplication3(firstPolynomial, secondPolynomial);

            // Karatsuba's algorithm - parallelized
            Multiplication4 multiplication4 = new Multiplication4(firstPolynomial, secondPolynomial);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
