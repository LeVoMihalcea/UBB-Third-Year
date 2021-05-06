f = @(x) exp(-x.^2);
a = 1;
b = 1.5;


# a)evaluate the integral
integral_evaluation = rectangle_quadrature(f, a, b)

# b)plot the graph
x = a:0.001:b;
y = f(x);

hold on;
axis([a b f(b) f(a)])
plot(x, f(x),'r');
plot([1,1,1.5,1.5], [0,f((a+b)/2),f((a+b)/2), 0], 'b');
legend('f(x)', 'rectangle');

# c)
fprintf('Repeated rectangle formula for %d: %.20f\n', 150, repeated_rectangle_quadrature(f, a, b, 150));
fprintf('Repeated rectangle formula for %d: %.20f\n', 500, repeated_rectangle_quadrature(f, a, b, 500));