package spectrum

import model.Complex
import model.FFT

class SpectrumGenerator {

    fun makeSpectrum(pcm: ByteArray, songId: Long, isMatching: Boolean) {

        val totalSize = pcm.size

        val amountPossible = totalSize / 4096

        // When turning into frequency domain we'll need complex numbers:
        val results = arrayOfNulls<Array<Complex>>(amountPossible)

        // For all the chunks:
        for (times in 0 until amountPossible) {
            val complex = arrayOfNulls<Complex>(4096)
            for (i in 0..4095) {
                // Put the time domain data into a complex number with imaginary
                // part as 0:
                complex[i] = Complex(pcm[times * 4096 + i].toDouble(), 0.0)
            }
            // Perform FFT analysis on the chunk:
            results[times] = FFT.fft(complex)
        }
        //        determineKeyPoints(results, songId, isMatching);
//        val spectrumView = SwingSpectrumView(results, 4096)
//        spectrumView.setVisible(true)
    }
}