class PIF:
    def __init__(self):
        self.__data = []

    def __setitem__(self, key, pos):
        self.__data.append((key, pos))

    def __str__(self):
        return "\n".join(map(str, self.__data))