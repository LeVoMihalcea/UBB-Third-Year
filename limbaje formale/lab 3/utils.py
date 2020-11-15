import re

reservedWords = ['read', 'write', 'not', 'break', 'start', 'Char', 'continue', 'do', 'else',
                 'float', 'for', 'while', 'if', 'pass', 'return', 'Integer', 'String', 'Float', 'Boolean']
reservedOperatorsSeparators = ['+', '-', '*', '/', '%', '=', '==', '!=', '>=', '<=', '>', '<', '[', ']',
                               '{', '}', '(', ')', ';', ':', ' ', '"', "&&", "||", '"']

def is_float_or_identifier(token):
    try:
        if (token[0] == '0' and (len(token) > 1 or token[1] == '.')):
            return False
        float(token)
    except:
        return re.search("[a-zA-Z0-9 \.\&]*", token).group()
    return True