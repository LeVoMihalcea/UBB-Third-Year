f = @(x) cos(x);
x = [0:pi/4:2*pi];
y = [0:0.01:2*pi];

hold on

plot(y, f(y), 'r');
plot(y, interp1(x, f(x), y), 'b');
legend('function graph', 'linear polinomyal spline');

hold off