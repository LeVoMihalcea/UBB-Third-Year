from Grammar import Grammar
from Production import Production
from State import State, StateType


class RDParser:
    def __init__(self, grammar, inpt):
        self.__grammar = grammar
        self.__input = inpt

    def __get_next_prod(self, prod, productions):
        for i in range(len(productions)):
            if prod == productions[i] and i < len(productions) - 1:
                return productions[i + 1]
        return None

    def recursive_descent_run(self):
        state = State(self.__grammar.getStartSymbol())
        while state.type != StateType.FINAL and state.type != StateType.ERROR:
            print(state)
            if state.type == StateType.NORMAL:
                if len(state.input_stack) == 0 and state.index == len(self.__input):
                    state.type = StateType.FINAL
                elif len(state.input_stack) == 0:
                    state.type = StateType.BACK
                else:
                    if state.input_stack[0] in self.__grammar.getNonTerminals():
                        non_term = state.input_stack[0]
                        first_prod = self.__grammar.getProdForNT(non_term)[0]
                        state.work_stack.append((first_prod.getLeftSide(), first_prod.getRightSide()))
                        state.input_stack = first_prod.getRightSide() + state.input_stack[1:]
                    else:
                        if state.index == len(self.__input):
                            state.type = StateType.BACK
                        elif state.input_stack[0] == StateType.ERROR:
                            state.work_stack.append(StateType.ERROR)
                            state.input_stack = state.input_stack[1:]
                        elif state.input_stack[0] == self.__input[state.index]:
                            state.index += 1
                            state.work_stack.append(state.input_stack[0])
                            state.input_stack = state.input_stack[1:]
                        else:
                            state.type = StateType.BACK
            else:
                if state.type == StateType.BACK:
                    if state.work_stack[-1] in self.__grammar.getTerminals():
                        if state.work_stack[-1] == StateType.ERROR:
                            state.work_stack.pop(-1)
                        else:
                            state.index -= 1
                            terminal = state.work_stack.pop(-1)
                            state.input_stack = [terminal] + state.input_stack
                    else:
                        last_production = state.work_stack[-1]
                        productions = self.__grammar.getProdForNT(last_production[0])
                        productions = [(prod.getLeftSide(), prod.getRightSide()) for prod in productions]
                        next_prod = self.__get_next_prod(last_production, productions)
                        if next_prod:
                            state.type = StateType.NORMAL
                            state.work_stack.pop(-1)
                            state.work_stack.append((next_prod[0], next_prod[1]))
                            state.input_stack = state.input_stack[len(last_production[1]):]
                            state.input_stack = next_prod[1] + state.input_stack
                        elif state.index == 0 and last_production[0] == self.__grammar.getStartSymbol():
                            state.type = StateType.ERROR
                        else:
                            state.work_stack.pop(-1)
                            if last_production[1] == [StateType.ERROR]:
                                state.input_stack = [last_production[0]] + state.input_stack
                            else:
                                state.input_stack = [last_production[0]] + state.input_stack[
                                                                            len(last_production[1]):]

        prod_rules = []
        if state.type == StateType.ERROR:
            return False, []
        else:
            for prod in state.work_stack:
                if len(prod) > 1:
                    if Production(prod[0], prod[1]) in self.__grammar.getProductions():
                        prod_rules.append(prod)

        return True, prod_rules


g = Grammar("resources/grammars/g1.txt")
rdp = RDParser(g, list("aacbc"))
isValid, seq = rdp.recursive_descent_run()
print(isValid)

if isValid:
    print("Sequence is valid")
    print(seq)
else:
    print("Sequence is not valid")