package com.example.bugwarshealerbackend.game;

import com.example.bugwarshealerbackend.model.Bug;
import com.example.bugwarshealerbackend.model.GameMap;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
@ActiveProfiles("test")
public class GameEngineTest {

    @Test
    public void game_engine_turn_right_all_bugs() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X                           X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        int[] script = new int[1];
        script[0] = 11;
        List<GameMap> replay = GameEngine.play(map, script, script, script, script, 1);
        //Assert
        Assert.assertEquals(2, replay.size());
        GameMap initialMap = replay.get(0);
        Assert.assertEquals(map, initialMap);
        GameMap nextMap = replay.get(1);
        List<Bug> bugs = map.getBugs();
        List<Bug> nextMapBugs = nextMap.getBugs();
        for(int i = 0; i < bugs.size(); i++){
            Bug bug = bugs.get(i);
            Bug nextMapBug = nextMapBugs.get(i);
            Assert.assertEquals(bug.getRow(), nextMapBug.getRow());
            Assert.assertEquals(bug.getColumn(), nextMapBug.getColumn());
            Assert.assertNotEquals(bug.getDirection(),nextMapBug.getDirection());
        }

    }
    @Test
    public void game_engine_bug_noop_move() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X                           X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        int[] script = new int[1];
        script[0] = 0;
        List<GameMap> replay = GameEngine.play(map, script, script, script, script, 1);
        //Assert
        Assert.assertEquals(2, replay.size());
        GameMap initialMap = replay.get(0);
        Assert.assertEquals(map, initialMap);
        GameMap nextMap = replay.get(1);
        List<Bug> bugs = map.getBugs();
        List<Bug> nextMapBugs = nextMap.getBugs();
        for (int i = 0; i < bugs.size(); i++) {
            Bug bug = bugs.get(i);
            Bug nextMapBug = nextMapBugs.get(i);
            Assert.assertEquals(bug.getRow(), nextMapBug.getRow());
            Assert.assertEquals(bug.getColumn(), nextMapBug.getColumn());
            Assert.assertEquals(bug.getDirection(), nextMapBug.getDirection());
        }
    }

    @Test
    public void game_engine_bug_move(){
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X                           X
X            a              X
X                           X
X    b       f        c     X
X                           X
X            d              X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        int[] script = new int[1];
        script[0] = 10;
        List<GameMap> replay = GameEngine.play(map, script, script, script, script, 1);
        //Assert
        Assert.assertEquals(2, replay.size());
        GameMap initialMap = replay.get(0);
        Assert.assertEquals(map, initialMap);
        GameMap nextMap = replay.get(1);
        List<Bug> bugs = map.getBugs();
        List<Bug> nextMapBugs = nextMap.getBugs();
        for (int i = 0; i < bugs.size(); i++) {
            Bug bug = bugs.get(i);
            Bug nextMapBug = nextMapBugs.get(i);
            Assert.assertEquals(bug.getRow() - 1, nextMapBug.getRow());
            Assert.assertEquals(bug.getColumn(), nextMapBug.getColumn());
            Assert.assertEquals("Bug direction: " + bug.getDirection() + ", next bug direction: " + nextMapBug.getDirection(), bug.getDirection(), nextMapBug.getDirection());
        }
    }

    @Test
    public void game_engine_rotate_and_move(){
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X                           X
X            a              X
X                           X
X    b       f        c     X
X                           X
X            d              X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        int[] script = new int[2];
        script[0] = 11;
        script[1] = 10;
        List<GameMap> replay = GameEngine.play(map, script, script, script, script, 2);
        //Assert
        Assert.assertEquals(3, replay.size());
        GameMap initialMap = replay.get(0);
        Assert.assertEquals(map, initialMap);
        GameMap nextMap = replay.get(2);
        List<Bug> bugs = map.getBugs();
        List<Bug> nextMapBugs = nextMap.getBugs();
        for (int i = 0; i < bugs.size(); i++) {
            Bug bug = bugs.get(i);
            Bug nextMapBug = nextMapBugs.get(i);
            Assert.assertEquals(bug.getRow(), nextMapBug.getRow());
            Assert.assertEquals(bug.getColumn() + 1, nextMapBug.getColumn());
            Assert.assertNotEquals("Bug direction: " + bug.getDirection() + ", next bug direction: " + nextMapBug.getDirection(), bug.getDirection(), nextMapBug.getDirection());
        }
    }

    @Test
    public void game_engine_check_new_bug_after_eat_food(){

    }

    @Test
    public void game_engine_conflicting_command_eat(){

    }

    @Test
    public void game_engine_conflicting_command_eat_and_run(){

    }

    @Test
    public void game_engine_conflicting_command_eat_same_food(){

    }

}