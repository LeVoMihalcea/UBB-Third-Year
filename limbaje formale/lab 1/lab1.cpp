//program 1

start(): Integer{
	Integer number_1 = 10;
	Integer number_2 = 20;
	Integer number_3 = 30;

	if ( number_1 > number_2 && number_1 > number_3 ){
		print(number_1);
	}
	else if ( number_2 > number_1 && number_2 > number_3 ){
		print(number_2);
	}
	else if ( number_3 > number_1 && number_3 > number_2 ){
		print(number_3);
	}
	else{
		print("Values are not unique");
	}   
	return 0;
}

//program 2

start(): Integer {
	Integer i, n;
	Boolean is_prime = true;

	read(n);

    // 0 and 1 are not prime numbers
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

//program 3

start(): Integer{
	Integer[] my_array = [1, 2, 3, 4, 5];
	Integer array_length = 5, i = 0;
	Integer sum = 0;


	for(i = 0; i < array_length; i++){
		sum = sum + my_array[i];
	}

	print(sum);

}

//program 4

start(): Integer{
	Integer[] my@array = [1, 2, 3, 4, 5]; //prima eroare
	Integer array_length = 5, i = 0; //a doua eroare
	Integer sum = 0;


	for(i = 0; i < array_length; i++){
		sum = sum + my_array[ĂĂĂĂĂĂĂ];
	}

	print(sum);
}