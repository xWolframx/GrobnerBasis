package com.grobnerBasis;

import java.util.Vector;

public class Polynomial {
    public Vector<Monom> polynom;

    public Polynomial() {
        polynom = new Vector<Monom>();
    }

    public Polynomial(Monom monom) {
        polynom = new Vector<Monom>();
        addMonom(monom);
    }

    public Polynomial(Polynomial polynom) {
        this.polynom = new Vector<Monom>();

        for (int i = 0; i < polynom.polynom.size(); ++i) {
            int coeff = polynom.polynom.elementAt(i).getCoeff();
            int x = polynom.polynom.elementAt(i).getX();
            int y = polynom.polynom.elementAt(i).getY();
            int z = polynom.polynom.elementAt(i).getZ();

            boolean boolX = false;
            boolean boolY = false;
            boolean boolZ = false;

            if (x == 7) boolX = true;
            if (y == 3) boolY = true;
            if (z == 2) boolZ = true;

            Monom newMonom = new Monom(coeff, boolX, boolY, boolZ);

            addMonom(newMonom);
        }
    }

    public void addMonom(Monom monom) {
        this.polynom.add(monom);
    }

    public Vector<Monom> getPlynom() {
        return this.polynom;
    }

    public int getSeniorMonom() {
        int seniorMonom = 0;

        int max = this.polynom.elementAt(0).getX() * this.polynom.elementAt(0).getY() * this.polynom.elementAt(0).getZ();
        for (int i = 0; i < this.polynom.size(); ++i) {
            int newMax = this.polynom.elementAt(i).getX() * this.polynom.elementAt(i).getY() * this.polynom.elementAt(i).getZ();
            if (max < newMax) {
                max = newMax;
                seniorMonom = i;
            }
        }
        return seniorMonom;
    }

    public String printPolynom() {
        if (this.polynom.size() != 0) {
            String sPolynom = "";
            for (int i = 0; i < this.polynom.size(); ++i) {
                sPolynom += this.polynom.elementAt(i).printMonom();
            }

            if (sPolynom.charAt(0) == '+') sPolynom = sPolynom.substring(1);

            return sPolynom;
        } else {
            return "0";
        }
    }

    public void multiplyPolynomByMonom(Monom monom) {
        for (int i = 0; i < this.polynom.size(); ++i) {
            polynom.elementAt(i).multiplyCoeff(monom.getCoeff());
            polynom.elementAt(i).multiplyX(monom.getX());
            polynom.elementAt(i).multiplyY(monom.getY());
            polynom.elementAt(i).multiplyZ(monom.getZ());
        }
    }

    public Polynomial simplifyPolynom(Polynomial polynom1, Polynomial polynom2) {
        Polynomial ansverPolinom = new Polynomial();

        for (int i = 0; i < polynom1.polynom.size(); ++i) {
            int coeff = polynom1.polynom.elementAt(i).getCoeff();
            int x = polynom1.polynom.elementAt(i).getX();
            int y = polynom1.polynom.elementAt(i).getY();
            int z = polynom1.polynom.elementAt(i).getZ();

            boolean[] XYZBoolean = Monom.intToBooleanXYZ(x, y, z);

            Monom newMonom = new Monom(coeff, XYZBoolean[0], XYZBoolean[1], XYZBoolean[2]);
            ansverPolinom.addMonom(newMonom);
        }

        for (int i = 0; i < polynom2.polynom.size(); ++i) {
            int coeff = polynom2.polynom.elementAt(i).getCoeff();
            int x = polynom2.polynom.elementAt(i).getX();
            int y = polynom2.polynom.elementAt(i).getY();
            int z = polynom2.polynom.elementAt(i).getZ();

            boolean[] XYZBoolean = Monom.intToBooleanXYZ(x, y, z);

            Monom newMonom = new Monom(coeff, XYZBoolean[0], XYZBoolean[1], XYZBoolean[2]);
            ansverPolinom.addMonom(newMonom);
        }

        Polynomial ansverPolinom1 = new Polynomial();
        if (ansverPolinom.polynom.size() > 1) {
            for (int i = 0; i < ansverPolinom.polynom.size(); ++i) {
                if (ansverPolinom.polynom.elementAt(i).getCoeff() != 0
                        && ansverPolinom.polynom.elementAt(i).getX() != 0
                        && ansverPolinom.polynom.elementAt(i).getY() != 0
                        && ansverPolinom.polynom.elementAt(i).getZ() != 0) {
                    boolean[] booleanXYZ = Monom.intToBooleanXYZ(ansverPolinom.polynom.elementAt(i));
                    Monom newMonom = new Monom(ansverPolinom.polynom.elementAt(i).getCoeff(), booleanXYZ[0], booleanXYZ[1], booleanXYZ[2]);

                    Monom monomCheck = new Monom();
                    for (int j = i + 1; j < ansverPolinom.polynom.size(); ++j) {
                        monomCheck = Monom.operationsByMonoms(newMonom, ansverPolinom.polynom.elementAt(j));

                        if ((monomCheck.getCoeff() != 0 || monomCheck.getCoeff() == 0) && monomCheck.getX() != 0 && monomCheck.getY() != 0 && monomCheck.getZ() != 0) {
                            newMonom = monomCheck;
                            ansverPolinom.polynom.remove(i);
                            ansverPolinom.polynom.add(i, newMonom);

                            ansverPolinom.polynom.remove(j);
                            Monom faicMonom = new Monom();
                            ansverPolinom.polynom.add(j, faicMonom);
                        } else if (monomCheck.getCoeff() == 0 && monomCheck.getX() == 0 && monomCheck.getY() == 0 && monomCheck.getZ() == 0) {
                        }
                    }
                }
            }

            for (int i = 0; i < ansverPolinom.polynom.size(); ++i) {
                if (ansverPolinom.polynom.elementAt(i).getCoeff() != 0) {
                    int coeff = ansverPolinom.polynom.elementAt(i).getCoeff();
                    boolean[] booleanXYZ = Monom.intToBooleanXYZ(ansverPolinom.polynom.elementAt(i));

                    Monom newMonom = new Monom(coeff, booleanXYZ[0], booleanXYZ[1], booleanXYZ[2]);
                    ansverPolinom1.addMonom(newMonom);
                }
            }
        } else if (ansverPolinom.polynom.size() == 1) {
            return ansverPolinom;
        }

        return ansverPolinom1;
    }
}
