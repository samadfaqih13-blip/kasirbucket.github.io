/**
 * Class induk (abstract) untuk semua jenis buket.
 *
 * - Abstract: toko tidak menjual "buket umum"; setiap buket pasti
 *   BuketBunga, BuketSnack, atau BuketUang.
 * - Encapsulation: semua atribut private. Harga dasar hanya bisa diubah
 *   lewat setHargaDasar() yang menolak nilai nol atau negatif.
 * - Polymorphism: hitungHarga() wajib di-override oleh setiap class turunan.
 */
public abstract class Buket {

    private final String kode;   // final: kode tidak boleh berubah setelah dibuat
    private String nama;
    private double hargaDasar;   // biaya kertas, pita, dan jasa rangkai

    public Buket(String kode, String nama, double hargaDasar) {
        if (kode == null || kode.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode buket tidak boleh kosong.");
        }
        this.kode = kode.trim().toUpperCase();
        setNama(nama);
        setHargaDasar(hargaDasar);
    }

    // ===== Getter =====
    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaDasar() {
        return hargaDasar;
    }

    // ===== Setter dengan validasi =====
    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama buket tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setHargaDasar(double hargaDasar) {
        if (hargaDasar <= 0) {
            throw new IllegalArgumentException("Harga dasar harus lebih dari 0.");
        }
        this.hargaDasar = hargaDasar;
    }

    /**
     * Method abstract: tidak punya isi di sini karena rumus harga
     * berbeda untuk setiap jenis buket.
     */
    public abstract double hitungHarga();

    /**
     * Menampilkan baris utama info buket. Pemanggilan hitungHarga() di sini
     * bersifat polymorphic: Java menjalankan versi milik object aslinya.
     * Class turunan meng-override method ini untuk menambah rincian.
     */
    public void tampilkanInfo() {
        System.out.printf("[%s] %-22s %12s%n", kode, nama, Rupiah.format(hitungHarga()));
    }
}
