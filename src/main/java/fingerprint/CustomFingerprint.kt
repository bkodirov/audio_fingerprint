package fingerprint

import model.Complex
import model.DataPoint
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap


private const val UPPER_LIMIT = 300
private const val LOWER_LIMIT = 40
private const val FUZ_FACTOR = 2
private val RANGE = intArrayOf(40, 80, 120, 180, UPPER_LIMIT + 1)

class CustomFingerprint {
//    private var recordPoints: Array<DoubleArray>? = Array()
//    private var points: Array<LongArray>? = null
//    private var hashMap: Map<Long, List<DataPoint>> = mutableMapOf()
//    private var matchMap: Map<Int, Map<Int, Int>>? = null // Map<SongId, Map<Offset,
//
//
//    private fun hash(p1: Long, p2: Long, p3: Long, p4: Long): Long {
//        return ((p4 - p4 % FUZ_FACTOR) * 100000000 + (p3 - p3 % FUZ_FACTOR) * 100000 + (p2 - p2 % FUZ_FACTOR) * 100
//                + (p1 - p1 % FUZ_FACTOR))
//    }
//
//    internal fun determineKeyPoints(results: Array<Array<Complex>>, songId: Long, isMatching: Boolean) {
//        this.matchMap = HashMap()
//
//        var fstream: FileWriter? = null
//        try {
//            fstream = FileWriter("result.txt")
//        } catch (e1: IOException) {
//            e1.printStackTrace()
//        }
//
//        val outFile = BufferedWriter(fstream!!)
//
//        val highscores = Array(results.size) { DoubleArray(5) }
//        for (i in results.indices) {
//            for (j in 0..4) {
//                highscores[i][j] = 0.0
//            }
//        }
//
//        recordPoints = Array(results.size) { DoubleArray(UPPER_LIMIT) }
//        for (i in results.indices) {
//            for (j in 0 until UPPER_LIMIT) {
//                recordPoints[i][j] = 0.0
//            }
//        }
//
//        points = Array(results.size) { LongArray(5) }
//        for (i in results.indices) {
//            for (j in 0..4) {
//                points[i][j] = 0
//            }
//        }
//
//        for (t in results.indices) {
//            for (freq in LOWER_LIMIT until UPPER_LIMIT - 1) {
//                // Get the magnitude:
//                val mag = Math.log(results[t][freq].abs() + 1)
//
//                // Find out which range we are in:
//                val index = getIndex(freq)
//
//                // Save the highest magnitude and corresponding frequency:
//                if (mag > highscores[t][index]) {
//                    highscores[t][index] = mag
//                    recordPoints[t][freq] = 1.0
//                    points[t][index] = freq.toLong()
//                }
//            }
//
//            try {
//                for (k in 0..4) {
//                    outFile.write("" + highscores[t][k] + ";" + recordPoints[t][k] + "\t")
//                }
//                outFile.write("\n")
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            val h = hash(points[t][0], points[t][1], points[t][2], points[t][3])
//
//            if (isMatching) {
//                val listPoints: List<DataPoint>
//
//                if ((listPoints = hashMap.get(h)) != null) {
//                    for (dP in listPoints) {
//                        val offset = Math.abs(dP.time - t)
//                        var tmpMap: MutableMap<Int, Int>? = null
//                        if ((tmpMap = this.matchMap.get(dP.songId)) == null) {
//                            tmpMap = HashMap()
//                            tmpMap[offset] = 1
//                            matchMap.put(dP.songId, tmpMap)
//                        } else {
//                            val count = tmpMap!![offset]
//                            if (count == null) {
//                                tmpMap[offset] = 1
//                            } else {
//                                tmpMap[offset] = count + 1
//                            }
//                        }
//                    }
//                }
//            } else {
//                var listPoints: MutableList<DataPoint>? = null
//                if ((listPoints = hashMap.get(h)) == null) {
//                    listPoints = ArrayList()
//                    val point = DataPoint(songId.toInt(), t)
//                    listPoints.add(point)
//                    hashMap.put(h, listPoints)
//                } else {
//                    val point = DataPoint(songId.toInt(), t)
//                    listPoints!!.add(point)
//                }
//            }
//        }
//        try {
//            outFile.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//    }
//
//    // Find out in which range
//    fun getIndex(freq: Int): Int {
//        var i = 0
//        while (RANGE[i] < freq)
//            i++
//        return i
//    }

}
