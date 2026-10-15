import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Program utama: menampilkan menu console dan menerima input kasir.
 * Object TokoBuket dibuat di sini, lalu semua menu memanggil method milik object tersebut.
 */
public class Main {

    private static final TokoBuket toko = new TokoBuket("Toko Buket");
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        isiDataContoh();
        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            int pilihan = bacaAngka("Pilih menu: ");
            try {
                switch (pilihan) {
                    case 1: toko.tampilkanKatalog(); break;
                    case 2: menuTambahBuket(); break;
                    case 3: menuUbahHarga(); break;
                    case 4: menuHapusBuket(); break;
                    case 5: menuTambahPelanggan(); break;
                    case 6: toko.tampilkanPelanggan(); break;
                    case 7: menuTransaksiBaru(); break;
                    case 8: menuUbahStatus(); break;
                    case 9: toko.tampilkanPesananAktif(); break;
                    case 10: toko.tampilkanLaporan(); break;
                    case 0: jalan = false; break;
                    default: System.out.println("Menu tidak tersedia.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                // Pesan kesalahan berasal dari validasi di setter/method class lain
                System.out.println("Gagal: " + e.getMessage());
            }
        }
        System.out.println("Terima kasih. Program selesai.");
    }

    private static void tampilkanMenu() {
        System.out.println("\n========== KASIR " + toko.getNamaToko().toUpperCase() + " ==========");
        System.out.println("1. Tampilkan katalog buket");
        System.out.println("2. Tambah buket");
        System.out.println("3. Ubah harga dasar buket");
        System.out.println("4. Hapus buket");
        System.out.println("5. Tambah pelanggan");
        System.out.println("6. Tampilkan pelanggan");
        System.out.println("7. Transaksi baru");
        System.out.println("8. Ubah status pesanan");
        System.out.println("9. Pesanan belum selesai");
        System.out.println("10. Laporan penjualan");
        System.out.println("0. Keluar");
    }

    /** Data awal agar program bisa langsung didemokan. Harga hanya contoh. */
    private static void isiDataContoh() {
        toko.tambahBuket(new BuketBunga("B001", "Mawar Merah", 25000, "Mawar", 10, 8000));
        toko.tambahBuket(new BuketSnack("B002", "Snack Wisuda", 30000, "Chitato, Oreo, Pocky", 12, 5000));
        toko.tambahBuket(new BuketUang("B003", "Uang 200 Ribu", 35000, 200000, 20, 1500));
        toko.tambahPelanggan(new Pelanggan("Andi", "081234567890"));
    }

    // ===================== MENU =====================

    private static void menuTambahBuket() {
        System.out.println("\nJenis buket: 1. Bunga  2. Snack  3. Uang");
        int jenis = bacaAngka("Pilih jenis: ");
        if (jenis < 1 || jenis > 3) {
            System.out.println("Jenis tidak tersedia.");
            return;
        }
        String kode = bacaTeks("Kode buket (contoh B004): ");
        String nama = bacaTeks("Nama buket: ");
        double hargaDasar = bacaDesimal("Harga dasar (kertas, pita, jasa): ");

        // Object dibuat sesuai jenis, tetapi semuanya disimpan sebagai tipe Buket
        Buket buket;
        if (jenis == 1) {
            String bunga = bacaTeks("Jenis bunga: ");
            int tangkai = bacaAngka("Jumlah tangkai: ");
            double hargaTangkai = bacaDesimal("Harga per tangkai: ");
            buket = new BuketBunga(kode, nama, hargaDasar, bunga, tangkai, hargaTangkai);
        } else if (jenis == 2) {
            String isi = bacaTeks("Isi snack: ");
            int jumlah = bacaAngka("Jumlah snack: ");
            double hargaSnack = bacaDesimal("Harga per snack: ");
            buket = new BuketSnack(kode, nama, hargaDasar, isi, jumlah, hargaSnack);
        } else {
            double nominal = bacaDesimal("Nominal uang: ");
            int lembar = bacaAngka("Jumlah lembar: ");
            double biaya = bacaDesimal("Biaya lipat per lembar: ");
            buket = new BuketUang(kode, nama, hargaDasar, nominal, lembar, biaya);
        }
        toko.tambahBuket(buket);
        System.out.println("Buket berhasil ditambahkan:");
        buket.tampilkanInfo();
    }

    private static void menuUbahHarga() {
        toko.tampilkanKatalog();
        String kode = bacaTeks("Kode buket: ");
        double harga = bacaDesimal("Harga dasar baru: ");
        if (toko.ubahHarga(kode, harga)) {
            System.out.println("Harga dasar berhasil diubah.");
            toko.cariBuket(kode).tampilkanInfo();
        } else {
            System.out.println("Buket dengan kode " + kode + " tidak ditemukan.");
        }
    }

    private static void menuHapusBuket() {
        toko.tampilkanKatalog();
        String kode = bacaTeks("Kode buket yang dihapus: ");
        if (toko.hapusBuket(kode)) {
            System.out.println("Buket " + kode.toUpperCase() + " dihapus dari katalog.");
        } else {
            System.out.println("Buket dengan kode " + kode + " tidak ditemukan.");
        }
    }

