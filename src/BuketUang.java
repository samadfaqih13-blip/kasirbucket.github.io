/**
 * Buket uang. Mewarisi Buket (inheritance).
 * Rumus harga: hargaDasar + nominalUang + jumlahLembar x biayaLipat
 * (nominal uang ikut dibayar pelanggan karena uangnya disediakan toko)
 */
public class BuketUang extends Buket {

    private double nominalUang;
    private int jumlahLembar;
    private double biayaLipat;   // biaya melipat per lembar uang

    public BuketUang(String kode, String nama, double hargaDasar,
                     double nominalUang, int jumlahLembar, double biayaLipat) {
        super(kode, nama, hargaDasar);
        setNominalUang(nominalUang);
        setJumlahLembar(jumlahLembar);
        setBiayaLipat(biayaLipat);
    }

    public double getNominalUang() {
        return nominalUang;
    }

    public int getJumlahLembar() {
        return jumlahLembar;
    }

    public double getBiayaLipat() {
        return biayaLipat;
    }

    public void setNominalUang(double nominalUang) {
        if (nominalUang <= 0) {
            throw new IllegalArgumentException("Nominal uang harus lebih dari 0.");
        }
        this.nominalUang = nominalUang;
    }

    public void setJumlahLembar(int jumlahLembar) {
        if (jumlahLembar < 1) {
            throw new IllegalArgumentException("Jumlah lembar minimal 1.");
        }
        this.jumlahLembar = jumlahLembar;
    }

    public void setBiayaLipat(double biayaLipat) {
        if (biayaLipat <= 0) {
            throw new IllegalArgumentException("Biaya lipat harus lebih dari 0.");
        }
        this.biayaLipat = biayaLipat;
    }

    @Override
    public double hitungHarga() {
        return getHargaDasar() + nominalUang + jumlahLembar * biayaLipat;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("       Buket Uang : uang %s, %d lembar x %s + dasar %s%n",
                Rupiah.format(nominalUang), jumlahLembar, Rupiah.format(biayaLipat),
                Rupiah.format(getHargaDasar()));
    }
}
