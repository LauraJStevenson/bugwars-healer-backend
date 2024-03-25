package com.example.bugwarshealerbackend.game;

import com.example.bugwarshealerbackend.model.Bug;
import com.example.bugwarshealerbackend.model.Food;
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
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            f              X
X            a              X
X    f                f     X
X    b                c     X
X            f              X
X            d              X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        int[] script = new int[2];
        script[0] = 14;
        script[1] = 11;
        List<GameMap> replay = GameEngine.play(map, script, script, script, script, 2);
        //Assert
        Assert.assertEquals(3, replay.size());
        GameMap initialMap = replay.get(0);
        Assert.assertEquals(map, initialMap);
        GameMap nextMap = replay.get(2);
        List<Bug> bugs = map.getBugs();
        List<Bug> nextMapBugs = nextMap.getBugs();
        Assert.assertEquals(8, nextMapBugs.size());
        // assert the initial bugs
        assertBug(nextMapBugs.get(1), bugs.get(0).getRow(), bugs.get(0).getColumn(), 'E', bugs.get(0).getBugType());
        assertBug(nextMapBugs.get(4), bugs.get(1).getRow(), bugs.get(1).getColumn(), 'E', bugs.get(1).getBugType());
        assertBug(nextMapBugs.get(5), bugs.get(2).getRow(), bugs.get(2).getColumn(), 'E', bugs.get(2).getBugType());
        assertBug(nextMapBugs.get(7), bugs.get(3).getRow(), bugs.get(3).getColumn(), 'E', bugs.get(3).getBugType());
        // assert the new bugs
        assertBug(nextMapBugs.get(0), 1, 13, 'N', 'a');
        assertBug(nextMapBugs.get(2), 3, 5, 'N', 'b');
        assertBug(nextMapBugs.get(3), 3, 22, 'N', 'c');
        assertBug(nextMapBugs.get(6), 5, 13, 'N', 'd');
    }

    private void assertBug(Bug bug, int row, int column, char direction, char bugType) {
        Assert.assertEquals(row, bug.getRow());
        Assert.assertEquals(column, bug.getColumn());
        Assert.assertEquals(direction, bug.getDirection());
        Assert.assertEquals(bugType, bug.getBugType());
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