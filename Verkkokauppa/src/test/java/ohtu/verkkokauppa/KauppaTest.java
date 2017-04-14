package ohtu.verkkokauppa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 *
 * @author tuomo
 */
public class KauppaTest {

    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;
    
    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        k = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
        
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void kahdenEriOstoksenJalkeenPankinMetodiaTilisiirtoKutustaan () {
        when(viite.uusi()).thenReturn(666);
        
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "vesi", 3));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "leipä", 6));
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto(eq("pekka"), eq(666), eq("12345"), anyString(), eq(9));
    }
    
    @Test
    public void kahdenSamanOstoksenJalkeenPankinMetodiaTilisiirtoKutustaan () {
        when(viite.uusi()).thenReturn(666);
        
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "vesi", 3));
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto(eq("pekka"), eq(666), eq("12345"), anyString(), eq(6));
    }
    
    @Test
    public void yhdenRiittavanJaYhdenLoppuneenOstoksenOstoToimii() {
        when(viite.uusi()).thenReturn(666);
        
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "vesi", 3));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "leipä", 6));
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto(eq("pekka"), eq(666), eq("12345"), anyString(), eq(3));
    }
    
    @Test
    public void aloitaAsiointiNollaaEdellisetTiedot() {
        when(viite.uusi()).thenReturn(666);
        
        when(varasto.saldo(1)).thenReturn(3);
        when(varasto.saldo(2)).thenReturn(4);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "vesi", 3));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "leipä", 6));
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto(eq("pekka"), eq(666), eq("12345"), anyString(), eq(6));
    }
    
    @Test
    public void kauppaPyytaaUudenViitteenJokaMaksulle() {
        when(viite.uusi())
                .thenReturn(666)
                .thenReturn(2);
        
        when(varasto.saldo(1)).thenReturn(3);
        when(varasto.saldo(2)).thenReturn(4);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "vesi", 3));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "leipä", 6));
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), eq(666), eq("12345"), anyString(), eq(3));
        
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), eq(2), eq("12345"), anyString(), eq(6));
    }
    
    @Test
    public void kaupanMetodiPoistaKoristaPalauttaaTuotteenVarastoon() {
        Tuote t = new Tuote(2, "leipä", 6);
        when(varasto.haeTuote(1)).thenReturn(t);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.poistaKorista(1);
        
        verify(varasto).palautaVarastoon(t);
    }
    
}
