x = [0, pi/2, pi, 3*pi/2, 2*pi];
y = sin(x);

X = pi/4;
fv = sin(X)
ns = spline(x, y);
Yns = ppval(ns, X)
clamped = spline(x, [1 y 1]);
Ycs = ppval(clamped, X)

points = 0:0.01:2*pi;

plot(
  x, y, 'o',
  points, sin(points), 'g-', 
  points, ppval(ns, points), 'r-', 
  points, ppval(clamped, points), 'b-'
);

legend(
  'sample points',
  'function',
  'cubic natural spline', 
  'cubic clamped spline'
);     