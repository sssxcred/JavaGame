package utils;

/**
 * The {@code Constants} class provides constants used throughout the game.
 */
public class Constants {

    /**
     * The {@code Directions} class provides constants representing different directions.
     */
    public static class Directions {
        /** Represents the left direction. */
        public static final int LEFT = 0;
        /** Represents the up direction. */
        public static final int UP = 1;
        /** Represents the right direction. */
        public static final int RIGHT = 2;
        /** Represents the down direction. */
        public static final int DOWN = 3;
    }

    /**
     * The {@code PlayConstants} class provides constants representing player actions.
     */
    public static class PlayConstants {
        /** Represents the landing action. */
        public static final int LANDING = 0;
        /** Represents the running action. */
        public static final int RUNNING = 1;
        /** Represents the attacking action. */
        public static final int ATTACKING = 2;
        /** Represents the dashing action. */
        public static final int DASHING = 3;
        /** Represents the dying action. */
        public static final int DYING = 4;
        /** Represents the falling action. */
        public static final int FALLING = 5;
        /** Represents the hitting action. */
        public static final int HITTING = 6;
        /** Represents the idle action. */
        public static final int IDLE = 7;
        /** Represents the jump action. */
        public static final int JUMP = 8;

        /**
         * Gets the number of sprite images associated with a player action.
         *
         * @param playerAction the player action
         * @return the number of sprite images
         */
        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                case LANDING:
                case DASHING:
                case HITTING:
                    return 2;
                case RUNNING:
                    return 12;
                case ATTACKING:
                    return 9;
                case DYING:
                    return 22;
                case FALLING:
                    return 4;
                case IDLE:
                    return 16;
                case JUMP:
                    return 3;
                default:
                    return 0;
            }
        }
    }
}
