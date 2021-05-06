Time = [0 3 5 8 13];
Distance = [0 225 383 623 993];
Speed = [75 77 80 74 72];
X = 10

res = HermitePol(Time, Distance, Speed, X) %symbolic Hermite polynomial

speed = res / X