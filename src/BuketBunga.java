/**
 * Buket bunga. Mewarisi Buket (inheritance).
 * Rumus harga: hargaDasar + jumlahTangkai x hargaPerTangkai
 */
public class BuketBunga extends Buket {

    private String jenisBunga;
    private int jumlahTangkai;
    private double hargaPerTangkai;

    public BuketBunga(String kode, String nama, double hargaDasar,
                      String jenisBunga, int jumlahTangkai, double hargaPerTangkai) {
        super(kode, nama, hargaDasar); // memanggil constructor class induk
        setJenisBunga(jenisBunga);
        setJumlahTangkai(jumlahTangkai);
        setHargaPerTangkai(hargaPerTangkai);
    }

    public String getJenisBunga() {
        return jenisBunga;
    }

    public int getJumlahTangkai() {
        return jumlahTangkai;
    }

    public double getHargaPerTangkai() {
        return hargaPerTangkai;
    }

    public void setJenisBunga(String jenisBunga) {
        if (jenisBunga == null || jenisBunga.trim().isEmpty()) {
            throw new IllegalArgumentException("Jenis bunga tidak boleh kosong.");
        }
        this.jenisBunga = jenisBunga.trim();
    }

    public void setJumlahTangkai(int jumlahTangkai) {
        if (jumlahTangkai < 1) {
            throw new IllegalArgumentException("Jumlah tangkai minimal 1.");
        }
        this.jumlahTangkai = jumlahTangkai;
    }

    public void setHargaPerTangkai(double hargaPerTangkai) {
        if (hargaPerTangkai <= 0) {
            throw new IllegalArgumentException("Harga per tangkai harus lebih dari 0.");
        }
        this.hargaPerTangkai = hargaPerTangkai;
    }

    @Override
    public double hitungHarga() {
        return getHargaDasar() + jumlahTangkai * hargaPerTangkai;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("       Buket Bunga: %d tangkai %s x %s + dasar %s%n",
                jumlahTangkai, jenisBunga, Rupiah.format(hargaPerTangkai),
                Rupiah.format(getHargaDasar()));
    }
}
