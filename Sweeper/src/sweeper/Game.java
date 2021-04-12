package sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }


    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start(){
//        bombMap = new Matrix(Box.ZERO);
//        bombMap.set(new Coord(0,0),Box.BOMB);
//        bombMap.set(new Coord(0,1),Box.NUM1);
//        bombMap.set(new Coord(1,1),Box.NUM1);
//        bombMap.set(new Coord(1,0),Box.NUM1);

        bomb.start();
        flag.start();
        state = GameState.PLAYED;

    }

    public Box getBox (Coord coord){
        //return Box.values()[(coord.x + coord.y) % Box.values().length];
        //return bombMap.get(coord);
        //return bomb.get(coord);
        if(flag.get(coord)== Box.OPENED){
            return bomb.get(coord);
        } else {
            return flag.get(coord);
        }
    }

    public void pressLeftButton(Coord coord){
        //flag.setOpenedToBox(coord);
        if (gameOver()) {
            return;
        }
        openBox(coord);
        checkWinner();
    }

    private void checkWinner(){
        if(state==GameState.PLAYED){
            if(flag.getCountOfCloseBoxes() == bomb.getTotalBombs()){
                state = GameState.WINNER;
            }
        }
    }
    
    private void openBox(Coord coord) {
        switch (flag.get(coord)){
            case OPENED:setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED:return;
            case CLOSED:
                switch (bomb.get(coord)){
                    case ZERO:openBoxesAround(coord);return;
                    case BOMB:openBombs(coord);return;
                    default:flag.setOpenedToBox(coord);return;
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if(bomb.get(coord) != Box.BOMB){
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber()){
                for(Coord around : Ranges.getCoordAround(coord)){
                    if(flag.get(around) == Box.CLOSED){
                        openBox(around);
                    }
                }
            }
        }
    }

    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord:Ranges.getAllCoords()){
            if(bomb.get(coord) == Box.BOMB){
                flag.setJpenedToClosedBombBox(coord);
            } else {
                flag.setNoBombToFlagedsSafeBox(coord);
            }
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordAround(coord)){
            openBox(around);
        }
    }

    public void pressRightButton(Coord coord) {
//        flag.setFlagedToBox(coord);
        if (gameOver()) {
            return;
        }
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED){
            return false;
        } else {
            start();
            return true;
        }
    }
}
