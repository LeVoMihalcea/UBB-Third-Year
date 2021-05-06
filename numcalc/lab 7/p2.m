t = [0 10 20 30 40 60 80 100];
p = [0.0061 0.0123 0.0234 0.0424 0.0738 0.1992 0.4736 1.0133];
exactValue = 0.095848;

%a

% 2nd degree polynomial 
secondDegreePolynomial = polyfit(t,p,2);
valueFor2ndDegree = polyval(secondDegreePolynomial,45)
error2ndDegree = exactValue - valueFor2ndDegree

% 3rd degree polynomial
thirdDegreePolynomial = polyfit(t,p,3);
valueFor3rdDegree = polyval(thirdDegreePolynomial,45)
error3rdDegree = exactValue - valueFor3rdDegree


%b
xp = 0:0.1:100;
hold on
%plot the points
plot(t, p, 'bo')
%plot the 2nd degree polynomial
plot(xp,polyval(secondDegreePolynomial,xp),'r-');
%plot the 3rd degree polynomial
plot(xp,polyval(thirdDegreePolynomial,xp),'g-');
legend('points','2nd degree polynomial','3rd degree polynomial');