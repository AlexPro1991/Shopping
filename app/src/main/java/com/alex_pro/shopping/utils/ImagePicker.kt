package com.alex_pro.shopping.utils

import android.net.Uri
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import java.io.File
import java.io.InputStream

object ImagePicker {
     fun getOptions(): Options{
        val options = Options().apply{
            ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
            count = 10                                                   //Number of images to restrict selection count
            spanCount = 4                                               //Number for columns in grid
            path = "Pix/Camera"                                         //Custom Path For media Storage
            isFrontFacing = false                                       //Front Facing camera on start
            mode = Mode.All                                             //Option to select only pictures or videos or both
            flash = Flash.Auto                                          //Option to select flash type
            preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
        }
return  options
    }
    fun File.copyInStreem(inStreem: InputStream){
        this.outputStream().use {
          out -> inStreem.copyTo(out)
        }
    }
}