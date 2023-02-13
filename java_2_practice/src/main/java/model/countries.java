package model;

/**
 * countries class
 */

public class countries {
    private int countryId;
    private String country;

    /**
     *
     * @param countryId country id
     * @param country country
     */

    public countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * returns countryId
     * @return country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * returns country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * toString method translastes data to string
     * @return translated data
     */
    @Override
    public String toString(){
        return (country);
    }
}
