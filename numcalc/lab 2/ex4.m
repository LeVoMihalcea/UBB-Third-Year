i = 0:6; % [0, 1, .., 6]
h = 0.25; % step
x = 1 + i .* h; % x computed by its formula  
f = sqrt(5 .* (x .^ 2) + 1); % list of f(x) values computed for every x

finiteDiffTable(x, f)