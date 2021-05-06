f = @(x) 100 ./ (x.^2) .* sin(10 ./ x);
x = linspace(1,3,100);
eps = 10^(-4);
a = 1;
b = 3;
y = f(x);

#the graph
plot(x, y);

%compute with adaptive quadrature
fprintf("adaptive_quadrature: %.6f\n", adaptive_quadrature(f, a, b, eps));