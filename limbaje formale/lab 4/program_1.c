start(): Integer{
    Integer number_1 = 0;
    Integer number_2 = 20.05;
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
