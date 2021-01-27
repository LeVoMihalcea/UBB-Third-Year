/* A Bison parser, made by GNU Bison 3.5.1.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2020 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    AND = 258,
    OR = 259,
    ANDAND = 260,
    OROR = 261,
    NOT = 262,
    BREAK = 263,
    CONTINUE = 264,
    PASS = 265,
    DO = 266,
    IF = 267,
    ELSE = 268,
    WHILE = 269,
    RETURN = 270,
    START = 271,
    PRINT = 272,
    READ = 273,
    PLUS = 274,
    MINUS = 275,
    MULTIPLICATION = 276,
    MOD = 277,
    EQUAL = 278,
    LESS = 279,
    GREATER = 280,
    LESS_OR_EQUAL = 281,
    GREATER_OR_EQUAL = 282,
    NOT_EQUAL = 283,
    INCREMENT = 284,
    DECREMENT = 285,
    LEFT_CURLY_BRACKETS = 286,
    RIGHT_CURLY_BRACKETS = 287,
    LEFT_ROUND_PARENTHESIS = 288,
    RIGHT_ROUND_PARENTHESIS = 289,
    LEFT_SQUARE_PARENTHESIS = 290,
    RIGHT_SQUARE_PARENTHESIS = 291,
    SEMICOLON = 292,
    COLON = 293,
    COMMA = 294,
    INTEGER = 295,
    STRING = 296,
    CHARACTER = 297,
    FLOAT = 298,
    IDENTIFIER = 299
  };
#endif
/* Tokens.  */
#define AND 258
#define OR 259
#define ANDAND 260
#define OROR 261
#define NOT 262
#define BREAK 263
#define CONTINUE 264
#define PASS 265
#define DO 266
#define IF 267
#define ELSE 268
#define WHILE 269
#define RETURN 270
#define START 271
#define PRINT 272
#define READ 273
#define PLUS 274
#define MINUS 275
#define MULTIPLICATION 276
#define MOD 277
#define EQUAL 278
#define LESS 279
#define GREATER 280
#define LESS_OR_EQUAL 281
#define GREATER_OR_EQUAL 282
#define NOT_EQUAL 283
#define INCREMENT 284
#define DECREMENT 285
#define LEFT_CURLY_BRACKETS 286
#define RIGHT_CURLY_BRACKETS 287
#define LEFT_ROUND_PARENTHESIS 288
#define RIGHT_ROUND_PARENTHESIS 289
#define LEFT_SQUARE_PARENTHESIS 290
#define RIGHT_SQUARE_PARENTHESIS 291
#define SEMICOLON 292
#define COLON 293
#define COMMA 294
#define INTEGER 295
#define STRING 296
#define CHARACTER 297
#define FLOAT 298
#define IDENTIFIER 299

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
