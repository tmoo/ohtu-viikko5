
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

public class JoukkoOperaatiotTest {
    
    
    private IntJoukko teeJoukko(int... luvut) {
        IntJoukko joukko = new IntJoukko();
        
        for (int luku : luvut) {
            joukko.lisaaLuku(luku);
        }
        
        return joukko;
    }    
    
    @Test
    public void yhdisteToimii() {
        IntJoukko eka = teeJoukko(1,2);
        IntJoukko toka = teeJoukko(3,4);
        
        IntJoukko tulos = IntJoukko.yhdiste(eka, toka);
        int[] vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu = {1,2,3,4};
        
        assertArrayEquals(odotettu, vastauksenLuvut);        
    } 
    
    @Test
    public void erotusToimiiKunSamojaLukuja() {
        IntJoukko joukko = teeJoukko(144,2,3,6,4);
        IntJoukko toinen = teeJoukko(3,2,5,6,7);
        
        IntJoukko erotus = IntJoukko.erotus(joukko, toinen);
        int[] vastaus = erotus.toIntArray();
        Arrays.sort(vastaus);
        int[] odotettu = {4,144};
        assertArrayEquals(odotettu, vastaus);
    }
    
    @Test
    public void erotusToimiiKunEiSamojaLukuja() {
        IntJoukko joukko = teeJoukko(144,22,43,56,24);
        IntJoukko toinen = teeJoukko(3,2,5,6,7);
        
        IntJoukko erotus = IntJoukko.erotus(joukko, toinen);
        int[] vastaus = erotus.toIntArray();
        Arrays.sort(vastaus);
        int[] odotettu = {22,24,43,56,144};
        assertArrayEquals(odotettu, vastaus);
    }
    
    @Test
    public void erotusTyhjallaJoukollaPalauttaaAlkuperaisen() {
        IntJoukko joukko = teeJoukko(144,22,43,56,24);
        IntJoukko toinen = teeJoukko();
        
        IntJoukko erotus = IntJoukko.erotus(joukko, toinen);
        int[] vastaus = erotus.toIntArray();
        Arrays.sort(vastaus);
        int[] odotettu = {22,24,43,56,144};
        assertArrayEquals(odotettu, vastaus);
    }
    
    @Test
    public void leikkausToimiiKunSamojaLukuja() {
        IntJoukko joukko = teeJoukko(144,2,3,6,4);
        IntJoukko toinen = teeJoukko(3,2,5,6,7);
        
        IntJoukko leikkaus = IntJoukko.leikkaus(joukko, toinen);
        int[] vastaus = leikkaus.toIntArray();
        Arrays.sort(vastaus);
        int[] odotettu = {2,3,6};
        assertArrayEquals(odotettu, vastaus);
    }
    
    @Test
    public void leikkausToimiiKunEiSamojaLukuja() {
        IntJoukko joukko = teeJoukko(144,22,43,56,24);
        IntJoukko toinen = teeJoukko(3,2,5,6,7);
        
        IntJoukko leikkaus = IntJoukko.leikkaus(joukko, toinen);
        int[] vastaus = leikkaus.toIntArray();
        
        int[] odotettu = {};
        assertArrayEquals(odotettu, vastaus);
    }
    
    @Test
    public void tyhjanJoukonLeikkausOnTyhja() {
        IntJoukko joukko = teeJoukko(144,22,43,56,24);
        IntJoukko toinen = teeJoukko();
        
        IntJoukko leikkaus = IntJoukko.leikkaus(joukko, toinen);
        int[] vastaus = leikkaus.toIntArray();
        Arrays.sort(vastaus);
        int[] odotettu = {};
        assertArrayEquals(odotettu, vastaus);
    }
}
