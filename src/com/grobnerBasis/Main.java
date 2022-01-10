package com.grobnerBasis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GrobnerBasis grobnerBasis = new GrobnerBasis();

        try {
            File file = new File("src/input.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextInt()) {
                int n = scan.nextInt();
                Polynomial polynomial = new Polynomial();
                for (int i = 0; i < n; ++i) {
                    String sMonom = scan.next();

                    int coeffMonom = grobnerBasis.getCoeff(sMonom);
                    String x = grobnerBasis.getX(sMonom);
                    String y = grobnerBasis.getY(sMonom);
                    String z = grobnerBasis.getZ(sMonom);

                    boolean boolX = false;
                    boolean boolY = false;
                    boolean boolZ = false;

                    if (!x.equals("")) boolX = true;
                    if (!y.equals("")) boolY = true;
                    if (!z.equals("")) boolZ = true;

                    Monom monom = new Monom(coeffMonom, boolX, boolY, boolZ);

                    polynomial.addMonom(monom);
                }
                grobnerBasis.addPolynomInIdeal(polynomial);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        System.out.println("Был введёт следующий идеал базиса Грёбнера:\n" + grobnerBasis.printIdeal());

        try {
            FileWriter fileWriter = new FileWriter("src/output.txt", false);

            grobnerBasis.calculateGrobnerBasis();
            System.out.print("Базис Гнёрнера:\n" + grobnerBasis.printGrobnerBasis());
            fileWriter.write(grobnerBasis.printGrobnerBasis());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
