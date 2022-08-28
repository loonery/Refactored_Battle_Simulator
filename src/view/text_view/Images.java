package view.text_view;

public enum Images {

    BEGIN_BATTLE("""

          O                                     O
    {o)xxx|===============-  *  -===============|xxx(o}
          O                                     O
                        
    """),

    BARE_HANDS("""
                

                    .--.___.----.___.--._
                  /|  |   |    |   |  | `--.
                /                          `.
                |       |        |           |
                |       |        |      |     |
                '|  `    | ` ` `  |  ` ` |  `  |
                ||`   `  |     `  |   `  |`   `|
                ||  `    |  `     | `    |  `  |
                ||  ___  |  ____  |  __  |  _  |
                |\\_____/ \\______/ \\____/ \\___/
                |     `----._
                |    /       \\'
                |         .--.\\'
                |        '    \\'
                `.      |  _.-'
                    `.|__.-'
            """),

    HIT("""
                                   |     \s
                   //              |     \s
              O===[================|====-\s
                   \\              |     \s
                                   |     \s
            """),

    MISS("""
                                 ___        \s
                                    ___    \s
                                     _\\   \s
                                       \\  \s
                   //                  \\ \s
              O===[====================-  \s
                   \\                     \s
                                          \s
            """),


    MOVE("""
                                                  _
                                                _( }
                                         (\\   _ <<\\\s
                                          `.\\__/`/\\\\
                                    --=      '--'\\\\  "
                                        --=     //
                                               \\)
            """),

    RANGED_HIT("""
                            
                            
            \s           |
            - - -   >>---|--->
                         |\040
            \s
                            
                            
            """),

    RANGED_MISS("""
                            
                            
            \s
            - - -   >>------>
            \s
                            
                            
            """);

    public final String label;

    Images(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
