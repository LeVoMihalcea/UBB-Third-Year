warning('off');

n1 = 10;
n2 = 15;

for i = n1:n2
  i
  cond(hilb(i), 2)
endfor