package view

import java.awt.Color
import java.awt.FlowLayout
import java.awt.Graphics
import java.awt.Graphics2D

class AudioTimeFrequencyWindow(private val pcm: ShortArray) : javax.swing.JFrame()  {
    private val windowWidth = 1000
    private val windowHeight = 400

    private var maxAmplitude: Short = Short.MIN_VALUE

    init {
        this.layout = FlowLayout()
        this.setSize(windowWidth, windowHeight)
        pcm.forEach { it -> maxAmplitude = maxOf(maxAmplitude, it) }
    }


    override fun paint(g: Graphics?) {
        println("AudioTimeFrequencyWindow, max = $maxAmplitude")
        val g2 = g as Graphics2D
        g2.color = Color.BLUE
        pcm.forEachIndexed { index, amplitude ->
            val heightPercentage = amplitude / maxAmplitude.toDouble()
            val adjustedHeight = (heightPercentage * windowHeight).toInt()
            g2.drawRect(index*windowWidth/pcm.size, ((windowHeight - adjustedHeight)/2), 1, adjustedHeight)
        }
    }
}
