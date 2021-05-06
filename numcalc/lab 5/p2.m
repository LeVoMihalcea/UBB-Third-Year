x = [1, 2];
f = [0, 0.6931];
df = [1, 0.5];

approx_value = HermitePol(x, f, df, 1.5)

ans = log(1.5)
printf("abs approx error: %f\n", ans);
