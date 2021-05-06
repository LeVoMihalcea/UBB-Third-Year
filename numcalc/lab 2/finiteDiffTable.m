function ret = finiteDiffTable(x, f) % ret is what we return
  [~, n] = size(x); % size returns a tuple (nrLines, nrCols), we keep only the number of columns
  m = zeros(n, n+1); % zeros initializes a matrix populated by 0s
  m(:, 1) = x'; % first column of m is x trasnposed
  m(:, 2) = f'; % second column of m is f transposed
  for col = 3:n+1 % we start from the third column
      for line = 1:n % go line by line
          if line + 1 <= n && m(line, col-1) != 0 % check we're still inside the bounds
              m(line, col) = m(line+1, col-1) - m(line, col-1); % we compute the difference
          else
              m(line, col) = 0; % if we are out of bounds, we put a NaN instead
          end % i kinda hate this syntax
      end
  end
  ret = m; % we return m
end