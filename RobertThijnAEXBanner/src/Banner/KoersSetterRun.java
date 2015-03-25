/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banner;

/**
 *
 * @author Thijn
 */
public class KoersSetterRun implements Runnable{

    String koers;
    AEXBanner banner;
    public KoersSetterRun(String koers, AEXBanner banner) {
        this.koers = koers;
        this.banner = banner;
    }

    @Override
    public void run() {
        banner.setKoersen(this.koers);
    }
    
    
    
}
