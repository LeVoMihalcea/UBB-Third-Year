lambda_f = @(x) sin(2*x);
lambda_df = @(x) 2*cos(2*x);

points = [-6: 0.05: 6];

x = linspace(-5, 5, 15);
f = lambda_f(x);
df = lambda_df(x);

h = HermitePol(x, f, df, points);

hold on

plot(points, lambda_f(points), 'b');
plot(x, f, '*');
plot(points, h, 'r');

hold off
