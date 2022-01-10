package com.grobnerBasis;

public class Monom {
    private int coeff;
    private int x;
    private int y;
    private int z;

    public Monom() {
        setCoeff(0);
        setX(0);
        setY(0);
        setZ(0);
    }

    public Monom(int coeff, boolean x, boolean y, boolean z) {
        setCoeff(coeff);

        if (x) setX(7);
        else setX(1);

        if (y) setY(3);
        else setY(1);

        if (z) setZ(2);
        else setZ(1);
    }

    private void setCoeff(int coeff) {
        this.coeff = coeff;
    }

    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

    private void setZ(int z) {
        this.z = z;
    }

    public int getCoeff() {
        return this.coeff;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public String printMonom() {
        String coeff = "";
        String x = "";
        String y = "";
        String z = "";

        if (this.coeff > 0) coeff = "+" + Integer.toString(getCoeff());
        else coeff = Integer.toString(getCoeff());
        if (this.x == 7) x = "x";
        if (this.y == 3) y = "y";
        if (this.z == 2) z = "z";

        return coeff + x + y + z;
    }

    public void multiplyCoeff(int a) {
        setCoeff(getCoeff() * a);
    }

    public void multiplyX(int a) {
        setX(getX() * a);
    }

    public void multiplyY(int a) {
        setY(getY() * a);
    }

    public void multiplyZ(int a) {
        setZ(getZ() * a);
    }

    static public Monom operationsByMonoms(Monom monom1, Monom monom2) {
        Monom ansverMonom = new Monom();
        if (monom1.getX() * monom1.getY() * monom1.getZ() == monom2.getX() * monom2.getY() * monom2.getZ()) {
            int x = monom1.getX();
            int y = monom1.getY();
            int z = monom1.getZ();
            int coeff = monom1.getCoeff() + monom2.getCoeff();

            ansverMonom.setCoeff(coeff);
            ansverMonom.setX(x);
            ansverMonom.setY(y);
            ansverMonom.setZ(z);
        }

        return ansverMonom;
    }

    public static boolean[] intToBooleanXYZ(int x, int y, int z) {
        boolean[] booleanXYZ = new boolean[3];

        boolean boolX = false;
        boolean boolY = false;
        boolean boolZ = false;

        if (x == 7) boolX = true;
        if (y == 3) boolY = true;
        if (z == 2) boolZ = true;

        booleanXYZ[0] = boolX;
        booleanXYZ[1] = boolY;
        booleanXYZ[2] = boolZ;

        return booleanXYZ;
    }

    public static boolean[] intToBooleanXYZ(Monom monom) {
        int x = monom.getX();
        int y = monom.getY();
        int z = monom.getZ();

        boolean[] booleanXYZ = new boolean[3];

        boolean boolX = false;
        boolean boolY = false;
        boolean boolZ = false;

        if (x == 7) boolX = true;
        if (y == 3) boolY = true;
        if (z == 2) boolZ = true;

        booleanXYZ[0] = boolX;
        booleanXYZ[1] = boolY;
        booleanXYZ[2] = boolZ;

        return booleanXYZ;
    }

    public static int gcd(int a, int b) {
        int secondA = a;
        int secondB = b;
        while (secondB != 0) {
            int tmp = secondA % secondB;
            secondA = secondB;
            secondB = tmp;
        }
        return secondA;
    }

    public static boolean divisionMonomByMonom(Monom monom1, Monom monom2) {
        boolean flag = false;

        Monom monom1Tmp = Monom.cloneMonom(monom1);
        Monom monom2Tmp = Monom.cloneMonom(monom2);

        if (monom1Tmp.getCoeff() < 0) monom1Tmp.setCoeff(monom1Tmp.getCoeff() * -1);
        if (monom2Tmp.getCoeff() < 0) monom2Tmp.setCoeff(monom2Tmp.getCoeff() * -1);

        if ((monom1Tmp.getX() * monom1Tmp.getY() * monom1Tmp.getZ()) / (monom2Tmp.getX() * monom2Tmp.getY() * monom2Tmp.getZ()) == 1) {
            int gcdMonom1CoeffMonom2Coeff = gcd(monom1Tmp.getCoeff(), monom2Tmp.getCoeff());
            if (gcdMonom1CoeffMonom2Coeff != 1 && (monom1Tmp.getCoeff() == 1 && monom2Tmp.getCoeff() == 1)) {
                flag = true;
            } else if (gcdMonom1CoeffMonom2Coeff != 1 && monom1Tmp.getCoeff() == monom2Tmp.getCoeff()) {
                flag = true;
            }
        }

        return flag;
    }

    public static Monom cloneMonom(Monom monom) {
        int coeff = monom.getCoeff();
        boolean[] booleanXYZ = intToBooleanXYZ(monom);

        Monom newMonom = new Monom(coeff, booleanXYZ[0], booleanXYZ[1], booleanXYZ[2]);
        return newMonom;
    }

    public void finalize() throws Throwable {
    }
}
