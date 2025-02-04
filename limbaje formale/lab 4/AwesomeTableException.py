class AwesomeTableException(Exception):

    def __init__(self, message="Something went wrong"):
        self.message = message
        super().__init__(self.message)

    def __str__(self):
        return f'{self.message}'