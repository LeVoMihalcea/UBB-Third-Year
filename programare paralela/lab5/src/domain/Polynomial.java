package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polynomial {
    public final Random randomGenerator = new Random();
    private final List<Integer> coefficients;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial(int degree) {
        coefficients = new ArrayList<>();
        for (int i = 0; i < degree; i++) {
            coefficients.add(randomGenerator.nextInt(10));
        }
        coefficients.add(randomGenerator.nextInt(10) + 1);
    }

    public int getDegree() {
        return this.coefficients.size() - 1;
    }

    public int getLength() {
        return this.coefficients.size();
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int power = getDegree();
        for (int i = getDegree(); i >= 0; i--) {
            str.append(" ").append(coefficients.get(i)).append("x^").append(power).append(" +");
            power--;
        }
        str.deleteCharAt(str.length() - 1); //delete last +
        return str.toString();
    }
}
