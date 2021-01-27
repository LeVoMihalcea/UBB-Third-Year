using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab7
{
    [Serializable]
    public class Polynomial
    {
        public int Degree { get; set; }
        public int[] Coefficients { get; set; }

        public Polynomial(int s)
        {
            Degree = s;
            Coefficients = new int[Degree + 1];
        }

        public override string ToString()
        {
            var sb = new StringBuilder();

            sb.Append("Degree: " + Degree + "\n");
            for (var i = Degree; i >= 0; i--)
            {
                if (Coefficients[i] > 0 && i < Degree)
                {
                    sb.Append("+");
                }

                sb.Append(Coefficients[i]);
                sb.Append("*X^" + i);
                sb.Append(" ");
            }

            return sb.ToString();
        }


        public static Polynomial Random(int degree)
        {
            var polynomial = new Polynomial(degree);
            var rnd = new Random();
            for (var i = 0; i < degree + 1; i++)
            {
                polynomial.Coefficients[i] = rnd.Next(-10, 10);
                if (i != degree) continue;
                while (polynomial.Coefficients[i] == 0)
                {
                    polynomial.Coefficients[i] = rnd.Next(-50, 50);
                }
            }

            return polynomial;
        }
    }
}