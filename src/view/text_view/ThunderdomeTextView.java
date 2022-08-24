package view;

public class ThunderdomeTextView implements IThunderdomeView {


    @Override
    public void displayWelcomeScreen() {

    }

    @Override
    public void displayMissedMelee() {

        System.out.println(

                """
                                             ___      \s
                                                ___    \s
                                                 _\\   \s
                                                   \\  \s
                               //                  \\ \s
                          O===[====================-  \s
                               \\                     \s
                                                      \s
                        """
        );
    }

    @Override
    public void displayHitMelee() {

        System.out.println(

                """
                                               |     \s
                               //              |     \s
                          O===[================|====-\s
                               \\              |     \s
                                               |     \s
                        """
        );

    }

    @Override
    public void displayMissedRanged() {

        System.out.println("""
                
                
                \s
                - - -   >>------>
                \s
                
                
                """);

    }

    @Override
    public void displayHitRanged() {

        System.out.println("""
                
                
                \s           |
                - - -   >>---|--->
                             |\040
                \s
                
                
                """);

    }

    @Override
    public void displayMovement() {

        System.out.println("""
                                                      _
                                                    _( }
                                             (\\   _ <<\\\s
                                              `.\\__/`/\\\\
                                        -=      '--'\\\\  "
                                            -=     //
                                                   \\)
                """);


    }

    @Override
    public void displayMeleeCombat() {

    }

    @Override
    public void displayWeaponBreak() {

    }

    // todo : need to add methods that result in character and weapon information
    //  being printed while maintaining separation of concerns


    // todo: need to add

}
