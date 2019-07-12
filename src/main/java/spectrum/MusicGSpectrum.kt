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

        val spectrumView = SwingSpectrumView(spectrogram.normalizedSpectrogramData)
        spectrumView.isVisible = true

        // Graphic render
        val render = GraphicRender()
        // render.setHorizontalMarker(1);
        // render.setVerticalMarker(1);
        render.renderSpectrogram(spectrogram, "$outFolder/spectrogram.jpg")

        // change the spectrogram representation
        val fftSampleSize = 512
        val overlapFactor = 2
        spectrogram = Spectrogram(wave, fftSampleSize, overlapFactor)
        render.renderSpectrogram(spectrogram, "$outFolder/spectrogram2.jpg")
    }
}