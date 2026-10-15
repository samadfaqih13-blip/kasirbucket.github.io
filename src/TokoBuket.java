import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Menyimpan dan mengelola seluruh data toko: katalog buket, pelanggan, dan transaksi.
 * Data disimpan sementara di ArrayList (hilang saat program ditutup; penyimpanan
 * permanen ke file/database direncanakan pada tahap UAS).
 */
public class TokoBuket {

    private final String namaToko;
    private final List<Buket> katalog = new ArrayList<>();       // berisi object turunan Buket
    private final List<Pelanggan> pelanggan = new ArrayList<>();
    private final List<Transaksi> transaksi = new ArrayList<>();

    public TokoBuket(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    // ===================== KATALOG BUKET =====================

    public void tambahBuket(Buket buket) {
        if (cariBuket(buket.getKode()) != null) {
            throw new IllegalArgumentException("Kode " + buket.getKode() + " sudah dipakai.");
        }
        katalog.add(buket);
    }

    public Buket cariBuket(String kode) {
        for (Buket b : katalog) {
            if (b.getKode().equalsIgnoreCase(kode.trim())) {
                return b;
            }
        }
        return null;
    }

    public boolean ubahHarga(String kode, double hargaDasarBaru) {
        Buket b = cariBuket(kode);
        if (b == null) {
            return false;
        }
        b.setHargaDasar(hargaDasarBaru); // validasi dilakukan oleh setter di class Buket
        return true;
    }

    public boolean hapusBuket(String kode) {
        Buket b = cariBuket(kode);
        if (b == null) {
            return false;
        }
        katalog.remove(b);
        return true;
    }

    public void tampilkanKatalog() {
        System.out.println("\n--- KATALOG BUKET ---");
        if (katalog.isEmpty()) {
            System.out.println("Katalog masih kosong.");
            return;
        }
        for (Buket b : katalog) {
            b.tampilkanInfo(); // POLYMORPHISM: versi Bunga/Snack/Uang dipilih otomatis
        }
    }

    // ===================== PELANGGAN =====================

    public void tambahPelanggan(Pelanggan p) {
        pelanggan.add(p);
    }

    public Pelanggan cariPelanggan(String id) {
        for (Pelanggan p : pelanggan) {
            if (p.getIdPelanggan().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    public void tampilkanPelanggan() {
        System.out.println("\n--- DATA PELANGGAN ---");
        if (pelanggan.isEmpty()) {
            System.out.println("Belum ada pelanggan.");
            return;
        }
        for (Pelanggan p : pelanggan) {
            p.tampilkanData();
        }
    }

    // ===================== TRANSAKSI =====================

    public void simpanTransaksi(Transaksi t) {
        if (!t.sudahDibayar()) {
            throw new IllegalStateException("Transaksi harus dibayar sebelum disimpan.");
        }
        transaksi.add(t);
    }

    public Transaksi cariTransaksi(String id) {
        for (Transaksi t : transaksi) {
            if (t.getIdTransaksi().equalsIgnoreCase(id.trim())) {
                return t;
            }
        }
        return null;
    }

    /** Pesanan yang belum selesai, diurutkan dari tanggal ambil paling dekat. */
    public void tampilkanPesananAktif() {
        System.out.println("\n--- PESANAN BELUM SELESAI ---");
        List<Transaksi> aktif = new ArrayList<>();
        for (Transaksi t : transaksi) {
            if (!Transaksi.SELESAI.equals(t.getStatus())) {
                aktif.add(t);
            }
        }
        if (aktif.isEmpty()) {
            System.out.println("Tidak ada pesanan yang belum selesai.");
            return;
        }
        aktif.sort(Comparator.comparing(Transaksi::getTanggalAmbil));
        LocalDate hariIni = LocalDate.now();
        for (Transaksi t : aktif) {
            String tanda = "";
            if (t.getTanggalAmbil().isEqual(hariIni)) {
                tanda = "  <- DIAMBIL HARI INI";
            } else if (t.getTanggalAmbil().isBefore(hariIni)) {
                tanda = "  <- LEWAT TANGGAL AMBIL";
            }
            System.out.printf("%s | ambil %s | %-15s | %-12s%s%n",
                    t.getIdTransaksi(), t.getTanggalAmbil().format(Transaksi.FORMAT_TANGGAL),
                    t.getPelanggan().getNama(), t.getStatus(), tanda);
        }
    }

    public void tampilkanLaporan() {
        System.out.println("\n--- LAPORAN PENJUALAN ---");
        if (transaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        double pendapatan = 0;
        for (Transaksi t : transaksi) {
            double total = t.hitungTotal();
            pendapatan += total;
            System.out.printf("%s | %s | %-15s | %-12s | %12s%n",
                    t.getIdTransaksi(), t.getTanggalPesan().format(Transaksi.FORMAT_TANGGAL),
                    t.getPelanggan().getNama(), t.getStatus(), Rupiah.format(total));
        }
        System.out.println("------------------------------------------");
        System.out.println("Jumlah transaksi : " + transaksi.size());
        System.out.println("Total pendapatan : " + Rupiah.format(pendapatan));
    }
}
