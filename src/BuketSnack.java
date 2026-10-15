/**
 * Buket snack. Mewarisi Buket (inheritance).
 * Rumus harga: hargaDasar + jumlahSnack x hargaPerSnack
 */
public class BuketSnack extends Buket {

    private String isiSnack;
    private int jumlahSnack;
    private double hargaPerSnack;

    public BuketSnack(String kode, String nama, double hargaDasar, String isiSnack, int jumlahSnack, double hargaPerSnack) {
        super(kode, nama, hargaDasar);
        setIsiSnack(isiSnack);
        setJumlahSnack(jumlahSnack);
        setHargaPerSnack(hargaPerSnack);
    }

    public String getIsiSnack() {
        return isiSnack;
    }

    public int getJumlahSnack() {
        return jumlahSnack;
    }

    public double getHargaPerSnack() {
        return hargaPerSnack;
    }

    public void setIsiSnack(String isiSnack) {
        if (isiSnack == null || isiSnack.trim().isEmpty()) {
            throw new IllegalArgumentException("Isi snack tidak boleh kosong.");
        }
        this.isiSnack = isiSnack.trim();
    }

    public void setJumlahSnack(int jumlahSnack) {
        if (jumlahSnack < 1) {
            throw new IllegalArgumentException("Jumlah snack minimal 1.");
        }
        this.jumlahSnack = jumlahSnack;
    }

    public void setHargaPerSnack(double hargaPerSnack) {
        if (hargaPerSnack <= 0) {
            throw new IllegalArgumentException("Harga per snack harus lebih dari 0.");
        }
        this.hargaPerSnack = hargaPerSnack;
    }

    @Override
    public double hitungHarga() {
        return getHargaDasar() + jumlahSnack * hargaPerSnack;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("       Buket Snack: %d snack (%s) x %s + dasar %s%n",
                jumlahSnack, isiSnack, Rupiah.format(hargaPerSnack),
                Rupiah.format(getHargaDasar()));
    }
}
