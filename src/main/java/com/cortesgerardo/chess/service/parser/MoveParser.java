package com.cortesgerardo.chess.service.parser;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.text.ParseException;

@Slf4j
public class MoveParser {
    public static MoveEntry parse(String input) {

        // Initialize parser
        AlgebraicNotationLexer lexer = new AlgebraicNotationLexer(CharStreams.fromString(input));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        AlgebraicNotationParser parser = new AlgebraicNotationParser(tokenStream);

        parser.removeErrorListeners();
        lexer.removeErrorListeners();
        parser.addErrorListener(MyAntlrErrorListener.INSTANCE);
        lexer.addErrorListener(MyAntlrErrorListener.INSTANCE);

        ParseTree tree = parser.move();

        ParseTreeWalker walker = new ParseTreeWalker();
        MoveListener listener = new MoveListener();

        walker.walk(listener, tree);

        if (MyAntlrErrorListener.INSTANCE.hasErrors()) {
            throw new RuntimeException("The text provided is invalid");
        }
        return listener.getCurrent();
    }
}
