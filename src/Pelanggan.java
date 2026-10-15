/**
 * Menyimpan data pelanggan yang memesan buket.
 * ID dibuat otomatis (P001, P002, ...) dan tidak bisa diubah.
 */
public class Pelanggan {

    private static int jumlahPelanggan = 0; // static: dipakai bersama untuk membuat ID

    private final String idPelanggan;
    private String nama;
    private String noHp;

    public Pelanggan(String nama, String noHp) {
        setNama(nama);
        setNoHp(noHp);
        jumlahPelanggan++;
        this.idPelanggan = String.format("P%03d", jumlahPelanggan);
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pelanggan tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setNoHp(String noHp) {
        // Nomor HP hanya boleh angka, 10 sampai 13 digit
        if (noHp == null || !noHp.trim().matches("\\d{10,13}")) {
            throw new IllegalArgumentException("Nomor HP harus berupa 10-13 digit angka.");
        }
        this.noHp = noHp.trim();
    }

    public void tampilkanData() {
        System.out.printf("[%s] %-20s %s%n", idPelanggan, nama, noHp);
    }
}
