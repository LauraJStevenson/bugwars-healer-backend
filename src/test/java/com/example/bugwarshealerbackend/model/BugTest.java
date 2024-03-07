package com.example.bugwarshealerbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class BugTest {

    @Test
    public void execute_bug_move_correctly() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X                           X
X                           X
X            a              X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X    b       f        c     X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X           d               X
X                           X
X                           X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[1];
        script[0] = 10;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,13
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        Cell updatedCell = map.getCells()[2][13];
        Assert.assertTrue(updatedCell instanceof Bug);
        Bug updatedBug = (Bug) updatedCell;
        Assert.assertEquals(bug, updatedBug);
        Cell emptyArea = map.getCells()[3][13];
        Assert.assertTrue(emptyArea instanceof EmptySpace);
    }

    @Test
    public void execute_bug_next_to_wall(){
        //Arrange
GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            a              X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X    b       f        c     X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X           d               X
X                           X
X                           X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[1];
        script[0] = 10;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //2,14
        Assert.assertEquals(1,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        Cell sameCell = map.getCells()[1][13];
        Assert.assertTrue(sameCell instanceof Bug);
        Bug updatedBug = (Bug) sameCell;
        Assert.assertEquals(bug, updatedBug);
    }

    @Test
    public void execute_noop() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X                           X
X                           X
X            a              X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X    b       f        c     X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X                           X
X           d               X
X                           X
X                           X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[1];
        script[0] = 0;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //4,14
        Assert.assertEquals(3,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        Cell sameCell = map.getCells()[3][13];
        Assert.assertTrue(sameCell instanceof Bug);
        Bug updatedBug = (Bug) sameCell;
        Assert.assertEquals(bug, updatedBug);
    }

    @Test
    public void execute_bug_direction_turn_right() {
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
        Bug bug = map.getBugs().get(0);
        int[] script = new int[4];
        script[0] = 11;
        script[1] = 11;
        script[2] = 11;
        script[3] = 11;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        Cell sameCell = map.getCells()[2][13];
        Assert.assertTrue(sameCell instanceof Bug);
        Bug updatedBug = (Bug) sameCell;
        Assert.assertEquals(bug, updatedBug);
        //new direction
        Assert.assertEquals('E', bug.getDirection());
        bug.execute(map.getCells(),1);
        Assert.assertEquals('S', bug.getDirection());
        bug.execute(map.getCells(),2);
        Assert.assertEquals('W', bug.getDirection());
        bug.execute(map.getCells(),3);
        Assert.assertEquals('N', bug.getDirection());
    }

    @Test
    public void execute_bug_direction_turn_left() {
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
        Bug bug = map.getBugs().get(0);
        int[] script = new int[4];
        script[0] = 12;
        script[1] = 12;
        script[2] = 12;
        script[3] = 12;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        Cell sameCell = map.getCells()[2][13];
        Assert.assertTrue(sameCell instanceof Bug);
        Bug updatedBug = (Bug) sameCell;
        Assert.assertEquals(bug, updatedBug);
        //new direction
        Assert.assertEquals('W', bug.getDirection());
        bug.execute(map.getCells(),1);
        Assert.assertEquals('S', bug.getDirection());
        bug.execute(map.getCells(),2);
        Assert.assertEquals('E', bug.getDirection());
        bug.execute(map.getCells(),3);
        Assert.assertEquals('N', bug.getDirection());
    }
    @Test
    public void execute_bug_att_for_noBug() {
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
        Bug bug = map.getBugs().get(0);
        int[] script = new int[1];
        script[0] = 13;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //no bug in front of bug
        Cell emptyCell = map.getCells()[1][13];
        Assert.assertFalse(emptyCell instanceof Bug);
    }
    @Test
    public void execute_bug_att_for_Bug() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            b              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(1);
        int[] script = new int[1];
        script[0] = 13;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug in front of bug
        Cell bugCell = map.getCells()[1][13];
        Assert.assertTrue(bugCell instanceof Food);
    }

    @Test
    public void execute_bug_eat_food() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            f              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[1];
        script[0] = 14;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //food in front of bug check in new bug
        Cell foodCell = map.getCells()[1][13];
        Assert.assertFalse(foodCell instanceof Food);
        Assert.assertTrue(foodCell instanceof Bug);
    }
    @Test
    public void execute_ifEnemy_turnRight() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            b              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(1);
        int[] script = new int[2];
        script[0] = 30;
        script[1] = 11;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug in front of bug check in
        Cell enemyCell = map.getCells()[1][13];
        Assert.assertTrue(enemyCell instanceof Bug);
        //bug turn right
        Assert.assertEquals('E',bug.getDirection());
    }

    @Test
    public void execute_ifEnemy_att() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            b              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(1);
        int[] script = new int[2];
        script[0] = 30;
        script[1] = 13;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug att other bug
        Cell enemyCell = map.getCells()[1][13];
        Assert.assertTrue(enemyCell instanceof Food);
    }
    @Test
    public void execute_ifAlly_att() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            a              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(1);
        int[] script = new int[2];
        script[0] = 31;
        script[1] = 13;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug att other bug
        Cell enemyCell = map.getCells()[1][13];
        Assert.assertTrue(enemyCell instanceof Bug);
    }
    @Test
    public void execute_ifAlly_turnLeft() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            a              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(1);
        int[] script = new int[2];
        script[0] = 31;
        script[1] = 12;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug turn left
        Assert.assertEquals('W',bug.getDirection());
    }

    @Test
    public void execute_ifFood_eat() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            f              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[2];
        script[0] = 32;
        script[1] = 14;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug eat food
        Cell enemyCell = map.getCells()[1][13];
        Assert.assertTrue(enemyCell instanceof Bug);
    }
    @Test
    public void execute_ifFood_mov() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            f              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[2];
        script[0] = 32;
        script[1] = 10;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug att other bug
        Assert.assertEquals('E',bug.getDirection());
    }
    @Test
    public void execute_ifEmpty_mov() {
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
        Bug bug = map.getBugs().get(0);
        int[] script = new int[2];
        script[0] = 33;
        script[1] = 10;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(1,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug move empty area
        Cell emptyCell = map.getCells()[1][13];
        Assert.assertTrue(emptyCell instanceof Bug);
    }
    @Test
    public void execute_ifEmpty_eat() {
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
        Bug bug = map.getBugs().get(0);
        int[] script = new int[2];
        script[0] = 33;
        script[1] = 14;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug eat empty area
        Cell emptyCell = map.getCells()[1][13];
        Assert.assertFalse(emptyCell instanceof Food);
    }
    @Test
    public void execute_ifWall_eat() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            X              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[2];
        script[0] = 34;
        script[1] = 14;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug eat wall area
        Cell wallCell = map.getCells()[1][13];
        Assert.assertFalse(wallCell instanceof Food);
    }
    @Test
    public void execute_ifWall_turnRight() {
        //Arrange
        GameMap map = new GameMap(
"""
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
X            X              X
X            a              X
X                           X
X    b       f        c     X
X                           X
X           d               X
X                           X
XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"""
        );
        //Act
        Bug bug = map.getBugs().get(0);
        int[] script = new int[2];
        script[0] = 34;
        script[1] = 11;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug turn right
        Assert.assertEquals('E', bug.getDirection());
    }
    @Test
    public void execute_goto() {
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
        Bug bug = map.getBugs().get(0);
        int[] script = new int[3];
        script[0] = 11;
        script[1] = 35;
        script[2] = 0;
        bug.setBugScript(script);
        bug.execute(map.getCells(),0);
        bug.execute(map.getCells(),1);
        //Assert
        //3,14
        Assert.assertEquals(2,bug.getRow());
        Assert.assertEquals(13,bug.getColumn());
        //bug turn right
        Assert.assertEquals('S', bug.getDirection());
    }
}