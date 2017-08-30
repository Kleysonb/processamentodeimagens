package interpolacao

import java.io.File
import java.util.*
import javax.imageio.ImageIO
import java.awt.image.DataBufferByte
import java.awt.image.DataBuffer
import javax.swing.Spring.height
import javax.swing.Spring.width



class Bilinear{

    fun seletorArquivo(selectedFile: File) {
        var img = ImageIO.read(selectedFile)
        //println(img.)


        var width = img.getWidth()
        var height = img.getHeight()
        val toArray = img.getRaster().getDataBuffer()
        var array = (toArray as DataBufferByte).data
        var hasAlphaChannel = img.getAlphaRaster() != null

        //var i;
        for (item in array) println(item)
        //println(array)
    }

}