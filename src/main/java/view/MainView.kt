package view

import fingerprint.DTMF

fun main(args: Array<String>) {
//    val audioWindow = AudioRecognizerWindow()
//    audioWindow.createWindow()
//    dtmf.forEach { println(it) }
    val dtmf = DTMF.generateDTMFTone('1')
    val amplitudeView = AudioTimeFrequencyWindow(dtmf.map { (65536 * it).toInt() }.toIntArray())
    amplitudeView.isVisible = true
}
