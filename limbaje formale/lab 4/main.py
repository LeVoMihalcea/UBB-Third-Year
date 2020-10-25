import sys
import re
from HashMap import HashMap
from PIF import PIF
from utils import is_constant_or_identifier

reservedWords = ['read', 'write', 'or', 'and', 'not', 'break', 'start', 'Char', 'continue', 'do', 'else',
                 'float', 'for', 'while', 'if', 'pass', 'return', 'Integer', 'String', 'Float', 'Boolean']
reservedOperatorsSeparators = ['+', '-', '*', '/', '%', '=', '==', '!=', '>=', '<=', '>', '<', '[', ']',
                               '{', '}', '(', ')', ';', ':', ' ', '"', "'"]
pif = PIF()
st = HashMap()


if len(sys.argv) != 2:
    raise Exception("analyze <input_file_name>")
else:
    with open(sys.argv[1]) as f:
        line_number = 1
        line = f.readline()
        while line:
            print(line)
            split = re.split('([^\"\'A-Za-zA-Z_0-9\"])', line)
            split = list(filter(lambda x: x is not None and x != '', map(lambda x: x.strip(), split)))
            print(split)

            for token in split:
                if token in reservedWords or token in reservedOperatorsSeparators:
                    pif[token] = 0
                elif is_constant_or_identifier(token):
                    index = st.add(token)
                    pif[token] = index
                else:
                    raise Exception("Lexical error. Invalid token: '{}' on line {}".format(token, line_number))
            line = f.readline()
            line_number += 1