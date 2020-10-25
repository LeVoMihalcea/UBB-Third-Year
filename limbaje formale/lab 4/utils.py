import re


def is_constant_or_identifier(token):
    try:
        int(token)
    except:
        return re.match("^[0-9\"']", token) is None or re.match('^"[a-zA-Z0-9]+"$', token) is not None \
                or re.match("^'[a-zA-Z0-9]'$", token) is not None
    return True