    private static Pelanggan menuTambahPelanggan() {
        String nama = bacaTeks("Nama pelanggan: ");
        String noHp = bacaTeks("No. HP: ");
        Pelanggan p = new Pelanggan(nama, noHp);
        toko.tambahPelanggan(p);
        System.out.println("Pelanggan ditambahkan dengan ID " + p.getIdPelanggan());
        return p;
    }

    private static void menuTransaksiBaru() {
        // 1. Pilih pelanggan
        toko.tampilkanPelanggan();
        String id = bacaTeksBolehKosong("ID pelanggan (kosongkan untuk pelanggan baru): ");
        Pelanggan p = id.isEmpty() ? menuTambahPelanggan() : toko.cariPelanggan(id);
        if (p == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        // 2. Tanggal ambil
        Transaksi t;
        while (true) {
            LocalDate tanggalAmbil = bacaTanggal("Tanggal ambil (dd-mm-yyyy, kosongkan = hari ini): ");
            try {
                t = new Transaksi(p, tanggalAmbil); // object Transaksi dibuat di sini
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal: " + e.getMessage());
            }
        }

        // 3. Masukkan buket
        toko.tampilkanKatalog();
        while (true) {
            String kode = bacaTeksBolehKosong("Kode buket (kosongkan jika selesai): ");
            if (kode.isEmpty()) {
                break;
            }
            Buket b = toko.cariBuket(kode);
            if (b == null) {
                System.out.println("Kode tidak ditemukan.");
                continue;
            }
            int jumlah = bacaAngka("Jumlah: ");
            String ucapan = bacaTeksBolehKosong("Ucapan kartu (boleh kosong): ");
            try {
                t.tambahItem(b, jumlah, ucapan);
                System.out.println("Ditambahkan. Total sementara: " + Rupiah.format(t.hitungTotal()));
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal: " + e.getMessage());
            }
        }
        if (t.getJumlahItem() == 0) {
            System.out.println("Transaksi dibatalkan karena belum ada buket.");
            return;
        }

        // 4. Pembayaran
        System.out.println("Total yang harus dibayar: " + Rupiah.format(t.hitungTotal()));
        double kembalian;
        while (true) {
            double uang = bacaDesimal("Uang bayar: ");
            try {
                kembalian = t.bayar(uang);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal: " + e.getMessage());
            }
        }
        System.out.println("Kembalian: " + Rupiah.format(kembalian));

        // 5. Simpan dan cetak struk
        toko.simpanTransaksi(t);
        System.out.println();
        t.cetakStruk(toko.getNamaToko());
    }

    private static void menuUbahStatus() {
        toko.tampilkanPesananAktif();
        String id = bacaTeks("ID transaksi: ");
        Transaksi t = toko.cariTransaksi(id);
        if (t == null) {
            System.out.println("Transaksi tidak ditemukan.");
            return;
        }
        System.out.println("Status sekarang: " + t.getStatus());
        System.out.println("1. " + Transaksi.DIPESAN + "  2. " + Transaksi.SIAP_DIAMBIL
                + "  3. " + Transaksi.SELESAI);
        int pilih = bacaAngka("Status baru: ");
        String[] daftar = {Transaksi.DIPESAN, Transaksi.SIAP_DIAMBIL, Transaksi.SELESAI};
        if (pilih < 1 || pilih > 3) {
            System.out.println("Pilihan tidak tersedia.");
            return;
        }
        t.ubahStatus(daftar[pilih - 1]);
        System.out.println("Status " + t.getIdTransaksi() + " sekarang: " + t.getStatus());
    }

    // ===================== BANTUAN INPUT =====================

    private static String bacaBaris(String pesan) {
        System.out.print(pesan);
        if (!input.hasNextLine()) { // input habis (misalnya saat pengujian otomatis)
            System.out.println("\nInput selesai. Program ditutup.");
            System.exit(0);
        }
        return input.nextLine().trim();
    }

    private static String bacaTeks(String pesan) {
        while (true) {
            String teks = bacaBaris(pesan);
            if (!teks.isEmpty()) {
                return teks;
            }
            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static String bacaTeksBolehKosong(String pesan) {
        return bacaBaris(pesan);
    }

    private static int bacaAngka(String pesan) {
        while (true) {
            try {
                return Integer.parseInt(bacaBaris(pesan));
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka bulat.");
            }
        }
    }

    private static double bacaDesimal(String pesan) {
        while (true) {
            try {
                return Double.parseDouble(bacaBaris(pesan).replace(".", "").replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka, contoh 25000.");
            }
        }
    }

    private static LocalDate bacaTanggal(String pesan) {
        while (true) {
            String teks = bacaBaris(pesan);
            if (teks.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(teks, Transaksi.FORMAT_TANGGAL);
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah. Contoh: 20-10-2026.");
            }
        }
    }
}
