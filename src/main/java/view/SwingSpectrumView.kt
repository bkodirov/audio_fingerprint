package view

import java.awt.*
import java.lang.Double.max
import java.lang.Double.min

class SwingSpectrumView(private val spectrogramData: Array<DoubleArray>) : javax.swing.JFrame() {
    private val max: Double
    private val min: Double
    init {
        this.layout = FlowLayout()
        this.setSize(spectrogramData[0].size, spectrogramData.size)
        var currMin = Double.MAX_VALUE
        var currMax = Double.MIN_VALUE
        spectrogramData.forEach {
            it.forEach {
                currMin = min(it, currMin)
                currMax = max(it, currMax)
            }
        }
        max = currMax
        min = currMin
        print("min = $min, max = $max")
    }

    override fun paint(g: Graphics?) {
        val g2d = g as Graphics2D
        for (blockIndex in spectrogramData.indices) {
            for (frqIndex in spectrogramData[blockIndex].indices) {
                g2d.color = colorCode(spectrogramData[blockIndex][frqIndex])
                g2d.fillRect(frqIndex, blockIndex, 1, 1)
            }
        }
    }

    fun colorCode(value: Double): Color {
        val offset = value - this.min
        val percentage: Float = (offset / (max - min)).toFloat()
        return if (percentage < 0.5) {
            Color(percentage*2, 1f, 0f)
        } else {
            Color(1f, (1-percentage)*2, 0f)
        }
    }
}
