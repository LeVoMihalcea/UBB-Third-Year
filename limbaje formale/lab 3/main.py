from HashMap import HashMap


def main():
    table = HashMap(50)

    table.add("some")
    table.add("random")
    table.add("text")
    table.add("for")
    table.add("testing")

    print(table["some"], table["random"], table["text"], table["not_you"], table["for"], table["testing"])

    del table["random"]
    del table["text"]

    table.add("test")

    print(table["some"], table["random"], table["text"], table["not_you"], table["testing"], table["test"])


main()
