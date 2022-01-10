package com.grobnerBasis;

import java.util.Vector;

public class GrobnerBasis {
    private Vector<Polynomial> ideal;
    private Vector<Polynomial> grobnerBasis;

    public GrobnerBasis() {
        ideal = new Vector<Polynomial>();
        grobnerBasis = new Vector<Polynomial>();
    }

    public GrobnerBasis(Polynomial polynom) {
        ideal = new Vector<Polynomial>();
        addPolynomInIdeal(polynom);
    }

    public void addPolynomInIdeal(Polynomial polynom) {
        this.ideal.add(polynom);
    }

    public void addPolynomInGrebnerBasis(Polynomial polynom) {
        this.grobnerBasis.add(polynom);
    }

    public int getCoeff(String monom) {
        int coeff = 0;
        String sCoeff = "";
        for (int i = 0; i < monom.length(); ++i) {
            if (Character.isDigit(monom.charAt(i)) || monom.charAt(i) == '-') sCoeff += monom.charAt(i);
        }
        if (sCoeff.equals("")) coeff = 1;
        else if (sCoeff.equals("-")) coeff = -1;
        else coeff = Integer.parseInt(sCoeff);
        return coeff;
    }

    public String getX(String monom) {
        String x = "";
        for (int i = 0; i < monom.length(); ++i) {
            if (monom.charAt(i) == 'x') return monom.charAt(i) + "";
        }
        return  x;
    }

    public String getY(String monom) {
        String y = "";
        for (int i = 0; i < monom.length(); ++i) {
            if (monom.charAt(i) == 'y') return monom.charAt(i) + "";
        }
        return  y;
    }

    public String getZ(String monom) {
        String z = "";
        for (int i = 0; i < monom.length(); ++i) {
            if (monom.charAt(i) == 'z') return monom.charAt(i) + "";
        }
        return  z;
    }

    public int[] polynomEngagement(Polynomial polynom1, Polynomial polynom2) {
        int[] engagement = new int[3];
        engagement[0] = 1;
        engagement[1] = 1;
        engagement[2] = 1;

        Monom seniorPolynom1Monom = polynom1.polynom.elementAt(polynom1.getSeniorMonom());
        int mSeniorPolynom1Monom = seniorPolynom1Monom.getX() * seniorPolynom1Monom.getY() * seniorPolynom1Monom.getZ();

        Monom seniorPolynom2Monom = polynom2.polynom.elementAt(polynom2.getSeniorMonom());
        int mSeniorPolynom2Monom = seniorPolynom2Monom.getX() * seniorPolynom2Monom.getY() * seniorPolynom2Monom.getZ();
        if ((mSeniorPolynom1Monom % 7 == 0) && (mSeniorPolynom2Monom % 7 == 0)) engagement[0] = 7;
        if ((mSeniorPolynom1Monom % 3 == 0) && (mSeniorPolynom2Monom % 3 == 0)) engagement[1] = 3;
        if ((mSeniorPolynom1Monom % 2 == 0) && (mSeniorPolynom2Monom % 2 == 0)) engagement[2] = 2;

        return engagement;
    }

    public Polynomial reduction(Polynomial polynom1, Polynomial polynom2) {
        int[] engagement = polynomEngagement(polynom1, polynom2);
        Polynomial polynom1Tmp = new Polynomial(polynom1);
        Polynomial polynom2Tmp = new Polynomial(polynom2);


        Polynomial polynomialRediction = new Polynomial();

        if (engagement[0] * engagement[1] * engagement[2] != 1) {
            Monom seniorPolynom1Monom = polynom1Tmp.polynom.elementAt(polynom1Tmp.getSeniorMonom());
            Monom seniorPolynom2Monom = polynom2Tmp.polynom.elementAt(polynom2Tmp.getSeniorMonom());



            //int mSeniorPolynom1Monom = seniorPolynom1Monom.getX() * seniorPolynom1Monom.getY() * seniorPolynom1Monom.getZ() / engagement[0] / engagement[1] / engagement[2];
            int x = seniorPolynom1Monom.getX() / engagement[0];
            int y = seniorPolynom1Monom.getY() / engagement[1];
            int z = seniorPolynom1Monom.getZ() / engagement[2];

            boolean[] XYZBoolean = Monom.intToBooleanXYZ(x, y, z);

            Monom monom1Multyply = new Monom(seniorPolynom1Monom.getCoeff() * -1, XYZBoolean[0], XYZBoolean[1], XYZBoolean[2]);



            //int mSeniorPolynom2Monom = seniorPolynom2Monom.getX() * seniorPolynom2Monom.getY() * seniorPolynom2Monom.getZ() / engagement[0] / engagement[1] / engagement[2];
            x = seniorPolynom2Monom.getX() / engagement[0];
            y = seniorPolynom2Monom.getY() / engagement[1];
            z = seniorPolynom2Monom.getZ() / engagement[2];

            XYZBoolean = Monom.intToBooleanXYZ(x, y, z);

            Monom monom2Multiply = new Monom(seniorPolynom2Monom.getCoeff(), XYZBoolean[0], XYZBoolean[1], XYZBoolean[2]);



            polynom1Tmp.polynom.remove(polynom1Tmp.getSeniorMonom());
            polynom2Tmp.polynom.remove(polynom2Tmp.getSeniorMonom());



            polynom1Tmp.multiplyPolynomByMonom(monom2Multiply);
            polynom2Tmp.multiplyPolynomByMonom(monom1Multyply);

            polynomialRediction = polynom1Tmp.simplifyPolynom(polynom1Tmp, polynom2Tmp);
        }

        return polynomialRediction;
    }

