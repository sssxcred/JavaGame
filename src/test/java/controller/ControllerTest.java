package controller;

import model.BloodShard;
import model.Enemy;
import model.Level;
import model.Player;
import org.junit.Before;
import org.junit.Test;
import view.Panel;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ControllerTest {

    private Player player;
    private Enemy enemy;
    private Level level;
    private Panel panel;
    private GameManager gameManager;
    private Controller controller;

    @Before
    public void setUp() {
        player = mock(Player.class);
        enemy = mock(Enemy.class);
        level = mock(Level.class);
        panel = mock(Panel.class);
        gameManager = mock(GameManager.class);

        controller = new Controller(player, enemy, level, panel, gameManager);
    }

    @Test
    public void testHandleJump_PlayerNotInAir() {
        when(player.isInAir()).thenReturn(false);
        when(player.getJumpSpeed()).thenReturn(5F);

        controller.handleJump();

        verify(player).setJump(true);
        verify(player).setInAir(true);
        verify(player).setAirSpeed(5);
    }

    @Test
    public void testHandleInteraction_PlayerCollectsBloodShard() {
        BloodShard shard = mock(BloodShard.class);
        ArrayList<BloodShard> bloodShards = new ArrayList<>();
        bloodShards.add(shard);

        when(player.getX()).thenReturn(50);
        when(player.getY()).thenReturn(50);
        when(player.getHitboxWidth()).thenReturn(10);
        when(player.getHitboxHeight()).thenReturn(10);
        when(shard.getX()).thenReturn(50);
        when(shard.getY()).thenReturn(50);
        when(shard.getWidth()).thenReturn(10);
        when(shard.getHeight()).thenReturn(10);
        when(shard.isCollected()).thenReturn(false);

        controller.setBloodShards(bloodShards);
        controller.handleInteraction();

        verify(shard).collect();
    }

    @Test
    public void testCanMoveHere() {
        int[][] lvlData = new int[10][10];
        lvlData[5][5] = 0;

        boolean result = Controller.CanMoveHere(50, 50, 10, 10, lvlData);
        assertFalse(result);
    }

    @Test
    public void testIsPlayerOnFloor() {
        int[][] lvlData = new int[10][10];
        lvlData[6][5] = 1;  // Simulate floor

        when(player.getX()).thenReturn(50);
        when(player.getY()).thenReturn(50);
        when(player.getHitboxWidth()).thenReturn(10);
        when(player.getHitboxHeight()).thenReturn(10);

        boolean result = Controller.isPlayerOnFloor(player, lvlData);
        assertTrue(result);
    }
}
