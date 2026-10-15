import java.util.Locale;

/**
 * Class bantu (utility) untuk menampilkan nominal uang dalam format rupiah.
 * Dipakai oleh Buket, ItemPesanan, Transaksi, dan TokoBuket agar format uang
 * di katalog, struk, dan laporan selalu sama. Contoh: 105000 -> "Rp105.000".
 */
public class Rupiah {

    // Constructor private: class ini tidak perlu dibuat object-nya,
    // cukup dipanggil lewat Rupiah.format(...)
    private Rupiah() {
    }

    public static String format(double nilai) {
        // Locale.US menghasilkan "105,000", lalu koma diganti titik
        return "Rp" + String.format(Locale.US, "%,.0f", nilai).replace(',', '.');
    }
}
