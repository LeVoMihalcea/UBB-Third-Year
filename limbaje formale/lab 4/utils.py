import re

from FiniteAutomata import FiniteAutomata

reservedWords = ['read', 'write', 'not', 'break', 'start', 'Char', 'continue', 'do', 'else',
                 'float', 'for', 'while', 'if', 'pass', 'return', 'Integer', 'String', 'Float', 'Boolean']
reservedOperatorsSeparators = ['+', '-', '*', '/', '%', '=', '==', '!=', '>=', '<=', '>', '<', '[', ']',
                               '{', '}', '(', ')', ';', ':', ' ', '"', "&&", "||", '"']


def is_float_or_identifier(fa, token):
    try:
        if token[0] == '0' and (len(token) > 1 or token[1] == '.'):
            return False
        if isSequenceAccepted(fa, fa.start, list(token)):
            print("number is integer")
            return True
        return True
    except:
        return re.search("[a-zA-Z0-9 \.\&]*", token).group()
    return True


def readFiniteAutomata():
    with open('input.txt', 'r') as f:
        states = f.readline().strip().split(' ')
        alphabet = f.readline().strip().split(' ')
        start = f.readline().strip().split(' ')
        finals = f.readline().strip().split(' ')
        aux_transitions = f.readline().strip().split(';')
        transitions = []
        for transition in aux_transitions:
            transition = transition.split(' ')
            transitions.append([(transition[0], transition[1]), transition[2]])
        finiteAutomata = FiniteAutomata(states, alphabet, start, finals, transitions)

        return finiteAutomata


def ifFADeterministic(fa):
    transitions = []
    for elem in fa.transitions:
        current = [elem[0][0], elem[1]]
        if current in transitions:
            return False
        else:
            transitions.append(current)
    return True


def isSequenceAccepted(fa, startState, sequence):
    currentState = startState
    for element in sequence:
        transitions = fa.getTransitionsFromState(currentState)
        if element not in [tr[1] for tr in transitions]:
            return False
        for transition in transitions:
            if transition[1] == element and len(sequence) == 1 and transition[0][1] in fa.finals:
                return True
            if transition[1] == element:
                return isSequenceAccepted(fa, [transition[0][1]], sequence[1:])
        return False
    return False


def printMenu(fa):
    deterministic = ifFADeterministic(fa)
    print("deterministic:", deterministic)

    if not deterministic:
        return

    print("sequence: abbci; accepted:", isSequenceAccepted(fa, fa.start, "a b b c i".split()))
    print("sequence: abbc; accepted:", isSequenceAccepted(fa, fa.start, "a b b c".split()))
    print("sequence: abbg; accepted:", isSequenceAccepted(fa, fa.start, "a b b g".split()))

    print("FA Menu")
    print("1. states")
    print("2. alphabet")
    print("3. start")
    print("4. finals")
    print("5. transitions")
    print("6. check if a sequence is accepted")

    command = int(input(">"))
    while True:
        if command == 1:
            print(fa.states)
        elif command == 2:
            print(fa.alphabet)
        elif command == 3:
            print(fa.start)
        elif command == 4:
            print(fa.finals)
        elif command == 5:
            print(fa.transitions)
        elif command == 6:
            sequence = input("sequence:")
            print("sequence: abbci; accepted:", isSequenceAccepted(fa, fa.start, list(sequence)))
        else:
            print("invalid command")
        command = int(input(">"))


# fa = readFiniteAutomata()
# printMenu(fa)
