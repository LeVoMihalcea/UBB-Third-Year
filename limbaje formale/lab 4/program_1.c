start(): Integer{
    Integer number_1 = 1.5;
    Integer number_2 = 20;
    Integer number_3 = 30;
    if (number_1 > number_2 && number_1 > number_3){
        print(number_1);
    }
    else if ( number_2 > number_1 && number_2 > number_3 ){
        print(number_2);
    }
    else if ( number_3 >= number_1 && number_3 <= number_2 ){
        print(number_3);
    }
    else{
        print("Values are not unique");
    }
    return 0;
}
