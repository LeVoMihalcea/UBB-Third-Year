A = [
  10 7 8 7; 
  7 5 6 5 ; 
  8 6 10 9; 
  7 5 9 10
];
b = [32 23 33 31];

gauss = GaussPivoting(A, b)
conditional = cond(A)

b2 = [32.1, 22.9, 33.1, 30.9];

gauss2 = GaussPivoting(A, b2);

Binput = norm(b - b2, 2) / norm(b, 2);

Boutput = norm(gauss - gauss2, 2) / norm(gauss, 2);

Berror = Boutput / Binput

A3 = [
  10 7 8.1 7.2 ;
  7.08 5.04 6 5;
  8 5.98 9.89 9;
  6.99 4.99 9 9
];

gauss3 = GaussPivoting(A3, b)

Binput = norm(b - b2, 2) / norm(b, 2);

Boutput = norm(gauss - gauss3, 2) / norm(gauss, 2);

Berror = Boutput / Binput