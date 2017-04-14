package ohtu.intjoukkosovellus;

import java.util.Arrays;

public class IntJoukko {

    public final static int KAPASITEETTI = 5,
            OLETUSKASVATUSKOKO = 5;
    private int kasvatuskoko;
    private int[] joukko;
    private int alkioidenLkm;

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti ei saa olla negatiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoko ei saa olla negatiivinen");
        }
        joukko = new int[kapasiteetti];
        Arrays.fill(joukko, 0);
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUSKOKO);
    }

    public IntJoukko() {
        this(KAPASITEETTI);
    }

    public boolean lisaaLuku(int luku) {
        if (!lukuSisaltyyJoukkoon(luku)) {
            joukko[alkioidenLkm] = luku;
            alkioidenLkm++;

            if (alkioidenLkm == joukko.length) {
                joukko = Arrays.copyOf(joukko, alkioidenLkm + kasvatuskoko);
            }
            return true;
        }
        return false;
    }

    public boolean lukuSisaltyyJoukkoon(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == joukko[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poistaLuku(int luku) {
        int indeksi = -1;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == joukko[i]) {
                indeksi = i;
                joukko[indeksi] = 0;
                break;
            }
        }
        if (indeksi != -1) {
            System.arraycopy(joukko, indeksi + 1, joukko, indeksi, joukko.length - indeksi - 1);
            alkioidenLkm--;
        }
        return indeksi != -1;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            sb.append(joukko[i]).append(", ");
        }
        if (alkioidenLkm > 0) {
            sb.append(joukko[alkioidenLkm - 1]);
        }
        sb.append("}");
        return sb.toString();
    }

    public int[] toIntArray() {
        return Arrays.copyOf(joukko, alkioidenLkm);
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaaLuku(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaaLuku(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaaLuku(bTaulu[j]);
                }
            }
        }
        return y;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko uusi = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        for (int i = 0; i < aTaulu.length; i++) {
            uusi.lisaaLuku(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            uusi.poistaLuku(bTaulu[i]);
        }
        return uusi;
    }
}
