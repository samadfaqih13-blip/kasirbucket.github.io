import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Mengelola satu transaksi: daftar item, total, pembayaran, status, dan tanggal ambil.
 *
 * Encapsulation:
 * - daftar item private dan hanya bisa ditambah lewat tambahItem()
 * - total tidak disimpan sebagai atribut, selalu dihitung dari isi pesanan
 * - pembayaran hanya lewat bayar() yang menolak uang kurang
 * - status hanya bisa diubah ke nilai yang valid lewat ubahStatus()
 */
public class Transaksi {

    public static final String DIPESAN = "Dipesan";
    public static final String SIAP_DIAMBIL = "Siap Diambil";
    public static final String SELESAI = "Selesai";

    public static final DateTimeFormatter FORMAT_TANGGAL = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static int jumlahTransaksi = 0;

    private final String idTransaksi;
    private final LocalDate tanggalPesan;
    private final LocalDate tanggalAmbil;
    private final Pelanggan pelanggan;
    private final List<ItemPesanan> items = new ArrayList<>();
    private double uangBayar;
    private String status;

    public Transaksi(Pelanggan pelanggan, LocalDate tanggalAmbil) {
        if (pelanggan == null) {
            throw new IllegalArgumentException("Pelanggan tidak boleh kosong.");
        }
        LocalDate hariIni = LocalDate.now();
        if (tanggalAmbil == null || tanggalAmbil.isBefore(hariIni)) {
            throw new IllegalArgumentException("Tanggal ambil tidak boleh sebelum hari ini.");
        }
        jumlahTransaksi++;
        this.idTransaksi = String.format("T%03d", jumlahTransaksi);
        this.tanggalPesan = hariIni;
        this.tanggalAmbil = tanggalAmbil;
        this.pelanggan = pelanggan;
        this.status = DIPESAN;
    }

    // ===== Getter (tanpa setter untuk id, tanggal, dan pelanggan) =====
    public String getIdTransaksi() {
        return idTransaksi;
    }

    public LocalDate getTanggalPesan() {
        return tanggalPesan;
    }

    public LocalDate getTanggalAmbil() {
        return tanggalAmbil;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public double getUangBayar() {
        return uangBayar;
    }

    public String getStatus() {
        return status;
    }

    public int getJumlahItem() {
        return items.size();
    }

    public boolean sudahDibayar() {
        return uangBayar > 0;
    }

    /** Object ItemPesanan dibuat di sini (komposisi: item milik transaksi ini). */
    public void tambahItem(Buket buket, int jumlah, String ucapan) {
        if (sudahDibayar()) {
            throw new IllegalStateException("Transaksi sudah dibayar, item tidak bisa ditambah.");
        }
        items.add(new ItemPesanan(buket, jumlah, ucapan));
    }

    public double hitungTotal() {
        double total = 0;
        for (ItemPesanan item : items) {
            total += item.hitungSubtotal(); // memanggil buket.hitungHarga() secara polymorphic
        }
        return total;
    }

    /** Menerima pembayaran tunai dan mengembalikan nilai kembalian. */
    public double bayar(double uang) {
        if (items.isEmpty()) {
            throw new IllegalStateException("Transaksi belum berisi buket.");
        }
        if (sudahDibayar()) {
            throw new IllegalStateException("Transaksi sudah dibayar.");
        }
        double total = hitungTotal();
        if (uang < total) {
            throw new IllegalArgumentException("Uang kurang " + Rupiah.format(total - uang) + ".");
        }
        this.uangBayar = uang;
        return uang - total;
    }

    public void ubahStatus(String statusBaru) {
        if (!DIPESAN.equals(statusBaru) && !SIAP_DIAMBIL.equals(statusBaru)
                && !SELESAI.equals(statusBaru)) {
            throw new IllegalArgumentException("Status tidak valid: " + statusBaru);
        }
        if (SELESAI.equals(status)) {
            throw new IllegalStateException("Pesanan sudah selesai, status tidak bisa diubah lagi.");
        }
        this.status = statusBaru;
    }

    public void cetakStruk(String namaToko) {
        String garis = "------------------------------------------";
        String garisTebal = "==========================================";
        System.out.println(garisTebal);
        System.out.println(tengah(namaToko.toUpperCase(), garisTebal.length()));
        System.out.println(garisTebal);
        System.out.println("No. Transaksi : " + idTransaksi);
        System.out.println("Tanggal Pesan : " + tanggalPesan.format(FORMAT_TANGGAL));
        System.out.println("Tanggal Ambil : " + tanggalAmbil.format(FORMAT_TANGGAL));
        System.out.println("Pelanggan     : " + pelanggan.getNama() + " (" + pelanggan.getNoHp() + ")");
        System.out.println(garis);
        for (ItemPesanan item : items) {
            item.tampilkanItem();
        }
        System.out.println(garis);
        double total = hitungTotal();
        System.out.printf("%-30s%12s%n", "TOTAL", Rupiah.format(total));
        if (sudahDibayar()) {
            System.out.printf("%-30s%12s%n", "Bayar", Rupiah.format(uangBayar));
            System.out.printf("%-30s%12s%n", "Kembalian", Rupiah.format(uangBayar - total));
        }
        System.out.println("Status        : " + status);
        System.out.println(garisTebal);
    }

    private static String tengah(String teks, int lebar) {
        int kiri = Math.max(0, (lebar - teks.length()) / 2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kiri; i++) {
            sb.append(' ');
        }
        return sb.append(teks).toString();
    }
}
