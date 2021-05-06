f = @(x) 1 + cos(pi * x) ./ (1 + x);

% plotuim functia
x = 0:0.01:10;
hold on
plot(x, f(x))

x = [0:0.5:10];
X = [0:0.01:10];

Y = LAGRANGE(x, f(x), X);

plot(X, Y, 'r')