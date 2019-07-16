package spectrum

import com.musicg.dsp.FastFourierTransform
import com.musicg.dsp.WindowFunction
import com.musicg.wave.Wave
import java.lang.Double.max
import java.lang.Double.min

class CustomSpectrum(private val audio: Wave, fftSampleSize: Int, overlapping: Int = 128) {
    val absoluteSpectrogram: Array<DoubleArray>
    val numFrequencyUnit: Int
    var unitFrequency: Double
    var relativeSpectrogram: Array<DoubleArray>

    /**
    :param pcm:  original time series
    :param samplingSize:the number of points sampled per second, so called sample_rate
    :param nFft: The number of data points used in each block for the DFT.
    :param overlapping: The number of points of overlap between blocks. The default value is 128.
    :return:
     */
    init {
        val pcm = audio.sampleAmplitudes
        val numSamples = pcm.size
        val durationSec = audio.lengthInSeconds
        if (overlapping >= fftSampleSize) throw IllegalArgumentException("Overlapping must be less then nFtt block size")
        // Create range array
        var sampledArray = IntArray(numSamples)
        if (overlapping > 0) {

        }// End of the overlapping
        val numFrames = numSamples / fftSampleSize // How many block to run FTT on
        val framePerSecond = numFrames / durationSec // HZ of the audio. ie: 44100

        // Set signals for FTT
        val window = WindowFunction()
        window.windowType = WindowFunction.HAMMING
        val hammingWindows = window.generate(fftSampleSize)
        val signals = Array(numFrames) { DoubleArray(fftSampleSize) }
        for (i in 0 until numFrames) {
            val startSample = i * fftSampleSize // Block number
            for (n in 0 until fftSampleSize) {
                signals[i][n] = pcm[startSample + n] * hammingWindows[n]
            }
        }
        // We ran window function on signals
        val fttAlgorithm = FastFourierTransform()
        absoluteSpectrogram = Array(numFrames) { DoubleArray(0) }
        for (i in 0 until numFrames) {
            absoluteSpectrogram[i] = fttAlgorithm.getMagnitudes(signals[i])
        }

        if (absoluteSpectrogram.isEmpty()) { throw IllegalArgumentException("Couldn't generate spectrogram for the given data") }
        numFrequencyUnit = absoluteSpectrogram.first().size

        // frequency could be caught within the half of nSamples according to Nyquist theory
        unitFrequency = audio.waveHeader.sampleRate.toDouble() / 2.0 / numFrequencyUnit
        // normalization of absoluteSpectrogram
        relativeSpectrogram = Array(numFrames) { DoubleArray(numFrequencyUnit) }

        // set max and min amplitudes
        var maxAmp = Double.MIN_VALUE
        var minAmp = Double.MAX_VALUE
        absoluteSpectrogram.forEach {  it.forEach {
                maxAmp = max(it, maxAmp)
                minAmp = min(it, minAmp)
        } }
        // end set max and min amplitudes

        // normalization
        // avoiding divided by zero
        val minValidAmp = if (minAmp == 0.0) 0.00000000001 else minAmp
        val diff = Math.log10(maxAmp / minAmp)    // perceptual difference
        for (i in 0 until numFrames) {
            for (j in 0 until numFrequencyUnit) {
                if (absoluteSpectrogram[i][j] < minValidAmp) {
                    spectrogram[i][j] = 0.0
                } else {
                    spectrogram[i][j] = Math.log10(absoluteSpectrogram[i][j] / minAmp) / diff
                }
            }
        }
        // end normalization
    }
}