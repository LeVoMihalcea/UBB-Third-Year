x=[1 1.5 2 3 4];
f=[0 0.17609 0.30103 0.47712 0.60206];
xx=[2.5, 3.25];

s= NewtonPol_stud(x,f,xx)

i=[10:35];
yi=i./10;
fyi = log10(yi);
rez = max(abs(fyi - NewtonPol_stud(x,f,yi)))