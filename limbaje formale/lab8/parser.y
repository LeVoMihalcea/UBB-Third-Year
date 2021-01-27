%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1
%}

%token AND
%token OR

%token ANDAND
%token OROR
%token NOT

%token BREAK
%token CONTINUE
%token PASS
%token DO
%token IF
%token ELSE
%token WHILE
%token RETURN
%token START
%token PRINT
%token READ

%token PLUS
%token MINUS
%token MULTIPLICATION
%token MOD
%token EQUAL
%token LESS
%token GREATER
%token LESS_OR_EQUAL
%token GREATER_OR_EQUAL
%token NOT_EQUAL
%token INCREMENT
%token DECREMENT

%token LEFT_CURLY_BRACKETS
%token RIGHT_CURLY_BRACKETS
%token LEFT_ROUND_PARENTHESIS
%token RIGHT_ROUND_PARENTHESIS
%token LEFT_SQUARE_PARENTHESIS
%token RIGHT_SQUARE_PARENTHESIS
%token SEMICOLON
%token COLON
%token COMMA

%token INTEGER
%token STRING
%token CHARACTER
%token FLOAT
%token IDENTIFIER


%start program

%%

program : START LEFT_ROUND_PARENTHESIS RIGHT_ROUND_PARENTHESIS COLON INTEGER compoundStatement ;

compoundStatement : LEFT_CURLY_BRACKETS statement RIGHT_CURLY_BRACKETS | LEFT_CURLY_BRACKETS statement statements RIGHT_CURLY_BRACKETS ;
	statement : declarationStatement | assignmentStatement | ifStatement | whileStatement | ioStatement | compoundStatement | returnStatement;
	statements : statement | statement statements ;

declarationStatement : type IDENTIFIER SEMICOLON | type LEFT_SQUARE_PARENTHESIS RIGHT_SQUARE_PARENTHESIS identifierList SEMICOLON | type assignmentStatement;
	identifierList: IDENTIFIER LEFT_SQUARE_PARENTHESIS INTEGER RIGHT_SQUARE_PARENTHESIS ;
	listIndex : IDENTIFIER LEFT_SQUARE_PARENTHESIS INTEGER RIGHT_SQUARE_PARENTHESIS ;

assignmentStatement : IDENTIFIER EQUAL expression SEMICOLON | listIndex EQUAL expression SEMICOLON ;
	expression : INTEGER | FLOAT | STRING | IDENTIFIER | term operator term ;
	term: INTEGER | FLOAT | STRING | IDENTIFIER | listIndex ; 
	operator : PLUS | MINUS | MOD | MULTIPLICATION | EQUAL | LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL | NOT_EQUAL | INCREMENT | DECREMENT ;

ifStatement : IF condition compoundStatement | IF condition compoundStatement ELSE compoundStatement | IF condition compoundStatement ELSE ifStatement;
	condition : LEFT_ROUND_PARENTHESIS evaluation RIGHT_ROUND_PARENTHESIS | LEFT_ROUND_PARENTHESIS evaluation continuation RIGHT_ROUND_PARENTHESIS;
	continuation: ANDAND evaluation | OROR evaluation;
	evaluation: expression relation expression;
	relation : GREATER | LESS | GREATER_OR_EQUAL | LESS_OR_EQUAL | EQUAL | NOT_EQUAL ;

whileStatement : WHILE condition compoundStatement ;

ioStatement : READ LEFT_ROUND_PARENTHESIS IDENTIFIER RIGHT_ROUND_PARENTHESIS SEMICOLON | PRINT LEFT_ROUND_PARENTHESIS IDENTIFIER RIGHT_ROUND_PARENTHESIS SEMICOLON 
	| PRINT LEFT_ROUND_PARENTHESIS STRING RIGHT_ROUND_PARENTHESIS SEMICOLON;

returnStatement: RETURN IDENTIFIER SEMICOLON | RETURN INTEGER SEMICOLON;

type: INTEGER | FLOAT | STRING



%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

int main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}


