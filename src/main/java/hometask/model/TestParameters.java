package hometask.model;

public class TestParameters {

    private String section;
    private String priceFrom;
    private String priceTo;
    private String[] manufacturer;

    public TestParameters(String section, String priceFrom, String priceTo, String[] manufacturer) {
        this.section = section;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.manufacturer = manufacturer;
    }

    public String getSection() {
        return section;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public String[] getManufacturer() {
        return manufacturer;
    }
}
