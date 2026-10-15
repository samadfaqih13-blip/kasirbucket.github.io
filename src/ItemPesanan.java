/**
 * Satu baris pesanan di dalam transaksi: buket apa, berapa banyak, dan ucapannya.
 *
 * Atribut buket bertipe Buket (class induk), sehingga bisa berisi
 * BuketBunga, BuketSnack, atau BuketUang. Inilah kunci polymorphism.
 */
public class ItemPesanan {

    private final Buket buket;
    private int jumlah;
    private String ucapan;

    public ItemPesanan(Buket buket, int jumlah, String ucapan) {
        if (buket == null) {
            throw new IllegalArgumentException("Buket tidak boleh kosong.");
        }
        this.buket = buket;
        setJumlah(jumlah);
        setUcapan(ucapan);
    }

    public Buket getBuket() {
        return buket;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getUcapan() {
        return ucapan;
    }

    public void setJumlah(int jumlah) {
        if (jumlah < 1) {
            throw new IllegalArgumentException("Jumlah buket minimal 1.");
        }
        this.jumlah = jumlah;
    }

    public void setUcapan(String ucapan) {
        this.ucapan = (ucapan == null) ? "" : ucapan.trim();
    }

    public double hitungSubtotal() {
        // POLYMORPHISM: versi hitungHarga() yang dijalankan bergantung pada
        // object aslinya (BuketBunga, BuketSnack, atau BuketUang).
        return buket.hitungHarga() * jumlah;
    }

    public void tampilkanItem() {
        String baris = buket.getNama() + " x" + jumlah;
        System.out.printf("%-30s%12s%n", baris, Rupiah.format(hitungSubtotal()));
        if (!ucapan.isEmpty()) {
            System.out.println("  Ucapan: " + ucapan);
        }
    }
}
