package interpolacao

import java.io.File
import java.util.*
import javax.imageio.ImageIO
import java.awt.image.DataBufferByte

class Interpolacao{

    fun seletorArquivo(selectedFile: File) {
        var img = ImageIO.read(selectedFile)

        //var width = img.getWidth()
        //var height = img.getHeight()
        //var hasAlphaChannel = img.getAlphaRaster() != null
        //var i;

        //Método de Convnersão do File para Bytes
        val toArray = img.getRaster().getDataBuffer()
        var array = (toArray as DataBufferByte).data
        for (item in array) println(item)

    }

}