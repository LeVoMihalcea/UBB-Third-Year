function ret = dividedDiffTable(x, f)
  [~, n] = size(x); % size returns a tuple (nrLines, nrCols), we keep only the number of columns
  m = zeros(n, n+1); % zeros initializes a matrix populated by 0s
  m(:, 1) = x'; % first column of m is x trasnposed
  m(:, 2) = f'; % second column of m is f transposed
  for col = 3:n+1 % we start from the third column
      for line = 1:n % go line by line
          if line + (col - 2) <= n && not(isnan(m(line, col-1))) % check we're still inside the bounds
              m(line, col) = (m(line+1, col-1) - m(line, col-1)) / (m(line + col - 2, 1) - m(line, 1)); % we compute
          else
              m(line, col) = nan; % NaN is we're out of bounds
          end % i still hate this syntax 
      end
  end
  ret = m; % we return m, our table
end