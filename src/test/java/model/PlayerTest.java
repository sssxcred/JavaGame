package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static utils.Constants.Directions.RIGHT;
import static utils.Constants.PlayConstants.IDLE;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player(100, 200, 50, 50, 100);
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals(100, player.getX());
        assertEquals(200, player.getY());
        assertEquals(50, player.getWidth());
        assertEquals(50, player.getHeight());
        assertEquals(100, player.getHp());
        assertEquals(RIGHT, player.getDirection());
        assertEquals(IDLE, player.getAction());
    }

    @Test
    public void testJump() {
        // Test jump functionality
        player.jump();
        assertTrue(player.isInAir());
        assertEquals(player.getJumpSpeed(), player.getAirSpeed(), 0.01);
    }

    @Test
    public void testResetInAir() {
        player.jump();
        assertTrue(player.isInAir());

        player.setInAir(false);
        player.setAirSpeed(0);

        assertFalse(player.isInAir());
        assertEquals(0, player.getAirSpeed(), 0.01);
    }

    @Test
    public void testAttackingState() {

        player.setAttacking(true);
        assertTrue(player.isAttacking());

        player.setAttacking(false);
        assertFalse(player.isAttacking());

        player.setAttackingFinished(false);
        assertFalse(player.isAttackingFinished());

        player.setAttackingFinished(true);
        assertTrue(player.isAttackingFinished());
    }

    @Test
    public void testAttackDamage() {

        player.setAttackDamage(20);
        assertEquals(20, player.getAttackDamage());

        player.setAttackDamage(30);
        assertEquals(30, player.getAttackDamage());
    }

    @Test
    public void testLoadLevelData() {

        int[][] sampleLvlData = {{0, 1}, {1, 0}};
        player.loadLevelData(sampleLvlData);

        assertArrayEquals(sampleLvlData, player.getLvlData());
    }

    @Test
    public void testGravity() {

        float newGravity = 0.05f;
        player.setGravity(newGravity);
        assertEquals(newGravity, player.getGravity(), 0.01);
    }

    @Test
    public void testJumpSpeed() {

        float newJumpSpeed = -3.0f;
        player.setJumpSpeed(newJumpSpeed);
        assertEquals(newJumpSpeed, player.getJumpSpeed(), 0.01);
    }

    @Test
    public void testFallSpeedAfterCollision() {

        float newFallSpeed = 0.6f;
        player.setFallSpeedAfterCollision(newFallSpeed);
        assertEquals(newFallSpeed, player.getFallSpeedAfterCollision(), 0.01);
    }
}
