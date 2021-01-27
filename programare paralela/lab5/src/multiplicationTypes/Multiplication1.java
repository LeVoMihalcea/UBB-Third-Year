package multiplicationTypes;

import domain.Polynomial;
import operations.Operations;

public class Multiplication1 {
    private Polynomial firstPolynomial;
    private Polynomial secondPolynomial;
    private Polynomial result;

    public Multiplication1(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        this.firstPolynomial = firstPolynomial;
        this.secondPolynomial = secondPolynomial;

        long startTime = System.currentTimeMillis();
        this.result = Operations.multiplicationSequentialForm(firstPolynomial, secondPolynomial);
        long endTime = System.currentTimeMillis();
        System.out.println("O(n2) algorithm - sequential");
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
