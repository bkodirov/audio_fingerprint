package utils

import kotlin.experimental.and

object ByteUtils {
    fun byteArrayToShortArrayLE(b: ByteArray): ShortArray {
        val result = ShortArray(b.size / 2)
        for (i in 0 until b.size step 2) {
            result[i / 2] = ByteToShortLE.byteArrayToShortLE(b, i)
        }
        return result
    }

    fun scalePcm(array: FloatArray, pcmApmlitudeRange: Int): ShortArray {
        val scale = Math.pow(2.0, pcmApmlitudeRange.toDouble()).toInt()
        return array.map { (scale * it).toShort() }.toShortArray()
    }
}