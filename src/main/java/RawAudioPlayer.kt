import javax.sound.sampled.*

class RawAudioPlayer {

    private fun rawplay(targetFormat: AudioFormat, din: AudioInputStream) {
        val data = ByteArray(4096)
        val line = getLine(targetFormat)
        // Start
        line.open(targetFormat)
        line.start()

        var nBytesRead = 0
        var nBytesWritten = 0

        while (nBytesRead != -1) {
            nBytesRead = din.read(data, 0, data.size)
            if (nBytesRead != -1) {
                nBytesWritten = line.write(data, 0, nBytesRead)
            }
        }
        // Stop
        line.drain()
        line.stop()
        line.close()
        din.close()
    }

    private fun getLine(audioFormat: AudioFormat): SourceDataLine {
        val info = DataLine.Info(SourceDataLine::class.java, audioFormat)
        return AudioSystem.getLine(info) as SourceDataLine
    }
}