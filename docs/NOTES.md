# Notes


## in flex examining context

```flex
    // Single character command abbreviations - only at command start
    {F_CMD} / [^a-zA-Z0-9_] {
        if (isCommandContext()) return COMMAND_STACK;
        else { yypushback(yylength()); return advance(); }
    }
```
