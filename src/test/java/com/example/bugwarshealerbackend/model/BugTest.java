package com.example.bugwarshealerbackend.model;

import org.checkerframework.checker.units.qual.A;
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
}