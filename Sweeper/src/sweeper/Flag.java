package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start(){
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;

//        for (Coord around : Ranges.getCoordAround(new Coord(4,4))) {
//            flagMap.set(around, Box.OPENED);
//        }
    }

    Box get(Coord coord){
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes --;
    }

    public void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){
            case FLAGED : setClosedToBox(coord);break;
            case CLOSED : setFlagedToBox(coord);break;
        }
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    private void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    int getCountOfCloseBoxes() {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord,Box.BOMBED);
    }

    void setJpenedToClosedBombBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord,Box.OPENED);
        }
    }

    void setNoBombToFlagedsSafeBox(Coord coord) {
        if(flagMap.get(coord) == Box.FLAGED){
            flagMap.set(coord,Box.NOBOMB);
        }
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordAround(coord)) {
            if(flagMap.get(around) == Box.FLAGED){
                count++;
            }
        }
        return count;
    }
}
