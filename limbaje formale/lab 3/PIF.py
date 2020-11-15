class PIF:
    def __init__(self):
        self.__data = {}

    def __setitem__(self, key, pos):
        self.__data[key] = pos

    def __str__(self):
        to_return = '---PIF---\n'
        for element in self.__data.keys():
            to_return += str(element) + " --- " + str(self.__data[element]) + "\n"
        return to_return
