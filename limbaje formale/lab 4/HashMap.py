from AwesomeTableException import AwesomeTableException


class HashMap:

    def __init__(self, size=50):
        self.__table = [None] * size
        self.__size = size

    # in: key - dictionary key
    # out: the function returns a hash code for the specified key argument_
    def hash_code(self, key) -> int:
        return hash(key) % self.__size

    # in: key - dictionary key
    # out: function adds the key to the table and returns position of the key
    def add(self, key) -> int:
        position = self.hash_code(key)
        if self.__table[position] is None:
            self.__table[position] = [key]
        else:
            for k in self.__table[position]:
                if k == key:
                    break
            else:
                self.__table[position].append(key)
        return position

    # in: key - dictionary key
    # out: value corresponding to the key and None if key not is not found
    def __getitem__(self, key):
        position = self.hash_code(key)
        if self.__table[position] is None:
            return None
        else:
            for k in self.__table[position]:
                if k == key:
                    return position
            return None

    # in: key - dictionary key
    # out: deletes the key from the table
    #      raises Exception if key does not exist
    def __delitem__(self, key):
        position = self.hash_code(key)
        if self.__table[position] is None:
            raise AwesomeTableException("Given key does not exist in the table")
        else:
            for i, k in enumerate(self.__table[position]):
                if k == key:
                    del self.__table[position][i]
                    break
            else:
                raise AwesomeTableException("Given key does not exist in the table")

    def __str__(self):
        to_return = '---Symbol Table--- (hashtable)\n'
        for i in range(len(self.__table)):
            if self.__table[i] is not None:
                to_return += str(i) + ". " + str(self.__table[i]) + '\n'
        return to_return
