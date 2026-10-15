# Sistem Kasir dan Pencatatan Pesanan Toko Buket

Prototype UTS Pemrograman Lanjut: program **Java Console** berbasis Object-Oriented Programming.

**Masalah:** pesanan buket dicatat manual, rumus harga tiap jenis buket berbeda sehingga rawan salah hitung, tanggal ambil pesanan mudah terlewat, dan total penjualan sulit diketahui.
**Solusi:** program kasir yang menghitung harga tiap jenis buket secara otomatis, mencatat transaksi beserta tanggal ambil dan statusnya, serta menampilkan rekap penjualan.

## Cara menjalankan

Butuh JDK 8 atau lebih baru.

- **Cara cepat (Windows):** klik dua kali `run.bat`.
- **VS Code:** buka folder `kasir-buket`, buka `src/Main.java`, lalu klik **Run** di atas method `main`.
- **Terminal / CMD** (dari folder `kasir-buket`):

```
javac -d out src/*.java
java -cp out Main
```

Program langsung berisi data contoh (3 buket dan 1 pelanggan) supaya bisa didemokan tanpa input awal.

## Struktur file

| File | Fungsi |
| --- | --- |
| `Buket.java` | Class induk **abstract** semua buket: kode, nama, hargaDasar; method abstract `hitungHarga()` |
| `BuketBunga.java` | Turunan Buket. Harga = hargaDasar + jumlahTangkai x hargaPerTangkai |
| `BuketSnack.java` | Turunan Buket. Harga = hargaDasar + jumlahSnack x hargaPerSnack |
| `BuketUang.java` | Turunan Buket. Harga = hargaDasar + nominalUang + jumlahLembar x biayaLipat |
| `Pelanggan.java` | Data pelanggan; ID otomatis (P001, P002, ...); validasi nomor HP |
| `ItemPesanan.java` | Satu baris pesanan: buket, jumlah, ucapan; `hitungSubtotal()` |
| `Transaksi.java` | Daftar item, total, pembayaran, kembalian, status, tanggal ambil, struk |
| `TokoBuket.java` | Menyimpan katalog, pelanggan, dan transaksi; pesanan aktif; laporan |
| `Rupiah.java` | Class bantu: format uang `Rp105.000` agar seragam di katalog, struk, dan laporan |
| `Main.java` | Menu console dan input kasir |

## Peta konsep OOP (untuk demo)

| Konsep | Di mana | Penjelasan singkat |
| --- | --- | --- |
| Class & Object | `Main.isiDataContoh()`, `Main.menuTambahBuket()`, `Main.menuTransaksiBaru()`, `Transaksi.tambahItem()` | `new BuketBunga(...)`, `new Pelanggan(...)`, `new Transaksi(...)`, `new ItemPesanan(...)` |
| Encapsulation | Semua atribut `private`; setter di `Buket`, `BuketBunga/Snack/Uang`, `Pelanggan`, `ItemPesanan`; `Transaksi.bayar()` dan `Transaksi.ubahStatus()` | Data harga, jumlah, uang, dan status hanya bisa diubah lewat method yang memvalidasi. Tidak ada `setTotal()`: total selalu dihitung dari isi pesanan. ID memakai `final` dan tidak punya setter. |
| Inheritance | `BuketBunga`, `BuketSnack`, `BuketUang` `extends Buket` | Ketiganya **adalah** buket: data umum (kode, nama, hargaDasar) cukup ditulis sekali di class induk. |
| Polymorphism | `hitungHarga()` dan `tampilkanInfo()` di-override; dipanggil di `ItemPesanan.hitungSubtotal()`, `Transaksi.hitungTotal()`, `TokoBuket.tampilkanKatalog()` | Variabel bertipe `Buket` bisa berisi object Bunga, Snack, atau Uang; Java memilih versi method sesuai object aslinya saat program berjalan. |

## Batasan prototype UTS

- Data tersimpan di ArrayList dan hilang saat program ditutup.
- Pembayaran hanya tunai; satu jenis pengguna (kasir) tanpa login.
- Rencana UAS: GUI, penyimpanan file/database, stok bahan baku, login, exception handling yang lebih lengkap.
