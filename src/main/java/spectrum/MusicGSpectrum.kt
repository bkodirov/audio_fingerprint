package spectrum

import com.musicg.wave.extension.Spectrogram
import com.musicg.graphic.GraphicRender
import com.musicg.wave.Wave
import com.musicg.wave.WaveHeader
import view.SwingSpectrumView


class MisucgSpectrum {
    fun show(pcm: ByteArray) {
        val outFolder = "out"

        // create a wave object
        val wave = Wave(WaveHeader(), pcm)
        var spectrogram = Spectrogram(wave)

        // Graphic render
        val render = GraphicRender()
         render.setHorizontalMarker(1);
         render.setVerticalMarker(1);
        render.renderSpectrogram(spectrogram, "$outFolder/spectrogram.jpg")

        // change the spectrogram representation
        val fftSampleSize = 256
        val overlapFactor = 2
        spectrogram = Spectrogram(wave, fftSampleSize, overlapFactor)
        render.renderSpectrogram(spectrogram, "$outFolder/spectrogram2.jpg")

        val spectrumView = SwingSpectrumView(flipArray(spectrogram.normalizedSpectrogramData))
        spectrumView.isVisible = true

    }

    private fun flipArray(arr: Array<DoubleArray>): Array<DoubleArray> {
        val R = arr.size
        val C = arr.first().size
        val result = Array(C) { DoubleArray(R) }
        for (r in 0 until R) {
            for (c in 0 until C) {
                result[c][r] = arr[r][c]
            }
        }
        return result

    }
}