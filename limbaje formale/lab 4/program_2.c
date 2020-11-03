start(): Integer {
	Integer i, n;
	Boolean is_prime = true;

	read(n);

	if (n == 0 || n == 1) {
		is_prime = false;
	}
	else {
		for (i=2; i <= n/2; i++) {
			if (n % i == 0) {
				is_prime = false;
				break;
			}
		}
	}
	if (is_prime){
		print("prime number");
	}
	else{
		print("not a prime number");
	}

	return 0;
}