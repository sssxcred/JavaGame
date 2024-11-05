package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevelTest {

    private Level level;
    private int[][] sampleLvlData = {{0, 1}, {1, 0}};

    @Before
    public void setUp() {
        level = new Level(sampleLvlData);
    }

    @Test
    public void testSetCameraPosition() {
        level.setCameraPosition(100, 200);
        assertEquals(100, level.getCameraX());
        assertEquals(200, level.getCameraY());
    }

    @Test
    public void testGetSpriteIndex() {
        int index = level.getSpriteIndex(1, 0);
        assertEquals(1, index);
    }

    @Test
    public void testUpdateShopAnimation() {
        for (int i = 0; i < 31; i++) {
            level.updateShopAnimation();
        }
        assertEquals(1, level.getShopAnimationIndex());
    }
}
