import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {
    private int countSteps;
    public void run() {
        setBeepersInBag(1000);
        countSteps = 0;

        int[] array = getDimensions();
        int x = array[0], y = array[1];
        if (isCornerCase(x, y)){
            System.out.println("Number of all moves = " + countSteps);
            return;
        }

        directedMove((y - 1) / 2, 2);
        turnRight();
        if (x % 2 == 0 && y % 2 == 0) {
            int a = (x / 2 - 1) * (y / 2), b = (x / 2) * (y / 2 - 1);
            if (x == y){
                stepLine(x, true, false);
                goFromWestToNorth(x);
                stepLine(x, false, false);
                returnToStart();
            }
            else if ((x / 2) % 2 == (y / 2) % 2){
                int diff = Math.abs(a - b);
                if (x > y){
                    stepLineWithReplacement(x, diff, true, true);
                } else{
                    stepLine(x, true, false);
                }
                goFromWestToNorth(x);
                if (x > y){
                    stepLine(y, false, false);
                } else{
                    stepLineWithReplacement(y, diff, false, true);
                }
                returnToStart();
            }
            else {
                if (a > b){
                    b++;
                } else {
                    a++;
                }
                int diff = Math.abs(a - b);
                if (diff != 0) {
                    if (x > y){
                        stepLineWithReplacement(x, diff, true, false);
                    } else{
                        stepLine(x, true, false);
                    }
                    goFromWestToNorth(x);
                    if (x > y){
                        stepLine(y, false, false);
                    } else{
                        stepLineWithReplacement(y, diff, false, false);
                    }
                    returnToStart();
                }
                else{
                    stepLine(x - ((x > y)? 1 : 0), true, x > y);
                    goFromWestToNorth(x);
                    stepLine(y - ((x < y)? 1 : 0), false, x < y);
                    returnToStart();
                }
            }
        }
        else if (x % 2 == 1 && y % 2 == 1){
            moveUntilBlocked(true);
            goFromWestToNorth(x + 1);
            moveUntilBlocked(true);
            returnToStart();
        }

        else if (y % 2 == 0){
            optimizedDoubleLine(x);
            goFromWestToNorth(x + 1);
            moveUntilBlocked(true);
            returnToStart();
        }

        else if (x % 2 == 0){
            moveUntilBlocked(true);
            goFromWestToNorth(x);
            optimizedDoubleLine(y);
            returnToStart();
        }
        System.out.println("Number of all moves = " + countSteps);
    }

    public int[] getDimensions(){
        int[] array = new int[2];
        array[0] = moveUntilBlocked() + 1;
        turnLeft();
        array[1] = moveUntilBlocked() + 1;
        return array;
    }

    public void putBeeper(boolean withBeeper) {
        if (withBeeper && noBeepersPresent()) {
            putBeeper();
        }
    }

    public int moveUntilBlocked(boolean withBeeper){
        int count = 0;
        putBeeper(withBeeper);
        while(frontIsClear()){
            move();
            putBeeper(withBeeper);
            countSteps++;
            count++;
        }
        return count;
    }

    public int moveUntilBlocked(){
        return moveUntilBlocked(false);
    }

    public void move(int steps, boolean withBeeper){
        putBeeper(withBeeper);
        while(steps > 0){
            if (frontIsClear()) {
                move();
                countSteps++;
                putBeeper(withBeeper);
            }
            steps--;
        }
    }

    public void move(int steps){
        move(steps, false);
    }

    public void directedMove(int steps, int direction){
        if (direction == 1){        // 1 is up
            while(!facingNorth()){
                turnLeft();
            }
            move(steps);
        }
        else if (direction == 2){   // 2 is down
            while(!facingSouth()){
                turnLeft();
            }
            move(steps);
        }
        else if (direction == 3){   // 3 is right
            while(!facingEast()){
                turnLeft();
            }
            move(steps);
        }
        else if (direction == 4){   // 4 is left
            while(!facingWest()){
                turnLeft();
            }
            move(steps);
        }
    }

    public void diagonalMove(int direction){
        if (direction == 1){        // up-left
            directedMove(1, 1);
            directedMove(1, 4);
        }
        else if (direction == 2){   // up-right
            directedMove(1, 1);
            directedMove(1, 3);
        }
        else if (direction == 3){   // down-right
            directedMove(1, 2);
            directedMove(1, 3);
        }
        else {                      // down-left
            directedMove(1, 2);
            directedMove(1, 4);
        }
    }

    public void returnToStart(){
        turnRight();
        moveUntilBlocked();
        turnAround();
    }

    public void goFromWestToNorth(int n){
        directedMove(n / 2 - 1, 3);
        turnLeft();
        moveUntilBlocked();
        turnAround();
    }

    public void optimizedDoubleLine(int n){
        int c = (n - 1) / 2;
        int space = c / 2 - ((c % 2 == 0)? 1 : 0);
        move(space, true);
        turnLeft();
        move(1, (c % 2 == 1));
        turnRight();
        move(1);
        move(space, true);
        move(1, true);
        move((c % 2 == 0)? 1 : 0);
        move(space, true);
        turnRight();
        move(1, (c % 2 == 1));
        turnLeft();
        move((c % 2 == 0)? 1 : 0);
        move(space, true);
    }

    public void stepLineWithReplacement(int n, int diff, boolean isHorizontal, boolean isSpecial){
        directedMove(1, (isHorizontal)? 2 : 3);
        turnRight();
        move(diff / 2 - 1, true);
        diagonalMove((isHorizontal)? 1 : 4);
        directedMove(0, (isHorizontal)? 4 : 2);
        move(n / 2 - 2 - (diff / 2), true);
        move(1, isSpecial);
        move(1);
        turnLeft();
        move(1, isSpecial);
        turnRight();
        move(1);
        directedMove(0, (isHorizontal)? 4 : 2);
        move(n / 2 - 2 - (diff / 2), true);
        diagonalMove((isHorizontal)? 1 : 4);
        directedMove(0, (isHorizontal)? 4 : 2);
        move(diff / 2 - 1, true);
    }

    public void stepLine(int n, boolean isHorizontal, boolean isSpecial){
        move(n / 2 - 1, true);
        move((isSpecial)? 2 : 0);
        diagonalMove((isHorizontal)? 4 : 3);
        directedMove(0, (isHorizontal)? 4 : 2);
        moveUntilBlocked(true);
    }

    public void specialLineWithJump(int n, boolean cond, int moved){
        move(0, true);
        move(2);
        if (moved + 2 < n) {
            move(0, true);
        }
        move((cond)? 1 : 2);
        if (n - 4 > 0){
            move(3, true);
        }
    }

    public void lineWithJump(int x, int y){
        int max = Math.max(x, y), step = Math.max((max - 3) / 4, 1);
        for(int i = 0; i < 4; i++){
            max -= step + 1;
            move(step);
            move(0, max >= 0);
            move(1);
        }
        move(max, max > 0);
    }

    public boolean isCornerCase(int x, int y){
        if (x * y == 1){        // 1 x 1
            directedMove(0, 3);
            return true;
        }
        if (x * y == 2){        // 2 x 1 or 1 x 2
            turnAround();
            moveUntilBlocked();
            returnToStart();
            return true;
        }
        if (x == 2 && y == 2){  // 2 x 2
            move(0, true);
            diagonalMove(4);
            move(0, true);
            turnAround();
            return true;
        }
        if (x == 1 || y == 1){  // 1 x n or n x 1
            directedMove(0, (x > y)? 4 : 2);
            lineWithJump(x, y);
            directedMove(0, 3);
            return true;
        }
        if (x == 2 || y == 2){  // 2 x n or n x 2
            int max = Math.max(x, y);
            directedMove(0, (x > y)? 4 : 2);
            if (max < 7){
                specialLineWithJump(max, false, 0);
                directedMove(1, (x > y)? 2 : 4);
                directedMove(max - 1, (x > y)? 3 : 1);
                turnAround();
                move(1);
                specialLineWithJump(max, true, 1);
                directedMove(0, 3);
            }
            else{
                lineWithJump(x, y);
                directedMove(1, (x > y)? 2 : 4);
                directedMove(max - 1, (x > y)? 3 : 1);
                turnAround();
                lineWithJump(x, y);
                directedMove(0, 3);
            }
            return true;
        }
        return false;
    }
}