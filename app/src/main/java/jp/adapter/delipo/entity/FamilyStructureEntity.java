package jp.adapter.delipo.entity;

public class FamilyStructureEntity {
    private String title = "";
    private int valueMen = 0;
    private int valueWomen = 0;

    public FamilyStructureEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValueMen() {
        return valueMen;
    }

    public void setValueMen(int valueMen) {
        this.valueMen = valueMen;
    }

    public int getValueWomen() {
        return valueWomen;
    }

    public void setValueWomen(int valueWomen) {
        this.valueWomen = valueWomen;
    }
}
