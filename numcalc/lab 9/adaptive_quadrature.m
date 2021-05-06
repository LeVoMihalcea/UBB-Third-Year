function A = adaptive_quadrature(f, a, b, err)
  I1 = simpson(f, a, b);
  I2 = simpson(f, a, (a + b) / 2) + simpson(f, (a + b) / 2, b);
  
  if (abs(I1 - I2) < 15 * err)
    A = I2;
  else
    A = adaptive_quadrature(f, a, (a + b) / 2, err / 2) + adaptive_quadrature(f, (a + b) / 2, b, err / 2);  
  endif
endfunction