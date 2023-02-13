package model;

/**
 * divsion class
 */
public class division {

    private int divisionId;
    private String division;

    /**
     *
     * @param divisionId division Id
     * @param division divisoin
     */

    public division(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    /**
     * return divisionId
     * @return divisonId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * return division
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * toString method to translate division data into a string
     * @return translated data
     */
    @Override
    public String toString(){
        return(division);
    }
}
