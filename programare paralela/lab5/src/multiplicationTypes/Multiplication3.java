package multiplicationTypes;

import domain.Polynomial;
import operations.Operations;

public class Multiplication3 {
    private Polynomial firstPolynomial;
    private Polynomial secondPolynomial;
    private Polynomial result;

    public Multiplication3(Polynomial firstPolynomial, Polynomial secondPolynomial) throws InterruptedException {
        this.firstPolynomial = firstPolynomial;
        this.secondPolynomial = secondPolynomial;

        long startTime = System.currentTimeMillis();
        this.result = Operations.multiplicationKaratsubaSequentialForm(firstPolynomial, secondPolynomial);
        long endTime = System.currentTimeMillis();
        System.out.println("Karatsuba sequential multiplication of polynomials: ");
        System.out.println("Execution time : " + (endTime - startTime) + " ms");
    }

    public Polynomial getFirstPolynomial() {
        return firstPolynomial;
    }

    public void setFirstPolynomial(Polynomial firstPolynomial) {
        this.firstPolynomial = firstPolynomial;
    }

    public Polynomial getSecondPolynomial() {
        return secondPolynomial;
    }

    public void setSecondPolynomial(Polynomial secondPolynomial) {
        this.secondPolynomial = secondPolynomial;
    }

    public Polynomial getResult() {
        return result;
    }

    public void setResult(Polynomial result) {
        this.result = result;
    }
}
