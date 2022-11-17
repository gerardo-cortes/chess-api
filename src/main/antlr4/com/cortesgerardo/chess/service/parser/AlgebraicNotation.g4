grammar AlgebraicNotation;

// parser rules
move
    : piece start end
    ;

piece
    : TITLE?
    ;

start
    : FILE? RANK?
    ;

end
    : FILE RANK
    ;

// lexer rules
TITLE
    : [KQRBNP]
    ;

RANK
    : [1-8]
    ;

FILE
    : [a-h]
    ;