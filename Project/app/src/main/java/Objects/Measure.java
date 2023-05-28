package Objects;

public class Measure {
    private int lArm;
    private int rArm;
    private int chest;
    private int abdomen;
    private int hit;
    private int lThigh;
    private int rThigh;
    private int weight;

    public Measure(int lArm, int rArm, int chest, int abdomen, int hit, int lThigh, int rThigh, int weight) {
        this.lArm = lArm;
        this.rArm = rArm;
        this.chest = chest;
        this.abdomen = abdomen;
        this.hit = hit;
        this.lThigh = lThigh;
        this.rThigh = rThigh;
        this.weight = weight;

    }

    public int getlArm() {
        return lArm;
    }

    public void setlArm(int lArm) {
        this.lArm = lArm;
    }

    public int getrArm() {
        return rArm;
    }

    public void setrArm(int rArm) {
        this.rArm = rArm;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public int getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(int abdomen) {
        this.abdomen = abdomen;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getlThigh() {
        return lThigh;
    }

    public void setlThigh(int lThigh) {
        this.lThigh = lThigh;
    }

    public int getrThigh() {
        return rThigh;
    }

    public void setrThigh(int rThigh) {
        this.rThigh = rThigh;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