    public void calculateGrobnerBasis() {
        for (int i = 0; i < this.ideal.size(); ++i) {
            Polynomial newPolynom = new Polynomial(this.ideal.elementAt(i));
            addPolynomInGrebnerBasis(newPolynom);
        }

        //Новые многочлены добавленные в базис после поиска зацеплений в grobnerBasis
        Vector<Polynomial> firstGrobnerBasis = new Vector<Polynomial>();
        //Новые многочлены добавленные в базис после поиска зацеплений в grobnerBasis и firstGrobnerBasis
        Vector<Polynomial> secondGrobnerBasis = new Vector<Polynomial>();

        for (int i = 0; i < this.grobnerBasis.size(); ++i) {
            for (int j = i + 1; j < this.grobnerBasis.size(); ++j) {
                //Поиск зацеплений и если есть зацепление у двух многочленов, то производится редуцирование
                Polynomial reducionPolinom = reduction(this.grobnerBasis.elementAt(i), this.grobnerBasis.elementAt(j));

                //Проверка получился ли полином или редуцирование свелось к 0
                if (reducionPolinom.polynom.size() != 0) {
                    //Просмотр делится ли новый многочлен на другие в базисе
                    boolean flagDivision = false;
                    for (int k = 0; k < this.grobnerBasis.size(); ++k) {
                        int indexSeniorMonomReductionRolinom = reducionPolinom.getSeniorMonom();
                        int indexSeniorMonomPolinom = this.grobnerBasis.elementAt(k).getSeniorMonom();
                        //Если разделились то flag = true
                        if (Monom.divisionMonomByMonom(reducionPolinom.polynom.elementAt(indexSeniorMonomReductionRolinom), this.grobnerBasis.elementAt(k).polynom.elementAt(indexSeniorMonomPolinom))) flagDivision = true;
                    }
                    //Если ни разу не поделился ни на один полином в текущем базисе, то добавляем его в базис
                    if (!flagDivision) firstGrobnerBasis.add(reducionPolinom);
                }
            }
        }

        //boolean flagFirstGrobnerBasis = true;

        while (firstGrobnerBasis.size() != 0) {
            //Если были найдены новые многочлены в базисе, то проверетить их на зацепления
            if (firstGrobnerBasis.size() != 0) {
                for (int i = 0; i < this.grobnerBasis.size(); ++i) {
                    for (int j = 0; j < firstGrobnerBasis.size(); ++j) {
                        Polynomial reducionPolinom = reduction(this.grobnerBasis.elementAt(i), firstGrobnerBasis.elementAt(j));

                        //Проверка получился ли полином или редуцирование свелось к 0
                        if (reducionPolinom.polynom.size() != 0) {
                            //Просмотр делится ли новый многочлен на другие в базисе
                            boolean flagDivision = false;
                            for (int k = 0; k < this.grobnerBasis.size(); ++k) {
                                int indexSeniorMonomReductionRolinom = reducionPolinom.getSeniorMonom();
                                int indexSeniorMonomPolinom = this.grobnerBasis.elementAt(k).getSeniorMonom();
                                //Если разделились то flag = true
                                if (Monom.divisionMonomByMonom(reducionPolinom.polynom.elementAt(indexSeniorMonomReductionRolinom), this.grobnerBasis.elementAt(k).polynom.elementAt(indexSeniorMonomPolinom)))
                                    flagDivision = true;
                            }


                            //Просмотр если зацепления в firstGrobnerBasis
                            for (int k = 0; k < firstGrobnerBasis.size(); ++k) {
                                int indexSeniorMonomReductionRolinom = reducionPolinom.getSeniorMonom();
                                int indexSeniorMonomPolinom = firstGrobnerBasis.elementAt(k).getSeniorMonom();
                                //Если разделились то flag = true
                                if (Monom.divisionMonomByMonom(reducionPolinom.polynom.elementAt(indexSeniorMonomReductionRolinom), firstGrobnerBasis.elementAt(k).polynom.elementAt(indexSeniorMonomPolinom)))
                                    flagDivision = true;
                            }

                            //Если ни разу не поделился ни на один полином в текущем базисе, то добавляем его в базис
                            if (!flagDivision) secondGrobnerBasis.add(reducionPolinom);
                        }
                    }
                }

                //Добавление новых многочленов в базис grobnerBasis и очистка firstGrobnerBasis
                for (int i = 0; i < firstGrobnerBasis.size(); ++i) {
                    Polynomial newPolynom = new Polynomial(firstGrobnerBasis.elementAt(i));
                    addPolynomInGrebnerBasis(newPolynom);
                }
                firstGrobnerBasis.clear();

                //Если были найдены новые многолчлены из новых многолченов, то внести их в базис firstGrobnerBasis и очистить secondGrobnerBasis
                if (secondGrobnerBasis.size() != 0) {
                    for (int i = 0; i < secondGrobnerBasis.size(); ++i) {
                        Polynomial newPolynom = new Polynomial(secondGrobnerBasis.elementAt(i));
                        firstGrobnerBasis.add(newPolynom);
                    }
                    secondGrobnerBasis.clear();
                }
            }
        }
    }

    public String printIdeal() {
        String sIdeal = "";
        for (int i = 0; i < this.ideal.size(); ++i) {
            sIdeal += ideal.elementAt(i).printPolynom() + "\n";
        }
        return sIdeal;
    }

    public String printGrobnerBasis() {
        String sGrobnerBasis = "";
        for (int i = 0; i < this.grobnerBasis.size(); ++i) {
            sGrobnerBasis += this.grobnerBasis.elementAt(i).printPolynom() + "\n";
        }
        return sGrobnerBasis;
    }
}
