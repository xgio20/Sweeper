package sweeper;

class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs){
        this.totalBombs = totalBombs;
        fixBombCount();
    }

    void start(){
        bombMap = new Matrix(Box.ZERO);
        for(int x = 0; x < totalBombs; x++) {
            placeBomb();
        }
    }

    Box get (Coord coord){
        return bombMap.get(coord);
    }

    private void fixBombCount(){
        int maxBombs = (Ranges.getSize().x * Ranges.getSize().y)/2 ;
        if(totalBombs > maxBombs){
            totalBombs = maxBombs;
        }
    }

    private void placeBomb(){
        while (true){
            Coord coord = Ranges.getRandomCoord();
            if((Box.BOMB)==bombMap.get(coord)){
                continue;
            } else {
                bombMap.set(coord, Box.BOMB);
                incNumbersAroundBomb(coord);
                break;
            }

        }



    }

    private void incNumbersAroundBomb(Coord coord){
        for(Coord around : Ranges.getCoordAround(coord)){
            if(Box.BOMB != bombMap.get(around)) {
                //bombMap.set(around, Box.NUM1);
                bombMap.set(around, bombMap.get(around).getNextNumber());
            }
        }
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
