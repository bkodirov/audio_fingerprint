package view

import java.awt.Color
import java.awt.FlowLayout
import java.awt.Graphics
import java.awt.Graphics2D

class AudioTimeFrequencyWindow(private val pcm: IntArray) : javax.swing.JFrame()  {
    private val windowWidth = 1000
    private val windowHeight = 400
    private val maxHeight = 65536

    init {
        this.layout = FlowLayout()
        this.setSize(windowWidth, windowHeight)
    }

    override fun paint(g: Graphics?) {
        println("AudioTimeFrequencyWindow")
        val g2 = g as Graphics2D
        g2.color = Color.BLUE
        pcm.forEachIndexed { index, amplitude ->

            val heightPercentage = amplitude / maxHeight.toDouble()
            val adjustedHeight = (heightPercentage * windowHeight).toInt()
            g2.drawRect(index*windowWidth/pcm.size, ((windowHeight - adjustedHeight)/2), 1, adjustedHeight)
println(adjustedHeight)
        }
    }
}
