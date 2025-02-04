import sys
import re
from HashMap import HashMap
from PIF import PIF
from utils import is_float_or_identifier, reservedWords, reservedOperatorsSeparators, readFiniteAutomata


class Scanner:
    pif = PIF()
    st = HashMap()
    fa_integer = readFiniteAutomata("fa_input/integer.in")
    fa_float = readFiniteAutomata("fa_input/float.in")
    fa_constant_name = readFiniteAutomata("fa_input/constants.in")
    fa_string = readFiniteAutomata("fa_input/string.in")
    fa_string.transitions.append([('q1', 'q1'), ' '])

    fa_list = [fa_integer, fa_float, fa_constant_name, fa_string]
    def __init__(self):
        if len(sys.argv) != 2:
            raise Exception("analyze <input_file_name>")
        else:
            output = open(sys.argv[1][:-2] + ".out", "w")
            try:
                with open(sys.argv[1]) as f:
                    line_number = 1
                    line = f.readline()
                    while line:
                        print("-----------\n", line)
                        split = re.split('([^A-Za-zA-Z_0-9&.,=><\|-])', line)
                        split = list(filter(lambda x: x is not None and x != '', map(lambda x: x.strip(), split)))
                        print(split)
                        buffer = ""
                        for token in split:
                            if token == '"':
                                if buffer == "":
                                    buffer += token + " "
                                else:
                                    token = buffer[2:]
                                    buffer = ""
                            elif buffer != "":
                                buffer += token + " "
                                continue

                            if token in reservedWords or token in reservedOperatorsSeparators:
                                self.pif[token] = 0
                            elif is_float_or_identifier(self.fa_list, token):
                                index = self.st.add(token)
                                self.pif[token] = index
                            else:
                                raise Exception(
                                    "Lexical error. Invalid token: '{}' on line {}".format(token, line_number))
                        line = f.readline()
                        line_number += 1

                    output.write(str(self.pif))
                    output.write("\n")
                    output.write(str(self.st))
            except Exception as e:
                output.write(str(e))


scanner = Scanner()
